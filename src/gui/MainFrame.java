package gui;

import lib.Manager;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JButton newPhotoButton;
    private JButton telescopesButton;
    private JButton camerasButton;
    private JButton settingsButton;
    private JPanel panel;
    private JButton filtersButton;
    private JButton lensesButton;

    public static Manager manager;

    public MainFrame() {
        setContentPane(panel);
        setTitle("Astrophoto Manager");
        setSize(600, 400);
        setLocation(200, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        manager = new Manager();

        camerasButton.addActionListener(e -> new CamerasFrame());
        telescopesButton.addActionListener(e -> new TelescopesFrame());
        filtersButton.addActionListener(e -> new FiltersFrame());
        lensesButton.addActionListener(e -> new LensesFrame());
    }
}
