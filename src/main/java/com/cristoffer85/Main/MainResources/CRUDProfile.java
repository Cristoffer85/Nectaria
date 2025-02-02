package com.cristoffer85.Main.MainResources;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CRUDProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private int playerX;
    private int playerY;

    public CRUDProfile(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    // CRUD operations for profiles
    public static void createProfile(String profileName) {
        String filePath = "profiles/" + profileName + ".dat";
        try {
            Files.createFile(Paths.get(filePath));
            System.out.println("Profile created: " + profileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getCurrentProfiles() {
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
}