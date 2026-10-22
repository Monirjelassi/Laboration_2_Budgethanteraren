import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private Repository<String> repository;

    @BeforeEach
    public void setUp() {
        repository = new Repository<>();
    }

    @Test
    public void findAllShouldReturnEmptyListWhenNothingAdded() {
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void addShouldStoreEntry() {
        repository.add("apple");

        assertEquals(List.of("apple"), repository.findAll());
    }

    @Test
    public void findWhereShouldReturnOnlyMatchingEntries() {
        repository.add("apple");
        repository.add("banana");
        repository.add("avocado");

        List<String> result = repository.findWhere(s -> s.startsWith("a"));

        assertEquals(List.of("apple", "avocado"), result);
    }

    @Test
    public void findWhereShouldReturnEmptyListWhenNothingMatches() {
        repository.add("apple");

        assertTrue(repository.findWhere(s -> s.startsWith("z")).isEmpty());
    }
}
