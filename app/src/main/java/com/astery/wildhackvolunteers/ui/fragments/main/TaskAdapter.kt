package com.astery.wildhackvolunteers.ui.fragments.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.astery.wildhackvolunteers.model.Task
import com.astery.wildhack.ui.adapterUtils.BaseViewHolder
import com.astery.wildhackvolunteers.databinding.UnitTaskBinding
import com.astery.xapplication.ui.adapterUtils.BaseAdapter

class TaskAdapter(units: ArrayList<Task>?, context: Context) :
    BaseAdapter<TaskAdapter.ViewHolder, Task>(units, context) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = UnitTaskBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(h: BaseViewHolder, position: Int) {
        val binding = (h as ViewHolder).binding
        binding.task = units!![position]
    }

    override fun onViewDetachedFromWindow(h: BaseViewHolder) {
        super.onViewDetachedFromWindow(h)
        val binding = (h as ViewHolder).binding
        binding.unbind()
    }

    inner class ViewHolder(val binding: UnitTaskBinding) :
        BaseViewHolder(blockListener, binding.root)
}