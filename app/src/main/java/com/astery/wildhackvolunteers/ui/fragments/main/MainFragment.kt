package com.astery.wildhackvolunteers.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astery.wildhackvolunteers.model.Task
import com.astery.wildhack.ui.adapterUtils.BlockListener
import com.astery.wildhack.ui.fragments.TFragment
import com.astery.wildhack.ui.fragments.main.AnswerAdapter
import com.astery.wildhackvolunteers.databinding.FragmentMainBinding
import com.astery.wildhackvolunteers.model.TaskId
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : TFragment(), MainFragmentTransport {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private var taskAdapter: TaskAdapter? = null
    private var answerAdapter: AnswerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInfo()

        viewModel.tasks.observe(viewLifecycleOwner) { list ->
            taskAdapter = TaskAdapter(list, requireContext())
            binding.tasks.adapter = taskAdapter
            binding.tasks.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            taskAdapter?.blockListener = object:BlockListener{
                override fun onClick(position: Int) {
                    doTask(taskAdapter!!.units!![position])
                }
            }
        }

        viewModel.answers.observe(viewLifecycleOwner) { list ->
            answerAdapter = AnswerAdapter(list, requireContext())
            binding.answers.adapter = answerAdapter
            binding.answers.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            answerAdapter?.blockListener = object:BlockListener{
                override fun onClick(position: Int) {
                    viewModel.forget(answerAdapter!!.units!![position])
                    answerAdapter?.units!!.removeAt(position)
                    answerAdapter?.notifyItemRemoved(position)
                }
            }
        }

        viewModel.wrongTask.observe(viewLifecycleOwner){ it ->
            openWrongDialogue(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun askQuestion(){
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToQuestionFragment())
    }
    override fun doTask(task: Task){
        findNavController().navigate(task.id.action)
    }
    override fun aboutUs(){
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToAboutUsFragment())
    }


    fun openWrongDialogue(field: TaskId){
        ProfileWrongDialogue(layoutInflater, requireContext(), field).setOnOkListener {
            findNavController().navigate(field.action)
        }.show()
    }
}
// https://kronoki.ru/ru/volunteerism/programs/

interface MainFragmentTransport{
    fun askQuestion()
    fun aboutUs()
    fun doTask(task: Task)
}