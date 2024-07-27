package com.paymob.movieapp.presentation.base

import androidx.fragment.app.Fragment
import com.paymob.movieapp.data.network.error.ErrorEntity
import com.paymob.movieapp.data.network.error.handleError
import com.paymob.movieapp.presentation.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    fun onLoading(show: Boolean) {
        try {
            (requireActivity() as? MainActivity)?.displayLoading(show)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun handleError(errorEntity: ErrorEntity?): String {
        return requireContext().handleError(errorEntity)
    }

}