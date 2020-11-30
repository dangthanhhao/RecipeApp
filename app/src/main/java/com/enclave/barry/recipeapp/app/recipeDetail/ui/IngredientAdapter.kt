package com.enclave.barry.recipeapp.app.recipeDetail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enclave.barry.recipeapp.data.local.model.Ingredient
import com.enclave.barry.recipeapp.databinding.ItemIngredientBinding

class IngredientAdapter constructor(
    val onDelete: (Ingredient) -> Unit,
    val onUpdate: (Ingredient) -> Unit
) :
    ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(StringDiffCallback) {

    class IngredientViewHolder(val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Ingredient?,
            onDelete: (Ingredient) -> Unit,
            onUpdate: (Ingredient) -> Unit
        ) {
            if (item != null) {
                binding.model = item
                binding.clickEvent = onDelete
                binding.edtIngredient.addTextChangedListener {
                    item.content = it.toString()
                    onUpdate.invoke(item)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position), onDelete, onUpdate)
    }

    object StringDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
}