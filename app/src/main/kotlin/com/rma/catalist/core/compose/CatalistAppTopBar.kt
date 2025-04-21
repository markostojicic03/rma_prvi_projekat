package com.rma.catalist.core.compose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
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
        isSearching: Boolean = false,
        searchQuery: String = "",
        onSearchQueryChange: ((String) -> Unit)? = null,
    ){
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { onSearchQueryChange?.invoke(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    singleLine = true,
                    placeholder = { Text("Search breed...") } ,
                    trailingIcon = {
                        IconButton(onClick = {
                            onSearchQueryChange?.invoke("")
                            actionOnClick?.invoke()

                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                )
            } else {
                Text(text)
            }
        },
        navigationIcon = {
            IconButton(onClick = { navigationOnClick?.invoke() }) {
                Icon(
                    imageVector = navigationIcon!!,
                    contentDescription = "Menu",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified // onemogućava automatsko skaliranje unutar 24dp
                )
            }
        },
        actions = {
            if (actionIcon != null && !isSearching) {
                AppTopBarIcon(
                    icon = actionIcon,
                    onClick = { actionOnClick?.invoke() }
                )
            }
        }
    )
}

