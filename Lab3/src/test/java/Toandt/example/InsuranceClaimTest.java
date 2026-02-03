package Toandt.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InsuranceClaimTest {

    @Test
    void testClaimInitialization() {
        // Kiểm tra khởi tạo
        InsuranceClaim claim = new InsuranceClaim("C001", 1000.0);
        assertEquals("Pending", claim.getClaimStatus(), "Trạng thái ban đầu phải là Pending");
    }

    @Test
    void testProcessClaimSuccess() {
        // Kiểm tra xử lý yêu cầu thành công
        InsuranceClaim claim = new InsuranceClaim("C001", 1000.0);
        boolean result = claim.processClaim(InsuranceClaim.STATUS_APPROVED);

        assertTrue(result, "Yêu cầu phải được xử lý thành công từ trạng thái Pending");
        assertEquals("Approved", claim.getClaimStatus());
    }

    @Test
    void testProcessClaimFailure() {
        // Kiểm tra xử lý yêu cầu thất bại (khi trạng thái không phải Pending)
        InsuranceClaim claim = new InsuranceClaim("C001", 1000.0);
        claim.processClaim("Approved"); // Thay đổi lần 1

        // Thử thay đổi lần nữa (Approved -> Rejected) - Phải thất bại theo logic
        boolean result = claim.processClaim("Rejected");

        assertFalse(result, "Không thể xử lý yêu cầu nếu trạng thái không phải là Pending");
        assertEquals("Approved", claim.getClaimStatus());
    }

    @Test
    void testCalculatePayoutApproved() {
        // Kiểm tra tính tiền chi trả khi đã duyệt
        InsuranceClaim claim = new InsuranceClaim("C001", 1000.0);
        claim.processClaim("Approved");

        assertEquals(850.0, claim.calculatePayout(), 0.01, "Tiền chi trả phải bằng 85% số tiền gốc");
    }

    @Test
    void testCalculatePayoutPending() {
        // Kiểm tra tính tiền chi trả khi chưa duyệt
        InsuranceClaim claim = new InsuranceClaim("C001", 1000.0);
        assertEquals(0.0, claim.calculatePayout(), "Tiền chi trả phải bằng 0 nếu chưa được duyệt (Approved)");
    }

    @Test
    void testUpdateClaimAmountNegative() {
        // Kiểm tra lỗi khi nhập số tiền âm
        InsuranceClaim claim = new InsuranceClaim("C001", 1000.0);
        assertThrows(IllegalArgumentException.class, () -> {
            claim.updateClaimAmount(-500.0);
        });
    }
}
