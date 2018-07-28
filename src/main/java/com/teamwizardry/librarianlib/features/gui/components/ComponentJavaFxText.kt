package com.teamwizardry.librarianlib.features.gui.components

import com.teamwizardry.librarianlib.core.LibrarianLog
import com.teamwizardry.librarianlib.features.gui.component.GuiComponent
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.java2d.SimpleStringRenderer
import com.teamwizardry.librarianlib.features.kotlin.toRl
import com.teamwizardry.librarianlib.features.math.BoundingBox2D
import com.teamwizardry.librarianlib.features.math.Vec2d
import com.teamwizardry.librarianlib.features.sprite.Java2DSprite
import com.teamwizardry.librarianlib.features.utilities.DispatchQueue
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.embed.swing.SwingFXUtils
import javafx.scene.Scene
import javafx.scene.text.TextFlow
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import java.awt.BasicStroke
import java.awt.Color
import java.awt.RenderingHints
import java.awt.font.TextAttribute
import org.lwjgl.opengl.Display.getHeight
import org.lwjgl.opengl.Display.getWidth
import scala.tools.nsc.typechecker.DestructureTypes.`DestructureType$class`.node
import javafx.scene.SnapshotParameters
import javafx.scene.image.WritableImage
import javafx.scene.layout.Region
import javafx.scene.text.FontSmoothingType
import javafx.scene.text.Text
import javax.swing.SwingUtilities
import java.util.concurrent.CountDownLatch



class ComponentJavaFxText @JvmOverloads constructor(
        posX: Int, posY: Int,
        horizontal: ComponentText.TextAlignH = ComponentText.TextAlignH.LEFT,
        vertical: ComponentText.TextAlignV = ComponentText.TextAlignV.TOP
) : ComponentText(posX, posY, horizontal, vertical) {

    private var spriteComponent: ComponentSprite = ComponentSprite(null, 0, 0, 0, 0)
    private lateinit var sprite: Java2DSprite
    private lateinit var writableImage: WritableImage
    private var rootNode = TextFlow()
    private var scene = Scene(rootNode)
    private var currentSize: Vec2d = size
    private var currentText: String = ""
    private var currentScale: Int = 0

    init {
        ensureJfxLoaded()
        this.add(spriteComponent)
        this.wrap.func { this.size.x.toInt() }
        this.scene.fill = javafx.scene.paint.Color.TRANSPARENT
        this.scene.stylesheets.add("/assets/librarianlib/fonts/javafx_text.css")
        this.scene.stylesheets.clear()
        this.scene.stylesheets.add("/assets/librarianlib/fonts/javafx_text.css")
        this.rootNode.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
        this.rootNode.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
    }

    private fun redraw() {
        val fontHeight = 160 // the only font size that doesn't antialias when pixel-perfect
        val scaleToRealPixels = 16.0/fontHeight
        val s = 1//scaleToRealPixels
        rootNode.setPrefSize(this.size.x/s, this.size.y/s)

        val text = Text(currentText)
        text.styleClass.add("mc-text")
        text.fontSmoothingType = FontSmoothingType.LCD

        rootNode.children.clear()
        rootNode.children.add(text)

        val spa = SnapshotParameters()
        spa.transform = javafx.scene.transform.Transform.scale(currentScale.toDouble()*s, currentScale.toDouble()*s)
        spa.fill = javafx.scene.paint.Color.TRANSPARENT

        val renderStart = System.currentTimeMillis()
        Platform.runLater {
            val javaFxThreadEnd = System.currentTimeMillis()
            rootNode.snapshot(spa, writableImage)
            val javaFxEnd = System.currentTimeMillis()
            DispatchQueue.clientThread.dispatch {
                val roundTripEnd = System.currentTimeMillis()
                val img = sprite.edit()
                SwingFXUtils.fromFXImage(writableImage, img)
                sprite.end()
                LibrarianLog.debug("JavaFx Text Component - JavaFx thread started text render after " +
                        "${javaFxThreadEnd-renderStart} ms")
                LibrarianLog.debug("JavaFx Text Component - JavaFX rendering took ${javaFxEnd-javaFxThreadEnd} ms")
                LibrarianLog.debug("JavaFx Text Component - Client thread started uploading texture after " +
                        "${roundTripEnd-javaFxEnd} ms")
            }
        }

//        val attributed = SimpleStringRenderer.createAttributedString(currentText, mapOf(
//                TextAttribute.FONT to ,
//                TextAttribute.FOREGROUND to Color.BLACK
//        ))
//        SimpleStringRenderer.drawAttributedString(attributed, g2d, 0f) { wrap.toFloat() }

    }

    override fun drawComponent(mousePos: Vec2d, partialTicks: Float) {
        val newText = text.getValue(this)
        //val scaledresolution = ScaledResolution(Minecraft.getMinecraft())
        val newScale = 1// scaledresolution.scaleFactor
        spriteComponent.pos = vec(0, 0)

        if(newText != currentText || size != currentSize || newScale != currentScale) {
            if(size != currentSize || newScale != currentScale) {
                currentSize = size
                currentScale = 1
                spriteComponent.size = currentSize
                this.sprite = Java2DSprite(currentSize.xi*currentScale, currentSize.yi*currentScale)
                this.writableImage = WritableImage(currentSize.xi * currentScale, currentSize.yi * currentScale)
                spriteComponent.sprite = this.sprite
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

    private fun ensureJfxLoaded() {
        val latch = CountDownLatch(1)
        SwingUtilities.invokeLater {
            JFXPanel() // initializes JavaFX environment
            SimpleStringRenderer.jfxUnifont
            latch.countDown()
        }
        latch.await()
    }
}
