package com.example.githubuserlist.ui

enum class Screen {
    HOME, DETAIL
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Detail : NavigationItem(Screen.DETAIL.name)
}