package com.ron.countrytableapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ron.countrytableapp.R
import com.ron.countrytableapp.data.model.Country
import com.ron.countrytableapp.ui.borders.BordersFragment
import com.ron.countrytableapp.ui.main.adapter.MainAdapter
import com.ron.countrytableapp.ui.main.adapter.OnClickItem
import com.ron.countrytableapp.ui.main.viewmodel.MainViewModel
import com.ron.countrytableapp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 * Use the [CountriesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CountriesListFragment : Fragment(), OnClickItem{

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }


    private fun setupUI() {
        recycleView.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(arrayListOf(),this)
        recycleView.addItemDecoration(
            DividerItemDecoration(
                recycleView.context,
                (recycleView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycleView.adapter = adapter
        btnSortByName.setOnClickListener {
            mainViewModel.sortByName()
        }
        btnSortByArea.setOnClickListener {
            mainViewModel.sortByArea()
        }
    }

    private fun setupObserver() {
        mainViewModel.countries.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    // progressBar.visibility = View.GONE
                    it.data?.let { countries -> renderList(countries) }
                    recycleView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    // progressBar.visibility = View.VISIBLE
                    recycleView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    // progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(countries: List<Country>) {
        adapter.addData(countries)
        adapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BordersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CountriesListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    override fun onItemClick(country: Country) {
        val bordersFragment = BordersFragment.newInstance(country.borders)
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container,bordersFragment,"BordersFragment")
            ?.commit()
    }
}