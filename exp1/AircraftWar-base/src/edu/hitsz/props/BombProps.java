package edu.hitsz.props;

public class BombProps extends AbstractProps {
    public BombProps(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void effectCrash() {
        System.out.println("BombSupply active!");
    }
}
