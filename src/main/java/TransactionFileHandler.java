import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeParseException;

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

    public List<Transaction> load() {
        List<Transaction> result = new ArrayList<>();
        Path path = Path.of("transactions.csv");
        if (!Files.exists(path)) {
            return result;
        }
        try {
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                try {
                    LocalDate date = LocalDate.parse(parts[0]);
                    double amount = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    TransactionType type = TransactionType.valueOf(parts[3]);
                    result.add(new Transaction(date, amount, category, type));
                } catch (ArrayIndexOutOfBoundsException | DateTimeParseException | IllegalArgumentException e) {
                    throw new FileFormatException("Invalid data on line " + (i + 1) + ": " + lines.get(i));
                }
            }
        } catch (IOException e) {
            IO.println("Unable to load transactions " + e.getMessage());
        }
        return result;
    }
}
