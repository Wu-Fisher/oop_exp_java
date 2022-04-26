package edu.hitsz.props;

import edu.hitsz.application.Game;

public class EasyProFactory extends PropsFactory {

    public EasyProFactory(Game game) {
        super(game);
    }

    @Override
    AbstractProps createProps(String type, int x, int y) {
        AbstractProps props = null;
        if (type.equals("bomb")) {
            props = new BombProps(x, y, 0, 2,super.mg);

        } else if (type.equals("bullet")) {
            props = new BulletProps(x, y, 0, 2,super.mg);
        } else if (type.equals("blood"))
            props = new BloodProps(x, y, 0, 2);
        return props;
    }

}
