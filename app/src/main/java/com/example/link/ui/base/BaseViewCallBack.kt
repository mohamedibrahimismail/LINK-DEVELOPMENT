package com.example.link.ui.base

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * BaseViewCallBack is used as call back for all helper methods in Activity&Fragment
 * @author Mohamed Ibrahim
 */
interface BaseViewCallBack {
    fun showLoading(loading: Boolean)

    fun hideLoading()

    fun openActivityOnTokenExpire()

    fun onError(@StringRes resId: Int)

    fun onError(message: String?)

    fun onError(
        textString: String, actionString: String?, icon: Drawable?,
        actionListener: View.OnClickListener?
    )

    fun onError(
        @StringRes errorTextRes: Int,
        @StringRes errorActionRes: Int,
        @DrawableRes errorIcon: Int,
        errorActionListener: View.OnClickListener?
    )

    fun showMessage(message: String?)

    fun showMessage(@StringRes resId: Int)

    fun isNetworkConnected(): Boolean

    fun hideKeyboard(activity: Activity)
}