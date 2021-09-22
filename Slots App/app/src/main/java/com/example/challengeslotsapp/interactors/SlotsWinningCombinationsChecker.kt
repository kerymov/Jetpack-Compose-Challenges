package com.example.challengeslotsapp.interactors

import com.example.challengeslotsapp.R
import com.example.challengeslotsapp.Slot

class SlotsWinningCombinationsChecker(private val slots: List<List<Slot>>) {

    fun checkLine() = checkRows(slots)

    fun checkSquare(): Pair<List<Pair<Int, Int>>, Int> {
        val (wonRows, amountOfWonRows) = checkRows(slots)
        val (wonColumns, amountOfWonColumns) = checkColumns(slots)
        val (wonDiagonals, amountOfWonDiagonals) = checkDiagonals(slots)

        val wonLines = (wonRows + wonColumns + wonDiagonals).distinct()
        val amountOfWonLines = amountOfWonRows + amountOfWonColumns + amountOfWonDiagonals

        return Pair(wonLines, amountOfWonLines)
    }

    private fun checkRows(
        spunSlots: List<List<Slot>>,
        isRotated: Boolean = false
    ): Pair<List<Pair<Int, Int>>, Int> {
        val wonSlots: MutableList<Pair<Int, Int>> = mutableListOf()
        var amountOfWonLines = 0
        spunSlots.forEachIndexed { i, row ->
            if (row.distinctBy { it.name }.size == 1) {
                row.forEachIndexed { j, _ ->
                    wonSlots.add(
                        if (isRotated) Pair(j, i) else Pair(i, j)
                    )
                    amountOfWonLines++
                }
            }
        }
        wonSlots.toList()

        return Pair(wonSlots, amountOfWonLines)
    }

    private fun checkColumns(spunSlots: List<List<Slot>>) = checkRows(rotate(spunSlots), true)

    private fun checkDiagonals(spunSlots: List<List<Slot>>): Pair<List<Pair<Int, Int>>, Int> {
        val wonSlots: MutableList<Pair<Int, Int>> = mutableListOf()
        var amountOfWonLines = 0
        val diagonalsInRows =
            MutableList(2) { MutableList(spunSlots.size) { Slot("Apple", R.drawable.ic_apple) } }

        for (i in spunSlots.indices) {
            diagonalsInRows[0][i] = spunSlots[i][i]
            diagonalsInRows[1][i] = spunSlots[spunSlots.lastIndex - i][i]
        }

        if (diagonalsInRows[0].distinctBy { it.name }.size == 1) {
            amountOfWonLines++
            for (i in spunSlots.indices) {
                wonSlots.add(Pair(i, i))
            }
        }
        if (diagonalsInRows[1].distinctBy { it.name }.size == 1) {
            amountOfWonLines++
            for (i in spunSlots.indices) {
                wonSlots.add(Pair(spunSlots.lastIndex - i, i))
            }
        }
        wonSlots.toList()

        return Pair(wonSlots, amountOfWonLines)
    }

    private fun rotate(spunSlots: List<List<Slot>>) = spunSlots.mapIndexed { i, row ->
        row.mapIndexed { j, _ -> spunSlots[j][i] }
    }
}