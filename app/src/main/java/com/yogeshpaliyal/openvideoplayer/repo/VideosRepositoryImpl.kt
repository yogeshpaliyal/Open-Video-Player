package com.yogeshpaliyal.openvideoplayer.repo

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.yogeshpaliyal.openvideoplayer.data.Folder
import com.yogeshpaliyal.openvideoplayer.data.Video
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideosRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    VideoRepository {

    override fun getVideosList(folderId: String) = flow<List<Video>> {
        // Display videos in alphabetical order based on their display name.
        Log.d("Testing123", "getVideosList: init")
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.BUCKET_ID
        )

        val videoList = mutableListOf<Video>()

        context.contentResolver.query(
            collection,
            projection,
            MediaStore.Video.Media.BUCKET_ID + " = ?",
            arrayOf(folderId),
            sortOrder
        )?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            Log.d("Testing123", "getVideosList: cursor init")


            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += Video(contentUri, name, duration, size)
                Log.d("Testing123", "getVideosList: video")

            }

            Log.d("Testing123", "getVideosList: videolist ${videoList.size}")

            emit(videoList)
        }
    }

    override fun getVideoInfo() {

    }

    override fun getFolders() = flow<List<Folder>> {
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Video.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            }

        val sortOrder = "${MediaStore.Video.Media.BUCKET_DISPLAY_NAME} ASC"

        val projection = arrayOf(
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        )

        val folders = hashMapOf<String, Folder>()

        context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID)
            val nameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)



            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getString(idColumn)
                val name = cursor.getString(nameColumn)

                if (folders.contains(id).not())
                    folders.put(id, Folder(id, name))
                // Stores column values and the contentUri in a local object
                // that represents the media file.

            }

            emit(folders.map { entry: Map.Entry<String, Folder> -> entry.value })
        }
    }
}