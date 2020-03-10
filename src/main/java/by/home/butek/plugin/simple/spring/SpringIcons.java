package by.home.butek.plugin.simple.spring;

import javax.swing.*;

import static com.intellij.icons.AllIcons.Gutter.ReadAccess;
import static com.intellij.icons.AllIcons.Gutter.WriteAccess;

public enum SpringIcons {
    RECEIVER(WriteAccess), SENDER(ReadAccess);

    protected Icon icon;

    SpringIcons(Icon icon) {
        this.icon = icon;
    }
}
