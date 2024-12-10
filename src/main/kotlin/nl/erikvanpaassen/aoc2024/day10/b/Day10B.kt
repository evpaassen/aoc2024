package nl.erikvanpaassen.aoc2024.day10.b

import java.io.File

fun main() {
    val input = File("input/day10a.txt").readLines()

    val topographicMap = TopographicMap.new(input)

    val totalScore = topographicMap.getTrailheads().sumOf { th -> topographicMap.findReachableNines(th).count() }
    println(totalScore)
}

data class Coordinates(val y: Int, val x: Int) {
    fun up() = Coordinates(y - 1, x)
    fun down() = Coordinates(y + 1, x)
    fun left() = Coordinates(y, x - 1)
    fun right() = Coordinates(y, x + 1)
}

class TopographicMap(private val grid: Array<Array<Int>>) {

    fun get(coordinates: Coordinates) = grid[coordinates.y][coordinates.x]

    fun getTrailheads(): List<Coordinates> {
        return grid.flatMapIndexed { y, row ->
            row.mapIndexed { x, height -> if (height == 0) Coordinates(y, x) else null }
        }.filterNotNull()
    }

    fun findReachableNines(position: Coordinates): List<Coordinates> {
        val currentHeight = get(position)

        return if (currentHeight == 9) {
            listOf(position)
        } else {
            listOf(position.up(), position.down(), position.left(), position.right())
                .filter(::contains)
                .filter { c -> get(c) == currentHeight + 1 }
                .flatMap(::findReachableNines)
        }
    }

    fun contains(coordinates: Coordinates): Boolean {
        return coordinates.y >= 0 && coordinates.y < grid.count() && coordinates.x >= 0 && coordinates.x < grid[0].count()
    }

    companion object {
        fun new(lines: List<String>) = TopographicMap(lines.map { line -> line.toCharArray().map(Character::getNumericValue).toTypedArray() }.toTypedArray())
    }
}
