package nl.erikvanpaassen.aoc2024.day14.b

import java.io.File

fun main() {
    val space = Space(101, 103)

    File("input/day14a.txt").readLines().forEach { l -> space.addRobot(l) }

    var seconds = 0L
    while (space.robots.map(Space.Robot::position).distinct().count() < space.robots.count()) {
        space.robots.forEach { r -> r.move(1) }
        seconds++
    }

    println("$seconds seconds:")
    space.print(false)
    println()
}

class Space(val width: Int, val height: Int) {

    val robots = mutableListOf<Robot>()

    fun addRobot(line: String) {
        val regex = Regex("p=([0-9-]+),([0-9-]+) v=([0-9-]+),([0-9-]+)")
        val match = regex.find(line)

        if (match == null) throw IllegalStateException()

        val robot = Robot(Position(match.groupValues[1].toInt(), match.groupValues[2].toInt()), match.groupValues[3].toInt(), match.groupValues[4].toInt())
        robots.add(robot)
    }

    fun print(quadrants: Boolean) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (quadrants && (x == width / 2 || y == height / 2)) {
                    print(' ')
                } else {
                    val numberOfRobots = robots.map(Robot::position).count { p -> p.x == x && p.y == y }

                    if (numberOfRobots == 0) {
                        print(' ')
                    } else {
                        print('#')
                    }
                }
            }
            println()
        }
    }

    inner class Robot(var position: Position, val velocityX: Int, val velocityY: Int) {
        fun move(times: Int) {
            position = Position(
                wrapAround(position.x + velocityX * times, width),
                wrapAround(position.y + velocityY * times, height)
            )
        }

        private fun wrapAround(p: Int, limit: Int): Int {
            return (p + 10000 * limit) % limit
        }

        override fun toString(): String {
            return "Robot(position=$position, velocityX=$velocityX, velocityY=$velocityY)"
        }
    }
}

data class Position(val x: Int, val y: Int)
