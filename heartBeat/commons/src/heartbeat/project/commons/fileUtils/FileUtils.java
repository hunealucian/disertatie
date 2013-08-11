package heartbeat.project.commons.fileUtils;

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
                generateVersionFile(new File(filePath + "/" + fileName), fileReplication);
            }

            return true;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void generateVersionFile(File file, int fileReplication) throws IOException, NoSuchAlgorithmException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getFileChecksum(file));
        buffer.append("\r\n");
        buffer.append(fileReplication);

        Files.write(buffer.toString().getBytes(), new File(file.getAbsolutePath() + ".version"));
    }

    public static boolean saveFile(byte[] bytes, String fileName, String filePath){
        return saveFile(bytes, fileName, filePath, -1);
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

    public static FilesAllocationTree<FATFolder> getFolderTree(File folder) throws Exception {
        if (folder != null && folder.exists() && folder.isDirectory()) {
            FilesAllocationTree<FATFolder> tree = new FilesAllocationTree<FATFolder>(new FATFolder(folder.getName(), folder.getAbsolutePath(), folderSize(folder), new Date(folder.lastModified())));

            return getChilds(folder, tree);


        } else {
            throw new Exception("The selected folder is not a directory");
        }
    }

    private static FilesAllocationTree<FATFolder> getChilds(File parrent, FilesAllocationTree tree) throws IOException, NoSuchAlgorithmException {
        if (parrent.isDirectory() && parrent.listFiles().length > 0) {
            for (File file : parrent.listFiles()) {
                if (file.isFile()) {
                    tree.addChild(new FATFile(file.getName(), file.getAbsolutePath(), file.length(), new Date(file.lastModified()), getFileChecksum(file) ));
                } else {
                    getChilds(file, tree.addChild(new FATFolder(file.getName(), file.getAbsolutePath(), folderSize(file), new Date(file.lastModified()))));
                }

            }
        }
        return tree;
    }
}
