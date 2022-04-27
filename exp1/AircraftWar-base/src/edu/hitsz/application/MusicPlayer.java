package edu.hitsz.application;

import edu.hitsz.thread.MusicThread;
import edu.hitsz.thread.menuThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

public class MusicPlayer {

    private static boolean BGM = true;
    private static final String BGM_PATH = "src/videos/bgm_2.wav";
    private static final String BOSS_PATH = "src/videos/bgm_boss_2.wav";
    private static final String BOMB_PATH = "src/videos/bomb_explosion.wav";
    private static final String HIT_PATH = "src/videos/bullet_hit.wav";
    private static final String OVER_PATH = "src/videos/game_over.wav";
    private static final String SUPPLY_PATH = "src/videos/get_supply.wav";

    private static final String LEVELUP_PATH = "src/videos/level_up.wav";
//    private ScheduledExecutorService executorService;
    private final ExecutorService executorN;
    private final ExecutorService executorM;
    private MusicThread mt;
    public MusicPlayer(boolean isVoice) {
//        executorN = new ExecutorService(16, new BasicThreadFactory.Builder().namingPattern("music-%d").daemon(true).build()) {
//        };
        executorN = Executors.newFixedThreadPool(32);
        executorM = Executors.newSingleThreadExecutor();
        MusicPlayer.BGM = isVoice;
    }
    public void setBgm(boolean b)
    {
        BGM = b;
    }


    public void playBgm()
    {

        shotDownBgm();
        Runnable r = ()->{
            do{
                mt=new MusicThread(BGM_PATH);
                mt.start();
                if(BGM)
                {try
                {
                    mt.join();
                }catch (Exception e)
                {
                }
                }
            }while(mt.isRunning && BGM);
        };
        if(BGM) {
            executorM.submit(r);
        }
    }

    public void playBgmBoss()
    {

        shotDownBgm();
        Runnable r = ()->{
            do{

                mt=new MusicThread(BOSS_PATH);
                mt.start();
                if(BGM) {
                    try {
                        mt.join();
                    } catch (Exception e) {
                    }
                }
            }while(mt.isRunning && BGM);
        };
        if(BGM)
        executorM.submit(r);
    }


    public void shotDownBgm()
    {
        if(mt!=null)
        {
            mt.setRunning(false);
        }
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
            BGM=false;
            shotDownBgm();
            executorN.execute(new MusicThread(OVER_PATH));
        }

    }
    public void playSupply()
    {
        if(BGM) {
            executorN.execute(new MusicThread(SUPPLY_PATH));
        }
    }
    public void playLevelup(){
        if(BGM)
        {
            executorN.execute(new MusicThread(LEVELUP_PATH));
        }
    }

}