package com.rma.catalist.core.compose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector


@ExperimentalMaterial3Api
    @Composable
    fun CatalistAppTopBar(
        modifier: Modifier = Modifier,
        text: String,
        navigationIcon: ImageVector? = null,
        navigationOnClick: (() -> Unit)? = null,
        actionIcon: ImageVector? = null,
        actionOnClick: (() -> Unit)? = null,
    ): Unit{
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        navigationIcon = {
            if (navigationIcon != null) {
                AppTopBarIcon(
                    icon = navigationIcon,
                    onClick = { navigationOnClick?.invoke() },
                )
            }
        },
        title = {
            Text(text = text)
        },
        actions = {
            if (actionIcon != null) {
                AppTopBarIcon(
                    icon = actionIcon,
                    onClick = { actionOnClick?.invoke() }
                )
            }
        },

    )
}

