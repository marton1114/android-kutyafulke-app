package com.example.kutyafulke.presentation.ui.main.components.bottom_bar.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kutyafulke.presentation.ui.main.components.bottom_bar.NavigationItem
import com.example.kutyafulke.presentation.ui.main.navigation.routes.NavNodeRoute
import com.example.kutyafulke.presentation.ui.main.navigation.routes.MainScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigationBar(
    navHostController: NavHostController,
    navBarItems: List<NavigationItem>?,
    modifier: Modifier = Modifier
) {
    var selectedNavBarItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    navHostController.addOnDestinationChangedListener { _, navDestination, _ ->
        if (navDestination.route != navBarItems?.get(selectedNavBarItemIndex)?.route) {
            if (navDestination.route == navBarItems?.get(0)?.route) {
                selectedNavBarItemIndex = 0
            } else {
                selectedNavBarItemIndex = 1
            }
        }
    }

    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        navBarItems?.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = (selectedNavBarItemIndex == index),
                onClick = {
                    selectedNavBarItemIndex = index
                    if (item.route != MainScreenRoute.HOME) {
                        navHostController.navigate(item.route) {
                            popUpTo(NavNodeRoute.AUTH)
                        }
                    } else {
                        navHostController.popBackStack(item.route, false)
                    }
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if(item.badgeCount != null) {
                                Badge {
                                    Text(item.badgeCount.toString())
                                }
                            } else if(item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector =
                            if (index == selectedNavBarItemIndex) {
                                item.selectedIcon
                            } else
                                item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }


}