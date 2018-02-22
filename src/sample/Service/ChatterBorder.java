package sample.Service;

public class ChatterBorder {
    public double chatterBorderSpindleSpeed(double natFrequency, double contactAngle){
        double periodOfChatter = 1 / natFrequency;

        double speed = 60*360/periodOfChatter/contactAngle;

        return speed;
    }
}
