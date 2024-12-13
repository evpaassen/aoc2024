package nl.erikvanpaassen.aoc2024.day13.b

import java.io.File

fun main() {
    val input = File("input/day13a.txt").readLines()

    val machines = parseLines(input)

    val minimumCost = machines.sumOf(ClawMachine::findMinimumCost)

    println(minimumCost)
}

fun parseLines(lines: List<String>): List<ClawMachine> {
    var toParse = lines

    var machines = mutableListOf<ClawMachine>()

    while (toParse.isNotEmpty()) {
        val machineLines = toParse.takeWhile{ l -> l.isNotEmpty()}
        machines.add(parseMachine(machineLines))

        toParse = toParse.drop(machineLines.count() + 1)
    }

    return machines
}

fun parseMachine(lines: List<String>): ClawMachine {
    val regex = Regex("Prize: X=([0-9]+), Y=([0-9]+)")
    val match = regex.find(lines.last())

    if (match == null) {
        throw IllegalStateException()
    }

    val prize =
        Position(match.groupValues[1].toLong() + 10000000000000L, match.groupValues[2].toLong() + 10000000000000L)

//    val prize =
//        Position(match.groupValues[1].toLong(), match.groupValues[2].toLong())

    val machine = ClawMachine(prize)

    for (i in 0..<lines.count() - 1) {
        machine.parseButton(lines[i])
    }

    return machine
}

data class Position(val x: Long = 0, val y: Long = 0)

data class ClawMachine(val prize: Position) {

    private var buttons = mutableListOf<Button>()

    fun parseButton(line: String) {
        val regex = Regex("Button ([A-B]): X\\+([0-9]+), Y\\+([0-9]+)")
        val match = regex.find(line)

        if (match == null) {
            throw IllegalStateException()
        }

        val buttonName = match.groupValues[1].toCharArray().first()

        val tokens = when (buttonName) {
            'A' -> 3L
            'B' -> 1L
            else -> 0L
        }

        buttons.add(
            Button(
                buttonName,
                match.groupValues[2].toLong(),
                match.groupValues[3].toLong(),
                tokens
            )
        )
    }

//    fun findMinimumCost(): Long {
//        var minimumCost = Long.MAX_VALUE
//
//        val buttonA = buttons.first { b -> b.name == 'A' }
//        val buttonB = buttons.first { b -> b.name == 'B' }
//
//        val maxA = min(prize.x / buttonA.x, prize.y / buttonA.y)
//
//        for (a in 0..maxA) {
//            val b = (prize.x - (a * buttonA.x)) / buttonB.x
//
//            val endPosition = Position(a * buttonA.x + b * buttonB.x, a * buttonA.y + b * buttonB.y)
//
//            if (endPosition == prize) {
//                val tokens = a * buttonA.tokens + b * buttonB.tokens
//                println("$this : (A = $a, B= $b) @ $tokens")
//                minimumCost = min(minimumCost, tokens)
//            }
//        }
//
//        return if (minimumCost == Long.MAX_VALUE) 0 else minimumCost
//    }

    fun findMinimumCost(): Long {
        val buttonA = buttons.first { b -> b.name == 'A' }
        val buttonB = buttons.first { b -> b.name == 'B' }

        println("start calc a(${buttonA.x}, ${buttonA.y}) b(${buttonB.x}, ${buttonB.y}) -> prize(${prize.x}, ${prize.y}) ")
        val bTimes = ((buttonA.x * prize.y) - (buttonA.y * prize.x)) / ((buttonA.x * buttonB.y) - (buttonA.y * buttonB.x))

        var hasSolution = ((buttonA.x * prize.y) - (buttonA.y * prize.x)) % ((buttonA.x * buttonB.y) - (buttonA.y * buttonB.x)) == 0L


        println("bTimes: $bTimes  $hasSolution")

        if (hasSolution) {
            val timesA = (prize.x - bTimes * buttonB.x) / buttonA.x

            return bTimes.toLong() * buttonB.tokens + timesA * buttonA.tokens
        }

        return 0L
    }

    inner class Button(val name: Char, val x: Long, val y: Long, val tokens: Long)
}
