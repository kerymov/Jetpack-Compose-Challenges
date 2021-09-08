package com.example.challengeslotsapp

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes

data class Slot(
    val name: String,
    @DrawableRes val IconId: Int
)