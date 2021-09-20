package com.example.challengeslotsapp.interactors

import com.example.challengeslotsapp.Slot
import kotlin.random.Random

class SlotsGenerator(
    private val slots: List<List<Slot>>,
    private val slotItems: List<Slot>
) {
    fun generateRandomSlots() = List(slots.size) {
        List(slots.size) { slotItems[Random.nextInt(slotItems.lastIndex + 1)] }
    }
}