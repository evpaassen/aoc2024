package nl.erikvanpaassen.aoc2024.day14.a

import java.io.File

fun main() {
    val space = Space(101, 103)
    // Example: val space = Space(11, 7)

    File("input/day14a.txt").readLines().forEach { l -> space.addRobot(l) }

    space.print(false)
    println()

    space.robots.forEach { r -> r.move(100) }

    space.print(true)
    println()

    println(space.getSafetyFactor())
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

    fun getSafetyFactor(): Int {
        val robotPositions = robots.map(Robot::position)

        val q1 = robotPositions.count { p -> p.x < (width / 2) && p.y < (height / 2) }
        val q2 = robotPositions.count { p -> p.x > (width / 2) && p.y < (height / 2) }
        val q3 = robotPositions.count { p -> p.x < (width / 2) && p.y > (height / 2) }
        val q4 = robotPositions.count { p -> p.x > (width / 2) && p.y > (height / 2) }

        println("q1 = $q1, q2 = $q2, q3 = $q3, q4 = $q4")

        return q1 * q2 * q3 * q4
    }

    fun print(quadrants: Boolean) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (quadrants && (x == width / 2 || y == height / 2)) {
                    print(' ')
                } else {
                    val numberOfRobots = robots.map(Robot::position).count { p -> p.x == x && p.y == y }

                    if (numberOfRobots == 0) {
                        print('.')
                    } else {
                        print(numberOfRobots)
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
