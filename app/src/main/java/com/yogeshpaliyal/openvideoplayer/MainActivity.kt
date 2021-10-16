package com.yogeshpaliyal.openvideoplayer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.yogeshpaliyal.openvideoplayer.ui.folders.FolderList
import com.yogeshpaliyal.openvideoplayer.ui.list.VideosListData
import com.yogeshpaliyal.openvideoplayer.ui.player.VideoDetail
import com.yogeshpaliyal.openvideoplayer.ui.theme.OpenVideoPlayerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder

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

                    var dashboardState by remember {
                        mutableStateOf<DashboardState>(DashboardState.ShowTopBar(""))
                    }

                    Scaffold(topBar = {
                        val state = dashboardState
                        if (state is DashboardState.ShowTopBar) {
                            TopAppBar(title = {
                                Text(text = state.title)
                            })
                        }
                    }) {
                        NavHost(navController = navController, startDestination = "folders") {
                            composable("folders") {
                                dashboardState = DashboardState.ShowTopBar("Folders")
                                FolderList{ item ->
                                    navController.navigate(
                                        "folders/${
                                            item.id
                                        }?folderName=${item.name}"
                                    )
                                }
                            }
                            composable("folders/{folderID}?folderName={folderName}",
                                arguments = listOf(navArgument("folderID") {
                                    type = NavType.StringType
                                }, navArgument("folderName"){ defaultValue = "" })
                            ) { backstackEntry ->
                                val folderId = backstackEntry.arguments?.getString("folderID")
                                val folderName = backstackEntry.arguments?.getString("folderName") ?: ""
                                folderId ?: return@composable

                                dashboardState = DashboardState.ShowTopBar(folderName)
                                VideosListData(folderId) { item ->
                                    navController.navigate(
                                        "videos/${
                                            URLEncoder.encode(
                                                item.contentId.toString(),
                                                "UTF-8"
                                            )
                                        }"
                                    )
                                }
                            }
                            composable(
                                "videos/{video}",
                                arguments = listOf(navArgument("video") { type = NavType.StringType })
                            ) { backstackEntry ->
                                dashboardState = DashboardState.HideTopBar

                                val video = Uri.parse(backstackEntry.arguments?.getString("video"))
                                Log.d("VideoDetail", "onCreate: $video")
                                VideoDetail(video)
                            }

                        }
                    }
                }
            }
        }
    }
}