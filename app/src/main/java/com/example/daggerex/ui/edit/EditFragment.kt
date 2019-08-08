package com.example.daggerex.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daggerex.MyApp
import com.example.daggerex.R
import com.example.daggerex.data.DataManager
import com.example.daggerex.data.model.Hotgirl
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class EditFragment : Fragment() {
    private var hotgirl: Hotgirl? = null

    private var dataManager: DataManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val userId = it.getInt(ARG_ID, -1)
            if (userId >= 0) {
                hotgirl = getDataManager()?.findById(userId)
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvent()
    }

    private fun addEvent() {
        btnSave.setOnClickListener {
            val name = edtUserName.text.toString()
            if (hotgirl != null) {
                hotgirl?.name = name
            } else {
                hotgirl = Hotgirl(0, name, null)
                getDataManager()?.add(hotgirl)
                fragmentManager?.popBackStack()
            }
        }
    }

    private fun getDataManager(): DataManager? {
        if (dataManager == null) {
            dataManager = (context?.applicationContext as MyApp).dataManager
        }
        return dataManager
    }

    companion object {
        private const val ARG_ID = "userId"

        @JvmStatic
        fun newInstance(id: Int?) =
                EditFragment().apply {
                    arguments = Bundle().apply {
                        if (id != null) putInt(ARG_ID, id)
                    }
                }
    }
}
