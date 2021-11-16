package com.catnip.rockpaperscissorchapter6and7.ui.access.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar.BinarApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.services.BinarApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment(), RegisterContract.View{
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setClickListener()

    }

    override fun setClickListener() {
        binding.btnRegister.setOnClickListener {
            viewModel.postRegisterUser(RegisterRequest(
                binding.tfEmail.editText?.text.toString(),
                binding.tfUsername.editText?.text.toString(),
                binding.tfPassword.editText?.text.toString()
            ))
        }
    }

    override fun initViewModel() {
        val dataSource = BinarApiDataSourceImpl(BinarApiService.invoke(LocalDataSourceImpl(
            SessionPreference(requireContext())
        )))
        val repository = RegisterRepository(dataSource)
        viewModel = GenericViewModelFactory(RegisterViewModel(repository)).create(RegisterViewModel::class.java)
        observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.getResponseLiveData().observe(requireActivity(), { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.data!!.isSuccess) {
                        Toast.makeText(context, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, """
                        Pendaftaran gagal, pastikan username lebih dari 5 karakter atau coba email yang lain atau username yang lain
                    """.trimIndent(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}