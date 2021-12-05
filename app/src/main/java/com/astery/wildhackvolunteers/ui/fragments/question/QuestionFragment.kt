package com.astery.wildhackvolunteers.ui.fragments.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.wildhack.ui.fragments.TFragment
import com.astery.wildhack.ui.fragments.main.AnswerAdapter
import com.astery.wildhack.ui.stt.SPTUsable
import com.astery.wildhackvolunteers.ui.fragments.profile.ProfileViewModel
import com.astery.wildhackvolunteers.databinding.FragmentProfileBinding
import com.astery.wildhackvolunteers.databinding.FragmentQuestionBinding
import com.astery.wildhackvolunteers.ui.activity.ParentActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class QuestionFragment : TFragment(), SPTUsable{

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private var answerAdapter: AnswerAdapter? = null

    private val viewModel: QuestionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as ParentActivity).setSearch(true, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.answers.observe(viewLifecycleOwner){it ->
            answerAdapter = AnswerAdapter(it, requireContext())
            binding.recyclerView.adapter = answerAdapter
            binding.recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun getText(value: String) {
        Timber.d("result: $value")
        viewModel.getAnswers(value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as ParentActivity).setSearch(false, this)
    }

}
