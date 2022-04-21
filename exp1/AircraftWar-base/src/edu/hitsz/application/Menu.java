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
    private static boolean Voice = false;
    public Menu() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                    Voice= voiceTurn.isSelected();
            }
        });
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Voice= voiceTurn.isSelected();
            }
        });
    }


}
