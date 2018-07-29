package com.teamwizardry.librarianlib.features.java2d

import javax.swing.text.*

class MCTextEditorKit: StyledEditorKit() {
    private val viewFactory = MCTextEditorViewFactory()

    override fun getViewFactory(): ViewFactory {
        return viewFactory
    }

    companion object {
        private val formatTokenizer = """§.|[^§]+|§""".toRegex(setOf(RegexOption.MULTILINE))

        fun insertMCString(document: StyledDocument, offset: Int, value: String) {
            val tokens = formatTokenizer.findAll(value)

            val attrs = SimpleAttributeSet()
            var start = offset
            tokens.forEach { tokenMatch ->
                val token = tokenMatch.value
                if(token.isEmpty()) return@forEach

                if(token.length == 2 && token[0] == '§') {
                    val format = MCFormatCode[token[1]]
                    val type = format?.type
                    if(format != null) {
                        format.prep(attrs)
                        if(type != null) {
                            attrs.addAttribute(format.type, format.value)
                        }
                        return@forEach
                    }
                }
                var text = token

                @Suppress("UNCHECKED_CAST")
                val transformer = attrs.getAttribute(MCFormatCode.CustomTransformerType) as? CustomTextTransformer
                if(transformer != null) {
                    text = transformer(token, attrs, document)
                }

                document.insertString(start, text, attrs.clone() as SimpleAttributeSet)
                start += text.length
            }
        }
    }
}

internal class MCTextEditorViewFactory: ViewFactory {
    override fun create(elem: Element): View {
        val kind = elem.name
        if (kind != null) {
            if (kind == AbstractDocument.ContentElementName) {
                return MCTextLabelView(elem)
            } else if (kind == AbstractDocument.ParagraphElementName) {
                return ParagraphView(elem)
            } else if (kind == AbstractDocument.SectionElementName) {
                return BoxView(elem, View.Y_AXIS)
            } else if (kind == StyleConstants.ComponentElementName) {
                return ComponentView(elem)
            } else if (kind == StyleConstants.IconElementName) {
                return IconView(elem)
            }
        }

        // default to text display
        return LabelView(elem)
    }
}



