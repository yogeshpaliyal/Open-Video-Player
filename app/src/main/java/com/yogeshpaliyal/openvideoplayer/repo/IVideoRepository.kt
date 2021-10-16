package com.yogeshpaliyal.openvideoplayer.repo

import com.yogeshpaliyal.openvideoplayer.data.Folder
import com.yogeshpaliyal.openvideoplayer.data.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getVideosList(folderId: String) : Flow<List<Video>>
    fun getVideoInfo()
    fun getFolders() : Flow<List<Folder>>
}