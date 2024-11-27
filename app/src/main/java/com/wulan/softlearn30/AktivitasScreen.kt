package com.wulan.softlearn30

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wulan.softlearn30.R
import com.wulan.softlearn30.model.Day
import com.wulan.softlearn30.model.SoftLearnRepository
import com.wulan.softlearn30.ui.theme.SoftLearnTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SoftLearnList(
    days: List<Day>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(days) { index, day ->
                DayListItem(
                    day = day,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun DayListItem(
    day: Day,
    modifier: Modifier = Modifier
) {
    val isDescriptionVisible = remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .sizeIn(minHeight = 72.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(day.nameRes),
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(day.titleRes),
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable {

                            isDescriptionVisible.value = !isDescriptionVisible.value
                        }
                ) {
                    Image(
                        painter = painterResource(day.imageRes),
                        contentDescription = null,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = isDescriptionVisible.value,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }), // Animasi masuk
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }) // Animasi keluar
                ) {
                    Text(
                        text = stringResource(day.descriptionRes),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroPreview() {
    val day = Day(
        R.string.day1,
        R.string.title1,
        R.string.description1,
        R.drawable.day1
    )
    SoftLearnTheme {
        DayListItem(day = day)
    }
}

@Preview("Days List")
@Composable
fun SoftLearnPreview() {
    SoftLearnTheme(darkTheme = false) {
        Surface (
            color = MaterialTheme.colorScheme.background
        ) {
            SoftLearnList(days = SoftLearnRepository.days)
        }
    }
}
