package com.example.hrm

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hrm.screen.AnalyseScreen
import com.example.hrm.screen.ProfileScreen
import com.example.hrm.screen.RecordScreen
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf("home", "discover", "profile")
    val label = stringResource(id = R.string.app_heading)

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
                 onClick = { /* TODO */ },
                 containerColor = MaterialTheme.colorScheme.primary,
             ) {
                 Icon(Icons.Default.Add, contentDescription = "Add")
             }
        },
        bottomBar = {
            NavigationBar (
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = navController.currentDestination?.route == screen,
                        onClick = {
                            navController.navigate(screen) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            when (screen) {
                                "home" -> Icon(Icons.Default.Home, contentDescription = null)
                                "discover" -> Icon(Icons.Default.Search, contentDescription = null)
                                "profile" -> Icon(Icons.Default.Person, contentDescription = null)
                            }
                        },
                        label = { Text(screen.capitalize(Locale.ROOT)) }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("home") { RecordScreen() }
            composable("discover") { AnalyseScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}
