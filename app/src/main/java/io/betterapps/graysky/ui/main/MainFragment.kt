package io.betterapps.graysky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.betterapps.graysky.R
import io.betterapps.graysky.ui.weatherforecast.WeatherViewModel
import org.junit.Assert.assertNotNull
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    // lazy inject MyViewModel
    val mainViewModel: WeatherViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // since Koin DI is done at run time instead of compile time, better to check
        assertNotNull(mainViewModel)
        assertNotNull(mainViewModel.repository)
    }
}
