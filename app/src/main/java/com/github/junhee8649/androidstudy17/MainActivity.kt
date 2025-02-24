package com.github.junhee8649.androidstudy17

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.github.junhee8649.androidstudy17.ui.theme.AndroidStudy17Theme
import com.github.junhee8649.androidstudy17.week6.RaceTrackerApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            AndroidStudy17Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RaceTrackerApp()
                }
            }

        }
    }
}