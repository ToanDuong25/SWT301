# 📚 ReadRune - Kiểm thử tự động Selenium (Page Object Model)

## 📋 Mô tả dự án
Dự án kiểm thử tự động cho ứng dụng web **ReadRune** sử dụng:
- **Selenium WebDriver** - Tự động hoá trình duyệt
- **Page Object Model (POM)** - Mô hình thiết kế kiểm thử
- **JUnit 5** - Framework kiểm thử
- **ExtentReports** - Báo cáo HTML đẹp
- **Maven** - Quản lý dependency và build
- **GitHub Actions** - CI/CD tự động

## 🌐 Ứng dụng được kiểm thử
- **URL:** https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/LandingPage
- **Platform:** OutSystems

## 🧪 Các chức năng kiểm thử

### 1. Đăng nhập (Login) - 8 Test Cases
| Test ID | Mô tả |
|---------|--------|
| TC-LG-001 | Trang Login load thành công |
| TC-LG-002 | Đăng nhập thành công với thông tin hợp lệ |
| TC-LG-003 | Đăng nhập thất bại khi email trống |
| TC-LG-004 | Đăng nhập thất bại khi password trống |
| TC-LG-005 | Đăng nhập thất bại với email sai |
| TC-LG-006 | Đăng nhập thất bại với password sai |
| TC-LG-007 | Đăng nhập thất bại khi cả 2 trường trống |
| TC-LG-008 | Chuyển sang trang Đăng ký |

### 2. Đăng ký tài khoản (Register) - 8 Test Cases
| Test ID | Mô tả |
|---------|--------|
| TC-RG-001 | Trang Đăng ký load thành công |
| TC-RG-002 | Đăng ký thành công với thông tin hợp lệ |
| TC-RG-003 | Đăng ký thất bại khi tất cả trường trống |
| TC-RG-004 | Đăng ký thất bại với email không hợp lệ |
| TC-RG-005 | Đăng ký thất bại với password quá ngắn |
| TC-RG-006 | Đăng ký thất bại khi confirm password không khớp |
| TC-RG-007 | Đăng ký thất bại khi email đã tồn tại |
| TC-RG-008 | Chuyển về trang Login |

## 📁 Cấu trúc dự án
```
EX6SWT/
├── pom.xml                                    # Maven config
├── README.md
├── .github/workflows/ci.yml                   # CI/CD pipeline
├── src/
│   ├── main/java/com/example/
│   │   ├── pages/                             # Page Object classes
│   │   │   ├── BasePage.java                  # Base class cho tất cả pages
│   │   │   ├── LoginPage.java                 # Page Object - Trang Login
│   │   │   └── RegisterPage.java              # Page Object - Trang Đăng ký
│   │   └── utils/                             # Utility classes
│   │       ├── DriverFactory.java             # Quản lý WebDriver
│   │       ├── ExtentReportManager.java       # Quản lý báo cáo
│   │       └── TestUtils.java                 # Các hàm tiện ích
│   └── test/
│       ├── java/com/example/tests/            # Test classes
│       │   ├── BaseTest.java                  # Base class cho tests
│       │   ├── LoginTest.java                 # Test đăng nhập
│       │   └── RegisterTest.java              # Test đăng ký
│       └── resources/
│           ├── config.properties              # Cấu hình test
│           └── SystemTest_ReadRune.csv        # Mẫu System Test
```

## 🚀 Hướng dẫn chạy

### Yêu cầu
- **JDK 17+**
- **Maven 3.8+**
- **Chrome / Firefox / Edge** (đã cài đặt)

### Chạy test
```bash
# Chạy tất cả tests
mvn test

# Chạy test với Chrome headless
mvn test -Dbrowser=chrome -Dheadless=true

# Chạy riêng Login test
mvn -Dtest=LoginTest test

# Chạy riêng Register test
mvn -Dtest=RegisterTest test

# Build project (không chạy test)
mvn -DskipTests package
```

### Xem báo cáo
- **Extent Report:** `target/ExtentReports/report.html`
- **Surefire Report:** `target/surefire-reports/`
- **Screenshots:** `target/screenshots/`

## ⚠️ Cập nhật Selector
Trước khi chạy test, cần cập nhật các CSS selector trong `LoginPage.java` và `RegisterPage.java` cho phù hợp với trang web thực tế:

1. Mở trang web ReadRune trên Chrome
2. Nhấn **F12** mở DevTools
3. Dùng **Ctrl+Shift+C** click vào các element (input, button)
4. Copy `id`, `name`, `class` hoặc XPath
5. Cập nhật vào các biến `By.cssSelector(...)` trong Page Object classes

## 👥 Thành viên
- **Toandt** - Nhóm phát triển ReadRune

## 📊 CI/CD
Dự án sử dụng **GitHub Actions** để tự động:
- Build project khi push code
- Chạy toàn bộ Selenium tests (headless)
- Sinh báo cáo test
- Upload artifacts (reports, screenshots)
