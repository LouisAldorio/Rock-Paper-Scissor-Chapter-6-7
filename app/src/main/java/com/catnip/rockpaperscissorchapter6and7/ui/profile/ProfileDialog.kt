package com.catnip.rockpaperscissorchapter6and7.ui.profile

import android.util.Log
import androidx.core.view.isVisible
import com.catnip.rockpaperscissorchapter6and7.base.BaseViewModelDialogFragment
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.UserPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.PreferenceDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.services.AuthApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.DialogFragmentProfileBinding
import com.shashank.sony.fancytoastlib.FancyToast

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
        val datasource = AuthApiDataSourceImpl(AuthApiService.invoke(PreferenceDataSourceImpl(
            UserPreference(requireContext())
        )))

        val repository = ProfileRepository(datasource)
        profileViewModel = GenericViewModelFactory(ProfileViewModel(repository)).create(ProfileViewModel::class.java)

        profileViewModel.transactionResult.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showError(false, null)
                    showContent(false)
                }
                is Resource.Success -> {
                    Log.i("sampe", "masuk error")
                    showLoading(false)
                    response.data?.let {
                        if (!it.isSuccess) {
                            showError(true, it.errorMsg)
                            showContent(false)
                        } else {
                            showError(false, null)
                            showContent(true)

                            it.data.email?.let { it1 -> it.data.username?.let { it2 ->
                                setProfileData(it1,
                                    it2
                                )
                            } }
                        }
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, response.data?.errorMsg)
                    showContent(false)
                }
            }
        })
    }

    override fun setProfileData(email: String, username: String) {
        Log.i("sampe", username + email)
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
}