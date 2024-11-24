package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.skt.tmap.TMapView
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.databinding.ActivityMainBinding // Binding 추가


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tMapView = TMapView(this).apply {
            setSKTMapApiKey("F9wKkfk1Vo7fe83G574bx7OOE2MVgpHY4Km4uZ0V")
            setOnMapReadyListener {
                Toast.makeText(this@MainActivity, "Map Ready", Toast.LENGTH_SHORT).show()
                // 맵 로딩 완료 후 구현?
            }
        }

        binding.tmapViewContainer.addView(tMapView)


        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TMapViewComposable(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TMapViewComposable(modifier: Modifier = Modifier) {
    // AndroidView를 사용하여 기존 Android View인 TMapView를 Compose에 추가
    AndroidView(
        factory = { context ->
            // FrameLayout 생성
            val frameLayout = FrameLayout(context)

            // TMapView 객체 생성
            val tMapView = TMapView(context).apply {
                // 발급받은 API Key 설정
                setSKTMapApiKey("F9wKkfk1Vo7fe83G574bx7OOE2MVgpHY4Km4uZ0V")

                // TMapView의 로딩이 완료되었을 때 수행할 작업 정의
                setOnMapReadyListener {
                    // 맵 준비가 완료되면 이곳에서 추가 작업을 구현
                    Toast.makeText(context, "Map Ready", Toast.LENGTH_SHORT).show()
                }
            }

            // TMapView를 FrameLayout에 추가
            frameLayout.addView(tMapView)
            frameLayout // 반환
        },
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        TMapViewComposable()
    }
}