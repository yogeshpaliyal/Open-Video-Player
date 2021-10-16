package com.yogeshpaliyal.openvideoplayer.ui.folders

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.yogeshpaliyal.openvideoplayer.data.Folder
import com.yogeshpaliyal.openvideoplayer.ui.list.ListViewModel

@ExperimentalPermissionsApi
@Composable
fun FolderList(navigateToVideos : (folder: Folder)-> Unit) {

    val readStoragePermissionState =
        rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)


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
            navigateToVideos(item)
        }
    }

}


@Composable
fun FolderListData(
    viewModel: ListViewModel = hiltViewModel(),
    navigateToVideos: (folder: Folder) -> Unit
) {

    Log.d("TestingVM", "FolderListData: ${viewModel.getNum()}")

    val folders = viewModel.getFolders().collectAsState(initial = arrayListOf())
    LazyColumn {
        itemsIndexed(folders.value) { index: Int, item: Folder ->
            Row(modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navigateToVideos(item)
                }) {

                Icon(Icons.Rounded.List, "", modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(), text = item.name
                )

            }
        }
    }
}