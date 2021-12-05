package com.astery.wildhack.ui.fragments

import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis

/** base fragment for all */
abstract class TFragment : Fragment() {

    fun setTransition(first: Boolean) {
        if (first) {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        } else {
            enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
            returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        }
    }
}