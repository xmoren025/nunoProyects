package upvictoria.pm_may_ago_2025.iti_271415.pi1u1.z_u2_moreno_ledesma_ximena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import upvictoria.pm_may_ago_2025.iti_271415.pi1u1.z_u2_moreno_ledesma_ximena.ui.theme.Z_U2_MORENO_LEDESMA_XIMENATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Z_U2_MORENO_LEDESMA_XIMENATheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    UnitCircleAnimation()
                }
            }
        }
    }
}


@Composable
fun UnitCircleAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val radius = 100f
    val amplitude = 80f

    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Gráficas a la izquierda (2/3)
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.Center
        ) {

            // Gráfica de Coseno
            Text("cos θ", color = Color.Red, fontWeight = FontWeight.Bold)
            Box(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clipToBounds()
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val centerY = height / 2
                    val cycles = 2

                    // Dibujar la onda de coseno
                    val path = Path().apply {
                        moveTo(0f, centerY)
                        for (x in 0..width.toInt()) {
                            val theta = (x / width) * cycles * 2 * Math.PI.toFloat() + angle
                            lineTo(x.toFloat(), centerY - (cos(theta) * amplitude))
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color.Red,
                        style = Stroke(width = 2f)
                    )

                    // Punto que sigue la onda
                    val currentY = centerY - (cos(angle) * amplitude)
                    drawCircle(
                        color = Color.Red,
                        radius = 8f,
                        center = Offset(width, currentY)
                    )
                }
            }
            HorizontalDivider(thickness = 2.dp)
            // Gráfica de Seno
            Text("sin θ", color = Color.Blue, fontWeight = FontWeight.Bold)
            Box(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clipToBounds()
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val centerY = height / 2
                    val cycles = 2

                    // Dibujar la onda de seno
                    val path = Path().apply {
                        moveTo(0f, centerY)
                        for (x in 0..width.toInt()) {
                            val theta = (x / width) * cycles * 2 * Math.PI.toFloat() + angle
                            lineTo(x.toFloat(), centerY - (sin(theta) * amplitude))
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color.Blue,
                        style = Stroke(width = 2f)
                    )

                    // Punto que sigue la onda (posición actual)
                    val currentY = centerY - (sin(angle) * amplitude)
                    drawCircle(
                        color = Color.Red,
                        radius = 8f,
                        center = Offset(width, currentY)
                    )
                }
            }

        }
        VerticalDivider(thickness = 2.dp)
        // Círculo a la derecha (1/3)
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(250.dp)) {

            }
            HorizontalDivider(thickness = 2.dp)
            Box(modifier = Modifier.size(250.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val pointX = center.x + radius * cos(angle)
                    val pointY = center.y + radius * sin(angle)

                    // Círculo unitario
                    drawCircle(
                        color = Color.Gray,
                        radius = radius,
                        center = center,
                        style = Stroke(width = 2f)
                    )

                    // Ejes X e Y
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(center.x - radius - 30, center.y),
                        end = Offset(center.x + radius + 30, center.y),
                        strokeWidth = 1f
                    )
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(center.x, center.y - radius - 30),
                        end = Offset(center.x, center.y + radius + 30),
                        strokeWidth = 1f
                    )

                    // Radio (línea azul desde centro a punto)
                    drawLine(
                        color = Color.Blue,
                        start = center,
                        end = Offset(pointX, pointY),
                        strokeWidth = 2f
                    )

                    // Punto móvil en el círculo
                    drawCircle(
                        color = Color.Red,
                        radius = 10f,
                        center = Offset(pointX, pointY)
                    )




                    // Línea VERTICAL (conexión a gráfica Coseno (la de arriba))
                    drawLine(
                        color = Color.Red.copy(alpha = 0.5f),
                        start = Offset(pointX, center.y),
                        end = Offset(pointX, 0f),
                        strokeWidth = 1.5f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f), 0f)
                    )

                    // Línea HORIZONTAL (conexión a gráfica seno)
                    drawLine(
                        color = Color.Blue.copy(alpha = 0.5f),
                        start = Offset(center.x, pointY),
                        end = Offset(0f, pointY),
                        strokeWidth = 1.5f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f), 0f)
                    )

                    // Etiqueta θ
                    drawContext.canvas.nativeCanvas.drawText(
                        "θ = ●",
                        center.x + radius * 0.7f,
                        center.y - radius * 0.7f,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 30f
                        }
                    )
                }
            }

        }

    }
}