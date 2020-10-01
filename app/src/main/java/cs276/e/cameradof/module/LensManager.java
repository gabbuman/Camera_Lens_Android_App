package cs276.e.cameradof.module;
/**
 * Manages a list of different lens with different attributes!
 * Functionality include adding a new lens to a list of lens
 * and create an iterable list of lens.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Singleton Class
public class LensManager implements Iterable<Lens> {
    private List<Lens> lens = new ArrayList<>();

    private static LensManager instance;
    public static LensManager getInstance(){
        if(instance == null){
            instance = new LensManager();
        }
        return instance;
    }

    private LensManager() {
        // Private constructor, this is Singleton Class
    }

    public void add(Lens lensToAdd){
        lens.add(lensToAdd);
    }

    @Override
    public Iterator<Lens> iterator() {
        return lens.iterator();
    }

    public Lens getListAtThisIterator (int i){
        return lens.get(i);
    }
}
