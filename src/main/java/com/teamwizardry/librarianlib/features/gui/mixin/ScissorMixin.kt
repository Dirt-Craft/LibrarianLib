package com.teamwizardry.librarianlib.features.gui.mixin

import com.teamwizardry.librarianlib.features.gui.GuiComponent
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.kotlin.minus
import com.teamwizardry.librarianlib.features.utilities.client.ScissorUtil

object ScissorMixin {

    fun <T : GuiComponent<T>> scissor(component: GuiComponent<T>) {
        component.BUS.hook(GuiComponent.PreDrawEvent::class.java) { event ->
            val root = event.component.unTransformRoot(event.component, vec(0, 0))
            val size = event.component.unTransformRoot(event.component, event.component.size) - root
            ScissorUtil.push()
            ScissorUtil.set(root.xi, root.yi, size.xi, size.yi)
            ScissorUtil.enable()
        }
        component.BUS.hook(GuiComponent.PostDrawEvent::class.java) { ScissorUtil.pop() }
    }

}
