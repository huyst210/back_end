package com.ecommerce.bonuongbackend.service;

import com.ecommerce.bonuongbackend.dto.MailDto;
import com.ecommerce.bonuongbackend.dto.ResponseDto;
import com.ecommerce.bonuongbackend.dto.order.*;
import com.ecommerce.bonuongbackend.dto.user.ActivateDto;
import com.ecommerce.bonuongbackend.mail.MailTemplate;
import com.ecommerce.bonuongbackend.model.*;
import com.ecommerce.bonuongbackend.repository.OrderRepository;
import com.ecommerce.bonuongbackend.repository.ProductRepository;
import com.ecommerce.bonuongbackend.repository.UserRepository;
import com.ecommerce.bonuongbackend.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Value("${client-url}")
    private String baseURL;
    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;
    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private SendGridMailService sendGridMailService;

    public GetOrdersResponseDto getOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return new GetOrdersResponseDto(200,true,"Lấy dữ liệu thành công",orders);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetOrdersResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateOrderResponseDto createOrder(CreateOrderDto createOrderDto) {
        try {
            User user = userRepository.findById(createOrderDto.getUserId()).get();
            if(user == null)
                return new CreateOrderResponseDto(404,false,"Không tìm thấy tài khoản");

            int orderProductLength = createOrderDto.getOrderDetail().length;
            OrderDetail[] orderDetails = new OrderDetail[orderProductLength];
            BigDecimal totalPrice = new BigDecimal(0);
            for (int i = 0; i < orderProductLength; i++) {
                OrderDetailDto orderDetailDto = createOrderDto.getOrderDetail()[i];
                Product product = productRepository.findById(orderDetailDto.getId()).get();
                if(Objects.isNull(product))
                    return new CreateOrderResponseDto(404,false,"Không tìm thấy sản phẩm %s".formatted(orderDetailDto.getId()));

                BigDecimal price = (new BigDecimal( product.getPrice().toString() )
                        .subtract( new BigDecimal( product.getDiscount().toString() ) ))
                        .multiply(new BigDecimal(orderDetailDto.getQuantity().toString()));
                orderDetails[i] = new OrderDetail(
                        product,
                        orderDetailDto.getQuantity(),
                        price);
                totalPrice = totalPrice.add(price);
            }

            Order order = new Order(
                    orderDetails,
                    false,
                    totalPrice,
                    user,
                    createOrderDto.getNote()
            );
            orderRepository.insert(order);

            MailDto emailContent = MailTemplate.orderMail(user.getFullName(), user.getPhone(), user.getAddress(), order.getId(), order.getTotalPrice(), "Thanh toán khi nhận hàng", order.getNote());
            sendGridMailService.sendHTML(user.getEmail(), emailContent);

            return new CreateOrderResponseDto(200,true,"Tạo đơn hàng thành công", order);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new CreateOrderResponseDto(500,false,"Lỗi Server");
        }
    }

    public CreateOrderResponseDto updateOrder(String id, UpdateOrderDto updateOrderDto) {
        try {
            Order order = orderRepository.findById(id).get();
            if(Objects.isNull(order))
                return new CreateOrderResponseDto(404,false,"Không tìm thấy đơn hàng");

            order.setIsDelivery(updateOrderDto.getDelivery());
            order.setIsPayment(updateOrderDto.getPayment());
            orderRepository.save(order);

            return new CreateOrderResponseDto(200,true,"Cập nhật đơn hàng thành công", order);
        }catch (Exception exception) {
            log.error(exception.toString());
            return new CreateOrderResponseDto(500,false,"Lỗi Server");
        }
    }

    public GetOrdersResponseDto getUserOrders(String userId) {
        try {
            List<Order> orders = orderRepository.findAllByUser_Id(userId);
            return new GetOrdersResponseDto(200,true,"Lấy dữ liệu thành công",orders);
        } catch (Exception exception) {
            log.error(exception.toString());
            return new GetOrdersResponseDto(500,false,"Lỗi Server");
        }
    }

    public PaymentResponseDto createSession(CreateOrderDto createOrderDto) throws StripeException {
        Stripe.apiKey = apiKey;

        User user = userRepository.findById(createOrderDto.getUserId()).get();
        if(user == null)
            return new PaymentResponseDto(404,false,"Không tìm thấy tài khoản");

        int orderProductLength = createOrderDto.getOrderDetail().length;
        OrderDetail[] orderDetails = new OrderDetail[orderProductLength];
        BigDecimal totalPrice = new BigDecimal(0);
        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (int i = 0; i < orderProductLength; i++) {
            OrderDetailDto orderDetailDto = createOrderDto.getOrderDetail()[i];
            Product product = productRepository.findById(orderDetailDto.getId()).get();
            if(Objects.isNull(product))
                return new PaymentResponseDto(404,false,"Không tìm thấy sản phẩm %s".formatted(orderDetailDto.getId()));
            BigDecimal price = (new BigDecimal( product.getPrice().toString() )
                    .subtract( new BigDecimal( product.getDiscount().toString() ) ))
                    .multiply(new BigDecimal(orderDetailDto.getQuantity().toString()));
            orderDetails[i] = new OrderDetail(
                    product,
                    orderDetailDto.getQuantity(),
                    price);
            totalPrice = totalPrice.add(price);
            sessionItemList.add(createSessionLineItem(orderDetails[i]));
        }
        Order order = new Order(
                orderDetails,
                false,
                totalPrice,
                user,
                createOrderDto.getNote()
        );
        orderRepository.insert(order);

        String token = jwtProvider.generateTokenPayment(JWT_SECRET, order.getId());
        // success and failure urls
        String successURL = baseURL + "/payment/success?order_id=%s&token=%s".formatted(order.getId(), token);
        String failureURL = baseURL + "/payment/failed";
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .addAllLineItem(sessionItemList)
                .setSuccessUrl(successURL)
                .build();

        Session session = Session.create(params);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new PaymentResponseDto(200,true,"Tạo đơn hàng thành công", stripeResponse.getSessionId());
    }
    private SessionCreateParams.LineItem createSessionLineItem(OrderDetail createOrderDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(createOrderDto))
                .setQuantity(Long.parseLong(String.valueOf(createOrderDto.getQuantity())))
                .build();

    }
    private SessionCreateParams.LineItem.PriceData createPriceData(OrderDetail createOrderDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("vnd")
                .setUnitAmount(createOrderDto.getPrice().longValue())
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(createOrderDto.getProduct().getName())
                                .build()
                ).build();
    }

    public ResponseDto updateCheckout(ActivateDto updateCheckoutDto) {
        try {
            JwtProvider jwtProvider = new JwtProvider();
            String token = updateCheckoutDto.getToken();
            if( !jwtProvider.validateToken(JWT_SECRET, token) || !updateCheckoutDto.getId().equals(jwtProvider.getUserIdFromJWT(JWT_SECRET,token)) )
                return new ResponseDto(400, false, "Token hoặc ID không hợp lệ.");

            Order order = orderRepository.findById(updateCheckoutDto.getId()).get();
            if(Objects.isNull(order))
                return new ResponseDto(404,false,"Không tìm thấy đơn hàng");

            order.setIsPayment(true);
            orderRepository.save(order);

            MailDto emailContent = MailTemplate.orderMail(order.getUser().getFullName(), order.getUser().getPhone(), order.getUser().getAddress(), order.getId(), order.getTotalPrice(), "Đã Thanh toán", order.getNote());
            sendGridMailService.sendHTML(order.getUser().getEmail(), emailContent);

            return new ResponseDto(200,true,"Bạn đã thanh toán thành công");
        }catch (Exception exception) {
            log.error(exception.toString());
            return new ResponseDto(500,false,"Lỗi Server");
        }
    }
}
