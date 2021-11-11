package com.yogeshpaliyal.openvideoplayer.ui.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yogeshpaliyal.openvideoplayer.R
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
            Row(modifier = Modifier.clickable {
                navigateToDetail(item)
            }.padding(8.dp)) {

                Icon(
                    painterResource(id = R.drawable.ic_video),
                    "",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(), text = item.videoName
                )

                if (index != videos.lastIndex)
                    Divider()

            }
        }
    }
}