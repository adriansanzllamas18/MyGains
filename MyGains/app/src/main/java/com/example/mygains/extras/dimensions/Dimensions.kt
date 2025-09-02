package com.example.mygains.extras.dimensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Dimensions {


    enum class WindowSize {
        Compact,
        Medium,
        Expanded;

        companion object {
            fun fromWidth(widthDp: Int): WindowSize {
                return when {
                    widthDp < 480 -> Compact
                    widthDp < 720 -> Medium
                    else -> Expanded
                }
            }

        }
    }

    data class AppDimensions(
        val padding: Dp,
        val spacing: Dp,
        val cardHeight: Dp,
        //val fontTitle: TextUnit,
        //val fontBody: TextUnit,
    ) {
        companion object {
            fun fromWindowSize(windowSize: WindowSize): AppDimensions {
                return when (windowSize) {
                    WindowSize.Compact -> AppDimensions(
                        padding = 8.dp,
                        spacing = 4.dp,
                        cardHeight = 120.dp
                    )
                    WindowSize.Medium -> AppDimensions(
                        padding = 16.dp,
                        spacing = 8.dp,
                        cardHeight = 140.dp
                    )
                    WindowSize.Expanded -> AppDimensions(
                        padding = 24.dp,
                        spacing = 12.dp,
                        cardHeight = 150.dp
                    )
                }
            }
        }
    }

}



