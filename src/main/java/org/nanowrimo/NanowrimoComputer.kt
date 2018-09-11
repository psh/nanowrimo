package org.nanowrimo

import java.util.*

import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH

const val DAYS_IN_NANO = 30

class NanowrimoComputer(written: List<Int>, val monthlyGoal: Int = 50000) {

    val historyCounts: IntArray = IntArray(DAYS_IN_NANO)
    val wordcount: IntArray = IntArray(DAYS_IN_NANO)
    val pace: IntArray = IntArray(DAYS_IN_NANO)
    val expectedPerDayAmount: IntArray = IntArray(DAYS_IN_NANO)
    val expectedWordCount: IntArray = IntArray(DAYS_IN_NANO)
    val boundries: BooleanArray = BooleanArray(DAYS_IN_NANO)
    val paceExpected: IntArray = IntArray(DAYS_IN_NANO)
    val outliers: BooleanArray = BooleanArray(DAYS_IN_NANO)
    var adjustedCount: IntArray = IntArray(DAYS_IN_NANO)

    var endDay: Int = 0
    var current: Int = 0
    var percentComplete: Double = 0.toDouble()
    var daysLeftNaNoWriMo: Int = 0
    var maxAmount: Int = 0
    var paceExpectedTotal: Int = 0
    var currentDay: Int = 0
    var numberObservations: Int = 0
    var populationMean: Int = 0

    lateinit var outliersList: MutableList<Int>

    init {
        computeBasicStatistics(written.toIntArray())
        detectPopulationOutliers()
        computePercentComplete()
        computeDaysLeft()
        computePerDayAmount()
    }

    private fun computeBasicStatistics(amounts: IntArray) {
        numberObservations = amounts.size

        var i = 0
        var count = 0
        while (i < historyCounts.size && count < DAYS_IN_NANO) {
            historyCounts[i] = amounts[i]
            val previous = current
            current += historyCounts[i]
            wordcount[i] = current
            boundries[i] = previous / 10000 + 1 == current / 10000
            count++
            pace[i] = current / count
            i++
        }
    }

    private fun computeCorrelation(skip: Int): Double {
        var sy = 0.0
        var sx = 0.0
        var sxx = 0.0
        var syy = 0.0
        var sxy = 0.0
        var n = wordcount.size.toDouble()
        var i = 0
        while (i < n) {
            if (i != skip) {
                sx += (i + 1).toDouble()
                sxx += ((i + 1) * (i + 1)).toDouble()
                syy += (wordcount[i] * wordcount[i]).toDouble()
                sxy += ((i + 1) * wordcount[i]).toDouble()
                sy += wordcount[i].toDouble()
            } else {
                n--
            }
            i++
        }

        val top = n * sxy - sx * sy
        val bottomX = n * sxx - sx * sx
        val bottomY = n * syy - sy * sy
        return top / (Math.sqrt(bottomX) * Math.sqrt(bottomY))
    }

    private fun detectPopulationOutliers() {
        var sxx = 0
        for (historyCount1 in historyCounts) {
            sxx += historyCount1 * historyCount1
        }

        var initialSumX = current
        var initialSumXX = sxx
        var theCount = historyCounts.size.toDouble() - 1
        outliersList = mutableListOf()
        for (i in historyCounts.indices) {
            val dailyValue = historyCounts[i]
            val sumx = initialSumX - dailyValue
            val sumxx = initialSumXX - dailyValue * dailyValue
            val mean = sumx.toDouble() / theCount
            val stdDev = Math.sqrt(sumxx.toDouble() / theCount - mean * mean)
            val range = (stdDev * 2).toInt()
            val minOutlier = Math.floor(mean - range).toInt()
            val maxOutlier = Math.ceil(mean + range).toInt()
            outliers[i] = dailyValue <= minOutlier || dailyValue >= maxOutlier
            if (outliers[i]) {
                theCount--
                initialSumX -= dailyValue
                initialSumXX -= dailyValue * dailyValue
                outliersList.add(dailyValue)
            }
            endDay = 1 + Math.ceil((monthlyGoal.toDouble() - current.toDouble()) / pace[i].toDouble()).toInt()
        }

        populationMean = Math.ceil(initialSumX.toDouble() / theCount).toInt()
        adjustedCount = IntArray(historyCounts.size)
        for (i in historyCounts.indices) {
            val historyCount = historyCounts[i]
            adjustedCount[i] = if (outliers[i]) populationMean else historyCount
        }

        var total = 0.0
        var expTotal = 0
        for (i in adjustedCount.indices) {
            total += adjustedCount[i].toDouble()
            pace[i] = Math.ceil(total / (i + 1).toDouble()).toInt()
            expTotal += pace[i]
            paceExpected[i] = expTotal
        }
    }

    private fun computePerDayAmount() {
        var remaining = monthlyGoal
        var daysLeft = DAYS_IN_NANO
        maxAmount = 0
        var runningTotal = 0
        for (i in expectedPerDayAmount.indices) {
            expectedPerDayAmount[i] = remaining / daysLeft
            maxAmount = Math.max(maxAmount, expectedPerDayAmount[i])
            runningTotal += expectedPerDayAmount[i]
            expectedWordCount[i] = runningTotal
            if (i < historyCounts.size) {
                remaining -= historyCounts[i]
                maxAmount = Math.max(maxAmount, historyCounts[i])
            } else {
                remaining -= expectedPerDayAmount[i]
            }
            daysLeft--
        }
    }

    private fun computePercentComplete() {
        percentComplete = current.toDouble() * 100.0 / monthlyGoal.toDouble()
        if (percentComplete > 100.0) {
            percentComplete = 100.0
        }
    }

    private fun computeDaysLeft() {
        val calendar = GregorianCalendar()
        calendar.time = Date(System.currentTimeMillis())
        val month = calendar.get(MONTH)
        val day = calendar.get(DAY_OF_MONTH)
        when {
            month < 10 -> {
                daysLeftNaNoWriMo = DAYS_IN_NANO
                currentDay = 1
            }
            month == 10 -> {
                daysLeftNaNoWriMo = 31 - day
                currentDay = day
            }
            else -> {
                daysLeftNaNoWriMo = 0
                currentDay = DAYS_IN_NANO
            }
        }
    }
}
