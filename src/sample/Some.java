package sample;

import sample.Service.ToolContact;

/**
 * Created by Anton on 16.01.2018.
 */
public class Some {
    public void main(String[] args) {
        System.out.println(new ToolContact().startAngle(20, 0.5F, 0.05F, 33, 2));
        System.out.println(new ToolContact().cuttingPeriod(20, 0.5F, 0.05F, 33, 2));
    }
}
