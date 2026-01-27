package Toandt.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {

    private final AccountService service = new AccountService();

    @ParameterizedTest
    @CsvFileSource(
            resources = "/test-data.csv",
            numLinesToSkip = 1,
            nullValues = {""}
    )
    void testRegisterAccount(String username,
                             String password,
                             String email,
                             boolean expected) {

        boolean actual = service.registerAccount(username, password, email);

        assertEquals(expected, actual);
    }
}