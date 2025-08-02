package com.k.shakhriyor.news.presentation.intro_activity.sign_in

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.k.shakhriyor.news.R
import dagger.hilt.android.AndroidEntryPoint
import com.k.shakhriyor.news.databinding.FragmentSignInBinding
import com.k.shakhriyor.news.presentation.main_activity.MainActivity
import com.k.shakhriyor.news.util.makeStatusBarTransparent
import com.k.shakhriyor.news.util.toast

@AndroidEntryPoint
class SignInFragment: Fragment() {
    private val viewModel by viewModels<SignInViewModel>()
    private lateinit var binding:FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        subscribeToLiveData()
        makeStatusBarTransparent()
    }

    private fun subscribeToLiveData() {
        viewModel.loading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible=it
            binding.loginBtn.text=if (it) "" else getString(R.string.fragment_sign_in_log_in)
        }
        viewModel.events.observe(viewLifecycleOwner){
            when(it){
                SignInViewModel.Event.ConnectionError -> toast(requireContext(),"Connection Error")
                SignInViewModel.Event.Error -> toast(requireContext(),"Error")
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
      val email= emailEt.text.toString()
      val password= passwordEt.text.toString()
        loginBtn.setOnClickListener {
            viewModel.signIn(email, password)
        }

        signUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSignUpFragment())
        }
    }


}