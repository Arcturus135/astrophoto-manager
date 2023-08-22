package gui;

import lib.Telescope;

import javax.swing.*;

public class CreateTelescopeFrame extends JFrame {
    private JTextField textFieldName;
    private JTextField textFieldAperture;
    private JTextField textFieldFocalLength;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel panel;

    public CreateTelescopeFrame() {
        setContentPane(panel);
        setTitle("Create Telescope");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Telescope telescope = new Telescope(textFieldName.getText(),
                        Integer.parseInt(textFieldAperture.getText()),
                        Integer.parseInt(textFieldFocalLength.getText()));
                MainFrame.manager.telescopes.add(telescope);
                MainFrame.manager.saveTelescopes();
                dispose();
            } else {
                JOptionPane.showConfirmDialog(CreateTelescopeFrame.this,
                        "At least one of your input values does not fit. Please try to correct them.",
                        "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
            if (textFieldAperture.getText().equalsIgnoreCase("")) return false;
            if (textFieldFocalLength.getText().equalsIgnoreCase("")) return false;
            Integer.parseInt(textFieldAperture.getText());
            Integer.parseInt(textFieldFocalLength.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
