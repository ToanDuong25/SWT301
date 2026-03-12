# 📚 BÁO CÁO CHI TIẾT - BÀI TẬP 6: SELENIUM TEST AUTOMATION DỰA TRÊN TEST PLAN

**Tóm tắt ngắn (Overview):** Bài tập này yêu cầu thực hiện V&V (Verification and Validation) thông qua kiểm thử tự động cho ứng dụng ReadRune (Login và Register) bằng Selenium, Java và TestNG. Dự án dựa trên các Test Cases được định nghĩa sẵn trong file `SystemTest_ReadRune.csv`. Cấu trúc code sử dụng Page Object Model (POM) kết hợp với công cụ xuất báo cáo (ExcelGenerator) thông qua Apache POI và quy trình CI/CD với GitHub Actions.

## Thông Tin Dự Án
- **Tên Dự Án:** EX6SWT - Automation Testing cho ứng dụng ReadRune
- **Ngôn Ngữ:** Java (Yêu cầu 11+)
- **Framework:** Selenium WebDriver, TestNG, Apache POI
- **Mô Hình Code:** Page Object Model (POM)
- **URL Ứng Dụng:** https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/LandingPage

---

## PHẦN 1: SETUP DỰ ÁN

### Bước 1: Yêu Cầu Môi Trường
- Cài đặt Java 11 hoặc mới hơn.
- Cài đặt Maven để quản lý thư viện (dependencies).

**Lệnh tải Dependencies:**
```bash
cd D:\EX6SWT\ReadRune_POM_Java
mvn clean install -DskipTests
```

---

### Bước 2: Cấu trúc Thư Mục Project

**Thư mục làm việc:**
```text
D:\EX6SWT\ReadRune_POM_Java\
├── src/main/java/
│   ├── pages/          (Page Object classes: BasePage, LoginPage, RegisterPage)
│   └── utils/          (Công cụ sinh Excel report: ExcelGenerator.java)
├── src/test/java/
│   └── tests/          (Test classes TestNG: BaseTest, LoginTest, RegisterTest)
├── src/test/resources/ (Chứa Data Driven/Test Plan: SystemTest_ReadRune.csv)
├── .github/workflows/  (File YAML cho GitHub Actions CI/CD)
└── pom.xml             (Quản lý Maven dependencies)
```

**Giải thích:**
- Áp dụng **Page Object Model (POM)** để tách gộp giao diện (UI) và logic test.
- Tích hợp **CI/CD** qua GitHub Actions.
- Dùng **Apache POI** sinh test cases ra định dạng Excel để nộp bài.

---

## PHẦN 2: PHÂN TÍCH VÀ THIẾT KẾ TEST PLAN

### Bước 3: Đọc Test Plan & Xác định nhiệm vụ
Tất cả kịch bản kiểm thử (Test Cases) được thiết kế chi tiết trong file `src/test/resources/SystemTest_ReadRune.csv`. Bạn cần đối chiếu file CSV này với code hiện tại để bổ sung những test case còn thiếu.

**Tổng cộng có 16 Test Cases được quy định:**
- **Module Login (8 Test Cases):** 
  - (TC-LG-001) Trang Login load thành công
  - (TC-LG-002) Đăng nhập thành công với thông tin hợp lệ
  - (TC-LG-003) Đăng nhập thất bại khi email trống
  - (TC-LG-004) Đăng nhập thất bại khi password trống
  - (TC-LG-005) Đăng nhập thất bại với email sai
  - (TC-LG-006) Đăng nhập thất bại với password sai
  - (TC-LG-007) Đăng nhập thất bại khi cả 2 trường trống
  - (TC-LG-008) Chuyển hướng sang trang Đăng ký
- **Module Register (8 Test Cases):**
  - (TC-RG-001) Trang Đăng ký load thành công
  - (TC-RG-002) Đăng ký thành công với thông tin hợp lệ
  - (TC-RG-003) Đăng ký thất bại khi tất cả trường trống
  - (TC-RG-004) Đăng ký thất bại với email không hợp lệ
  - (TC-RG-005) Đăng ký thất bại với password quá ngắn
  - (TC-RG-006) Đăng ký thất bại khi confirm password không khớp
  - (TC-RG-007) Đăng ký thất bại khi email đã tồn tại
  - (TC-RG-008) Chuyển hướng về trang Login

