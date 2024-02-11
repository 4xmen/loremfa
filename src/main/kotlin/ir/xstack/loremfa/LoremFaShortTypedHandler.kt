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

class LoremFaShortTypedHandler : TypedHandlerDelegate() {

    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        val document = editor.document
        val offset = editor.caretModel.offset
        val word = "loremfashort"
        val wordLen = word.length
        if (offset >= wordLen) {
            val start = offset - wordLen
            val end = offset
            val textToCheck = document.getText(com.intellij.openapi.util.TextRange(start, end))
            if (textToCheck == word) {
                replaceText(project, document, start, end)
            }
        }
        return super.charTyped(c, project, editor, file)
    }

    private fun replaceText(project: Project, document: Document, start: Int, end: Int) {
        WriteCommandAction.runWriteCommandAction(project) {
            val resourceStream = javaClass.classLoader.getResourceAsStream("textreplacments.txt")
            val textFromFile = resourceStream?.bufferedReader(StandardCharsets.UTF_8)?.use(BufferedReader::readText) ?: return@runWriteCommandAction
            val words = textFromFile.split("\\s+".toRegex()).take(15).joinToString(" ")
            document.replaceString(start, end, words)
        }
    }
}