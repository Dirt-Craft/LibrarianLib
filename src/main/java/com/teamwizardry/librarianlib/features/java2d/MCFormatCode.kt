package com.teamwizardry.librarianlib.features.java2d

import java.awt.Color
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument

typealias CustomFormatHandler = (attrs: SimpleAttributeSet) -> Unit
typealias CustomTextTransformer = (text: String, attrs: SimpleAttributeSet, document: StyledDocument) -> String
class MCFormatCode<T>(val type: Any?, val value: T, val prep: CustomFormatHandler) {
    var code: Char? = null
        private set

    fun register(char: Char): MCFormatCode<T> {
        if (code != null)
            throw IllegalStateException("Format code cannot be registered for '$char', " +
                    "it is already registered for '$code'")
        if (registrations[char] != null)
            throw IllegalStateException("Format code cannot be registered for '$char', " +
                    "another format code is already registered for it")
        code = char
        registrations[char] = this
        return this
    }

    companion object {
        const val CustomTransformerType = "CustomTransformerType"
        const val UnderlineType = "CustomUnderline"
        const val StrikeThroughType = "CustomStrikeThrough"

        private val registrations = mutableMapOf<Char, MCFormatCode<*>>()

        operator fun get(char: Char): MCFormatCode<*>? {
            return registrations[char]
        }

        private val colorClearCallback: CustomFormatHandler = { attrs ->
            attrs.removeAttribute(StyleConstants.Foreground)
            attrs.removeAttribute(StyleConstants.Bold)
            attrs.removeAttribute(StyleConstants.Italic)
            attrs.removeAttribute(UnderlineType)
            attrs.removeAttribute(StrikeThroughType)
            attrs.removeAttribute(CustomTransformerType)
        }

        private val decorationClearCallback: CustomFormatHandler = { attrs ->
            attrs.removeAttribute(StyleConstants.Bold)
            attrs.removeAttribute(StyleConstants.Italic)
            attrs.removeAttribute(UnderlineType)
            attrs.removeAttribute(StrikeThroughType)
            attrs.removeAttribute(CustomTransformerType)
        }

        @JvmField
        val black: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x000000),
                prep = colorClearCallback)
                .register('0')
        @JvmField
        val darkBlue: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x002aa6),
                prep = colorClearCallback)
                .register('1')
        @JvmField
        val darkGreen: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x43a217),
                prep = colorClearCallback)
                .register('2')
        @JvmField
        val darkAqua: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x43a6a8),
                prep = colorClearCallback)
                .register('3')
        @JvmField
        val darkRed: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x9f261c),
                prep = colorClearCallback)
                .register('4')
        @JvmField
        val darkPurple: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x9f3aa8),
                prep = colorClearCallback)
                .register('5')
        @JvmField
        val gold: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0xf5ab37),
                prep = colorClearCallback)
                .register('6')
        @JvmField
        val gray: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0xaaaaaa),
                prep = colorClearCallback)
                .register('7')
        @JvmField
        val darkGray: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x555555),
                prep = colorClearCallback)
                .register('8')
        @JvmField
        val blue: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x5469fa),
                prep = colorClearCallback)
                .register('9')
        @JvmField
        val green: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x81f45d),
                prep = colorClearCallback)
                .register('a')
        @JvmField
        val aqua: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0x81fafc),
                prep = colorClearCallback)
                .register('b')
        @JvmField
        val red: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0xf06760),
                prep = colorClearCallback)
                .register('c')
        @JvmField
        val lightPurple: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0xf077fd),
                prep = colorClearCallback)
                .register('d')
        @JvmField
        val yellow: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0xfff967),
                prep = colorClearCallback)
                .register('e')
        @JvmField
        val white: MCFormatCode<Color> = MCFormatCode(type = StyleConstants.Foreground, value = Color(0xffffff),
                prep = colorClearCallback)
                .register('f')

        @JvmField
        val obfuscated: MCFormatCode<CustomTextTransformer> = MCFormatCode(type = CustomTransformerType,
                value = { text: String, attrs: SimpleAttributeSet, document: StyledDocument ->
                    val font = document.getFont(attrs)
                    MCTextObfuscator.obfuscate(text, font)
                }, prep = decorationClearCallback)
                .register('k')

        @JvmField
        val bold: MCFormatCode<Boolean> = MCFormatCode(type = StyleConstants.Bold, value = true,
                prep = decorationClearCallback)
                .register('l')

        @JvmField
        val italic: MCFormatCode<Boolean> = MCFormatCode(type = StyleConstants.Italic, value = true,
                prep = decorationClearCallback)
                .register('o')

        @JvmField
        val strikethrough: MCFormatCode<Boolean> = MCFormatCode(type = StrikeThroughType, value = true,
                prep = decorationClearCallback)
                .register('m')

        @JvmField
        val underline: MCFormatCode<Boolean> = MCFormatCode(type = UnderlineType, value = true,
                prep = decorationClearCallback)
                .register('n')

        @JvmField
        val reset: MCFormatCode<Nothing?> = MCFormatCode(type = null, value = null,
                prep = { attrs: SimpleAttributeSet ->
                    attrs.removeAttribute(StyleConstants.Foreground)
                    attrs.removeAttribute(StyleConstants.Bold)
                    attrs.removeAttribute(StyleConstants.Italic)
                    attrs.removeAttribute(UnderlineType)
                    attrs.removeAttribute(StrikeThroughType)
                    attrs.removeAttribute(CustomTransformerType)
                })
                .register('r')

    }
}
