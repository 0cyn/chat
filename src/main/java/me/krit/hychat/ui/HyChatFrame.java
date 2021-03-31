/*
 * Created by JFormDesigner on Wed Mar 31 12:44:35 CDT 2021
 */

package me.krit.hychat.ui;

import com.bulenkov.darcula.DarculaLaf;
import com.bulenkov.darcula.util.DarculaColors;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.text.BadLocationException;

/**
 * @author unknown
 */
public class HyChatFrame extends JFrame
{

    private final Map<String, Component> tabsByName = new HashMap<>();

    public HyChatFrame()
    {
        BasicLookAndFeel darcula = new DarculaLaf();
        try
        {
            UIManager.setLookAndFeel(darcula);
        }
        catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
        System.out.println(UIManager.getLookAndFeel().getName());
        initComponents();
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
        setBackground(Color.decode("#282a36"));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public void createTabWithName(String name)
    {
        TabPanel tab = new TabPanel();
        tabbedPane.addTab(name, tab);
        tabsByName.put(name, tab);
    }

    public void addLineToTabWithName(String name, String message)
    {
        TabPanel tab = (TabPanel) tabsByName.get(name);
        tab.appendToChatPane(message);
    }

    private JTabbedPane tabbedPane = new JTabbedPane();
    protected TabPanel panel1 = new TabPanel();

    private class TabPanel extends JPanel
    {
        private TabPanel()
        {
            initComponents();
        }

        public void appendToChatPane(String string)
        {
            try
            {
                chatEditorPane.getDocument().insertString(chatEditorPane.getDocument().getLength(), string, null);
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }
        }

        private void initComponents()
        {
            scrollPane1 = new JScrollPane();
            chatEditorPane = new JEditorPane();
            textArea1 = new JTextArea();
            scrollPane2 = new JScrollPane();
            memberEditorPane = new JEditorPane();

            //======== this ========



            //======== scrollPane1 ========
            {
                scrollPane1.setBorder(BorderFactory.createEmptyBorder());

                //---- editorPane2 ----
                chatEditorPane.setBorder(null);
                scrollPane1.setViewportView(chatEditorPane);
            }

            //======== scrollPane2 ========
            {
                scrollPane2.setBorder(new EmptyBorder(5, 5, 5, 5));
                scrollPane2.setViewportView(memberEditorPane);
            }

            GroupLayout layout = new GroupLayout(this);
            setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup()
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, 1037, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 1037, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup()
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup()
                                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 638, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scrollPane2))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textArea1, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                    .addContainerGap())
            );
        }

        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        // Generated using JFormDesigner Evaluation license - unknown
        private JScrollPane scrollPane1;
        private JEditorPane chatEditorPane;
        private JTextArea textArea1;
        private JScrollPane scrollPane2;
        private JEditorPane memberEditorPane;
        // JFormDesigner - End of variables declaration  //GEN-END:variables
    }
}
