package com.enclave.barry.recipeapp.app.recipeDetail.ui

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.data.local.model.Step
import com.enclave.barry.recipeapp.databinding.ItemStepBinding

class StepAdapter constructor(val onDelete: (Step) -> Unit, val onUpdate: (Step) -> Unit) :
    ListAdapter<Step, StepAdapter.StepViewHolder>(StringDiffCallback) {

    class StepViewHolder(val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Step?, onDelete: (Step) -> Unit, onUpdate: (Step) -> Unit) {
            if (item != null) {
                binding.model = item
                binding.clickEvent = onDelete
                //validate and realtime update
                binding.edtContent.addTextChangedListener {
                    val step = it.toString().trim()
                    if (step.isEmpty()) {
                        binding.edtContent.apply {
                            backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    context,
                                    R.color.invalidate_hint_bg
                                )
                            )
                            hint = context.getString(R.string.validate_empty_field)
                            setHintTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.invalidate_hint_text
                                )
                            )
                        }
                    } else {
                        item.content = it.toString()
                        onUpdate.invoke(item)
                    }

                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder(
            ItemStepBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(getItem(position), onDelete, onUpdate)
    }

    object StringDiffCallback : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem == newItem
        }
    }
}