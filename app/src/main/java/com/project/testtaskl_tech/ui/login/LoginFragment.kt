package com.project.testtaskl_tech.ui.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.github.vacxe.phonemask.PhoneMaskManager
import com.project.testtaskl_tech.OpenNewFragment
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.StateSuccess
import com.project.testtaskl_tech.data.RepositoryImpl
import com.project.testtaskl_tech.databinding.FragmentLoginBinding
import com.project.testtaskl_tech.remote.RemoteMaskPhone
import com.project.testtaskl_tech.ui.dev_exam.DevExamFragment
import com.project.testtaskl_tech.utility.autoCleared
import com.project.testtaskl_tech.utility.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var viewBinding: FragmentLoginBinding by autoCleared()
    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentLoginBinding.bind(view)
        observeSignIn()
        observeMaskPhone()
        observeOnSuccessSignIn()
        viewModel.getSuccessSignIn(RepositoryImpl.SP_KEY_PASSWORD_PHONE)

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

    // the phone mask comes in the form of "+44 ХХХХ-ХХХХХХ"
    // it is split into two parts
    // region = +44
    // mask = ХХХХ-ХХХХХХ
    // then a symbol is taken from the mask to know what the mask consists of
    // maskSymbol = Х

    private fun createETWithMask(remoteMask: StateSuccess) {
        val remoteMaskPhone = (remoteMask as RemoteMaskPhone).phoneMask
        val splitMask = Regex(REGEX_SPLIT_MASK).split(remoteMaskPhone, 2)
        val region = splitMask[0]
        val mask = splitMask[1]
        val maskSymbol = Regex(REGEX_MASK_SYMBOL).find(mask)?.groups?.get(0)?.value

        runCatching {
            PhoneMaskManager()
                .withMaskSymbol(maskSymbol.toString())
                .withMask(" $mask")
                .withRegion(region)
                .bindTo(viewBinding.etPhoneNumber)
        }
    }

    // the phone number is converted to the format to be sent to the server

    private fun phoneNumberToSendToTheServer(phone: String): String {
        val formattedNumber = StringBuffer()
        Regex(REGEX_PHONE_FOR_SERVER).findAll(phone).forEach {
            formattedNumber.append(it.value)
        }
        return formattedNumber.toString()
    }

    private fun observeSignIn() {
        viewModel.signInLiveDate.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is LoginLoadState.Success -> {
                    openFragment()
                }
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

    // the phone number and password from the memory come in the form 1234567&password
    // and is converted to (& - separator)
    // phoneNumber = 1234567
    // password = password

    private fun observeOnSuccessSignIn() {
        viewModel.successSignInLiveDate.observe(viewLifecycleOwner) {
            if (it != null) {
                val dataLogin = it.split("&")
                val phoneNumber = dataLogin[0]
                val password = dataLogin[1]
                viewBinding.etPhoneNumber.setText(phoneNumber)
                viewBinding.etPassword.setText(password)
            } else {
                viewModel.getMaskPhone()
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
        const val REGEX_SPLIT_MASK = "\\s"
    }
}