package com.rscorp.quicknotes.util.extensions

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.show() {
    if (visibility == View.VISIBLE) return

    val parent = parent as ViewGroup
    // View needs to be laid out to create a snapshot & know position to animate. If view isn't
    // laid out yet, need to do this manually.
    if (!isLaidOut) {
        measure(
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST)
        )
        layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
    }

    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    drawable.setBounds(left, parent.height, right, parent.height + height)
    parent.overlay.add(drawable)
    ValueAnimator.ofInt(parent.height, top).apply {
        startDelay = 100L
        duration = 300L
        interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.linear_out_slow_in
        )
        addUpdateListener {
            val newTop = it.animatedValue as Int
            drawable.setBounds(left, newTop, right, newTop + height)
        }
        doOnEnd {
            parent.overlay.remove(drawable)
            visibility = View.VISIBLE
        }
        start()
    }
}

/**
 * Potentially animate hiding a [BottomNavigationView].
 *
 * Abruptly changing the visibility leads to a re-layout of main content, animating
 * `translationY` leaves a gap where the view was that content does not fill.
 *
 * Instead, take a snapshot, instantly hide the view (so content lays out to fill), then animate
 * out the snapshot.
 */
fun BottomNavigationView.hide() {
    if (visibility == View.GONE) return

    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    val parent = parent as ViewGroup
    drawable.setBounds(left, top, right, bottom)
    parent.overlay.add(drawable)
    visibility = View.GONE
    ValueAnimator.ofInt(top, parent.height).apply {
        startDelay = 100L
        duration = 200L
        interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.fast_out_linear_in
        )
        addUpdateListener {
            val newTop = it.animatedValue as Int
            drawable.setBounds(left, newTop, right, newTop + height)
        }
        doOnEnd {
            parent.overlay.remove(drawable)
        }
        start()
    }
}

fun View.makeGone(){
    this.visibility = View.GONE
}

fun View.makeVisible(){
    this.visibility = View.VISIBLE
}

fun View.makeInVisible() {
    this.visibility = View.INVISIBLE
}


fun View.anim() {
    when (this) {
        is ImageView -> {
            val drawable = this.drawable
            when (drawable) {
                is AnimatedVectorDrawable -> {
                    drawable.start()
                }
                is AnimatedVectorDrawableCompat -> {
                    drawable.start()
                }
            }
        }
        is AppCompatImageView -> {
            val drawable = this.drawable
            when (drawable) {
                is AnimatedVectorDrawable -> {
                    drawable.start()
                }
                is AnimatedVectorDrawableCompat -> {
                    drawable.start()
                }
            }
        }
    }
}

fun View.anim(drawableId: Int) {
    val drawable = ContextCompat.getDrawable(this.context, drawableId)
    when (this) {
        is ImageView -> {
            setImageDrawable(drawable)
            when (drawable) {
                is AnimatedVectorDrawable -> {
                    drawable.start()
                }
                is AnimatedVectorDrawableCompat -> {
                    drawable.start()
                }
            }
        }
        is AppCompatImageView -> {
            setImageDrawable(drawable)
            when (drawable) {
                is AnimatedVectorDrawable -> {
                    drawable.start()
                }
                is AnimatedVectorDrawableCompat -> {
                    drawable.start()
                }
            }
        }
    }
}

fun View.margin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.toFloat()?.run { leftMargin = dpToPx(this) }
        top?.toFloat()?.run { topMargin = dpToPx(this) }
        right?.toFloat()?.run { rightMargin = dpToPx(this) }
        bottom?.toFloat()?.run { bottomMargin = dpToPx(this) }
    }
}




inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()