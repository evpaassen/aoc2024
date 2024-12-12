package nl.erikvanpaassen.aoc2024.day12.b

import java.io.File

fun main() {
    val input = File("input/day12a.txt").readLines()

    val map = PlotMap.new(input)

    println(map.getTotalFencingPrice())
}

data class Coordinates(val y: Int, val x: Int) {
    fun up() = Coordinates(y - 1, x)
    fun down() = Coordinates(y + 1, x)
    fun left() = Coordinates(y, x - 1)
    fun right() = Coordinates(y, x + 1)
}

class PlotMap(private val grid: Array<Array<Plot>>) {

    fun get(coordinates: Coordinates) = grid[coordinates.y][coordinates.x]


    fun contains(coordinates: Coordinates): Boolean {
        return coordinates.y >= 0 && coordinates.y < grid.count() && coordinates.x >= 0 && coordinates.x < grid[0].count()
    }

    fun allCoordinates(): List<Coordinates> {
        return grid.indices.flatMap { y -> grid[y].indices.map { x -> Coordinates(x, y) } }
    }

    fun getTotalFencingPrice(): Long {
        val stack = ArrayDeque<Pair<Coordinates, Price>>(allCoordinates().map { c -> Pair(c, Price()) })
        val allPrices = stack.map { (_, p) -> p }

        while (!stack.isEmpty()) {
            val (coords, price) = stack.removeLast()

            val plot = get(coords)
            if (plot.visited) {
                continue
            }
            plot.visited = true

            var corners = 0
            val directNeighbours = getDirectNeighbourPlots(coords)
            val surroundingNeighbours = getSurroundingNeighbourPlots(coords)

            // Outside corners
            if (!directNeighbours.contains(coords.up()) && !directNeighbours.contains(coords.left())) {
                corners++
            }
            if (!directNeighbours.contains(coords.up()) && !directNeighbours.contains(coords.right())) {
                corners++
            }
            if (!directNeighbours.contains(coords.down()) && !directNeighbours.contains(coords.left())) {
                corners++
            }
            if (!directNeighbours.contains(coords.down()) && !directNeighbours.contains(coords.right())) {
                corners++
            }

            // Inside corners
            if (directNeighbours.containsAll(listOf(coords.up(), coords.left())) && !surroundingNeighbours.contains(coords.up().left())) {
                corners++
            }
            if (directNeighbours.containsAll(listOf(coords.up(), coords.right())) && !surroundingNeighbours.contains(coords.up().right())) {
                corners++
            }
            if (directNeighbours.containsAll(listOf(coords.down(), coords.left())) && !surroundingNeighbours.contains(coords.down().left())) {
                corners++
            }
            if (directNeighbours.containsAll(listOf(coords.down(), coords.right())) && !surroundingNeighbours.contains(coords.down().right())) {
                corners++
            }

            price.addPlot(corners)

            stack.addAll(directNeighbours.map { n -> Pair(n, price) })
        }

        return allPrices.sumOf { p -> p.getPrice() }
    }

    private fun getDirectNeighbourPlots(coordinates: Coordinates): List<Coordinates> {
        val value = get(coordinates).value

        return listOf(
            coordinates.up(),
            coordinates.down(),
            coordinates.left(),
            coordinates.right(),
        )
            .filter(::contains)
            .filter { neighbour -> get(neighbour).value == value }
    }

    private fun getSurroundingNeighbourPlots(coordinates: Coordinates): List<Coordinates> {
        val value = get(coordinates).value

        return listOf(
            coordinates.up().left(),
            coordinates.up().right(),
            coordinates.down().left(),
            coordinates.down().right()
        )
            .filter(::contains)
            .filter { p -> get(p).value == value }
    }

    class Plot(val value: Char) {
        var visited = false
    }

    class Price(var totalArea: Long = 0L, var totalSides: Long = 0L) {

        fun addPlot(side: Int) {
            totalArea++
            totalSides += side
        }

        fun getPrice(): Long {
            return totalArea * totalSides
        }
    }

    companion object {
        fun new(lines: List<String>): PlotMap {
            return PlotMap(lines.map { line -> line.toCharArray().map(::Plot).toTypedArray() }.toTypedArray())
        }
    }
}
