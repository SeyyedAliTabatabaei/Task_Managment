package ir.gonabad.taskmanagment.presentation.cviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.annotation.DrawableRes
import ir.gonabad.taskmanagment.R

class ExposedDropDownMenuCustomAdapter(
    myContext: Context,
    private val itemResIdLayout: Int,
    textViewResId: Int,
    private val contentArray: List<String>,
    @DrawableRes private val selectedItemBackgroundColor : Int,
    private val ltrTextDirection:Boolean = false,
    private val iconListResIds:List<Int>? = null
) : ArrayAdapter<String>(myContext, itemResIdLayout, textViewResId, contentArray) {

    private var selectedItemPosition = 0
    private var selectedView = false

    fun setSelectedItemPosition(position:Int){
        selectedItemPosition = position
    }

    override fun getCount(): Int {
        return contentArray.size
    }

    override fun getPosition(item: String?): Int {
        return contentArray.indexOf(item)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (position == selectedItemPosition) {
            if (!selectedView){
                selectedView = true
                val view = super.getView(position, convertView, parent)
                return view
            }
            val view = LayoutInflater.from(parent.context)
                .inflate(itemResIdLayout, parent, false)
            val textView = view.findViewById<TextViewMedium>(R.id.tv_itemExposedDropDownMenu_item)
            textView.setTextColor(context.getColor(R.color.white))
            textView.text = contentArray[position]
            if (ltrTextDirection) textView.textDirection = View.TEXT_DIRECTION_LTR
            selectedView = false
            return view
        }
        if(ltrTextDirection){
            val view = LayoutInflater.from(parent.context)
                .inflate(itemResIdLayout, parent, false)
            val textView = view.findViewById<TextViewMedium>(R.id.tv_itemExposedDropDownMenu_item)
            textView.text = contentArray[position]
            textView.textDirection = View.TEXT_DIRECTION_LTR
            return view
        }

        val view = super.getView(position, convertView, parent)
        return view
    }
}