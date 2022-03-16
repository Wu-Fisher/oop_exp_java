package edu.hitsz.props;

public class BloodProps extends AbstractProps {

    public BloodProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effectCrash() {
        System.out.println("GetRecover:" + Integer.toString(getHp()));
    }

}
