package com.project.testtaskl_tech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.testtaskl_tech.ui.deve_xam.DevExamFragment
import com.project.testtaskl_tech.ui.login.LoginFragment

class MainActivity : AppCompatActivity(), OpenNewFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: openFragment(DevExamFragment(), false)
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