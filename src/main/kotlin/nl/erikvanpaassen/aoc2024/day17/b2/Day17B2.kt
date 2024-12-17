package nl.erikvanpaassen.aoc2024.day17.b2

import java.io.File
import kotlin.math.pow

fun main() {
    val input = File("input/day17b-example.txt").readLines()

    val inputComputer = Computer.parse(input)

    for (i in 0..100) {
        val c = Computer(i, inputComputer.registerB, inputComputer.registerC, inputComputer.program)
        println(c.run().joinToString(","))
    }
    return

//    println(computer.output.joinToString(","))
}

class Computer(var registerA: Int, var registerB: Int, var registerC: Int, var program: List<Int>) {

    var instructionPointer = 0

    var output = mutableListOf<Int>()


    fun run(): List<Int> {
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

        return output
    }

    private fun increaseInstructionPointer() {
        instructionPointer += 2
    }

    // Opcode 0: adv
    private fun adv(operand: Int) {
//        println("adv before $registerA op: $operand")
        registerA = registerA / 2.0.pow(comboOperand(operand)).toInt()
//        println("adv after $registerA")
        increaseInstructionPointer()
    }

    // Opcode 1: bxl
    private fun bxl(operand: Int) {
        registerB = registerB.xor(operand)
        increaseInstructionPointer()
    }

    // Opcode 2: bst
    private fun bst(operand: Int) {
        registerB = comboOperand(operand) % 8
        increaseInstructionPointer()
    }

    // Opcode 3: jnz
    private fun jnz(operand: Int) {
        if (registerA == 0) {
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
        output.add(comboOperand(operand) % 8)
        increaseInstructionPointer()
    }

    // Opcode 6: bdv
    private fun bdv(operand: Int) {
        registerB = registerA / 2.0.pow(comboOperand(operand)).toInt()
        increaseInstructionPointer()
    }

    // Opcode 7: (reserved)
    private fun cdv(operand: Int) {
        registerC = registerA / 2.0.pow(comboOperand(operand)).toInt()
        increaseInstructionPointer()
    }

    private fun comboOperand(operand: Int): Int {
        return when (operand) {
            0, 1, 2, 3 -> operand
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> throw IllegalArgumentException("Operand is reserved")
        }
    }

    companion object {
        fun parse(input: List<String>): Computer {
            val registerA = input.first { l -> l.startsWith("Register A: ") }.substring("Register A: ".length).toInt()
            val registerB = input.first { l -> l.startsWith("Register B: ") }.substring("Register B: ".length).toInt()
            val registerC = input.first { l -> l.startsWith("Register C: ") }.substring("Register C: ".length).toInt()

            val program = input.first { l -> l.startsWith("Program: ") }.substring("Program: ".length).split(',')
                .map(String::toInt)

            return Computer(registerA, registerB, registerC, program)
        }
    }
}
