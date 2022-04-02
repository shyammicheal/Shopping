package com.vajro.shopping

import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty


fun Fragment.showToast( messageResId: String?, isSuccess: Boolean){
    if (isSuccess)
        Toasty.success(requireActivity(), messageResId!!, Toast.LENGTH_SHORT).show()
    else
        Toasty.error(requireActivity(), messageResId!!, Toast.LENGTH_SHORT).show()

}