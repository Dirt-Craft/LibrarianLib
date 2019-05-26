package com.teamwizardry.librarianlib.features.sprite

import com.teamwizardry.librarianlib.features.kotlin.Minecraft
import com.teamwizardry.librarianlib.features.utilities.client.ClientRunnable
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.PngSizeInfo
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

/**
 * This class represents a texture and it's size. It is mostly used to create [Sprite]
 * objects
 *
 * sprite information stored in the texture .mcmeta file
 * ```
 * {
 *     "spritesheet": {
 *         "textureWidth": <texture width in pixels>,
 *         "textureHeight": <texture height in pixels>,
 *         "sprites": {
 *             "<sprite name>": [<u>, <v>, <w>, <h>],       // static sprite
 *             "<sprite name>": {                           // animated sprite
 *                 "pos": [<u>, <v>, <w>, <h>],
 *                 "frames": 12,                            // the number of frames, shorthand for [0, 1, 2, ..., n-1]
 *                 "frameTime": 2                           // default: 1 - the number of ticks per frame
 *             },
 *             "<sprite name>": {
 *                 "pos": [<u>, <v>, <w>, <h>],
 *                 "frames": [0, 1, 2, 3, 2, 1],            // animation frame indices
 *                 "offset": [<u>, <v>]                     // default: [u, <h>] - uv offset per frame.
 *             },
 *             "<sprite name>": {
 *                 "pos": [<u>, <v>, <w>, <h>],
 *                 "caps": [<minU>, <minV>, <maxU>, <maxV>], // the number of pixels on each edge that should remain non-distorted when stretching the sprite
 *                 "hardScale": [<uAxis>, <vAxis>] // default: [false, false] - whether the texture should be repeated/truncated rather than stretched/squished along each axis
 *             }
 *         }
 *     }
 * }
 * ```
 */
