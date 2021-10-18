package com.project.testtaskl_tech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.testtaskl_tech.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OpenNewFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: openFragment(LoginFragment(), false)
    }

    override fun openFragment(fragment: Fragment, isAddBackStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (isAddBackStack)
                addToBackStack(null)
            replace(R.id.container_fragment, fragment)
            commit()
        }
    }
}