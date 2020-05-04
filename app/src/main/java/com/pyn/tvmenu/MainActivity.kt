package com.pyn.tvmenu

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.leanback.widget.HorizontalGridView
import androidx.recyclerview.widget.RecyclerView
import com.pyn.tvmenu.pojo.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : Activity(),MenuItemAdapter.ItemFocusListener{

    lateinit var focusedIndicatorView: View
    var overallXScroll:Int=0
    var menuList:ArrayList<MenuItem> = ArrayList()

    companion object{
        val TAG:String="MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        focusedIndicatorView=findViewById(R.id.focusIndicator)

        //Glide.with(applicationContext).load("https://cdn.europosters.eu/image/hp/4928.jpg").into(posterBackgorundImageView)

        val adapter=MenuItemAdapter(menuList)
        adapter.setItemFocusListener(this)
        menuContainer.adapter=adapter
        menuContainer.isFocusedByDefault=true
        menuContainer.addItemDecoration(ItemOffsetDecoration(16))
         menuContainer.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val transaltionX=menuContainer.translationX
            Log.d(TAG,"Horizontal gridview scrolled from {$oldScrollX} to {$scrollX} transaltionX:{$transaltionX}")
            adapter.parentScrolled()
        }
        addMenuItems()
        adapter.notifyDataSetChanged()
    }

    override fun onMenuItemFocus(x: Int, viewWidth: Int) {
        Log.d(TAG,"Screen location of focused view:{$x}, and width:{$viewWidth}")
        val animation = ObjectAnimator.ofFloat(focusedIndicatorView, "translationX", x.toFloat()-26)
        animation.duration=50
        animation.start()
        var layoutParams= LinearLayout.LayoutParams(viewWidth+10,focusedIndicatorView.measuredHeight)
        focusedIndicatorView.layoutParams=layoutParams

        val widthAnimator = ValueAnimator.ofInt(focusedIndicatorView.measuredWidth, viewWidth+10)
        widthAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            focusedIndicatorView.getLayoutParams().width = animatedValue
            focusedIndicatorView.requestLayout()
        }
        widthAnimator.duration=50
        widthAnimator.start()


    }

    fun addMenuItems(){
        menuList.add(MenuItem("Search",R.drawable.lb_ic_in_app_search,1,true))
        menuList.add(MenuItem("Home",R.drawable.lb_ic_search_mic,2,false))
        menuList.add(MenuItem("Premium",0,4,false))
        menuList.add(MenuItem("TvShows",0,4,false))
        menuList.add(MenuItem("Videos",0,4,false))
        menuList.add(MenuItem("Music",0,4,false))
        menuList.add(MenuItem("Sports",0,4,false))
        menuList.add(MenuItem("Kids",0,4,false))
        menuList.add(MenuItem("Comedy",0,4,false))
        menuList.add(MenuItem("Action movies",0,4,false))
        menuList.add(MenuItem("Popular",0,4,false))
        menuList.add(MenuItem("Latest",0,4,false))
        menuList.add(MenuItem("Up coming",0,4,false))
        menuList.add(MenuItem("Trending",0,4,false))
        menuList.add(MenuItem("News",0,4,false))
        menuList.add(MenuItem("Favourites",0,4,false))
        menuList.add(MenuItem("Apps & Games",0,4,false))

    }
}
