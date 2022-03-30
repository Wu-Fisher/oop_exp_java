package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class LineShootStrategy implements AbstractShootStrategy {

    int shootNum = 1;
    int power = 10;
    int speed = 5;

    /**
     * @return List<BaseBullet>
     */
    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        return res;

    }

}
