package com.catnip.rockpaperscissorchapter6and7.ui.auth.register

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.catnip.rockpaperscissorchapter6and7.R
import com.catnip.rockpaperscissorchapter6and7.base.BaseFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentRegisterBinding
import com.catnip.rockpaperscissorchapter6and7.utils.StringUtils
import com.shashank.sony.fancytoastlib.FancyToast


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
), RegisterContract.View {

    private lateinit var viewModel: RegisterViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setClickListener()
    }

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
                showToast(
                    false,
                    getString(R.string.text_fill_email)
                )
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_check_email)
                )
            }
            name.isEmpty() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_fill_name)
                )
            }
            name.count() < 6 -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_name_shorter_than_minimum)
                )
            }
            pass.isEmpty() -> {
                isFormValid = false
                showToast(
                    false,
                    getString(R.string.text_fill_password)
                )
            }
        }
        return isFormValid
    }

    override fun initViewModel() {
        val dataSource = AuthApiDataSourceImpl(
            AuthApiService.invoke(LocalDataSourceImpl(SessionPreference(requireContext())))
        )

        val repository = RegisterRepository(dataSource)
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
                    if (response.data!!.isSuccess) {
                        showLoading(dialog, false)
                        showToast(true, getString(R.string.text_register_success))
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
                                R.string.text_register_failed,
                                getString(R.string.text_email_is_exist)
                            )
                        )
                        "username_1 dup key" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_register_failed,
                                getString(R.string.text_name_is_exist)
                            )
                        )
                        "alphanumeric characters" in msg -> showToast(
                            false,
                            getString(
                                R.string.text_register_failed,
                                getString(R.string.text_name_should_only_alphanumeric)
                            )
                        )
                    }
                }
            }
        })
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
        initViewModel()
    }
}