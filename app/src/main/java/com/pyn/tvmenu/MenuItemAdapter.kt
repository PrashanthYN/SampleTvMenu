package com.pyn.tvmenu

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.RecyclerView
import com.pyn.tvmenu.pojo.MenuItem
import kotlinx.android.synthetic.main.menu_item_view.view.*
import java.util.*


/**
 * Created by Prashant on 4/29/2020.
 */
class MenuItemAdapter(var menuList: ArrayList<MenuItem>) :
    RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    private var selectedPosition = -1
    private var itemFocusListener: ItemFocusListener? = null
    private var selectedView: View?=null

    interface ItemFocusListener {
        fun onMenuItemFocus(x: Int, viewWidth: Int)
    }

    fun parentScrolled() {
        if (selectedView != null) {
            publishEvent(selectedView!!)
        }
    }

    fun setItemFocusListener(focusListener: ItemFocusListener) {
        this.itemFocusListener = focusListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_view, null)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val itemView = holder.itemView
        itemView.menuItemLayout.setOnFocusChangeListener { v, hasFocus ->
            Log.d("Position:" + position + ",Focused:", hasFocus.toString())

            if (hasFocus) {
                selectedView = v
                publishEvent(v)
                if (selectedPosition > position) {
                    itemView.focusIndicator.animation = inFromRightAnimation()
                } else {
                    itemView.focusIndicator.animation = outToRightAnimation()
                }
                selectedPosition = position

                itemView.focusIndicator.visibility = View.VISIBLE
            } else {
                //itemView.focusIndicator.animation=inFromRightAnimation()
                itemView.focusIndicator.visibility = View.GONE
            }
        }
        if (menuList[position].iconOnly) {
            itemView.menuIconView.visibility = View.VISIBLE
            itemView.menuTextView.visibility = View.GONE
            itemView.menuIconView.setImageResource(menuList[position].icon)

        } else {
            itemView.menuIconView.visibility = View.GONE
            itemView.menuTextView.visibility = View.VISIBLE
            itemView.menuTextView.text = menuList[position].title
        }
    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    fun inFromRightAnimation(): Animation? {
        val inFromRight: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, +1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        )
        inFromRight.setDuration(100)
        inFromRight.setInterpolator(AccelerateInterpolator())
        return inFromRight
    }

    fun outToRightAnimation(): Animation? {
        val outtoLeft: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, -1.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        )
        outtoLeft.setDuration(100)
        outtoLeft.setInterpolator(AccelerateInterpolator())
        return outtoLeft
    }

    fun publishEvent(v: View) {
        val originalPos = IntArray(2)
        v.getLocationOnScreen(originalPos)
        val x = originalPos[0]
        val width:Int = v.measuredWidth
        itemFocusListener?.onMenuItemFocus(x, width)
    }

}