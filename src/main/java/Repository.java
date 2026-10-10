import java.util.ArrayList;
import java.util.List;

public class Repository<T> {
    private List<T> storage = new ArrayList<>();

    public void add(T entry){
        storage.add(entry);
    }

    public List<T> findAll(){
        return storage;
    }

}
