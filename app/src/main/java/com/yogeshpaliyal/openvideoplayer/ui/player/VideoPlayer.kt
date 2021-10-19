package com.yogeshpaliyal.openvideoplayer.ui.player

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        }
    }


        AndroidView(modifier = Modifier.fillMaxSize(), factory = { viewContext ->
            PlayerView(viewContext).apply {
                Log.d(TAG, "VideoDetail: PlayerView factory")
                player = exoPlayer
                exoPlayer.playWhenReady = true
                useController = false
                setOnClickListener {
                    if (it.visibility == View.VISIBLE) {
                        // hide buttons
                        controllerState = View.GONE
                        Log.d(TAG, "VideoDetail: visibility change gone")
                    } else {
                        // show buttons
                        Log.d(TAG, "VideoDetail: visibility change visible")
                        controllerState = View.VISIBLE
                    }
                }
                exoPlayer.prepare()
            }
        })

    Controller(controllerState = controllerState)
}


@Composable
fun Controller(controllerState : Int){
    Log.d(TAG, "Controller: composed $controllerState")
    if(controllerState == View.VISIBLE){
        // visible
        Text(text = "Contolls Visible")
    }else{
        // hide controller
        Text(text = "Contolls Gone")
    }
}