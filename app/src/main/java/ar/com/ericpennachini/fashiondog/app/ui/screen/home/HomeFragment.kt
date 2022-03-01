package ar.com.ericpennachini.fashiondog.app.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ar.com.ericpennachini.fashiondog.app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {
                        Button(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            onClick = {
                                findNavController().navigate(R.id.toCustomerFragment)
                            }
                        ) {
                            Text(text = "Go to customer screen")
                        }
                    }
                }
            }
        }
    }

}
