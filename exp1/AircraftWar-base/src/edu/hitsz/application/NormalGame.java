package edu.hitsz.application;

import edu.hitsz.aircraft.NormalFactory;
import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.NormalProFactory;

import java.util.Random;

public class NormalGame extends Game{
    int NOW_LEVEL=0;
    private static final Random random = new Random();
    public NormalGame(boolean voice) {
        super(voice);
    }

@Override
public  void levelChange()
{

    if(this.score>=2000 && NOW_LEVEL==0)
    {
        NOW_LEVEL++;
        this.DAMAGE_TIMES+=0.05;
        this.HEALTH_TIMES+=0.1;
        System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
        System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
    }
    else if (
        this.score>=3000 && NOW_LEVEL==1
    )
    {
        NOW_LEVEL++;
        this.DAMAGE_TIMES+=0.05;
        this.HEALTH_TIMES+=0.1;
        this.ENEMY_SHOOT_PRE-=1;
        this.HERO_SHOOT_PRE-=1;
        System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
        System.out.println("射速UP！！！！");
        System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
    }
    else if (
            this.score>=5000 && NOW_LEVEL==2
    )
    {
        NOW_LEVEL++;
        this.DAMAGE_TIMES+=0.05;
        this.HEALTH_TIMES+=0.05;
        System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
        System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);

    }
    else if(this.score>=7000 && NOW_LEVEL==3)
    {
        NOW_LEVEL++;
        this.DAMAGE_TIMES+=0.05;
        this.HEALTH_TIMES+=0.05;
        this.ENEMY_SHOOT_PRE-=1;
//        this.HERO_SHOOT_PRE-=1;
        System.out.println("难度提升！nowLevel:"+NOW_LEVEL);
        System.out.println("射速UP！！！！");
        System.out.println("伤害倍率："+DAMAGE_TIMES+" 生命倍率："+HEALTH_TIMES);
        System.out.println("到达最大难度");
    }

}

    @Override
    public String selectEnemy() {
        int a = random.nextInt(100);
        if (a < 20) {
            return "elite";
        }
        else {
            return "mob";
        }
    }

    @Override
    public String selectProp() {
        int a = random.nextInt(100);
        if (a <=10)
        {
            return "bomb";
        }
        else if (a<=40)
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
    enemyMaxNumber = 6;
    bossScoreThreshold = 3000;
    score_moe = 100;
    score_Elite = 200;
    score_Boss = 500;
    precent_Props = 40;
    health_Up = 200;
    HERO_HEALTH = 1200;
    HERO_SHOOT_NUM = 2;
    HERO_SHOOT_DAMAGE = 40;
    levelChoose = "Normal";
    enemyFactory = new NormalFactory();
    propsFactory = new NormalProFactory(this);
    BACKGROUND=ImageManager.BACKGROUND_IMAGE_TWO;
    cycleDuration=600;
    AbstractProps.setHp(health_Up);

}







}
