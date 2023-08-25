package gui;

import lib.Astrophoto;
import lib.Manager;
import lib.Session;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class AstrophotoInfoFrame extends CustomFrame {
    private JTextField textFieldName;
    private JTextField textFieldCamera;
    private JLabel lbl_date1;
    private JLabel lbl_date2;
    private JLabel lbl_temp;
    private JLabel lbl_exp;
    private JLabel lbl_gain;
    private JCheckBox finishedCheckBox;
    private JList<String> listSessions;
    private JButton newSessionButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JButton imageButton;
    private JLabel lbl_url;
    private JTextField textFieldTelescope;
    private JTextField textFieldPrograms;
    private JTextField textFieldLens;
    private JList<String> listFilters;
    private JButton saveButton;
    private JPanel panel;
    private JButton editButton;

    private final Astrophoto astrophoto;

    public AstrophotoInfoFrame(Astrophoto astrophoto) {
        this.astrophoto = astrophoto;
        setContentPane(panel);
        setTitle("Astrophoto");
        setSize(650, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        Frames.frames.add(this);

        setUp();
        fillValues();

        newSessionButton.addActionListener(e -> new CreateSessionFrame(astrophoto));

        cancelButton.addActionListener(e -> dispose());

        deleteButton.addActionListener(e -> {
            confirmDialog(() -> {
                try {
                    Session session = Manager.sessions.get(listSessions.getSelectedIndex());
                    astrophoto.removeSession(session);
                    Manager.sessions.remove(session);
                    Manager.saveSessions();
                    Frames.update();
                } catch (IndexOutOfBoundsException ignored) {}
            });
        });

        editButton.addActionListener(e -> {
            try {
                Session session = Manager.sessions.get(listSessions.getSelectedIndex());
                new SessionInfoFrame(session);
            } catch (IndexOutOfBoundsException ignored) {}
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                astrophoto.setName(textFieldName.getText());
                astrophoto.setFinished(finishedCheckBox.isSelected());
                astrophoto.setPath_to_img(lbl_url.getText());
                astrophoto.setPrograms(textFieldPrograms.getText());
                Manager.saveAstrophotos();
                Frames.update();
            } else JOptionPane.showConfirmDialog(AstrophotoInfoFrame.this,
                    "At least one of your input values does not fit. Please try to correct them.",
                    "Invalid values", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        });

        imageButton.setPreferredSize(new Dimension(150, 100));
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
            int option = fileChooser.showDialog(this, "Select");
            if (option == JFileChooser.APPROVE_OPTION) {
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                File file = new File(selectedFilePath);
                if (file.exists()) {
                    astrophoto.setPath_to_img(selectedFilePath);
                    Manager.saveAstrophotos();
                    System.out.println(astrophoto.toJSONObject().toString(2));
                    try {
                        Image img = ImageIO.read(new File(astrophoto.getPath_to_img()));
                        Image resizedImg = img.getScaledInstance(250, 170,  java.awt.Image.SCALE_SMOOTH) ;
                        ImageIcon icon = new ImageIcon(resizedImg);
                        imageButton.setIcon(icon);
                        imageButton.setText("");
                    } catch (Exception ignored) {
                    }
                }
            }
        });
    }

    private void setUp() {
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        String[] array = new String[astrophoto.getSessions().size()];
        for (int i=0;i<astrophoto.getSessions().size();i++) {
            array[i] = astrophoto.getSessions().get(i).getName();
        }
        listSessions.setListData(array);

        listSessions.addListSelectionListener(e -> {
            deleteButton.setEnabled(true);
            editButton.setEnabled(true);
        });

        finishedCheckBox.addActionListener(e -> imageButton.setEnabled(finishedCheckBox.isSelected()));

        try {
            Image img = ImageIO.read(new File(astrophoto.getPath_to_img()));
            Image resizedImg = img.getScaledInstance(250, 170,  Image.SCALE_DEFAULT) ;
            ImageIcon icon = new ImageIcon(resizedImg);
            imageButton.setIcon(icon);
            imageButton.setText("");
        } catch (Exception ignored) {
        }
    }

    private void fillValues() {
        astrophoto.update();

        textFieldName.setText(astrophoto.getName());
        lbl_date1.setText(astrophoto.getDateAsString(astrophoto.getDateStart()));
        lbl_date2.setText(astrophoto.getDateAsString(astrophoto.getDateEnd()));
        lbl_temp.setText(Arrays.toString(astrophoto.getTempsWithUnit().toArray())
                .replace("[", "").replace("]", ""));
        lbl_exp.setText(astrophoto.getExposureSimple());
        lbl_gain.setText(Arrays.toString(astrophoto.getGains().toArray())
                .replace("[", "").replace("]", ""));
        finishedCheckBox.setSelected(astrophoto.isFinished());
        textFieldPrograms.setText(astrophoto.getPrograms());
        if (astrophoto.getCamera() != null) textFieldCamera.setText(astrophoto.getCamera().getName());
        if (astrophoto.getTelescope() != null) textFieldTelescope.setText(astrophoto.getTelescope().getName());
        if (astrophoto.getLens() != null) textFieldLens.setText(astrophoto.getLens().getName());

        String[] array = new String[astrophoto.getFilters().size()];
        for (int i=0;i<astrophoto.getFilters().size();i++) {
            array[i] = astrophoto.getFilters().get(i).getName();
        }
        listFilters.setListData(array);

        lbl_url.setText(astrophoto.getPath_to_img());

        imageButton.setEnabled(finishedCheckBox.isSelected());
    }

    public boolean checkInputs() {
        try {
            if (textFieldName.getText().equalsIgnoreCase("")) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        setUp();
        fillValues();
    }
}
