package com.mauto.myapplication.prebooking

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.mauto.myapplication.R
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.Collapse
import com.mindorks.placeholderview.annotations.expand.Expand
import com.mindorks.placeholderview.annotations.expand.Parent
import com.mindorks.placeholderview.annotations.expand.SingleTop


@Parent
@SingleTop
@Layout(R.layout.header_layout)
class HeaderView(private val mContext: Context, private val mHeaderText: String,private val mHeaderText1: String,private val mHeaderText2: String,private val mHeaderText3: String) {
    @View(R.id.txt_cell1)
    var headerText: TextView? = null


    @View(R.id.txt_cell2)
    var headerText1: TextView? = null


    @View(R.id.txt_cell4)
    var headerText2: TextView? = null


    @View(R.id.txt_cell5)
    var headerText3: TextView? = null
    @Resolve
    private fun onResolve() {
        headerText!!.text = mHeaderText
        headerText1!!.text = mHeaderText1
        headerText2!!.text = mHeaderText2
        headerText3!!.text = mHeaderText3
    }

    @View(R.id.child_image)
    var headerImage: ImageView? = null

    @Expand
    private fun onExpand() {

        headerImage!!.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_icon_minus));
    }

    @Collapse
    private fun onCollapse() {
        headerImage!!.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_icon_plus));
    }

    companion object {
        private const val TAG = "HeaderView"
    }
}