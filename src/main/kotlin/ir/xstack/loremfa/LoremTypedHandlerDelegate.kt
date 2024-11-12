package ir.xstack.loremfa

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.InputStream
import java.nio.charset.StandardCharsets


class LoremTypedHandlerDelegate : TypedHandlerDelegate() {
    override fun newTypingStarted(c: Char, editor: Editor, context: DataContext) {
        super.newTypingStarted(c, editor, context)
        val str = getCurrentLine(editor)
        var loremIpsumContent : String? = null
        if (c.code == 32) {
            val pattern = "\\s*lorem(\\d+)\\s*".toRegex()
            val match = pattern.find(str)
            if (match != null) {
                val numberString = match.groupValues[1] // groupValues[1] will contain the first capturing group - the digit
                val wordCount = numberString.toInt()

                val startOffset = getWordOffset(editor)

                try {

                    val resourceStream: InputStream? = javaClass.classLoader.getResourceAsStream("textreplacments.txt")
                    val textFromFile = resourceStream?.bufferedReader(StandardCharsets.UTF_8)?.use(BufferedReader::readText) ?: return
                    val textArray = textFromFile.split(" ").map { it.trim() }.toTypedArray()
                    // Check if word count is within the text array size
                    loremIpsumContent = if (wordCount <= textArray.size) {
                        StringUtils.join(textArray, ' ', 0, wordCount)
                    } else {
                        // Generate a larger lorem content if word count exceeds array size
                        buildString {
                            for (i in 0 until wordCount) {
                                append(textArray[i % textArray.size]).append(' ')
                            }
                        }.trim()
                    }

                    val endOffset = startOffset + "lorem$numberString".length
                    if (loremIpsumContent != null) {
                        editor.document.replaceString(startOffset, endOffset, loremIpsumContent)
                    }
                } catch (ignored: NumberFormatException) {
                    println("$greenColor $ignored $reset")
                    throw ignored

                }
            }
        }


    }


}

