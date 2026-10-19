import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileHandler {
    public void save(List<Transaction> transactions) {
        List<String> lines = new ArrayList<>();
        for (Transaction t : transactions) {
            lines.add(t.date() + "," + t.amount() + "," + t.category() + "," + t.type());
        }
        try {
            Files.write(Path.of("transactions.csv"), lines);
        } catch (IOException e) {
            IO.println("Unable to save transactions " + e.getMessage());
        }
    }
}
