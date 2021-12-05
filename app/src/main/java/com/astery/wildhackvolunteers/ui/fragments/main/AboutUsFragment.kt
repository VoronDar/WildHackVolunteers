package com.astery.wildhackvolunteers.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.astery.wildhackvolunteers.ui.activity.ParentActivity
import com.astery.wildhack.ui.fragments.TFragment
import com.astery.wildhackvolunteers.databinding.FragmentAboutUsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AboutUsFragment : TFragment(){

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as ParentActivity).setFullScreen(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener { navigateToMainFragment() }

    }

    private fun navigateToMainFragment(){
        findNavController().navigate(AboutUsFragmentDirections.actionAboutUsFragmentToMainFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as ParentActivity).setFullScreen(false)
    }
}
