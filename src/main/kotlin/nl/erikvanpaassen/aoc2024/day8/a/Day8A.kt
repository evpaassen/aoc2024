package nl.erikvanpaassen.aoc2024.day8.a

import java.io.File

fun main() {
    val input = File("input/day8a.txt").readLines()

    val roofMap = RoofMap.parse(input)

    println(roofMap.findNumberOfAntinodes())
}

class RoofMap(private val maxX: Int, private val maxY: Int, private val antennaCoordinates: Map<Char, Antennas>) {

    private fun isInBounds(c: Coordinates): Boolean {
        return c.x in 0..maxX && c.y in 0..maxY
    }

    fun findNumberOfAntinodes() = antennaCoordinates.flatMap { p -> p.value.findAntinodes() }
        .distinct()
        .count(::isInBounds)

    companion object {
        fun parse(input: List<String>): RoofMap {
            val maxY = input.count() - 1
            val maxX = input[0].count() - 1

            val antennaCoordinates = mutableMapOf<Char, Antennas>()

            input.forEachIndexed { y, line ->
                run {
                    val row = line.toCharArray()
                    row.forEachIndexed { x, c ->
                        run {
                            if (c != '.') {
                                val antennas = antennaCoordinates.computeIfAbsent(c, { _ -> Antennas() })
                                antennas.addAntenna(Coordinates(x, y))
                            }
                        }
                    }
                }
            }

            return RoofMap(maxX, maxY, antennaCoordinates)
        }
    }
}

class Antennas {
    private val coordinates = mutableListOf<Coordinates>()

    fun findAntinodes(): Set<Coordinates> {
        val antinodes = mutableSetOf<Coordinates>()

        for (c1 in coordinates) {
            for (c2 in coordinates) {
                if (c1 != c2) {
                    val diffX = c1.x - c2.x
                    val diffY = c1.y - c2.y

                    val antiNode1 = Coordinates(c1.x + diffX, c1.y + diffY)
                    val antiNode2 = Coordinates(c2.x - diffX, c2.y - diffY)

                    antinodes.add(antiNode1)
                    antinodes.add(antiNode2)
                }
            }
        }

        return antinodes
    }

    fun addAntenna(c: Coordinates) {
        coordinates.add(c)
    }
}

data class Coordinates(val x: Int, val y: Int)