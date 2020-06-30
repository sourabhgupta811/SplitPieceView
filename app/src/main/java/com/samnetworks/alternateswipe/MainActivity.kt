package com.samnetworks.alternateswipe

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Sourabh Gupta on 29/6/20.
 */
class MainActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpager.adapter = RecyclerViewAdapter()
        viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewpager.setPageTransformer(CustomPageTransformer())
        viewpager.offscreenPageLimit = 3
        viewpager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewpager.findViewWithTag<CustomShatterFrame>(position).visibility = View.VISIBLE
                (viewpager.findViewWithTag<CustomShatterFrame>(position) as CustomShatterFrame).animationRunning = false
                (viewpager.findViewWithTag<CustomShatterFrame>(position) as CustomShatterFrame).invalidate()
            }
        })
    }
}