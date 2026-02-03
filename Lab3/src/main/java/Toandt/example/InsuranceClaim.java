package Toandt.example;

public class InsuranceClaim {
    // 1. Sửa Access Modifiers (Tính đóng gói) & Quy ước đặt tên (camelCase)
    private String claimId;
    private double amount;
    private String claimStatus;

    // Định nghĩa hằng số để tránh dùng "Magic Strings"
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_APPROVED = "Approved";
    public static final String STATUS_REJECTED = "Rejected";

    // Constructor
    public InsuranceClaim(String id, double claimAmount) {
        this.claimId = id;
        this.amount = claimAmount;
        this.claimStatus = STATUS_PENDING; // Trạng thái mặc định
    }

    // 2. Sửa tên hàm (processClaim) & 3. Sửa so sánh chuỗi
    public boolean processClaim(String statusUpdate) {
        // Dùng .equals() để so sánh nội dung chuỗi
        if (this.claimStatus.equals(STATUS_PENDING)) {
            this.claimStatus = statusUpdate;
            return true;
        }
        return false;
    }

    public double calculatePayout() {
        if (this.claimStatus.equals(STATUS_APPROVED)) {
            return amount * 0.85; // Chi trả 85%
        } else {
            return 0;
        }
    }

    // 6. Sửa Logic (Kiểm tra dữ liệu đầu vào - Input Validation)
    public void updateClaimAmount(double newAmount) {
        if (newAmount < 0) {
            throw new IllegalArgumentException("Số tiền yêu cầu bồi thường không được là số âm.");
        }
        this.amount = newAmount;
    }

    // Getters phục vụ cho việc test
    public String getClaimStatus() {
        return claimStatus;
    }

    public double getAmount() {
        return amount;
    }
}