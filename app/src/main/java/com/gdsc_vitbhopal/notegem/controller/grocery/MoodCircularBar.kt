package com.gdsc_vitbhopal.notegem.controller.grocery

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.util.grocery.Mood

@Composable
fun MoodCircularBar(
    modifier: Modifier = Modifier,
    entries: List<GroceryEntry>,
    strokeWidth: Float = 85f,
    showPercentage: Boolean = true,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(
            Modifier.clickable {
                onClick()
            }
        ) {
            val mostFrequentMood by remember(entries) {
                derivedStateOf {
                    // if multiple ones with the same frequency, return the most positive one
                    val entriesGrouped = entries
                        .groupBy { it.mood }
                    val max = entriesGrouped.maxOf { it.value.size }
                    entriesGrouped
                        .filter { it.value.size == max }
                        .maxByOrNull {
                            it.key.value
                        }?.key ?: Mood.OKAY
                }
            }
            val moods by remember(entries) {
                derivedStateOf {
                    entries.toPercentages()
                }
            }
            Text(
                text = stringResource(R.string.mood_summary),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                textAlign = TextAlign.Center
            )
            if (entries.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    var currentAngle = remember { 90f }
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(29.dp)
                    ) {
                        for ((mood, percentage) in moods) {
                            drawArc(
                                color = mood.color,
                                startAngle = currentAngle,
                                sweepAngle = percentage * 360f,
                                useCenter = false,
                                size = Size(size.width, size.width),
                                style = Stroke(strokeWidth)
                            )
                            currentAngle += percentage * 360f
                        }
                    }
                    if (showPercentage) Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for ((mood, percentage) in moods) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    stringResource(
                                        R.string.percent,
                                        (percentage * 100).toInt()
                                    )
                                )
                                Spacer(Modifier.width(8.dp))
                                Icon(
                                    painter = painterResource(mood.icon),
                                    contentDescription = stringResource(mood.title),
                                    tint = mood.color,
                                    modifier = Modifier.size(strokeWidth.dp / 3)
                                )
                            }
                        }
                    }
                }
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.your_mood_was))
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = mostFrequentMood.color
                            )
                        ) {
                            append(stringResource(mostFrequentMood.title))
                        }
                        append(stringResource(R.string.most_of_the_time))
                    },
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = stringResource(R.string.no_data_yet),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun List<GroceryEntry>.toPercentages(): Map<Mood, Float> {
    return this
        .sortedBy { it.mood.value }
        .groupingBy { it.mood }
        .eachCount()
        .mapValues { it.value / this.size.toFloat() }
}

@Composable
@Preview
fun MoodCircularBarPreview() {
    MoodCircularBar(
        entries = listOf(
            GroceryEntry(
                id = 1,
                mood = Mood.AWESOME
            ),
            GroceryEntry(
                id = 1,
                mood = Mood.AWESOME
            ),
            GroceryEntry(
                id = 2,
                mood = Mood.GOOD,
            ),
            GroceryEntry(
                id = 3,
                mood = Mood.OKAY,
            ),
            GroceryEntry(
                id = 3,
                mood = Mood.OKAY,
            ),
            GroceryEntry(
                id = 4,
                mood = Mood.BAD,
            ),
            GroceryEntry(
                id = 5,
                mood = Mood.TERRIBLE,
            ),
            GroceryEntry(
                id = 5,
                mood = Mood.TERRIBLE,
            )
        )
    )
}