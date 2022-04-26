package edu.hitsz.aircraft;

public abstract class EnemyFactory {
    public AbstractAircraft callEnemy(String type) {
        AbstractAircraft enemy = createEnemy(type);
//        System.out.println("生成敌机：" + enemy.getClass().getSimpleName());
        return enemy;
    }
    public AbstractAircraft callEnemy(String type, double damageTimes, double healthTimes) {
        AbstractAircraft enemy = createEnemy(type,damageTimes,healthTimes);
        System.out.println("生成敌机：" + enemy.getClass().getSimpleName());
        return enemy;
    }

    abstract AbstractAircraft createEnemy(String type);
    abstract AbstractAircraft createEnemy(String type, double damageTimes, double healthTimes);
}
