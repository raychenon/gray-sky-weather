package io.betterapps.graysky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.betterapps.graysky.R
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.coroutines.Status
import io.betterapps.graysky.data.domains.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import io.betterapps.graysky.ui.adapter.HourlyWeatherAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import org.junit.Assert.assertNotNull
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    // lazy inject MyViewModel
    val mainViewModel: MainViewModel by viewModel()

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel.requestWeatherByLocation(GeoLocation(52.520007, 13.404954))
            .observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    it?.let { resource ->
                        processCommitResult(resource)
                    }
                }
            )
    }

    private fun processCommitResult(
        resource: Resource<WeatherByLocationResponse>
    ) {
        when (resource.status) {
            Status.LOADING -> {
                message.text = "LOADING"
                Timber.d("Weather LOADING")
            }
            Status.SUCCESS -> {
                message.text = ""
                Timber.d("Weather Success ${resource.data}")
                setupUI(response = resource.data!!)
            }
            Status.ERROR -> {
                Timber.d("Weather ERROR")
            }
        }
    }

    private fun setupUI(response: WeatherByLocationResponse) {
        // add divider
        val dividerItemDecoration = DividerItemDecoration(
            main_recyclerview.context,
            LinearLayout.HORIZONTAL
        )
        main_recyclerview.addItemDecoration(dividerItemDecoration)
        // add divider

        val horizontalLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val weatherAdapter = HourlyWeatherAdapter(response)
        main_recyclerview.apply {
            adapter = weatherAdapter
            layoutManager = horizontalLayoutManager
        }
    }
}
