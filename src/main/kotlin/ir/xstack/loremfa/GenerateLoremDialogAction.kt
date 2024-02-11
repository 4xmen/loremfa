package ir.xstack.loremfa
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import java.io.BufferedReader
import java.nio.charset.StandardCharsets
import com.intellij.openapi.command.WriteCommandAction
class GenerateLoremDialogAction : AnAction() {



    override fun actionPerformed(e: AnActionEvent) {

        val editor = e.getData(PlatformDataKeys.EDITOR)
        val document = editor?.document
        val project = e.project

        // Show input dialog to get a number
        val userInput = Messages.showInputDialog(
                e.project,
                "Enter the number of words:",
                "Generate Persian Lorem",
                Messages.getQuestionIcon()
        )

        // Check if user input is not null and is a valid number
        val number = userInput?.toIntOrNull()
        if (number != null) {
            val selectionModel = editor?.selectionModel
            val start = selectionModel?.selectionStart
            val end = selectionModel?.selectionEnd

            // Load the text from the resource folder
            val resourceStream = javaClass.classLoader.getResourceAsStream("textreplacments.txt")
            val textFromFile = resourceStream?.bufferedReader(StandardCharsets.UTF_8)?.use(BufferedReader::readText)
            val words = textFromFile?.split("\\s+".toRegex())?.take(15)?.joinToString(" ") ?: ""

            WriteCommandAction.runWriteCommandAction(project) {
                val offset = editor?.caretModel?.offset
                if (document != null) {
                    if (offset != null) {
                        document.insertString(offset,words)
                    }
                }
                Messages.showMessageDialog(project, "Inserted successfully.", "Info", Messages.getInformationIcon())
            }
        } else if (userInput != null) {
            // Handle invalid input
            Messages.showErrorDialog(
                    e.project,
                    "Please enter a valid number.",
                    "Invalid Input"
            )
        }
    }
}