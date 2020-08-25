package io.betterapps.graysky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.betterapps.graysky.R
import io.betterapps.graysky.data.coroutines.Resource
import io.betterapps.graysky.data.coroutines.Status
import io.betterapps.graysky.data.models.GeoLocation
import io.betterapps.graysky.data.models.WeatherByLocationResponse
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.requestWeatherByLocation(GeoLocation(52.520007, 13.404954))
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let { resource ->
                    processCommitResult(resource)
                }
            })
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
                message.text = resource.data!!.toString()
                Timber.d("Weather Success ${resource.data.toString()}")
            }
            Status.ERROR -> {
                Timber.d("Weather ERROR")
            }
        }
    }


}