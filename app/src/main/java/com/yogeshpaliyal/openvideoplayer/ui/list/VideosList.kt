package com.yogeshpaliyal.openvideoplayer.ui.list

import android.net.Uri
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yogeshpaliyal.openvideoplayer.data.Video

@Composable
fun VideosList(viewModel: ListViewModel = viewModel()) {
    val videos = viewModel.getVideos().collectAsState(initial = listOf())

    videos.value.let {
        VideosListData(it)
    }
}


@Composable
fun VideosListData(videos: List<Video>) {
    LazyColumn {
        itemsIndexed(videos) { index: Int, item: Video ->
            Text(text = item.videoName)
            if (index != videos.lastIndex)
                Divider()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewVideoListData(){
    val videos = listOf<Video>(Video(Uri.EMPTY, "Test Name",0,0))
    VideosListData(videos = videos)
}
