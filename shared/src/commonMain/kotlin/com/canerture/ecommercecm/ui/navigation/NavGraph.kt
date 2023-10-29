package com.canerture.ecommercecm.ui.navigation

import androidx.compose.runtime.Composable
import com.canerture.ecommercecm.ui.home.HomeRoute
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun NavGraph(navigator: Navigator = rememberNavigator()) {
    NavHost(
        navigator = navigator,
        initialRoute = "/home",
    ) {
        scene("/home") {
            HomeRoute {

            }
        }
    }
}