package com.example.hrm

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hrm.screen.user.AddUserScreen
import com.example.hrm.screen.AnalyseScreen
import com.example.hrm.screen.PdfViewScreen
import com.example.hrm.screen.ProfileScreen
import com.example.hrm.screen.record.AddBloodScreen
import com.example.hrm.screen.record.AddEcgScreen
import com.example.hrm.screen.record.AddGeneralScreen
import com.example.hrm.screen.record.AddLiverScreen
import com.example.hrm.screen.record.AddRecordScreen
import com.example.hrm.screen.record.AddUrineScreen
import com.example.hrm.screen.record.AddXrayScreen
import com.example.hrm.screen.record.RecordSelectScreen
import com.example.hrm.screen.user.ModifyUserScreen
import com.example.hrm.ui.theme.MyAppTheme
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController,
                    startDestination = "home",
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(0)) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(0)) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(0)) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(0)) }
                ) {
                    composable("home") { HomeScreen(navController) }
                    composable("add_user") { AddUserScreen(navController) }
                    composable("edit_user/{id}") {
                        val id = it.arguments?.getString("id")
                        ModifyUserScreen(navController, id?.toLongOrNull() ?: 0L)
                    }
                    composable("discover") { AnalyseScreen() }
                    composable("profile") { ProfileScreen(navController) }
                    composable("add") { AddRecordScreen(navController) }
                    composable(
                        route = "add_select/{id}/{isModify}",
                        arguments = listOf(
                            navArgument("id") { type = NavType.LongType },
                            navArgument("isModify") { type = NavType.BoolType }
                        )

                    ) {
                        val id = it.arguments?.getLong("id") ?: 0L
                        val isModify = it.arguments?.getBoolean("isModify") == true
                        RecordSelectScreen(navController, id, isModify)
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
                    composable(
                        "pdf_view/{uri}",
                        arguments = listOf(navArgument("uri") { type = NavType.StringType })
                    ) {
                        val uriString = it.arguments?.getString("uri")
                        val uri = Uri.decode(uriString).toUri()
                        PdfViewScreen(uri, navController)
                    }
                }
            }
        }
    }
}
