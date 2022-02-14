package com.task.noteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kennyc.bottomsheet.BottomSheetListener
import com.kennyc.bottomsheet.BottomSheetMenuDialogFragment
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNoteBinding
import com.task.noteapp.util.loadImage
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
        viewModel.noteImageUrl.value = args.note?.imageUrl

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
                activity?.onBackPressed()
            }

            setOnMenuItemClickListener {
                showOptions()
                false
            }
        }
    }


    private fun showOptions() {
        context?.let {
            BottomSheetMenuDialogFragment.Builder(
                context = it,
                sheet = R.menu.note_bottom_sheet_menu,
                listener = bottomSheetClickListener,
                style = R.style.MyBottomSheetMenuStyle
            ).show(childFragmentManager)

        }
    }

    private val bottomSheetClickListener = object : BottomSheetListener {
        override fun onSheetDismissed(
            bottomSheet: BottomSheetMenuDialogFragment,
            `object`: Any?,
            dismissEvent: Int
        ) {
        }

        override fun onSheetItemSelected(
            bottomSheet: BottomSheetMenuDialogFragment,
            item: MenuItem,
            `object`: Any?
        ) {
            when (item.itemId) {
                R.id.delete -> {
                    viewModel.deleteNote()
                    activity?.onBackPressed()
                }
                R.id.add_photo -> getImageUrl()
                R.id.done -> activity?.onBackPressed()
            }
        }

        override fun onSheetShown(
            bottomSheet: BottomSheetMenuDialogFragment,
            `object`: Any?
        ) {
        }
    }

    private fun getImageUrl() {
        with(binding.getImageView) {
            getImageUrlRoot.isVisible = true

            addImage.setOnClickListener {
                viewModel.noteImageUrl.value = this.imageUrlEditText.text.toString()
                loadImage()
            }

            imageUrlEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.noteImageUrl.value = this.imageUrlEditText.text.toString()
                    loadImage()
                    true
                } else false
            }
        }
    }

    private fun loadImage() {
        viewModel.isLoading.value = true

        binding.noteImageView.loadImage(viewModel.noteImageUrl.value) {
            viewModel.isLoading.value = false
            binding.noteImageView.isVisible = it
        }
    }

    private fun saveNote() {
        viewModel.saveNote(
            binding.titleEditText.text.toString(),
            binding.bodyEditText.text.toString()
        )
    }
}