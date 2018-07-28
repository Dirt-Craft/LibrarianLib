package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.component.GuiComponentEvents
import com.teamwizardry.librarianlib.features.gui.components.ComponentJava2DText
import com.teamwizardry.librarianlib.features.gui.components.ComponentRect
import com.teamwizardry.librarianlib.features.gui.components.ComponentSprite
import com.teamwizardry.librarianlib.features.gui.components.ComponentText
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.java2d.SimpleStringRenderer
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
        val c2 = ComponentRect(-2, -2, 304, 154)
        c2.color.setValue(Color.BLACK)
        mainComponents.add(c2)

        val c = ComponentRect(0, 0, 300, 150)
        c.color.setValue(Color.LIGHT_GRAY)
        mainComponents.add(c)

        val c3 = ComponentRect(148, 0, 4, 150)
        c3.color.setValue(Color.BLACK)
        mainComponents.add(c3)
        //==================================================================
        val j2dTextComponent = ComponentJava2DText(1, 1)
        j2dTextComponent.size = vec(150, 150)
        j2dTextComponent.text.setValue(
                "Lorem ipsum dolor §2sit amet§r, §lconsectetur§r adipiscing §melit.§r" +
                " §nInteger a§r orci ut arcu scelerisque tempus. Nunc finibus, erat non pellentesque egestas," +
                " neque massa volutpat lectus, eu consequat sapien lacus ut lorem."
        )
        mainComponents.add(j2dTextComponent)

        val textComponent = ComponentText(153, 1)
        textComponent.unicode.setValue(true)
        textComponent.wrap.setValue(146)
        textComponent.text.setValue(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Integer a orci ut arcu scelerisque tempus. Nunc finibus, erat non pellentesque egestas," +
                " neque massa volutpat lectus, eu consequat sapien lacus ut lorem."
        )
        mainComponents.add(textComponent)
    }
}
