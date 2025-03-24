package com.example.hrm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrm.screen.HomeScreen
import com.example.hrm.screen.ProfileScreen
import com.example.hrm.screen.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun HomeScreenDrawer() {

    val scope = rememberCoroutineScope()

    var screenState by remember { mutableStateOf(Screen.Home) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    fun openDrawer() {
        scope.launch {
            drawerState.open()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectScreen = screenState,
                onScreenSelected = {
                    screenState = it
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenContents(
                selectScreen = screenState,
                modifier = Modifier.weight(1f),
                onCloseDrawer = ::openDrawer,
            )
        }
    }
}

@Composable
private fun ScreenContents(
    selectScreen: Screen,
    modifier: Modifier = Modifier,
    onCloseDrawer: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (selectScreen) {
            Screen.Home -> HomeScreen(
                onDrawerClicked = onCloseDrawer
            )
            Screen.Profile -> ProfileScreen()
            Screen.Settings -> SettingsScreen()
        }
    }
}

@Composable
private fun DrawerContent(
    selectScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "选择页面", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Screen.Companion.entries.forEach { screen ->
            NavigationDrawerItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = screen == selectScreen,
                onClick = { onScreenSelected(screen) }
            )
        }
    }
}

private enum class Screen(val title: String, val icon: ImageVector) {
    Home("首页", Icons.Default.Home),
    Profile("睡眠", Icons.Default.Star),
    Settings("设置", Icons.Default.Settings);

    companion object {
        val entries = listOf(Home, Profile, Settings)
    }
}

