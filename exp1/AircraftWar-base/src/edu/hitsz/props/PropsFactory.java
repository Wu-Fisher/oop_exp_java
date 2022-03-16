package edu.hitsz.props;

public abstract class PropsFactory {
    public AbstractProps callProps(String type, int x, int y) {
        AbstractProps props = createProps(type, x, y);
        System.out.println("生成道具：" + props.getClass().getSimpleName());
        return props;
    }

    abstract AbstractProps createProps(String type, int x, int y);
}
