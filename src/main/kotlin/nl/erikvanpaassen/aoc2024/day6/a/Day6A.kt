package nl.erikvanpaassen.aoc2024.day6.a

import java.io.File

fun main() {
    val input = File("input/day6a.txt").readLines()

    val map = LabMap.new(input)

    val guard = Guard(map)
    while (guard.isInsideLab()) {
        guard.step()
    }

    println(map.getPathLength())
}

data class Coordinates(val y: Int, val x: Int) {
    fun up() = Coordinates(y - 1, x)
    fun down() = Coordinates(y + 1, x)
    fun left() = Coordinates(y, x - 1)
    fun right() = Coordinates(y, x + 1)
}

class LabMap(private val grid: Array<Array<Position>>) {

    fun get(coordinates: Coordinates) = grid[coordinates.y][coordinates.x]

    fun getGuardCoordinates(): Coordinates {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                val guardCoordinates = Coordinates(y, x)
                if (get(guardCoordinates).isGuard()) {
                    return guardCoordinates
                }
            }
        }

        throw IllegalStateException()
    }

    fun contains(coordinates: Coordinates): Boolean {
        return coordinates.y >= 0 && coordinates.y < grid.count() && coordinates.x >= 0 && coordinates.x < grid[0].count()
    }

    fun getPathLength(): Int {
        return grid.map { row -> row.count { p -> p.isMarked() } }.reduce(Int::plus)
    }

    companion object {
        fun new(lines: List<String>): LabMap {
            return LabMap(lines.map { line -> line.toCharArray().map(::Position).toTypedArray() }.toTypedArray())
        }
    }
}

class Position(private var value: Char) {

    fun isObstacle() = value == '#'

    fun isGuard() = value == '^'

    fun isMarked() = value == 'X'

    fun mark() {
        value = 'X'
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

class Guard(private var map: LabMap) {

    private var coordinates = map.getGuardCoordinates()
    private var direction: Direction = Direction.UP

    fun step() {
        map.get(coordinates).mark()

        var next = nextCoordinates()
        while (map.contains(next) && map.get(next).isObstacle()) {
            changeDirection()
            next = nextCoordinates()
        }
        coordinates = next
    }

    private fun nextCoordinates(): Coordinates {
        return when (direction) {
            Direction.UP -> coordinates.up()
            Direction.DOWN -> coordinates.down()
            Direction.LEFT -> coordinates.left()
            Direction.RIGHT -> coordinates.right()
        }
    }

    private fun changeDirection() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
            Direction.RIGHT -> Direction.DOWN
        }
    }

    fun isInsideLab(): Boolean {
        return map.contains(coordinates)
    }
}
