package nl.erikvanpaassen.aoc2024.day5b

import java.io.File

fun main() {
    val input = File("input/day5a.txt").readLines()

    val separator = input.indexOf("")

    val rules = input.subList(0, separator)
        .map(Rule::parse)

    val updates = input.subList(separator + 1, input.size)
        .map(Update::parse)

    val answer = updates.filter { u -> !u.isValid(rules) }
        .map { u -> u.fix(rules) }
        .map(Update::getMiddlePage)
        .reduce(Int::plus)

    println(answer)
}

class Rule(val before: Int, val after: Int) {

    fun isValid(update: Update): Boolean {
        val afterIndex = update.pages.indexOf(after)
        return update.pages.indexOf(before) < afterIndex || afterIndex == -1
    }

    companion object {
        fun parse(line: String): Rule {
            val split = line.split('|')
            return Rule(split[0].toInt(), split[1].toInt())
        }
    }
}

class Update(val pages: List<Int>) {

    fun isValid(rules: List<Rule>): Boolean {
        return rules.all { r -> r.isValid(this) }
    }

    fun getMiddlePage(): Int {
        return pages[pages.count() / 2]
    }

    fun fix(rules: List<Rule>): Update {
        val toSort = pages.toMutableList()
        val sorted = mutableListOf<Int>()

        while (toSort.isNotEmpty()) {
            val firstFree = toSort.first { p ->
                rules
                    .filter { r -> r.after == p }
                    .filter { r -> pages.contains(r.before) }
                    .all { r -> sorted.contains(r.before) }
            }

            sorted.add(firstFree)
            toSort.remove(firstFree)
        }

        return Update(sorted)
    }

    companion object {
        fun parse(line: String): Update {
            return Update(
                line.split(',')
                    .map(String::toInt)
            )
        }
    }
}
