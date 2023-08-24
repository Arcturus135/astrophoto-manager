package gui;

import lib.Astrophoto;
import lib.Filter;
import lib.Manager;
import lib.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings({"unused"})
public class MainFrame extends CustomFrame {
    private JButton newPhotoButton;
    private JButton telescopesButton;
    private JButton camerasButton;
    private JButton settingsButton;
    private JPanel panel;
    private JButton filtersButton;
    private JButton lensesButton;
    private JList<String> listAstrophotos;
    private JButton deleteButton;
    private JButton editButton;
    private JButton exportJsonButton;
    private JScrollPane paneAstrophotos;

    public MainFrame() {
        setContentPane(panel);
        setTitle("Astrophoto Manager");
        setSize(600, 400);
        setLocation(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Manager.init();
        Frames.init();

        Frames.frames.add(this);

        newPhotoButton.addActionListener(e -> new CreateAstrophotoFrame());

        camerasButton.addActionListener(e -> new CamerasFrame());
        telescopesButton.addActionListener(e -> new TelescopesFrame());
        filtersButton.addActionListener(e -> new FiltersFrame());
        lensesButton.addActionListener(e -> new LensesFrame());

        deleteButton.addActionListener(e -> {
            Astrophoto astrophoto = Manager.astrophotos.get(listAstrophotos.getSelectedIndex());
            for (Session session : astrophoto.getSessions()) Manager.sessions.remove(session);
            Manager.astrophotos.remove(astrophoto);
            Manager.saveSessions();
            Manager.saveAstrophotos();
            Frames.update();
        });

        update();

        listAstrophotos.addListSelectionListener(e -> {
            deleteButton.setEnabled(listAstrophotos.getSelectedValue() != null);
            editButton.setEnabled(listAstrophotos.getSelectedValue() != null);
            exportJsonButton.setEnabled(listAstrophotos.getSelectedValue() != null);
        });

        editButton.addActionListener(e -> new AstrophotoInfoFrame(Manager.astrophotos.get(listAstrophotos.getSelectedIndex())));

        exportJsonButton.addActionListener(e -> {
            Astrophoto astrophoto = Manager.astrophotos.get(listAstrophotos.getSelectedIndex());
            JSONObject jsonObject = astrophoto.toJSONObject();
            jsonObject.put("camera", astrophoto.getCamera() != null ? astrophoto.getCamera().toJSONObject() : null);
            jsonObject.put("telescope", astrophoto.getTelescope() != null ? astrophoto.getTelescope().toJSONObject() : null);
            jsonObject.put("lens", astrophoto.getLens() != null ? astrophoto.getLens().toJSONObject() : null);
            jsonObject.put("sessions", getSessionsAsArray(astrophoto));
            jsonObject.put("filters", getFiltersAsArray(astrophoto));

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
            int option = fileChooser.showDialog(this, "Select");
            if (option == JFileChooser.APPROVE_OPTION) {
                String selectedDirectory = fileChooser.getSelectedFile().getAbsolutePath();
                File file = new File(selectedDirectory, astrophoto.getName() + ".json");
                if (file.exists()) {
                    int choice = JOptionPane.showConfirmDialog(this,
                            "This file exists already.\nDo you want to overwrite it?", "Warning",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (choice == 0) export(jsonObject.toString(2), file);
                } else export(jsonObject.toString(2), file);
            }
        });
    }

    private void export(String text, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JSONArray getFiltersAsArray(Astrophoto astrophoto) {
        JSONArray array = new JSONArray();
        for (Filter filter : astrophoto.getFilters()) array.put(filter.toJSONObject());
        return array;
    }

    private JSONArray getSessionsAsArray(Astrophoto astrophoto) {
        JSONArray array = new JSONArray();
        for (Session session : astrophoto.getSessions()) array.put(getSessionAsJSON(session));
        return array;
    }

    private JSONObject getSessionAsJSON(Session session) {
        JSONObject jsonObject = session.toJSONObject();
        jsonObject.put("camera", session.getCamera() != null ? session.getCamera().toJSONObject() : null);
        jsonObject.put("telescope", session.getTelescope() != null ? session.getTelescope().toJSONObject() : null);
        jsonObject.put("lens", session.getLens() != null ? session.getLens().toJSONObject() : null);
        jsonObject.put("filter", session.getFilter() != null ? session.getFilter().toJSONObject() : null);
        return jsonObject;
    }

    @Override
    public void update() {
        String[] array = new String[Manager.astrophotos.toArray().length];
        for (int i=0;i<Manager.astrophotos.toArray().length;i++) {
            array[i] = ((Astrophoto) Manager.astrophotos.toArray()[i]).getName();
        }
        listAstrophotos.setListData(array);

        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        exportJsonButton.setEnabled(false);
    }
}
