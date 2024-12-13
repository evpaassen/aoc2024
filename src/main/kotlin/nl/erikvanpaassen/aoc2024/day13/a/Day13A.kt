package nl.erikvanpaassen.aoc2024.day13.a

import java.io.File
import kotlin.math.min

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

    val prize = Position(match.groupValues[1].toInt(), match.groupValues[2].toInt())

    val machine = ClawMachine(prize)

    for (i in 0..<lines.count() - 1) {
        machine.parseButton(lines[i])
    }

    return machine
}

data class Position(val x: Int = 0, val y: Int = 0) {

    fun move(x: Int, y: Int): Position {
        return Position(this.x + x, this.y + y)
    }
}

data class ClawMachine(val prize: Position) {

    private var buttons = mutableListOf<Button>()
    private var position = Position()
    private var tokens = 0


    fun reset() {
        position = Position()
        tokens = 0
    }

    fun parseButton(line: String) {
        val regex = Regex("Button ([A-B]): X\\+([0-9]+), Y\\+([0-9]+)")
        val match = regex.find(line)

        if (match == null) {
            throw IllegalStateException()
        }

        val buttonName = match.groupValues[1].toCharArray().first()

        val tokens = when (buttonName) {
            'A' -> 3
            'B' -> 1
            else -> 0
        }

        buttons.add(
            Button(
                buttonName,
                match.groupValues[2].toInt(),
                match.groupValues[3].toInt(),
                tokens
            )
        )
    }

    fun findMinimumCost(): Int {
        var minimumCost = Int.MAX_VALUE

        for (a in 0..100) {
            for (b in 0..100) {
                reset()

                for (pA in 1..a) {
                    buttons.first { b -> b.name == 'A' }.press()
                }

                for (pB in 1..b) {
                    buttons.first { b -> b.name == 'B' }.press()
                }

                if (position == prize) {
//                    println("$this : (A = $a, B= $b) @ $tokens")
                    minimumCost = min(minimumCost, tokens)
                }
            }
        }

        return if (minimumCost == Int.MAX_VALUE) 0 else minimumCost
    }

    inner class Button(val name: Char, val x: Int, val y: Int, val tokens: Int) {

        fun press() {
            this@ClawMachine.position = this@ClawMachine.position.move(x, y)
            this@ClawMachine.tokens += tokens
        }
    }
}
