package com.demo.nearbyrestaratantlistukotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.demo.nearbyrestaratantlistukotlin.R
import com.demo.nearbyrestaratantlistukotlin.model.NearByRestaurantResponse
import kotlinx.android.synthetic.main.nearby_list_items.view.*
import java.util.*
import kotlin.collections.ArrayList

class NearByRestauranrListAdapter(val nearByRestaurantResponseList: ArrayList<NearByRestaurantResponse>) :
    RecyclerView.Adapter<NearByRestauranrListAdapter.ViewHolder>()  {

    var nearByRestaurantResponse = ArrayList<NearByRestaurantResponse>()

    lateinit var mcontext: Context

    init {
        nearByRestaurantResponse = nearByRestaurantResponseList
    }
    class ViewHolder  (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val hotelName=itemView.hotelName
        val txtneighborhood=itemView.txtneighborhood
        val txtdistance=itemView.txtdistance
        val txtTypes=itemView.txtTypes
        val txtaddress=itemView.txtaddress
        val txtRating=itemView.txtRating

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearByRestauranrListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val itemView = inflater.inflate(R.layout.nearby_list_items, parent, false)
        return NearByRestauranrListAdapter.ViewHolder(itemView)    }

    override fun getItemCount(): Int {
        return nearByRestaurantResponse.size
    }

    override fun onBindViewHolder(holder: NearByRestauranrListAdapter.ViewHolder, position: Int) {
        holder?.hotelName?.text=nearByRestaurantResponse.get(position).name
        holder?.txtneighborhood?.text=nearByRestaurantResponse.get(position).neighborhood
        holder?.txtdistance?.text=nearByRestaurantResponse.get(position).distance
        holder?.txtTypes?.text=nearByRestaurantResponse.get(position).cuisine_type
        holder?.txtaddress?.text=nearByRestaurantResponse.get(position).address
        holder?.txtRating?.text= nearByRestaurantResponse.get(position).rating.toString()

    }

     fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    nearByRestaurantResponse = nearByRestaurantResponseList
                } else {
                    val resultList = ArrayList<NearByRestaurantResponse>()
                    for (row in nearByRestaurantResponseList) {
                        if (row.name.toString().toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    nearByRestaurantResponse = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = nearByRestaurantResponse
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                nearByRestaurantResponse = results?.values as ArrayList<NearByRestaurantResponse>
                notifyDataSetChanged()
            }

        }
    }


}
