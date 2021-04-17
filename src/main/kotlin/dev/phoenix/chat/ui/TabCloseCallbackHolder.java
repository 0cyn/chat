package dev.phoenix.chat.ui;

import dev.phoenix.chat.window.WindowLayoutCoordinator;

import javax.swing.*;
import java.util.function.BiConsumer;

public class TabCloseCallbackHolder
{
    public static BiConsumer<JTabbedPane, Integer> consumer =
            (tabbedPane, tabIndex) -> {
        String title = tabbedPane.getTitleAt(tabIndex);
        tabbedPane.remove(tabIndex);
        WindowLayoutCoordinator.INSTANCE.getFrame().getTabsByName().remove(title);
    };
}
