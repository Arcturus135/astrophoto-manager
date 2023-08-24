package gui;

import lib.Lens;
import lib.Manager;

import javax.swing.*;
import java.util.Objects;

public class CreateLensFrame extends CustomFrame {
    private JPanel panel;
    private JTextField textFieldName;
    private JTextField textFieldFactor;
    private JComboBox<String> comboBoxConnection;
    private JButton cancelButton;
    private JButton saveButton;

    public CreateLensFrame() {
        setContentPane(panel);
        setTitle("Create Lens");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        Frames.frames.add(this);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Lens lens = new Lens(textFieldName.getText(), Double.parseDouble(textFieldFactor.getText()),
                        Double.parseDouble(Objects.requireNonNull(comboBoxConnection.getSelectedItem())
                                .toString().replace("''", "")));
                Manager.lenses.add(lens);
                Manager.saveLenses();
                dispose();
            } else {
                JOptionPane.showConfirmDialog(CreateLensFrame.this,
                        "At least one of your input values does not fit. Please try to correct them.",
                        "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
            if (textFieldFactor.getText().equalsIgnoreCase("")) return false;
            Double.parseDouble(textFieldFactor.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {

    }
}
