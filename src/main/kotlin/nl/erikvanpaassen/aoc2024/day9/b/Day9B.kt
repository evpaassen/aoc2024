package nl.erikvanpaassen.aoc2024.day9.b

import java.io.File

fun main() {
    val input = File("input/day9a.txt").readText().toCharArray()

    val diskMap = DiskMap.parse(input)
    diskMap.defrag()

    println(diskMap.getChecksum())
}

class DiskMap(val blocks: MutableList<UInt?>) {

    fun defrag() {
        var fileEnd = 0

        var fileId: UInt? = null

        for (i in blocks.indices.reversed()) {
            if (fileId != blocks[i]) {
                // End of a file detected
                fileEnd = i
                fileId = blocks[i]
            }

            if (i == 0 || blocks[i - 1] != fileId) {
                // Start of a file detected
                val fileLength = IntRange(i, fileEnd).count()

                for (j in blocks.indices) {
                    if (j >= i) {
                        break
                    }

                    var spaceFound = blocks.subList(j, j + fileLength).all { v -> v == null }

                    if (spaceFound) {
                        moveFile(i, j, fileLength)
                        break
                    }
                }
            }
        }
    }

    private fun moveFile(srcStart: Int, dstStart: Int, length: Int) {
        for (b in 0..<length) {
            blocks[dstStart + b] = blocks[srcStart + b]
            blocks[srcStart + b] = null
        }
    }

    fun getChecksum() = blocks.mapIndexed { index, fileId -> if (fileId == null) 0 else index * fileId.toLong() }.sum()

    companion object {
        fun parse(input: CharArray): DiskMap {
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
    }
}
