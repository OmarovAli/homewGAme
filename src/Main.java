import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static String path = "C://Games";
    static String savegamesPath = "C://Games/savegames/";
    static String zipPath = "C://Games/savegames/save.zip";
    static StringBuilder sb = new StringBuilder();
    static String saveFilePath1 = "C://Games/savegames/save1.dat";
    static String saveFilePath2 = "C://Games/savegames/save2.dat";
    static String saveFilePath3 = "C://Games/savegames/save3.dat";

    public static void main(String[] args) {
        List<String> files = Arrays.asList("/src", "/rec", "/savegames", "/temp", "/src/main", "/src/test", "/res/drawbles", "/res/icons");
        for (String f : files) {
            createDir(path + f);
        }
        createFile(path + "/src/main/", "Main.java");
        createFile(path + "/src/main/", "Utils.java");
        createFile(path + "/temp/", "temp.txt");
        String[] list = {saveFilePath1, saveFilePath2, saveFilePath3};
        try (FileWriter writer = new FileWriter(path + "/temp/temp.txt", true)) {
            writer.write((String.valueOf(sb)));
            writer.append('\n');
            writer.append('!');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        GameProgress game1 = new GameProgress(10, 10, 10, 10);
        GameProgress game2 = new GameProgress(7, 7, 7, 7);
        GameProgress game3 = new GameProgress(5, 5, 5, 5);

            saveGame(savegamesPath + "save1.dat", game1);
            saveGame(savegamesPath + "save2.dat", game2);
            saveGame(savegamesPath + "save3.dat", game3);
        zipFiles(zipPath, list);
            delete(savegamesPath + "save1.dat");
            delete(savegamesPath + "save2.dat");
            delete(savegamesPath + "save3.dat");
    }

    public static void createDir(String dirPath) {
        File dir = new File(dirPath);
        String text = "Папка " + dir + " создана";
        if (dir.mkdir()) {
            sb.append(text + '\n');
        }
    }

    public static void createFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        String text = "Файл " + file + " создан";
        try {
            if (file.createNewFile())
                sb.append(text + '\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveGame(String savegamesPath, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(savegamesPath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, String... list)  {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
//            File dir = new File(savegamesPath);
            for (int i = 0; i < list.length; i++) {
                try (FileInputStream fis = new FileInputStream(list[i])) {
                     byte[] buffer = new byte[fis.available()];
                     fis.read(buffer);
                     zos.putNextEntry(new ZipEntry("save_" + (i + 1) + ".dat"));
                     zos.write(buffer);
                     zos.closeEntry();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void delete(String savegamesPath) {
        File file = new File(savegamesPath);
        if (file.delete()) {
            System.out.println("Незаархивированные файлы удалены");
        } else {
            System.out.println("Файлы не удалены");
        }
    }
}   