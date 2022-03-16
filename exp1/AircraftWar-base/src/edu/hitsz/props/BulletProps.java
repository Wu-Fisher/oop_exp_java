package edu.hitsz.props;

public class BulletProps extends AbstractProps {
    public BulletProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }

    @Override
    public void effectCrash() {
        System.out.println("FireSupplyactive!");
    }

}
