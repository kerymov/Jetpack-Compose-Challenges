package com.example.challengeslotsapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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

@ExperimentalFoundationApi
@Composable
fun GameScreen() {
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
            Credits()
            Slots(
                listOf(
                    Slot("Apple", R.drawable.ic_apple),
                    Slot("Cherry", R.drawable.ic_avocado),
                    Slot("Star", R.drawable.ic_grapes)
                )
            )
            Spin()
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
    slots: List<Slot>
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(slots.size) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(White50)
                    .padding(10.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(slots[it].IconId),
                    contentDescription = slots[it].name
                )
            }
        }
    }
}

@Composable
fun Spin() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
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
        GameScreen()
    }
}