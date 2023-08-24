package gui;

import lib.Camera;
import lib.Manager;

import javax.swing.*;
import java.util.Objects;

public class CreateCameraFrame extends CustomFrame {
    private JCheckBox colorCheckBox;
    private JButton saveButton;
    private JTextField textFieldName;
    private JTextField textFieldMGP;
    private JTextField textFieldResX;
    private JTextField textFieldResY;
    private JComboBox<String> comboBoxConnection;
    private JButton cancelButton;
    private JPanel panel;

    public CreateCameraFrame() {
        setContentPane(panel);
        setTitle("Create Camera");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        Frames.frames.add(this);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Camera camera = new Camera(textFieldName.getText(), Integer.parseInt(textFieldMGP.getText()),
                        Integer.parseInt(textFieldResX.getText()), Integer.parseInt(textFieldResY.getText()),
                        Double.parseDouble(Objects.requireNonNull(comboBoxConnection.getSelectedItem()).toString().replace("''", "")),
                        colorCheckBox.isSelected());
                Manager.cameras.add(camera);
                Manager.saveCameras();
                dispose();
            } else {
                JOptionPane.showConfirmDialog(CreateCameraFrame.this,
                        "At least one of your input values does not fit. Please try to correct them.",
                        "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
            if (textFieldMGP.getText().equalsIgnoreCase("")) return false;
            if (textFieldResX.getText().equalsIgnoreCase("")) return false;
            if (textFieldResY.getText().equalsIgnoreCase("")) return false;
            Integer.parseInt(textFieldMGP.getText());
            Integer.parseInt(textFieldResX.getText());
            Integer.parseInt(textFieldResY.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {

    }
}
