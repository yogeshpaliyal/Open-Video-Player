package com.yogeshpaliyal.openvideoplayer.ui.player

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


const val TAG = "VideoPlayerTag"

@Composable
fun VideoDetail(videoUri: Uri) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    var controllerState by remember {
        mutableStateOf(View.VISIBLE)
    }

    var progress by remember {
        mutableStateOf(0)
    }

    Log.d(TAG, "VideoDetail: composed")

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember(context) {
        Log.d(TAG, "VideoDetail: create exoplayer")
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    MediaItem.fromUri(
                        videoUri
                    )
                )

            this.setMediaSource(source)
            this.prepare()
        }
    }


    Player(exoPlayer)

    /*Controller(controllerState = controllerState) {
        controllerState = it
    }*/
}

@Composable
fun Player(exoPlayer: SimpleExoPlayer) {
    Log.d(TAG, "Player: Compose")

    AndroidView(modifier = Modifier.fillMaxSize(), factory = { viewContext ->
        StyledPlayerView(viewContext).apply {
            Log.d(TAG, "Player: PlayerView factory")
            player = exoPlayer
            useController = false
            setControllerVisibilityListener {
                Log.d(TAG, "Player: Controller visibility change")
            }
            exoPlayer.playWhenReady = true
        }
    }, update = {
        Log.d(TAG, "Player: PlayerView update")
    })
}


@Composable
fun Controller(controllerState: Int, changeControllerState: (Int) -> Unit) {
    Log.d(TAG, "Controller: composed $controllerState")

    val backgroundColor by remember(controllerState) {
        mutableStateOf(if(controllerState == View.VISIBLE) Color(0x80000000) else Color(0x00000000))
    }


    Box(modifier = Modifier.fillMaxSize().background(backgroundColor).clickable {
        changeControllerState(if (controllerState == View.VISIBLE) View.GONE else View.VISIBLE)
    }) {

    }

    if (controllerState == View.VISIBLE) {
        // visible
        Text(text = "Contolls Visible")
    } else {
        // hide controller
        Text(text = "Contolls Gone")
    }
}