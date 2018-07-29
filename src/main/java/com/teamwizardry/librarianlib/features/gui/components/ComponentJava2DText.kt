package com.teamwizardry.librarianlib.features.gui.components

import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.java2d.MCTextSwingPane
import com.teamwizardry.librarianlib.features.math.BoundingBox2D
import com.teamwizardry.librarianlib.features.math.Vec2d
import com.teamwizardry.librarianlib.features.sprite.Java2DSprite
import com.teamwizardry.librarianlib.features.utilities.DispatchQueue
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import java.awt.Dimension
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.SwingUtilities

class ComponentJava2DText @JvmOverloads constructor(
        posX: Int, posY: Int,
        horizontal: ComponentText.TextAlignH = ComponentText.TextAlignH.LEFT,
        vertical: ComponentText.TextAlignV = ComponentText.TextAlignV.TOP
) : ComponentText(posX, posY, horizontal, vertical) {

    private var spriteComponent: ComponentSprite = ComponentSprite(null, 0, 0, 0, 0)
    private lateinit var sprite: Java2DSprite
    private var currentSize: Vec2d = size
    private var currentText: String = ""
    private var currentScale: Int = 0
    private var mcTextPane = MCTextSwingPane()
    private val isUpdateQueued = AtomicBoolean(false)

    init {
        this.add(spriteComponent)
        this.wrap.func { this.size.x.toInt() }
    }

    private fun redraw() {
        if(!isUpdateQueued.compareAndSet(false, true)) return
        SwingUtilities.invokeLater {
            isUpdateQueued.set(false) // after this point any changes will require another redraw
            val currentSize = this.currentSize
            val currentScale = this.currentScale
            val currentText = this.currentText
            val sprite = this.sprite

            val dim = Dimension(currentSize.xi, currentSize.yi)
            mcTextPane.pane.size = dim
            mcTextPane.pane.minimumSize = dim
            mcTextPane.pane.maximumSize = dim
            mcTextPane.pane.preferredSize = dim
            mcTextPane.text = currentText

            val g2d = sprite.begin()
            g2d.scale(currentScale.toDouble(), currentScale.toDouble())
            mcTextPane.pane.paint(g2d)

            DispatchQueue.clientThread.dispatch {
                sprite.end()
                spriteComponent.sprite = sprite
            }
        }
    }

    override fun drawComponent(mousePos: Vec2d, partialTicks: Float) {
        val newText = text.getValue(this)
        val scaledresolution = ScaledResolution(Minecraft.getMinecraft())
        val newScale = scaledresolution.scaleFactor
        spriteComponent.pos = vec(0, 0)

        if(newText != currentText || size != currentSize || newScale != currentScale) {
            if(size != currentSize || newScale != currentScale) {
                currentSize = size
                currentScale = newScale
                spriteComponent.size = currentSize
                this.sprite = Java2DSprite(currentSize.xi*currentScale, currentSize.yi*currentScale)
            }
            currentText = newText
            redraw()
        }
    }

    override fun sizeToText() {
        throw UnsupportedOperationException("")
    }

    override val contentSize: BoundingBox2D
        get() {
            val wrap = this.wrap.getValue(this)

            val size: Vec2d

            val fr = Minecraft.getMinecraft().fontRenderer

            val enableFlags = unicode.getValue(this)

            if (enableFlags) {
                if(enableUnicodeBidi.getValue(this))
                    fr.bidiFlag = true
                fr.unicodeFlag = true
            }

            if (wrap == -1) {
                size = vec(fr.getStringWidth(text.getValue(this)), fr.FONT_HEIGHT)
            } else {
                val wrapped = fr.listFormattedStringToWidth(text.getValue(this), wrap)
                size = vec(wrap, wrapped.size * fr.FONT_HEIGHT)
            }

            if (enableFlags) {
                if(enableUnicodeBidi.getValue(this))
                    fr.bidiFlag = false
                fr.unicodeFlag = false
            }

            return BoundingBox2D(Vec2d.ZERO, size)
        }
}
