package gui;

import lib.Camera;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CamerasFrame extends JFrame {
    private JList listCameras;
    private JTextField textFieldName;
    private JTextField textFieldMGP;
    private JTextField textFieldResX;
    private JCheckBox colorCheckBox;
    private JButton newCameraButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JPanel panel;
    private JTextField textFieldResY;
    private JComboBox comboBox1;

    public CamerasFrame() {
        setContentPane(panel);
        setTitle("Cameras");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        setupList();

        newCameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateCameraFrame();
            }
        });

        deleteButton.addActionListener(e -> {
            Camera camera = MainFrame.manager.cameras.get(listCameras.getSelectedIndex());
            MainFrame.manager.cameras.remove(camera);
            MainFrame.manager.saveCameras();
            dispose();
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Camera camera = MainFrame.manager.cameras.get(listCameras.getSelectedIndex());
                camera.setName(textFieldName.getText());
                camera.setMgp(Integer.parseInt(textFieldMGP.getText()));
                camera.setResX(Integer.parseInt(textFieldResX.getText()));
                camera.setResY(Integer.parseInt(textFieldResY.getText()));
                camera.setConnection(Double.parseDouble(Objects.requireNonNull(comboBox1.getSelectedItem()).toString()
                        .replace("''", "")));
                camera.setColor(colorCheckBox.isSelected());
                MainFrame.manager.saveCameras();
                new CamerasFrame();
                dispose();
            } else JOptionPane.showConfirmDialog(CamerasFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupList() {
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        String[] array = new String[MainFrame.manager.cameras.toArray().length];
        for (int i=0;i<MainFrame.manager.cameras.toArray().length;i++) {
            array[i] = ((Camera) MainFrame.manager.cameras.toArray()[i]).getName();
        }
        listCameras.setListData(array);

        listCameras.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Camera camera = MainFrame.manager.cameras.get(listCameras.getSelectedIndex());
                textFieldName.setText(camera.getName());
                textFieldMGP.setText(camera.getMgp() + "");
                textFieldResX.setText(camera.getResX() + "");
                textFieldResY.setText(camera.getResY() + "");
                if (camera.getConnection() == 1.25) comboBox1.setSelectedIndex(0); else comboBox1.setSelectedIndex(1);
                colorCheckBox.setSelected(camera.isColor());

                deleteButton.setVisible(true);
                saveButton.setVisible(true);
            }
        });
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
            if (textFieldMGP.getText().equalsIgnoreCase("")) return false;
            if (textFieldResX.getText().equalsIgnoreCase("")) return false;
            if (textFieldResY.getText().equalsIgnoreCase("")) return false;
            Integer.parseInt(textFieldMGP.getText());
            Integer.parseInt(textFieldResX.getText());
            Integer.parseInt(textFieldResY.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
