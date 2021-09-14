package com.example.challengeslotsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.challengeslotsapp.ui.theme.ChallengeSlotsAppTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeSlotsAppTheme {
                val lineMode = GameMode(3, 1)
                val squareMode = GameMode(3, 3)
                GameScreen(squareMode)
            }
        }
    }
}