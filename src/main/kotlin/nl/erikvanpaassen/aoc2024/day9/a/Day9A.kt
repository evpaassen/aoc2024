package nl.erikvanpaassen.aoc2024.day9.a

import java.io.File

fun main() {
    val input = File("input/day9a.txt").readText().toCharArray()

    val diskMap = createDiskMap(input)
    diskMap.defrag()

    println(diskMap.getChecksum())
}

private fun createDiskMap(input: CharArray): DiskMap {
    val diskMapSize = input.sumOf(Character::getNumericValue)

    val diskMap = MutableList<UInt?>(diskMapSize) { null }

    var fileId = 0u
    var block = 0
    var emptySpace = false

    for (c in input) {
        val size = Character.getNumericValue(c)

        for (b in 0..<size) {
            diskMap[block] = if (emptySpace) null else fileId
            block++
        }

        if (!emptySpace) {
            fileId++
        }

        emptySpace = !emptySpace
    }

    return DiskMap(diskMap)
}

class DiskMap(val blocks: MutableList<UInt?>) {

    fun defrag() {
        for (i in blocks.indices.reversed()) {
            val newIndex = blocks.indexOf(null)

            if (newIndex == -1 || newIndex > i) {
                return
            } else {
                blocks[newIndex] = blocks[i]
                blocks[i] = null
            }
        }
    }

    fun getChecksum() = blocks.mapIndexed { index, fileId -> if (fileId == null) 0 else index * fileId.toLong() }.sum()
}
