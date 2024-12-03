package nl.erikvanpaassen.aoc2024

import java.io.File

fun main() {
    val input = File("input/day3a.txt").readText()

    var preProcessedInput = removeDisabledInstructions(input)

    var total = getTotalOfMultiplications(preProcessedInput)

    println(total)
}

private fun removeDisabledInstructions(input: String): String {
    var processed = input

    while (true) {
        val disabledStart = processed.indexOf("don't()")
        if (disabledStart == -1) {
            break
        }

        var disabledEnd = processed.indexOf("do()", disabledStart)
        if (disabledEnd == -1) {
            disabledEnd = processed.lastIndex + 1
        }

        processed = processed.removeRange(disabledStart, disabledEnd)
    }

    return processed
}

private fun getTotalOfMultiplications(input: String): Int {
    val regex = Regex("mul\\(([0-9]+),([0-9]+)\\)")
    val result = regex.findAll(input)

    var total = 0
    result.forEach { match -> total += match.groupValues[1].toInt() * match.groupValues[2].toInt() }
    return total
}
