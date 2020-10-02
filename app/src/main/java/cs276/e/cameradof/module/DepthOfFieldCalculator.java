package cs276.e.cameradof.module;
/**
 * DepthOfFieldCalculator calculates hyper focal distance,
 * near focal point, far focal point and depth of field
 * of a lens.
*/

public class DepthOfFieldCalculator {

    private double COC;    // "Circle of Confusion" for a "Full Frame" camera
    private Lens l;
    private double distanceInMM;
    private double aperture;

    public DepthOfFieldCalculator(Lens l, double distance, double aperture, double COC) {
        checkIfValidArguments(l, distance, aperture, COC);
        this.l = l;
        this.distanceInMM = distance;
        this.aperture = aperture;
        this.COC = COC;
    }

    public double getCOC() {
        return COC;
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
        checkIfValidArguments(l, distanceInMM, aperture, COC);

        return (l.getFocalLength() * l.getFocalLength()) / (1000 * aperture * COC);
    }

    public double nearFocalPointInM(){
        checkIfValidArguments(l, distanceInMM, aperture, COC);

        double nearFocalPoint = (1000 * hyperFocalDistanceInM() * distanceInMM) /
                (1000 * hyperFocalDistanceInM() + (distanceInMM - l.getFocalLength()));
        return nearFocalPoint / 1000;
    }

    public double farFocalPointInM(){
        checkIfValidArguments(l, distanceInMM, aperture, COC);
        if(distanceInMM > (1000 * hyperFocalDistanceInM())){
            return Double.POSITIVE_INFINITY;
        }

        double farFocalPoint =  (1000 * hyperFocalDistanceInM() * distanceInMM) /
                (1000 * hyperFocalDistanceInM() - (distanceInMM - l.getFocalLength()));
        return farFocalPoint / 1000;
    }

    public double depthOfFieldInM(){
        checkIfValidArguments(l, distanceInMM, aperture, COC);

        return farFocalPointInM() - nearFocalPointInM();
    }

    private void checkIfValidArguments(Lens l, double distance, double aperture, double COC){
        if(l == null){
            throw new IllegalArgumentException("Lens cannot be null!");
        }
        if(distance < 0){
            throw new IllegalArgumentException("Distance cannot be negative!");
        }
        if(aperture < l.getMaximumAperture() || aperture > 22){
            throw new IllegalArgumentException("Aperture chosen is incompatible with selected lens");
        }
        if(COC <= 0){
            throw new IllegalArgumentException("COC value cannot be less than or 0");
        }
    }

}
