@file:Suppress("DEPRECATION")

package com.teamwizardry.librarianlib.common.core

import com.teamwizardry.librarianlib.LibrarianLib
import com.teamwizardry.librarianlib.client.book.Book
import com.teamwizardry.librarianlib.common.test.BlockTest
import com.teamwizardry.librarianlib.common.util.AutomaticTileSavingHandler
import net.minecraft.util.text.translation.I18n
import net.minecraftforge.fml.common.discovery.ASMDataTable
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

/**
 * @author WireSegal
 * Created at 5:07 PM on 4/12/16.
 */
open class LibCommonProxy {

    var data: ASMDataTable? = null
    open fun pre(e: FMLPreInitializationEvent) {
        val config = e.suggestedConfigurationFile
        ConfigHandler.initConfig(config)
        data = e.asmData
        if(LibrarianLib.DEV_ENVIRONMENT && BlockTest.SHOULD_GENERATE)
            BlockTest() //just an example block
    }

    open fun init(e: FMLInitializationEvent) {
        //NO-OP
        AutomaticTileSavingHandler.init(data)
    }

    open fun post(e: FMLPostInitializationEvent) {
        // NO-OP
    }

    open fun translate(s: String, vararg format: Any?): String {
        return I18n.translateToLocalFormatted(s, *format)
    }

    open val bookInstance: Book?
        get() = null

}
