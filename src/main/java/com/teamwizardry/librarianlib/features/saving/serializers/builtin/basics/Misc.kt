package com.teamwizardry.librarianlib.features.saving.serializers.builtin.basics

import com.teamwizardry.librarianlib.features.autoregister.SerializerRegister
import com.teamwizardry.librarianlib.features.helpers.castOrDefault
import com.teamwizardry.librarianlib.features.kotlin.*
import com.teamwizardry.librarianlib.features.saving.FieldType
import com.teamwizardry.librarianlib.features.saving.serializers.Serializer
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagByteArray
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagString
import net.minecraft.network.PacketBuffer
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.items.ItemStackHandler
import java.awt.Color
import java.util.*

@SerializerRegister(Color::class)
object SerializeColor : Serializer<Color>(FieldType.create(Color::class.java)) {
    override fun getDefault(): Color {
        return Color(0, 0, 0, 0)
    }

    override fun readNBT(nbt: NBTBase, existing: Color?, syncing: Boolean): Color {
        val tag = nbt.castOrDefault(NBTTagCompound::class.java)

        return Color(component(tag, "r"), component(tag, "g"), component(tag, "b"), component(tag, "a"))
    }

    private fun component(tag: NBTTagCompound, name: String) = tag.getByte(name).toInt() and 0xFF

    override fun writeNBT(value: Color, syncing: Boolean): NBTBase {
        val tag = NBTTagCompound()
        tag.setByte("r", value.red.toByte())
        tag.setByte("g", value.green.toByte())
        tag.setByte("b", value.blue.toByte())
        tag.setByte("a", value.alpha.toByte())
        return tag
    }

    override fun readBytes(buf: ByteBuf, existing: Color?, syncing: Boolean): Color {
        return Color(buf.readInt())
    }

    override fun writeBytes(buf: ByteBuf, value: Color, syncing: Boolean) {
        buf.writeInt(value.rgb)
    }
}

@SerializerRegister(ItemStack::class)
object SerializeItemStack : Serializer<ItemStack>(FieldType.create(ItemStack::class.java)) {
    override fun getDefault(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun readNBT(nbt: NBTBase, existing: ItemStack?, syncing: Boolean): ItemStack {
        return ItemStack(nbt.castOrDefault(NBTTagCompound::class.java))
    }

    override fun writeNBT(value: ItemStack, syncing: Boolean): NBTBase {
        return value.writeToNBT(NBTTagCompound())
    }

    override fun readBytes(buf: ByteBuf, existing: ItemStack?, syncing: Boolean): ItemStack {
        return buf.readStack()
    }

    override fun writeBytes(buf: ByteBuf, value: ItemStack, syncing: Boolean) {
        buf.writeStack(value)
    }
}

@SerializerRegister(FluidStack::class)
object SerializeFluidStack : Serializer<FluidStack>(FieldType.create(FluidStack::class.java)) {
    override fun readNBT(nbt: NBTBase, existing: FluidStack?, syncing: Boolean) =
            FluidStack.loadFluidStackFromNBT(nbt.castOrDefault(NBTTagCompound::class.java))!!

    override fun writeNBT(value: FluidStack, syncing: Boolean) =
            value.writeToNBT(NBTTagCompound())!!

    override fun readBytes(buf: ByteBuf, existing: FluidStack?, syncing: Boolean) =
            buf.readFluidStack() ?: FluidStack(FluidRegistry.WATER, 0)

    override fun writeBytes(buf: ByteBuf, value: FluidStack, syncing: Boolean) =
            buf.writeFluidStack(value)

    override fun getDefault(): FluidStack {
        return FluidStack(FluidRegistry.WATER, 0)
    }
}

@SerializerRegister(ItemStackHandler::class)
object SerializeItemStackHandler : Serializer<ItemStackHandler>(FieldType.create(ItemStackHandler::class.java)) {
    override fun getDefault(): ItemStackHandler {
        return ItemStackHandler()
    }

    override fun readNBT(nbt: NBTBase, existing: ItemStackHandler?, syncing: Boolean): ItemStackHandler {
        val handler = ItemStackHandler()
        handler.deserializeNBT(nbt.castOrDefault(NBTTagCompound::class.java))
        return handler
    }

    override fun writeNBT(value: ItemStackHandler, syncing: Boolean): NBTBase {
        return value.serializeNBT()
    }

    override fun readBytes(buf: ByteBuf, existing: ItemStackHandler?, syncing: Boolean): ItemStackHandler {
        val handler = existing ?: ItemStackHandler()
        handler.deserializeNBT(buf.readTag())
        return handler
    }

