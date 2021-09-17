package com.example.challengeslotsapp

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.challengeslotsapp.interactors.SlotsGenerator
import com.example.challengeslotsapp.interactors.SlotsWinningCombinationsChecker
import com.example.challengeslotsapp.interactors.WinningPositionsSetter
import com.example.challengeslotsapp.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChallengeSlotsAppTheme {
                val lineMode = GameMode("Line", 1, 3)
                val squareMode = GameMode("Square", 3, 3)
                GameScreen(lineMode)
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun GameScreen(
        gameMode: GameMode
    ) {
        val slotItems = listOf(
            Slot("Apple", R.drawable.ic_apple),
            Slot("Avocado", R.drawable.ic_avocado),
            Slot("Grapes", R.drawable.ic_grapes)
        )

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
        ) {
            val systemUiController = rememberSystemUiController()
            systemUiController.isStatusBarVisible = false
            LaunchedEffect(key1 = systemUiController.isStatusBarVisible) {
                delay(1000)
                systemUiController.isStatusBarVisible = false
            }

            MediaQuery(Dimensions.Width lessThen 500.dp) {
                PortraitOrientation(
                    gameMode = gameMode,
                    slotItems = slotItems
                )
            }
            MediaQuery(Dimensions.Width greaterThen 499.dp) {
                LandscapeOrientation(
                    gameMode = gameMode,
                    slotItems = slotItems
                )
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun PortraitOrientation(
        gameMode: GameMode,
        slotItems: List<Slot>
    ) {
        val rows = gameMode.rows
        val columns = gameMode.columns
        var slots by remember {
            mutableStateOf(List(gameMode.rows) { slotItems })
        }
        var credits by remember {
            mutableStateOf(1000)
        }
        val maxWin = 70
        val minWin = 10

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Title()
            Credits(credits)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Slots(slots, rows, columns)
            }
            Spin(
                spin = {
                    val spunSlots = SlotsGenerator(slots, slotItems).generateRandomSlots()

                    val (wonSlotsCoordinates, amountOfWonLines) =
                        if (gameMode.name == "Line") SlotsWinningCombinationsChecker(spunSlots).checkLine()
                        else SlotsWinningCombinationsChecker(spunSlots).checkSquare()

                    slots = WinningPositionsSetter(
                        wonSlotsCoordinates,
                        spunSlots
                    ).setWinningPositions()

                    credits -= 100
                    credits += amountOfWonLines * (Random.nextInt(maxWin - minWin) + minWin)
                }
            )
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun LandscapeOrientation(
        gameMode: GameMode,
        slotItems: List<Slot>
    ) {
        val rows = gameMode.rows
        val columns = gameMode.columns
        var slots by remember {
            mutableStateOf(List(gameMode.rows) { slotItems })
        }
        var credits by remember {
            mutableStateOf(1000)
        }
        val maxWin = 70
        val minWin = 10

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f),
                contentAlignment = Alignment.Center
            ) {
                Slots(slots, rows, columns)
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Title(isLandscape = true)
                Credits(credits = credits)
                Spin(
                    spin = {
                        val spunSlots = SlotsGenerator(slots, slotItems).generateRandomSlots()

                        val (wonSlotsCoordinates, amountOfWonLines) =
                            if (gameMode.name == "Line") SlotsWinningCombinationsChecker(spunSlots).checkLine()
                            else SlotsWinningCombinationsChecker(spunSlots).checkSquare()

                        slots = WinningPositionsSetter(
                            wonSlotsCoordinates,
                            spunSlots
                        ).setWinningPositions()

                        credits -= 100
                        credits += amountOfWonLines * (Random.nextInt(maxWin - minWin) + minWin)
                    }
                )
            }
        }
    }


    @Composable
    private fun Title(
        isLandscape: Boolean = false
    ) {
        Row(
            horizontalArrangement = if (isLandscape) Arrangement.Start
            else Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(White50)
                .padding(5.dp)
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
                color = TextBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
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
    private fun Credits(
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
    private fun Slots(
        slots: List<List<Slot>>,
        rows: Int,
        columns: Int
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(columns)
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
    private fun Spin(
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
    private fun DefaultPreview() {
        ChallengeSlotsAppTheme {
            GameScreen(GameMode("Square", 3, 3))
        }
    }
}