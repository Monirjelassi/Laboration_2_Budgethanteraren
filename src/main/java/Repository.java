import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {
    private List<T> storage = new ArrayList<>();

    public void add(T entry){
        storage.add(entry);
    }

    public List<T> findAll(){
        return storage;
    }
    public List<T> findWhere(Predicate<T> condition) {
        return storage.stream().filter(condition).toList();
    }



}
