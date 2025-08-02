package com.k.shakhriyor.news.presentation.main_activity.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.databinding.BottomSheetDialogBinding
import com.k.shakhriyor.news.databinding.FragmentProfileBinding
import com.k.shakhriyor.news.databinding.UiModeBottomSheetDialogBinding
import com.k.shakhriyor.news.presentation.main_activity.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ProfileFragment: Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        subscribeToLiveData()
        viewModel.getPearson()
    }


    private fun subscribeToLiveData() {

        viewModel.loading.observe(viewLifecycleOwner) {
            if (binding.refreshLayout.isRefreshing){
                binding.refreshLayout.isRefreshing=it
            }else{
                binding.loading.root.isVisible=it
                binding.profileView.isVisible=!it
                Log.d("QAZ", "profileLoading: $it")
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.error.root.isVisible=it
            binding.profileView.isVisible=!it
            Log.d("QAZ", "profileError: $it")
        }
        viewModel.event.observe(viewLifecycleOwner) {
            when(it){
                HomeViewModel.Event.ConnectionError -> {
                    binding.error.errorText.text=getString(R.string.connection_error)}
                HomeViewModel.Event.Error -> {
                    binding.error.errorText.text=getString(R.string.error)}
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.avatar).into(binding.pearsonImage)
            binding.pearsonNameTv.text=it.fullName
            binding.pearsonEmailTv.text=it.email
        }
    }

    private fun initUi() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getPearson()
        }
        binding.refreshLayout.setColorSchemeResources(R.color.red)
        binding.error.retry.setOnClickListener {
            viewModel.getPearson()
        }

        binding.darkModeTv.setOnClickListener {
            setUiMode()
        }

    }



    private fun setUiMode(){
        val bottomDialog=BottomSheetDialog(requireContext())
        val bind=UiModeBottomSheetDialogBinding.inflate(layoutInflater)
        bottomDialog.setContentView(bind.root)
        bottomDialog.setCancelable(false)

        when(viewModel.getUiModeType()){
            AppCompatDelegate.MODE_NIGHT_YES->{
                bind.radioGroup.check(R.id.nightRadio)
            }
            AppCompatDelegate.MODE_NIGHT_NO->{
                bind.radioGroup.check(R.id.lightRadio)
            }
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM->{
                bind.radioGroup.check(R.id.systemRadio)
            }
        }

        bind.saveBtn.setOnClickListener {
            val radioBtnId=bind.radioGroup.checkedRadioButtonId
            when(radioBtnId){
                R.id.nightRadio->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    viewModel.changeUiModeType(AppCompatDelegate.MODE_NIGHT_YES)
                }
                R.id.lightRadio->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    viewModel.changeUiModeType(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.systemRadio->{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    viewModel.changeUiModeType(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            bottomDialog.dismiss()
        }
        bottomDialog.show()
    }
}