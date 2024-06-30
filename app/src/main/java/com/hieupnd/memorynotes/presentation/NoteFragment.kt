package com.hieupnd.memorynotes.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.hieupnd.core.data.Note
import com.hieupnd.memorynotes.R
import com.hieupnd.memorynotes.databinding.FragmentNoteBinding
import com.hieupnd.memorynotes.framework.viewModel.NoteViewModel

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheck.setOnClickListener {
            if(binding.edtTitle.text.toString() != "" || binding.edtContent.text.toString() != "") {
                val time = System.currentTimeMillis()
                currentNote.title = binding.edtTitle.text.toString()
                currentNote.content = binding.edtContent.text.toString()
                if(currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                currentNote.updateTime = time

                viewModel.saveNote(currentNote)
            } else {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_LONG).show()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Done !", Toast.LENGTH_LONG).show()
                popBackStack()
            } else {
                Toast.makeText(context, "Something went wrong, please try again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }
}