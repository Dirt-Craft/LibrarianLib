package com.teamwizardry.librarianlib.features.java2d

import com.teamwizardry.librarianlib.features.kotlin.toRl
import net.minecraft.client.Minecraft
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Shape
import javax.swing.JEditorPane
import javax.swing.JTextPane
import javax.swing.SwingUtilities
import javax.swing.text.*

private val unifont: Font = Font.createFont(Font.TRUETYPE_FONT, Minecraft.getMinecraft().
        resourceManager.getResource("librarianlib:fonts/unifont-11.0.01.ttf".toRl()).inputStream
)

class ColoredStrikeThroughText {
    val panel = JTextPane()
    init {
        SwingUtilities.invokeLater {
            val kit = MCTextEditorKit()
            panel.editorKit = kit
            panel.isOpaque = false
            val doc = panel.styledDocument
            doc.putProperty("i18n", true);

            var attr = SimpleAttributeSet()
            StyleConstants.setAlignment(attr, StyleConstants.ALIGN_LEFT);
            StyleConstants.setLineSpacing(attr, -0.2f);
            panel.setParagraphAttributes(attr, false)
            //panel.font = Font("SansSerif", 0, 8)
            panel.font = unifont.deriveFont(8f);

            attr = SimpleAttributeSet()
            StyleConstants.setAlignment(attr, StyleConstants.ALIGN_LEFT);
            attr.addAttribute("strike-color", Color.red)
            doc.insertString(doc.length, "wwwwwidth format xxx", attr)

            doc.insertString(doc.length, "xxx no wiiiidth format xxx", null)

            doc.insertString(doc.length, "xxx format widdddth", attr)

            doc.insertString(doc.length, "X\nX", null)
            doc.insertString(doc.length, "WWWWWWWWWWWWWW", attr)
            doc.insertString(doc.length, "X\nX", null)
            doc.insertString(doc.length, "llllllllllllll", attr)
            doc.insertString(doc.length, "X\nX", null)
        }
    }
}

class MCTextEditorKit: StyledEditorKit() {
    private val viewFactory = MCTextEditorViewFactory()
    override fun getViewFactory(): ViewFactory {
        return viewFactory
    }
}

internal class MCTextEditorViewFactory: ViewFactory {
    override fun create(elem: Element): View {
        val kind = elem.name
        if (kind != null) {
            if (kind == AbstractDocument.ContentElementName) {
                return MyLabelView(elem)
            } else if (kind == AbstractDocument.ParagraphElementName) {
                return ParagraphView(elem)
            } else if (kind == AbstractDocument.SectionElementName) {
                return BoxView(elem, View.Y_AXIS)
            } else if (kind == StyleConstants.ComponentElementName) {
                return ComponentView(elem)
            } else if (kind == StyleConstants.IconElementName) {
                return IconView(elem)
            }
        }

        // default to text display
        return LabelView(elem)
    }
}

internal class MyLabelView(elem: Element): LabelView(elem) {
    init {
    }

    override fun paint(g: Graphics, allocation: Shape) {

//        val a = allocation
//        val old = g.color
//        g.color = Color.MAGENTA
//        g.drawLine(a.bounds.x, a.bounds.y, a.bounds.x, a.bounds.y+a.bounds.height)
//        g.color = Color.RED
//        g.drawLine(a.bounds.x, a.bounds.y+a.bounds.height, a.bounds.x+a.bounds.width, a.bounds.y)
//        g.color = Color.BLUE
//        g.drawLine(a.bounds.x+a.bounds.width, a.bounds.y, a.bounds.x+a.bounds.width, a.bounds.y+a.bounds.height)
//        g.color = old

        super.paint(g, allocation)
        paintStrikeLine(g, allocation)
    }

    fun paintStrikeLine(g: Graphics, a: Shape) {
        val c = element.attributes.getAttribute("strike-color") as? Color ?: return
        var y = a.bounds.y + a.bounds.height - glyphPainter.getDescent(this).toInt()

        y -= (glyphPainter.getAscent(this) * 0.3f).toInt()
        val x1 = a.bounds.getX().toInt()
        val x2 = (a.bounds.getX() + a.bounds.getWidth()).toInt()

        val old = g.color
        g.color = c
        g.drawLine(x1, y, x2, y)
        g.color = old
    }

    override fun getAlignment(axis: Int): Float {
        return super.getAlignment(axis);
    }
}


