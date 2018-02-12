package sample.Service;


public class ForceCalculation {

    private double kn;
    private double kt;
    private double maxForceFy;

    public double forceCalc(double kS, int beta, double feedPerTooth, double start, double ap){
        kn = kS * Math.cos(Math.toRadians(beta));
        kt = kS * Math.sin(Math.toRadians(beta));
        double h = feedPerTooth * Math.sin(Math.toRadians(start));
        double fT = kt*ap*h;
        double fN = kn*ap*h;

        maxForceFy = -fN*Math.cos(Math.toRadians(start))+fT*Math.sin(Math.toRadians(start));

       return maxForceFy;
    }
}
