package nl.erikvanpaassen.aoc2024.day11.a

import java.io.File

fun main() {
    val stones = File("input/day11a-example.txt").readLines().first().split(' ').map(String::toLong)

    val totalStones = stones.sumOf { stone -> blink(stone, 25) }

    println(totalStones)
}

fun blink(stone: Long, times: Int): Long {
    if (times == 0) {
        return 1
    }

    val stoneString = stone.toString()
    return if (stone == 0L) {
        blink(1, times - 1)
    } else if (stoneString.length % 2 == 0) {
        blink(stoneString.substring(0, stoneString.length / 2).toLong(), times - 1) + blink(stoneString.substring(stoneString.length / 2, stoneString.length).toLong(), times - 1)
    } else {
        blink(stone * 2024, times - 1)
    }
}
