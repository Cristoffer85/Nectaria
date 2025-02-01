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

public class InitialState extends JPanel {
    private Image logoImage;

    public InitialState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        setBackground(Color.ORANGE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale logo image
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and style "Create New Profile" button
        JButton newProfileButton = new JButton("CREATE NEW PROFILE");
        newProfileButton.addActionListener(e -> {
            String profileName = JOptionPane.showInputDialog("Enter profile name:");
            if (profileName != null && !profileName.trim().isEmpty()) {
                CRUDProfile.createProfile(profileName);
                gamePanel.setProfileName(profileName);
                gamePanel.resetGame();
                gamePanel.changeGameState(StatesDefinitions.GAME);
            }
        });
        styleRegularButton(newProfileButton);

        // Create and style "Select Existing Profile" button
        JButton existingProfileButton = new JButton("SELECT EXISTING PROFILE");
        existingProfileButton.addActionListener(e -> {
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
        });
        styleRegularButton(existingProfileButton);

        // #### Add components to grid ####
        // Empty above logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(Box.createVerticalStrut(20), gbc);

        // Add logo
        gbc.gridy = 1;
        add(new JLabel(new ImageIcon(logoImage)), gbc);

        // Empty space below logo
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(Box.createVerticalStrut(20), gbc);

        // Add buttons
        gbc.gridy = 3;
        add(newProfileButton, gbc);

        gbc.gridy = 4;
        add(existingProfileButton, gbc);
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

    private void styleRegularButton(JButton button) {
        try {
            Font retroFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Retro-pixelfont.ttf")).deriveFont(44f);
            button.setFont(retroFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        button.setForeground(Color.BLACK);
        button.setBackground(Color.ORANGE);
        button.setMargin(new Insets(5, 5, 1, 2));
    }
}