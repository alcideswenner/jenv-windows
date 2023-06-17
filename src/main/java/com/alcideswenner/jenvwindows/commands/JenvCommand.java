package com.alcideswenner.jenvwindows.commands;

import org.springframework.shell.standard.*;
import com.alcideswenner.jenvwindows.helpers.CacheMemoryHelper;
import com.alcideswenner.jenvwindows.helpers.CommandExecutorHelper;

@ShellComponent
public class JenvCommand {

    @ShellMethod("Exibe uma mensagem de boas vindas")
    public String hello() {
        return "Ola, seja bem vindo!";
    }

    @ShellMethod(key = "jenv", value = "Exibe a versão do java", prefix = "get")
    public String version() {
        return System.getProperty("java.version");
    }

    @ShellMethod(key = "jenv change", value = "Altera versão Jdk")
    public void changeJdk(@ShellOption String jdk) {
        final String path = CacheMemoryHelper.getInstance().getPathByJdk(jdk);
        String command = "cmd /c setx JAVA_HOME \"" + path + "\"";
        System.out.println(command);
        CommandExecutorHelper.execute(command);

        CommandExecutorHelper.addPathEnvJdk(path.concat("\\").concat("bin"));
    }

    @ShellMethod(key = "jenv add", value = "Add versão Jdk")
    public void addJdk(@ShellOption String jdk, @ShellOption String path) {
        CacheMemoryHelper.getInstance().addCacheJdk(jdk, path);
    }

    @ShellMethod(key = "jenv list", value = "Lista versões Jdk salvas")
    public void listJdks() {
        CacheMemoryHelper.getInstance().readCacheFile().stream().forEach(System.out::println);
        System.out.println("#############PATH#############");
        CommandExecutorHelper.getListEnvPath();
    }

}
