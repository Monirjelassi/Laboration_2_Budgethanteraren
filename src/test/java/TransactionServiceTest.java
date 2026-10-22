import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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
    public void invalidAmountInputInAddTransactionShouldReturnInvalidTransactionException()
    {
        assertThrows(InvalidTransactionException.class, () -> transactionService.addTransaction(0, "salary", "1"));
    }

    @Test
    public void invalidCategoryInputInAddTransactionShouldReturnInvalidTransactionException()
    {
        assertThrows(InvalidTransactionException.class, () -> transactionService.addTransaction(100, " ", "1"));
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

    @Test
    public void getBalanceShouldReturnCorrectBalance() {
        transactionService.addTransaction(100, "salary", "2");
        transactionService.addTransaction(100, "salary", "1");
        transactionService.addTransaction(100, "salary", "2");
        assertEquals(-100, transactionService.getBalance());
    }

    @Test
    public void getSummaryPerCategoryShouldReturnCorrectAmount() {
        transactionService.addTransaction(100, "salary", "1");
        transactionService.addTransaction(100, "salary", "1");
        transactionService.addTransaction(100, "gift", "1");
        transactionService.addTransaction(1000, "rent", "2");
        Map<String, Double> summary = transactionService.getSummaryPerCategory();
        assertEquals(200.0, summary.get("salary"));
        assertEquals(100.0, summary.get("gift"));
        assertEquals(-1000.0, summary.get("rent"));
    }

    @Test
    public void filterByTypeShouldFilterByIncome() {
        transactionService.addTransaction(100, "salary", "1");
        transactionService.addTransaction(100, "rent", "2");
        transactionService.addTransaction(100, "salary", "1");

        List<Transaction> result = transactionService.filterByType(TransactionType.INCOME);
        assertEquals(2, result.size());


    }
    @Test
    public void filterByTypeShouldFilterByExpense() {
        transactionService.addTransaction(100, "salary", "1");
        transactionService.addTransaction(100, "rent", "2");
        transactionService.addTransaction(100, "salary", "1");

        List<Transaction> result = transactionService.filterByType(TransactionType.EXPENSE);
        assertEquals(1, result.size());

    }

    @Test
    public void getBalanceShouldReturnZeroWhenNoTransactions() {
        assertEquals(0.0, transactionService.getBalance());
    }

    @Test
    public void filterByTypeShouldReturnEmptyListWhenNoTransactions() {
        List<Transaction> result = transactionService.filterByType(TransactionType.INCOME);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldLoadSavedTransactionsFromFile(@TempDir Path tempDir) {
        Path file = tempDir.resolve("transactions.csv");
        TransactionService saver = new TransactionService(file);
        saver.addTransaction(100, "salary", "1");
        saver.addTransaction(50, "food", "2");
        saver.saveToFile();

        TransactionService loader = new TransactionService(file);
        loader.loadFromFile();

        assertEquals(2, loader.getTransactions().size());
        assertEquals(saver.getTransactions(), loader.getTransactions());
    }




}
