package com.astery.wildhack.ui.fragments.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.astery.wildhack.model.Answer
import com.astery.wildhack.ui.adapterUtils.BaseViewHolder
import com.astery.wildhackvolunteers.databinding.UnitAnswerBinding
import com.astery.xapplication.ui.adapterUtils.BaseAdapter

class AnswerAdapter(units: ArrayList<Answer>?, context: Context) :
    BaseAdapter<AnswerAdapter.ViewHolder, Answer>(units, context) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = UnitAnswerBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(h: BaseViewHolder, position: Int) {
        val binding = (h as ViewHolder).binding
        binding.answer = units!![position]
    }

    override fun onViewDetachedFromWindow(h: BaseViewHolder) {
        super.onViewDetachedFromWindow(h)
        val binding = (h as ViewHolder).binding
        binding.unbind()
    }

    inner class ViewHolder(val binding: UnitAnswerBinding) :
        BaseViewHolder(blockListener, binding.root)
}