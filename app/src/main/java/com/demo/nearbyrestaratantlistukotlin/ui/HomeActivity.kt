package com.demo.nearbyrestaratantlistukotlin.ui

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.nearbyrestaratantlistukotlin.MainActivity
import com.demo.nearbyrestaratantlistukotlin.R
import com.demo.nearbyrestaratantlistukotlin.adapter.NearByRestauranrListAdapter
import com.demo.nearbyrestaratantlistukotlin.model.NearByRestaurantResponse
import com.demo.nearbyrestaratantlistukotlin.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {
    var nearByRestaurantResponse = ArrayList<NearByRestaurantResponse>()
    lateinit var adapter: NearByRestauranrListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val searchIcon = nearbyrestaurant_search.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)


        val cancelIcon = nearbyrestaurant_search.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)


        val textView = nearbyrestaurant_search.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(
            this,
            LinearLayout.VERTICAL,
            false)


       readArrayOfJsonObject()

        nearbyrestaurant_rv.layoutManager = linearLayoutManager
        nearbyrestaurant_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }

        })

        Buttonlogout.setOnClickListener {
            AppPreferences.isLogin=false
            AppPreferences.username=""
            AppPreferences.password=""
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun readArrayOfJsonObject() {
        val json_string: String? = this!!.getJSONFileFromAsset("data.json",this)
        Log.d("response",json_string)

        try {
            val json_object:JSONObject = JSONObject(json_string)
            val jsonarray: JSONArray = json_object.getJSONArray("restaurants")

            val size: Int = jsonarray.length()

            for (i in 0..size - 1) {
                val json_objectdetail: JSONObject = jsonarray.getJSONObject(i)
                val model: NearByRestaurantResponse = NearByRestaurantResponse()
                model.name = json_objectdetail.getString("name")
                model.neighborhood = json_objectdetail.getString("neighborhood")
                model.address = json_objectdetail.getString("address")
                model.distance = json_objectdetail.getString("distance")
                model.cuisine_type = json_objectdetail.getString("cuisine_type")
                val jsonreview: JSONArray = json_objectdetail.getJSONArray("reviews")
                val size: Int = jsonreview.length()

                for (j in 0..size - 1) {
                    val json_objdetail: JSONObject = jsonreview.getJSONObject(j)
                    model.rating=json_objdetail.getInt("rating")

                }

                nearByRestaurantResponse.add(model)
            }

            nearByRestaurantResponse.sortWith(object: Comparator<NearByRestaurantResponse>{
                override fun compare(p1: NearByRestaurantResponse, p2: NearByRestaurantResponse): Int = when {
                    p1.distance!! > p2.distance!! -> 1
                    p1.distance == p2.distance -> 0
                    else -> -1
                }
            })
            adapter= NearByRestauranrListAdapter(nearByRestaurantResponse)
            nearbyrestaurant_rv.adapter = adapter
        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    @Throws(IOException::class)
    fun getJSONFileFromAsset(filename: String?, context: Context): String? {
        val manager: AssetManager = context.getAssets()
        val file = manager.open(filename!!)
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()
        return String(formArray)
    }
}
