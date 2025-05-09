package com.example.hrm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hrm.screen.AnalyseScreen
import com.example.hrm.screen.ProfileScreen
import com.example.hrm.screen.record.AddBloodScreen
import com.example.hrm.screen.record.AddRecordScreen
import com.example.hrm.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") { TestScreen(navController) }
                    composable("discover") { AnalyseScreen() }
                    composable("profile") { ProfileScreen() }
                    composable("add") {
                        AddRecordScreen(navController)
                    }
                    composable("add_blood") {
                        AddBloodScreen(
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
