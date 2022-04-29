package edu.hitsz.application;

import edu.hitsz.aircraft.EasyFactory;
import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.EasyProFactory;

import java.util.Random;

public class EasyGame extends Game{
    private static final Random random = new Random();
    public EasyGame(boolean voice) {
        super(voice);
    }

    @Override
    public void levelChange()
    {
    }

    @Override
    public String selectEnemy() {
        int a = random.nextInt(100);
        if (a < 10) {
            return "elite";
        }
        else {
            return "mob";
        }
    }

    @Override
    public String  selectProp() {
        int a = random.nextInt(100);
        if (a <=10)
        {
            return "bomb";
        }
        else if (a<=50)
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
        enemyMaxNumber = 5;
        bossScoreThreshold = 2000;
        score_moe = 20;
        score_Elite = 60;
        score_Boss = 200;
        precent_Props = 70;
        health_Up = 200;
        HERO_HEALTH = 2000;
        HERO_SHOOT_NUM = 1;
        HERO_SHOOT_DAMAGE = 60;
        levelChoose = "Easy";
        enemyFactory = new EasyFactory();
        propsFactory = new EasyProFactory(this);
        BACKGROUND=ImageManager.BACKGROUND_IMAGE;
        AbstractProps.setHp(health_Up);
    }


}
