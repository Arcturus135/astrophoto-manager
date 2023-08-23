package gui;

import lib.Filter;
import lib.Manager;

import javax.swing.*;
import java.util.Objects;

public class CreateFilterFrame extends JFrame {
    private JTextField textFieldName;
    private JComboBox comboBoxConnection;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel panel;

    public CreateFilterFrame() {
        setContentPane(panel);
        setTitle("Create Filter");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Filter filter = new Filter(textFieldName.getText(),
                        Double.parseDouble(Objects.requireNonNull(comboBoxConnection.getSelectedItem())
                                .toString().replace("''", "")));
                Manager.filters.add(filter);
                Manager.saveFilters();
                dispose();
            } else {
                JOptionPane.showConfirmDialog(CreateFilterFrame.this,
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
}
