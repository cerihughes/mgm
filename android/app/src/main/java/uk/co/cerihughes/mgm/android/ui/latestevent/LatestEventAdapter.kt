package uk.co.cerihughes.mgm.android.ui.latestevent

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.co.cerihughes.mgm.android.R
import uk.co.cerihughes.mgm.android.model.Event

class LatestEventAdapter (private val latestEvent: Event) : RecyclerView.Adapter<LatestEventAdapter.LatestEventItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LatestEventItemViewHolder {
        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.latest_event_list_item, viewGroup, false)
        return LatestEventItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: LatestEventItemViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        var items = 1
        latestEvent.newAlbum?.let {
            items += 1
        }
        latestEvent.classicAlbum?.let {
            items += 1
        }
        latestEvent.playlist?.let {
            items += 1
        }
        return items
    }

    class LatestEventItemViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
