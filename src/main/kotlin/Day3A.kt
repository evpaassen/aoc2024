package nl.erikvanpaassen.aoc2024

import java.io.File

fun main() {
    val input = File("input/day3a.txt").readText()

    val regex = Regex("mul\\(([0-9]+),([0-9]+)\\)")
    val result = regex.findAll(input)

    var total = 0
    result.forEach { match -> total += match.groupValues[1].toInt() * match.groupValues[2].toInt() }

    println(total)
}
