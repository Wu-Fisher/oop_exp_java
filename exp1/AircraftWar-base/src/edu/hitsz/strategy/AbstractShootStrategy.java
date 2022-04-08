package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author Wufisher
 */
public interface AbstractShootStrategy {
    /**
     * @return 返回一个子弹列表
     * 方法用于更新飞机设计的策略
     */


    List<BaseBullet> shoot(int x, int y,int speedX,int speedY,int shootSpeed,int shootNum,int power,int direction);
}
