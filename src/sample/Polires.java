package sample;


public class Polires {
    private static int pointsNumber = 1000;//2850
    private int harmonicsNumber = 16;
    private double rotationalSpeed = 0.004;
    private double rotationalPeriod = 2 * Math.PI / rotationalSpeed;
    private double[] a = new double[pointsNumber];
    private double[] b = new double[pointsNumber];
    public static double[] f = new double[pointsNumber];
    public static int[] omega = new int[pointsNumber];
    public static double[] wwSpeed = new double[pointsNumber];
    private double[] ww = new double[pointsNumber];
    double a0;


    public double[] getF() {
        return f;
    }

    public double[] getWwSpeed() {
        return wwSpeed;
    }

    public void foruerCoefCalc(double fMax, int teethNumber, double contactAngle) {


        a0 = teethNumber * rotationalSpeed * contactAngle * rotationalPeriod * fMax / 4 / Math.PI;
        for (int i = 1; i <= harmonicsNumber; i++) {

            a[i] = Math.cos((i) * teethNumber * rotationalSpeed * contactAngle * rotationalPeriod) - 1;
            a[i] = a[i] / ((i) * teethNumber * rotationalSpeed * contactAngle * rotationalPeriod);
            a[i] = a[i] + Math.sin((i) * teethNumber * rotationalSpeed * contactAngle * rotationalPeriod);
            a[i] = a[i] * fMax / (i) / Math.PI;

            b[i] = Math.cos((i) * teethNumber * rotationalSpeed * contactAngle * rotationalPeriod) -
                    Math.sin((i) * teethNumber * rotationalSpeed * contactAngle * rotationalPeriod) /
                            ((i) * teethNumber * rotationalSpeed * contactAngle * rotationalPeriod);
            b[i] = fMax * b[i] / i / Math.PI;

        }
    }

    public void poliresCalc(double natFreq, double dampRatio, double fMax, double stifness, int teethNumber) {

        double wmin = 0;
        double w0 = natFreq * 2 * Math.PI;
        double mass = stifness / Math.pow(w0, 2);
        double wmax = 1.01 * w0;
        double dw = (wmax - wmin) / (pointsNumber - 1);

        System.out.println("Max force " + fMax);

        wwSpeed[0] = 0;

        for (int i = 1; i <= pointsNumber; i++) {
            ww[i] = wmin + dw * (i-1);

            double sum = Math.pow(a0, 2) / Math.pow(w0, 4);
            for (int j = 0; j < harmonicsNumber; j++) {
                sum = sum + 0.5 * (a[j] * a[j] + b[j] * b[j]) /
                        ((w0 * w0 - j * j * teethNumber * teethNumber * ww[i] * ww[i]) *
                                (w0 * w0 - j * j * teethNumber * teethNumber * ww[i] * ww[i]) +
                                4.0 * dampRatio * 2 * Math.PI * dampRatio * 2 * Math.PI * j * j *
                                        teethNumber * teethNumber * ww[i] * ww[i]);

            }

            f[i] = Math.sqrt(sum) / (mass * 1000) * 1000000;
            wwSpeed[i] = ww[i] * 60 / 2 / Math.PI;
            System.out.println(i);
            if (wwSpeed[i] > 10000) break;


        }

    }
}
