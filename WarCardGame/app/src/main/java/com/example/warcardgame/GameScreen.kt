package com.example.warcardgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.warcardgame.ui.theme.DarkGreen
import com.example.warcardgame.ui.theme.LightGreen
import kotlin.random.Random

@Composable
fun GameScreen() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(LightGreen, DarkGreen)))
            .padding(25.dp)
    ) {
        var firstCard by remember {
            mutableStateOf(Card("Back", 0, R.drawable.back))
        }
        var secondCard by remember {
            mutableStateOf(Card("Back", 0, R.drawable.back))
        }
        var firstPlayerScore by remember {
            mutableStateOf(0)
        }
        var secondPlayerScore by remember {
            mutableStateOf(0)
        }

        val cardItems = listOf(
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

        val width = constraints.maxWidth.dp
        if (width > 1000.dp) {
            LandscapeOrientation(
                firstCard = firstCard,
                secondCard = secondCard,
                firstPlayerScore = firstPlayerScore,
                secondPlayerScore = secondPlayerScore,
                deal = {
                    firstCard = generateRandomCard(cardItems)
                    secondCard = generateRandomCard(cardItems)

                    val winner = check(firstCard, secondCard)
                    if (winner == Winner.FIRST_PLAYER) firstPlayerScore++
                    if (winner == Winner.SECOND_PLAYER) secondPlayerScore++
                }
            )
        } else {
            PortraitOrientation(
                firstCard = firstCard,
                secondCard = secondCard,
                firstPlayerScore = firstPlayerScore,
                secondPlayerScore = secondPlayerScore,
                deal = {
                    firstCard = generateRandomCard(cardItems)
                    secondCard = generateRandomCard(cardItems)

                    val winner = check(firstCard, secondCard)
                    if (winner == Winner.FIRST_PLAYER) firstPlayerScore++
                    if (winner == Winner.SECOND_PLAYER) secondPlayerScore++
                }
            )
        }
    }
}

private fun generateRandomCard(cardItems: List<Card>) =
    cardItems[Random.nextInt(cardItems.lastIndex + 1)]

private fun check(firstCard: Card, secondCard: Card): Winner {
    return when {
        firstCard.rank > secondCard.rank -> Winner.FIRST_PLAYER
        firstCard.rank < secondCard.rank -> Winner.SECOND_PLAYER
        else -> Winner.DRAW
    }
}