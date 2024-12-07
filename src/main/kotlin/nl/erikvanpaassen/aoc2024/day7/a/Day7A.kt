package nl.erikvanpaassen.aoc2024.day7.a

import java.io.File

fun main() {
    val equations = File("input/day7a.txt").readLines().map(Equation::parse)

    val totalCalibrationResult = equations.filter(Equation::canBeTrue).map(Equation::testValue).reduce(Long::plus)

    println(totalCalibrationResult)
}

data class Equation(val testValue: Long, val numbers: List<Long>) {

    fun canBeTrue(): Boolean {
        return canBeTrue(testValue, numbers)
    }

    fun canBeTrue(testValue: Long, numbers: List<Long>): Boolean {
        val lastNumber = numbers.last()

        if (numbers.count() == 1) {
            return lastNumber == testValue
        }

        val remainingNumbers = numbers.dropLast(1)

        return canBeTrue(testValue - lastNumber, remainingNumbers)
                || testValue % lastNumber == 0L && canBeTrue(testValue / lastNumber, remainingNumbers)
    }

    companion object {
        fun parse(line: String): Equation {
            val separatorIndex = line.indexOf(": ")

            return Equation(
                line.substring(0, separatorIndex).toLong(),
                line.substring(separatorIndex + 2).split(' ').map(String::toLong)
            )
        }
    }
}
