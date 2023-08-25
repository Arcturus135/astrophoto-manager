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

        setup();

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

        openImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            int option = fileChooser.showDialog(this, "Select");
            if (option == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                File file = new File(selectedFilePath);
                if (file.exists()) {
                    try {
                        Image img = ImageIO.read(new File(selectedFilePath));
                        Image resizedImg = img.getScaledInstance(250, 170,  java.awt.Image.SCALE_SMOOTH) ;
                        ImageIcon icon = new ImageIcon(resizedImg);
                        openImageButton.setIcon(icon);
                        openImageButton.setText("");
                    } catch (Exception ignored) {
                    }
                }
            }
        });
    }

    private void setup() {
        openImageButton.setVisible(finishedCheckBox.isSelected());
        finishedCheckBox.addActionListener(e -> openImageButton.setVisible(finishedCheckBox.isSelected()));
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
