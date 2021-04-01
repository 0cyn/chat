/*
 * Created by JFormDesigner on Wed Mar 31 12:44:35 CDT 2021
 */

package me.krit.hychat.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import me.krit.hychat.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author unknown
 */
public class HyChatFrame extends JFrame
{

    private final Map<String, Component> tabsByName = new HashMap<>();

    public HyChatFrame()
    {
        FlatDarkLaf.install();
        System.out.println(UIManager.getLookAndFeel().getName());
        initComponents();
        setTitle("HyChat");
        Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
        setIconImage(icon);
        SwingUtilities.updateComponentTreeUI(getContentPane());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void initComponents()
    {


        //======== this ========
        Container contentPane = getContentPane();

        //======== tabbedPane1 ========
        {
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(tabbedPane)
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(tabbedPane)
        );
        pack();
        setLocationRelativeTo(getOwner());
        setPreferredSize(new Dimension(600, 400));
        setSize(600, 400);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public void createTabWithName(String name)
    {
        TabPanel tab = new TabPanel(name);
        tabbedPane.addTab(name, tab);
        tabsByName.put(name, tab);
    }

    public void addLineToTabWithName(String name, String message)
    {
        //:vomit:
        StringTokenizer st = new StringTokenizer(message, "", false);
        StringBuilder formatted = new StringBuilder();
        boolean start = true;
        int i = 0;
        boolean skip = false;
        for (char token : message.toCharArray())
        {
            if (token=='\u00A7')
            {
                if (start)
                {
                    start=false;
                }
                else
                {
                    formatted.append("</span>");
                }
                skip = true;
                char color = message.toCharArray()[i+1];
                switch (color)
                {
                    case '0':
                        formatted.append("<span style=\"color:#000000\">");
                        break;
                    case '1':
                        formatted.append("<span style=\"color:#0000AA\">");
                        break;
                    case '2':
                        formatted.append("<span style=\"color:#00AA00\">");
                        break;
                    case '3':
                        formatted.append("<span style=\"color:#00AAAA\">");
                        break;
                    case '4':
                        formatted.append("<span style=\"color:#AA0000\">");
                        break;
                    case '5':
                        formatted.append("<span style=\"color:#AA00AA\">");
                        break;
                    case '6':
                        formatted.append("<span style=\"color:#FFAA00\">");
                        break;
                    case '7':
                        formatted.append("<span style=\"color:#AAAAAA\">");
                        break;
                    case '8':
                        formatted.append("<span style=\"color:#555555\">");
                        break;
                    case '9':
                        formatted.append("<span style=\"color:#5555FF\">");
                        break;
                    case 'a':
                        formatted.append("<span style=\"color:#55FF55\">");
                        break;
                    case 'b':
                        formatted.append("<span style=\"color:#55FFFF\">");
                        break;
                    case 'c':
                        formatted.append("<span style=\"color:#FF5555\">");
                        break;
                    case 'd':
                        formatted.append("<span style=\"color:#FF55FF\">");
                        break;
                    case 'e':
                        formatted.append("<span style=\"color:#FFFF55\">");
                        break;
                    case 'f':
                        formatted.append("<span style=\"color:#FFFFFF\">");
                        break;
                    default:
                        //TODO: hijack something and render this properly or w/e
                        formatted.append("<span style=\"color:#FFFFFF\">");
                        break;
                }
            }
            else
            {
                if (!skip)
                formatted.append(token);
                else
                    skip = false;
            }
            i++;
        }

        if (!start)
        {
            formatted.append("</span>");
        }

        TabPanel tab = (TabPanel) tabsByName.get(name);
        tab.appendToChatPane(formatted.toString());
    }

    private JTabbedPane tabbedPane = new JTabbedPane();

    private class TabPanel extends JPanel
    {
        private String contextName;

        private TabPanel(String contextName)
        {
            this.contextName = contextName;
            initComponents();
        }

        public void appendToChatPane(String string)
        {
            HTMLDocument doc = (HTMLDocument)chatEditorPane.getDocument();
            HTMLEditorKit editorKit = (HTMLEditorKit)chatEditorPane.getEditorKit();
            try
            {
                editorKit.insertHTML(doc, doc.getLength(), string, 0, 0, null);
            }
            catch (BadLocationException | IOException e)
            {
                e.printStackTrace();
            }
            chatEditorPane.setCaretPosition(chatEditorPane.getDocument().getLength());
        }

        private void initComponents()
        {
            scrollPane1 = new JScrollPane();
            chatEditorPane = new JEditorPane();
            textArea1 = new JTextField();
            scrollPane2 = new JScrollPane();
            memberEditorPane = new JEditorPane();

            //======== this ========



            //======== scrollPane1 ========
            {
                scrollPane1.setBorder(BorderFactory.createEmptyBorder());

                //---- editorPane2 ----
                chatEditorPane.setBorder(null);
                chatEditorPane.setEditable(false);
                chatEditorPane.setContentType("text/html");
                scrollPane1.setViewportView(chatEditorPane);
                scrollPane1.setAutoscrolls(true);
                DefaultCaret caret = (DefaultCaret)chatEditorPane.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            }

            //======== scrollPane2 ========
            {
                scrollPane2.setBorder(null);
                scrollPane2.setViewportView(memberEditorPane);
            }

            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridx=0;
            c.gridy=0;
            c.weightx = 1;
            c.weighty = 1;
            c.anchor=GridBagConstraints.PAGE_START;
            c.insets = new Insets(10,10,0,10);
            add(scrollPane1, c);
            textArea1.setMaximumSize(new Dimension(
                    Integer.MAX_VALUE,
                    30
            ));
            c.gridy=1;
            c.weighty = 0;
            c.insets = new Insets(10,10,10,10);
            c.fill=GridBagConstraints.HORIZONTAL;
            c.anchor=GridBagConstraints.PAGE_END;
            add(textArea1, c);

            Action action = new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String msg = textArea1.getText();
                    Client.getInstance().currentServer.sendChatFromTab(((TabPanel) textArea1.getParent()).contextName, msg);
                    textArea1.setText("");
                }
            };
            textArea1.addActionListener(action);
        }

        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        // Generated using JFormDesigner Evaluation license - unknown
        private JScrollPane scrollPane1;
        private JEditorPane chatEditorPane;
        private JTextField textArea1;
        private JScrollPane scrollPane2;
        private JEditorPane memberEditorPane;
        // JFormDesigner - End of variables declaration  //GEN-END:variables
    }
}
