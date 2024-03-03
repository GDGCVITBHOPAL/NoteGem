package com.gdsc_vitbhopal.notegem.controller.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.ui.theme.Blue
import com.gdsc_vitbhopal.notegem.ui.theme.ModerateBlueCard


@Composable
fun DashboardCard(
    title: String,
    image: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.padding(10.dp),
        shape = RoundedCornerShape(25.dp),
        backgroundColor = backgroundColor,
        elevation = 10.dp
    ) {
        Column(
            Modifier
                .clickable { onClick() }
                .padding(17.dp)
                .aspectRatio(1.0f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.h6.copy(color = Color.Black))
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.End),
                painter = painterResource(id = image),
                contentDescription = title)

        }
    }
}

@Composable
fun DashboardTallCard(
    title: String,
    image: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.padding(10.dp),
        shape = RoundedCornerShape(25.dp),
        backgroundColor = backgroundColor,
        elevation = 10.dp
    ) {
        Column(
            Modifier
                .clickable { onClick() }
                .padding(17.dp)
                .aspectRatio(0.7f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.h6.copy(color = Color.Black))
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.End),
                painter = painterResource(id = image),
                contentDescription = title)

        }
    }
}

@Composable
fun DashboardWideCard(
    title: String,
    image: Int,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(25.dp),
        backgroundColor = backgroundColor,
        elevation = 20.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(17.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6.copy(color = Color.Black),
            )
            Spacer(Modifier.height(12.dp))
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.End),
                painter = painterResource(id = image),
                contentDescription = title)

        }
    }
}

@Preview
@Composable
fun DashboardCardPreview() {
    Column {
        DashboardTallCard("Notes", R.drawable.notes_logo, Blue)
        DashboardWideCard("Bookmarks", R.drawable.bookmarks_logo, ModerateBlueCard)
    }
}