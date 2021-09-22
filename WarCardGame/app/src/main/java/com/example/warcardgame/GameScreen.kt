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

@Composable
fun GameScreen() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(LightGreen, DarkGreen)))
            .padding(25.dp)
    ) {
        val width = constraints.maxWidth.dp
        val height = constraints.maxHeight.dp

        if (width > 400.dp) PortraitOrientation()
        else LandscapeOrientation()
    }
}