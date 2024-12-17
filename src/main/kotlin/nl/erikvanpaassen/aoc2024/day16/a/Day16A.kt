//package nl.erikvanpaassen.aoc2024.day16.a
//
//import java.io.File
//
//fun main() {
//    val input = File("input/day16a-example.txt").readLines()
//
//    val map = Maze.new(input)
//
//    println(extraObstaclePossibilities)
//}
//
//data class Coordinates(val y: Int, val x: Int) {
//    fun up() = Coordinates(y - 1, x)
//    fun down() = Coordinates(y + 1, x)
//    fun left() = Coordinates(y, x - 1)
//    fun right() = Coordinates(y, x + 1)
//}
//
//class Maze(private val grid: Array<Array<Tile>>) {
//
//    fun get(coordinates: Coordinates) = grid[coordinates.y][coordinates.x]
//
//    fun getGuardCoordinates(): Coordinates {
//        for (y in grid.indices) {
//            for (x in grid[y].indices) {
//                val guardCoordinates = Coordinates(y, x)
//                if (get(guardCoordinates).isStart()) {
//                    return guardCoordinates
//                }
//            }
//        }
//
//        throw IllegalStateException()
//    }
//
//    fun contains(coordinates: Coordinates): Boolean {
//        return coordinates.y >= 0 && coordinates.y < grid.count() && coordinates.x >= 0 && coordinates.x < grid[0].count()
//    }
//
//    fun allCoordinates(): List<Coordinates> {
//        return grid.indices.flatMap { y -> grid[y].indices.map { x -> Coordinates(x, y) } }
//    }
//
//    fun allTiles(): List<Tile> {
//        return allCoordinates().map(::get)
//    }
//
//    fun getStartTile() = allTiles().first(Tile::isStart)
//
//    fun getEndTile() = allTiles().first(Tile::isEnd)
//
//    companion object {
//        fun new(lines: List<String>): Maze {
//            return Maze(lines.map { line -> line.toCharArray().map(::Tile).toTypedArray() }.toTypedArray())
//        }
//    }
//}
//
//class Tile(private var value: Char) {
//
//    fun isWall() = value == '#'
//
//    fun isStart() = value == 'S'
//
//    fun isEnd() = value == 'E'
//}
//
//enum class Direction {
//    UP, DOWN, LEFT, RIGHT
//}
//
//fun findOptimalPath(maze: Maze) {
//    var minScore = Int.MAX_VALUE
//
//    var currentDirection = Direction.RIGHT
//    var currentTile = maze.getStartTile()
//
//    var path = ArrayDeque<>
//
//    while (curren)
//}
//
//fun find
//
//class Reindeer(private var map: Maze) {
//
//    private var coordinates = map.getGuardCoordinates()
//    private var direction = Direction.UP
//
//    fun step() {
//        map.get(coordinates).visit(direction)
//
//        var next = nextCoordinates()
//        while (map.contains(next) && map.get(next).isWall()) {
//            changeDirection()
//            next = nextCoordinates()
//        }
//
//        coordinates = next
//    }
//
//    fun hasLooped() = map.get(coordinates).wasVisitedInDirection(direction)
//
//    private fun nextCoordinates(): Coordinates {
//        return when (direction) {
//            Direction.UP -> coordinates.up()
//            Direction.DOWN -> coordinates.down()
//            Direction.LEFT -> coordinates.left()
//            Direction.RIGHT -> coordinates.right()
//        }
//    }
//
//    private fun changeDirection() {
//        direction = when (direction) {
//            Direction.UP -> Direction.RIGHT
//            Direction.DOWN -> Direction.LEFT
//            Direction.LEFT -> Direction.UP
//            Direction.RIGHT -> Direction.DOWN
//        }
//    }
//
//    fun isInsideLab(): Boolean {
//        return map.contains(coordinates)
//    }
//}
