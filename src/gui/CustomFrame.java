package gui;

import javax.swing.*;

public abstract class CustomFrame extends JFrame {

    public abstract void update();

    @Override
    public void dispose() {
        Frames.frames.remove(this);
        Frames.update();
        super.dispose();
    }

    public void confirmDialog(Runnable runnable) {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure?", "Warning",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) runnable.run();
    }
}
