package nl.erikvanpaassen.aoc2024.day11.b

import java.io.File
import java.math.BigInteger

val cache = hashMapOf<StoneTimes, BigInteger>()

fun main() {
    val stones = File("input/day11a.txt").readLines().first().split(' ').map(String::toLong)

    val totalStones = stones.sumOf { stone -> blink(stone, 75) }

    println(totalStones)
}

data class StoneTimes(val stone: Long, val times: Int)

fun blink(stone: Long, times: Int): BigInteger {
    val stoneTimes = StoneTimes(stone, times)

    val cached = cache.get(stoneTimes)
    if (cached != null) {
        return cached
    }

    if (times == 0) {
        return BigInteger.ONE
    }

    val stoneString = stone.toString()

    val result = if (stone == 0L) {
        blink(1, times - 1)
    } else if (stoneString.length % 2 == 0) {
        blink(stoneString.substring(0, stoneString.length / 2).toLong(), times - 1) + blink(stoneString.substring(stoneString.length / 2, stoneString.length).toLong(), times - 1)
    } else {
        blink(stone * 2024, times - 1)
    }

    cache.put(stoneTimes, result)

    return result
}
