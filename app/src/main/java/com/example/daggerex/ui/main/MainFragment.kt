package com.example.daggerex.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerex.MyApp
import com.example.daggerex.R
import com.example.daggerex.data.DataManager
import com.example.daggerex.data.model.Hotgirl
import com.example.daggerex.ui.edit.EditFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment(), View.OnClickListener, CoroutineScope {
    private var dataManager: DataManager? = null

    private val parentJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + parentJob

    private val listData = ArrayList<Hotgirl>()
    private val itemClickListener: View.OnClickListener = View.OnClickListener {
        val layoutParams = it.layoutParams as RecyclerView.LayoutParams
        val position = layoutParams.viewAdapterPosition
        val data = listData[position]

        fragmentManager?.beginTransaction()
                ?.replace(R.id.container, EditFragment.newInstance(data.id))
                ?.addToBackStack(null)
                ?.commit()
    }

    private val adapter = MainAdapter(itemClickListener)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvent()
        initView()
    }

    private fun addEvent() {
        fabAdd.setOnClickListener(this)
    }

    private fun initView() {
        rcvListGirl.adapter = adapter

        launch(Dispatchers.IO) {
            listData.clear()
            listData.addAll(getDataManager()?.all ?: emptyList())
            adapter.submitList(listData)
            Log.e(TAG, "List size: ${listData.size}")
            Log.w(TAG, "List size: ${listData.size}")
            Log.wtf(TAG, "List size: ${listData.size}")
        }
    }

    private fun getDataManager(): DataManager? {
        if (dataManager == null) {
            dataManager = (context?.applicationContext as MyApp).dataManager
        }
        return dataManager
    }

    override fun onClick(v: View?) {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.container, EditFragment.newInstance(null))
                ?.addToBackStack(null)
                ?.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancelChildren()
    }

    companion object {
        private const val TAG = "HHMainFragment"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
