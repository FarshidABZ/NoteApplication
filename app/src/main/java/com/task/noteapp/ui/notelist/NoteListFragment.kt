package com.task.noteapp.ui.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNoteListBinding
import com.task.noteapp.navigation.gotoNoteFragment
import com.task.noteapp.ui.notelist.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment() {
    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: NoteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)

        setupBinding()
        setupToolbar()
        setupRecyclerView()
        setupFab()

        return binding.root
    }

    private fun setupBinding() {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@NoteListFragment.viewModel
        }
    }

    private fun setupToolbar() {
        with(binding.genericToolbar.toolbar) {
            setTitle(R.string.notes)
            navigationIcon = null
        }
    }

    private fun setupRecyclerView() {
        with(binding.noteListRecyclerView) {
            adapter = NoteListAdapter({ noteUIModel ->
                findNavController().gotoNoteFragment(noteUIModel)
            }, { noteUIModel ->
                // show bottom sheet menu
            })

            val animator: RecyclerView.ItemAnimator? = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }
    }

    private fun setupFab() {
        binding.addNewNoteFab.setOnClickListener {
            findNavController().gotoNoteFragment()
        }
    }
}