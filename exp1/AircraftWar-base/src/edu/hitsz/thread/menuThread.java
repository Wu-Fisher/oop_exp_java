package edu.hitsz.thread;

import edu.hitsz.application.Main;

public class menuThread extends Thread {
    @Override
    public void run() {

        synchronized (Main.lock) {
            while (Main.menu == null) {
                try {
                    Main.lock.wait();
                } catch (InterruptedException e) {

                }
            }synchronized (Main.lock) {
                while (Main.menu != null && !Main.menu.getChoose()) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Main.lock.notify();
            }
        }
    }
}