    override fun writeBytes(buf: ByteBuf, value: ItemStackHandler, syncing: Boolean) {
        buf.writeTag(value.serializeNBT())
    }
}

@SerializerRegister(UUID::class)
object SerializeUUID : Serializer<UUID>(FieldType.create(UUID::class.java)) {
    override fun getDefault(): UUID {
        return UUID(0, 0)
    }

    override fun readNBT(nbt: NBTBase, existing: UUID?, syncing: Boolean): UUID {
        val tag = nbt as? NBTTagString
        return if (tag == null)
            UUID.randomUUID()
        else
            UUID.fromString(tag.string)
    }

    override fun writeNBT(value: UUID, syncing: Boolean): NBTBase {
        return NBTTagString(value.toString())
    }

    override fun readBytes(buf: ByteBuf, existing: UUID?, syncing: Boolean): UUID {
        return UUID(buf.readLong(), buf.readLong())
    }

    override fun writeBytes(buf: ByteBuf, value: UUID, syncing: Boolean) {
        buf.writeLong(value.mostSignificantBits)
        buf.writeLong(value.leastSignificantBits)
    }
}

@SerializerRegister(ITextComponent::class)
object SerializeITextComponent : Serializer<ITextComponent>(FieldType.create(ITextComponent::class.java)) {
    override fun getDefault(): ITextComponent {
        return TextComponentString("NULL")
    }

    override fun readNBT(nbt: NBTBase, existing: ITextComponent?, syncing: Boolean): ITextComponent {
        return ITextComponent.Serializer.jsonToComponent(nbt.castOrDefault(NBTTagString::class.java).string)!!
    }

    override fun writeNBT(value: ITextComponent, syncing: Boolean): NBTBase {
        return NBTTagString(ITextComponent.Serializer.componentToJson(value))
    }

    override fun readBytes(buf: ByteBuf, existing: ITextComponent?, syncing: Boolean): ITextComponent {
        return PacketBuffer(buf).readTextComponent() ?: TextComponentString("") // #BlameLordMau
    }

    override fun writeBytes(buf: ByteBuf, value: ITextComponent, syncing: Boolean) {
        PacketBuffer(buf).writeTextComponent(value)
    }
}

@SerializerRegister(ResourceLocation::class)
object SerializeResourceLocation : Serializer<ResourceLocation>(FieldType.create(ResourceLocation::class.java)) {
    override fun getDefault(): ResourceLocation {
        return ResourceLocation("minecraft:missingno")
    }

    override fun readNBT(nbt: NBTBase, existing: ResourceLocation?, syncing: Boolean): ResourceLocation {
        val tag = nbt as? NBTTagString
        return if (tag == null)
            ResourceLocation("minecraft:missingno")
        else
            ResourceLocation(tag.string)
    }

    override fun writeNBT(value: ResourceLocation, syncing: Boolean): NBTBase {
        return NBTTagString(value.toString())
    }

    override fun readBytes(buf: ByteBuf, existing: ResourceLocation?, syncing: Boolean): ResourceLocation {
        return ResourceLocation(buf.readString())
    }

    override fun writeBytes(buf: ByteBuf, value: ResourceLocation, syncing: Boolean) {
        buf.writeString(value.toString())
    }
}

@SerializerRegister(ByteBuf::class)
object SerializeByteBuf : Serializer<ByteBuf>(FieldType.create(ByteBuf::class.java)) {
    override fun getDefault(): ByteBuf {
        return Unpooled.buffer()
    }

    override fun readNBT(nbt: NBTBase, existing: ByteBuf?, syncing: Boolean): ByteBuf {
        val tag = nbt as? NBTTagByteArray
        return if (tag == null)
            Unpooled.EMPTY_BUFFER
        else
            Unpooled.wrappedBuffer(tag.byteArray)
    }

    override fun writeNBT(value: ByteBuf, syncing: Boolean): NBTBase {
        val bytes = ByteArray(value.readableBytes())
        value.readBytes(bytes)
        return NBTTagByteArray(bytes)
    }

    override fun readBytes(buf: ByteBuf, existing: ByteBuf?, syncing: Boolean): ByteBuf {
        val bytes = ByteArray(buf.readVarInt())
        buf.readBytes(bytes)
        return Unpooled.wrappedBuffer(bytes)
    }

    override fun writeBytes(buf: ByteBuf, value: ByteBuf, syncing: Boolean) {
        val bytes = ByteArray(value.readableBytes())
        value.readBytes(bytes)
        buf.writeVarInt(bytes.size)
        buf.writeBytes(bytes)
    }
}