---

## PHẦN 3: IMPLEMENTATION & BÀI TẬP THỰC HÀNH YÊU CẦU

Hoàn thiện Bài Tập 6 yêu cầu bạn trực tiếp code các phần sau:

### Nhiệm Vụ 1: Inspect lại Element Locator (CẬP NHẬT QUAN TRỌNG)
Vì hệ thống OutSystems sinh ID tự động và có thể thay đổi, bạn phải:
1. Mở code của 2 file `src/main/java/pages/LoginPage.java` và `src/main/java/pages/RegisterPage.java`.
2. Mở trình duyệt, truy cập ReadRune, bật F12 (Inspect) để lấy lại các locator mới nhất (như XPath, ID, CSS Selector) của textbox, button, error message.
3. Thay thế các XPath/ID bị cũ (ví dụ: `//input[@id='Input_UsernameVal']`) trong code Page Object bằng locator mới.

### Nhiệm Vụ 2: Viết code cho các Test Cases còn thiếu
Dựa vào 16 Test Cases được định nghĩa ở file CSV, hiện tại code mẫu chỉ có 4 test cases. Bạn phải tiến hành viết thêm 12 test cases còn lại vào 2 file:
- `LoginTest.java` (Bổ sung 6 test cases).
- `RegisterTest.java` (Bổ sung 6 test cases).

---

## PHẦN 4: BUILD, RUN TEST & LẤY REPORT TỰ ĐỘNG

### Bước 4: Chạy toàn bộ Test Suite bằng Maven
Sau khi cập nhật code hoàn tất, mở Terminal/PowerShell tại thư mục gốc của project (có chứa `pom.xml`) và chạy lệnh sau để Maven khởi chạy Selenium + TestNG:

```bash
mvn test
```
**Quy trình diễn ra:** Code sẽ tự động mở Chrome/Edge, bật ứng dụng website, tự động nhập text, click button và kiểm tra (assert) các kết quả đúng/sai... Cuối cùng Terminal báo `BUILD SUCCESS` nếu tất cả pass.

### Bước 5: Sinh Report & Chụp Màn Hình Nộp Bài (Excel + Word)
Trong yêu cầu Bài tập 6, chúng ta cần nộp bảng System Test Case (Excel) và hình chụp quá trình test (Word). Project đã thiết lập sẵn:
1. **Chụp Màn Hình Tự Động:** Sau mỗi test case chạy xong (Pass/Fail), Selenium sẽ tự động chụp ảnh màn hình và lưu vào thư mục `Screenshots/` trong project.
2. **Sinh File Excel:** Chạy file `src/main/java/utils/ExcelGenerator.java` trên IDE. File test cases `System_Test_Cases.xlsx` sẽ tự động được tạo ra.
3. **Sinh File Word chứa Ảnh:** Chạy file `src/main/java/utils/WordReportGenerator.java` trên IDE. Một file tên **`Bao_Cao_Kiem_Thu_BT6.docx`** chứa tất cả hình ảnh test chụp được sẽ tự sinh ra để bạn dễ dàng làm minh chứng nộp bài.

---

## Các tính năng nâng cao trong Project
- **Chụp ảnh tự động (Auto Screenshot):** Được thiết lập trong `BaseTest.java`, tự động bắt mọi màn hình ngay trước khi browser đóng.
- **Reporting Framework:** Tự động build file hệ thống test định dạng Excel và Word (chứa hình) sử dụng thư viện `Apache POI`.
- **CI/CD:** Đã cấu hình sẵn GitHub Actions (`.github/workflows/maven.yml`).

## Cấu trúc thư mục tham khảo
- `src/main/java/pages/`: File định nghĩa UI Elements và action (POM).
- `src/main/java/utils/`: script tiện ích (`ExcelGenerator.java`, `WordReportGenerator.java`).
- `src/test/java/tests/`: Chứa kịch bản kiểm thử tĩnh (TestNG - `BaseTest`, `LoginTest`, `RegisterTest`).
- `src/test/resources/`: Chứa dữ liệu đầu vào.
- `Screenshots/`: Nơi chứa ảnh tự động chụp (tự tạo ra sau khi chạy `mvn test`).
