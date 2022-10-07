package com.pro.sofitdemo.views.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pro.sofitdemo.MainActivity
import com.pro.sofitdemo.databinding.FragmentHomeBinding
import com.pro.sofitdemo.models.Drink
import com.pro.sofitdemo.views.adapter.RecycleViewAdapter


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val homeAdapter by lazy { RecycleViewAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.homeRec.adapter = homeAdapter


        activity?.let {
            if (it is MainActivity) {
                if (it.pref?.getUserState(requireContext()) == false) {
                    binding.search.filters = arrayOf(InputFilter.LengthFilter(30))
                    val string = it.pref?.getNameString(requireContext())
                    if (string != null) {
                        it.drinkViewModel.getDrinksByName(string)
                        binding.search.setText(string)
                    }
                    binding.nameRadio.isChecked = true
                } else {
                    binding.search.filters = arrayOf(InputFilter.LengthFilter(1))
                    val string = it.pref?.getAlphabetString(requireContext())
                    if (string != null) {
                        it.drinkViewModel.getDrinksByAlphabet(string)
                        binding.search.setText(string)
                    }
                    binding.alphabetRadio.isChecked = true
                }
            }
        }
        binding.radioGroup.setOnCheckedChangeListener { p0, checkedId ->
            when (checkedId) {
                binding.nameRadio.id -> {
                    binding.search.filters = arrayOf(InputFilter.LengthFilter(30))
                    activity?.let {
                        if (it is MainActivity) {
                            it.pref?.setUserState(requireContext(), false)
                        }
                    }
                }
                binding.alphabetRadio.id -> {
                    binding.search.filters = arrayOf(InputFilter.LengthFilter(1))
                    activity?.let {
                        if (it is MainActivity) {
                            it.pref?.setUserState(requireContext(), true)
                        }
                    }
                }
            }
        }
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty()) {
                    when (binding.radioGroup.checkedRadioButtonId) {
                        binding.nameRadio.id -> {
                            binding.search.filters = arrayOf(InputFilter.LengthFilter(30))
                            activity?.let {
                                if (it is MainActivity) {
                                    it.pref?.setNameString(requireContext(), s.toString())
                                    it.drinkViewModel.getDrinksByName(s.toString())

                                }
                            }
                        }
                        binding.alphabetRadio.id -> {
                            binding.search.filters = arrayOf(InputFilter.LengthFilter(1))
                            activity?.let {
                                if (it is MainActivity) {
                                    it.pref?.setAlphabetString(requireContext(), s.toString())
                                    it.drinkViewModel.getDrinksByAlphabet(s.toString())
                                }
                            }
                        }
                    }
                }
            }

        })
        initObserver()
        return binding.root
    }

    private fun initObserver() {
        activity?.let {
            if (it is MainActivity) {
                it.drinkViewModel.drinkResponse.observe(viewLifecycleOwner) {
                    Log.i("testingtag", "initObserver: ")
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()?.drinks != null) {
                            if (it.body()?.drinks!!.isNotEmpty()) {
                                homeAdapter.setData(it.body()?.drinks as MutableList<Drink>)
                            } else {

                            }
                        }
                    }
                }
                it.drinkViewModel.drinkResponse2.observe(viewLifecycleOwner) {
                    if (it.isSuccessful && it.body() != null) {
                        if (it.body()?.drinks != null) {
                            if (it.body()?.drinks!!.isNotEmpty()) {
                                homeAdapter.setData(it.body()?.drinks as MutableList<Drink>)
                            } else {

                            }
                        }
                    }
                }
                it.drinkViewModel.homeUpdater.observe(viewLifecycleOwner) {
                    if (it) {
                        homeAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}