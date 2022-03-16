package edu.hitsz.props;

import java.util.Random;

public class PropsSelector {
    static String[] propsTypes = { "blood", "bomb", "bullet" };
    static final Random random = new Random();

    public static String selectoString_easy() {
        return propsTypes[(int) (random.nextInt(propsTypes.length))];
    }

}
