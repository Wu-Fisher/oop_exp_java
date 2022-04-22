package edu.hitsz.application;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 程序入口
 * 
 * @author hitsz
 */
public class Menu
{

    public JPanel menu;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JCheckBox voiceTurn;
    private boolean Voice = false;
    private int level =0;
    private boolean choose = false;
    public Menu() {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                level=0;
                Voice= voiceTurn.isSelected();
                choose = true;
            }
        });
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                level=1;
                Voice= voiceTurn.isSelected();
                choose = true;
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                level=2;
                Voice= voiceTurn.isSelected();
                choose = true;
            }
        });
    }
    public int getLevel(){
        return this.level;
    }
    public  boolean getVoice(){
        return this.Voice;
    }
    public boolean getChoose(){
        return this.choose;
    }


}
