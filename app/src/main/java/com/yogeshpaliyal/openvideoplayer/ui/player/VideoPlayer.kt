package com.yogeshpaliyal.openvideoplayer.ui.player

import android.net.Uri
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.yogeshpaliyal.openvideoplayer.data.Video
import javax.sql.DataSource


@Composable
fun VideoDetail(videoUri: Uri){
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.packageName))

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                    // Big Buck Bunny from Blender Project
                        videoUri
                ))

            this.setMediaSource(source)
        }
    }

    AndroidView(factory = { viewContext ->
        PlayerView(viewContext).apply {
            player = exoPlayer
            exoPlayer.playWhenReady = true
            exoPlayer.prepare()
        }
    })

}