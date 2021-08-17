package com.rscorp.quicknotes.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rscorp.quicknotes.R
import com.rscorp.quicknotes.databinding.FragmentNotesBottomSheetBinding
import com.rscorp.quicknotes.util.MySpinnerAdapter
import com.rscorp.quicknotes.util.PrefHelper
import com.rscorp.quicknotes.util.extensions.makeInVisible
import com.rscorp.quicknotes.util.extensions.makeVisible
import com.rscorp.quicknotes.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotesBottomSheetBinding
    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBottomSheetBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        observeData()
        setClickListener()
    }

    private fun setClickListener(){
        binding.tvEdit.setOnClickListener {
            viewModel.apply {
                isEditing.value = isEditing.value == false
            }

        }
    }

    private fun observeData(){
        viewModel.isEditing.observe(viewLifecycleOwner , {
            if (it){
                binding.root.setBackgroundColor(Color.RED)
                binding.apply {
                    imgNoteIcon.makeInVisible()
                    spinner.makeVisible()
                    spinner.adapter = MySpinnerAdapter(requireContext() , PrefHelper.getIconsArray(requireContext()))
                    spinner.setSelection(viewModel.selectedNoteData?.iconPosition ?: 0)
                }

            }else{
                binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200))
                binding.apply {
                    imgNoteIcon.makeVisible()
                    spinner.makeVisible()
                    spinner.adapter = MySpinnerAdapter(requireContext() , PrefHelper.getIconsArray(requireContext()))
                    spinner.setSelection(viewModel.selectedNoteData?.iconPosition ?: 0)
                }
            }
        })
    }


    private fun setUpUI(){
        val noteData = viewModel.selectedNoteData
        noteData?.let {
            binding.apply {
                imgNoteIcon.setImageResource(it.icon)
                tvNote.text = it.title
            }
        }
    }
}