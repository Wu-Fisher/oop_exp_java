package edu.hitsz.thread;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.LineShootStrategy;

public class shootThread extends Thread {
    private HeroAircraft heroAircraft;
    public shootThread(HeroAircraft heroAircraft) {
        super();
        this.heroAircraft = heroAircraft;
    }

    @Override
    public void run() {
        int n=4;
        int i=0;
        while (i<n) {
            try {
                i++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        heroAircraft.setStrategy(new LineShootStrategy());
    }
}
