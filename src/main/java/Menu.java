
public class Menu {
    private Repository<Transaction> repo = new Repository<>();
    private final TransactionFileHandler fileHandler = new TransactionFileHandler();
    private final TransactionService transactionService = new TransactionService();


    public void startMenu() {

        try {
            for (Transaction t : fileHandler.load()) {
                repo.add(t);
            }
        } catch (FileFormatException e) {
            IO.println("Could not load file: " + e.getMessage());
        }


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
                    double amount = Double.parseDouble(IO.readln("Enter an amount to add: "));
                    String category = IO.readln("Choose category: ").toLowerCase();
                    String typeInput = IO.readln("Choose Transaction type: 1 = Income, 2 = Expense: ");
                    transactionService.addTransaction(amount, category, typeInput);
                    break;
                case 2:
                    IO.println("Show all transactions");
                    transactionService.showTransaction();
                    break;
                case 3:
                    IO.println("Show balance and summary per category");
                    transactionService.showSummary();
                    break;
                case 4:
                    IO.println("Filter transactions");
                    transactionService.filterTransactionsByType();
                    break;
                case 5:
                    IO.println("Saved to file");
                    transactionService.saveToFile();
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

}


//LocalDate date, double amount, String category, TransactionType type