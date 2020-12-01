package com.enclave.barry.recipeapp.app.recipeDetail.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.enclave.barry.recipeapp.R
import com.enclave.barry.recipeapp.app.recipeDetail.vm.RecipeDetailViewModel
import com.enclave.barry.recipeapp.base.BaseBindingActivity
import com.enclave.barry.recipeapp.data.local.model.Recipe
import com.enclave.barry.recipeapp.databinding.ActivityRecipeDetailBinding
import com.enclave.barry.recipeapp.util.ReadFilePermission
import com.enclave.barry.recipeapp.util.RealPathUtil
import timber.log.Timber

const val IMAGE_PICK_INTENT = 1998

class RecipeDetailActivity :
    BaseBindingActivity<ActivityRecipeDetailBinding, RecipeDetailViewModel>() {

    override val layoutId = R.layout.activity_recipe_detail
    override val viewModelClass = RecipeDetailViewModel::class
    private var avatarPicked = ""
    private var isUpdate = false

    companion object {
        const val ADD_RECIPE_ACTION = "add recipe"
        const val UPDATE_RECIPE_ACTION = "update recipe"
        const val UPDATE_OBJECT_ID = "recipe object id"
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.viewmodel = viewModel
        val isAddAction = intent.getBooleanExtra(ADD_RECIPE_ACTION, false)
        if (isAddAction) {
            viewModel.initData(
                Recipe(name = "", description = "", ingredients = listOf(), steps = listOf())
            )
        } else {
            isUpdate = true
            val idRecipe = intent.getIntExtra(UPDATE_OBJECT_ID, -1)
            viewModel.loadRecipe(idRecipe)
        }
        setupToolbar()
        setupSpinner()
        setupButtons()
        setupListIngredients()
        setUpListSteps()
        ReadFilePermission.verifyStoragePermissions(this)
        setupResultListener()
        setupVMObserver()
        seyupKeyboard()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun seyupKeyboard() {
        binding.scrollLayout.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }


    private fun setupVMObserver() {
        //default img
        Glide.with(this).load(ContextCompat.getDrawable(this, R.drawable.defaultfood))
            .into(binding.imageRecipe)

        viewModel.currentRecipe.observe(this, {
            val typeRecipe = it.type
            if (!it.img.isNullOrEmpty()) {
                Glide.with(this).load(it.img).into(binding.imageRecipe)
            }
            if (isUpdate) {
                binding.txtActivityTitle.text = it.name
                binding.spinnerRecipeTypes.postDelayed({
                    binding.spinnerRecipeTypes.setSelection(
                        viewModel.listRecipeTypes.value!!.indexOf(viewModel.listRecipeTypes.value!!.find { item -> item.id == typeRecipe })
                    )
                }, 200)
            }
        })
    }

    private fun setupResultListener() {
        viewModel.saveResult.observe(this, {
            if (it) {
                Toast.makeText(this, "Save successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        viewModel.deleteResult.observe(this, {
            if (it) {
                Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun setUpListSteps() {
        val adapter = StepAdapter(onDelete = {
            viewModel.removeStep(it.id!!)
        }, onUpdate = {
            viewModel.updateStep(it)
        })
        binding.listSteps.adapter = adapter
        viewModel.listSteps.observe(this, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupListIngredients() {
        val adapter = IngredientAdapter(onDelete = {
            viewModel.removeIngredient(it.id!!)
        }, onUpdate = {
            viewModel.updateIngredient(it)
        })
        binding.listIngredients.adapter = adapter
        viewModel.listIngredients.observe(this, {
            Timber.w("List recieve $it")
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    //onclick events and validate fields
    private fun setupButtons() {
        binding.imageRecipe.setOnClickListener {
            onShowImagePicker()
        }
        binding.imgChangeImage.setOnClickListener {
            onShowImagePicker()
        }
        binding.btnSave.setOnClickListener {
            val recipeName = binding.edtRecipeName.text.toString().trim()
            if (recipeName.isEmpty()) {
                binding.edtRecipeName.apply {
                    setText("")
                    hint = getString(R.string.validate_empty_field)
                    setHintTextColor(ContextCompat.getColor(context, R.color.invalidate_hint_text))
                }
            } else {
                viewModel.saveRecipe()
            }
        }
        binding.btnAddIngredient.setOnClickListener {
            val ingredient = binding.edtIngredient.text.toString().trim()
            if (ingredient.isEmpty()) {
                binding.edtIngredient.apply {
                    setText("")
                    hint = getString(R.string.validate_empty_field)
                    setHintTextColor(ContextCompat.getColor(context, R.color.invalidate_hint_text))
                }
            } else {
                binding.edtIngredient.apply {
                    hint = getString(R.string.hint_enter_ingredient_here)
                    setHintTextColor(ContextCompat.getColor(context, R.color.default_hint_text))
                }
                viewModel.addIngredient(ingredient)
                binding.edtIngredient.text.clear()
            }
        }
        binding.btnAddStep.setOnClickListener {
            val step = binding.edtStep.text.toString().trim()
            if (step.isEmpty()) {
                binding.edtStep.apply {
                    setText("")
                    hint = getString(R.string.validate_empty_field)
                    setHintTextColor(ContextCompat.getColor(context, R.color.invalidate_hint_text))
                }
            } else {
                binding.edtStep.apply {
                    hint = getString(R.string.hint_enter_step_here)
                    setHintTextColor(ContextCompat.getColor(context, R.color.default_hint_text))
                }
                viewModel.addStep(step)
                binding.edtStep.text.clear()
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setupSpinner() {
        viewModel.listRecipeTypes.observe(this, {
            val listTypes = it.map { recipeType ->
                recipeType.name!!
            }
            val adapter = ArrayAdapter(this, R.layout.spinner_item, listTypes)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerRecipeTypes.adapter = adapter
        })
        binding.spinnerRecipeTypes.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(_0: AdapterView<*>?, _1: View?, _2: Int, _3: Long) {
                viewModel.setRecipeType(binding.spinnerRecipeTypes.selectedItem as String)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isUpdate) {
            menuInflater.inflate(R.menu.recipe_detail_menu, menu)
            menu?.findItem(R.id.delete_menu)?.actionView?.setOnClickListener {
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
                Timber.d("Clicked")
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_menu -> viewModel.deleteRecipe()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onShowImagePicker() {
        val intent = Intent()
        ReadFilePermission.verifyStoragePermissions(this)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_INTENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICK_INTENT && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            avatarPicked = RealPathUtil.getRealPath(this, uri!!).toString()
            viewModel.setImageRecipe(avatarPicked)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}