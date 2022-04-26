package edu.hitsz.application;

import edu.hitsz.scores.Score;
import edu.hitsz.scores.ScoreDao;
import edu.hitsz.scores.ScoreDaolmpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Result {
    public  JPanel mainPanel;
    private JButton deButton;
    private JTable table;
    private JScrollPane ScrollPanel;
    private JLabel label;
    private JLabel labe2;
    private final ScoreDao scoreDao;
    private final DefaultTableModel model;

    public Result(int Score,int levels) {
        String level = toString(levels);
        scoreDao = new ScoreDaolmpl(level);
        label.setText("难度："+level);
        String[]cloname={"排名","玩家名称","得分","记录日期"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(cloname);
        update();
        table.setModel(model);
        ScrollPanel.setViewportView(table);
        String name = JOptionPane.showInputDialog("请输入姓名:", "testPlayer");
        if(name!=null) {
            scoreDao.doAdd(name, Score);
            update();
        }
        deButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(null, "请选择一行");
                }
                else {
                    int id=JOptionPane.showConfirmDialog(null, "确定删除吗？", "删除", JOptionPane.YES_NO_OPTION);
                    if(id==JOptionPane.YES_OPTION)
                    {
                        String name = (String) model.getValueAt(row, 1);
                        String date = (String) model.getValueAt(row, 3);
                        scoreDao.doDelete(name,date);
                        update();
                    }

                }
            }
        });
    }
    void update(){
        while (model.getRowCount()>0){
            model.removeRow(0);
        }
        List<String[]> list = scoreDao.outPut();
        for (String[] s:list){
            model.addRow(s);
        }
    }
    String toString(int a)
    {
        switch (a)
        {
            case 0:
                return "Easy";

            case 1:
                return "Normal";

            case 2:
                return "Hard";
            default:
                return "";
        }
    }
}
