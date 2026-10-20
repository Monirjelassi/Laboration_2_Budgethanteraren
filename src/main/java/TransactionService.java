import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {

    private final Repository<Transaction> repo = new Repository<>();
    private final TransactionFileHandler fileHandler = new TransactionFileHandler();

    public boolean addTransaction(double amount, String category, String typeInput) {
        TransactionType type = parseType(typeInput);
        if (type == null) {
            return false;
        }
        repo.add(new Transaction(LocalDate.now(), amount, category, type));
        return true;
    }

    public TransactionType parseType(String input) {
        if (input.equals("1")) {
            return TransactionType.INCOME;
        } else if (input.equals("2")) {
            return TransactionType.EXPENSE;
        }
        return null;
    }

    public List<Transaction> getTransactions() {
        return repo.findAll();
    }

    public double getBalance() {
        return repo.findAll().stream()
                .mapToDouble(this::signedAmount)
                .sum();
    }

    public Map<String, Double> getSummaryPerCategory() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(
                        Transaction::category,
                        Collectors.summingDouble(this::signedAmount)));
    }

    public List<Transaction> filterByType(TransactionType type) {
        return repo.findWhere(t -> t.type() == type);
    }

    public void loadFromFile() {
        for (Transaction t : fileHandler.load()) {
            repo.add(t);
        }
    }

    public void saveToFile() {
        fileHandler.save(repo.findAll());
    }

    private double signedAmount(Transaction t) {
        return t.type() == TransactionType.INCOME ? t.amount() : -t.amount();
    }
}
