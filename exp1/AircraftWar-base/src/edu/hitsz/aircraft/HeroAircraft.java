package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * 
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /** 攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    private int shootSpeed = 8;

    private static HeroAircraft myHero;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    public HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootnum, int power) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = shootnum;
        this.power = power;
    }

    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp) {
        synchronized (HeroAircraft.class) {
            if (myHero == null) {
                myHero = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
            }
        }
        return myHero;
    }

    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp, int shootnum,
            int power) {
        synchronized (HeroAircraft.class) {
            if (myHero == null) {
                myHero = new HeroAircraft(locationX, locationY, speedX, speedY, hp, shootnum, power);
            }
        }
        return myHero;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /*
      通过射击产生子弹

      @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        /*
          子弹射击方向 (向上发射：1，向下发射：-1)
         */
        int direction = -1;
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * shootSpeed;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(baseBullet);
        }
        return res;
    }

}
