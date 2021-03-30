package com.example.rent_a_boatnavigationcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rent_a_boatnavigationcomponents.feed.BOATS
import com.example.rent_a_boatnavigationcomponents.feed.Boat
import com.example.rent_a_boatnavigationcomponents.feed.BoatsAdapter
import kotlinx.android.synthetic.main.fragment_feed.*

class OrdersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_orders, container, false)



        return view


    }


}