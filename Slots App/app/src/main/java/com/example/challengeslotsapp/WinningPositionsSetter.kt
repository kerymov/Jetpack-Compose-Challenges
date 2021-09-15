package com.example.challengeslotsapp

class WinningPositionsSetter(private val wonLines: List<Pair<Int, Int>>, private val spunSlots: List<List<Slot>>) {
    fun setWinningPositions(): List<List<Slot>> {
        val checkedSlots = spunSlots.toMutableList().map { it.toMutableList() }
        wonLines.forEach { indexes ->
            val i = indexes.first
            val j = indexes.second
            checkedSlots[i][j] = spunSlots[i][j].copy(isWin = true)
        }
        return checkedSlots
    }
}