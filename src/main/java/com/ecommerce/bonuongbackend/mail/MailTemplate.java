package com.ecommerce.bonuongbackend.mail;

import com.ecommerce.bonuongbackend.dto.MailDto;

import java.math.BigDecimal;

public class MailTemplate {
    public static MailDto activationMail(String clientUrl, String accessToken, String id, String username) {
        String subject = "Kích hoạt tài khoản Bò Nướng Vỉa Hè ✔";
        String content = """
                        <h2>Cảm ơn bạn đã đăng ký Bò Nướng Vỉa Hè</h2>
                        <p>Bạn đã đăng ký tài khoản trên Bò Nướng Vỉa Hè! Trước khi bắt đầu, chúng tôi chỉ cần xác nhận rằng đây là bạn. Nhấp vào liên kết để kích hoạt tài khoản của bạn: </p>
                        <p>Tên đăng nhập đã đăng ký: %s</p>
                        <div>%s/activate-account?token=%s&id=%s</div>
                        """.formatted(username, clientUrl, accessToken, id);
        return new MailDto(subject, content);
    }

    public static MailDto welcomeMail(String clientUrl, String username) {
        String subject = "Chào mừng bạn đến với Bò Nướng Vỉa Hè ✔";
        String content = """
                        <h2>Bò Nướng Vỉa Hè chúc một ngày tốt lành!</h2>
                        <p>Vui lòng đăng nhập để bắt đầu</p>
                        <p>Tên đăng nhập đã đăng ký: %s</p>
                        <div>%s/login</div>
                        """.formatted(username, clientUrl);
        return new MailDto(subject, content);
    }

    public static MailDto resetPasswordMail(String clientUrl, String accessToken, String id) {
        String subject = "Đặt lại mật khẩu Bò Nướng Vỉa Hè ✔";
        String content = """
                        <h2>Bạn đã yêu cầu đặt lại mật khẩu của mình!</h2>
                        <p>Điền vào biểu mẫu để xác nhận</p>
                        <div>%s/reset-password?token=%s&id=%s</div>
                        """.formatted(clientUrl, accessToken, id);
        return new MailDto(subject, content);
    }

    public static MailDto contactMail(String email, String fullName, String message, String phone) {
        String subject = "Lời nhắn tới Bò Nướng Vỉa Hè ✔";
        String content = """
                        <h2>Lời nhắn của người dùng!</h2>
                        <div>Họ tên: %s</div>
                        <div>Email: %s</div>
                        <div>Số điện thoại: %s</div>
                        <div>Nội dung: %s</div>
                        """.formatted(fullName, email, phone, message);
        return new MailDto(subject, content);
    }

    public static MailDto orderMail(String fullName, String phone, String address, String orderId, Number totalPrice, String payment, String note) {
        String subject = "Bạn đã đặt hàng thành công tại Bò Nướng Vỉa Hè ✔";
        String content = """
                        <h2>Thông tin đơn hàng</h2>
                        <div>Họ tên: %s</div>
                        <div>Số điện thoại: %s</div>
                        <div>Địa chỉ: %s</div>
                        <div>Mã đơn hàng: %s</div>
                        <div>Tổng tiền: %s</div>
                        <div>Trạng thái thanh toán: %s</div>
                        <div>Ghi chú: %s</div>
                        """.formatted(fullName, phone, address, orderId, totalPrice, payment, note);
        return new MailDto(subject, content);
    }
}
