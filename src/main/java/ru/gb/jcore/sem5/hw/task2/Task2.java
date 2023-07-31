/*
    Задание 2.
    Предположить, что числа в исходном массиве из 9 элементов имеют диапазон [0, 3],
    и представляют собой, например, состояния ячеек поля для игры в крестики-нолики, где:
    - 0 – это пустое поле,
    - 1 – это поле с крестиком,
    - 2 – это поле с ноликом,
    - 3 – резервное значение.
    Такое предположение позволит хранить в одном числе типа int всё поле 3х3.
    Реализовать функционал с записью в файл и обратно игрового поля.
    Выводить в консоль игровое поле после импорта, заменяя числа символами X, O, •(пусто)
 */
package ru.gb.jcore.sem5.hw.task2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Task2 {
    final static String[] playSymbols = new String[]{".", "X", "O", "R"};
    final static String workDir = "src/main/resources";

    public static void main(String[] args) {
        int[] playfield = playFieldInit();
        System.out.println("1. Исходный код игрового поля:");
        System.out.println(Arrays.toString(playfield));

        // Запись в файл:
        System.out.println();
        String filename = "playfield.txt";
        File file = new File(workDir + "/" + filename);
        System.out.print("2. Запись игрового поля в файл " + file.getPath() + " ... ");
        writeToFile(playfield, file);
        System.out.println("Done");

        // Считывание из файла
        System.out.println();
        System.out.print("3. Чтение игрового поля из файла " + file.getPath() + " ... ");
        int[] importedPlayfield = readFromFile(file);
        System.out.println("Done.");

        // Конвертация игрового поля в 2D массив и вывод в консоль
        System.out.println();
        System.out.println("4. Вывод игрового поля в консоль:");
        displayPlayfield2D(convertToPlayfield2D(importedPlayfield), true);
        System.out.println();
        System.out.println("Done.");

    }

    /**
     * Создает массив кода игрового поля длиной 9 символов (3x3)
     * @return возвращает массив int
     */
    public static int[] playFieldInit() {
        int size = 9;
        int[] playfield = new int[size];
        Random rnd = new Random();
        int upper = 3;  // 0 - пусто, 1 - X, 2 - O
        for (int i = 0; i < size; i++) {
            playfield[i] = rnd.nextInt(upper);
        }
        return playfield;
    }

    /**
     * Конвертирует массив игрового поля в 2D массив
     * @param playfield массив int
     * @return воззвращает 2D массив
     */
    public static int[][] convertToPlayfield2D(int[] playfield) {
        int array2dSize = 3;
        int[][] playfield2D = new int[array2dSize][array2dSize];
        int index = 0;
        for (int i = 0; i < playfield2D.length; i++) {
            for (int j = 0; j < playfield2D[i].length; j++) {
                playfield2D[i][j] = playfield[index++];
            }
        }
        return playfield2D;
    }

    /**
     * Конвертирует 2D массив игрового поля в массив int
     * @param playfield2D конвертируемый 2D массив int
     * @return возвращает массив int
     */
    public static int[] convert2DtoPlayfield(int[][] playfield2D) {
        int arraySize = 9;
        int[] playfield = new int[arraySize];
        int index = 0;
        for (int i = 0; i < playfield2D.length; i++) {
            for (int j = 0; j < playfield2D[i].length; j++) {
                playfield[index++] = playfield2D[i][j];
            }
        }
        return playfield;
    }

    /**
     * Выводит в консоль игровое поле в виде символов или в виде чисел
     * @param playfield2D игровое поле в формате 2D массива int
     * @param symbolsOn true | false, выводит значения в виде символов или в виде цифр
     */
    public static void displayPlayfield2D(int[][] playfield2D, boolean symbolsOn) {
        for (int i = 0; i < playfield2D.length; i++) {
            for (int j = 0; j < playfield2D[i].length; j++) {
                System.out.printf("%s",
                        (symbolsOn) ? playSymbols[playfield2D[i][j]] : String.valueOf(playfield2D[i][j])
                );
            }
            System.out.println();
        }
    }

    /**
     * Записывает игровое поле в формате int[] в файл
     * @param array игровое поле в формате int[]
     * @param file файл, в который записывается массив
     */
    public static void writeToFile(int[] array, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (int num : array) {
                writer.write(String.valueOf(num));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Чтение из файла игрового поля
     * @param file файл, в котором хранится код игрового поля
     * @return возвращает массив int кода игрового поля
     */
    public static int[] readFromFile(File file){
        int[] array = null;
        try (FileReader reader = new FileReader(file);
             Scanner scanner = new Scanner(reader);
        ) {
            String line = scanner.nextLine();

            array = new int[line.length()];
            for (int i = 0; i < array.length; i++) {
                array[i] = Integer.parseInt(String.valueOf(line.toCharArray()[i]));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return array;


    }
}
