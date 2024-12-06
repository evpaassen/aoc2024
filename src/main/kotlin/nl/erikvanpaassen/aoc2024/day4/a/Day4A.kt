package nl.erikvanpaassen.aoc2024.day4.a

import nl.erikvanpaassen.aoc2024.day4.b.isInBounds
import java.io.File

fun main() {
    val input = File("input/day4a.txt").readLines()

    val grid = Array(input.count()) { "".toCharArray() }
    input.forEachIndexed { index, line -> run { grid[index] = line.toCharArray() } }

    var xmases = 0

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            xmases += findXmases(grid, x, y)
        }
    }

    println(xmases)
}

fun findXmases(grid: Array<CharArray>, x: Int, y: Int): Int {
    if (grid[y][x] != 'X') {
        return 0
    }

    var xmases = 0

    // Naar rechts
    if (isInBounds(grid, y, x + 3) && grid[y][x + 1] == 'M' && grid[y][x + 2] == 'A' && grid[y][x + 3] == 'S') {
        xmases++
    }

    // Naar links
    if (isInBounds(grid, y, x - 3) && grid[y][x - 1] == 'M' && grid[y][x - 2] == 'A' && grid[y][x - 3] == 'S') {
        xmases++
    }

    // Naar onder
    if (isInBounds(grid, y + 3, x) && grid[y + 1][x] == 'M' && grid[y + 2][x] == 'A' && grid[y + 3][x] == 'S') {
        xmases++
    }

    // Naar boven
    if (isInBounds(grid, y - 3, x) && grid[y - 1][x] == 'M' && grid[y - 2][x] == 'A' && grid[y - 3][x] == 'S') {
        xmases++
    }

    // Naar rechtsonder
    if (isInBounds(
            grid,
            y + 3,
            x + 3
        ) && grid[y + 1][x + 1] == 'M' && grid[y + 2][x + 2] == 'A' && grid[y + 3][x + 3] == 'S'
    ) {
        xmases++
    }

    // Naar rechtsboven
    if (isInBounds(
            grid,
            y - 3,
            x + 3
        ) && grid[y - 1][x + 1] == 'M' && grid[y - 2][x + 2] == 'A' && grid[y - 3][x + 3] == 'S'
    ) {
        xmases++
    }

    // Naar linksonder
    if (isInBounds(
            grid,
            y + 3,
            x - 3
        ) && grid[y + 1][x - 1] == 'M' && grid[y + 2][x - 2] == 'A' && grid[y + 3][x - 3] == 'S'
    ) {
        xmases++
    }

    // Naar linksboven
    if (isInBounds(
            grid,
            y - 3,
            x - 3
        ) && grid[y - 1][x - 1] == 'M' && grid[y - 2][x - 2] == 'A' && grid[y - 3][x - 3] == 'S'
    ) {
        xmases++
    }

    return xmases
}

fun isInBounds(grid: Array<CharArray>, x: Int, y: Int): Boolean {
    return y >= 0 && y < grid.size && x >= 0 && x < grid[y].size
}
