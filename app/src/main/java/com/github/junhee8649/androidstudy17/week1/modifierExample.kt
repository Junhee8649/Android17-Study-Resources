package com.github.junhee8649.androidstudy17.week1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.junhee8649.androidstudy17.ui.theme.AndroidStudy17Theme

@Composable
fun modifierExample(){
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 예제 1: padding -> background 순서
        // 결과: 패딩이 적용된 영역 전체에 배경색이 적용됨
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.Red)
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("패딩 → 배경", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 예제 2: background -> padding 순서
        // 결과: 배경이 적용된 후에 패딩이 적용됨 (배경색 바깥에 투명한 여백)
        Box(
            modifier = Modifier
                .background(Color.Blue)
                .padding(8.dp)
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("배경 → 패딩", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifierExamplePreview() {
    AndroidStudy17Theme {
        modifierExample()
    }
}