@SideOnly(Side.CLIENT)
class Texture(
    loc: ResourceLocation,
    /**
     * The logical width of this texture in pixels. Used to determine the scaling factor from texture pixels to
     * logical pixels
     */
    val logicalWidth: Int,
    /**
     * The logical height of this texture in pixels. Used to determine the scaling factor from texture pixels to
     * logical pixels
     */
    val logicalHeight: Int
) {
    @Deprecated("Assumes a 256x texture", replaceWith = ReplaceWith("Texture(loc, 256, 256)"))
    constructor(loc: ResourceLocation): this(loc, 256, 256)

    var loc: ResourceLocation = loc
        private set
    /**
     * The width of the texture in pixels
     */
    var width: Int = 0
        private set
    /**
     * The height of the texture in pixels
     */
    var height: Int = 0
        private set

    var image: BufferedImage? = null
        private set

    /**
     * The ratio of texture pixels / logical pixels. Calculated by averaging the ratio on each axis.
     */
    val logicalScale: Int
        get() = ((logicalWidth.toFloat() / width + logicalHeight.toFloat() / height) / 2).toInt()
    private var section: SpritesMetadataSection? = null
    internal var sprites: MutableMap<String, Sprite> = mutableMapOf()
    private var colors: MutableMap<String, TextureColor> = mutableMapOf()

    init {
        textures.add(WeakReference(this))
        if (SpritesMetadataSection.registered)
            load()
    }

    /**
     * Loads the sprite data from disk
     */
    fun load() {
        var pngWidth: Int
        var pngHeight: Int
        try {
            val pngSizeInfo = PngSizeInfo.makeFromResource(Minecraft().resourceManager.getResource(loc))
            pngWidth = pngSizeInfo.pngWidth
            pngHeight = pngSizeInfo.pngHeight

            if (width > 0 && height <= 0) {
                pngWidth = width
                pngHeight = pngHeight * width / pngWidth
            } else if (width <= 0 && height > 0) {
                pngHeight = height
                pngWidth = pngWidth * height / pngHeight
            } else if (width > 0 && height > 0) {
                pngWidth = width
                pngHeight = height
            }
        } catch (e: FileNotFoundException) {
            pngWidth = 16
            pngHeight = 16
        }

        this.width = pngWidth
        this.height = pngHeight
        this.section = null
        try {
            this.section = Minecraft().resourceManager.getResource(loc).getMetadata("spritesheet")
        } catch (e: FileNotFoundException) {
            // nop
        }
        readSection()
        loadImageData()
    }

    private fun readSection() {
        val section = this.section

        val oldSprites = this.sprites
        val oldColors = this.colors
        this.sprites = mutableMapOf()

        if (section != null) {
            this.width = section.width
            this.height = section.height

            for (def in section.sprites) {
                val sprite = oldSprites[def.name] ?: Sprite(this)
                sprites[def.name] = sprite
                sprite.init(def)
            }

            for (def in section.colors) {
                val color = oldColors[def.name] ?: TextureColor()
                colors[def.name] = color
                color.init(def)
            }
        }
    }

    fun loadImageData() {
        try {
            val image = TextureUtil.readBufferedImage(Minecraft().resourceManager.getResource(loc).inputStream)
            this.image = image
            this.sprites.forEach { name, sprite ->
                try {
                    sprite.loadImage(image)
                } catch(e: Exception) {
                    RuntimeException("Error loading sprite image $name in texture $loc", e).printStackTrace()
                }
            }
            this.colors.forEach { _, color ->
                val x = (color.u.toDouble() / this.width * image.width).toInt()
                val y = (color.v.toDouble() / this.height * image.height).toInt()
                color.color.replaceColor(Color(image.getRGB(x, y), true))
            }
        } catch (e: FileNotFoundException) {
            // nop
        }
    }

    fun switchTexture(loc: ResourceLocation) {
        this.loc = loc
        this.textureLoaded = false
        if (SpritesMetadataSection.registered)
            load()
    }

    /**
     * Gets the sprite with the specified name
     */
    fun getSprite(name: String): Sprite {
        return sprites.getOrPut(name) { Sprite(this) }
    }

    /**
     * Gets the sprite with the specified name
     */
    @Deprecated("Ignores width and height parameters, width/height are based on the mcmeta file and the logical " +
        "width/height", replaceWith = ReplaceWith("getSprite(name)"))
    fun getSprite(name: String, width: Int, height: Int): Sprite {
        return getSprite(name)
    }

    /**
     * Gets the color with the specified name
     */
    fun getColor(name: String): Color {
        return colors.getOrPut(name) { TextureColor() }.color
    }

    private var blending = false
    private var textureLoaded = false

    /**
     * Enables linear blending
     */
    fun enableBlending() {
        blending = true
        if(!textureLoaded) {
            return
        }
        Minecraft.getMinecraft().textureManager.bindTexture(loc)
        Minecraft.getMinecraft().textureManager.getTexture(loc).setBlurMipmap(true, false)
    }

    /**
     * Disables linear blending
     */
    fun disableBlending() {
        blending = false
        if(!textureLoaded) {
            return
        }
        Minecraft.getMinecraft().textureManager.bindTexture(loc)
        Minecraft.getMinecraft().textureManager.getTexture(loc).setBlurMipmap(false, false)
    }

    /**
     * Bind this texture
     */
    fun bind() {
        Minecraft.getMinecraft().textureManager.bindTexture(loc)
        if(!textureLoaded) {
            textureLoaded = true
            if (blending)
                enableBlending()
            else
                disableBlending()
        }
    }

    companion object {

        internal fun register() {
            ClientRunnable.registerReloadHandler {
                val newList = ArrayList<WeakReference<Texture>>()

                for (tex in Texture.textures) {
                    tex.get()?.load()
                    tex.get()?.loadImageData()
                    if (tex.get() != null) newList.add(tex)
                }

                Texture.textures = newList
            }
        }

        var textures: MutableList<WeakReference<Texture>> = ArrayList()
    }

    private class TextureColor {
        var u: Int = 0
        var v: Int = 0
        val color = MutableColor()

        fun init(def: ColorDefinition) {
            this.u = def.u
            this.v = def.v
        }
    }
}
