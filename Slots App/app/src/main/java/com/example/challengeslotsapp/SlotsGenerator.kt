package com.example.challengeslotsapp

import kotlin.random.Random

class SlotsGenerator(private val slots: List<List<Slot>>, private val slotItems: List<Slot>) {
    fun generateRandomSlots(): List<List<Slot>> {
        val spunSlots = MutableList(slots.size) { slotItems.toMutableList() }
        slots.forEachIndexed { i, _ ->
            slots[i].forEachIndexed { j, _ ->
                spunSlots[i][j] = slotItems[Random.nextInt(slotItems.lastIndex + 1)]
            }
        }
        return spunSlots
    }
}