package dev.phoenix.chat.ui 

import com.formdev.flatlaf.FlatDarkLaf
import dev.phoenix.chat.Client
import dev.phoenix.chat.ui.TabCloseCallbackHolder.*
import dev.phoenix.chat.ui.TabPanel
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.*
import java.util.function.BiConsumer
import java.util.function.IntConsumer
import javax.swing.*
import javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultCaret
import javax.swing.text.html.HTMLDocument
import javax.swing.text.html.HTMLEditorKit

/**
 * Individual tab class.
 */
class TabPanel(private val contextName: String) : JPanel() {
    fun appendToChatPane(string: String?) {
        val doc = chatEditorPane!!.document as HTMLDocument
        val editorKit = chatEditorPane!!.editorKit as HTMLEditorKit
        try {
            editorKit.insertHTML(doc, doc.length, string, 0, 0, null)
        } catch (e: BadLocationException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        chatEditorPane!!.caretPosition = chatEditorPane!!.document.length
    }

    private fun initComponents() {
        scrollPane1 = JScrollPane()
        chatEditorPane = JEditorPane()
        textArea1 = JTextField()
        scrollPane2 = JScrollPane()
        memberEditorPane = JEditorPane()
        scrollPane1!!.horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_NEVER
        scrollPane2!!.horizontalScrollBarPolicy = HORIZONTAL_SCROLLBAR_NEVER

        scrollPane1!!.border = BorderFactory.createEmptyBorder()

        chatEditorPane!!.border = null
        chatEditorPane!!.isEditable = false
        chatEditorPane!!.contentType = "text/html"
        scrollPane1!!.setViewportView(chatEditorPane)
        scrollPane1!!.autoscrolls = true
        val caret = chatEditorPane!!.caret as DefaultCaret
        caret.updatePolicy = DefaultCaret.ALWAYS_UPDATE

        scrollPane2!!.border = null
        scrollPane2!!.setViewportView(memberEditorPane)
        

        layout = GridBagLayout()
        val c = GridBagConstraints()
        c.fill = GridBagConstraints.BOTH
        c.gridx = 0
        c.gridy = 0
        c.weightx = 1.0
        c.weighty = 1.0
        c.anchor = GridBagConstraints.PAGE_START
        c.insets = Insets(10, 10, 0, 10)
        add(scrollPane1!!, c)
        textArea1!!.maximumSize = Dimension(Int.MAX_VALUE,
                30
        )
        c.gridy = 1
        c.weighty = 0.0
        c.insets = Insets(10, 10, 10, 10)
        c.fill = GridBagConstraints.HORIZONTAL
        c.anchor = GridBagConstraints.PAGE_END
        add(textArea1!!, c)

        val action: Action = object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent) {
                val msg = textArea1!!.text
                Client.currentServer?.sendChatFromTab((textArea1!!.parent as TabPanel).contextName, msg)
                textArea1!!.text = ""
            }
        }

        textArea1!!.addActionListener(action)
    }

    private var scrollPane1: JScrollPane? = null
    private var chatEditorPane: JEditorPane? = null
    private var textArea1: JTextField? = null
    private var scrollPane2: JScrollPane? = null
    private var memberEditorPane: JEditorPane? = null

    init {
        initComponents()
    }
}