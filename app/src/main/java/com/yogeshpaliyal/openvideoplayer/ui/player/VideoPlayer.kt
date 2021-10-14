package com.yogeshpaliyal.openvideoplayer.ui.player

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.yogeshpaliyal.openvideoplayer.data.Video


@Composable
fun VideoDetail(navController: NavController, video: Video?){
    video?.videoName?.let { Text(text = it) } ?: navController.popBackStack()
}