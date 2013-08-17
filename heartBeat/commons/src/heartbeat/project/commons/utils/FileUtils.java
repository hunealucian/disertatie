package heartbeat.project.commons.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * User: luc  | Date: 8/7/13  |  Time: 10:40 PM
 */
public class FileUtils {

    public static boolean saveFile(byte[] bytes, String fileName, String filePath, int fileReplication){
        try {
            Files.write(bytes, new File(filePath + "/" + fileName));

            if( fileReplication != -1 ){
                generateVersionFile(new File(filePath + "/" + fileName));
            }

            return true;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveFile(byte[] bytes, String fileName, String filePath){
        return saveFile(bytes, fileName, filePath, -1);
    }

    public static boolean deleteFile(File file){
        if( file.exists() )
            if( file.delete())
                return true;

        return false;
    }

    public static void generateVersionFile(File file) throws IOException, NoSuchAlgorithmException {

        int fileVersion = getFileVersionInfo(file);

        StringBuffer buffer = new StringBuffer();
        buffer.append(fileVersion == -1 ? 1 : (fileVersion + 1));

        Files.write(buffer.toString().getBytes(), new File(file.getAbsolutePath() + ".version"));
    }

    public static int getFileVersionInfo(File file) throws IOException {
        int result = -1;

        String fileContent = getFileContent(new File(file.getAbsolutePath() + ".version"));
        if( fileContent != null ){

            String[] rows = fileContent.split(System.getProperty("line.separator"));
            if( rows != null && rows.length >= 1 ){
                result = Integer.parseInt(rows[0].replace("\r", ""));
            }
        }

        return result;
    }

    public static String getFileContent(File file) throws IOException {
        return Files.toString(file, Charsets.UTF_8);
    }

    /**
     * Calculates a file checksum
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

    public static FilesAllocationTree<FATFolder, FATFile> getFolderTree(File folder) throws Exception {
        if (folder != null && folder.exists() && folder.isDirectory()) {
            FilesAllocationTree<FATFolder, FATFile> tree = new FilesAllocationTree<>(new FATFolder(folder.getName(), folder.getAbsolutePath(), folderSize(folder), new Date(folder.lastModified())));

            return getChilds(folder, tree);


        } else {
            throw new Exception("The selected folder is not a directory");
        }
    }

    private static FilesAllocationTree<FATFolder, FATFile> getChilds(File parrent, FilesAllocationTree tree) throws IOException, NoSuchAlgorithmException {
        if (parrent.isDirectory() && parrent.listFiles().length > 0) {
            for (File file : parrent.listFiles()) {
                if (file.isFile()) {
                    if( !file.getName().contains(".version") )
                        tree.addChild(new FATFile(file.getName(), file.getAbsolutePath(), file.length(), new Date(file.lastModified()), FileUtils.getFileVersionInfo(file), FileUtils.getFileChecksum(file) ));
                } else {
                    getChilds(file, tree.addChild(new FATFolder(file.getName(), file.getAbsolutePath(), folderSize(file), new Date(file.lastModified()))));
                }

            }
        }
        return tree;
    }
}
