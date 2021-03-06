package com.teamwizardry.librarianlib.features.network

import com.teamwizardry.librarianlib.features.autoregister.PacketRegister
import com.teamwizardry.librarianlib.features.base.block.tile.TileMod
import com.teamwizardry.librarianlib.features.kotlin.*
import com.teamwizardry.librarianlib.features.saving.AbstractSaveHandler
import com.teamwizardry.librarianlib.features.saving.Save
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.FMLLaunchHandler
import net.minecraftforge.fml.relauncher.Side

@PacketRegister(Side.CLIENT)
class PacketTileSynchronization(var tile: TileMod? = null /* Tile is always null on clientside */) : PacketAbstractUpdate(tile?.pos) {

    @Save
    var pos: BlockPos? = tile?.pos

    // Buf is always null serverside
    private var buf: ByteBuf? = null

    override fun handle(ctx: MessageContext) {
        if (FMLLaunchHandler.side().isServer) return

        val b = buf
        val p = pos ?: return

        val tile = Minecraft.getMinecraft().world.getTileEntity(p)
        if (b == null || tile == null || tile !is TileMod) return

        AbstractSaveHandler.readAutoBytes(tile, b, true)
        if (!b.hasNullSignature())
            tile.readModuleNBT(b.readTag())
        tile.readCustomBytes(b)
        b.release()
    }

    override fun readCustomBytes(buf: ByteBuf) {
        if (buf.hasNullSignature()) return
        this.buf = buf.copy()
    }

    override fun writeCustomBytes(buf: ByteBuf) {
        val te = tile
        if (te == null)
            buf.writeNullSignature()
        else {
            buf.writeNonnullSignature()

            AbstractSaveHandler.writeAutoBytes(te, buf, true)
            val moduleNBT = te.writeModuleNBT(true)
            if (moduleNBT == null)
                buf.writeNullSignature()
            else {
                buf.writeNonnullSignature()
                buf.writeTag(moduleNBT)
            }
            te.writeCustomBytes(buf, true)
        }
    }
}
