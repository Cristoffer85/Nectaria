package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.Main.MainResources.SaveLoadReset;
import com.cristoffer85.States.StatesResources.StatesDefinitions;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InitialState extends JPanel {
    public InitialState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create and style the "Create New Profile" button
        JButton newProfileButton = new JButton("CREATE NEW PROFILE");
        newProfileButton.addActionListener(e -> {
            String profileName = JOptionPane.showInputDialog("Enter profile name:");
            if (profileName != null && !profileName.trim().isEmpty()) {
                SaveLoadReset.createProfile(profileName); // Create the profile file
                gamePanel.setProfileName(profileName); // Set the profile name in the GamePanel
                gamePanel.resetGame(); // Reset the game for a new profile
                gamePanel.setGameState(StatesDefinitions.GAME);
            }
        });
        styleButton(newProfileButton);

        // Create and style the "Select Existing Profile" button
        JButton existingProfileButton = new JButton("SELECT EXISTING PROFILE");
        existingProfileButton.addActionListener(e -> {
            String[] profiles = getProfiles();
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
                    gamePanel.setGameState(StatesDefinitions.MAIN_MENU); // Go to main menu state
                }
            } else {
                JOptionPane.showMessageDialog(this, "No profiles found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        styleButton(existingProfileButton);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Welcome to the Game!"), gbc);

        gbc.gridy = 1;
        add(newProfileButton, gbc);

        gbc.gridy = 2;
        add(existingProfileButton, gbc);
    }

    private void styleButton(JButton button) {
        button.setForeground(Color.BLACK);
        button.setBackground(Color.ORANGE);
        button.setMargin(new Insets(5, 5, 1, 2));
    }

    private String[] getProfiles() {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}