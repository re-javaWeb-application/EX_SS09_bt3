# Bài 3: Ghi nhớ Chế độ Ban đêm

## Phần 1 - Báo cáo phân tích I/O và thiết kế giải pháp

### Công cụ cần dùng

Sử dụng Cookie để lưu cấu hình giao diện.

Lý do:
- Cookie được lưu ở trình duyệt của client và có thể đặt thời hạn sống 30 ngày bằng `Max-Age`.
- Khi người dùng tắt trình duyệt hoặc tắt máy, cookie vẫn còn nếu chưa hết hạn.
- Mỗi lần client gửi request về server, cookie sẽ được gửi kèm để server biết giao diện người dùng đã chọn.

Không dùng Session vì:
- Session chủ yếu lưu dữ liệu ở phía server.
- Session thường mất khi trình duyệt đóng, hết thời gian timeout, hoặc server xóa session.
- Session không phù hợp với yêu cầu phải nhớ giao diện ít nhất 30 ngày trên máy khách.

### I/O

Input:
- `POST /change-theme`: nhận tham số `theme` từ form, giá trị hợp lệ là `light` hoặc `dark`.
- `GET /`: nhận cookie `theme` do client gửi lên.

Output:
- `POST /change-theme`: trả header `Set-Cookie` để lưu theme xuống client trong 30 ngày.
- `GET /`: đưa theme đã kiểm tra an toàn vào `Model` để JSP hiển thị đúng giao diện.

### Chặn bẫy dữ liệu XSS qua JavaScript

Cookie được bật cờ `HttpOnly`. Khi có `HttpOnly`, JavaScript trên trình duyệt không thể đọc cookie này qua `document.cookie`, nên giảm rủi ro bị mã XSS đánh cắp hoặc sửa cookie cấu hình.

Ngoài ra, server không tin trực tiếp dữ liệu cookie hoặc request. Trước khi đưa vào `Model`, giá trị theme được whitelist:
- Nếu là `dark` thì dùng `dark`.
- Các giá trị khác đều bị đưa về `light`.

Cookie cũng được đặt `SameSite=Lax` để giảm rủi ro request giả mạo từ site khác. Khi chạy qua HTTPS, cookie tự bật thêm `Secure`.

## Phần 2 - Triển khai

File chính:
- `src/main/java/com/re/bt3/ThemeController.java`

API đã triển khai:
- `@PostMapping("/change-theme")`: nhận theme, chuẩn hóa giá trị, gửi cookie `theme` về client với `Max-Age=30 ngày`, `Path=/`, `HttpOnly`, `SameSite=Lax`.
- `@GetMapping("/")`: đọc cookie `theme`, chuẩn hóa an toàn, đưa `theme`, `themeName`, `nextTheme`, `buttonText` vào `Model`.

Thời hạn sống cookie:
- 30 ngày = `Duration.ofDays(30)` = 2.592.000 giây.
