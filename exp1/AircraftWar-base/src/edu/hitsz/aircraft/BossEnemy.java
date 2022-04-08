package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.*;
import edu.hitsz.strategy.AbstractShootStrategy;
import edu.hitsz.strategy.SpreadShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Wufisher
 */
public class BossEnemy extends AbstractAircraft {

    /* 攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 3;
    private int shootSpeed=10;
    private int direction=1;
    private AbstractShootStrategy shootStrategy=new SpreadShootStrategy();
    /**
     * 子弹伤害
     */
    private int power = 20;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootnum, int power) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootnum;
        this.power = power;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
//        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
//            this.setSpeedX(-this.getSpeedX());
//        }
    }

    @Override
    /*
      通过射击产生子弹

      @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        if(shootStrategy==null) {
            List<BaseBullet> res = new LinkedList<>();
            int x = this.getLocationX();
        /*
          子弹射击方向 (向上发射：1，向下发射：-1)

          <=====按照人类视角应该是写反了吧

         */
            int direction = 1;
            int y = this.getLocationY() + direction * 2;
            int speedX = 0;
            int speedY = this.getSpeedY() + direction * 10;
            BaseBullet baseBullet;
            for (int i = 0; i < shootNum; i++) {
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
                res.add(baseBullet);
            }
            return res;
        }
        else
        {
            return shootStrategy.shoot(getLocationX(),getLocationY(),getSpeedX(),getSpeedY(),shootSpeed,shootNum,power,direction);
        }
    }

    @Override
    public void setStrategy
            (AbstractShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }
}
