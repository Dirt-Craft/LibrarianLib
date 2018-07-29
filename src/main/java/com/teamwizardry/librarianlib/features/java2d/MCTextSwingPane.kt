package com.teamwizardry.librarianlib.features.java2d

import com.teamwizardry.librarianlib.core.LibrarianLog
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

class MCTextSwingPane {
    val pane = JTextPane()
    init {
        SwingUtilities.invokeLater {
            val kit = MCTextEditorKit()
            pane.editorKit = kit
            pane.isOpaque = false
            pane.styledDocument.putProperty("i18n", true)
            pane.font = unifont.deriveFont(8f)
        }
    }

    var text: String = ""
        set(value) {
            field = value

            pane.styledDocument.remove(0, pane.styledDocument.length)
            MCTextEditorKit.insertMCString(pane.styledDocument, 0, value)
        }

    companion object {
        private val unifont: Font = Font.createFont(Font.TRUETYPE_FONT, Minecraft.getMinecraft().
                resourceManager.getResource("librarianlib:fonts/unifont-11.0.01.ttf".toRl()).inputStream
        )
    }
}

