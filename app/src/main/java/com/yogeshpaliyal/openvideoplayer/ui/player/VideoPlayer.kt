package com.yogeshpaliyal.openvideoplayer.ui.player

import android.net.Uri
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


@Composable
fun VideoDetail(videoUri: Uri) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                        // Big Buck Bunny from Blender Project
                        videoUri
                    )
                )

            this.setMediaSource(source)
        }
    }


    AndroidView(modifier = Modifier.fillMaxSize(), factory = { viewContext ->
        PlayerView(viewContext).apply {
            player = exoPlayer
            exoPlayer.playWhenReady = true
            setControllerVisibilityListener { visibility ->
                if(visibility == View.VISIBLE){
                    // show buttons
                }else{
                    // hide buttons
                }
            }
            exoPlayer.prepare()
        }
    })

}