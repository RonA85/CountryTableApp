package com.ron.countrytableapp.ui.borders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ron.countrytableapp.R
import com.ron.countrytableapp.data.model.Country
import com.ron.countrytableapp.ui.main.CountriesListFragment
import com.ron.countrytableapp.ui.main.adapter.MainAdapter
import com.ron.countrytableapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_borders.*

/**
 * A simple [Fragment] subclass.
 * Use the [BordersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class BordersFragment : Fragment(){

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var borders: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            borders = it.getStringArrayList("borders")!!
        }
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val countriesListFragment = CountriesListFragment.newInstance()
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container,countriesListFragment,"CountriesListFragment")
                ?.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_borders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        mainViewModel.getBorders(borders);
        setupObserver()
    }


    private fun setupUI() {
        recycleView.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(arrayListOf(),null)
        recycleView.addItemDecoration(
            DividerItemDecoration(
                recycleView.context,
                (recycleView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycleView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.borders.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                recycleView.visibility = View.INVISIBLE
                textViewMessage.visibility = View.VISIBLE
            }else{
                textViewMessage.visibility = View.INVISIBLE
                recycleView.visibility = View.VISIBLE
                renderList(it)
            }

        })
    }

    private fun renderList(countries: List<Country>) {
        adapter.addData(countries)
        adapter.notifyDataSetChanged()
    }

    companion object {

        @JvmStatic
        fun newInstance(borders: ArrayList<String>) =
            BordersFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList("borders",borders)
                }
            }
    }
}