package edu.hitsz.scores;

import java.util.ArrayList;
import java.util.List;

public interface ScoreDao {

    Score findByName(String name);

    List<Score> getAllScores();

    void saveAllScores(List<Score> scores);

    void doAdd(String name, int score);

    void doDeleteByName(String name);

    void doDelete(String name,String date);

    void printRank();

    ArrayList<String[]> outPut();
}
