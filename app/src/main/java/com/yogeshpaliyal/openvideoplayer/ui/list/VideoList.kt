package com.yogeshpaliyal.openvideoplayer.ui.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.yogeshpaliyal.openvideoplayer.data.Folder
import com.yogeshpaliyal.openvideoplayer.data.Video


@Composable
fun VideosListData(
    folderId: String,
    viewModel: ListViewModel = hiltViewModel(),
    navigateToDetail: (video: Video) -> Unit
) {

    Log.d("TestingVM", "VideosListData: ${viewModel.getNum()}")

    val videosState = viewModel.getVideos(folderId).collectAsState(initial = arrayListOf())
    val videos = videosState.value
    LazyColumn {
        itemsIndexed(videos) { index: Int, item: Video ->
            Row {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToDetail(item)
                        }
                        .padding(8.dp), text = item.videoName
                )

                if (index != videos.lastIndex)
                    Divider()

            }
        }
    }
}