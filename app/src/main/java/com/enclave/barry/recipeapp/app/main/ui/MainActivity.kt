package com.enclave.barry.recipeapp.app.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.app.login.ui.LoginActivity
import com.enclave.barry.recipeapp.app.main.vm.MainViewModel
import com.enclave.barry.recipeapp.app.recipeDetail.ui.RecipeDetailActivity
import com.enclave.barry.recipeapp.base.BaseBindingActivity
import com.enclave.barry.recipeapp.databinding.ActivityMainBinding
import com.enclave.barry.recipeapp.util.ReadFilePermission

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId = R.layout.activity_main
    override val viewModelClass = MainViewModel::class
    lateinit var listRecipesAdapter: RecipeAdapter

    override fun initView(savedInstanceState: Bundle?) {
        ReadFilePermission.verifyStoragePermissions(this)
        setupSpinner()
        setupListRecipe()
        setupButtons()
    }

    private fun setupButtons() {
        binding.fabAddRecipe.setOnClickListener {
            startActivity(
                Intent(this, RecipeDetailActivity::class.java).apply {
                    putExtra(RecipeDetailActivity.ADD_RECIPE_ACTION, true)
                })
        }
        binding.btnLogin.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }

    private fun setupSpinner() {
        viewModel.listRecipeTypes.observe(this, {
            val listTypes = mutableListOf(MainViewModel.FILTER_ALL)
            listTypes.addAll(it.map { recipeType ->
                recipeType.name!!
            })
            val adapter = ArrayAdapter(this, R.layout.spinner_item, listTypes)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerRecipeType.adapter = adapter
        })
        binding.spinnerRecipeType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(_0: AdapterView<*>?, _1: View?, _2: Int, _3: Long) {
                if (viewModel.listRecipes.value != null)
                    listRecipesAdapter.submitList(
                        viewModel.getListRecipesFiltered(
                            viewModel.listRecipes.value!!,
                            binding.spinnerRecipeType.selectedItem as String?
                        )
                    )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setupListRecipe() {
        listRecipesAdapter = RecipeAdapter(viewModel, onclick = {
            startActivity(
                Intent(this, RecipeDetailActivity::class.java).apply {
                    putExtra(RecipeDetailActivity.UPDATE_RECIPE_ACTION, true)
                    putExtra(RecipeDetailActivity.UPDATE_OBJECT_ID, it.id)
                })
        })
        binding.listRecipes.adapter = listRecipesAdapter
        viewModel.listRecipes.observe(this, {
            listRecipesAdapter.submitList(
                viewModel.getListRecipesFiltered(
                    it,
                    binding.spinnerRecipeType.selectedItem as String?
                )
            )
        })
    }
}