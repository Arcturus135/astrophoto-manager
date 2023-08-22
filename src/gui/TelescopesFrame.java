package gui;

import lib.Telescope;

import javax.swing.*;

public class TelescopesFrame extends JFrame {
    private JPanel panel;
    private JList listTelescopes;
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

        setupList();

        newTelescopeButton.addActionListener(e -> new CreateTelescopeFrame());

        deleteButton.addActionListener(e -> {
            Telescope telescope = MainFrame.manager.telescopes.get(listTelescopes.getSelectedIndex());
            MainFrame.manager.telescopes.remove(telescope);
            MainFrame.manager.saveTelescopes();
            dispose();
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Telescope telescope = MainFrame.manager.telescopes.get(listTelescopes.getSelectedIndex());
                telescope.setName(textFieldName.getText());
                telescope.setAperture(Integer.parseInt(textFieldAperture.getText()));
                telescope.setFocal_length(Integer.parseInt(textFieldFocalLength.getText()));
                MainFrame.manager.saveTelescopes();
                new TelescopesFrame();
                dispose();
            } else JOptionPane.showConfirmDialog(TelescopesFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupList() {
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        String[] array = new String[MainFrame.manager.telescopes.toArray().length];
        for (int i=0;i<MainFrame.manager.telescopes.toArray().length;i++) {
            array[i] = ((Telescope) MainFrame.manager.telescopes.toArray()[i]).getName();
        }
        listTelescopes.setListData(array);

        listTelescopes.addListSelectionListener(e -> {
            Telescope telescope = MainFrame.manager.telescopes.get(listTelescopes.getSelectedIndex());
            textFieldName.setText(telescope.getName());
            textFieldAperture.setText(telescope.getAperture() + "");
            textFieldFocalLength.setText(telescope.getFocal_length() + "");

            deleteButton.setVisible(true);
            saveButton.setVisible(true);
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
