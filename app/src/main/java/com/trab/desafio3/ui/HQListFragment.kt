package com.trab.desafio3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.trab.desafio3.R
import com.trab.desafio3.adapters.ComicsAdapter
import com.trab.desafio3.adapters.HQClick
import com.trab.desafio3.helper.MarvelAPI
import com.trab.desafio3.models.Results
import kotlinx.android.synthetic.main.fragment_hq_list.*

class HQListFragment : Fragment(), HQClick {
    private val viewModel: HQListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hq_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcHq.layoutManager = GridLayoutManager(context, 3)
        rcHq.setHasFixedSize(true)
        rcHq.adapter = ComicsAdapter(MarvelAPI.getInstance(), this)

        (rcHq.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()

        rcHq.addOnScrollListener(viewModel.PaginationCall())

        viewModel.comicList.observe(viewLifecycleOwner, ::updateList)
    }

    private fun updateList(a: Any) {
        (rcHq.adapter as ComicsAdapter).notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.comicList.removeObservers(viewLifecycleOwner)
    }

    override fun hqClick(hq: Results) {
        val nav = HQListFragmentDirections.actionHQListFragmentToHQFragment(hq)
        findNavController().navigate(nav)
    }
}