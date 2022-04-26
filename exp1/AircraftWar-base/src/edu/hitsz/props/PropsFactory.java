package edu.hitsz.props;

import edu.hitsz.application.Game;

public abstract class PropsFactory {
    public Game mg;
    public PropsFactory(Game game)
    {
        this.mg=game;
    }

    public AbstractProps callProps(String type, int x, int y) {
        AbstractProps props = createProps(type, x, y);
        System.out.println("生成道具：" + props.getClass().getSimpleName());
        return props;
    }

    abstract AbstractProps createProps(String type, int x, int y);
}
