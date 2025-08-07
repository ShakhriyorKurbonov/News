package com.k.shakhriyor.news.presentation.main_activity.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.data.common.Constants
import com.k.shakhriyor.news.databinding.BottomSheetDialogBinding
import com.k.shakhriyor.news.databinding.FragmentProfileBinding
import com.k.shakhriyor.news.databinding.LanguageBottomSheetDialogBinding
import com.k.shakhriyor.news.databinding.NotificationSettingsBottomSheetDialogBinding
import com.k.shakhriyor.news.databinding.UiModeBottomSheetDialogBinding
import com.k.shakhriyor.news.presentation.SplashActivity
import com.k.shakhriyor.news.presentation.main_activity.MainActivity
import com.k.shakhriyor.news.presentation.main_activity.home.HomeViewModel
import com.k.shakhriyor.news.util.setLocal
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

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

        binding.nightModeContainer.setOnClickListener {
            setUiMode()
        }

        binding.logOut.setOnClickListener {
            logOut()
        }

        binding.languageContainer.setOnClickListener {
            setLanguage()
        }

        binding.notificationContainer.setOnClickListener {
            notificationSettings()
        }

    }

    private fun setLanguage() {

        val bottomDialog=BottomSheetDialog(requireContext())
        val bind=LanguageBottomSheetDialogBinding.inflate(layoutInflater)
        bottomDialog.setContentView(bind.root)
        bottomDialog.setCancelable(false)


        viewModel.getLanguage()?.let {
            it.let { langCode->
                when(langCode){
                    Constants.UZ->{bind.radioGroup.check(R.id.uzbekRadio)}
                    Constants.UZK->{bind.radioGroup.check(R.id.uzbekKRadio)}
                    Constants.RU->{bind.radioGroup.check(R.id.rusRadio)}
                    Constants.EN->{bind.radioGroup.check(R.id.engRadio)}
                    Constants.KK->{bind.radioGroup.check(R.id.kazRadio)}
                    Constants.KY->{bind.radioGroup.check(R.id.kyRadio)}
                    Constants.TG->{bind.radioGroup.check(R.id.tgRadio)}
                    Constants.KAA->{bind.radioGroup.check(R.id.kaaRadio)}
                }
            }
        }

        bind.saveBtn.setOnClickListener {
            val radioButtonId=bind.radioGroup.checkedRadioButtonId
            when(radioButtonId){
                R.id.uzbekRadio->{
                    setLocal(requireActivity(),Constants.UZ)
                    viewModel.changeLanguage(Constants.UZ)
                }
                R.id.uzbekKRadio->{
                    setLocal(requireActivity(),Constants.UZK)
                    viewModel.changeLanguage(Constants.UZK)
                }
                R.id.rusRadio->{
                    setLocal(requireActivity(),Constants.RU)
                    viewModel.changeLanguage(Constants.RU)
                }
                R.id.engRadio->{
                    setLocal(requireActivity(),Constants.EN)
                    viewModel.changeLanguage(Constants.EN)
                }
                R.id.kazRadio->{
                    setLocal(requireActivity(),Constants.KK)
                    viewModel.changeLanguage(Constants.KK)
                }
                R.id.kyRadio->{
                    setLocal(requireActivity(),Constants.KY)
                    viewModel.changeLanguage(Constants.KY)
                }
                R.id.tgRadio->{
                    setLocal(requireActivity(),Constants.TG)
                    viewModel.changeLanguage(Constants.TG)
                }
                R.id.kaaRadio->{
                    setLocal(requireActivity(),Constants.KAA)
                    viewModel.changeLanguage(Constants.KAA)
                }
            }
            bottomDialog.dismiss()
            Intent(requireContext(),SplashActivity::class.java).apply {
                startActivity(this)
            }
            activity?.finish()
        }

        bottomDialog.show()
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

    private fun logOut(){
        viewModel.logOut()
        Intent(requireContext(),SplashActivity::class.java).apply {
            startActivity(this)
        }
        activity?.finish()
    }

    private fun notificationSettings() {
        val bottomDialog= BottomSheetDialog(requireContext())
        val bind= NotificationSettingsBottomSheetDialogBinding.inflate(layoutInflater)
        bottomDialog.setContentView(bind.root)
        bottomDialog.setCancelable(false)

        val isChecked=viewModel.getNotificationStatus()
        bind.voiceSwitch.isChecked=isChecked

        viewVisibility(bind.radioGroup,isChecked)

        val isVoiced=viewModel.getNotificationImportanceStatus()
        if (isVoiced){
            bind.radioGroup.check(R.id.voicedRadio)
        }else{
            bind.radioGroup.check(R.id.silentRadio)
        }

        bind.voiceSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewVisibility(bind.radioGroup,isChecked)
        }

        bind.saveBtn.setOnClickListener {


            viewModel.changeNotificationStatus(bind.voiceSwitch.isChecked)

            val isVoiced= bind.radioGroup.checkedRadioButtonId==R.id.voicedRadio

            viewModel.changeNotificationImportanceStatus(isVoiced)

            bottomDialog.dismiss()

        }


        bottomDialog.show()

    }


    private fun viewVisibility(view:View,isChecked: Boolean){
        if (isChecked){
            view.visibility= View.VISIBLE
        }else{
            view.visibility= View.GONE
        }
    }

}

