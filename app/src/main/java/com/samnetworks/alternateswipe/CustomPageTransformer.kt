package com.samnetworks.alternateswipe

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CustomPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position >= 0) {
            page.elevation = 1f - 0.3f * position
            page.translationY = -page.height * position
        } else {
            page.translationY = -page.height * position
            if (page is CustomShatterFrame) {
                page.advance(position)
            }
        }
    }
}
