package com.yogeshpaliyal.openvideoplayer.ui.list

import androidx.lifecycle.ViewModel
import com.yogeshpaliyal.openvideoplayer.repo.VideoRepository
import com.yogeshpaliyal.openvideoplayer.repo.VideosRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val videosRepository: VideoRepository) : ViewModel(){

    private var num = 0;

    fun getNum() = "${num++}"

    fun getVideos(folderId: String) = videosRepository.getVideosList(folderId)

    fun getFolders() = videosRepository.getFolders()

}