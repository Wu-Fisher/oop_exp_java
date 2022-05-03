package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.AbstractShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft {

    /** 攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 2;

    /**
     * 子弹伤害
     */
    private int power = 10;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     * 
     * <=====按照人类视角应该是写反了吧
     * 
     */
    private int direction = 1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootnum, int power) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootnum;
        this.power = power;
    }
    private int DIST=0;

    @Override
    public void forward() {
        locationX += speedX;
        locationY += speedY;
        DIST+=speedX>0?speedX:-speedX;
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            // 横向超出边界后反向
            speedX = -speedX;
            DIST=0;
        }
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
        if(DIST>=Main.WINDOW_WIDTH/6) {
            speedX = -speedX;
            DIST=0;
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * 
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(baseBullet);
        }
        return res;
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
        mg.score+=mg.score_Elite;
    }
}
