package org.nanowrimo.ws;

/**
 * <p>A summary of a given user or region including name, numeric Nanowrimo ID, and the current wordcount.</p>
 */
class WordCountSummary {
    private int summaryID;
    private String name;
    private String error;
    private int wordcount;
    private Boolean winner;
    private int goal = 50000;

    public int getSummaryID() {
        return summaryID;
    }

    public void setSummaryID(int summaryID) {
        this.summaryID = summaryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        if (goal > 0) {
            this.goal = goal;
        }
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public Boolean isWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
