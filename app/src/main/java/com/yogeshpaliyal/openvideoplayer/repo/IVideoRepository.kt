package com.yogeshpaliyal.openvideoplayer.repo

import com.yogeshpaliyal.openvideoplayer.data.Video
import kotlinx.coroutines.flow.Flow

interface IVideoRepository {
    fun getVideosList() : Flow<List<Video>>
    fun getVideoInfo()
}