package org.nanowrimo;

import java.util.*;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;

@SuppressWarnings("unused")
public class NanowrimoComputer {
    private static final int DAYS_IN_NANO = 30;

    private int historyCounts[];
    private int pace[];
    private boolean boundries[];
    private int endDay;
    private int current;
    private double percentComplete;
    private int daysLeftNaNoWriMo;
    private int expectedPerDayAmount[];
    private int expectedWordCount[];
    private int paceExpected[];
    private int wordcount[];
    private boolean outliers[];
    private int maxAmount;
    private int paceExpectedTotal;
    private int currentDay;
    private int numberObservations;
    private int monthlyGoal;
    private int populationMean;
    private int[] adjustedCount;
    private List<Integer> outliersList;

    public NanowrimoComputer(String historyParameter, int goal) {
        historyParameter = (historyParameter == null) ? " " : historyParameter;
        StringTokenizer tokenizer = new StringTokenizer(historyParameter, " ,");
        int[] amounts = new int[tokenizer.countTokens()];
        for (int i=0; i < amounts.length; i++) {
            amounts[i] = Integer.parseInt(tokenizer.nextToken());
        }
        init(goal, amounts);
    }

    public NanowrimoComputer(int[] amounts, int goal) {
        init(goal, amounts);
    }

    private void init(int goal, int[] amounts) {
        monthlyGoal = goal;

        computeBasicStatistics(amounts);
        detectPopulationOutliers();
        computePercentComplete();
        computeDaysLeft();
        computePerDayAmount();
    }

    private void computeBasicStatistics(int[] amounts) {
        current = 0;
        endDay = 0;
        int count = 0;
        numberObservations = amounts.length;
        createArrays(numberObservations);

        for (int i = 0; i < historyCounts.length && count < DAYS_IN_NANO; i++) {
            historyCounts[i] = amounts[i];
            int previous = current;
            current += historyCounts[i];
            wordcount[i] = current;
            boundries[i] = ((previous / 10000 + 1) == (current / 10000));
            count++;
            pace[i] = current / count;
        }
    }

    private double computeCorrelation(int skip) {
        double sy = 0;
        double sx = 0;
        double sxx = 0;
        double syy = 0;
        double sxy = 0;
        double n = wordcount.length;
        for (int i = 0; i < n; i++) {
            if (i != skip) {
                sx += (i + 1);
                sxx += (i + 1) * (i + 1);
                syy += wordcount[i] * wordcount[i];
                sxy += (i + 1) * wordcount[i];
                sy += wordcount[i];
            } else {
                n--;
            }
        }

        double top = (n * sxy - sx * sy);
        double bottomX = (n * sxx - sx * sx);
        double bottomY = (n * syy - sy * sy);
        return top / (Math.sqrt(bottomX) * Math.sqrt(bottomY));
    }

    private void detectPopulationOutliers() {
        int sxx = 0;
        for (int historyCount1 : historyCounts) {
            sxx += historyCount1 * historyCount1;
        }

        int initialSumX = current;
        int initialSumXX = sxx;
        double theCount = ((double) historyCounts.length - 1);
        outliersList = new ArrayList<>();
        for (int i = 0; i < historyCounts.length; i++) {
            int dailyValue = historyCounts[i];
            int sumx = initialSumX - dailyValue;
            int sumxx = initialSumXX - (dailyValue * dailyValue);
            double mean = (double) sumx / theCount;
            double stdDev = Math.sqrt((double) sumxx / theCount - mean * mean);
            int range = (int) (stdDev * 2);
            int minOutlier = (int) Math.floor(mean - range);
            int maxOutlier = (int) Math.ceil(mean + range);
            outliers[i] = (dailyValue <= minOutlier || dailyValue >= maxOutlier);
            if (outliers[i]) {
                theCount--;
                initialSumX -= dailyValue;
                initialSumXX -= (dailyValue * dailyValue);
                outliersList.add(dailyValue);
            }
            endDay = 1 + (int) Math.ceil(((double) monthlyGoal - (double) current) / (double) pace[i]);
        }

        populationMean = (int) Math.ceil((double) initialSumX / theCount);
        adjustedCount = new int[historyCounts.length];
        for (int i = 0; i < historyCounts.length; i++) {
            int historyCount = historyCounts[i];
            adjustedCount[i] = (outliers[i]) ? populationMean : historyCount;
        }

        double total = 0;
        int expTotal = 0;
        for (int i = 0; i < adjustedCount.length; i++) {
            total += adjustedCount[i];
            pace[i] = (int) Math.ceil(total / (double) (i + 1));
            expTotal += pace[i];
            paceExpected[i] = expTotal;
        }
    }

