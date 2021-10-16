package com.yogeshpaliyal.openvideoplayer

sealed class DashboardState{
    object HideTopBar : DashboardState()
    class ShowTopBar(var title: String) : DashboardState()
}
