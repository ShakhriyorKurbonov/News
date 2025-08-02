package com.k.shakhriyor.news.presentation.main_activity.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.databinding.BottomSheetDialogBinding
import com.k.shakhriyor.news.databinding.FragmentSearchBinding
import com.k.shakhriyor.news.domain.model.LastedNew
import com.k.shakhriyor.news.domain.model.New
import com.k.shakhriyor.news.util.FlowLayoutItemClick
import com.k.shakhriyor.news.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment:Fragment() {
    private lateinit var  binding:FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private var filterList=mutableListOf<String>()
    private var categoriesList=mutableListOf<String>()
    val selectedChips = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToLiveData() {
        viewModel.liveSearchResponse.observe(viewLifecycleOwner) {
            setNewsAdapter(it.news)
            binding.resultTv.text="About ${it.results} results for \"${binding.searchEt.text}.\""
        }
        viewModel.loading.observe(viewLifecycleOwner){
            binding.loading.root.isVisible=it
            binding.newsList.isVisible=!it
        }
        viewModel.liveSearchOption.observe(viewLifecycleOwner) {
            categoriesList.addAll(it.categories)
          setAdapter()

            filterList.addAll(it.filter)


        }

        viewModel.loadSearchOption.observe(viewLifecycleOwner) {
            binding.categoryContainer.isVisible=it
        }


    }

    @SuppressLint("ResourceAsColor")
    private fun initUi() {
        val bottomNav=activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.visibility= View.GONE
        bottomNav?.animation=null
        closeScreen()

        binding.searchEt.setOnEditorActionListener{v,actionId,event->

            if (actionId== EditorInfo.IME_ACTION_SEARCH){
                setFilterBackground(R.drawable.gray_rectangle_16,R.color.black)
                setAdapter()
                setSearchAction(SearchAction.TextOnly)
                setNewsAdapter(emptyList())
                binding.resultTv.text=""
                hideKeyboard()
                binding.searchEt.clearFocus()
                return@setOnEditorActionListener true

            }

            false
        }


       binding.filterContainer.setOnClickListener {
           val bottomSheetDialog= BottomSheetDialog(requireContext())
           val bind= BottomSheetDialogBinding.inflate(layoutInflater)
           bottomSheetDialog.setContentView(bind.root)

           filterList.forEach {
               bind.chipLayout.addView(getChip(it))
           }

           bind.resetContainer.setOnClickListener {
               for(i in 0 until bind.chipLayout.childCount){
                   val chip=bind.chipLayout.getChildAt(i) as Chip
                   chip.isChecked=false
               }
           }

           bind.saveBtn.setOnClickListener {


               for (i in 0 until bind.chipLayout.childCount) {
                   val chip = bind.chipLayout.getChildAt(i) as Chip
                   if (chip.isChecked) {
                       selectedChips.add(chip.text.toString())
                   }
               }
               if (selectedChips.isNotEmpty()){
                   setSearchAction(SearchAction.Filter(selectedChips.joinToString(",")))
                   setFilterBackground(R.drawable.red_rectangle_16,R.color.white)
                   setAdapter()
                   setNewsAdapter(emptyList())
               }else{
                   setFilterBackground(R.drawable.gray_rectangle_16,R.color.black)
               }
               bottomSheetDialog.dismiss()
           }

           bottomSheetDialog.show()
       }

        viewModel.searchOption()

    }


    private fun setNewsAdapter(list: List<New>){
        binding.newsList.adapter= NewsAdapter(list){itemClick->
            val bundle= Bundle().apply {
                putParcelable(Constants.GET_NEWS,LastedNew(itemClick.author,itemClick.description,itemClick.image,itemClick.like,itemClick.postedDate,itemClick.title))
            }
            findNavController().navigate(R.id.to_newsItemFragment,bundle)
        }
    }

    sealed class SearchAction{
        class Category(val category: String): SearchAction()
        class Filter(val filter: String): SearchAction()
        object TextOnly: SearchAction()
    }

    private fun setSearchAction(searchAction: SearchAction){
        val text=binding.searchEt.text.toString()
        when(searchAction){
            is SearchAction.Category -> {
                viewModel.getSearchNewsByCategory(searchAction.category,text)
            }
            is SearchAction.Filter -> {
                viewModel.getSearchNewsByFilter(searchAction.filter,text)
            }
            SearchAction.TextOnly -> {
                viewModel.getSearchNews(binding.searchEt.text.toString())
            }
        }
    }




    @SuppressLint("ResourceAsColor")
    private fun getChip(text: String): Chip{
        val chip = Chip(requireContext())
        val layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        chip.text=text
        chip.setPadding(16,8,16,8)
        chip.layoutParams = layoutParams
        chip.isCheckable = true
        chip.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.chip_text_color))
        chip.setChipBackgroundColorResource(R.color.chip_backgrount_color)

        return chip
    }


    private fun setFilterBackground(backgroundId:Int,textColorId: Int){
        binding.filterContainer.setBackgroundResource(backgroundId)
        binding.filterTv.setTextColor(textColorId)
        binding.filterIc.setColorFilter(textColorId)
    }

    private fun setAdapter(){
        binding.categoriesList.adapter= CategoryAdapter(categoriesList){category->
            setSearchAction(SearchAction.Category(category))
            setNewsAdapter(emptyList())
            setFilterBackground(R.drawable.gray_rectangle_16,R.color.black)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun closeScreen(){
        binding.searchEt.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // RIGHT = 2
                val editText = v as EditText
                val drawable = editText.compoundDrawables[drawableEnd]

                if (drawable != null) {
                    // Touch editTextning o‘ng (end) qismiga to‘g‘ri keldi deb tekshiramiz
                    if (event.rawX >= (editText.right - drawable.bounds.width() - editText.paddingEnd)) {

                        findNavController().popBackStack()

                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

    }

}

