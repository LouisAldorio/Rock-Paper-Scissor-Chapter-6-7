package com.catnip.rockpaperscissorchapter6and7.ui.access.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.catnip.rockpaperscissorchapter6and7.base.GenericViewModelFactory
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.SessionPreference
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.LocalDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.datasource.binar.BinarApiDataSourceImpl
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.services.BinarApiService
import com.catnip.rockpaperscissorchapter6and7.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
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

        viewModel.postRegisterUser(RegisterRequest(
            "hermasasdd.yp@gmail.com",
            "aaaasd",
            "123se456"
        ))
    }

    fun initViewModel() {
        val dataSource = BinarApiDataSourceImpl(BinarApiService.invoke(LocalDataSourceImpl(
            SessionPreference(requireContext())
        )))
        val repository = RegisterRepository(dataSource)
        viewModel = GenericViewModelFactory(RegisterViewModel(repository)).create(RegisterViewModel::class.java)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.getResponseLiveData().observe(requireActivity(), { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("HABIS", "observeViewModel: ${response.data}")
                    Log.d("HABIS", "observeViewModel: ${response.message}")
                }
                is Resource.Error -> {
                    Log.d("HABIS", "ERROR observeViewModel: ${response.data}")
                    Log.d("HABIS", "ERROR observeViewModel: ${response.message}")
                }
            }
        })
    }
}