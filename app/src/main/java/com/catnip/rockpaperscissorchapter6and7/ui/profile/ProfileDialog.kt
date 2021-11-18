package com.catnip.rockpaperscissorchapter6and7.ui.profile

import androidx.core.view.isVisible
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.DialogFragmentProfileBinding
import com.shashank.sony.fancytoastlib.FancyToast
import android.content.Intent
import android.view.ViewGroup
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.ui.splashscreen.SplashScreenActivity


class ProfileDialog : BaseViewModelDialogFragment<DialogFragmentProfileBinding>(
    DialogFragmentProfileBinding::inflate
), ProfileContract.View {

    private lateinit var profileViewModel: ProfileViewModel

    private fun validate() : Boolean {
        if (getViewBinding().etEmail.text.toString().trim() != "" && getViewBinding().etUsername.text.toString().trim() != "") {
            return true
        }
        return false
    }

    override fun initView() {
        dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getViewBinding().btnCancel.setOnClickListener {
            dismiss()
        }
        getViewBinding().btnUpdate.setOnClickListener {
            if (validate()) {
                profileViewModel.updateProfile(getViewBinding().etUsername.text.toString(),getViewBinding().etEmail.text.toString())

            }else {
                FancyToast.makeText(
                    requireContext(),
                    "Username or Email must not be Empty!",
                    FancyToast.LENGTH_LONG,
                    FancyToast.WARNING,
                    true
                ).show();
            }
        }
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getProfileData()
    }

    override fun initViewModel() {
        val datasource = AuthApiDataSourceImpl(AuthApiService.invoke(
            LocalDataSourceImpl(
                SessionPreference(requireContext())
            )
        ))

        val repository = ProfileRepository(datasource)
        profileViewModel = GenericViewModelFactory(ProfileViewModel(repository)).create(ProfileViewModel::class.java)

        profileViewModel.transactionResult.observe(this, { response ->
            determineAction(false, response)
        })

        profileViewModel.updateTransactionResult.observe(this, { response ->
            determineAction(true, response)
        })
    }

    override fun setProfileData(email: String, username: String) {
        getViewBinding().etEmail.setText(email)
        getViewBinding().etUsername.setText(username)
    }

    override fun showContent(isContentVisible: Boolean) {
        super.showContent(isContentVisible)

        getViewBinding().clContent.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().pbLoading.isVisible = isLoading
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)

        getViewBinding().tvMessage.isVisible = isErrorEnabled
        getViewBinding().tvMessage.text = msg
    }

    private fun restartApplicationAndClearPreference() {

        val intent = Intent(requireContext(), SplashScreenActivity::class.java)
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

        //todo : clear the expired token that is stored under the shared preference


        dismiss()
    }

    private fun closeDialogWhenUpdateSuccess() {
        FancyToast.makeText(
            requireContext(),
            "Successfully Updated Profile Data!",
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            true
        ).show();
        dismiss()
    }

    private fun determineAction(isUpdateTransaction : Boolean,
                                response: Resource<BaseAuthResponse<UserData, String>>) {

        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showError(false, null)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    if (!it.isSuccess) {
                        showError(true, it.errorMsg)
                        showContent(false)
                    } else {
                        showError(false, null)
                        showContent(true)

                        if(isUpdateTransaction) {
                            closeDialogWhenUpdateSuccess()
                        }else
                            it.data.email?.let { it1 -> it.data.username?.let { it2 ->
                                setProfileData(it1,
                                    it2
                                )
                            }}
                    }
                }
            }
            is Resource.Error -> {
                if(response.message == "Token is expired") {
                    restartApplicationAndClearPreference()
                }else {
                    showLoading(false)
                    showError(true, response.message)
                    showContent(false)
                }
            }
        }
    }

}