public class Menu {

    public void startMenu() {

        boolean running = true;

        while (running) {
            showmenu();
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
                    break;
                case 2:
                    IO.println("Show all transactions");
                    break;
                case 3:
                    IO.println("Show balance and summary per category");
                    break;
                case 4:
                    IO.println("Filter transactions");
                    break;
                case 5:
                    IO.println("Save to file");
                    break;
                case 6:
                    IO.println("Exit");
                    running = false;
                    break;
                default:
                    IO.println("Invalid choice");
            }
        }
    }

    public void showmenu(){
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
