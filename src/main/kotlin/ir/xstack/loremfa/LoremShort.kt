package ir.xstack.loremfa

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.Messages
import java.io.BufferedReader
import java.nio.charset.StandardCharsets



class LoremShort : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val editor = e.getData(PlatformDataKeys.EDITOR)
        val document = editor?.document

        val project = e.project

        if (editor != null && document != null && project != null) {
            val selectionModel = editor.selectionModel
            val start = selectionModel.selectionStart
            val end = selectionModel.selectionEnd

            // Load the text from the resource folder
            val resourceStream = javaClass.classLoader.getResourceAsStream("textreplacments.txt")
            val textFromFile = resourceStream?.bufferedReader(StandardCharsets.UTF_8)?.use(BufferedReader::readText)
            // split file by line
            val textArray = textFromFile?.split("\n")?.toTypedArray();
            val r = (0..(textArray!!.size-3)).random(); // random index
            val words = textArray!![r]?.split("\\s+".toRegex())?.take(15)?.joinToString(" ") ?: ""

            WriteCommandAction.runWriteCommandAction(project) {
                // Check if the selected text is "loremfashort"
                if (start != end && document.getText(com.intellij.openapi.util.TextRange(start, end)) == "loremfash") {
                    document.replaceString(start, end, words)
                    editor.caretModel.moveToOffset(start + words.length)
//                    Messages.showMessageDialog(project, "Replacement done.", "Info", Messages.getInformationIcon())
                } else {
                    val offset = editor.caretModel.offset
                    document.insertString(offset,words)
//                    Messages.showMessageDialog(project, "Replacement done.", "Info", Messages.getInformationIcon())
                }
            }
        }
    }
}
