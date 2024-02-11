package ir.xstack.loremfa

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document

class Lorem : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val editor = e.getData(PlatformDataKeys.EDITOR)
        val document = editor?.document

        val project = e.project

        if (editor != null && document != null && project != null) {

            WriteCommandAction.runWriteCommandAction(project) {
                val offset = editor.caretModel.offset
                document.insertString(editor.caretModel.offset, "Hello World")
                editor.caretModel.moveToOffset(offset + "Hello World".length)
            }
        }
    }
}