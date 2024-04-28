package com.example.kutyafulke.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kutyafulke.presentation.theme.KutyafulkeTheme
import com.example.kutyafulke.presentation.ui.main.navigation.navhosts.RootNavNode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KutyafulkeTheme {
                navHostController = rememberNavController()

                RootNavNode(
                    navHostController = navHostController
                )
            }
        }
    }
}

