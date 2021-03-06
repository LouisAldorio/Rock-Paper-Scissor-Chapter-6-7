package com.catnip.rockpaperscissorchapter6and7.ui.auth.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Toast
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentLoginBinding
import com.catnip.rockpaperscissorchapter6and7.ui.auth.AuthActivity
import com.catnip.rockpaperscissorchapter6and7.ui.intro.IntroActivity
import com.catnip.rockpaperscissorchapter6and7.utils.StringUtils
import com.shashank.sony.fancytoastlib.FancyToast

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
), LoginContract.View {

    private lateinit var viewModel: LoginViewModel

    override fun observeViewModel() {
        val dialog = Dialog(requireContext())
        viewModel.loginResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(dialog, true)
                }
                is Resource.Success -> {
                    showLoading(dialog, false)
                    showToast(true, getString(R.string.text_login_success))
                    response.data?.data.let {
                        it?.let { it1 ->
                            saveSessionLogin(it1)
                            saveUsername(it1)
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(dialog, false)
                    val msg = response.message.orEmpty()
                    showToast(
                        false,
                        getString(R.string.text_error_login_failed, msg)
                    )
                    navigateToRegister()
                }
            }
        })
    }

    private fun navigateToRegister() {
        /*AuthActivity().setViewPager()*/
    }

    private fun saveUsername(data: UserData) {
        val userName = data.username.orEmpty()
        viewModel.saveUsername(userName)
    }

    override fun showLoading(dialog: Dialog, isLoading: Boolean) {
        super.showLoading(isLoading)
        if (isLoading) {
            dialog.window?.setTitle(Window.FEATURE_NO_TITLE.toString())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.loading_screen)
            dialog.setCancelable(false)
            dialog.show()
        } else dialog.cancel()
    }

    override fun showToast(isSuccess: Boolean, msg: String) {
        super.showLoading(isSuccess)
        if (isSuccess) {
            FancyToast.makeText(
                requireContext(),
                msg,
                Toast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                true
            ).show()
            navigateToMenu()
        } else {
            FancyToast.makeText(
                requireContext(),
                msg,
                Toast.LENGTH_SHORT,
                FancyToast.ERROR,
                true
            ).show()
        }
    }

    override fun navigateToMenu() {
        val intent = Intent(requireContext(), IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun setOnClick() {
        getViewBinding().cvButtonAuth.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.loginUser(
                    AuthRequest(
                        email = getViewBinding().etEmail.text.toString(),
                        password = getViewBinding().etPassword.text.toString()
                    )
                )
            }
        }
    }

    override fun checkFormValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
        val pass = getViewBinding().etPassword.text.toString()
        var isFormValid = true
        when {
            email.isEmpty() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.text_error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.text_error_email_invalid)
            }
            else -> {
                getViewBinding().tilEmail.isErrorEnabled = false

            }
        }

        if (pass.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.text_error_password_empty)
        } else getViewBinding().tilPassword.isErrorEnabled = false

        return isFormValid
    }

    override fun saveSessionLogin(data: UserData) {
        viewModel.saveSession(data.token.orEmpty())
    }

    override fun initView() {
        initViewModel()
        setOnClick()
    }

    override fun initViewModel() {
        val apiDataSource = AuthApiDataSourceImpl(
            AuthApiService.invoke(
                LocalDataSourceImpl(
                    SessionPreference(requireContext()),
                    UserPreference(requireContext())
                )
            )
        )
        val localDataSource = LocalDataSourceImpl(
            SessionPreference(requireContext()),
            UserPreference(requireContext())
        )
        val repository = LoginRepository(apiDataSource,
            localDataSource,
            PlayersDataSourceImpl(PlayersDatabase.getInstance(requireContext()).playersDao())
        )
        viewModel = GenericViewModelFactory(LoginViewModel(repository))
            .create(LoginViewModel::class.java)
        observeViewModel()
    }
}