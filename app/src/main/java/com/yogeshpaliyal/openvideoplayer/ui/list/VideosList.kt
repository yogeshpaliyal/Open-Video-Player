package com.yogeshpaliyal.openvideoplayer.ui.list

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.yogeshpaliyal.openvideoplayer.data.Video

@ExperimentalPermissionsApi
@Composable
fun VideosList(viewModel: ListViewModel = hiltViewModel()) {

    val readStoragePermissionState = rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)


    PermissionRequired(
        permissionState = readStoragePermissionState,
        permissionNotGrantedContent = {

            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Text(text = "Permission not granted")
            }
            readStoragePermissionState.run {
                launchPermissionRequest()
            }
        },
        permissionNotAvailableContent = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Text(text = "Permission not available")
            }
        }) {
        val videos = viewModel.getVideos().collectAsState(initial = listOf())

        videos.value.let {
            VideosListData(it)
        }
    }




}


@Composable
fun VideosListData(videos: List<Video>) {
    LazyColumn {
        itemsIndexed(videos) { index: Int, item: Video ->
            Column {
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), text = item.videoName)
                if (index != videos.lastIndex)
                    Divider()

            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewVideoListData() {
    val videos =
        listOf<Video>(Video(Uri.EMPTY, "Test Name", 0, 0), Video(Uri.EMPTY, "Test Name", 0, 0))
    VideosListData(videos = videos)
}
