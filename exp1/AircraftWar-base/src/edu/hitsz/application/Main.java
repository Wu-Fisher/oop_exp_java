package edu.hitsz.application;

import edu.hitsz.thread.menuThread;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object lock = new Object();
    public static Menu menu;
    public static Game game;
    public static Result result;
    private static int Level = 0;
    private static boolean isGameOver = false;
    private static boolean isVoice = false;
    private static String Name = "testPlayer";
    private static int Score = 0;

    public static int getLevel() {
        return Level;
    }

    public static void setLevel(int level) {
        Level = level;
    }

    public static boolean isIsGameOver() {
        return isGameOver;
    }

    public static void setIsGameOver(boolean isGameOver) {
        Main.isGameOver = isGameOver;
    }

    public static boolean isIsVoice() {
        return isVoice;
    }

    public static void setIsVoice(boolean isVoice) {
        Main.isVoice = isVoice;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static int getScore() {
        return Score;
    }

    public static void setScore(int score) {
        Score = score;
    }

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        // 设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 初始化菜单
        Thread mt =  new menuThread();
        mt.start();
        synchronized (lock){
            menu = new Menu();
            lock.notify();
        frame.add(menu.menu);
        frame.setVisible(true);
        try {

            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.setIsVoice(Main.menu.getVoice());
        Main.setLevel(Main.menu.getLevel());

        }
        synchronized (lock) {
            frame.remove(menu.menu);
            game = new Game(Level, isVoice);
            frame.add(game);
            frame.setVisible(true);
            game.action();
            try {
                lock.wait();
            } catch (Exception e) {
            }
        }
        Score = game.getScore();
        synchronized (lock) {
            frame.remove(game);
            result = new Result(Score,getLevel());
            frame.add(result.mainPanel);
            frame.setVisible(true);


        }
    }

}


