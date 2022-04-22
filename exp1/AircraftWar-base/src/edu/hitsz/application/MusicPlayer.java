package edu.hitsz.application;

import edu.hitsz.thread.MusicThread;
import edu.hitsz.thread.menuThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MusicPlayer {

    private static boolean BGM = true;
    private static final String BGM_PATH = "src/videos/bgm.wav";
    private static final String BOSS_PATH = "src/videos/bgm_boss.wav";
    private static final String BOMB_PATH = "src/videos/bomb_explosion.wav";
    private static final String HIT_PATH = "src/videos/bullet_hit.wav";
    private static final String OVER_PATH = "src/videos/game_over.wav";
    private static final String SUPPLY_PATH = "src/videos/get_supply.wav";
    private ScheduledExecutorService executorService;
    private final ExecutorService executorN;
    public MusicPlayer(boolean isVoice) {
        executorN = new ScheduledThreadPoolExecutor(16,new BasicThreadFactory.Builder().namingPattern("music-%d").daemon(true).build());
        MusicPlayer.BGM = isVoice;
    }
    public void setBgm(boolean b)
    {
        BGM = b;
    }
    public void playBgm()
    {
        shotDownBgm();
        if(this.executorService == null && BGM)
        {this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
        Thread task = new MusicThread(BGM_PATH);
        executorService.scheduleAtFixedRate(task, 0, 35, TimeUnit.SECONDS);}
    }
    public void playBgmBoss()
    {
        shotDownBgm();
        if(this.executorService == null && BGM)
        {this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
            Thread task = new MusicThread(BOSS_PATH);
            executorService.scheduleAtFixedRate(task, 0, 75, TimeUnit.SECONDS);}
    }
    public void playHit()
    {
        if(BGM) {
            executorN.execute(new MusicThread(HIT_PATH));
        }
    }
    public void playBomb()
    {
        if(BGM) {
            executorN.execute(new MusicThread(BOMB_PATH));
        }
    }
    public void playOver()
    {
        if(BGM) {
            executorN.execute(new MusicThread(OVER_PATH));
        }
    }
    public void playSupply()
    {
        if(BGM) {
            executorN.execute(new MusicThread(SUPPLY_PATH));
        }
    }
    public void shotDownBgm()
    {
        if(executorService!= null) {
            this.executorService.shutdownNow();
            this.executorService = null;
        }
    }
}
