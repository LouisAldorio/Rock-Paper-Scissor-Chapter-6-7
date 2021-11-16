package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.catnip.rockpaperscissorchapter6and7.base.BaseFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentLoginBinding
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
), LoginContract.View {

    private lateinit var viewModel: LoginViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun observeViewModel() {
        viewModel.loginResponse.observe(viewLifecycleOwner, { response ->
            if (response is Resource.Success) {
                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                response.data?.data.let{
                    it?.let {
                        it1 -> saveSessionLogin(it1)
                        val intent = Intent(requireContext(), IntroActivity::class.java)
                        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
            }
            else if (response is Resource.Error) {
                Toast.makeText(context, "Login Failed, Please check email and password correctly", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun navigateToMenu() {
        TODO("Not yet implemented")
    }

    override fun navigateToRegister() {
        TODO("Not yet implemented")
    }

    override fun setOnClick() {
        getViewBinding().cvButtonAuth.setOnClickListener {
            viewModel.loginUser(
                AuthRequest(
                    email = getViewBinding().etEmail.text.toString(),
                    password = getViewBinding().etPassword.text.toString()
                )
            )
        }
    }

    override fun saveSessionLogin(data: UserData) {
        SessionPreference(requireContext()).authToken = data.token
    }

    override fun initView() {
        initViewModel()
        setOnClick()
    }

    override fun initViewModel() {
        val dataSource = AuthApiDataSourceImpl(
            AuthApiService.invoke(LocalDataSourceImpl(
            SessionPreference(requireContext())
        )))
        val repository = LoginRepository(dataSource)
        viewModel = GenericViewModelFactory(LoginViewModel(repository))
            .create(LoginViewModel::class.java)
        observeViewModel()
    }
}