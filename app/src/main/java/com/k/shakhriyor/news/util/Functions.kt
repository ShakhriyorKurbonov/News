package com.k.shakhriyor.news.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.k.shakhriyor.news.R

fun toast(context: Context,string: String){
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

fun Window.makeStatusBarTransparent(withDarkIcons: Boolean = true) {
    var flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    if (withDarkIcons) {
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    decorView.systemUiVisibility = flags
    statusBarColor = Color.TRANSPARENT
}

fun Fragment.makeStatusBarTransparent() {
    activity?.window?.makeStatusBarTransparent()
}

fun Fragment.hideKeyboard(){
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard(){
    view?.let { activity?.showKeyboard(it) }
}

fun Activity.hideKeyboard(){
    hideKeyboard(currentFocus?:View(this))
}

fun Context.hideKeyboard(view: View){
    val inputMethodManager=getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
}

fun Context.showKeyboard(view: View){
    val inputMethodManager=getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view,0)
}



fun showViewBottomAnim(context: Context, view: View?){
    view?.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context,R.anim.translation_bottom_show_y))
}
fun hideViewBottomAnim(context: Context, view: View?){
    view?.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context,R.anim.translation_bottom_hide_y))
}
fun hideViewBottom(context: Context, view: View?){
    view?.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context,R.anim.alpha_bottom_hide_y))
}
fun showViewTop(context: Context, view: View?){
    view?.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context,R.anim.translation_top_show_y))
}
fun hideViewTop(context: Context,view: View?){
    view?.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context,R.anim.translation_top_hide_y))
}