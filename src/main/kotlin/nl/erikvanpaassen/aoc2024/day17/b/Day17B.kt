package nl.erikvanpaassen.aoc2024.day17.b

import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("input/day17a.txt").readLines()

    val inputComputer = Computer.parse(input)

//    for (i in 0..100L) {
//        val c = Computer(i, inputComputer.registerB, inputComputer.registerC, inputComputer.program)
//        println(c.run().joinToString(","))
//    }
//    return

    var power = 0
    var value = 0L
    var digit = 0L
    while (true) {
        val testValue = value + digit * 8.0.pow(power).toLong()
        val c = Computer(testValue, inputComputer.registerB, inputComputer.registerC, inputComputer.program)

        val output = c.run()
        if (output == inputComputer.program) {
            println(testValue)
            return
        } else if (inputComputer.program.count() < output.count()) {
            println("NOT FOUND")
            return
        } else if (inputComputer.program.subList(inputComputer.program.count() - (power + 1), inputComputer.program.count()) == output) {
            value = testValue
            digit = 0
            power++
        }

        digit++
    }
}

class Computer(var registerA: Long, var registerB: Long, var registerC: Long, var program: List<Int>) {

    var instructionPointer = 0

    var output = mutableListOf<Int>()


    fun run(): List<Int> {
        output.clear()

        while (program.indices.contains(instructionPointer)) {
            val instruction = program[instructionPointer]
            val operand = program[instructionPointer + 1]

            when (instruction) {
                0 -> adv(operand)
                1 -> bxl(operand)
                2 -> bst(operand)
                3 -> jnz(operand)
                4 -> bxc(operand)
                5 -> out(operand)
                6 -> bdv(operand)
                7 -> cdv(operand)
            }
        }

        return output.toList()
    }

    private fun increaseInstructionPointer() {
        instructionPointer += 2
    }

    // Opcode 0: adv
    private fun adv(operand: Int) {
//        println("adv before $registerA op: $operand")
        registerA = registerA / 2.0.pow(comboOperand(operand).toDouble()).toLong()
//        println("adv after $registerA")
        increaseInstructionPointer()
    }

    // Opcode 1: bxl
    private fun bxl(operand: Int) {
        registerB = registerB.xor(operand.toLong())
        increaseInstructionPointer()
    }

    // Opcode 2: bst
    private fun bst(operand: Int) {
        registerB = comboOperand(operand) % 8
        increaseInstructionPointer()
    }

    // Opcode 3: jnz
    private fun jnz(operand: Int) {
        if (registerA == 0L) {
            increaseInstructionPointer()
        } else {
            instructionPointer = operand
        }
    }

    // Opcode 4: bxc
    private fun bxc(operand: Int) {
        registerB = registerB.xor(registerC)
        increaseInstructionPointer()
    }

    // Opcode 5: out
    private fun out(operand: Int) {
        output.add((comboOperand(operand) % 8).toInt())
        increaseInstructionPointer()
    }

    // Opcode 6: bdv
    private fun bdv(operand: Int) {
        registerB = registerA / 2.0.pow(comboOperand(operand).toDouble()).toLong()
        increaseInstructionPointer()
    }

    // Opcode 7: (reserved)
    private fun cdv(operand: Int) {
        registerC = registerA / 2.0.pow(comboOperand(operand).toDouble()).toLong()
        increaseInstructionPointer()
    }

    private fun comboOperand(operand: Int): Long {
        return when (operand) {
            0, 1, 2, 3 -> operand.toLong()
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> throw IllegalArgumentException("Operand is reserved")
        }
    }

    companion object {
        fun parse(input: List<String>): Computer {
            val registerA = input.first { l -> l.startsWith("Register A: ") }.substring("Register A: ".length).toLong()
            val registerB = input.first { l -> l.startsWith("Register B: ") }.substring("Register B: ".length).toLong()
            val registerC = input.first { l -> l.startsWith("Register C: ") }.substring("Register C: ".length).toLong()

            val program = input.first { l -> l.startsWith("Program: ") }.substring("Program: ".length).split(',')
                .map(String::toInt)

            return Computer(registerA, registerB, registerC, program)
        }
    }
}
