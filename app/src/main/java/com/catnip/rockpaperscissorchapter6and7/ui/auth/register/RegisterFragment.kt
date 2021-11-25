package com.catnip.rockpaperscissorchapter6and7.ui.auth.register

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.local.room.PlayersDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.PlayersDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.model.Player
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentRegisterBinding
import com.catnip.rockpaperscissorchapter6and7.ui.auth.AuthActivity
import com.catnip.rockpaperscissorchapter6and7.utils.StringUtils
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
), RegisterContract.View {

    private lateinit var viewModel: RegisterViewModel

    override fun setClickListener() {
        getViewBinding().cvButtonAuth.setOnClickListener {
            if (checkFormValidation()) {
                viewModel.postRegisterUser(
                    RegisterRequest(
                        getViewBinding().etEmail.text.toString(),
                        getViewBinding().etName.text.toString(),
                        getViewBinding().etPassword.text.toString()
                    )
                )
            }
        }
    }

    override fun checkFormValidation(): Boolean {
        val email = getViewBinding().etEmail.text.toString()
        val name = getViewBinding().etName.text.toString()
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
        when {
            name.isEmpty() -> {
                isFormValid = false
                getViewBinding().tilName.isErrorEnabled = true
                getViewBinding().tilName.error = getString(R.string.text_error_name_empty)
            }
            name.count() < 6 -> {
                isFormValid = false
                getViewBinding().tilName.isErrorEnabled = true
                getViewBinding().tilName.error = getString(R.string.text_error_name_short)
            }
            name.count() > 10 -> {
                isFormValid = false
                getViewBinding().tilName.isErrorEnabled = true
                getViewBinding().tilName.error = getString(R.string.text_error_name_long)
            }
            else -> {
                getViewBinding().tilName.isErrorEnabled = false
            }
        }
        if (pass.isEmpty()) {
            isFormValid = false
            getViewBinding().tilPassword.isErrorEnabled = true
            getViewBinding().tilPassword.error = getString(R.string.text_error_password_empty)
        } else getViewBinding().tilPassword.isErrorEnabled = false

        return isFormValid
    }

    override fun initViewModel() {
        val dataSource = AuthApiDataSourceImpl(
            AuthApiService.invoke(
                LocalDataSourceImpl(
                    SessionPreference(requireContext()),
                    UserPreference(requireContext())
                )
            )
        )
        val playersDataSource =
            PlayersDataSourceImpl(PlayersDatabase.getInstance(requireContext()).playersDao())
        val repository = RegisterRepository(dataSource, playersDataSource)
        viewModel =
            GenericViewModelFactory(RegisterViewModel(repository)).create(RegisterViewModel::class.java)
        observeViewModel()
    }

    override fun observeViewModel() {
        val dialog = Dialog(requireContext())
        viewModel.getResponseLiveData().observe(requireActivity(), { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(dialog, true)
                }
                is Resource.Success -> {
                    showLoading(dialog, false)
                    if (response.data!!.isSuccess) {
                        showToast(true, getString(R.string.text_register_success))
                        saveToDao(response.data.data.username)
                    }
                    initView()
                }
                is Resource.Error -> {
                    showLoading(dialog, false)
                    val msg = response.message.toString()
                    when {
                        "email_1 dup key" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_error_register_failed,
                                getString(R.string.text_error_email_exist)
                            )
                        )
                        "username_1 dup key" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_error_register_failed,
                                getString(R.string.text_error_name_exist)
                            )
                        )
                        "alphanumeric characters" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_error_register_failed,
                                getString(R.string.text_error_name_alphanumeric)
                            )
                        )
                    }
                }
            }
        })
    }

    private fun saveToDao(userName: String) {
        viewModel.saveToDao(userName)
    }

    override fun showLoading(dialog: Dialog, isLoading: Boolean) {
        if (isLoading) {
            dialog.window?.setTitle(Window.FEATURE_NO_TITLE.toString())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.loading_screen)
            dialog.setCancelable(false)
            dialog.show()
        } else dialog.cancel()
    }

    override fun showToast(isSuccess: Boolean, msg: String) {
        if (isSuccess) {
            FancyToast.makeText(
                requireContext(),
                msg,
                Toast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                true
            ).show()
        } else {
            FancyToast.makeText(
                requireContext(),
                msg,
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                true
            ).show()
        }
    }

    override fun initView() {
        getViewBinding().etName.setText("")
        getViewBinding().etEmail.setText("")
        getViewBinding().etPassword.setText("")
        setClickListener()
        initViewModel()
    }
}