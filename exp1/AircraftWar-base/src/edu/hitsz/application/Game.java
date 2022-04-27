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
public abstract class Game extends JPanel {
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    public ExecutorService getShootExecutor() {
        return shootExecutor;
    }

    private ExecutorService shootExecutor;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    public int timeInterval = 40;

    public HeroAircraft getHeroAircraft() {
        return heroAircraft;
    }

    private final HeroAircraft heroAircraft;
    public final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    public final List<BaseBullet> enemyBullets;
    private final List<AbstractProps> leftProps;
    public EnemyFactory enemyFactory;
    public PropsFactory propsFactory;

    public int enemyMaxNumber = 5;
    public int bossScoreThreshold = 1500;
    public boolean isBoss = false;
    public boolean gameOverFlag = false;
    public int score = 0;
    public int time = 0;
    public String levelChoose = "easy";
    public String playerName = "testPlayer";
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    public int cycleDuration = 800;
    public int cycleDuration_b = 800;
    private int cycleTime = 0;
    private int cycleTime_b = 0;

    public int score_moe = 20;
    public int score_Elite = 40;
    public int score_Boss = 100;

    public int precent_Props = 50;

    public int health_Up = 50;

    public int HERO_HEALTH = 1000;
    public int HERO_SHOOT_NUM = 2;
    public int HERO_SHOOT_DAMAGE = 30;

    public int HERO_SHOOT_PRE = 4;
    public int ENEMY_SHOOT_PRE = 12;

    public double HEALTH_TIMES = 1;
    public double DAMAGE_TIMES = 1;

    public BufferedImage BACKGROUND = ImageManager.BACKGROUND_IMAGE;

    public final MusicPlayer musicPlayer;

    private final Random random = new Random();

    // 游戏数据初始化
    public abstract void initGame();

    public Game(boolean voice) {

        // 初始化游戏
        initGame();
        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                0, 0, HERO_HEALTH, HERO_SHOOT_NUM, HERO_SHOOT_DAMAGE);

        heroAircraft.setStrategy(new LineShootStrategy());
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        leftProps = new LinkedList<>();
        // 音乐播放器
        musicPlayer = new MusicPlayer(voice);
        // 难度选择修改处
        // 简单模式工厂
        // 产生工厂，设定道具

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        // 火力线程池
        this.shootExecutor = Executors.newFixedThreadPool(1);
        // 游戏线程池
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
            synchronized (Main.lock) {

                time += timeInterval;
                // 周期性执行（控制频率）
                if (timeCountAndNewCycleJudge() && !gameOverFlag) {
                    // 新敌机产生
                    if (!isBoss && score >= bossScoreThreshold && levelChoose != "Easy") {
                        enemyAircrafts.add(enemyFactory.callEnemy("boss"));
                        isBoss = true;
                        musicPlayer.shotDownBgm();
                        musicPlayer.playBgmBoss();
                    } else if (enemyAircrafts.size() < enemyMaxNumber) {
                        String str = selectEnemy();
                        enemyAircrafts.add(enemyFactory.callEnemy(str));
                    }
                    // // 飞机射出子弹-==
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
                    // 关闭背景音乐，播放游戏结束
                    executorService.shutdown();
                    musicPlayer.shotDownBgm();
                    musicPlayer.playOver();

                    gameOverFlag = true;
                    System.out.println("Game Over!");
                    // 唤醒主线程
                    Main.lock.notifyAll();
                }
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         *
         */
        new Thread(() -> {
            while (!gameOverFlag) {
                try {
                    levelChange();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        if (!gameOverFlag) {
            musicPlayer.playBgm();
        }
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

    // 子弹射击频率更快

    private boolean timeCountAndNewCycleJudge_bullet() {
        cycleTime_b += timeInterval * 10;
        if (cycleTime_b >= cycleDuration_b && cycleTime_b - timeInterval < cycleTime_b) {
            // 跨越到新的周期
            cycleTime_b %= cycleDuration_b;
            return true;
        } else {
            return false;
        }
    }

    public int elite_check = 0;
    public int hero_check = 0;

    private void shootAction() {
        // TODO 敌机射击

        // enemyBullets.addAll(enemyAircrafts)
        if (elite_check >= ENEMY_SHOOT_PRE) {
            for (AbstractAircraft en : enemyAircrafts) {
                enemyBullets.addAll(en.shoot());

            }
            elite_check = 0;
        } else {
            elite_check++;
        }
        // 英雄射击\
        if (hero_check >= HERO_SHOOT_PRE) {
            heroBullets.addAll(heroAircraft.shoot());
            hero_check = 0;
        } else {
            hero_check++;
        }

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
                // System.out.println("GET HIT!");
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
                        // boss 机坠毁
                        if (enemyAircraft instanceof BossEnemy) {
                            bossScoreThreshold += (bossScoreThreshold + score);
                            isBoss = false;
                            score += score_Boss;
                            musicPlayer.shotDownBgm();
                            musicPlayer.playBgm();
                        }
                        // 精英敌机坠毁
                        else if (enemyAircraft instanceof EliteEnemy) {
                            score += score_Elite;
                            if (isPercent(precent_Props)) {
                                int x = enemyAircraft.getLocationX();
                                int y = enemyAircraft.getLocationY();
                                leftProps.add(propsFactory.callProps(selectProp(), x, y));
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
                    // 多线程实现子弹增幅
                    // Fire();
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
        g.drawString("HEALTH:" + this.heroAircraft.getHp(), x, y);
    }

    private boolean isPercent(int over)

    {
        if (random.nextInt(100) < over) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOver() {
        return this.gameOverFlag;
    }

    public int getScore() {
        return this.score;
    }

    public abstract void levelChange();

    public abstract String selectEnemy();

    public abstract String selectProp();

}
