package com.astery.wildhack.ui.fragments.main

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.astery.wildhack.model.Answer
import com.astery.wildhack.ui.adapterUtils.BaseViewHolder
import com.astery.wildhack.ui.adapterUtils.BlockListener
import com.astery.wildhackvolunteers.R
import com.astery.wildhackvolunteers.databinding.UnitAnswerBinding
import com.astery.xapplication.ui.adapterUtils.BaseAdapter

class AnswerAdapter(units: ArrayList<Answer>?, context: Context) :
    BaseAdapter<AnswerAdapter.ViewHolder, Answer>(units, context) {

    var isWholeCardClickable:Boolean = true

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
        if (isWholeCardClickable) binding.clickable.setImageDrawable(context.getDrawable(R.drawable.ic_forward))
    }

    override fun onViewDetachedFromWindow(h: BaseViewHolder) {
        super.onViewDetachedFromWindow(h)
        val binding = (h as ViewHolder).binding
        binding.unbind()
    }

    inner class ViewHolder(val binding: UnitAnswerBinding) :
        BaseViewHolder(blockListener, binding.root){
            init{
                if (!isWholeCardClickable){
                    binding.root.setOnClickListener {}
                    binding.clickable.setOnClickListener{blockListener?.onClick(adapterPosition)}
                }
            }
        }
}