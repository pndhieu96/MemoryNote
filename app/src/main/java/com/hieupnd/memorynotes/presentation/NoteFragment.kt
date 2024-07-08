package com.hieupnd.memorynotes.presentation

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.hieupnd.core.data.Note
import com.hieupnd.memorynotes.R
import com.hieupnd.memorynotes.databinding.FragmentNoteBinding
import com.hieupnd.memorynotes.framework.viewModel.NoteViewModel

class NoteFragment : Fragment(), MenuProvider {

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
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding = FragmentNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            currentNote.id = NoteFragmentArgs.fromBundle(it).noteId
        }

        if(currentNote.id != 0L) {
            viewModel.getNote(currentNote.id)
        }

        binding.btnCheck.setOnClickListener {
            if(binding.edtTitle.text.toString() != "" || binding.edtContent.text.toString() != "") {
                val time = System.currentTimeMillis()
                val newTitle = binding.edtTitle.text.toString()
                val newContent = binding.edtContent.text.toString()
                val isUpdate = viewModel.isUpdatedNote(currentNote, newTitle, newContent)

                currentNote.title = binding.edtTitle.text.toString()
                currentNote.content = binding.edtContent.text.toString()
                if(currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                currentNote.updateTime = time

                if(currentNote.id == 0L || isUpdate) {
                    viewModel.saveNote(currentNote)
                } else {
                    popBackStack()
                }
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

        viewModel.noteLiveData.observe(viewLifecycleOwner) {
            currentNote = it
            binding.edtTitle.setText(currentNote.title)
            binding.edtContent.setText(currentNote.content)
        }
    }

    private fun popBackStack() {
        findNavController().popBackStack()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.deleteNote -> {
                if(context != null && currentNote.id != 0L) {
                    AlertDialog.Builder(context)
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note ?")
                        .setPositiveButton("Yes") { _, _ ->
                            viewModel.deleteNote(currentNote)
                        }
                        .setNegativeButton("NO") {_, _ -> }
                        .create()
                        .show()
                }
            }
        }
        return true
    }
}