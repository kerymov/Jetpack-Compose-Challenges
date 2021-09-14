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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengeslotsapp.ui.theme.*
import java.lang.Math.PI
import java.util.Collections.swap
import kotlin.random.Random

fun rotate(slots: MutableList<MutableList<Slot>>): MutableList<MutableList<Slot>> {
    slots.forEachIndexed { i, row ->
        for (j in i..row.lastIndex) {
            val temp = slots[i][j]
            slots[i][j] = slots[j][i]
            slots[j][i] = temp
        }
    }

    return slots
}

fun checkRows(slots: MutableList<MutableList<Slot>>): MutableList<MutableList<Slot>> {
    slots.forEachIndexed { i, row ->
        if (row.distinctBy { it.name }.size == 1) {
            row.forEachIndexed { j, _ ->
                slots[i][j] = slots[i][j].copy(win = true)
            }
        }
    }
    return slots
}

fun checkColumns(slots: MutableList<MutableList<Slot>>) = rotate(checkRows(rotate(slots)))

fun checkDiagonals(slots: MutableList<MutableList<Slot>>): MutableList<MutableList<Slot>> {
    val diagonalsInRows = MutableList(2) { MutableList(slots.size) { Slot("Apple", R.drawable.ic_apple) } }

    for (i in slots.indices) {
        diagonalsInRows[0][i] = slots[i][i]
        diagonalsInRows[1][i] = slots[slots.lastIndex - i][i]
    }

    if (diagonalsInRows[0].distinctBy { it.name }.size == 1) {
        for (i in slots.indices) {
            slots[i][i] = slots[i][i].copy(win = true)
        }
    }
    if (diagonalsInRows[1].distinctBy { it.name }.size == 1) {
        for (i in slots.indices) {
            slots[slots.lastIndex - i][i] = slots[slots.lastIndex - i][i].copy(win = true)
        }
    }
    return slots
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
        mutableStateOf(MutableList(rows) { slotItems.toMutableList() })
    }
    var credits by remember {
        mutableStateOf(1000)
    }
    val maxWin = 500
    val minWin = 50

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
                    val spunSlots = MutableList(rows) { slotItems.toMutableList() }
                    slots.forEachIndexed { i, _ ->
                        slots[i].forEachIndexed { j, _ ->
                            spunSlots[i][j] = slotItems[Random.nextInt(slotItems.lastIndex + 1)]
                        }
                    }
                    slots = spunSlots
                    slots = checkRows(slots)
                    slots = checkColumns(slots)
                    slots = checkDiagonals(slots)
//                    credits += if (result) Random.nextInt(maxWin - minWin) + minWin else -100
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
                        if (slots[row][column].win) Green50 else White50
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