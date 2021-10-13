package com.yogeshpaliyal.openvideoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.yogeshpaliyal.openvideoplayer.ui.list.ListViewModel
import com.yogeshpaliyal.openvideoplayer.ui.list.VideosList
import com.yogeshpaliyal.openvideoplayer.ui.theme.OpenVideoPlayerTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<ListViewModel>()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        setContent {
            OpenVideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //VideosList()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpenVideoPlayerTheme {
        Greeting("Android")
    }
}