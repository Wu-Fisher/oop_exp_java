package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.AbstractShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    // public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,
    // int shootnum, int power) {
    // super(locationX, locationY, speedX, speedY, hp);
    // this.shootNum = shootnum;
    // this.power = power;
    // }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
    private AbstractShootStrategy shootStrategy;
    @Override
    public void setStrategy
            (AbstractShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    @Override
    public void update(Game mg)
    {
//        this.decreaseHp(this.getHp());
        this.vanish();
        mg.score+=mg.score_moe;
    }

}
