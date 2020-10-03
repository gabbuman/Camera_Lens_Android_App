package cs276.e.cameradof.module;
/**
 * Manages a list of different lens with different attributes!
 * Functionality include adding a new lens to a list of lens
 * and create an iterable list of lens.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs276.e.cameradof.R;

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
        instance.add(new Lens("Canon", 1.8, 50, R.drawable.icon1));
        instance.add(new Lens("Tamron", 2.8, 90, R.drawable.icon2));
        instance.add(new Lens("Sigma", 2.8, 200, R.drawable.icon9));
        instance.add(new Lens("Nikon", 4, 200, R.drawable.icon4));
        instance.add(new Lens("ElCheepo", 12, 24, R.drawable.icon3));
        instance.add(new Lens("Leica", 5.6, 1600, R.drawable.icon6));
        instance.add(new Lens("TheWide", 1.0, 16, R.drawable.icon7));
        instance.add(new Lens("IWish", 1.0, 200, R.drawable.icon8));
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
