package com.aisight.shopassist.ui.initiallogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aisight.shopassist.databinding.FragmentInitialLoginBinding

class InitialLoginFragment : Fragment() {

    private var _binding: FragmentInitialLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val initialLoginViewModel =
            ViewModelProvider(this)[InitialLoginViewModel::class.java]

        _binding = FragmentInitialLoginBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val textView: TextView = binding.textInitialLogin
        initialLoginViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}