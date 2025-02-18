package cat.itb.m78.exercices.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import m78exercices.composeapp.generated.resources.Res
import m78exercices.composeapp.generated.resources.fondo_estrellas_difuso
import m78exercices.composeapp.generated.resources.trivial
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScreenMenu(changeToSettings: () -> Unit, changeToGameplay: () -> Unit)
{
    val menuImg = painterResource(Res.drawable.trivial)
    val backgroundImg = painterResource(Res.drawable.fondo_estrellas_difuso)
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF1B1F3B))
            .paint(backgroundImg,
                contentScale = ContentScale.Crop,
                alpha = 0.15f)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(0.45f),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    painter = menuImg,
                    ""

                )
            }
            AestheticButton(text = "New Game", onClick = { changeToGameplay() })
            AestheticButton(text = "Settings", onClick = { changeToSettings() })
        }
    }
}

@Composable
fun AestheticButton(text: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val backgroundColor by animateColorAsState(
        targetValue = if (isHovered) Color(0xFF0EF6CC) else Color.White, label = "buttonColor"
    )
    val textColor by animateColorAsState(
        targetValue = if (isHovered) Color.Black else Color(0xFF1B1F3B), label = "textColor"
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .hoverable(interactionSource)
            .width(200.dp)
            .height(50.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}