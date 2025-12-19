# Ứng dụng To-Do List (Kiến trúc MVVM)

##  Tổng quan

Đây là một ứng dụng ghi chú công việc (To-Do List) được xây dựng trên nền tảng Android bằng ngôn ngữ Kotlin. Dự án này được thiết kế theo kiến trúc MVVM (Model-View-ViewModel) hiện đại, sử dụng DataBinding để tạo sự liên kết chặt chẽ và tự động giữa giao diện người dùng (View) và logic nghiệp vụ (ViewModel).

## . Tính năng

*   **Đăng ký & Đăng nhập:** Hệ thống quản lý người dùng cơ bản.
*   **Quản lý công việc:**
    *   Thêm công việc mới.
    *   Xem danh sách các công việc.
    *   Sửa nội dung công việc.
    *   Xóa công việc.
*   **Lưu trữ cục bộ:** Dữ liệu người dùng và công việc được lưu trữ an toàn trên thiết bị bằng SQLite.
*   **Giao diện động:** Giao diện tự động cập nhật khi có sự thay đổi dữ liệu mà không cần can thiệp thủ công.

## 3. Công nghệ sử dụng

*   **Ngôn ngữ:** Kotlin
*   **Kiến trúc:** MVVM (Model-View-ViewModel)
*   **Thư viện Android Jetpack:**
    *   `ViewModel`: Quản lý logic và trạng thái của UI.
    *   `LiveData`: Cung cấp dữ liệu có thể quan sát, giúp UI tự động cập nhật.
    *   `DataBinding`: Liên kết các thành phần UI trong layout với nguồn dữ liệu một cách trực tiếp.
*   **Giao diện:** `RecyclerView` để hiển thị danh sách một cách hiệu quả.
*   **Cơ sở dữ liệu:** SQLite.

## 4. Nguyên lý hoạt động (Kiến trúc MVVM)

Kiến trúc MVVM chia ứng dụng thành ba thành phần chính, giúp mã nguồn trở nên rõ ràng, dễ kiểm thử và bảo trì.


### Sơ đồ luồng dữ liệu và tương tác trực quan:
# Ứng dụng To-Do List (Kiến trúc MVVM)

## . Tổng quan

Đây là một ứng dụng ghi chú công việc (To-Do List) được xây dựng trên nền tảng Android bằng ngôn ngữ Kotlin. Dự án này được thiết kế theo kiến trúc MVVM (Model-View-ViewModel) hiện đại, sử dụng DataBinding để tạo sự liên kết chặt chẽ và tự động giữa giao diện người dùng (View) và logic nghiệp vụ (ViewModel).

## . Tính năng

*   **Đăng ký & Đăng nhập:** Hệ thống quản lý người dùng cơ bản.
*   **Quản lý công việc:**
    *   Thêm công việc mới.
    *   Xem danh sách các công việc.
    *   Sửa nội dung công việc.
    *   Xóa công việc.
*   **Lưu trữ cục bộ:** Dữ liệu người dùng và công việc được lưu trữ an toàn trên thiết bị bằng SQLite.
*   **Giao diện động:** Giao diện tự động cập nhật khi có sự thay đổi dữ liệu mà không cần can thiệp thủ công.

## . Công nghệ sử dụng

*   **Ngôn ngữ:** Kotlin
*   **Kiến trúc:** MVVM (Model-View-ViewModel)
*   **Thư viện Android Jetpack:**
    *   `ViewModel`: Quản lý logic và trạng thái của UI.
    *   `LiveData`: Cung cấp dữ liệu có thể quan sát, giúp UI tự động cập nhật.
    *   `DataBinding`: Liên kết các thành phần UI trong layout với nguồn dữ liệu một cách trực tiếp.
*   **Giao diện:** `RecyclerView` để hiển thị danh sách một cách hiệu quả.
*   **Cơ sở dữ liệu:** SQLite.

