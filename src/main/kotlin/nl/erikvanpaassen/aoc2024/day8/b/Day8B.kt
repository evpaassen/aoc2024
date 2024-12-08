package nl.erikvanpaassen.aoc2024.day8.b

import java.io.File

fun main() {
    val input = File("input/day8a.txt").readLines()

    val roofMap = RoofMap.parse(input)

    printMap(roofMap)
    println()

    println(roofMap.findAntinodes().count())
}

fun printMap(map: RoofMap) {
    val antinodes = map.findAntinodes()

    for (y in 0..map.maxY) {
        for (x in 0..map.maxX) {
            val c = Coordinates(x, y)
            if (antinodes.contains(c)) {
                print('#')
            } else {
                print('.')
            }
        }
        println()
    }
}


class RoofMap(val maxX: Int, val maxY: Int) {

    private val antennaCoordinates = mutableMapOf<Char, Antennas>()

    fun isInBounds(c: Coordinates): Boolean {
        return c.x in 0..maxX && c.y in 0..maxY
    }

    fun addAntenna(c: Char, coordinates: Coordinates) {
        val antennas = antennaCoordinates.computeIfAbsent(c, { _ -> Antennas(this) })
        antennas.addAntenna(coordinates)
    }

    fun findAntinodes() = antennaCoordinates.flatMap { p -> p.value.findAntinodes() }
        .distinct()

    companion object {
        fun parse(input: List<String>): RoofMap {
            val maxY = input.count() - 1
            val maxX = input[0].count() - 1

            val map = RoofMap(maxX, maxY)

            input.forEachIndexed { y, line ->
                run {
                    val row = line.toCharArray()
                    row.forEachIndexed { x, c ->
                        run {
                            if (c != '.') {
                                map.addAntenna(c, Coordinates(x, y))
                            }
                        }
                    }
                }
            }

            return map
        }
    }
}

class Antennas(private val map: RoofMap) {
    private val coordinates = mutableListOf<Coordinates>()

    fun findAntinodes(): Set<Coordinates> {
        val antinodes = coordinates.toMutableSet()

        for (c1 in coordinates) {
            for (c2 in coordinates) {
                if (c1 != c2) {
                    val diffX = c1.x - c2.x
                    val diffY = c1.y - c2.y

                    var currentCoordinates = c1
                    while (true) {
                        currentCoordinates = Coordinates(currentCoordinates.x + diffX, currentCoordinates.y + diffY)
                        if (map.isInBounds(currentCoordinates)) {
                            antinodes.add(currentCoordinates)
                        } else {
                            break
                        }
                    }

                    currentCoordinates = c2
                    while (true) {
                        currentCoordinates = Coordinates(currentCoordinates.x - diffX, currentCoordinates.y - diffY)
                        if (map.isInBounds(currentCoordinates)) {
                            antinodes.add(currentCoordinates)
                        } else {
                            break
                        }
                    }
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