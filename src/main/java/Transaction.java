import java.time.LocalDate;

public record Transaction(LocalDate date, double amount, String category, TransactionType type) {
}
