package com.example.challengeslotsapp

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

data class Slot(
    val name: String,
    @DrawableRes val IconId: Int,
    val win: Boolean = false
)