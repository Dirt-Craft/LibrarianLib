package com.teamwizardry.librarianlib.features.java2d

import java.awt.*
import javax.swing.text.*

internal class MCTextLabelView(elem: Element): LabelView(elem) {

//    var image: BufferedImage? = null

    override fun paint(g: Graphics, allocation: Shape) {
//        if(image?.width != allocation.bounds.width || image?.height != allocation.bounds.height) {
//            this.image = BufferedImage(allocation.bounds.width, allocation.bounds.height, BufferedImage.TYPE_INT_ARGB)
//        }
//        val bufferGraphics = this.image!!.createGraphics()
//        bufferGraphics.translate(-allocation.bounds.x, -allocation.bounds.y)
//        super.paint(bufferGraphics, allocation)
//        bufferGraphics.dispose()
        super.paint(g, allocation)
        val g2d = g as Graphics2D
        paintLines(g2d, allocation)
    }

    fun paintLines(g: Graphics2D, a: Shape) {
        val baseline = a.bounds.y + a.bounds.height - glyphPainter.getDescent(this).toInt()
        val left = a.bounds.getX().toInt()
        val right = (a.bounds.getX() + a.bounds.getWidth()).toInt()

        val oldColor = g.color
        val oldStroke = g.stroke
        g.stroke = BasicStroke(font.size/16f)
        g.color = foreground

        if(element.attributes.getAttribute(MCFormatCode.StrikeThroughType) == true) {
            val strikeHeight = baseline - (glyphPainter.getAscent(this) * 0.3f).toInt()

            g.drawLine(left, strikeHeight, right, strikeHeight)
        }

        if(element.attributes.getAttribute(MCFormatCode.UnderlineType) == true) {
            val underlineHeight = baseline - glyphPainter.getDescent(this).toInt()

            g.drawLine(left, underlineHeight, right, underlineHeight)
        }

        g.color = oldColor
        g.stroke = oldStroke
    }
}

