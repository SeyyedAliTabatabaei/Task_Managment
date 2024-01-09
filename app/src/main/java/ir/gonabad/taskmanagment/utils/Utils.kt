package ir.gonabad.taskmanagment.utilsimport android.content.Contextimport android.util.TypedValueimport androidx.annotation.AttrResimport androidx.annotation.ColorIntimport dagger.hilt.android.qualifiers.ApplicationContext@ColorIntfun getValueFromTheme(@ApplicationContext context: Context, @AttrRes resId: Int): Int {    val typedValue = TypedValue()    context.theme.resolveAttribute(resId, typedValue, true)    return typedValue.data}fun convertToEnglishNumber(text : String) : String {    var t = text    t = t.replace("۰" , "0")    t = t.replace("۱" , "1")    t = t.replace("۲" , "2")    t = t.replace("۳" , "3")    t = t.replace("۴" , "4")    t = t.replace("۵" , "5")    t = t.replace("۶" , "6")    t = t.replace("۷" , "7")    t = t.replace("۸" , "8")    t = t.replace("۹" , "9")    return t}