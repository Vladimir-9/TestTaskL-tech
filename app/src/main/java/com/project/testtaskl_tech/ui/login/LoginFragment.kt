package com.project.testtaskl_tech.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.vacxe.phonemask.PhoneMaskManager
import com.project.testtaskl_tech.StateSuccess
import com.project.testtaskl_tech.OpenNewFragment
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.FragmentLoginBinding
import com.project.testtaskl_tech.remote.RemoteMaskPhone
import com.project.testtaskl_tech.ui.dev_exam.DevExamFragment
import com.project.testtaskl_tech.utility.autoCleared
import com.project.testtaskl_tech.utility.toast

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var viewBinding: FragmentLoginBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentLoginBinding.bind(view)
        viewModel.getMaskPhone()
        observeSignIn()
        observeMaskPhone()
        viewBinding.btSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val phoneNumber =
            phoneNumberToSendToTheServer(viewBinding.etPhoneNumber.text.toString())
        val password = viewBinding.etPassword.text.toString()
        viewModel.signIn(phoneNumber, password)
    }

    private fun createETWithMask(remoteMask: StateSuccess) {
        val splitMask = Regex("\\s").split((remoteMask as RemoteMaskPhone).phoneMask, 2)
        val region = splitMask[0]
        val mask = splitMask[1]
        val maskSymbol = Regex(REGEX_MASK_SYMBOL).find(mask)?.groups?.get(0)?.value

        PhoneMaskManager()
            .withMaskSymbol(maskSymbol.toString())
            .withMask(" $mask")
            .withRegion(region)
            .bindTo(viewBinding.etPhoneNumber)
    }

    private fun phoneNumberToSendToTheServer(phone: String): String {
        val formattedNumber = StringBuffer()
        Regex(REGEX_PHONE_FOR_SERVER).findAll(phone).forEach {
            formattedNumber.append(it.value)
        }
        return formattedNumber.toString()
    }

    private fun observeMaskPhone() {
        viewModel.maskPhoneLiveDate.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is LoginLoadState.Success -> {
                    createETWithMask(loadState.response)
                    visibleProgressBar(false)
                }
                is LoginLoadState.Error -> {
                    toast("wrong data")
                    visibleProgressBar(false)
                }
                is LoginLoadState.LoadState -> visibleProgressBar(true)
            }
        }
    }

    private fun observeSignIn() {
        viewModel.signInLiveDate.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is LoginLoadState.Success -> openFragment()
                is LoginLoadState.Error -> {
                    toast("wrong data")
                    visibleProgressBar(false)
                }
                is LoginLoadState.LoadState -> {
                    visibleProgressBar(true)
                }
            }
        }
    }

    private fun visibleProgressBar(isVisible: Boolean) {
        viewBinding.progressBar.isVisible = isVisible
        viewBinding.etPhoneNumber.isVisible = isVisible.not()
        viewBinding.etPassword.isVisible = isVisible.not()
        viewBinding.btSignIn.isVisible = isVisible.not()
    }

    private fun openFragment() {
        (requireActivity() as? OpenNewFragment)?.openFragment(DevExamFragment(), false)
    }

    companion object {
        const val REGEX_MASK_SYMBOL = "[a-zA-Z]|[а-яА-Я]"
        const val REGEX_PHONE_FOR_SERVER = "\\d"
    }
}