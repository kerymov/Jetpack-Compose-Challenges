package com.example.warcardgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Logo() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = "Logo")
    }
}

@Composable
fun CardsPanel() {
    var firstCard by remember {
        mutableStateOf(Card("Back", 0, R.drawable.back))
    }
    var secondCard by remember {
        mutableStateOf(Card("Back", 0, R.drawable.back))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardView(firstCard)
        Spacer(Modifier.width(12.dp))
        CardView(secondCard)
    }
}

@Composable
fun CardView(card: Card) {
    Box(
        modifier = Modifier
            .shadow(3.dp)
    ) {
        Image(
            painter = painterResource(card.cardId),
            contentDescription = card.name
        )
    }
}

@Composable
fun DealButtonView() {
    val dealButton = DealButton()

    Image(
        painter = painterResource(R.drawable.dealbutton),
        contentDescription = "Deal button",
        modifier = Modifier.clickable { dealButton.deal() },
        alignment = Alignment.Center
    )
}

@Composable
fun ScorePanel() {
    var firstPlayerScore by remember {
        mutableStateOf(0)
    }
    var secondPlayerScore by remember {
        mutableStateOf(0)
    }
    var currentWinner by remember {
        mutableStateOf(Winner.DRAW)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScoreView(
            playerName = "Player",
            score = firstPlayerScore
        )
        ScoreView(
            playerName = "CPU",
            score = secondPlayerScore
        )
    }

    if (currentWinner == Winner.FIRST_PLAYER) firstPlayerScore++
    if (currentWinner == Winner.SECOND_PLAYER) secondPlayerScore++

}

@Composable
fun ScoreView(
    playerName: String,
    score: Int
) {
    Column {
        Text(
            text = playerName,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "$score",
            color = Color.White,
            fontWeight = FontWeight.SemiBold)
    }
}