package com.task.noteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNoteBinding
import com.task.noteapp.model.NoteUIModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels()
    lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        setupBinding()
        setupToolbar()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.note.value = NoteUIModel(
            0,
            "Farshid",
            "This is the description",
            System.currentTimeMillis(),
            System.currentTimeMillis() - 100000,
            "https://picsum.photos/id/1/300/200"
        )
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
                activity?.onBackPressed()
            }

            setOnMenuItemClickListener {
                false
            }
        }
    }
}