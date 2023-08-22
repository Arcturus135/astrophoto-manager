package gui;

import lib.Lens;

import javax.swing.*;
import java.util.Objects;

public class LensesFrame extends JFrame {
    private JPanel panel;
    private JList listLenses;
    private JTextField textFieldName;
    private JTextField textFieldFactor;
    private JComboBox comboBoxConnection;
    private JButton newLensButton;
    private JButton deleteButton;
    private JButton saveButton;

    public LensesFrame() {
        setContentPane(panel);
        setTitle("Lenses");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        setupList();

        newLensButton.addActionListener(e -> new CreateLensFrame());

        deleteButton.addActionListener(e -> {
            Lens lens = MainFrame.manager.lenses.get(listLenses.getSelectedIndex());
            MainFrame.manager.lenses.remove(lens);
            MainFrame.manager.saveLenses();
            dispose();
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Lens lens = MainFrame.manager.lenses.get(listLenses.getSelectedIndex());
                lens.setName(textFieldName.getText());
                lens.setFactor(Double.parseDouble(textFieldFactor.getText()));
                lens.setConnection(Double.parseDouble(Objects.requireNonNull(comboBoxConnection.getSelectedItem())
                        .toString().replace("''", "")));
                MainFrame.manager.saveLenses();
                new LensesFrame();
                dispose();
            } else JOptionPane.showConfirmDialog(LensesFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupList() {
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        String[] array = new String[MainFrame.manager.lenses.toArray().length];
        for (int i=0;i<MainFrame.manager.lenses.toArray().length;i++) {
            array[i] = ((Lens) MainFrame.manager.lenses.toArray()[i]).getName();
        }
        listLenses.setListData(array);

        listLenses.addListSelectionListener(e -> {
            Lens lens = MainFrame.manager.lenses.get(listLenses.getSelectedIndex());
            textFieldName.setText(lens.getName());
            textFieldFactor.setText(lens.getFactor() + "");
            if (lens.getConnection() == 1.25) comboBoxConnection.setSelectedIndex(0);
            else comboBoxConnection.setSelectedIndex(1);

            deleteButton.setVisible(true);
            saveButton.setVisible(true);
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
}
