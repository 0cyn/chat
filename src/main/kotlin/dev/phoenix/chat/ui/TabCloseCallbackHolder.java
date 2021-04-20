package dev.phoenix.chat.ui;

import javax.swing.*;
import java.util.function.BiConsumer;


/** 
 * We have to have this file because FlatLAF expects either an IntConsumer
 *  or a BiConsumer, and I can't seem to find a way to create that in Kotlin.
*/

public class TabCloseCallbackHolder
{
    public static BiConsumer<JTabbedPane, Integer> consumer =
            (tabbedPane, tabIndex) -> {
        String title = tabbedPane.getTitleAt(tabIndex);
        tabbedPane.remove(tabIndex);
        WindowLayoutCoordinator.INSTANCE.getFrame().getTabsByName().remove(title);
    };
}
