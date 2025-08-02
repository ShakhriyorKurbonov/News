package com.k.shakhriyor.news.presentation.intro_activity.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.k.shakhriyor.news.R
import com.k.shakhriyor.news.databinding.FragmentSignUpBinding
import com.k.shakhriyor.news.presentation.main_activity.MainActivity
import com.k.shakhriyor.news.util.makeStatusBarTransparent
import com.k.shakhriyor.news.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeToLiveData()
        makeStatusBarTransparent()
    }

    private fun subscribeToLiveData() {
        viewModel.loading.observe(viewLifecycleOwner){isLoading->
            binding.progressBar.isVisible=isLoading
            binding.signUpBtn.text=if (isLoading) "" else getString(R.string.fragment_sign_in_sign_up)
        }
        viewModel.events.observe(viewLifecycleOwner){
            when(it){
                SignUpViewModel.Event.ConnectionError -> toast(requireContext(),"Connection Error")
                SignUpViewModel.Event.Error -> toast(requireContext(),"Error")
            }
        }
        viewModel.isLoggedIn.observe(viewLifecycleOwner){
            if (it){
                Intent(requireContext(),MainActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun initUI()= with(binding) {
        signUpBtn.setOnClickListener {
            val fullName=fullNameEt.text.toString()
            val email=emailEt.text.toString()
            val password=passwordEt.text.toString()
            viewModel.signUp(fullName, email, password)
        }
    }

}