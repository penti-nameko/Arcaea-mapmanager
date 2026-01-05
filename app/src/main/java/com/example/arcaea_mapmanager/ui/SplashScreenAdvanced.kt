package com.example.arcaea_mapmanager.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreenAdvanced(onTimeout: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }

    // 無限ループアニメーション
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // フェードインアニメーション
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(1000),
        label = "alpha"
    )

    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2D1B69),
                        Color(0xFF1A1A2E),
                        Color(0xFF000000)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // 背景の回転する円
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .alpha(alphaAnim.value * 0.3f)
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.minDimension / 2

            // 外側のリング
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF9D4EDD),
                        Color(0xFF7B2CBF),
                        Color(0xFF5A189A),
                        Color(0xFF3C096C),
                        Color(0xFF9D4EDD)
                    ),
                    center = Offset(centerX, centerY)
                ),
                radius = radius * 0.9f,
                style = Stroke(width = 3f)
            )

            // 中間のリング
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF10002B),
                        Color(0xFF240046),
                        Color(0xFF3C096C),
                        Color(0xFF10002B)
                    ),
                    center = Offset(centerX, centerY)
                ),
                radius = radius * 0.7f,
                style = Stroke(width = 2f)
            )

            // 回転する点（星のような効果）
            for (i in 0..11) {
                val angle = (rotationAngle + i * 30) * Math.PI / 180
                val x = centerX + cos(angle).toFloat() * radius * 0.85f
                val y = centerY + sin(angle).toFloat() * radius * 0.85f

                drawCircle(
                    color = Color.White.copy(alpha = 0.8f),
                    radius = 4f,
                    center = Offset(x, y)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .scale(scaleAnim.value)
                .alpha(alphaAnim.value)
        ) {
            // メインタイトル
            Text(
                text = "ARCAEA",
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 8.sp,
                modifier = Modifier.scale(pulseScale)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 装飾ライン
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(2.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF9D4EDD),
                                Color.Transparent
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // サブタイトル
            Text(
                text = "MAP MANAGER",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFFB8B8B8),
                letterSpacing = 6.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            // ローディングドット
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    val dotAlpha by infiniteTransition.animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "dot$index"
                    )

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .alpha(dotAlpha)
                            .background(
                                color = Color(0xFF9D4EDD),
                                shape = androidx.compose.foundation.shape.CircleShape
                            )
                    )
                }
            }
        }
    }
}