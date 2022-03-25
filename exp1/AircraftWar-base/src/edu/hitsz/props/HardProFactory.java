package edu.hitsz.props;

public class HardProFactory extends PropsFactory {
    @Override
    AbstractProps createProps(String type, int x, int y) {
        AbstractProps props = null;
        if (type.equals("bomb")) {
            props = new BombProps(x, y, 0, 2);

        } else if (type.equals("bullet")) {
            props = new BulletProps(x, y, 0, 2);
        } else if (type.equals("blood"))
            props = new BloodProps(x, y, 0, 2);

        return props;
    }

}