package gui;

import lib.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SessionInfoFrame extends CustomFrame {
    private JTextField textFieldName;
    private JTextField textFieldDate;
    private JComboBox comboBoxCamera;
    private JComboBox comboBoxTelescope;
    private JComboBox comboBoxLens;
    private JScrollPane filtersScrollPane;
    private JTextField textFieldTemperature;
    private JTextField textFieldExposure;
    private JTextField textFieldNumber;
    private JTextField textFieldGain;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel panel;
    private JComboBox comboBoxFilter;

    private Session session;

    public SessionInfoFrame(Session session) {
        this.session = session;
        setContentPane(panel);
        setTitle("Session");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        Frames.frames.add(this);

        setUp();
        fillValues();

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                session.setName(textFieldName.getText());
                session.setDate(getDate());

                Camera camera = null;
                if (comboBoxCamera.getSelectedIndex() != 0)
                    camera = Manager.cameras.get(comboBoxCamera.getSelectedIndex()-1);
                Telescope telescope = null;
                if (comboBoxTelescope.getSelectedIndex() != 0)
                    telescope = Manager.telescopes.get(comboBoxTelescope.getSelectedIndex()-1);
                Lens lens = null;
                if (comboBoxLens.getSelectedIndex() != 0)
                    lens = Manager.lenses.get(comboBoxLens.getSelectedIndex()-1);
                Filter filter = null;
                if (comboBoxFilter.getSelectedIndex() != 0)
                    filter = Manager.filters.get(comboBoxFilter.getSelectedIndex()-1);

                session.setCamera(camera);
                session.setTelescope(telescope);
                session.setLens(lens);
                session.setFilter(filter);
                session.setTemp(Double.parseDouble(textFieldTemperature.getText()));
                session.setExposure(Double.parseDouble(textFieldExposure.getText()));
                session.setNumber(Integer.parseInt(textFieldNumber.getText()));
                session.setGain(Integer.parseInt(textFieldGain.getText()));
                Manager.saveSessions();
                new SessionInfoFrame(session);
                dispose();
            } else JOptionPane.showConfirmDialog(SessionInfoFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setUp() {
        comboBoxTelescope.addItem(" --- ");
        for (Telescope telescope : Manager.telescopes) comboBoxTelescope.addItem(telescope.getName());
        comboBoxCamera.addItem(" --- ");
        for (Camera camera : Manager.cameras) comboBoxCamera.addItem(camera.getName());
        comboBoxFilter.addItem(" --- ");
        for (Filter filter : Manager.filters) comboBoxFilter.addItem(filter.getName());
        comboBoxLens.addItem(" --- ");
        for (Lens lens : Manager.lenses) comboBoxLens.addItem(lens.getName());
    }

    private void fillValues() {
        textFieldName.setText(session.getName());
        textFieldDate.setText(session.getDateAsString());
        comboBoxCamera.setSelectedIndex(session.getCamera() != null ? Manager.cameras.indexOf(session.getCamera())+1 : 0);
        comboBoxTelescope.setSelectedIndex(session.getTelescope() != null ? Manager.telescopes.indexOf(session.getTelescope())+1 : 0);
        comboBoxLens.setSelectedIndex(session.getLens() != null ? Manager.lenses.indexOf(session.getLens())+1 : 0);
        comboBoxFilter.setSelectedIndex(session.getFilter() != null ? Manager.filters.indexOf(session.getFilter())+1 : 0);
        textFieldTemperature.setText(session.getTemp() + "");
        textFieldExposure.setText(session.getExposure() + "");
        textFieldNumber.setText(session.getNumber() + "");
        textFieldGain.setText(session.getGain() + "");
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
            if (textFieldTemperature.getText().equalsIgnoreCase("")) return false;
            if (textFieldDate.getText().equalsIgnoreCase("")) return false;
            Double.parseDouble(textFieldExposure.getText());
            Double.parseDouble(textFieldTemperature.getText());
            Integer.parseInt(textFieldNumber.getText());
            Integer.parseInt(textFieldGain.getText());
            getDate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        fillValues();
    }
}
