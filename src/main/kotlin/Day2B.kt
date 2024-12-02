package nl.erikvanpaassen.aoc2024

import java.io.File
import kotlin.math.abs

fun main() {
    val reports = File("input/day2a.txt").readLines().map(::DampenedReport)
    println(reports.count(DampenedReport::isDampenedSafe))
}

class DampenedReport(val levels: List<Int>) {

    constructor(line: String) : this(line.split(' ').map { v -> v.toInt() })

    fun isSafe(): Boolean {
        var direction = 0

        for (i in levels.indices) {
            if (i < levels.lastIndex) {
                val thisLevel = levels[i]
                val nextLevel = levels[i + 1]

                val diff = abs(thisLevel - nextLevel);

                if (thisLevel < nextLevel && direction >= 0 && diff <= 3 && diff >= 1) {
                    // Increasing
                    direction = 1
                } else if (thisLevel > nextLevel && direction <= 0 && diff <= 3 && diff >= 1) {
                    // Decreasing
                    direction = -1
                } else {
                    return false
                }
            }
        }

        return true
    }

    fun isDampenedSafe(): Boolean {
        if (isSafe()) {
            return true
        }

        for (i in levels.indices) {
            if (DampenedReport(levels.filterIndexed { index, level -> index != i }).isSafe()) {
                return true
            }
        }

        return false
    }
}
