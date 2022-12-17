package com.fasa.runtracker.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.fasa.runtracker.R
import com.fasa.runtracker.databinding.FragmentSetupBinding
import com.fasa.runtracker.util.Constants.KEY_FIRST_TIME_TOGGLE
import com.fasa.runtracker.util.Constants.KEY_NAME
import com.fasa.runtracker.util.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {
    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var isFirstTimeOpen = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        val view = binding.root

        if (!isFirstTimeOpen) {
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOption
            )
        }

        binding.tvContinue.setOnClickListener {
            val success = writePersonalDataToSharedPreferences()
            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                Snackbar.make(requireView(), "Please enter all the fields!", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }


        return view
    }

    private fun writePersonalDataToSharedPreferences(): Boolean {
        val name = binding.etNameWelcome.text.toString()
        val weight = binding.etWeightWelcome.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences
            .edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()
        val toolbarText = "Let's go, $name"
        requireActivity().findViewById<MaterialTextView>(R.id.tv_toolbar_title).text = toolbarText
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}