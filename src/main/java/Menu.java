public class Menu {
    private final TransactionService transactionService = new TransactionService();

    public void startMenu() {

        try {
            transactionService.loadFromFile();
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
                    addTransaction();
                    break;
                case 2:
                    IO.println("Show all transactions");
                    transactionService.getTransactions().forEach(IO::println);
                    break;
                case 3:
                    IO.println("Show balance and summary per category");
                    showSummary();
                    break;
                case 4:
                    IO.println("Filter transactions");
                    filterTransactions();
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

    private void addTransaction() {
        double amount = Double.parseDouble(IO.readln("Enter an amount to add: "));
        String category = IO.readln("Choose category: ").toLowerCase();
        String typeInput = IO.readln("Choose Transaction type: 1 = Income, 2 = Expense: ");
        if (!transactionService.addTransaction(amount, category, typeInput)) {
            IO.println("Invalid type, transaction not added.");
        }
    }

    private void showSummary() {
        IO.println("Balance: " + transactionService.getBalance());
        transactionService.getSummaryPerCategory()
                .forEach((category, sum) -> IO.println(category + ": " + sum));
    }

    private void filterTransactions() {
        String input = IO.readln("Press 1 to filter by income\nPress 2 to filter by expense\nChoice: ");
        TransactionType type = transactionService.parseType(input);
        if (type == null) {
            IO.println("Invalid type.");
            return;
        }
        transactionService.filterByType(type).forEach(IO::println);
    }
}
