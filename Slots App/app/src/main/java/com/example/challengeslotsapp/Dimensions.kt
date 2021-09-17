package com.example.challengeslotsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class Dimensions {
    object Width: Dimensions()
    object Height: Dimensions()

    sealed class DimensionsOperator {
        object LessThen: DimensionsOperator()
        object GreaterThen: DimensionsOperator()
    }

    class DimensionsComparator(
        val operator: DimensionsOperator,
        private val dimension: Dimensions,
        val value: Dp
    ) {
        fun compare(screenWidth: Dp, screenHeight: Dp): Boolean {
            return if (dimension is Width) {
                when (operator) {
                    is DimensionsOperator.LessThen -> screenWidth < value
                    is DimensionsOperator.GreaterThen -> screenWidth > value
                }
            } else {
                when (operator) {
                    is DimensionsOperator.LessThen -> screenHeight < value
                    is DimensionsOperator.GreaterThen -> screenHeight > value
                }
            }
        }
    }
}

@Composable
fun MediaQuery(comparator: Dimensions.DimensionsComparator, content: @Composable () -> Unit) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp /
            LocalDensity.current.density
    if (comparator.compare(screenWidth, screenHeight)) {
        content()
    }
}

infix fun Dimensions.lessThen(value: Dp): Dimensions.DimensionsComparator {
    return Dimensions.DimensionsComparator(
        operator = Dimensions.DimensionsOperator.LessThen,
        dimension = this,
        value = value
    )
}

infix fun Dimensions.greaterThen(value: Dp): Dimensions.DimensionsComparator {
    return Dimensions.DimensionsComparator(
        operator = Dimensions.DimensionsOperator.GreaterThen,
        dimension = this,
        value = value
    )
}