    private void computePerDayAmount() {
        int remaining = monthlyGoal;
        int daysLeft = DAYS_IN_NANO;
        maxAmount = 0;
        int runningTotal = 0;
        for (int i = 0; i < expectedPerDayAmount.length; i++) {
            expectedPerDayAmount[i] = remaining / daysLeft;
            maxAmount = Math.max(maxAmount, expectedPerDayAmount[i]);
            runningTotal += expectedPerDayAmount[i];
            expectedWordCount[i] = runningTotal;
            if (i < historyCounts.length) {
                remaining -= historyCounts[i];
                maxAmount = Math.max(maxAmount, historyCounts[i]);
            } else {
                remaining -= expectedPerDayAmount[i];
            }
            daysLeft--;
        }
    }

    private void computePercentComplete() {
        percentComplete = ((double) current * 100.0) / (double) monthlyGoal;
        if (percentComplete > 100.0) {
            percentComplete = 100.0;
        }
    }

    private void createArrays(int numberObservations) {
        historyCounts = new int[numberObservations];
        wordcount = new int[numberObservations];
        pace = new int[DAYS_IN_NANO];
        expectedPerDayAmount = new int[DAYS_IN_NANO];
        expectedWordCount = new int[DAYS_IN_NANO];
        boundries = new boolean[DAYS_IN_NANO];
        paceExpected = new int[DAYS_IN_NANO];
        outliers = new boolean[DAYS_IN_NANO];
    }

    private void computeDaysLeft() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(System.currentTimeMillis()));
        int month = calendar.get(MONTH);
        int day = calendar.get(DAY_OF_MONTH);
        if (month < 10) {
            daysLeftNaNoWriMo = DAYS_IN_NANO;
            currentDay = 1;
        } else if (month == 10) {
            daysLeftNaNoWriMo = 31 - day;
            currentDay = day;
        } else {
            daysLeftNaNoWriMo = 0;
            currentDay = DAYS_IN_NANO;
        }
    }

    public int getNumberObservations() {
        return numberObservations;
    }

    public int getPopulationMean() {
        return populationMean;
    }

    public List<Integer> getOutliersList() {
        return outliersList;
    }

    public int getMonthlyGoal() {
        return monthlyGoal;
    }

    public int[] getHistoryCounts() {
        return historyCounts;
    }

    public int[] getPace() {
        return pace;
    }

    public boolean[] getBoundries() {
        return boundries;
    }

    public int getEndDay() {
        return endDay;
    }

    public int getCurrent() {
        return current;
    }

    public double getPercentComplete() {
        return percentComplete;
    }

    public int getDaysLeftNaNoWriMo() {
        return daysLeftNaNoWriMo;
    }

    public int[] getExpectedPerDayAmount() {
        return expectedPerDayAmount;
    }

    public int[] getPaceExpected() {
        return paceExpected;
    }

    public boolean[] getOutliers() {
        return outliers;
    }

    public int[] getExpectedWordCount() {
        return expectedWordCount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public int getPaceExpectedTotal() {
        return paceExpectedTotal;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public int[] getWordcount() {
        return wordcount;
    }

    public int[] getAdjustedCount() {
        return adjustedCount;
    }
}
