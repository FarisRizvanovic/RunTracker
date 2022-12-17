package com.fasa.runtracker.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fasa.runtracker.R
import com.fasa.runtracker.databinding.FragmentSettingsBinding
import com.fasa.runtracker.util.Constants.KEY_NAME
import com.fasa.runtracker.util.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container, false)
        val view = binding.root

        loadFieldsFromSharedPReferences()

        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPreferences()
            if (success){
                Snackbar.make(view, "Changes saved.", Snackbar.LENGTH_SHORT).show()
            }else{
                Snackbar.make(view, "Can't save empty fields.", Snackbar.LENGTH_SHORT).show()
            }
        }


        return view
    }

    fun loadFieldsFromSharedPReferences(){
        val name = sharedPreferences.getString(KEY_NAME, "")?:""
        val weight = sharedPreferences.getFloat(KEY_WEIGHT, 80f)

        binding.etName.setText(name)
        binding.etWeight.setText(weight.toString())
    }

    fun applyChangesToSharedPreferences() : Boolean{
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()
        if(name.isEmpty() || weight.isEmpty()){
            return false
        }
        sharedPreferences.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .apply()

        val toolbarText = "Let's go $name"
        requireActivity().findViewById<MaterialTextView>(R.id.tv_toolbar_title).text = toolbarText
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}