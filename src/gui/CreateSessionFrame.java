package gui;

import lib.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateSessionFrame extends JFrame {
    private JTextField textFieldName;
    private JComboBox comboBoxTelescope;
    private JComboBox comboBoxCamera;
    private JComboBox comboBoxFilter;
    private JComboBox comboBoxLens;
    private JTextField textFieldTemp;
    private JTextField textFieldExposure;
    private JTextField textFieldNumber;
    private JTextField textFieldGain;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel panel;
    private JTextField textFieldDate;

    public CreateSessionFrame() {
        setContentPane(panel);
        setTitle("Create Session");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        setup();

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Camera camera = null;
                if (comboBoxCamera.getSelectedIndex() != 0)
                    camera = MainFrame.manager.cameras.get(comboBoxCamera.getSelectedIndex()-1);
                Telescope telescope = null;
                if (comboBoxTelescope.getSelectedIndex() != 0)
                    telescope = MainFrame.manager.telescopes.get(comboBoxTelescope.getSelectedIndex()-1);
                Filter filter = null;
                if (comboBoxFilter.getSelectedIndex() != 0)
                    filter = MainFrame.manager.filters.get(comboBoxFilter.getSelectedIndex()-1);
                Lens lens = null;
                if (comboBoxLens.getSelectedIndex() != 0)
                    lens = MainFrame.manager.lenses.get(comboBoxLens.getSelectedIndex()-1);


                Session session = new Session(textFieldName.getText(), getDate(), camera, filter, telescope, lens,
                        Double.parseDouble(textFieldTemp.getText()), Double.parseDouble(textFieldExposure.getText()),
                        Integer.parseInt(textFieldNumber.getText()), Integer.parseInt(textFieldGain.getText()));
                MainFrame.manager.sessions.add(session);
                MainFrame.manager.saveSessions();
                dispose();
            } else {
                JOptionPane.showConfirmDialog(CreateSessionFrame.this,
                        "At least one of your input values does not fit. Please try to correct them.",
                        "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void setup() {
        comboBoxTelescope.addItem(" --- ");
        for (Telescope telescope : MainFrame.manager.telescopes) comboBoxTelescope.addItem(telescope.getName());
        comboBoxCamera.addItem(" --- ");
        for (Camera camera : MainFrame.manager.cameras) comboBoxCamera.addItem(camera.getName());
        comboBoxFilter.addItem(" --- ");
        for (Filter filter : MainFrame.manager.filters) comboBoxFilter.addItem(filter.getName());
        comboBoxLens.addItem(" --- ");
        for (Lens lens : MainFrame.manager.lenses) comboBoxLens.addItem(lens.getName());
    }

    private LocalDate getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(textFieldDate.getText(), formatter);
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
            if (textFieldExposure.getText().equalsIgnoreCase("")) return false;
            if (textFieldGain.getText().equalsIgnoreCase("")) return false;
            if (textFieldNumber.getText().equalsIgnoreCase("")) return false;
            if (textFieldTemp.getText().equalsIgnoreCase("")) return false;
            if (textFieldDate.getText().equalsIgnoreCase("")) return false;
            Double.parseDouble(textFieldExposure.getText());
            Double.parseDouble(textFieldTemp.getText());
            Integer.parseInt(textFieldNumber.getText());
            Integer.parseInt(textFieldGain.getText());
            getDate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
