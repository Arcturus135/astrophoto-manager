package gui;

import lib.Manager;
import lib.Telescope;

import javax.swing.*;

public class TelescopesFrame extends CustomFrame {
    private JPanel panel;
    private JList<String> listTelescopes;
    private JTextField textFieldName;
    private JTextField textFieldAperture;
    private JTextField textFieldFocalLength;
    private JButton newTelescopeButton;
    private JButton deleteButton;
    private JButton saveButton;

    public TelescopesFrame() {
        setContentPane(panel);
        setTitle("Telescopes");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        Frames.frames.add(this);

        setupList();

        newTelescopeButton.addActionListener(e -> new CreateTelescopeFrame());

        deleteButton.addActionListener(e -> {
            Telescope telescope = Manager.telescopes.get(listTelescopes.getSelectedIndex());
            Manager.telescopes.remove(telescope);
            Manager.saveTelescopes();
            Frames.update();
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Telescope telescope = Manager.telescopes.get(listTelescopes.getSelectedIndex());
                telescope.setName(textFieldName.getText());
                telescope.setAperture(Integer.parseInt(textFieldAperture.getText()));
                telescope.setFocal_length(Integer.parseInt(textFieldFocalLength.getText()));
                Manager.saveTelescopes();
                Frames.update();
            } else JOptionPane.showConfirmDialog(TelescopesFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupList() {
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        String[] array = new String[Manager.telescopes.toArray().length];
        for (int i=0;i<Manager.telescopes.toArray().length;i++) {
            array[i] = ((Telescope) Manager.telescopes.toArray()[i]).getName();
        }
        listTelescopes.setListData(array);

        listTelescopes.addListSelectionListener(e -> {
            try {
                Telescope telescope = Manager.telescopes.get(listTelescopes.getSelectedIndex());
                textFieldName.setText(telescope.getName());
                textFieldAperture.setText(telescope.getAperture() + "");
                textFieldFocalLength.setText(telescope.getFocal_length() + "");

                deleteButton.setVisible(true);
                saveButton.setVisible(true);
            } catch (IndexOutOfBoundsException exception) {
                textFieldName.setText("");
                textFieldAperture.setText("");
                textFieldFocalLength.setText("");
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

    @Override
    public void update() {
        setupList();
    }
}
