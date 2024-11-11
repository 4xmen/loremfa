package ir.xstack.loremfa

import com.ibm.icu.impl.duration.impl.Utils
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.util.ExceptionUtil.rethrow
import jdk.jfr.internal.SecuritySupport.getResourceAsStream
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.InputStream
import java.nio.charset.StandardCharsets


val greenColor = "\u001b[32m"
val oColor = "\u001b[32m"
val reset = "\u001b[0m"


fun getWordOffset(editor: Editor, word: String = "lorem"): Int {
    val document: Document = editor.document
    val documentText = document.text
    return documentText.indexOf(word)
}

fun getCurrentLine(editor: Editor): String {
    val lineNumber = editor.caretModel.logicalPosition.line
    val document: Document = editor.document
    val lineStartOffset: Int = document.getLineStartOffset(lineNumber)
    val lineEndOffset: Int = document.getLineEndOffset(lineNumber)
    return document.getText(TextRange(lineStartOffset, lineEndOffset))
}


