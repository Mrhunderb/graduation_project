package com.example.hrm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hrm.screen.AnalyseScreen
import com.example.hrm.screen.ProfileScreen
import com.example.hrm.screen.record.AddBloodScreen
import com.example.hrm.screen.record.AddEcgScreen
import com.example.hrm.screen.record.AddGeneralScreen
import com.example.hrm.screen.record.AddLiverScreen
import com.example.hrm.screen.record.AddRecordScreen
import com.example.hrm.screen.record.AddUrineScreen
import com.example.hrm.screen.record.AddXrayScreen
import com.example.hrm.screen.record.RecordSelectScreen
import com.example.hrm.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable("discover") { AnalyseScreen() }
                    composable("profile") { ProfileScreen() }
                    composable("add") { AddRecordScreen(navController) }
                    composable("add_select/{id}") {
                        val id = it.arguments?.getString("id")
                        RecordSelectScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("add_blood/{id}") {
                        val id = it.arguments?.getString("id")
                        AddBloodScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("add_urine/{id}") {
                        val id = it.arguments?.getString("id")
                        AddUrineScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("add_general/{id}") {
                        val id = it.arguments?.getString("id")
                        AddGeneralScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("add_ecg/{id}") {
                        val id = it.arguments?.getString("id")
                        AddEcgScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("add_xray/{id}") {
                        val id = it.arguments?.getString("id")
                        AddXrayScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("add_liver/{id}") {
                        val id = it.arguments?.getString("id")
                        AddLiverScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                }
            }
        }
    }
}
