package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SpreadShootStrategy implements AbstractShootStrategy {
    /**
     * @return List<BaseBullet>
     */
    @Override
    public List<BaseBullet> shoot(int x, int y, int speedX, int speedY, int shootSpeed, int shootNum, int power, int direction) {
        List<BaseBullet> res = new LinkedList<>();
        int mx = x;
        /*
          子弹射击方向 (向上发射：1，向下发射：-1)
         */
        int my =
                y + direction * 2;
        int mspeedX = 2;
        int mspeedY = speedY + direction * shootSpeed;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum * 2; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (direction == 1) {
                baseBullet = new EnemyBullet(mx + (i * 2 - shootNum + 1) * 10, my, mspeedX * (i - shootNum + 1) / (shootNum - 1), mspeedY, power);
            } else {
                baseBullet = new HeroBullet(mx + (i * 2 - shootNum + 1) * 10, my, mspeedX * (i - shootNum + 1) / (shootNum - 1), mspeedY, power);
            }
            res.add(baseBullet);
        }
            return res;

        }
    }
