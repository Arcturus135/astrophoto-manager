package gui;

import lib.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateSessionFrame extends CustomFrame {
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

    private Astrophoto astrophoto;

    public CreateSessionFrame(Astrophoto astrophoto) {
        this.astrophoto = astrophoto;

        setContentPane(panel);
        setTitle("Create Session");
        setSize(480, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(920, 210);

        Frames.frames.add(this);

        setup();
        block();

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Camera camera = null;
                if (comboBoxCamera.getSelectedIndex() != 0)
                    camera = Manager.cameras.get(comboBoxCamera.getSelectedIndex()-1);
                Telescope telescope = null;
                if (comboBoxTelescope.getSelectedIndex() != 0)
                    telescope = Manager.telescopes.get(comboBoxTelescope.getSelectedIndex()-1);
                Filter filter = null;
                if (comboBoxFilter.getSelectedIndex() != 0)
                    filter = Manager.filters.get(comboBoxFilter.getSelectedIndex()-1);
                Lens lens = null;
                if (comboBoxLens.getSelectedIndex() != 0)
                    lens = Manager.lenses.get(comboBoxLens.getSelectedIndex()-1);


                Session session = new Session(textFieldName.getText(), getDate(), camera, filter, telescope, lens,
                        Double.parseDouble(textFieldTemp.getText()), Double.parseDouble(textFieldExposure.getText()),
                        Integer.parseInt(textFieldNumber.getText()), Integer.parseInt(textFieldGain.getText()));
                Manager.sessions.add(session);
                Manager.saveSessions();
                astrophoto.addSession(session);
                Manager.saveAstrophotos();
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
        for (Telescope telescope : Manager.telescopes) comboBoxTelescope.addItem(telescope.getName());
        comboBoxCamera.addItem(" --- ");
        for (Camera camera : Manager.cameras) comboBoxCamera.addItem(camera.getName());
        comboBoxFilter.addItem(" --- ");
        for (Filter filter : Manager.filters) comboBoxFilter.addItem(filter.getName());
        comboBoxLens.addItem(" --- ");
        for (Lens lens : Manager.lenses) comboBoxLens.addItem(lens.getName());
    }

    private LocalDate getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(textFieldDate.getText(), formatter);
    }

    private void block() {
        if (astrophoto.getCamera() != null) {
            comboBoxCamera.setSelectedIndex(Manager.cameras.indexOf(astrophoto.getCamera()) + 1);
            comboBoxCamera.setEnabled(false);
        }
        if (astrophoto.getTelescope() != null) {
            comboBoxTelescope.setSelectedIndex(Manager.telescopes.indexOf(astrophoto.getTelescope()) + 1);
            comboBoxTelescope.setEnabled(false);
        }
        if (astrophoto.getLens() != null) {
            comboBoxLens.setSelectedIndex(Manager.lenses.indexOf(astrophoto.getLens()) + 1);
            comboBoxLens.setEnabled(false);
        }
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

    @Override
    public void update() {

    }
}
