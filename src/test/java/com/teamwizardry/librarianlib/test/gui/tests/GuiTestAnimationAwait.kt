package com.teamwizardry.librarianlib.test.gui.tests

import com.teamwizardry.librarianlib.features.gui.GuiBase
import com.teamwizardry.librarianlib.features.gui.layers.TextLayer
import com.teamwizardry.librarianlib.features.gui.provided.pastry.PastryBackground
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.math.Align2d
import kotlinx.coroutines.launch
import java.awt.Color
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

/**
 * Created by TheCodeWarrior
 */
class GuiTestAnimationAwait : GuiBase() {
    init {
        main.size = vec(100, 100)

        main.add(PastryBackground(-10, -10, 120, 120))

        val layer = TextLayer(0, 0, 10, 10)
        layer.anchor = vec(0.5, 0.5)
        layer.fitToText = true
        layer.wrap = false
        layer.align = Align2d.CENTER_TOP
        main.add(layer)

        launch {
            var i = 0
            while(true) {
                val x = ThreadLocalRandom.current().nextDouble(0.0, 100.0)
                val y = ThreadLocalRandom.current().nextDouble(0.0, 100.0)
                val newPos = vec(x, y)
                val t = Math.sqrt(newPos.squareDist(layer.pos)) * 20 / 100

                layer.pos_rm.animate(newPos, t.toFloat()).await()
                layer.text = "$i"
                i++
            }
        }

    }

    fun randomString(count: Int): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
        return ThreadLocalRandom.current()
            .ints(count.toLong(), 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
    }
}