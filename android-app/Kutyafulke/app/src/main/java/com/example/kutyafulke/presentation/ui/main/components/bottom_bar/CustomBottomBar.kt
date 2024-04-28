package com.example.kutyafulke.presentation.ui.main.components.bottom_bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kutyafulke.R
import com.example.kutyafulke.presentation.ui.main.components.bottom_bar.components.CustomNavigationBar
import com.example.kutyafulke.presentation.ui.main.navigation.routes.MainScreenRoute

@Composable
fun CustomBottomBar(
    navHostController: NavHostController,
    onFloatingActionButtonClick: () -> Unit,
) {
    val navBarItems = listOf(
        NavigationItem(
            title = stringResource(R.string.home_navigation_item_label),
            route = MainScreenRoute.HOME,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = stringResource(R.string.breeds_navigation_item_label),
            route = MainScreenRoute.BREEDS,
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        CustomNavigationBar(
            navHostController = navHostController,
            navBarItems,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        FloatingActionButton(
            onClick = onFloatingActionButtonClick,
            shape = CircleShape,
            elevation = elevation(0.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 20.dp)
                .size(80.dp)
        ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "") }
    }
}

data class NavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean = false,
    val badgeCount: Int? = null
)

@Preview
@Composable
fun CustomBottomBarPreview() {
    CustomBottomBar(navHostController = rememberNavController(), onFloatingActionButtonClick = {})
}