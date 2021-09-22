package com.example.warcardgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
fun CardsPanel(
    firstCard: Card,
    secondCard: Card
) {
    Row(
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
fun DealButtonView(deal: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.dealbutton),
        contentDescription = "Deal button",
        modifier = Modifier.clickable { deal() },
        alignment = Alignment.Center
    )
}

@Composable
fun ScorePanel(
    firstPlayerScore: Int,
    secondPlayerScore: Int
) {
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