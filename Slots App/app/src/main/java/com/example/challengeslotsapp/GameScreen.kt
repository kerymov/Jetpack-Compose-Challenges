package com.example.challengeslotsapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengeslotsapp.ui.theme.*
import kotlin.random.Random

fun generateRandomSlots(slots: List<List<Slot>>, slotItems: List<Slot>): List<List<Slot>> {
    val spunSlots = MutableList(slots.size) { slotItems.toMutableList() }
    slots.forEachIndexed { i, _ ->
        slots[i].forEachIndexed { j, _ ->
            spunSlots[i][j] = slotItems[Random.nextInt(slotItems.lastIndex + 1)]
        }
    }
    return spunSlots
}

fun setWinningPositions(wonLines: List<Pair<Int, Int>>, spunSlots: List<List<Slot>>): List<List<Slot>> {
    val checkedSlots = spunSlots.toMutableList().map { it.toMutableList() }
    wonLines.forEach { indexes ->
        val i = indexes.first
        val j = indexes.second
        checkedSlots[i][j] = spunSlots[i][j].copy(isWin = true)
    }
    return checkedSlots
}

@ExperimentalFoundationApi
@Composable
fun GameScreen(
    gameMode: GameMode
) {
    val rows = gameMode.rows
    val columns = gameMode.columns

    val slotItems = listOf(
        Slot("Apple", R.drawable.ic_apple),
        Slot("Avocado", R.drawable.ic_avocado),
        Slot("Grapes", R.drawable.ic_grapes)
    )
    var slots by remember {
        mutableStateOf(List(rows) { slotItems })
    }
    var credits by remember {
        mutableStateOf(1000)
    }
    val maxWin = 70
    val minWin = 10

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange)
            .drawBehind {
                rotate(degrees = 50f) {
                    drawLine(
                        color = LightYellow,
                        start = Offset(size.width * 0.5f, size.height * 1.5f),
                        end = Offset(size.width * 0.5f, size.height * -1.5f),
                        strokeWidth = 330.dp.toPx()
                    )
                }
            }
            .padding(horizontal = 15.dp, vertical = 50.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Title()
            Credits(credits)
            Slots(slots, columns, rows)
            Spin(
                spin = {
                    val spunSlots = generateRandomSlots(slots, slotItems)

                    val (wonSlotsCoordinates, amountOfWonLines) = SlotsWinningCombinationsChecker(spunSlots).check()
                    slots = setWinningPositions(wonSlotsCoordinates, spunSlots)

                    credits -= 100
                    credits += amountOfWonLines * (Random.nextInt(maxWin - minWin) + minWin)
                }
            )
        }
    }
}

@Composable
fun Title() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "Star",
            tint = YellowStar,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "Slots App",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextWhite,
            modifier = Modifier.padding(horizontal = 6.dp)
        )
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = "Star",
            tint = YellowStar,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun Credits(
    credits: Int = 1000
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .background(White50)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = "Credits: $credits",
            color = TextBlack
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun Slots(
    slots: List<List<Slot>>,
    rows: Int,
    columns: Int
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(rows * columns) {
            val row = it / 3
            val column = it % 3
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (slots[row][column].isWin) Green50 else White50
                    )
                    .padding(10.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(slots[row][column].IconId),
                    contentDescription = slots[row][column].name
                )
            }
        }
    }
}

@Composable
fun Spin(
    spin: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .clickable(onClick = spin)
            .background(ButtonPink)
            .padding(horizontal = 25.dp, vertical = 5.dp)
    ) {
        Text(
            text = "Spin",
            color = TextWhite,
            fontSize = 18.sp
        )
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChallengeSlotsAppTheme {
        GameScreen(GameMode(3, 1))
    }
}