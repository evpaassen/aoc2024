package nl.erikvanpaassen.aoc2024

import java.io.File
import kotlin.math.abs

fun main() {
    val lists = DistantLists(File("input/day1a.txt"))
    println(lists.getTotalDistance())
}

class DistantLists {
    val listA: MutableList<Int> = mutableListOf()
    val listB: MutableList<Int> = mutableListOf()

    constructor(file: File) {
        file.readLines().forEach { l ->
            val split = l.split("   ")
            listA.add(split[0].toInt())
            listB.add(split[1].toInt())
        }

        listA.sort()
        listB.sort()
    }

    fun getTotalDistance(): Int {
        var totalDistance = 0

        for (i in 0..listA.lastIndex) {
            totalDistance += abs(listA[i] - listB[i])
        }

        return totalDistance
    }
}
