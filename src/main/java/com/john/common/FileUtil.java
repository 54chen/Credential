package com.john.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    private static String FILE_SALT = "./salt.db";
    private static String FILE_PASSWORD = "./password.db";
    private static String SEPARATOR = ":";

    private static String FILE_DICT = "./dicts.db";

    public static String KEY_USER = "user";
    public static String KEY_SALT = "salt";
    public static String KEY_HASH = "hash";
    public static String KEY_STATUS = "status";

    public static String getUserStoredSalt(String user) throws IOException {
        String salt = "";
        List<Map<String, String>> users = getUserFromPwdFile(user);
        for (Map<String, String> map : users) {
            if (map.get(KEY_USER).equalsIgnoreCase(user)) {
                return map.get(KEY_SALT);
            }
        }
        return salt;
    }
    public static List<Map<String, String>> getUserFromSaltFile(String user) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();
        List<String> dataFromFile = readFileByLines(FILE_SALT);
        dataFromFile.forEach((v) -> {
            String[] info = v.split(SEPARATOR);
            Map<String, String> m = new HashMap<>();
            m.put(KEY_USER, info[0]);
            m.put(KEY_SALT, info[1]);
            result.add(m);
        });
        return result;
    }

    public static void saveUserToSaltFile(String user, String salt) throws IOException {
        saveFileByLine(FILE_SALT, user + SEPARATOR + salt);
    }

    public static Map<String, String> getSingleUserFromPwdFile(String user) throws IOException {
        Map<String, String> m = new HashMap<>();
        List<String> dataFromFile = readFileByLines(FILE_PASSWORD);
        dataFromFile.forEach((v) -> {
            String[] info = v.split(SEPARATOR);
            m.put(KEY_USER, info[0]);
            m.put(KEY_HASH, info[1]);
            m.put(KEY_STATUS, info[2]);
        });
        return m;
    }
    public static List<Map<String, String>> getUserFromPwdFile(String user) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();
        List<String> dataFromFile = readFileByLines(FILE_PASSWORD);
        dataFromFile.forEach((v) -> {
            String[] info = v.split(SEPARATOR);
            Map<String, String> m = new HashMap<>();
            m.put(KEY_USER, info[0]);
            m.put(KEY_HASH, info[1]);
            m.put(KEY_STATUS, info[1]);
            result.add(m);
        });
        return result;
    }

    public static void saveUserToPwdFile(String user, String hash, String status) throws IOException {
        saveFileByLine(FILE_PASSWORD, user + SEPARATOR + hash + SEPARATOR + status);
    }

    public static List<String> readDictFromFile() throws IOException {
        return readFileByLines(FILE_DICT);
    }

    private static List<String> readFileByLines(String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        FileReader fr = new FileReader(new File(fileName));
        BufferedReader reader = new BufferedReader(fr);
        String tempString = null;
        while ((tempString = reader.readLine()) != null) {
            result.add(tempString);
        }
        reader.close();
        fr.close();
        return result;
    }

    private static void saveFileByLine(String fileName, String line) throws IOException {
        FileWriter fw = new FileWriter(new File(fileName));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(line + "\r\n");
        bw.close();
        fw.close();
    }
}