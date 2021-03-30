package com.example.rent_a_boatnavigationcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_home, container, false)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            .setupWithNavController(navController = navHostFragment.navController)



        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
           view.home_toolbar.title = destination.label
        }
        return view
    }


}