package com.example.hrm

import android.R.id.tabs
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hrm.screen.AnalyseScreen
import com.example.hrm.screen.ProfileScreen
import com.example.hrm.screen.RecordScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController? = null,
) {
    val items = listOf("home", "discover", "profile")
    var selectedTab by rememberSaveable { mutableIntStateOf(0) } // 当前选中的 tab
    val label = stringResource(id = R.string.app_heading)
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.value?.destination?.route
    val shouldShowBottomBar = currentDestination in items
    val shouldShowFloatingActionButton = currentDestination == "home"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(label) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController?.navigate("add")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, title ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            when (selectedTab) {
                0 -> {
                    RecordScreen(navController)
                }
                1 -> {
                    AnalyseScreen()
                }
                2 -> {
                    ProfileScreen()
                }
            }
        }
    }
}
//
//@Composable
//fun BottomNavigationBar(navController: NavController?) {
//    val items = listOf("home", "discover", "profile")
//    val currentBackStackEntry = navController?.currentBackStackEntryAsState()
//    val currentDestination = currentBackStackEntry?.value?.destination?.route
//
//    NavigationBar(
//        containerColor = MaterialTheme.colorScheme.background,
//        contentColor = MaterialTheme.colorScheme.onBackground
//    ) {
//        items.forEach { screen ->
//            NavigationBarItem(
//                selected = currentDestination == screen,
//                onClick = {
//                    navController?.navigate(screen) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                icon = {
//                    when (screen) {
//                        "home" -> Icon(Icons.Default.Home, contentDescription = null)
//                        "discover" -> Icon(Icons.Default.Search, contentDescription = null)
//                        "profile" -> Icon(Icons.Default.Person, contentDescription = null)
//                    }
//                },
//            )
//        }
//    }
//}