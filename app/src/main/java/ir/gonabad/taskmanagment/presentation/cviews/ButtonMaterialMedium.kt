package ir.gonabad.taskmanagment.presentation.cviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import ir.gonabad.taskmanagment.di.qualifier.MediumTypeface
import javax.inject.Inject

@AndroidEntryPoint
class ButtonMaterialMedium: MaterialButton {
    @Inject
    @MediumTypeface
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