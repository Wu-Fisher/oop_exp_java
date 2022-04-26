package edu.hitsz.props;

import java.util.Random;

public class PropsSelector {
    static String[] propsTypes = { "blood", "bomb", "bullet" };
    static final Random random = new Random();

    public static String selectoString() {
        int a = random.nextInt(100);
        if(a<=60)
        {
            return "blood";
        }
        else if(a<=90)
        {
            return "bullet";
        }
        else
        {
            return "bomb";
        }
    }

}
