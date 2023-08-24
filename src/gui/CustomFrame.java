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
}
