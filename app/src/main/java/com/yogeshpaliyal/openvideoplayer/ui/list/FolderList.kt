package com.yogeshpaliyal.openvideoplayer.ui.list

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.yogeshpaliyal.openvideoplayer.data.Folder
import com.yogeshpaliyal.openvideoplayer.data.Video

@ExperimentalPermissionsApi
@Composable
fun FolderList(navController: NavController, viewModel: ListViewModel = hiltViewModel()) {

    val readStoragePermissionState =
        rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)


    PermissionRequired(
        permissionState = readStoragePermissionState,
        permissionNotGrantedContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(text = "Permission not granted")
                Button(onClick = {
                    readStoragePermissionState.launchPermissionRequest()
                }) {
                    Text(text = "Permission")
                }
            }


        },
        permissionNotAvailableContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(text = "Permission not available")
            }
        }) {

        FolderListData { item ->
            navController.navigate(
                "folders/${
                    item.id
                }"
            )
        }
    }


}


@Composable
fun FolderListData(
    viewModel: ListViewModel = hiltViewModel(),
    navigateToVideos: (folder: Folder) -> Unit
) {
    val folders = viewModel.getFolders().collectAsState(initial = arrayListOf())
    LazyColumn {
        itemsIndexed(folders.value) { index: Int, item: Folder ->
            Row(modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navigateToVideos(item)
                }) {

                Icon(Icons.Rounded.List, "", modifier = Modifier.align(CenterVertically))
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(), text = item.name
                )

            }
        }
    }
}


@Composable
fun VideosListData(
    folderId: String,
    viewModel: ListViewModel = hiltViewModel(),
    navigateToDetail: (video: Video) -> Unit
) {
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

/*@Preview(showSystemUi = true)
@Composable
fun PreviewVideoListData() {
    val videos =
        listOf<Video>(Video(Uri.EMPTY, "Test Name", 0, 0), Video(Uri.EMPTY, "Test Name", 0, 0))
    VideosListData(videos = videos)
}*/
