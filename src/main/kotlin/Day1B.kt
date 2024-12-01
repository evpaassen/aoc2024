package nl.erikvanpaassen.aoc2024

import java.io.File

fun main() {
    val lists = SimilarLists(File("input/day1a.txt"))
    println(lists.getSimilarityScore())
}

class SimilarLists {
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

    fun getSimilarityScore(): Int {
        var similarityScore = 0

        for (n in listA) {
            similarityScore += n * listB.count { it == n }
        }

        return similarityScore;
    }
}
