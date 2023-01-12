package com.codingtestwl.customView

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.graphics.Typeface
import android.util.AttributeSet
import com.codingtestwl.R

class RobotoTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {
        if (isInEditMode) return
        parseAttributes(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        if (isInEditMode) return
        parseAttributes(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        if (isInEditMode) return
        parseAttributes(attrs)
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val typeface: Int
        if (attrs == null) { //Not created from xml
            typeface = Roboto.ROBOTO_REGULAR
        } else {
            val values = context.obtainStyledAttributes(attrs, R.styleable.RobotoTextView)
            typeface = values.getInt(R.styleable.RobotoTextView_typeface, Roboto.ROBOTO_REGULAR)
            values.recycle()
        }
        setTypeface(getRoboto(typeface))
    }

    fun setRobotoTypeface(typeface: Int) {
        setTypeface(getRoboto(typeface))
    }

    private fun getRoboto(typeface: Int): Typeface? {
        return getRoboto(context, typeface)
    }

    object Roboto {
        const val ROBOTO_BLACK = 0
        const val ROBOTO_BLACK_ITALIC = 1
        const val ROBOTO_BOLD = 2
        const val ROBOTO_BOLD_ITALIC = 3
        const val ROBOTO_BOLD_CONDENSED = 4
        const val ROBOTO_BOLD_CONDENSED_ITALIC = 5
        const val ROBOTO_CONDENSED = 6
        const val ROBOTO_CONDENSED_ITALIC = 7
        const val ROBOTO_ITALIC = 8
        const val ROBOTO_LIGHT = 9
        const val ROBOTO_LIGHT_ITALIC = 10
        const val ROBOTO_MEDIUM = 11
        const val ROBOTO_MEDIUM_ITALIC = 12
        const val ROBOTO_REGULAR = 13
        const val ROBOTO_THIN = 14
        const val ROBOTO_THIN_ITALIC = 15
        var sRobotoBlack: Typeface? = null
        var sRobotoBlackItalic: Typeface? = null
        var sRobotoBold: Typeface? = null
        var sRobotoBoldItalic: Typeface? = null
        var sRobotoBoldCondensed: Typeface? = null
        var sRobotoBoldCondensedItalic: Typeface? = null
        var sRobotoCondensed: Typeface? = null
        var sRobotoCondensedItalic: Typeface? = null
        var sRobotoItalic: Typeface? = null
        var sRobotoLight: Typeface? = null
        var sRobotoLightItalic: Typeface? = null
        var sRobotoMedium: Typeface? = null
        var sRobotoMediumItalic: Typeface? = null
        var sRobotoRegular: Typeface? = null
        var sRobotoThin: Typeface? = null
        var sRobotoThinItalic: Typeface? = null
    }

    companion object {
        fun getRoboto(context: Context, typeface: Int): Typeface? {
            return when (typeface) {
                Roboto.ROBOTO_BLACK -> {
                    if (Roboto.sRobotoBlack == null) {
                        Roboto.sRobotoBlack =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Black.ttf")
                    }
                    Roboto.sRobotoBlack
                }
                Roboto.ROBOTO_BLACK_ITALIC -> {
                    if (Roboto.sRobotoBlackItalic == null) {
                        Roboto.sRobotoBlackItalic =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-BlackItalic.ttf")
                    }
                    Roboto.sRobotoBlackItalic
                }
                Roboto.ROBOTO_BOLD -> {
                    if (Roboto.sRobotoBold == null) {
                        Roboto.sRobotoBold =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Bold.ttf")
                    }
                    Roboto.sRobotoBold
                }
                Roboto.ROBOTO_BOLD_CONDENSED -> {
                    if (Roboto.sRobotoBoldCondensed == null) {
                        Roboto.sRobotoBoldCondensed =
                            Typeface.createFromAsset(
                                context.assets,
                                "fonts/Roboto-BoldCondensed.ttf"
                            )
                    }
                    Roboto.sRobotoBoldCondensed
                }
                Roboto.ROBOTO_BOLD_CONDENSED_ITALIC -> {
                    if (Roboto.sRobotoBoldCondensedItalic == null) {
                        Roboto.sRobotoBoldCondensedItalic =
                            Typeface.createFromAsset(
                                context.assets,
                                "fonts/Roboto-BoldCondensedItalic.ttf"
                            )
                    }
                    Roboto.sRobotoBoldCondensedItalic
                }
                Roboto.ROBOTO_BOLD_ITALIC -> {
                    if (Roboto.sRobotoBoldItalic == null) {
                        Roboto.sRobotoBoldItalic =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-BoldItalic.ttf")
                    }
                    Roboto.sRobotoBoldItalic
                }
                Roboto.ROBOTO_CONDENSED -> {
                    if (Roboto.sRobotoCondensed == null) {
                        Roboto.sRobotoCondensed =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Condensed.ttf")
                    }
                    Roboto.sRobotoCondensed
                }
                Roboto.ROBOTO_CONDENSED_ITALIC -> {
                    if (Roboto.sRobotoCondensedItalic == null) {
                        Roboto.sRobotoCondensedItalic =
                            Typeface.createFromAsset(
                                context.assets,
                                "fonts/Roboto-CondensedItalic.ttf"
                            )
                    }
                    Roboto.sRobotoCondensedItalic
                }
                Roboto.ROBOTO_ITALIC -> {
                    if (Roboto.sRobotoItalic == null) {
                        Roboto.sRobotoItalic =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Italic.ttf")
                    }
                    Roboto.sRobotoItalic
                }
                Roboto.ROBOTO_LIGHT -> {
                    if (Roboto.sRobotoLight == null) {
                        Roboto.sRobotoLight =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Light.ttf")
                    }
                    Roboto.sRobotoLight
                }
                Roboto.ROBOTO_LIGHT_ITALIC -> {
                    if (Roboto.sRobotoLightItalic == null) {
                        Roboto.sRobotoLightItalic =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-LightItalic.ttf")
                    }
                    Roboto.sRobotoLightItalic
                }
                Roboto.ROBOTO_MEDIUM -> {
                    if (Roboto.sRobotoMedium == null) {
                        Roboto.sRobotoMedium =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Medium.ttf")
                    }
                    Roboto.sRobotoMedium
                }
                Roboto.ROBOTO_MEDIUM_ITALIC -> {
                    if (Roboto.sRobotoMediumItalic == null) {
                        Roboto.sRobotoMediumItalic =
                            Typeface.createFromAsset(
                                context.assets,
                                "fonts/Roboto-MediumItalic.ttf"
                            )
                    }
                    Roboto.sRobotoMediumItalic
                }
                Roboto.ROBOTO_REGULAR -> {
                    if (Roboto.sRobotoRegular == null) {
                        Roboto.sRobotoRegular =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Regular.ttf")
                    }
                    Roboto.sRobotoRegular
                }
                Roboto.ROBOTO_THIN -> {
                    if (Roboto.sRobotoThin == null) {
                        Roboto.sRobotoThin =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Thin.ttf")
                    }
                    Roboto.sRobotoThin
                }
                Roboto.ROBOTO_THIN_ITALIC -> {
                    if (Roboto.sRobotoThinItalic == null) {
                        Roboto.sRobotoThinItalic =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-ThinItalic.ttf")
                    }
                    Roboto.sRobotoThinItalic
                }
                else -> {
                    if (Roboto.sRobotoRegular == null) {
                        Roboto.sRobotoRegular =
                            Typeface.createFromAsset(context.assets, "fonts/Roboto-Regular.ttf")
                    }
                    Roboto.sRobotoRegular
                }
            }
        }
    }
}