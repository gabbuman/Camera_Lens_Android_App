package cs276.e.cameradof.module;

/**
 * Class to represent a camera lens and its attributes. Attributes
 * included are lens' make, its maximum aperture and its focal length!
 */
public class Lens {
    private String make;
    private double maximumAperture;
    private double focalLength;
    private int iconID;


    public Lens(String make, double maximumAperture, double focalLength, int iconID) {
        checkIfValidArguments(make, maximumAperture, focalLength);
        this.make = make;
        this.maximumAperture = maximumAperture;
        this.focalLength = focalLength;
        this.iconID = iconID;
    }

    public int getIconID() {
        return iconID;
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

    public void setMake(String make) {
        this.make = make;
    }

    public void setMaximumAperture(double maximumAperture) {
        this.maximumAperture = maximumAperture;
    }

    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
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
