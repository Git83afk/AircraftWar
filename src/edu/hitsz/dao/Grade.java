package edu.hitsz.dao;

public class Grade {
    private String playerName;
    private String time;
    private int score;

    public  Grade(String playerName ,String time ,int score){
        this.playerName = playerName;
        this.time = time;
        this.score = score;
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getTime(){
        return  time;
    }

    public int getScore(){
        return score;
    }
}
