package ir.xstack.loremfa

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiFile
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class LoremFaGenerateTypedHandler : TypedHandlerDelegate() {

    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        val document = editor.document
        val offset = editor.caretModel.offset
        if (offset > 12) {


            val pattern = Regex("loremfa\\d+ $|loremfa $")
            val textToCheck = document.charsSequence.subSequence(0, offset).toString()
            val matchResult = pattern.find(textToCheck)

            if (matchResult != null) {
                println(matchResult.value)
                val wordlen = matchResult.value.length.toInt();

                val start = offset - wordlen
                val end = offset

                val parts = matchResult.value.substring(7);
                var valLength =  (15..99).random()
                if (parts.trim() != ""){
                    valLength = parts.trim().toInt() // Assuming "12" is at index 1
                }

                println(valLength)

                generateText(project, document, start, end, valLength)
            }
        }
        return super.charTyped(c, project, editor, file)
    }

    private fun generateText(project: Project, document: Document, start: Int, end: Int, lenght: Int) {
        WriteCommandAction.runWriteCommandAction(project) {
            val resourceStream = javaClass.classLoader.getResourceAsStream("textreplacments.txt")
            val textFromFile = resourceStream?.bufferedReader(StandardCharsets.UTF_8)?.use(BufferedReader::readText)
                    ?: return@runWriteCommandAction
            // split file by line
            val textArray = textFromFile?.split("\n")?.toTypedArray();
            val r = (0..(textArray!!.size-3)).random(); // random index
            val words = textArray!![r]?.split("\\s+".toRegex())?.take(lenght)?.joinToString(" ") ?: ""
//            val words = textFromFile.split("\\s+".toRegex()).take(lenght).joinToString(" ")
            document.replaceString(start, end, words)
        }
    }
}