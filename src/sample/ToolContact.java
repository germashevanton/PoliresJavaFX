package sample;


public class ToolContact {

    public double cuttingPeriod(float dMill, float ae, float ft, float helixAngle, float ap){
        double result =  (dMill / 2 * (Math.PI / 2 - Math.asin(1 - 2 * ae / dMill)) + 1 / 2 * ft +
                Math.tan(helixAngle * Math.PI / 180) * ap) / (Math.PI * dMill);
        return result;
    }

    public double startAngle(float dMill, float ae, float ft, float helixAngle, float ap){
        double result =  180 - 360 * (dMill / 2 * (Math.PI / 2 - Math.asin(1 - 2 * ae / dMill)) + 1 / 2 * ft +
                Math.tan(helixAngle * Math.PI / 180) * ap) / (Math.PI * dMill);
        return result;
    }
}

