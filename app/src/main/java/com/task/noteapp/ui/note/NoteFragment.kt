package com.task.noteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels()
    lateinit var binding: FragmentNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback {
            saveNote()
            if (isEnabled) {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        val args: NoteFragmentArgs by navArgs()
        viewModel.note.value = args.note

        setupBinding()
        setupToolbar()

        return binding.root
    }

    private fun setupBinding() {
        with(binding) {
            lifecycleOwner = this@NoteFragment.viewLifecycleOwner
            viewModel = this@NoteFragment.viewModel
        }
    }

    private fun setupToolbar() {
        with(binding.genericToolbar.toolbar) {
            inflateMenu(R.menu.note_menu)
            setNavigationOnClickListener {
                saveNote()
                activity?.onBackPressed()
            }

            setOnMenuItemClickListener {
                false
            }
        }
    }

    private fun saveNote() {
        viewModel.saveNote(
            binding.titleEditText.text.toString(),
            binding.bodyEditText.text.toString()
        )
    }
}