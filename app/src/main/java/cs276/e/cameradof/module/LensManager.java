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
            populateInstance();
        }
        return instance;
    }

    private static void populateInstance() {
        instance.add(new Lens("Canon", 1.8, 50));
        instance.add(new Lens("Tamron", 2.8, 90));
        instance.add(new Lens("Sigma", 2.8, 200));
        instance.add(new Lens("Nikon", 4, 200));
        instance.add(new Lens("ElCheepo", 12, 24));
        instance.add(new Lens("Leica", 5.6, 1600));
        instance.add(new Lens("TheWide", 1.0, 16));
        instance.add(new Lens("IWish", 1.0, 200));
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
