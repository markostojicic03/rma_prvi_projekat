package com.rma.catalist.core.compose


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import coil3.Image


@OptIn(ExperimentalMaterial3Api::class)
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
) {
    TopAppBar(
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
                    placeholder = { Text("Search breed...") },
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
                Text(
                    text = text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        navigationIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navigationOnClick?.invoke() }) {
                    Icon(if(navigationIcon == Icons.AutoMirrored.Filled.ArrowBack){Icons.AutoMirrored.Filled.ArrowBack}else{Icons.Default.Menu}, contentDescription = "Menu")
                }
                if (navigationIcon != null && navigationIcon != Icons.AutoMirrored.Filled.ArrowBack) {
                    Image(
                        imageVector = navigationIcon,
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(40.dp) // prilagodi po potrebi
                            .padding(start = 4.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                }
            }
        },
        actions = {
            if (actionIcon != null && !isSearching) {
                IconButton(onClick = { actionOnClick?.invoke() }) {
                    Icon(imageVector = actionIcon, contentDescription = "Action")
                }
            }
        },
    )
}


@Composable
fun DrawerContent(
    onItemClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Options",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        NavigationDrawerItem(
            label = { Text("Edit Profile") },
            selected = false,
            onClick = { onItemClick("edit_profile") }
        )
        NavigationDrawerItem(
            label = { Text("Start Quiz") },
            selected = false,
            onClick = { onItemClick("start_quiz") }
        )
        NavigationDrawerItem(
            label = { Text("Leaderboard") },
            selected = false,
            onClick = { onItemClick("leaderboard") }
        )
    }
}