package com.example.rent_a_boatnavigationcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rent_a_boatnavigationcomponents.feed.BOATS
import com.example.rent_a_boatnavigationcomponents.feed.BoatsAdapter
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.view.*


class FeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_feed, container, false)

        view.recycler.layoutManager = LinearLayoutManager(view.context)
        val adapter =  BoatsAdapter(BOATS, ::onBoatClick)
        view.recycler.adapter = adapter

        return view

    }

    private fun onBoatClick(boatId: Int) {
//        var args = Bundle()
//        args.putInt("id", boatId)
//        activity?.findNavController(R.id.nav_container)?.navigate(R.id.boatFragment, args)

        val actions = HomeFragmentDirections.actionHomeFragmentToBoatFragment(boatId)
        activity?.findNavController(R.id.nav_container)?.navigate(actions)

    }


}