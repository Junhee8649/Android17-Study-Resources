package com.github.junhee8649.androidstudy17.week3.animationExample

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SpringAnimationWithColorDemo() {
    var expanded by remember { mutableStateOf(false) }

    // 박스 크기 애니메이션
    val boxSize by animateDpAsState(
        targetValue = if (expanded) 200.dp else 100.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ), label = ""
    )

    // 배경 색상 애니메이션
    val boxColor by animateColorAsState(
        targetValue = if (expanded) Color(0xFF4CAF50) else Color(0xFFF44336),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(boxSize)
            .background(boxColor)
            .clickable { expanded = !expanded },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (expanded) "확대됨" else "축소됨",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpringAnimationWithColorDemoPreview() {
    SpringAnimationWithColorDemo()
}
