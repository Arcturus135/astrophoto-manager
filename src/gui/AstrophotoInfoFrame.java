package gui;

import lib.Astrophoto;
import lib.Filter;
import lib.Manager;
import lib.Session;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class AstrophotoInfoFrame extends JFrame {
    private JTextField textFieldName;
    private JTextField textFieldCamera;
    private JLabel lbl_date1;
    private JLabel lbl_date2;
    private JLabel lbl_temp;
    private JLabel lbl_exp;
    private JLabel lbl_gain;
    private JCheckBox finishedCheckBox;
    private JList listSessions;
    private JButton newSessionButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JButton imageButton;
    private JLabel lbl_url;
    private JTextField textFieldTelescope;
    private JTextField textFieldPrograms;
    private JTextField textFieldLens;
    private JList listFilters;
    private JButton saveButton;
    private JPanel panel;
    private JButton editButton;

    private Astrophoto astrophoto;

    public AstrophotoInfoFrame(Astrophoto astrophoto) {
        this.astrophoto = astrophoto;
        setContentPane(panel);
        setTitle("Astrophoto");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(810, 200);

        setUp();
        fillValues();

        newSessionButton.addActionListener(e -> new CreateSessionFrame(astrophoto));

        deleteButton.addActionListener(e -> {
            Session session = Manager.sessions.get(listSessions.getSelectedIndex());
            Manager.sessions.remove(session);
            Manager.saveSessions();
            dispose();
        });

        editButton.addActionListener(e -> {
            Session session = Manager.sessions.get(listSessions.getSelectedIndex());
            new SessionInfoFrame(session);
        });

        saveButton.addActionListener(e -> {
            if (checkInputs()) {
                astrophoto.setName(textFieldName.getText());
                astrophoto.setFinished(finishedCheckBox.isSelected());
                astrophoto.setPath_to_img(lbl_url.getText());
                astrophoto.setPrograms(textFieldPrograms.getText());
                Manager.saveAstrophotos();
                new AstrophotoInfoFrame(astrophoto);
                dispose();
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
                        Image img = ImageIO.read(file);
                        ImageIcon icon = new ImageIcon(img);
                        imageButton.setIcon(icon);                                                                  //RESIZE HERE
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void setUp() {
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        String[] array = new String[Manager.sessions.toArray().length];
        for (int i=0;i<Manager.sessions.toArray().length;i++) {
            array[i] = ((Session) Manager.sessions.toArray()[i]).getName();
        }
        listSessions.setListData(array);

        listSessions.addListSelectionListener(e -> {
            deleteButton.setEnabled(true);
            editButton.setEnabled(true);
        });

        try {
            Image img = ImageIO.read(new File(astrophoto.getPath_to_img()));
            ImageIcon icon = new ImageIcon(img);
            imageButton.setIcon(icon);                                                                           //RESIZE HERE
        } catch (Exception ex) {
            ex.printStackTrace();
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
