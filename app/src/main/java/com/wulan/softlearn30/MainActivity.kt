package com.wulan.softlearn30

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.wulan.softlearn30.R
import com.wulan.softlearn30.model.SoftLearnRepository
import com.wulan.softlearn30.ui.theme.SoftLearnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoftLearnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SoftLearnApp()
                }
            }
        }
    }

    @Composable
    fun SoftLearnApp() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar()
            }
        ) {
            val days = SoftLearnRepository.days
            SoftLearnList(days = days, contentPadding = it)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(modifier: Modifier = Modifier) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.top_bar),
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.displayMedium,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(id = R.color.ungu)
            ),
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun SoftLearnPreview() {
        SoftLearnTheme {
            SoftLearnApp()
        }
    }
}
