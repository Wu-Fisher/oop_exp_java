package edu.hitsz.application;

import edu.hitsz.aircraft.HardFactory;
import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.HardProFactory;

import java.util.Random;

public class HardGame extends Game {
    int NOW_LEVEL=0;
    private static final Random random = new Random();
    public HardGame(boolean voice) {
        super(voice);
    }

    @Override
    public void levelChange() {
        if(this.score>=4000 && NOW_LEVEL==0)
        {
            NOW_LEVEL++;
            this.DAMAGE_TIMES+=0.1;
            this.HEALTH_TIMES+=0.1;
            System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
            System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
            musicPlayer.playLevelup();
        }
        else if (
                this.score>=8000 && NOW_LEVEL==1
        )
        {
            NOW_LEVEL++;
            this.DAMAGE_TIMES+=0.1;
            this.HEALTH_TIMES+=0.1;
            this.ENEMY_SHOOT_PRE-=1;

            System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
            System.out.println("射速UP！！！！");
            System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
            musicPlayer.playLevelup();
        }
        else if (
                this.score>=11000 && NOW_LEVEL==2
        )
        {
            NOW_LEVEL++;
            this.DAMAGE_TIMES+=0.05;
            this.HEALTH_TIMES+=0.05;
            this.ENEMY_SHOOT_PRE-=1;
            System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
            System.out.println("射速UP！！！！");
            System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
            musicPlayer.playLevelup();

        }
        else if(this.score>=15000 && NOW_LEVEL==3)
        {
            NOW_LEVEL++;
            this.DAMAGE_TIMES+=0.05;
            this.HEALTH_TIMES+=0.05;
            this.ENEMY_SHOOT_PRE-=1;
            this.HERO_SHOOT_PRE-=1;
            System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
            System.out.println("射速UP！！！！");
            System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
            System.out.println("到达最大难度");
            musicPlayer.playLevelup();
        }
    }

    @Override
    public String selectEnemy() {
        int a = random.nextInt(100);
        if (a < 30) {
            return "elite";
        }
        else {
            return "mob";
        }
    }

    @Override
    public String selectProp() {
        int a = random.nextInt(100);
        if (a < 40)
        {
            return "bomb";
        }
        else if (a<=80)
        {
            return "bullet";
        }
        else
        {
            return "blood";
        }
    }

    @Override
    public void initGame()
    {
        enemyMaxNumber = 8;
        bossScoreThreshold = 5000;
        score_moe = 150;
        score_Elite = 250;
        score_Boss = 800;
        precent_Props = 50;
        health_Up = 400;
        HERO_HEALTH = 2500;
        HERO_SHOOT_NUM = 3;
        HERO_SHOOT_DAMAGE = 80;
        levelChoose = "Hard";
        enemyFactory = new HardFactory();
        propsFactory = new HardProFactory(this);
        BACKGROUND=ImageManager.BACKGROUND_IMAGE_FIVE;
        cycleDuration=600;
        AbstractProps.setHp(health_Up);
        ENEMY_SHOOT_PRE=15;
        HERO_SHOOT_PRE=4;
    }

}
