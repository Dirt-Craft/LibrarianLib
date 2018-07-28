package com.teamwizardry.librarianlib.features.java2d

import com.teamwizardry.librarianlib.features.kotlin.toRl
import com.teamwizardry.librarianlib.features.sprite.ISprite
import com.teamwizardry.librarianlib.features.sprite.Java2DSprite
import net.minecraft.client.Minecraft
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Stroke
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.text.AttributedString
import kotlin.math.min
import kotlin.streams.toList

object SimpleStringRenderer {
    @JvmStatic
    val unifont: Font = Font.createFont(Font.TRUETYPE_FONT, Minecraft.getMinecraft().
            resourceManager.getResource("librarianlib:fonts/unifont-11.0.01.ttf".toRl()).inputStream
    )

    fun createAttributedString(mcString: String, defaultAttributes: Map<TextAttribute, Any>): AttributedString {
        val builder = AttributedStringBuilder()

        var stringRemaining = mcString
        var attributes = defaultAttributes.toMutableMap()
        var obf = false
        while(stringRemaining.isNotEmpty()) {
            var endIndex = stringRemaining.indexOf('§', startIndex = 1)
            if(endIndex == -1) endIndex = stringRemaining.length
            val section = stringRemaining.substring(0, endIndex)
            stringRemaining = stringRemaining.substring(endIndex)

            var toAppend: String? = null
            if(section.startsWith("§")) {
                if(section.length < 2) {
                    toAppend = section
                } else {
                    if(section.length > 2) {
                        stringRemaining = section.substring(2) + stringRemaining
                    }
                    when (section[1].toLowerCase()) {
                        '§' -> toAppend = "§"
                        '0' -> attributes[TextAttribute.FOREGROUND] = Color(0x000000)
                        '1' -> attributes[TextAttribute.FOREGROUND] = Color(0x002aa6)
                        '2' -> attributes[TextAttribute.FOREGROUND] = Color(0x43a217)
                        '3' -> attributes[TextAttribute.FOREGROUND] = Color(0x43a6a8)
                        '4' -> attributes[TextAttribute.FOREGROUND] = Color(0x9f261c)
                        '5' -> attributes[TextAttribute.FOREGROUND] = Color(0x9f3aa8)
                        '6' -> attributes[TextAttribute.FOREGROUND] = Color(0xf5ab37)
                        '7' -> attributes[TextAttribute.FOREGROUND] = Color(0xaaaaaa)
                        '8' -> attributes[TextAttribute.FOREGROUND] = Color(0x555555)
                        '9' -> attributes[TextAttribute.FOREGROUND] = Color(0x5469fa)
                        'a' -> attributes[TextAttribute.FOREGROUND] = Color(0x81f45d)
                        'b' -> attributes[TextAttribute.FOREGROUND] = Color(0x81fafc)
                        'c' -> attributes[TextAttribute.FOREGROUND] = Color(0xf06760)
                        'd' -> attributes[TextAttribute.FOREGROUND] = Color(0xf077fd)
                        'e' -> attributes[TextAttribute.FOREGROUND] = Color(0xfff967)
                        'f' -> attributes[TextAttribute.FOREGROUND] = Color(0xffffff)
                        'k' -> obf = true
                        'l' -> attributes[TextAttribute.WEIGHT] = TextAttribute.WEIGHT_BOLD
                        'm' -> attributes[TextAttribute.STRIKETHROUGH] = true
                        'n' -> attributes[TextAttribute.UNDERLINE] = TextAttribute.UNDERLINE_ON
                        'o' -> attributes[TextAttribute.POSTURE] = TextAttribute.POSTURE_OBLIQUE
                        'r' -> {
                            attributes = defaultAttributes.toMutableMap(); obf = false
                        }
                    }
                }
            } else {
                if(obf) {
                    var jumbled = section
                    jumbled = String(jumbled.chars().toList().shuffled().map { it.toChar() }.toCharArray() )
                    toAppend = jumbled
                } else {
                    toAppend = section
                }
            }
            if(toAppend != null && toAppend.isNotEmpty()) {
                val attributedSection = AttributedString(toAppend)
                attributes.forEach { (k, v) ->
                    attributedSection.addAttribute(k, v)
                }
                builder.append(attributedSection)
            }
        }

        return builder.value
    }

    fun drawAttributedString(string: AttributedString, g2d: Graphics2D, startY: Float, breakWidth: (yPos: Float) -> Float) {
        // Create a new LineBreakMeasurer from the paragraph.
        // It will be cached and re-used.
        val paragraph = string.iterator
        val paragraphStart = paragraph.beginIndex
        val paragraphEnd = paragraph.endIndex
        val frc = g2d.fontRenderContext
        val lineMeasurer = LineBreakMeasurer(paragraph, frc)

        // Set break width to width of Component.
        var drawPosY = startY //-1f
        // Set position to the index of the first character in the paragraph.
        lineMeasurer.position = paragraphStart

        // Get lines until the entire paragraph has been displayed.
        while (lineMeasurer.position < paragraphEnd) {

            val width = breakWidth(drawPosY)

            val layout = lineMeasurer.nextLayout(width)

            var drawPosX = if(layout.isLeftToRight) 0f else width - layout.advance
            drawPosX = Math.ceil(drawPosX.toDouble()).toFloat()

            // Move y-coordinate by the ascent of the layout.
            drawPosY += layout.ascent
            drawPosY = Math.ceil(drawPosY.toDouble()).toFloat()

            // Draw the TextLayout at (drawPosX, drawPosY).
            layout.draw(g2d, drawPosX, drawPosY)
            layout.getLogicalHighlightShape(0, 10)

            // Move y-coordinate in preparation for next layout.
            drawPosY += layout.descent + layout.leading
        }
    }
}