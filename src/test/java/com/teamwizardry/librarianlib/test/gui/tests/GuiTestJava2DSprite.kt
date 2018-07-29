package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.*
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.sprite.ISprite
import com.teamwizardry.librarianlib.features.sprite.Java2DSprite
import com.teamwizardry.librarianlib.features.sprite.Sprite
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import java.awt.*
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.awt.geom.Ellipse2D
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D
import java.text.AttributedCharacterIterator
import java.text.AttributedString

/**
 * Created by TheCodeWarrior
 */
class GuiTestJava2DSprite : GuiBase(300, 150) {
    init {
        val c2 = ComponentRect(-6, -3, 312, 156)
        c2.color.setValue(Color.BLACK)
        mainComponents.add(c2)

        val c = ComponentRect(-4, -1, 308, 152)
        c.color.setValue(Color.LIGHT_GRAY)
        mainComponents.add(c)

        val c3 = ComponentRect(148, -1, 4, 152)
        c3.color.setValue(Color.BLACK)
        mainComponents.add(c3)

        //==================================================================

        val j2dTextComponent = ComponentJava2DText(-3, 0)
        j2dTextComponent.size = vec(150, 150)
        j2dTextComponent.text.setValue(
                "Plain text\n" +
                        "§0R§1a§2i§3n§4b§5o§6w§7s§8! §9C§ao§bl§co§dr§es§f!§r\n" +
                        "This is §lbold§r\n" +
                        "This is §mstrikethrough§r\n" +
                        "This is §nunderline§r\n" +
                        "This is §oitalic§r\n"
        )
        mainComponents.add(j2dTextComponent)

        val textComponent = ComponentText(153, 0)
        textComponent.unicode.setValue(true)
        textComponent.wrap.setValue(150)
        textComponent.text.setValue(
                "Plain text\n" +
                        "§0R§1a§2i§3n§4b§5o§6w§7s§8! §9C§ao§bl§co§dr§es§f!§r\n" +
                        "This is §lbold§r\n" +
                        "This is §mstrikethrough§r\n" +
                        "This is §nunderline§r\n" +
                        "This is §oitalic§r\n"
        )
        mainComponents.add(textComponent)
    }
}
