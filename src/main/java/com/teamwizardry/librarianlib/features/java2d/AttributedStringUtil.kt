package com.teamwizardry.librarianlib.features.java2d

import java.text.AttributedCharacterIterator
import java.text.AttributedString
import java.text.CharacterIterator

object AttributedStringUtil {

    fun concat(first: AttributedString, secound: AttributedString, seperation: String): AttributedString {
        val firstString = AttributedStringUtil.getString(first)
        val secoundString = AttributedStringUtil.getString(secound)
        val resultString = firstString + seperation + secoundString
        val result = AttributedString(resultString)
        AttributedStringUtil.addAttributes(result, first, secound, seperation.length)
        return result
    }

    fun concat(first: AttributedString, secound: AttributedString): AttributedString {
        return AttributedStringUtil.concat(first, secound, "")
    }

    private fun addAttributes(result: AttributedString, first: AttributedString, secound: AttributedString, seperationOffset: Int) {
        val resultIterator = result.iterator
        val firstIterator = first.iterator
        val secoundIterator = secound.iterator

        var resultCharacter = resultIterator.current()
        var truePosition = 0
        var usePosition = 0

        while (resultCharacter != CharacterIterator.DONE) {
            usePosition = truePosition
            val it = AttributedStringUtil.getIterator(firstIterator, secoundIterator) ?: break
            if (it === secoundIterator) {
                usePosition += seperationOffset
            }
            result.addAttributes(it.attributes, usePosition, usePosition + 1)
            resultCharacter = resultIterator.next()
            it.next()
            truePosition++
        }
    }

    private fun getIterator(firstIterator: AttributedCharacterIterator, secoundIterator: AttributedCharacterIterator): AttributedCharacterIterator? {
        if (firstIterator.current() != CharacterIterator.DONE) {
            return firstIterator
        }
        return if (secoundIterator.current() != CharacterIterator.DONE) {
            secoundIterator
        } else null

    }

    fun getString(attributedString: AttributedString): String {
        val it = attributedString.iterator
        val stringBuilder = StringBuilder()

        var ch = it.current()
        while (ch != CharacterIterator.DONE) {
            stringBuilder.append(ch)
            ch = it.next()
        }
        return stringBuilder.toString()
    }
}