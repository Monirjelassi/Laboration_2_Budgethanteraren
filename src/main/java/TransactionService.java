import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionService {

    private Repository<Transaction> repo = new Repository<>();
    private TransactionFileHandler fileHandler = new TransactionFileHandler();


    public void addTransaction(double amount, String category, String typeInput) {
        TransactionType type;
        if (typeInput.equals("1")) {
            type = TransactionType.INCOME;
        } else if (typeInput.equals("2")) {
            type = TransactionType.EXPENSE;
        } else {
            IO.println("Invalid type, transaction not added.");
            return;
        }
        Transaction transaction = new Transaction(LocalDate.now(), amount, category, type);
        repo.add(transaction);


    }

    public void showTransaction() {
        repo.findAll().forEach(IO::println);
    }

    public void showSummary() {
        double balance = repo.findAll().stream()
                .mapToDouble(t -> t.type() == TransactionType.INCOME ? t.amount() : -t.amount())
                .sum();

        Map<String, Double> perCategory = repo.findAll().stream()
                .collect(Collectors.groupingBy(
                        Transaction::category,
                        Collectors.summingDouble(t -> t.type() == TransactionType.INCOME ? t.amount() : -t.amount())));
        IO.println("Balance: " + balance);
        perCategory.forEach((category, sum) -> IO.println(category + ": " + sum));

    }

    public void filterTransactionsByType() {
        String input = IO.readln("Press 1 to filter by income\nPress 2 to filter by expense\nChoice: ");
        TransactionType type;
        if (input.equals("1")) {
            type = TransactionType.INCOME;
        } else if (input.equals("2")) {
            type = TransactionType.EXPENSE;
        } else {
            IO.println("Invalid type.");
            return;
        }
        repo.findWhere(t -> t.type() == type).forEach(IO::println);
    }

    public void saveToFile() {
        fileHandler.save(repo.findAll());
    }




}
