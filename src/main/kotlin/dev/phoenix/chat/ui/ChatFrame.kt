package dev.phoenix.chat.ui

import com.formdev.flatlaf.FlatDarkLaf
import dev.phoenix.chat.ui.TabCloseCallbackHolder.consumer
import java.awt.*
import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO
import javax.swing.*


/**
 * @author _kritanta
 */
class ChatFrame : JFrame() {
    var tabsByName: MutableMap<String, Component> = HashMap()

    fun destroyTabs() {
        while (tabbedPane.tabCount > 0)
        {
            tabbedPane.remove(0)
        }
        tabsByName = HashMap()
    }

    fun createTabWithName(name: String) {
        val tab = TabPanel(name, this)
        tabbedPane.addTab(name, tab)
        tabsByName[name] = tab
    }

    fun addLineToTabWithName(name: String?, message: String) {

        if (!tabsByName.containsKey(name) && name != null)
            createTabWithName(name)
            
        val tab = tabsByName[name] as TabPanel? 
        tab!!.appendToChatPane(message)
    }

    private fun initComponents() {

        val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tabbedPane, plistPane)
        plistScrollPane.maximumSize = Dimension(200, 20000)


        val contentPane = contentPane

        val contentPaneLayout = GroupLayout(contentPane)
        contentPane.layout = contentPaneLayout
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(splitPane)
        )

        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(splitPane)
        )
        plistPane = JList(plistModel)
        plistScrollPane.add(plistPane)
        pack()
        splitPane.resetToPreferredSizes()
        splitPane.dividerLocation = 600

        setLocationRelativeTo(owner)
        preferredSize = Dimension(800, 500)
        setSize(800, 500)

        tabbedPane.putClientProperty("JTabbedPane.tabClosable", true)

        tabbedPane.putClientProperty("JTabbedPane.tabCloseCallback", consumer)


        // Options
        // - 

        var toolbar = JToolBar()
        var options = JMenu("Options")
        var filter = JMenu("Filter")

    }

    val tabbedPane = JTabbedPane()
    val plistModel: DefaultListModel<String> = DefaultListModel<String>()
    var plistPane = JList(plistModel)
    val plistScrollPane = JScrollPane()
    init {
        FlatDarkLaf.install()
        println(UIManager.getLookAndFeel().name)
        initComponents()
        title = "Chat"
        val icon: Image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE)
        iconImage = icon
        SwingUtilities.updateComponentTreeUI(contentPane)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        this.isVisible = true
    }
}