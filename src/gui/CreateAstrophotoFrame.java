package gui;

import lib.Astrophoto;
import lib.Manager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CreateAstrophotoFrame extends CustomFrame {
    private JTextField textFieldName;
    private JCheckBox finishedCheckBox;
    private JButton openImageButton;
    private JTextField textFieldPrograms;
    private JPanel panel;
    private JButton cancelButton;
    private JButton saveButton;
    private JLabel image_url;

    public CreateAstrophotoFrame() {
        setContentPane(panel);
        setTitle("Create Astrophoto");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        Frames.frames.add(this);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Astrophoto astrophoto = new Astrophoto(textFieldName.getText(), finishedCheckBox.isSelected(),
                        image_url.getText(), textFieldPrograms.getText());
                Manager.astrophotos.add(astrophoto);
                Manager.saveAstrophotos();
                dispose();
            } else {
                JOptionPane.showConfirmDialog(CreateAstrophotoFrame.this,
                        "At least one of your input values does not fit. Please try to correct them.",
                        "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {

    }
}
