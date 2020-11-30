package com.enclave.barry.recipeapp.app.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enclave.barry.recipeapp.app.main.vm.MainViewModel
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.databinding.ItemRecipeBinding
import java.io.File

class RecipeAdapter(val viewModel: MainViewModel, val onclick: (Recipe) -> Unit) :
    ListAdapter<Recipe, RecipeAdapter.ListRecipeViewHolder>(recipeDiffCallBack) {
    object recipeDiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    class ListRecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe?, viewModel: MainViewModel, onclick: (Recipe) -> Unit) {
            if (item != null) {
                binding.model = item
                binding.txtType.text = item.type.let { viewModel.getNameOfRecipeTypeByID(it) }
                binding.imgRecipe.adjustViewBounds
                binding.clickEvent = onclick
                if (item.img != null) {
                    val imgFile = File(item.img)
                    if (imgFile.exists()) {
                        Glide.with(binding.root.context).load(imgFile).into(binding.imgRecipe)
                    }
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecipeViewHolder {
        return ListRecipeViewHolder(
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListRecipeViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel, onclick)
    }
}