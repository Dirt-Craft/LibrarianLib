package com.teamwizardry.librarianlib.features.network

import com.google.common.collect.HashBiMap
import com.teamwizardry.librarianlib.core.LibrarianLib
import com.teamwizardry.librarianlib.features.kotlin.withRealDefault
import com.teamwizardry.librarianlib.features.saving.AbstractSaveHandler
import com.teamwizardry.librarianlib.features.utilities.client.ClientRunnable
import net.minecraft.client.Minecraft
import net.minecraft.network.NetHandlerPlayServer
import net.minecraft.util.IThreadListener
import net.minecraft.world.WorldServer
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

@Mod.EventBusSubscriber(modid = LibrarianLib.MODID)
object PacketHandler {

    @JvmField
    val NETWORK: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("TeamWizardry")
    @JvmField
    val CHANNEL: Channel = Channel(NETWORK)

    private var id = 0
    private val discriminators = HashBiMap.create<Class<*>, Int>()

    @JvmStatic
    fun <T : PacketBase> register(clazz: Class<T>, targetSide: Side) {
        AbstractSaveHandler.cacheFields(clazz)
        discriminators[clazz] = id
        NETWORK.registerMessage<T, PacketBase>(Handler<T>(), clazz, id, targetSide)
        id++
    }

    fun getDiscriminator(clazz: Class<*>): Int {
        return discriminators[clazz] ?: -1
    }

    fun getPacketClass(descriminator: Int): Class<*>? {
        return discriminators.inverse()[descriminator]
    }

    private val channels = mutableMapOf<String, Channel>().withRealDefault { Channel(NETWORK) }

    fun getChannel(modId: String): Channel {
        return channels[modId]
    }

    @JvmStatic
    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun tick(e: TickEvent.ServerTickEvent) {
        CHANNEL.onTickEnd()
        channels.values.forEach { it.onTickEnd() }
    }

    class Handler<REQ : PacketBase> : IMessageHandler<REQ, PacketBase> {

        override fun onMessage(message: REQ, ctx: MessageContext): PacketBase? {
            val mainThread = if (ctx.netHandler is NetHandlerPlayServer)
                ctx.serverHandler.player.world as WorldServer
            else
                ClientRunnable.produce { Minecraft.getMinecraft() as IThreadListener }
            mainThread?.addScheduledTask { message.handle(ctx) }
            return message.reply(ctx)
        }
    }
}

operator fun PacketHandler.get(modId: String) = this.getChannel(modId)
