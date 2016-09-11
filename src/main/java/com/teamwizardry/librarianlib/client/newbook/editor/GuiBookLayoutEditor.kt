package com.teamwizardry.librarianlib.client.newbook.editor

import com.teamwizardry.librarianlib.client.font.LLFontRenderer
import com.teamwizardry.librarianlib.client.font.StringRenderer
import com.teamwizardry.librarianlib.client.gui.GuiBase
import com.teamwizardry.librarianlib.client.gui.GuiComponent
import com.teamwizardry.librarianlib.client.gui.components.*
import com.teamwizardry.librarianlib.client.gui.mixin.DragMixin
import com.teamwizardry.librarianlib.client.gui.mixin.DrawBorderMixin
import com.teamwizardry.librarianlib.client.gui.mixin.ResizableMixin
import com.teamwizardry.librarianlib.client.newbook.backend.Book
import com.teamwizardry.librarianlib.client.sprite.Texture
import com.teamwizardry.librarianlib.common.util.math.Vec2d
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by TheCodeWarrior
 */
class GuiBookLayoutEditor(val book: Book) : GuiBase(0, 0) {

    var selected: GuiComponent<*>? = null

    companion object {
        val TEX = Texture(ResourceLocation("librarianlib:textures/gui/editorUI.png"))
    }

    var bookWidth = 100
        set(value) {
            field = value
            mainComponent.size = mainComponent.size.setX(value.toDouble())
        }
    var bookHeight = 150
        set(value) {
            field = value
            mainComponent.size = mainComponent.size.setY(value.toDouble())
        }

    val mainComponent = ComponentVoid(0,0, bookWidth, bookHeight)
    val sidebarList = ComponentRect(0, 0, 100, 0)
    val sidebarInfo = ComponentRect(0, 0, 100, 0)
    val sprites = ComponentVoid(0, 0, 0, 0)

    val sidebar_size: ComponentText
    val sidebar_pos: ComponentText
    val sidebar_zIndex: ComponentTextInput

    init {

        val center = ComponentCenterAlign(0,0,true,true)
        center.add(mainComponent)
        DrawBorderMixin(mainComponent).color.setValue(Color.MAGENTA)

        sidebarList.color.setValue(Color.LIGHT_GRAY)
        sidebarInfo.color.setValue(Color.LIGHT_GRAY)
        fullscreenComponents.BUS.hook(GuiComponent.SetSizeEvent::class.java) {
            resize(it.size)
        }

        components.add(center)
        fullscreenComponents.add(sidebarList)
        fullscreenComponents.add(sidebarInfo)


        //region Sidebar stuff
        //==============================================================================================================

        sidebar_pos = ComponentText(1, 1, ComponentText.TextAlignH.LEFT, ComponentText.TextAlignV.TOP)
        sidebar_pos.text.func {
            val sel = selected
            if(sel == null)
                "(N/A, N/A)"
            else
                "(${sel.pos.xi}, ${sel.pos.yi})"
        }
        sidebarInfo.add(sidebar_pos)

        sidebar_size = ComponentText(1, 10, ComponentText.TextAlignH.LEFT, ComponentText.TextAlignV.TOP)
        sidebar_size.text.func {
            val sel = selected
            if(sel == null)
                "N/A x N/A"
            else
                "${sel.size.xi} x ${sel.size.yi}"
        }

        sidebarInfo.add(sidebar_size)


        sidebar_zIndex = ComponentTextInput(0, 20, 100, 100)

        sidebarInfo.add(ComponentRect(sidebar_zIndex.pos.xi, sidebar_zIndex.pos.yi, sidebar_zIndex.size.xi, sidebar_zIndex.size.yi))
        sidebarInfo.add(sidebar_zIndex)
        //==============================================================================================================
        //endregion

        sprites.calculateOwnHover = false
        components.add(sprites)

        val componentsbg = ComponentVoid(-1000, -1000, 1000, 1000)
        componentsbg.zIndex = -1000
        componentsbg.BUS.hook(GuiComponent.MouseDownEvent::class.java) {
            if(componentsbg.mouseOver) select(null)
        }
        sprites.add(componentsbg)

        val rect = createRect()
        createRect()
        createRect()
        createRect()
        createRect()
        createRect()
        createRect()
        createRect()

        selected = rect

        val sr = StringRenderer()
        sr.addText("Hello world! a¡b™c£d¢e∞f§g¶h•iªjºk")
        sr.buildText()
        sidebarInfo.BUS.hook(GuiComponent.PostDrawEvent::class.java) { event ->
            GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA)
            sr.render(0, 30)
        }

    }

    fun createRect(): GuiComponent<*> {
        val rect = ComponentRect(0, 0, 25, 25)
        val color = Color(ThreadLocalRandom.current().nextInt(0, 255), ThreadLocalRandom.current().nextInt(0, 255), ThreadLocalRandom.current().nextInt(0, 255))
        rect.color.func { color }
        ResizableMixin(rect, 8, false)
        DragMixin(rect, { it })

        rect.BUS.hook(GuiComponent.MouseDownEvent::class.java) { if(rect.mouseOver) select(rect) }

        sprites.add(rect)
        return rect
    }

    fun select(component: GuiComponent<*>?) {
        selected = component

    }

    fun resize(size: Vec2d) {
        sidebarInfo.size = sidebarInfo.size.setY(size.y)

        sidebarList.size = sidebarList.size.setY(size.y)
        sidebarList.pos = sidebarList.pos.setX(size.x - sidebarList.size.x)
    }

}