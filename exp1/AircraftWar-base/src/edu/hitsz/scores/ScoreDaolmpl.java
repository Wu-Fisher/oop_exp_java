package edu.hitsz.scores;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScoreDaolmpl implements ScoreDao {

    private String fileName = System.getProperty("user.dir") + "/src/edu/hitsz/scores/scores.txt";

    public ScoreDaolmpl() {
        super();
    }

    public ScoreDaolmpl(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Score findByName(String name) {
        List<Score> scores = getAllScores();
        for (Score score : scores) {
            if (score.getName().equals(name)) {
                return score;
            }
        }
        return null;
    }

    @Override
    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<Score>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream(new File(fileName)));
            Score sc = null;
            while ((sc = (Score) ois.readObject()) != null) {
                scores.add(sc);
            }
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }

    @Override
    public void saveAllScores(List<Score> scores) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new java.io.FileOutputStream(fileName));
            for (Score sc : scores) {
                oos.writeObject(sc);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAdd(String name, int score) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String d = sdf.format(date);
        Score sc = new Score(score, d, name);
        List<Score> scores = getAllScores();
        scores.add(sc);
        saveAllScores(scores);
    }

    @Override
    public void doDeleteByName(String name) {
        List<Score> scores = getAllScores();
        for (Score score : scores) {
            if (score.getName().equals(name)) {
                scores.remove(score);
                break;
            }
        }
        saveAllScores(scores);
    }

    @Override
    public void printRank() {

        List<Score> scores = getAllScores();
        Collections.sort(scores, (Score s1, Score s2) -> s2.getScore() - s1.getScore());
        System.out.println("**********************");
        System.out.println("排行榜");
        System.out.println("**********************");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println("第" + Integer.toString(i + 1) + "名：" + scores.get(i).getName() + ","
                    + scores.get(i).getScore() + ","
                    + scores.get(i).getDate());
        }
        saveAllScores(scores);
    }

}
