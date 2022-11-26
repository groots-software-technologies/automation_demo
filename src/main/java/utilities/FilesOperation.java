package utilities;

import org.apache.commons.io.FileUtils;

import java.io.*;

public class FilesOperation {
    private static void copyFiles(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest); // buffer size 1K
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static void copyDirectory(String source, String destination) throws IOException {
        FileUtils.copyDirectory(new File(source), new File(destination));
    }

    public static void cleanDirectory(String dirName) throws IOException {
        File dir;
        switch (dirName) {
            case "logs":
                dir = new File(globalVariables.projectDIR + "\\logs\\");
                if (!dir.exists()) {
                    dir.mkdirs();
                    System.out.println("'logs' directory is created");
                } else {
                    FileUtils.cleanDirectory(new File(globalVariables.projectDIR + "\\logs\\"));
                    System.out.println("'logs' directory is cleaned");
                }
                break;
            case "failed":
                dir = new File(globalVariables.projectDIR + "\\src\\test\\resources\\failed\\");
                if (!dir.exists()) {
                    dir.mkdirs();
                    System.out.println("'failed' directory is created inside \\test\\resources");
                } else {
                    FileUtils.cleanDirectory(new File(globalVariables.projectDIR + "\\src\\test\\resources\\failed\\"));
                    System.out.println("'failed' directory is cleaned");
                }
                break;
            case "report":
                dir = new File(globalVariables.projectDIR + "\\src\\test\\reports\\");
                if (!dir.exists()) {
                    dir.mkdirs();
                    System.out.println("'reports' directory is created inside \\test");
                } else {
                    FileUtils.cleanDirectory(new File(globalVariables.projectDIR + "\\src\\test\\reports\\"));
                    System.out.println("'reports' directory is cleaned");
                }
                break;

            default:
                System.out.println("Wrong directory name!!");
                break;
        }

    }

    public static void deleteFile(String fileName) throws IOException {
        File file = new File(fileName);

        if (file.delete()) {
            System.out.println(file.getName()+" File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file "+file.getName()+" path: "+fileName);
        }
    }

    public static void copyFile(String src, String dest) throws IOException{
        File from = new File(src);
        File to = new File(dest);

        FileUtils.copyFile(from, to);
        System.out.println("File '"+from+"' is copied successfully, "+dest);
    }
}
