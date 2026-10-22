import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionFileHandlerTest {

    private TransactionFileHandler fileHandler;

    @BeforeEach
    public void setUp() {
        fileHandler = new TransactionFileHandler();
    }

    @Test
    public void saveThenLoadShouldReturnSameTransactions(@TempDir Path tempDir) {
        Path file = tempDir.resolve("transactions.csv");
        List<Transaction> transactions = List.of(
                new Transaction(LocalDate.of(2026, 10, 19), 100.0, "salary", TransactionType.INCOME),
                new Transaction(LocalDate.of(2026, 10, 20), 50.5, "food", TransactionType.EXPENSE));

        fileHandler.save(transactions, file);
        List<Transaction> loaded = fileHandler.load(file);

        assertEquals(transactions, loaded);
    }

    @Test
    public void loadShouldReturnEmptyListWhenFileIsMissing(@TempDir Path tempDir) {
        List<Transaction> loaded = fileHandler.load(tempDir.resolve("missing.csv"));
        assertTrue(loaded.isEmpty());
    }

    @Test
    public void loadShouldThrowWithTooFewParts(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("broken.csv");
        Files.writeString(file, "2026-10-19,100.0,salary\n");

        assertThrows(FileFormatException.class, () -> fileHandler.load(file));
    }

    @Test
    public void loadExceptionShouldMentionLineNumber(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("broken.csv");
        Files.writeString(file, "2026-10-19,100.0,salary,INCOME\nbroken line\n");

        FileFormatException e = assertThrows(FileFormatException.class, () -> fileHandler.load(file));
        assertTrue(e.getMessage().contains("line 2"));
    }
}
