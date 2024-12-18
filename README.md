<h1 align="center"> Học viện Công nghệ Bưu chính viễn thông <br/>
    Đồ án tốt nghiệp - 
    “XÂY DỰNG HỆ THỐNG QUẢN LÝ KHO THÔNG MINH CHO DOANH NGHIỆP KINH DOANH ĐỒNG HỒ ĐEO TAY”
</h1>

# [**Introduction**](#introduction)

Người hướng dẫn : THS. NGUYỄN NGỌC DUY.
Sinh viên thực hiện : TRỊNH THANH SƠN.
Mã số sinh viên : N20DCCN134.
Lớp : D20CQCNPM02-N.
Hệ : ĐẠI HỌC CHÍNH QUY.
Đồ án tốt nghiệp này có tất cả là 3 thành phần bao gồm:

- [**API**](#) (Hiện tại)
- [**Frontend**](https://github.com/trinhthanhson/watchshop_fe_datn)
- [**AI**](https://github.com/trinhthanhson/AI_Linear_Regression)

# [**Theory**](#Theory)

- Tìm hiểu về nghiệp vụ quản lý kho.
- Tìm ngôn ngữ lập trình Java, ReacJS.
- Tìm hiểu sử dụng hệ quản trị cơ sở dữ liệu MySQL.
- Tìm hiểu sử dụng phần mềm Power Designer cho nhiệm vụ thiết kế hệ thống.
- Tìm hiểu các giải thuật học máy hồi quy.

# [**Practice**](#Practice)

## **Phân tích thiết kế cơ sở dữ liệu**

- **Thiết kế lược đồ quan hệ thực thể (ERD):**
  - Xây dựng sơ đồ quan hệ thực thể (ERD) cho hệ thống kinh doanh và quản lý kho đồng hồ đeo tay.
- **Thiết kế cơ sở dữ liệu mức khái niệm (Conceptual):**
  - Phân tích yêu cầu và thiết kế lược đồ khái niệm cho hệ thống.
- **Thiết kế cơ sở dữ liệu mức vật lý (Physical):**
  - Chuyển đổi từ lược đồ khái niệm sang lược đồ vật lý, tối ưu hóa bảng dữ liệu để tăng hiệu năng cho hệ thống.

---

## **Xây dựng hệ thống danh mục cần thiết**

- **Danh mục quản lý:**
  - Sản phẩm.
  - Loại sản phẩm.
  - Loại khách hàng.
  - Nhân viên.
  - Thương hiệu.
- **Chức năng phân quyền:**
  - Các thao tác được hỗ trợ:
    - Đọc.
    - Thêm.
    - Xóa.
    - Sửa.

---

## **Xây dựng module kinh doanh đồng hồ đeo tay**

- **Kinh doanh đồng hồ online:**
  - Hỗ trợ khả năng tìm kiếm phong phú theo nhiều thuộc tính của đồng hồ.
  - **Chức năng tạo đơn hàng cho khách hàng:**
    - Khách hàng có thể theo dõi tình trạng đơn hàng của mình.
  - **Chức năng quản lý đơn hàng cho người kinh doanh:**
    - Người kinh doanh có thể cập nhật tiến độ thực hiện đơn hàng để khách hàng theo dõi.
  - **Xử lý đơn hàng không giao được:**
    - Cho phép chọn lựa nhập hoặc không nhập sản phẩm về kho (khi đơn hàng không giao được do quá trình vận chuyển ảnh hưởng tình trạng sản phẩm).
  - **Thanh toán:**
    - Hỗ trợ các hình thức thanh toán cơ bản trong thương mại điện tử.
- **Chức năng phân quyền:**
  - Các thao tác được hỗ trợ:
    - Đọc.
    - Thêm.
    - Xóa.
    - Sửa.

---

## **Xây dựng module quản lý kho đồng hồ đeo tay**

- **Quản lý nhập kho:**
  - Nhập kho theo lô và lưu thông tin chi tiết từng lần nhập sản phẩm.
  - Tạo phiếu nhập kho theo mỗi lần phát sinh.
- **Quản lý xuất kho:**
  - Xuất kho theo lô và lưu thông tin chi tiết từng lần xuất sản phẩm.
  - Tạo phiếu xuất kho theo mỗi lần phát sinh.
- **Hiệu chỉnh thông tin:**
  - Hỗ trợ hiệu chỉnh thông tin nhập/xuất kho theo nhu cầu thực tế.
- **Đề xuất nhập kho:**
  - Dự đoán thời gian và số lượng sản phẩm cần nhập kho dựa trên mô hình hồi quy.

---

## **Xây dựng module báo cáo**

- **Loại báo cáo:**
  - Báo cáo doanh thu theo thời gian.
  - Báo cáo doanh thu theo sản phẩm.
  - Báo cáo tồn kho theo mặt hàng.

---

## **Sơ đồ thực thể ERD**

<p align="center">
    <img src="./erd_manager.jpg" />
</p>

**_Sơ đồ cơ sở dữ liệu_**

<h3 align="center">

# [**Document**](#document)

---

1. **Thiết kế cơ sở dữ liệu:**
   - Import file SQL được cung cấp trong thư mục `/data`.
   - Kiểm tra kết nối với cơ sở dữ liệu trước khi chạy ứng dụng.
2. **Cài đặt môi trường:**
   - ReactJs cho giao diện người dùng.
   - Spring Boot cho backend.
   - MySQL/PostgreSQL làm cơ sở dữ liệu.
3. **Chạy ứng dụng:**

   - Khởi động backend bằng lệnh: `mvn spring-boot:run`
   - Chạy frontend bằng lệnh: `npm run dev`.
   - Chạy AI bằng lệnh: `py routes.py`.

## **Liên hệ**

- **Email:** sontrinh2507@gmail.com
- **Hỗ trợ:** Vui lòng liên hệ nếu gặp bất kỳ lỗi nào.