## 4. Nguyên lý hoạt động (Kiến trúc MVVM)

Kiến trúc MVVM chia ứng dụng thành ba thành phần chính, giúp mã nguồn trở nên rõ ràng, dễ kiểm thử và bảo trì.



### Sơ đồ luồng dữ liệu và tương tác trực quan:
 +-----------------------------+         +----------------------------+         +--------------------------+
  |      VIEW (Giao diện)       |         |         VIEWMODEL          |         |      MODEL (Dữ liệu)     |
  | (MainActivity, XML Layouts) |         |      (ItemViewModel)       |         | (Item.kt, DBHelper.kt)   |
  +-----------------------------+         +----------------------------+         +--------------------------+
              |                                        |                                        |
  1. Người dùng tương tác (nhấn nút "Thêm")             |                                        |
  |--------------------------------------------------->|                                        |
              |                                        |                                        |
              |                            2. Gọi hàm `addItem()`                                 |
              |                                        |--------------------------------------->| 3. Thực thi logic
              |                                        |                                        |  (lưu vào SQLite)
              |                                        |                                        |
              |                            5. `LiveData` chứa danh sách `Item` thay đổi          |
              |<---------------------------------------|                                        |
              |                                        |                            4. Tải lại dữ liệu
  6. Giao diện tự động cập nhật                      | (loadItems())                          |  từ SQLite
     (thông qua DataBinding & LiveData observer)     |<---------------------------------------|
              |


### Chi tiết các thành phần:

*   **Model (`Item.kt`, `DBHelper.kt`)**
    *   Là lớp đại diện cho dữ liệu (`Item.kt`).
    *   Chứa logic truy cập dữ liệu, trong trường hợp này là `DBHelper.kt`, chịu trách nhiệm thực hiện các thao tác thêm, xóa, sửa, đọc dữ liệu từ cơ sở dữ liệu SQLite.
    *   Model không hề biết về sự tồn tại của `ViewModel` hay `View`.

*   **View (`MainActivity.kt`, `activity_main.xml`)**
    *   Chịu trách nhiệm hiển thị giao diện người dùng.
    *   Sử dụng DataBinding để liên kết trực tiếp với `ViewModel`. Ví dụ, `android:text="@={viewModel.newItemName}"` sẽ tự động cập nhật `newItemName` trong `ViewModel` khi người dùng nhập liệu.
    *   Lắng nghe (observe) các thay đổi từ `LiveData` trong `ViewModel`. Khi `LiveData` thay đổi, `View` sẽ tự động cập nhật lại giao diện (ví dụ: hiển thị danh sách công việc mới).
    *   Chuyển tiếp các hành động của người dùng (như nhấn nút) đến `ViewModel` để xử lý (`android:onClick="@{() -> viewModel.addItem()}"`).

*   **ViewModel (`ItemViewModel.kt`)**
    *   Là cầu nối giữa `Model` và `View`.
    *   Nó chứa toàn bộ logic xử lý (business logic) và trạng thái của giao diện (ví dụ: danh sách công việc đang hiển thị, nội dung của ô nhập liệu).
    *   ViewModel tương tác với `Model` (`DBHelper`) để lấy hoặc cập nhật dữ liệu.
    *   Nó cung cấp dữ liệu cho `View` thông qua `LiveData`. Khi dữ liệu thay đổi, `LiveData` sẽ thông báo cho `View` để cập nhật lại.
    *   ViewModel không chứa bất kỳ tham chiếu nào đến `View` (Activity, Fragment), giúp nó dễ dàng được kiểm thử và không bị ảnh hưởng bởi vòng đời của `View`.


##  Hướng dẫn cài đặt

1.  Sao chép (clone) dự án này về máy tính.
2.  Mở dự án bằng Android Studio.
3.  Đợi Gradle đồng bộ (sync) các thư viện cần thiết.
4.  Chạy ứng dụng trên máy ảo Android (Emulator) hoặc thiết bị thật.
