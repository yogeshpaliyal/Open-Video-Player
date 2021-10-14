package com.yogeshpaliyal.openvideoplayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.yogeshpaliyal.openvideoplayer.ui.list.VideosList
import com.yogeshpaliyal.openvideoplayer.ui.player.VideoDetail
import com.yogeshpaliyal.openvideoplayer.ui.theme.OpenVideoPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenVideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "videos") {
                        composable("videos") {
                            VideosList(navController)
                        }
                        composable(
                            "videos/{video}",
                            arguments = listOf(navArgument("video") { type = NavType.StringType })
                        ) { backstackEntry ->
                            val video = Uri.parse(backstackEntry.arguments?.getString("video"))
                            Log.d("VideoDetail", "onCreate: $video")
                            VideoDetail(navController,video)
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpenVideoPlayerTheme {
        Greeting("Android")
    }
}