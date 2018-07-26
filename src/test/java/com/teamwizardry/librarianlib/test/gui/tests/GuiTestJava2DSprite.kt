package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import com.teamwizardry.librarianlib.features.gui.components.ComponentSprite
import com.teamwizardry.librarianlib.features.gui.components.ComponentText
import com.teamwizardry.librarianlib.features.java2d.SimpleStringRenderer
import com.teamwizardry.librarianlib.features.sprite.ISprite
import com.teamwizardry.librarianlib.features.sprite.Java2DSprite
import com.teamwizardry.librarianlib.features.sprite.Sprite
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import java.awt.*
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.awt.geom.Ellipse2D
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D
import java.text.AttributedCharacterIterator
import java.text.AttributedString

/**
 * Created by TheCodeWarrior
 */
class GuiTestJava2DSprite : GuiBase(300, 150) {
    init {
        val c2 = ComponentRect(-2, -2, 304, 154)
        c2.color.setValue(Color.BLACK)
        mainComponents.add(c2)

        val c = ComponentRect(0, 0, 300, 150)
        c.color.setValue(Color.LIGHT_GRAY)
        mainComponents.add(c)

        val c3 = ComponentRect(148, 0, 4, 150)
        c3.color.setValue(Color.BLACK)
        mainComponents.add(c3)
        //==================================================================
        val font = SimpleStringRenderer.unifont.deriveFont(8f)//Font("Comic Sans MS", Font.PLAIN, 8)//
        val unifontSprite = createTextSprite(font)

        val j2dTextComponent = ComponentSprite(unifontSprite, 1, 1, 150, 150)
        mainComponents.add(j2dTextComponent)

        val text = "`~!@#$%^&*()-_=+ a\n[{]}\\|;:'\",<.>/? a"

        val bgTextComponent = ComponentText(153, 1)
        bgTextComponent.color.setValue(Color(255, 0, 255, 64))
        bgTextComponent.unicode.setValue(true)
        bgTextComponent.wrap.setValue(146)
        bgTextComponent.text.setValue(text+"\n\n\n\n\n\n<->")
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
//                " Integer a orci ut arcu scelerisque tempus. Nunc finibus, erat non pellentesque egestas," +
//                " neque massa volutpat lectus, eu consequat sapien lacus ut lorem. <->")
        mainComponents.add(bgTextComponent)

        val textComponent = ComponentText(153, 1)
        textComponent.unicode.setValue(true)
        textComponent.wrap.setValue(146)
        textComponent.text.setValue(text)
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
//                " Integer a orci ut arcu scelerisque tempus. Nunc finibus, erat non pellentesque egestas," +
//                " neque massa volutpat lectus, eu consequat sapien lacus ut lorem.")
        mainComponents.add(textComponent)
    }

    fun createTextSprite(font: Font): ISprite {
        val string = AttributedString("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Integer a orci ut arcu scelerisque tempus. Nunc finibus, erat non pellentesque egestas," +
                " neque massa volutpat lectus, eu consequat sapien lacus ut lorem.")

        var scale = 3.0
        val textSprite = Java2DSprite((150*scale).toInt(), (150*scale).toInt())

        val g2d = textSprite.begin()
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        g2d.scale(scale.toDouble(), scale.toDouble())

//        string.addAttribute(TextAttribute.FONT, font)
        string.addAttribute(TextAttribute.FONT, font)
        string.addAttribute(TextAttribute.FOREGROUND, Color.BLACK)

        // Create a new LineBreakMeasurer from the paragraph.
        // It will be cached and re-used.
        val paragraph = string.iterator
        val paragraphStart = paragraph.beginIndex
        val paragraphEnd = paragraph.endIndex
        val frc = g2d.fontRenderContext
        val lineMeasurer = LineBreakMeasurer(paragraph, frc)

        // Set break width to width of Component.
        val breakWidth = 146f
        var drawPosY = -1f
        // Set position to the index of the first character in the paragraph.
        lineMeasurer.position = paragraphStart

        // Get lines until the entire paragraph has been displayed.
        while (lineMeasurer.position < paragraphEnd) {

            // Retrieve next layout. A cleverer program would also cache
            // these layouts until the component is re-sized.
            val layout = lineMeasurer.nextLayout(breakWidth)

            // Compute pen x position. If the paragraph is right-to-left we
            // will align the TextLayouts to the right edge of the panel.
            // Note: this won't occur for the English text in this sample.
            // Note: drawPosX is always where the LEFT of the text is placed.
            var drawPosX = (if(layout.isLeftToRight) 0f else breakWidth - layout.advance)
            drawPosX = Math.ceil(drawPosX.toDouble()).toFloat()

            // Move y-coordinate by the ascent of the layout.
            drawPosY += layout.ascent
            drawPosY = Math.ceil(drawPosY.toDouble()).toFloat()

            // Draw the TextLayout at (drawPosX, drawPosY).
            layout.draw(g2d, drawPosX, drawPosY)

            // Move y-coordinate in preparation for next layout.
            drawPosY += layout.descent + layout.leading
        }

        textSprite.end()

        return textSprite
    }
}
