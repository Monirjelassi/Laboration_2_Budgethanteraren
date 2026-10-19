import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class Menu {
    private Repository<Transaction> repo = new Repository<>();
    private TransactionFileHandler fileHandler = new TransactionFileHandler();

    public void startMenu() {

        boolean running = true;

        while (running) {
            showMenu();
            int choice;
            try {
                choice = Integer.parseInt(IO.readln("Choose an option: "));
            } catch (NumberFormatException e) {
                IO.println("Please enter a number! (1-6)");
                continue;
            }
            switch (choice) {
                case 1:
                    IO.println("Add transaction");
                    addTransaction();
                    break;
                case 2:
                    IO.println("Show all transactions");
                    showTransaction();
                    break;
                case 3:
                    IO.println("Show balance and summary per category");
                    showSummary();
                    break;
                case 4:
                    IO.println("Filter transactions");
                    filterTransactionsByType();
                    break;
                case 5:
                    IO.println("Save to file");
                    fileHandler.save(repo.findAll());
                    break;
                case 6:
                    IO.println("Shutting down...");
                    running = false;
                    break;
                default:
                    IO.println("Invalid choice");
            }
        }
    }

    public void showMenu() {
        IO.println("Budget Manager");
        IO.println("==============");
        IO.println("1. Add transaction");
        IO.println("2. Show all transactions");
        IO.println("3. Show balance and summary per category");
        IO.println("4. Filter transactions");
        IO.println("5. Save to file");
        IO.println("6. Exit");
    }

    private void addTransaction() {
        double amount = Double.parseDouble(IO.readln("Enter an amount to add: "));
        String category = IO.readln("Choose category: ").toLowerCase();
        String typeInput = IO.readln("Choose Transaction type: 1 = Income, 2 = Expense: ");
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

    private void showTransaction() {
        repo.findAll().forEach(System.out::println);
    }

    private void showSummary() {
        double balance = repo.findAll().stream()
                .mapToDouble(t -> t.type() == TransactionType.INCOME ? t.amount() : -t.amount())
                .sum();

        Map<String, Double> perCategory = repo.findAll().stream()
                .collect(Collectors.groupingBy(
                        Transaction::category,
                        Collectors.summingDouble(t -> t.type() == TransactionType.INCOME ? t.amount() : -t.amount())));
        IO.println("Balance: " + balance);
        perCategory.forEach((category, sum) -> IO.println(category + ": " + sum));


        ;

    }

    private void filterTransactionsByType() {
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
    
}


//LocalDate date, double amount, String category, TransactionType type