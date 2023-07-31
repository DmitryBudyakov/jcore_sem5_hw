/*
    Задание 1.
    Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий)
    во вновь созданную папку ./backup
 */
package ru.gb.jcore.sem5.hw.task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Task1 {
    public static void main(String[] args) {
        String workDirPath = "src/main/resources";
        File workDir = new File(workDirPath);
        workDirPath = workDir.getPath();
        File backupDir = new File(workDirPath + "/backup");
        String backupDirPath = backupDir.getPath();
        String title = "Копирование всех файлов из папки " + workDirPath + " в папку " + backupDirPath;
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < title.length(); i++) {
            sb.append("-");
        }
        System.out.println(sb.toString());
        System.out.println(title);
        System.out.println(sb.toString());

        // Создание директории backup в папке src/main/resources:
        System.out.println("1. Создание папки " + backupDirPath);
        createDir(backupDir);
        System.out.println("Done.");

        // Содержимое исходной папки
        System.out.println();
        System.out.println("2. Содержимое папки " + workDirPath);
        listOfFolder(workDir);
        System.out.println("Done.");

        // Содержимое папки backup до копирования
        System.out.println();
        System.out.println("3. Содержимое папки " + backupDirPath + " до копирования файлов");
        listOfFolder(backupDir);
        System.out.println("Done.");

        // Копирование файлов
        System.out.println();
        System.out.println("4. Копирование всех файлов из " + workDirPath + " в " + backupDirPath);
        File[] files = getListOfFolder(workDir);
        copyAllFiles(files, backupDirPath);
        System.out.println("Done.");

        // Просмотр содержимого папки backup после копирования
        System.out.println();
        System.out.println("5. Содержимое папки " + backupDirPath + " после копирования файлов");
        listOfFolder(backupDir);
        System.out.println("Done.");
    }

    /**
     * Создает папку
     * @param dirPathName папка в формате File
     */
    public static void createDir(File dirPathName) {
        String createResult = (dirPathName.mkdir()) ?
                "Dir " + dirPathName.getPath() + " was successfully created." :
                "Dir " + dirPathName.getPath() + " already exists.";
        System.out.println(createResult);
    }

    /**
     * Получение массива содержимого папки
     * @param dir папка
     * @return возвращает массив File
     */
    public static File[] getListOfFolder(File dir) {
        if (!dir.exists()) {
            System.out.println(dir.getPath() + " is not exists.");
            return null;
        }
        return dir.listFiles();
    }

    /**
     * Просмотр содержимого папки
     * @param dir папка
     */
    public static void listOfFolder(File dir) {
        if (!dir.exists()) {
            System.out.println("Folder " + dir.getPath() + " is not exists.");
            return;
        }

        File[] files = dir.listFiles();
        System.out.println("List of folder " + dir.getPath() + ":");
        StringBuilder sb = new StringBuilder("");
        if (files.length == 0) sb.append("Folder is empty.");
        else {
            for (File file : files) {
                sb.append(file.getPath());
                sb.append("\t");
                try {
                    String filesize = (file.isFile()) ? "\t" + Files.size(Paths.get(file.getPath())) + " Byte" : "dir";
                    sb.append(filesize);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(System.lineSeparator());
            }
        }
        System.out.print(sb.toString());
    }

    /**
     * Копирует все файлы из текущей папки в указанную папку
     * @param files массив файлов
     * @param dstDirPath путь копирования
     */
    public static void copyAllFiles(File[] files, String dstDirPath) {
        int count = 0;
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Path srcFilePath = Paths.get(file.getPath());
                    Path dstFilePath = Paths.get(dstDirPath + "/" + file.getName());
                    Files.copy(srcFilePath, dstFilePath, REPLACE_EXISTING);
                    count++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Copied " + count + " files.");
    }

}
