package com.yogeshpaliyal.openvideoplayer.ui.list

import androidx.lifecycle.ViewModel
import com.yogeshpaliyal.openvideoplayer.repo.VideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val videosRepository: VideosRepository) : ViewModel(){

    fun getVideos() = videosRepository.getVideosList()

}