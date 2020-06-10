package velvet.util.structs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

/*
Functions as a set for a list of items
The auto-set automatically extracts a key from each item, and uses this key to store the value
Essentially functions as a normal set, with an override on the key extractor
 */
public class AutoSet<K, V> {

    private static final Random random = new Random();

    private final Function<V, K> keyExtractor;
    private final ArrayList<K> keys;
    private final HashMap<K, V> map;

    public AutoSet(Function<V, K> keyExtractor) {
        this.keyExtractor = keyExtractor;
        keys = new ArrayList<>();
        map = new HashMap<>();
    }

    public void add(V value){
        K key = keyExtractor.apply(value);
        if(map.containsKey(key)){ return; }

        keys.add(key);
        map.put(key, value);
    }

    public Optional<V> get(K key){
        return Optional.ofNullable(map.get(key));
    }

    public V random(){ return map.get(keys.get(random.nextInt(keys.size()))); }

    public boolean contains(V value){
        return map.containsKey(keyExtractor.apply(value));
    }

    public int size(){ return map.size(); }

    public void clear(){
        keys.clear();
        map.clear();
    }

    public void remove(V value){
        K key = keyExtractor.apply(value);
        if(!map.containsKey(key)){ return; }

        keys.remove(key);
        map.remove(key);
    }

    public Stream<V> getValues(){
        return keys.stream().map(map::get);
    }
}
