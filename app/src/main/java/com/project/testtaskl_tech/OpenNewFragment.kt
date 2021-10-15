package com.project.testtaskl_tech

import androidx.fragment.app.Fragment

@FunctionalInterface
interface OpenNewFragment {

    fun openFragment(fragment: Fragment, isAddBackStack: Boolean)
}