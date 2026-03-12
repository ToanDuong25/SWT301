package utils;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WordReportGenerator {

    // ─── Helper: Heading (Bold, larger font, no built-in style needed) ─────────
    private static void addHeading(XWPFDocument doc, String text, int level) {
        XWPFParagraph p = doc.createParagraph();
        p.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = p.createRun();
        run.setText(text);
        run.setBold(true);
        run.setFontSize(level == 1 ? 15 : level == 2 ? 13 : 11);
        run.setFontFamily("Times New Roman");
        run.addBreak();
    }

    // ─── Helper: Normal paragraph ──────────────────────────────────────────────
    private static void addParagraph(XWPFDocument doc, String text) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.setText(text);
        run.setFontSize(11);
        run.setFontFamily("Times New Roman");
    }

    // ─── Helper: Bullet item ───────────────────────────────────────────────────
    private static void addBullet(XWPFDocument doc, String text) {
        XWPFParagraph p = doc.createParagraph();
        p.setIndentationLeft(720);
        XWPFRun run = p.createRun();
        run.setText("•  " + text);
        run.setFontSize(11);
        run.setFontFamily("Times New Roman");
    }

    // ─── Helper: Blank line ────────────────────────────────────────────────────
    private static void addBlankLine(XWPFDocument doc) {
        doc.createParagraph();
    }

    // ─── Helper: Separator line ────────────────────────────────────────────────
    private static void addSeparator(XWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.setText("══════════════════════════════════════════════════════════");
        run.setFontSize(9);
    }

    // ─── Helper: Code-style paragraph ─────────────────────────────────────────
    private static void addCode(XWPFDocument doc, String... lines) {
        XWPFParagraph p = doc.createParagraph();
        p.setIndentationLeft(720);
        XWPFRun run = p.createRun();
        run.setFontFamily("Courier New");
        run.setFontSize(10);
        for (int i = 0; i < lines.length; i++) {
            run.setText(lines[i]);
            if (i < lines.length - 1) run.addBreak();
        }
    }

    // ─── Helper: Một dòng trong mục lục (tên + dấu chấm + số trang) ───────────
    private static void addTocEntry(XWPFDocument doc, String title, String pageNum, boolean isSub) {
        XWPFParagraph p = doc.createParagraph();
        p.setIndentationLeft(isSub ? 560 : 0);

        // Phần tiêu đề
        XWPFRun rTitle = p.createRun();
        rTitle.setText(title + " ");
        rTitle.setFontFamily("Times New Roman");
        rTitle.setFontSize(isSub ? 11 : 12);
        rTitle.setBold(!isSub);

        // Dấu chấm lấp đầy
        XWPFRun rDots = p.createRun();
        int titleLen  = title.length();
        int dotCount  = Math.max(5, 72 - titleLen - (isSub ? 6 : 0));
        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < dotCount; i++) dots.append('.');
        rDots.setText(dots.toString());
        rDots.setFontFamily("Times New Roman");
        rDots.setFontSize(11);
        rDots.setColor("AAAAAA");

        // Số trang
        XWPFRun rPage = p.createRun();
        rPage.setText(" " + pageNum);
        rPage.setFontFamily("Times New Roman");
        rPage.setFontSize(isSub ? 11 : 12);
        rPage.setBold(!isSub);
    }

    // ─── Helper: Page break ────────────────────────────────────────────────────
    private static void addPageBreak(XWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.addBreak(BreakType.PAGE);
    }

    public static void main(String[] args) {
        System.out.println("Đang tạo báo cáo Word đầy đủ (README + ảnh)...");

        try (XWPFDocument doc = new XWPFDocument()) {

            // ══ TRANG BÌA ══════════════════════════════════════════════════════
            {
                XWPFParagraph p = doc.createParagraph();
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r = p.createRun();
                r.addBreak(); r.addBreak();
                r.setText("BÁO CÁO CHI TIẾT");
                r.setBold(true); r.setFontSize(22); r.setFontFamily("Times New Roman");
                r.addBreak();
            }
            {
                XWPFParagraph p = doc.createParagraph();
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r = p.createRun();
                r.setText("BÀI TẬP 6: SELENIUM TEST AUTOMATION DỰA TRÊN TEST PLAN");
                r.setBold(true); r.setFontSize(16); r.setFontFamily("Times New Roman");
                r.addBreak(); r.addBreak();
            }
            {
                XWPFParagraph p = doc.createParagraph();
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r = p.createRun();
                String ts = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                r.setText("Ngày tạo báo cáo: " + ts);
                r.setFontSize(12); r.setFontFamily("Times New Roman");
            }

            // Ngắt trang sang trang Mục Lục
            addPageBreak(doc);

            // ══ TRANG MỤC LỤC ══════════════════════════════════════════════════
            {
                XWPFParagraph p = doc.createParagraph();
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r = p.createRun();
                r.setText("MỤC LỤC");
                r.setBold(true);
                r.setFontSize(18);
                r.setFontFamily("Times New Roman");
                r.setUnderline(UnderlinePatterns.SINGLE);
            }
            addBlankLine(doc);
            addSeparator(doc);
            addBlankLine(doc);

            addTocEntry(doc, "A.  THÔNG TIN DỰ ÁN",                                  "3",  false);
            addBlankLine(doc);
            addTocEntry(doc, "B.  SETUP DỰ ÁN",                                       "3",  false);
            addTocEntry(doc, "B.1.  Yêu Cầu Môi Trường",                              "3",  true);
            addTocEntry(doc, "B.2.  Cấu Trúc Thư Mục Project",                        "3",  true);
            addBlankLine(doc);
            addTocEntry(doc, "C.  BUSINESS RULES & PHÂN TÍCH TEST PLAN",              "4",  false);
            addTocEntry(doc, "C.1.  Business Rules – Module LOGIN",                   "4",  true);
            addTocEntry(doc, "C.2.  Business Rules – Module REGISTER",                "4",  true);
            addBlankLine(doc);
            addTocEntry(doc, "D.  DANH SÁCH TEST CASES (16 Test Cases)",              "5",  false);
            addTocEntry(doc, "D.1.  Module Login (TC-LG-001 → TC-LG-008)",           "5",  true);
            addTocEntry(doc, "D.2.  Module Register (TC-RG-001 → TC-RG-008)",        "5",  true);
            addBlankLine(doc);
            addTocEntry(doc, "E.  IMPLEMENTATION & CÁC TÍNH NĂNG DỰ ÁN",            "6",  false);
            addBlankLine(doc);
            addTocEntry(doc, "F.  BUILD & RUN TEST",                                  "6",  false);
            addBlankLine(doc);
            addTocEntry(doc, "G.  HÌNH ẢNH MINH CHỨNG KIỂM THỬ",                    "7",  false);

            addBlankLine(doc);
            addSeparator(doc);

            // Ghi chú nhỏ dưới mục lục
            {
                XWPFParagraph note = doc.createParagraph();
                note.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun rn = note.createRun();
                rn.setText("(Số trang mang tính tham khảo — mở bằng Microsoft Word để xem phân trang chính xác)");
                rn.setFontSize(9);
                rn.setFontFamily("Times New Roman");
                rn.setItalic(true);
                rn.setColor("888888");
            }

            // Ngắt trang sang nội dung chính
            addPageBreak(doc);
            addBlankLine(doc);

            // ══ A. THÔNG TIN DỰ ÁN ════════════════════════════════════════════
            addHeading(doc, "A. THÔNG TIN DỰ ÁN", 1);
            addSeparator(doc);
            addBullet(doc, "Tên Dự Án   : EX6SWT - Automation Testing cho ứng dụng ReadRune");
            addBullet(doc, "Ngôn Ngữ    : Java (Yêu cầu Java 11+)");
            addBullet(doc, "Framework   : Selenium WebDriver, TestNG, Apache POI");
            addBullet(doc, "Mô Hình Code: Page Object Model (POM)");
            addBullet(doc, "URL Ứng Dụng: https://personal-wkrgpp1g.outsystemscloud.com/ReadRune/LandingPage");
            addBlankLine(doc);

            // ══ B. SETUP DỰ ÁN ════════════════════════════════════════════════
            addHeading(doc, "B. SETUP DỰ ÁN", 1);
            addSeparator(doc);

            addHeading(doc, "B.1. Yêu Cầu Môi Trường", 2);
            addBullet(doc, "Cài đặt Java 11 hoặc mới hơn");
            addBullet(doc, "Cài đặt Maven để quản lý thư viện (dependencies)");
            addParagraph(doc, "Lệnh tải Dependencies:");
            addCode(doc, "cd D:\\EX6SWT\\ReadRune_POM_Java", "mvn clean install -DskipTests");
            addBlankLine(doc);

            addHeading(doc, "B.2. Cấu Trúc Thư Mục Project", 2);
            addBullet(doc, "src/main/java/pages/   → Page Object classes (BasePage, LoginPage, RegisterPage)");
            addBullet(doc, "src/main/java/utils/   → Công cụ sinh báo cáo (ExcelGenerator, WordReportGenerator)");
            addBullet(doc, "src/test/java/tests/   → Test classes TestNG (BaseTest, LoginTest, RegisterTest)");
            addBullet(doc, "src/test/resources/    → Data Driven / Test Plan (SystemTest_ReadRune.csv)");
            addBullet(doc, ".github/workflows/     → File YAML cho GitHub Actions CI/CD");
            addBullet(doc, "pom.xml                → Quản lý Maven dependencies");
            addBlankLine(doc);

            // ══ C. BUSINESS RULES ═════════════════════════════════════════════
            addHeading(doc, "C. BUSINESS RULES & PHÂN TÍCH TEST PLAN", 1);
            addSeparator(doc);
            addParagraph(doc, "Tất cả kịch bản kiểm thử được thiết kế trong file SystemTest_ReadRune.csv. " +
                "Tổng cộng 16 Test Cases cho 2 module chính:");
            addBlankLine(doc);

            addHeading(doc, "C.1. Business Rules – Module LOGIN", 2);
            addBullet(doc, "BR-LG-01: Trang Login phải load thành công, hiển thị đầy đủ form đăng nhập.");
            addBullet(doc, "BR-LG-02: Hệ thống CHẤP NHẬN đăng nhập NẾU email và password đúng → chuyển hướng trang Home.");
            addBullet(doc, "BR-LG-03: Hệ thống TỪ CHỐI NẾU email bị bỏ trống → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-LG-04: Hệ thống TỪ CHỐI NẾU password bị bỏ trống → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-LG-05: Hệ thống TỪ CHỐI NẾU email không tồn tại trong hệ thống → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-LG-06: Hệ thống TỪ CHỐI NẾU password không đúng → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-LG-07: Hệ thống TỪ CHỐI NẾU cả email và password đều trống → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-LG-08: Người dùng có thể điều hướng đến trang Đăng Ký từ trang Login.");
            addBlankLine(doc);

            addHeading(doc, "C.2. Business Rules – Module REGISTER", 2);
            addBullet(doc, "BR-RG-01: Trang Đăng ký phải load thành công, hiển thị đầy đủ form đăng ký.");
            addBullet(doc, "BR-RG-02: Hệ thống CHẤP NHẬN đăng ký NẾU tất cả thông tin hợp lệ → đăng ký thành công.");
            addBullet(doc, "BR-RG-03: Hệ thống TỪ CHỐI NẾU tất cả trường đều trống → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-RG-04: Hệ thống TỪ CHỐI NẾU email sai định dạng → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-RG-05: Hệ thống TỪ CHỐI NẾU password dưới 8 ký tự → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-RG-06: Hệ thống TỪ CHỐI NẾU Confirm Password không khớp với Password → hiển thị lỗi.");
            addBullet(doc, "BR-RG-07: Hệ thống TỪ CHỐI NẾU email đã được đăng ký → hiển thị thông báo lỗi.");
            addBullet(doc, "BR-RG-08: Người dùng có thể quay về trang Login từ trang Đăng Ký.");
            addBlankLine(doc);

            // ══ D. DANH SÁCH TEST CASES ══════════════════════════════════════
            addHeading(doc, "D. DANH SÁCH TEST CASES (16 Test Cases)", 1);
            addSeparator(doc);

            addHeading(doc, "D.1. Module Login (8 Test Cases)", 2);
            addBullet(doc, "TC-LG-001 | Trang Login load thành công");
            addBullet(doc, "TC-LG-002 | Đăng nhập thành công với thông tin hợp lệ");
            addBullet(doc, "TC-LG-003 | Đăng nhập thất bại khi email để trống");
            addBullet(doc, "TC-LG-004 | Đăng nhập thất bại khi password để trống");
            addBullet(doc, "TC-LG-005 | Đăng nhập thất bại với email sai");
            addBullet(doc, "TC-LG-006 | Đăng nhập thất bại với password sai");
            addBullet(doc, "TC-LG-007 | Đăng nhập thất bại khi cả 2 trường trống");
            addBullet(doc, "TC-LG-008 | Chuyển hướng sang trang Đăng ký");
            addBlankLine(doc);

            addHeading(doc, "D.2. Module Register (8 Test Cases)", 2);
            addBullet(doc, "TC-RG-001 | Trang Đăng ký load thành công");
            addBullet(doc, "TC-RG-002 | Đăng ký thành công với thông tin hợp lệ");
            addBullet(doc, "TC-RG-003 | Đăng ký thất bại khi tất cả trường trống");
            addBullet(doc, "TC-RG-004 | Đăng ký thất bại với email không hợp lệ");
            addBullet(doc, "TC-RG-005 | Đăng ký thất bại với password quá ngắn");
            addBullet(doc, "TC-RG-006 | Đăng ký thất bại khi confirm password không khớp");
            addBullet(doc, "TC-RG-007 | Đăng ký thất bại khi email đã tồn tại");
            addBullet(doc, "TC-RG-008 | Chuyển hướng về trang Login");
            addBlankLine(doc);

            // ══ E. IMPLEMENTATION & TÍNH NĂNG ════════════════════════════════
            addHeading(doc, "E. IMPLEMENTATION & CÁC TÍNH NĂNG DỰ ÁN", 1);
            addSeparator(doc);
            addBullet(doc, "Page Object Model (POM): Tách biệt UI và logic test.");
            addBullet(doc, "Auto Screenshot: BaseTest.java tự động chụp màn hình sau mỗi test case (Pass/Fail).");
            addBullet(doc, "Excel Report: ExcelGenerator.java → sinh System_Test_Cases.xlsx.");
            addBullet(doc, "Word Report: WordReportGenerator.java → sinh Bao_Cao_Kiem_Thu_BT6.docx.");
            addBullet(doc, "CI/CD: GitHub Actions .github/workflows/maven.yml.");
            addBlankLine(doc);

            // ══ F. BUILD & RUN ════════════════════════════════════════════════
            addHeading(doc, "F. BUILD & RUN TEST", 1);
            addSeparator(doc);
            addParagraph(doc, "Chạy toàn bộ Test Suite bằng lệnh Maven:");
            addCode(doc, "mvn test");
            addBlankLine(doc);
            addParagraph(doc, "Code tự động mở Chrome/Edge, điền thông tin, click và assert kết quả. " +
                "Terminal báo BUILD SUCCESS nếu tất cả test pass. " +
                "Ảnh minh chứng được lưu tự động vào thư mục Screenshots/.");
            addBlankLine(doc);

            // ══ G. HÌNH ẢNH MINH CHỨNG ═══════════════════════════════════════
            addHeading(doc, "G. HÌNH ẢNH MINH CHỨNG KIỂM THỬ", 1);
            addSeparator(doc);
            addParagraph(doc, "Danh sách hình ảnh minh chứng kiểm thử sinh tự động bằng Selenium WebDriver:");
            addBlankLine(doc);

            File folder = new File("Screenshots");
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        XWPFParagraph para = doc.createParagraph();
                        para.setAlignment(ParagraphAlignment.LEFT);
                        XWPFRun run = para.createRun();
                        String baseName = file.getName()
                            .replace(".png","").replace(".jpg","");
                        run.setText("Test Case: " + baseName);
                        run.setBold(true);
                        run.setFontSize(11);
                        run.setFontFamily("Times New Roman");
                        run.addBreak();

                        boolean isJpg = file.getName().toLowerCase().endsWith(".jpg");
                        int picType = isJpg ? XWPFDocument.PICTURE_TYPE_JPEG
                                            : XWPFDocument.PICTURE_TYPE_PNG;
                        try (FileInputStream is = new FileInputStream(file)) {
                            run.addPicture(is, picType, file.getName(),
                                Units.toEMU(450), Units.toEMU(250));
                        } catch (Exception e) {
                            run.setText("  [Lỗi chèn ảnh: " + e.getMessage() + "]");
                        }
                        run.addBreak();
                        run.addBreak();
                    }
                } else {
                    addParagraph(doc, "⚠ Không tìm thấy ảnh trong Screenshots/. Chạy `mvn test` trước.");
                }
            } else {
                addParagraph(doc, "⚠ Thư mục Screenshots/ chưa tồn tại. Chạy `mvn test` để tạo ảnh tự động.");
            }

            // ── Lưu file ──────────────────────────────────────────────────────
            try (FileOutputStream out = new FileOutputStream("Bao_Cao_Kiem_Thu_BT6.docx")) {
                doc.write(out);
            }
            System.out.println("✅ TẠO THÀNH CÔNG: Bao_Cao_Kiem_Thu_BT6.docx");
            System.out.println("Nội dung bao gồm: Thông tin dự án, Business Rules, Test Cases, Implementation và hình ảnh minh chứng.");

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi tạo file Word: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
