package velvet.structs

import java.awt.Color
import kotlin.math.pow

data class VColor(val red: Double, val green: Double, val blue: Double, val alpha: Double = 1.0) {

    constructor(red: Int, green: Int, blue: Int, alpha: Int = 255) :
            this(red/255.0, green/255.0, blue/255.0, alpha/255.0)

    val javaColor: Color by lazy { Color(red.toFloat(), green.toFloat(), blue.toFloat(), alpha.toFloat()) }

    fun toInt() = ((red*255).toInt() shl 16) +
            ((green*255).toInt() shl 8) +
            (blue*255).toInt() +
            ((alpha*255).toInt() shl 24)

    companion object {

        val BLACK: VColor = VColor(0, 0, 0)
        val RED: VColor = VColor(255, 0, 0)
        val GREEN: VColor = VColor(0, 255, 0)
        val BLUE: VColor = VColor(0, 0, 255)
        val WHITE: VColor = VColor(255, 255, 255)

        fun fromJavaColor(c: Color) = VColor(c.red, c.green, c.blue, c.alpha)

        fun fromIntWithAlpha(color: Int, alpha: Int) =
                VColor((color shr 16) and 0xFF, (color shr 8) and 0xFF, color and 0xFF, alpha)

        fun fromInt(color: Int) =
                VColor((color shr 16) and 0xFF, (color shr 8) and 0xFF, color and 0xFF, (color shr 24) and 0xFF)

        fun fromHSB(hue: Double, sat: Double, bright: Double, alpha: Double = 1.0) =
                fromIntWithAlpha(Color.HSBtoRGB(hue.toFloat(), sat.toFloat(), bright.toFloat()), (alpha * 255).toInt())
    }

    fun getRGBDistance(vColor: VColor) =
            ((red - vColor.red).pow(2) + (green - vColor.green).pow(2) + (blue - vColor.blue).pow(2)).pow(0.5)
}