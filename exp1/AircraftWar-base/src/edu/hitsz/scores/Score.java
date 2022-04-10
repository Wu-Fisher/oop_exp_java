package edu.hitsz.scores;

import java.io.Serializable;

public class Score implements Serializable {
    private int score;
    private String date;
    private String name;

    public Score(int score, String date, String name) {
        this.score = score;
        this.date = date;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
