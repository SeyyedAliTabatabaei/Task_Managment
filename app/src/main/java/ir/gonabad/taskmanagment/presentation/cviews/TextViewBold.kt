package ir.gonabad.taskmanagment.presentation.cviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import dagger.hilt.android.AndroidEntryPoint
import ir.gonabad.taskmanagment.di.qualifier.BoldTypeface
import javax.inject.Inject
@AndroidEntryPoint
class TextViewBold:androidx.appcompat.widget.AppCompatTextView {
    @Inject
    @BoldTypeface
    lateinit var typeFace : Typeface

    constructor(context: Context):super(context){
        setTextViewTypeface()
    }

    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        setTextViewTypeface()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int):super(context,attrs, defStyleAttr){
        setTextViewTypeface()
    }

    private fun setTextViewTypeface() {
        typeface = this.typeFace
    }
}