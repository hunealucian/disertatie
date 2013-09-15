package heartbeat.project.commons.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: luc  | Date: 8/7/13  |  Time: 10:40 PM
 */
public class FileUtils {

    public static boolean saveFile(byte[] bytes, String fileName, String filePath, int fileReplication) {
        try {
            Files.write(bytes, new File(filePath + "/" + fileName));

            if (fileReplication != -1) {
                generateVersionFile(new File(filePath + "/" + fileName), fileReplication);
            }

            return true;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveFile(byte[] bytes, String fileName, String filePath) {
        return saveFile(bytes, fileName, filePath, -1);
    }

    public static boolean deleteFile(File file) {
        if (file.exists())
            if (file.delete())
                return true;

        return false;
    }

    public static void generateVersionFile(File file, int replication) throws IOException, NoSuchAlgorithmException {

        int fileVersion = getFileVersionInfo(file);

        StringBuffer buffer = new StringBuffer();
        buffer.append(fileVersion == -1 ? 1 : (fileVersion + 1));
        buffer.append("\n");
        buffer.append(replication);

        Files.write(buffer.toString().getBytes(), new File(file.getAbsolutePath() + ".version"));
    }

    public static int getFileVersionInfo(File file) throws IOException {
        int result = -1;

        if (new File(file.getAbsolutePath() + ".version").exists()) {
            String fileContent = getFileContent(new File(file.getAbsolutePath() + ".version"));
            if (fileContent != null) {

                String[] rows = fileContent.split(System.getProperty("line.separator"));
                if (rows != null && rows.length >= 1) {
                    result = Integer.parseInt(rows[0].replace("\r", ""));
                }
            }
        } else {
            new File(file.getAbsolutePath() + ".version").createNewFile();
        }

        return result;
    }

    public static int getFileReplicationInfo(File file) throws IOException {
        int result = -1;

        if (new File(file.getAbsolutePath() + ".version").exists()) {
            String fileContent = getFileContent(new File(file.getAbsolutePath() + ".version"));
            if (fileContent != null) {

                String[] rows = fileContent.split(System.getProperty("line.separator"));
                if (rows != null && rows.length >= 2) {
                    result = Integer.parseInt(rows[1].replace("\r", ""));
                }
            }
        } else {
            new File(file.getAbsolutePath() + ".version").createNewFile();
        }

        return result;
    }



    public static String getFileContent(File file) throws IOException {
        return Files.toString(file, Charsets.UTF_8);
    }

    /**
     * Calculates a file checksum
     *
     * @param file
     * @return - checksum  as String
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String getFileChecksum(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fiStream = new FileInputStream(file);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fiStream.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }


        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer buffer = new StringBuffer("");

        if (mdbytes.length > 0)
            for (byte mdbyte : mdbytes) {
                buffer.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
            }

        return buffer.toString();
    }

    /**
     * Calculates a folder size in kb
     *
     * @param directory
     * @return - returns folder size in kb
     */
    public static long folderSize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                length += file.length();
            else
                length += folderSize(file);
        }
        return length;
    }

}
