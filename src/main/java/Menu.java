public class Menu {

    public void startMenu() {

        boolean running = true;

        while (running) {
            int choice;
            try {
                choice = Integer.parseInt(IO.readln("Choose an option: "));
            } catch (NumberFormatException e) {
                IO.println("Please enter a number! (1-6)");
                continue;
            }
            switch (choice) {
                case 1:
                    IO.println("Add transaktion");
                    break;
                case 2:
                    IO.println("Show all transaktions");
                    break;
                case 3:
                    IO.println("Show balance and summary per category");
                    break;
                case 4:
                    IO.println("Filter transaktions");
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
}
