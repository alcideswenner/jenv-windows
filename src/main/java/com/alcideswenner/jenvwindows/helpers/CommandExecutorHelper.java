package com.alcideswenner.jenvwindows.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.validation.constraints.NotNull;

public class CommandExecutorHelper {

    public static void execute(@NotNull final String command) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getListClearLastPathJdk() {
        String currentPath = String.join(";", getListEnvPath());

        String[] pathEntries = currentPath.split(";");

        List<String> modifiedEntries = new ArrayList<>();

        for (String entry : pathEntries) {

            if (!entry.toLowerCase().startsWith("java-")
                    && !entry.toLowerCase().contains("openjdk")
                    && !entry.toLowerCase().contains("graalvm")) {
                modifiedEntries.add(entry);
            }
        }

        return modifiedEntries;
    }

    public static void addPathEnvJdk(String newPath) {
        String currentPath = String.join(";", getListClearLastPathJdk());

        System.out.println(newPath);
        String modifiedPath = "";

        if (currentPath.endsWith(";")) {
            modifiedPath = currentPath.concat(newPath);
        } else {
            modifiedPath = currentPath.concat(";").concat(newPath);
        }

        if (!modifiedPath.isBlank() || !modifiedPath.isEmpty()) {
            String command = "cmd /c setx PATH \"" + modifiedPath + "\"";
            execute(command);
            System.out.println(command);
        }

    }

    public static List<String> getListEnvPath() {
        List<String> list = new ArrayList<>();
        Map<String, String> map = System.getenv();
        String pathValue = map.get("Path");

        Set<String> uniquePaths = new LinkedHashSet<>(Arrays.asList(pathValue.split(";")));
        uniquePaths.forEach(System.out::println);
        list.addAll(uniquePaths);
        return list;
    }
}
