package com.example.rent_a_boatnavigationcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rent_a_boatnavigationcomponents.feed.BOATS
import com.example.rent_a_boatnavigationcomponents.feed.Boat
import com.example.rent_a_boatnavigationcomponents.feed.getBoat
import kotlinx.android.synthetic.main.item_boat.view.*


class BoatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //id from deep link
        val id = arguments?.getString("id_dl")?.toInt()?: BoatFragmentArgs.fromBundle(requireArguments()).id




       // val id = BoatFragmentArgs.fromBundle(requireArguments()).id
        val boat = BOATS.getBoat(id)
        val view =  inflater.inflate(R.layout.fragment_boat, container, false)
        view.nameTextView.text = boat.name
        view.locationTextView.text = boat.location
        view.imageView2.setImageResource(boat.picture)
        view.priceTextView.text = boat.price

        return view
    }

}