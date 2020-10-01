package cs276.e.cameradof.module;

/**
 * Class to represent a camera lens and its attributes. Attributes
 * included are lens' make, its maximum aperture and its focal length!
 */
public class Lens {
    private String make;
    private double maximumAperture;
    private double focalLength;


    public Lens(String make, double maximumAperture, double focalLength) {
        checkIfValidArguments(make, maximumAperture, focalLength);
        this.make = make;
        this.maximumAperture = maximumAperture;
        this.focalLength = focalLength;
    }

    public String getMake() {
        return make;
    }

    public double getMaximumAperture() {
        return maximumAperture;
    }

    public double getFocalLength() {
        return focalLength;
    }

    private void checkIfValidArguments(String make, double maximumAperture, double focalLength){
        if(make == null || make.length() == 0){
            throw new IllegalArgumentException("Lens make cannot be empty");
        }
        if(maximumAperture <= 0){
            throw new IllegalArgumentException("Maximum aperture cannot be <= 0!");
        }
        if(focalLength <= 0){
            throw new IllegalArgumentException("Focal Length cannot be <=0");
        }
    }

}
