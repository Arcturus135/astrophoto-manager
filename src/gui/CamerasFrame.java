package gui;

import lib.Camera;
import lib.Manager;

import javax.swing.*;
import java.util.Objects;

public class CamerasFrame extends CustomFrame {
    private JList<String> listCameras;
    private JTextField textFieldName;
    private JTextField textFieldMGP;
    private JTextField textFieldResX;
    private JCheckBox colorCheckBox;
    private JButton newCameraButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JPanel panel;
    private JTextField textFieldResY;
    private JComboBox<String> comboBox1;

    public CamerasFrame() {
        setContentPane(panel);
        setTitle("Cameras");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        Frames.frames.add(this);

        setupList();

        newCameraButton.addActionListener(e -> new CreateCameraFrame());

        deleteButton.addActionListener(e -> {
            Camera camera = Manager.cameras.get(listCameras.getSelectedIndex());
            Manager.cameras.remove(camera);
            Manager.saveCameras();
            Frames.update();
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                Camera camera = Manager.cameras.get(listCameras.getSelectedIndex());
                camera.setName(textFieldName.getText());
                camera.setMgp(Integer.parseInt(textFieldMGP.getText()));
                camera.setResX(Integer.parseInt(textFieldResX.getText()));
                camera.setResY(Integer.parseInt(textFieldResY.getText()));
                camera.setConnection(Double.parseDouble(Objects.requireNonNull(comboBox1.getSelectedItem()).toString()
                        .replace("''", "")));
                camera.setColor(colorCheckBox.isSelected());
                Manager.saveCameras();
                Frames.update();
            } else JOptionPane.showConfirmDialog(CamerasFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });
    }

    private void setupList() {
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        String[] array = new String[Manager.cameras.toArray().length];
        for (int i=0;i<Manager.cameras.toArray().length;i++) {
            array[i] = ((Camera) Manager.cameras.toArray()[i]).getName();
        }
        listCameras.setListData(array);

        listCameras.addListSelectionListener(e -> {
            try {
                Camera camera = Manager.cameras.get(listCameras.getSelectedIndex());
                textFieldName.setText(camera.getName());
                textFieldMGP.setText(camera.getMgp() + "");
                textFieldResX.setText(camera.getResX() + "");
                textFieldResY.setText(camera.getResY() + "");
                if (camera.getConnection() == 1.25) comboBox1.setSelectedIndex(0);
                else comboBox1.setSelectedIndex(1);
                colorCheckBox.setSelected(camera.isColor());

                deleteButton.setVisible(true);
                saveButton.setVisible(true);
            } catch (IndexOutOfBoundsException exception) {
                textFieldName.setText("");
                textFieldMGP.setText("");
                textFieldResX.setText("");
                textFieldResY.setText("");
                comboBox1.setSelectedIndex(0);
                colorCheckBox.setSelected(false);
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

    @Override
    public void update() {
        setupList();
    }
}
