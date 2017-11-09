package com.teamwizardry.librarianlib.features.sprite

/*
 * Created by Bluexin.
 * Made for LibrarianLib, under GNU LGPL v3.0
 * (a copy of which can be found at the repo root)
 */

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * [ISprite] wrapper for [TextureAtlasSprite].
 * Should ONLY be used when a [Sprite] can't be used.
 *
 * Nothing special needs to be done for animations to work ([TextureAtlasSprite] handles them out of the box).
 */
@SideOnly(Side.CLIENT)
class LTextureAtlasSprite(private val tas: TextureAtlasSprite, override val inWidth: Int, override val inHeight: Int): ISprite {

    override fun bind() = Minecraft.getMinecraft().textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)

    override fun minU(animFrames: Int) = tas.minU

    override fun minV(animFrames: Int) = tas.minV

    override fun maxU(animFrames: Int) = tas.maxU

    override fun maxV(animFrames: Int) = tas.maxV

    override val width: Int
        get() = tas.iconWidth

    override val height: Int
        get() = tas.iconHeight

    override val frameCount: Int
        get() = 0

    /**
     * The size in pixels of the nine slice border if a nine slice image. Zero otherwise.
     */
    var nineSliceSize: Int = 0

    override val nineSliceSizeW: Float
        get() = nineSliceSize / tas.iconWidth.toFloat()
    override val nineSliceSizeH: Float
        get() = nineSliceSize / tas.iconHeight.toFloat()
}
