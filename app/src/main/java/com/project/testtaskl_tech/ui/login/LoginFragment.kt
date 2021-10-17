package com.project.testtaskl_tech.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.project.testtaskl_tech.OpenNewFragment
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.FragmentLoginBinding
import com.project.testtaskl_tech.ui.dev_exam.DevExamFragment

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewBinding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentLoginBinding.bind(view)

        viewBinding.btSignIn.setOnClickListener {
            openFragment()
//            lifecycleScope.launch {
//                Network.createRetrofit.authorization()
//            }
        }
    }

    private fun openFragment() {
        (requireActivity() as? OpenNewFragment)?.openFragment(DevExamFragment(), true)
    }
}