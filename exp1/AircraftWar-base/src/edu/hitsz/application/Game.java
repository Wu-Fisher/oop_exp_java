package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.props.*;
import edu.hitsz.basic.AbstractFlyingObject;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import edu.hitsz.strategy.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    private ExecutorService shootExecutor;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProps> leftProps;
    private EnemyFactory enemyFactory;
    private PropsFactory propsFactory;

    private int enemyMaxNumber = 5;
    private int bossScoreThreshold = 1500;
    private boolean isBoss = false;
    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    private String levelChoose = "easy";
    private String playerName = "testPlayer";
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private int cycleTime_b = 0;

    private int score_moe = 20;
    private int score_Elite = 40;
    private int score_Boss = 100;

    private int precent_Props_Health = 50;

    private int health_Up = 50;

    private int hero_hp = 1000;
    private int hero_shootnum = 2;
    private int power_hero = 30;

    private BufferedImage BACKGROUND=ImageManager.BACKGROUND_IMAGE;

    private final MusicPlayer musicPlayer;

    private final Random random = new Random();

    void initGame(int level) {
        switch (level) {
            case 0:
                enemyMaxNumber = 5;
                bossScoreThreshold = 1200;
                score_moe = 20;
                score_Elite = 40;
                score_Boss = 100;
                precent_Props_Health = 50;
                health_Up = 100;
                hero_hp = 1000;
                hero_shootnum = 1;
                power_hero = 60;
                levelChoose = "Easy";
                enemyFactory = new EasyFactory();
                propsFactory = new EasyProFactory();
                BACKGROUND=ImageManager.BACKGROUND_IMAGE;
                break;
            case 1:
                enemyMaxNumber = 6;
                bossScoreThreshold = 2000;
                score_moe = 30;
                score_Elite = 80;
                score_Boss = 200;
                precent_Props_Health = 20;
                health_Up = 80;
                hero_hp = 800;
                hero_shootnum = 2;
                power_hero = 40;
                levelChoose = "Normal";
                enemyFactory = new NormalFactory();
                propsFactory = new NormalProFactory();
                BACKGROUND=ImageManager.BACKGROUND_IMAGE_TWO;
                break;
            case 2:
                enemyMaxNumber = 7;
                bossScoreThreshold = 3000;
                score_moe = 40;
                score_Elite = 100;
                score_Boss = 300;
                precent_Props_Health = 10;
                health_Up = 60;
                hero_hp = 500;
                hero_shootnum = 2;
                power_hero = 50;
                levelChoose = "Hard";
                enemyFactory = new HardFactory();
                propsFactory = new HardProFactory();
                BACKGROUND=ImageManager.BACKGROUND_IMAGE_FIVE;
                break;
            default:break;

        }
        AbstractProps.setHp(health_Up);
    }
    public Game(int chanllengeLevel,boolean voice) {

        // 初始化游戏
        initGame(chanllengeLevel);
        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, hero_hp, hero_shootnum, power_hero);

        heroAircraft.setStrategy(new LineShootStrategy());
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        leftProps = new LinkedList<>();
        musicPlayer = new MusicPlayer(voice);
        // 难度选择修改处
        // 简单模式工厂
        // 产生工厂，设定道具

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.shootExecutor = Executors.newFixedThreadPool(2);
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

            // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
            Runnable task = () -> {

                synchronized (Main.lock){time += timeInterval;

                // 周期性执行（控制频率）
                if (timeCountAndNewCycleJudge()) {
                    System.out.println(time);
                    // 新敌机产生
                    if (!isBoss && score >= bossScoreThreshold) {
                        enemyAircrafts.add(enemyFactory.callEnemy("boss"));
                        isBoss = true;
                        musicPlayer.playBgmBoss();
                    } else if (enemyAircrafts.size() < enemyMaxNumber) {
                        String str = EnemySelector.selectoString();
                        enemyAircrafts.add(enemyFactory.callEnemy(str));
                    }
                    // // 飞机射出子弹
                    // shootAction();
                }
                // 子弹采用新的周期
                // TODO 考虑敌机和自己的也分开
                // 后面改为多线程之后可能需要
                if (timeCountAndNewCycleJudge_bullet()) {
                    shootAction();
                }
                // 子弹移动
                bulletsMoveAction();

                // 飞机移动
                aircraftsMoveAction();

                // 道具移动
                propsMoveAction();

                // 撞击检测
                crashCheckAction();

                // 后处理
                postProcessAction();

                // 每个时刻重绘界面
                repaint();

                // 游戏结束检查
                if (heroAircraft.getHp() <= 0) {
                    // 游戏结束
                    musicPlayer.shotDownBgm();
                    musicPlayer.playOver();
                    executorService.shutdown();
                    gameOverFlag = true;
                    System.out.println("Game Over!");
                    Main.lock.notifyAll();
                }
            }
            };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         *
         */
        musicPlayer.playBgm();
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    // ***********************
    // Action 各部分
    // ***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean timeCountAndNewCycleJudge_bullet() {
        cycleTime_b += timeInterval * 2;
        if (cycleTime_b >= cycleDuration && cycleTime_b - timeInterval < cycleTime_b) {
            // 跨越到新的周期
            cycleTime_b %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private static int elite_check = 0;

    private void shootAction() {
        // TODO 敌机射击

        // enemyBullets.addAll(enemyAircrafts)
        if (elite_check == 3) {
            for (AbstractAircraft en : enemyAircrafts) {
                enemyBullets.addAll(en.shoot());

            }
            elite_check = 0;
        } else {
            elite_check++;
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProps prob : leftProps) {
            prob.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄

        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                musicPlayer.playHit();
                heroAircraft.decreaseHp(bullet.getPower());
                System.out.println("GET HIT!");
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    musicPlayer.playHit();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if (enemyAircraft instanceof BossEnemy) {
                            bossScoreThreshold =score + bossScoreThreshold;
                            isBoss = false;
                            score += score_Boss;
                            musicPlayer.playBgm();
                        } else if (enemyAircraft instanceof EliteEnemy) {
                            score += score_Elite;
                            if (isPercent(precent_Props_Health)) {
                                int x = enemyAircraft.getLocationX();
                                int y = enemyAircraft.getLocationY();
                                leftProps.add(propsFactory.callProps(PropsSelector.selectoString(), x, y));
                            }
                        } else {
                            score += score_moe;
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProps probs : leftProps) {
            if (probs.notValid()) {
                continue;
            }
            if (heroAircraft.crash(probs)) {

                if (probs instanceof BloodProps) {
                    heroAircraft.recoverHp(probs.getHp());
                    musicPlayer.playSupply();

                } else if (probs instanceof BombProps) {
                    musicPlayer.playBomb();
                    probs.effectCrash();

                } else if (probs instanceof BulletProps) {
                    musicPlayer.playSupply();
                    probs.effectCrash();
//                    heroAircraft.setStrategy(new SpreadShootStrategy());
                    // 子弹增加
                    Fire();

                }
                probs.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        leftProps.removeIf(AbstractFlyingObject::notValid);
    }

    // ***********************
    // Paint 各部分
    // ***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(BACKGROUND, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(BACKGROUND, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, leftProps);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        // 绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    private boolean isPercent(int over)

    {
        if (random.nextInt(100) > over) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isOver(){
        return this.gameOverFlag;
    }
    public int getScore()
    {
        return this.score;
    }
    public void Fire(){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                heroAircraft.setStrategy(new SpreadShootStrategy());
//
//                try {
//                    Thread.sleep(7000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                heroAircraft.setStrategy(new LineShootStrategy());
//            }
//        };
        Runnable runnable = ()->{
            heroAircraft.setStrategy(new SpreadShootStrategy());

                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                heroAircraft.setStrategy(new LineShootStrategy());
        };
        shootExecutor.submit(runnable);
    }
}
