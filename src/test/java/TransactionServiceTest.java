import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private TransactionService transactionService;


    @BeforeEach
    public void setUp() {
        transactionService = new TransactionService();
    }
    @Test
    public void shouldAddTransaction() {
        boolean added = transactionService.addTransaction(100, "salary", "1");

        assertTrue(added);
        assertEquals(1, transactionService.getTransactions().size());

        Transaction result = transactionService.getTransactions().get(0);
        assertEquals(100, result.amount());
        assertEquals("salary", result.category());
        assertEquals(TransactionType.INCOME, result.type());
    }

    @Test
    public void canHoldMoreThenOneTransaction() {
        transactionService.addTransaction(100, "salary", "1");
        transactionService.addTransaction(100, "salary", "2");
        transactionService.addTransaction(100, "salary", "2");
        assertEquals(3, transactionService.getTransactions().size());
    }

    @Test
    public void typeInputNullShouldNotAddTransaction() {
        boolean added = transactionService.addTransaction(100, "salary", "");
        assertEquals(0, transactionService.getTransactions().size());
        assertFalse(added);

    }
}
