package com.alcideswenner.jenvwindows.helpers;

import java.io.*;
import java.util.*;

public class CacheMemoryHelper {

    private static CacheMemoryHelper instance;

    public static CacheMemoryHelper getInstance() {
        if (instance == null) {
            synchronized (CacheMemoryHelper.class) {
                if (instance == null) {
                    instance = new CacheMemoryHelper();
                }
            }
        }
        return instance;
    }

    public void addCacheJdk(final String versionJdk, final String pathJdk) {
        final String conteudo = "jdk" + versionJdk + " # " + pathJdk.replace(",", " ");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\base.txt", true))) {
            writer.write(conteudo);
            writer.newLine();
            System.out.println("Salvo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar a decisão: " + e.getMessage());
        }
    }

    public List<String> readCacheFile() {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\base.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.replace("#", ""));
            }
            System.out.println("Arquivo de cache lido com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de cache: " + e.getMessage());
        }
        return list;
    }

    public String getPathByJdk(final String jdk) {
        return readCacheFile()
                .stream()
                .filter(j -> j.contains(jdk))
                .findFirst()
                .map(e -> {
                    int firstSpaceIndex = e.indexOf(" ");
                    System.out.println(firstSpaceIndex);
                    if (firstSpaceIndex >= 0) {
                        return e.substring(0, firstSpaceIndex) + e.substring(firstSpaceIndex + 1);
                    } else {
                        return e;
                    }
                })
                .map(e -> e.trim().replaceFirst(" ", ""))
                .map(e -> e.replaceFirst(jdk, ""))
                .map(e -> e.replaceFirst("jdk", ""))
                .orElse("");
    }

}
