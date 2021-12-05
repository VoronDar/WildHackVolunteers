package com.astery.wildhackvolunteers.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.astery.wildhack.ui.fragments.TFragment
import com.astery.wildhackvolunteers.databinding.FragmentProfileBinding
import com.astery.wildhackvolunteers.model.PersonFields
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : TFragment(){

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPerson()
        viewModel.saved.observe(this){
            if (it){
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToMainFragment())
            }
        }
    }

    fun ask(field: PersonFields){
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToQuestionFragment())
    }

    fun openDescription(field:PersonFields){
        ProfileQuestionDialog(layoutInflater, requireContext(), field).setOnOkListener {
            ask(field)
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
