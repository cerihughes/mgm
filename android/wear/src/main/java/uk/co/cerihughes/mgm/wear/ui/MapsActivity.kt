package uk.co.cerihughes.mgm.wear.ui

import android.os.Bundle
import android.support.wear.widget.SwipeDismissFrameLayout
import android.support.wearable.activity.WearableActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rantmedia.wear.R
import kotlinx.android.synthetic.main.activity_maps.*
import uk.co.cerihughes.mgm.common.repository.RepositoryImpl
import uk.co.cerihughes.mgm.common.repository.local.LocalDataSourceImpl
import uk.co.cerihughes.mgm.common.repository.remote.RemoteDataSourceImpl
import uk.co.cerihughes.mgm.common.viewmodel.LatestEventViewModel
import uk.co.cerihughes.mgm.common.viewmodel.RemoteDataLoadingViewModel


class MapsActivity : WearableActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: LatestEventViewModel

    public override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setAmbientEnabled()

        setContentView(R.layout.activity_maps)

        val localDataSouce = LocalDataSourceImpl(this)
        val remoteDataSource = RemoteDataSourceImpl()
        val repository = RepositoryImpl(remoteDataSource, localDataSouce)
        viewModel = LatestEventViewModel(repository)

        swipe_dismiss_root_container.addCallback(object : SwipeDismissFrameLayout.Callback() {
            override fun onDismissed(layout: SwipeDismissFrameLayout?) {
                // Hides view before exit to avoid stutter.
                layout?.visibility = View.GONE
                finish()
            }
        })

        swipe_dismiss_root_container.setOnApplyWindowInsetsListener { _, insetsArg ->
            val insets = swipe_dismiss_root_container.onApplyWindowInsets(insetsArg)

            val params = map_container.layoutParams as FrameLayout.LayoutParams

            params.setMargins(
                insets.systemWindowInsetLeft,
                insets.systemWindowInsetTop,
                insets.systemWindowInsetRight,
                insets.systemWindowInsetBottom
            )
            map_container.layoutParams = params

            insets
        }

        val mapFragment = map as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(getApplicationContext(), R.string.intro_text, duration)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

        viewModel.loadData(object: RemoteDataLoadingViewModel.LoadDataCallback {
            override fun onDataLoaded() {
                viewModel.mapReference()?.let {
                    val position = LatLng(it.first, it.second)
                    mMap.addMarker(MarkerOptions().position(position).title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
                    val marker = mMap.addMarker(MarkerOptions().position(position).title(viewModel.locationName()))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
                    marker.showInfoWindow()
                }
            }
        })
    }
}
