package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.strategy.LineShootStrategy;
import edu.hitsz.strategy.SpreadShootStrategy;

import java.util.concurrent.ExecutorService;

public class BulletProps extends AbstractProps {
    Game mg;
    HeroAircraft heroAircraft;
    private ExecutorService shootExecutor;
    public BulletProps(int locationX, int locationY, int speedX, int speedY, Game game) {
        super(locationX, locationY, speedX, speedY);
        this.mg=game;
        this.heroAircraft=mg.getHeroAircraft();
        this.shootExecutor=mg.getShootExecutor();

    }

    @Override
    public void effectCrash() {
        System.out.println("FireSupplyactive!");
        Fire();


    }
    public void Fire(){
        // lamdba表达式
        Runnable runnable = ()->{
            heroAircraft.setStrategy(new SpreadShootStrategy());

            try {
                // 这里后续可以设置每个难度持续时间
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heroAircraft.setStrategy(new LineShootStrategy());
        };
        shootExecutor.submit(runnable);
    }

}
