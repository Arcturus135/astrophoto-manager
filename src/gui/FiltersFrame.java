package gui;

import lib.Filter;
import lib.Manager;

import javax.swing.*;
import java.util.Objects;

public class FiltersFrame extends JFrame {
    private JList listFilters;
    private JTextField textFieldName;
    private JComboBox comboBoxConnection;
    private JButton newFilterButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JPanel panel;

    public FiltersFrame() {
        setContentPane(panel);
        setTitle("Filters");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        setupList();

        newFilterButton.addActionListener(e -> new CreateFilterFrame());

        deleteButton.addActionListener(e -> {
            Filter filter = Manager.filters.get(listFilters.getSelectedIndex());
            Manager.filters.remove(filter);
            Manager.saveFilters();
            dispose();
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Filter filter = Manager.filters.get(listFilters.getSelectedIndex());
                filter.setName(textFieldName.getText());
                filter.setConnection(Double.parseDouble(Objects.requireNonNull(comboBoxConnection.getSelectedItem())
                        .toString().replace("''", "")));
                Manager.saveFilters();
                new FiltersFrame();
                dispose();
            } else JOptionPane.showConfirmDialog(FiltersFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupList() {
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        String[] array = new String[Manager.filters.toArray().length];
        for (int i=0;i<Manager.filters.toArray().length;i++) {
            array[i] = ((Filter) Manager.filters.toArray()[i]).getName();
        }
        listFilters.setListData(array);

        listFilters.addListSelectionListener(e -> {
            Filter filter = Manager.filters.get(listFilters.getSelectedIndex());
            textFieldName.setText(filter.getName());
            if (filter.getConnection() == 1.25) comboBoxConnection.setSelectedIndex(0);
            else comboBoxConnection.setSelectedIndex(1);

            deleteButton.setVisible(true);
            saveButton.setVisible(true);
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
