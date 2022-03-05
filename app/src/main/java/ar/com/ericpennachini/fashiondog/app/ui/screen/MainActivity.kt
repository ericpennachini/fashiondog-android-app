package ar.com.ericpennachini.fashiondog.app.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.com.ericpennachini.fashiondog.app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
