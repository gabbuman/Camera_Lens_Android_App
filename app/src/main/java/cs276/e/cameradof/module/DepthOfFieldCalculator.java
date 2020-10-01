package cs276.e.cameradof.module;
/**
 * DepthOfFieldCalculator calculates hyper focal distance,
 * near focal point, far focal point and depth of field
 * of a lens.
*/

public class DepthOfFieldCalculator {
    private static final double COC = 0.029;    // "Circle of Confusion" for a "Full Frame" camera
    private Lens l;
    private double distanceInMM;
    private double aperture;

    public DepthOfFieldCalculator(Lens l, double distance, double aperture) {
        checkIfValidArguments(l, distance, aperture);
        this.l = l;
        this.distanceInMM = distance;
        this.aperture = aperture;
    }

    public Lens getL() {
        return l;
    }

    public double getDistanceInMM() {
        return distanceInMM;
    }

    public double getAperture() {
        return aperture;
    }

    public double hyperFocalDistanceInM(){
        checkIfValidArguments(l, distanceInMM, aperture);

        return (l.getFocalLength() * l.getFocalLength()) / (1000 * aperture * COC);
    }

    public double nearFocalPointInM(){
        checkIfValidArguments(l, distanceInMM, aperture);

        double nearFocalPoint = (1000 * hyperFocalDistanceInM() * distanceInMM) /
                (1000 * hyperFocalDistanceInM() + (distanceInMM - l.getFocalLength()));
        return nearFocalPoint / 1000;
    }

    public double farFocalPointInM(){
        checkIfValidArguments(l, distanceInMM, aperture);
        if(distanceInMM > (1000 * hyperFocalDistanceInM())){
            return Double.POSITIVE_INFINITY;
        }

        double farFocalPoint =  (1000 * hyperFocalDistanceInM() * distanceInMM) /
                (1000 * hyperFocalDistanceInM() - (distanceInMM - l.getFocalLength()));
        return farFocalPoint / 1000;
    }

    public double depthOfFieldInM(){
        checkIfValidArguments(l, distanceInMM, aperture);

        return farFocalPointInM() - nearFocalPointInM();
    }

    private void checkIfValidArguments(Lens l, double distance, double aperture){
        if(l == null){
            throw new IllegalArgumentException("Lens cannot be null!");
        }
        if(distance < 0){
            throw new IllegalArgumentException("Distance cannot be negative!");
        }
        if(aperture < l.getMaximumAperture() || aperture > 22){
            throw new IllegalArgumentException("Aperture chosen is incompatible with selected lens");
        }
    }

}
