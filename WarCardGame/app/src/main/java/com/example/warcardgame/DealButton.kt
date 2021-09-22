package com.example.warcardgame

import kotlin.random.Random

class DealButton() {
    private val cardItems = listOf(
        Card("Card 2", 2, R.drawable.card2),
        Card("Card 3", 3, R.drawable.card3),
        Card("Card 4", 4, R.drawable.card4),
        Card("Card 5", 5, R.drawable.card5),
        Card("Card 6", 6, R.drawable.card6),
        Card("Card 7", 7, R.drawable.card7),
        Card("Card 8", 8, R.drawable.card8),
        Card("Card 9", 9, R.drawable.card9),
        Card("Card 10", 10, R.drawable.card10),
        Card("Card 11", 11, R.drawable.card11),
        Card("Card 12", 12, R.drawable.card12),
        Card("Card 13", 13, R.drawable.card13),
        Card("Card 14", 14, R.drawable.card14)
    )

    fun deal(): Winner {
        val firstCard = generateRandomCard(cardItems)
        val secondCard = generateRandomCard(cardItems)

        return when(check(firstCard, secondCard)) {
            1 -> Winner.FIRST_PLAYER
            2 -> Winner.SECOND_PLAYER
            else -> Winner.DRAW
        }
    }

    private fun generateRandomCard(cardItems: List<Card>) =
        cardItems[Random.nextInt(cardItems.lastIndex + 1)]

    private fun check(firstCard: Card, secondCard: Card): Int {
        return when {
            firstCard.rank > secondCard.rank -> 1
            firstCard.rank < secondCard.rank -> 2
            else -> 0
        }
    }
}