package com.canerture.ecommercecm.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {

    MaterialTheme(
        typography = Typography,
        content = {
            Surface(content = content)
        }
    )
}