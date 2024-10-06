package com.taskyproject.tasky.presentation.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.taskyproject.tasky.ui.theme.DarkGray
import com.taskyproject.tasky.ui.theme.LightBlue
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.MediumGray
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun ImageBox(
    onExitClick: () -> Unit,
    image: Uri,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    Box {
        Image(
            modifier = Modifier
                .height(dimensions.size60)
                .width(dimensions.size60)
                .border(
                    width = dimensions.size2,
                    color = LightBlue
                ),
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Card(
            shape = CircleShape,
            modifier = modifier
                .size(dimensions.size20)
                .offset(x = dimensions.size50, y = dimensions.minus10),
            colors = CardDefaults.cardColors(
                containerColor = MediumGray
            )
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = DarkGray,
                modifier = Modifier.clickable { onExitClick() })
        }
    }
}

@Preview
@Composable
fun ImageBoxPreview() {
    TaskyTheme {
        ImageBox(onExitClick = {  }, image = Uri.EMPTY)
    }
}