package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.Main.MainResources.CRUDProfile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;

public class InitialState extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.ORANGE;
    private static final int MIDDLE_PANEL_OFFSET = 36; // Offset to match MainMenuState, currently the (non-present here) topPanels height in MainMenuState is 36
    private static final int BOTTOM_PANEL_OFFSET = 280;
   
    private static final Color BUTTON_COLOR = Color.ORANGE;
    private final Font MENU_BUTTON_FONTANDSIZE = loadFont("/Retro-pixelfont.ttf", 44f);
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12); // Have backup font in case of loadfont failure
    private static final int MENUBUTTON_VERTICAL_SPACING = 20;

    private Image logoImage;

    public InitialState(GamePanel gamePanel) {
        setLayout(new BorderLayout());

        // Uses borderlayout to place the panels in the correct position on the screen
        add(middlePanel(), BorderLayout.CENTER);
        add(bottomPanel(gamePanel), BorderLayout.SOUTH);
    }

    private JPanel middlePanel() {
        JPanel logoPanel = createSimpleVerticalPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.add(Box.createVerticalStrut(MIDDLE_PANEL_OFFSET));
        addLogo(logoPanel);
        return logoPanel;
    }

    private JPanel bottomPanel(GamePanel gamePanel) {
        JPanel buttonPanel = createSimpleVerticalPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(menuButton("CREATE NEW PROFILE", e -> createNewProfile(gamePanel)));
        buttonPanel.add(Box.createVerticalStrut(MENUBUTTON_VERTICAL_SPACING));
        buttonPanel.add(menuButton("SELECT EXISTING PROFILE", e -> selectExistingProfile(gamePanel)));
        buttonPanel.add(Box.createVerticalStrut(BOTTOM_PANEL_OFFSET));

        return buttonPanel;
    }

    // Helper methods
    private void createNewProfile(GamePanel gamePanel) {
        String profileName = JOptionPane.showInputDialog("Enter profile name:");
        if (profileName != null && !profileName.trim().isEmpty()) {
            CRUDProfile.createProfile(profileName);
            gamePanel.setProfileName(profileName);
            gamePanel.resetGame();
            gamePanel.changeGameState(StatesDefinitions.GAME);
        }
    }

    private void selectExistingProfile(GamePanel gamePanel) {
        String[] profiles = getCurrentProfiles();
        if (profiles.length > 0) {
            String selectedProfile = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a profile:",
                    "Select Profile",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    profiles,
                    profiles[0]
            );
            if (selectedProfile != null) {
                gamePanel.setProfileName(selectedProfile);
                gamePanel.loadGame();
                gamePanel.changeGameState(StatesDefinitions.MAIN_MENU);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No profiles found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] getCurrentProfiles() {
        File profilesDir = new File("profiles");
        if (!profilesDir.exists() || !profilesDir.isDirectory()) {
            return new String[0];
        }
        File[] profileFiles = profilesDir.listFiles((dir, name) -> name.endsWith(".dat"));
        if (profileFiles == null) {
            return new String[0];
        }
        List<String> profiles = new ArrayList<>();
        for (File file : profileFiles) {
            profiles.add(file.getName().replace(".dat", ""));
        }
        return profiles.toArray(new String[0]);
    }

    private Image scaleImage(Image image, double scale) {
        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void addLogo(JPanel logoPanel) {
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoPanel.add(Box.createVerticalStrut(100));
            logoPanel.add(logoLabel);
            logoPanel.add(Box.createVerticalGlue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton menuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(MENU_BUTTON_FONTANDSIZE);
        button.setForeground(Color.BLACK);
        button.setBackground(BUTTON_COLOR);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(5, 5, 1, 2));
        return button;
    }

    private JPanel createSimpleVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    private Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return DEFAULT_FONT;
        }
    }
}