package com.example.android.navigation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    private  val TAG = TitleFragment::class.java.simpleName
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated called")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_title, container, false)
        Log.i(TAG, "onCreateView called ")
        binding.playButton.setOnClickListener { view ->
            view.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment2())

        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart called")

    }
    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop called")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView called")
    }
    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach called")
    }

}