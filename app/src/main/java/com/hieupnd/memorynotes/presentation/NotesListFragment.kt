package com.hieupnd.memorynotes.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hieupnd.memorynotes.databinding.FragmentListBinding
import com.hieupnd.memorynotes.framework.recycleView.adapter.NotesListAdapter
import com.hieupnd.memorynotes.framework.recycleView.callback.ListAction
import com.hieupnd.memorynotes.framework.viewModel.NotesListViewModel

class NotesListFragment : Fragment(), ListAction {

    private val TAG = NotesListFragment::class.simpleName
    private lateinit var binding: FragmentListBinding
    private lateinit var notesListAdapter: NotesListAdapter
    private val mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    private val viewModel: NotesListViewModel by viewModels()

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e(TAG, "onCreateView")
        if(rootView == null) {
            binding = FragmentListBinding.inflate(inflater)
            rootView = binding.root
        }
        return rootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesListAdapter = NotesListAdapter(mutableListOf(), this)
        binding.lvNotes.apply {
            this.adapter = notesListAdapter
            this.layoutManager = mLayoutManager
        }

        binding.btnCreate.setOnClickListener {
            goToNoteDetail()
        }

        viewModel.getAllNote()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }

    private fun observeViewModel() {
        viewModel.noteListLiveData.observe(viewLifecycleOwner) {
            binding.pbLoading.visibility = GONE
            binding.lvNotes.visibility = VISIBLE
            notesListAdapter.updateNotes(it)
        }
    }

    private fun goToNoteDetail(id: Long = 0L) {
        val action = NotesListFragmentDirections.goToNoteFragment(id)
        findNavController().navigate(action)
    }

    override fun onRCItemClick(id: Long) {
        goToNoteDetail(id)
    }
}