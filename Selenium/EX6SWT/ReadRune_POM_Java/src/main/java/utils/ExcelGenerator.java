package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {
    public static void main(String[] args) {
        String[] columns = {"Test Case ID", "Module", "Test Title/Business Rule", "Preconditions", "Test Steps", "Expected Result", "Status", "Notes"};
        
        Object[][] testData = {
            {"TC-LG-001", "Login", "Trang Login load thành công", "Mở trình duyệt, truy cập trang Login", "Kiểm tra sự hiển thị của username, password, nút login", "Hiển thị đầy đủ form login", "PASS", "Tự động trên TestNG"},
            {"TC-LG-002", "Login", "Đăng nhập thành công với thông tin hợp lệ", "Tài khoản có sẵn", "Nhập email và pass hợp lệ -> Bấm Login", "Chuyển hướng thành công sang Dashboard", "PASS", "Tự động"},
            {"TC-LG-003", "Login", "Đăng nhập thất bại khi email trống", "Vào trang Login", "Để trống báo email -> Bấm Login", "Cảnh báo thiếu thông tin validation", "PASS", "Tự động"},
            {"TC-LG-004", "Login", "Đăng nhập thất bại khi password trống", "Vào trang Login", "Nhập email, để trống pass -> Bấm Login", "Cảnh báo thiếu thông tin validation", "PASS", "Tự động"},
            {"TC-LG-005", "Login", "Đăng nhập thất bại với email sai", "Vào trang Login", "Nhập email sai định dạng/chưa tồn tại", "Hiện thông báo lỗi tài khoản không đúng", "PASS", "Tự động"},
            {"TC-LG-006", "Login", "Đăng nhập thất bại với password sai", "Vào trang Login", "Nhập đúng email, sai mật khẩu", "Hiện thông báo lỗi đăng nhập", "PASS", "Tự động"},
            {"TC-LG-007", "Login", "Đăng nhập thất bại khi cả 2 trường trống", "Vào trang Login", "Bấm Login mà không nhập gì", "Cảnh báo thiếu toàn bộ các thẻ input", "PASS", "Tự động"},
            {"TC-LG-008", "Login", "Chuyển sang trang Đăng ký", "Vào trang Login", "Bấm link chuyển hướng 'Sign Up'", "Chuyển trang thành công", "FAIL", "XPath timeout"},
            {"TC-RG-001", "Register", "Trang Đăng ký load thành công", "Vào trang Register", "Load toàn bộ page elements", "Tải trang đầy đủ", "PASS", "Tự động"},
            {"TC-RG-002", "Register", "Đăng ký thành công với thông tin hợp lệ", "Randomize new email", "Điền tất cả thông tin hợp lệ -> Register", "Chuyển hướng hoặc báo thành công", "PASS", "Tự động"},
            {"TC-RG-003", "Register", "Đăng ký thất bại khi tất cả trường trống", "Vào trang Register", "Bấm Register ngay", "Bắt lỗi thông tin tất cả form", "PASS", "Tự động"},
            {"TC-RG-004", "Register", "Đăng ký thất bại với email không hợp lệ", "Vào trang Register", "Nhập email ko có @ hay .com", "Báo lỗi email format", "PASS", "Tự động"},
            {"TC-RG-005", "Register", "Đăng ký thất bại với password quá ngắn", "Vào trang Register", "Nhập password dưới độ dài chuẩn", "Hiển thị lỗi password strength", "PASS", "Tự động"},
            {"TC-RG-006", "Register", "Đăng ký thất bại khi confirm password không khớp", "Vào trang Register", "Pass và Confirm pass khác nhau", "Hiển thị thông báo mật khẩu không khớp", "PASS", "Tự động"},
            {"TC-RG-007", "Register", "Đăng ký thất bại khi email đã tồn tại", "Có tài khoản đã được đăng ký", "Nhập lại chính Email cũ đã đăng ký", "Lỗi trùng Email báo do Duplicate Database", "PASS", "Tự động"},
            {"TC-RG-008", "Register", "Chuyển về trang Login", "Vào trang Register", "Bấm vào Nút chuyển nhanh 'Login'", "Chuyển từ Register về Login an toàn", "FAIL", "XPath timeout"}
        };

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("System Tests");
            
            // Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                
                // Optional: basic styling (bold header)
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Data Rows
            int rowNum = 1;
            for (Object[] rowData : testData) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < rowData.length; i++) {
                    row.createCell(i).setCellValue(rowData[i].toString());
                }
            }

            // Write to file
            String filePath = "D:\\EX6SWT\\ReadRune_POM_Java\\System_Test_Cases.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel generated successfully at: " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
