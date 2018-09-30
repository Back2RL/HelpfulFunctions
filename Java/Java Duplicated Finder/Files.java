package DuplicatesFinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Leo on 27.01.2016.
 */
public class Files {
    public static void main(String[] args) {

// wurden gültige Argumente übergeben?
        if (args.length == 0) {
            throw new IllegalArgumentException("Ordnerpfad [Listenpfad]");
        }
        File folder = new File(args[0]);
        if(!folder.isDirectory()){
            folder = folder.getParentFile();
            System.out.println(folder.getName());
            throw new IllegalArgumentException("Ordnerpfad verweist nicht auf einen Ordner!");
        }

        System.out.println(String.valueOf(folder.lastModified()));
        System.out.println(folder.lastModified());

        String databaseFilePath = folder.getAbsolutePath() + "\\";

        if (args.length == 2) {
            databaseFilePath = args[1] + "\\";
        }


        LocalDateTime time = LocalDateTime.now();
        File backup = new File(databaseFilePath + "output.txt");

        Map<String, TreeSet<File>> alreadyListedFiles = new TreeMap<>();
        if (backup.exists()) {
            String timeString = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            timeString = timeString.replaceAll(":", "-");
            timeString = timeString.replaceAll("\\.", "-");
            String listName = databaseFilePath + "output_" + timeString + ".txt";
            System.out.println("print: " + listName);
            backup.renameTo(new File(new String(listName)));
            Scanner scanner = null;
            try {

                scanner = new Scanner(new File(listName));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String sha256 = line.substring(0, 64);
                    String file = line.substring(65, line.length());

                    if (alreadyListedFiles.containsKey(sha256)) {
                        alreadyListedFiles.get(sha256).add(new File(file));
                    } else {
                        TreeSet<File> files = new TreeSet<>();
                        files.add(new File(file));
                        alreadyListedFiles.put(sha256, files);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getLocalizedMessage());
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }


        Map<File, String> comparisonList = new TreeMap<>();
        for (String sha256 : alreadyListedFiles.keySet()) {
            for (File file : alreadyListedFiles.get(sha256)) {
                if (file.exists()) {
                    //System.out.println(file);
                    comparisonList.put(file, sha256);
                } else {
                    alreadyListedFiles.get(sha256).remove(file);
                    System.out.println("file notfound: " + file);
                    if (alreadyListedFiles.get(sha256).size() == 0) {
                        alreadyListedFiles.remove(sha256);
                    }
                }
            }
        }
        Map<String, TreeSet<File>> foundFiles = alreadyListedFiles;


        ArrayList<File> dirs = new ArrayList<>();
        dirs.add(folder);

        do {
            for (File fileEntry : dirs.get(0).listFiles()) {
                if (fileEntry.isDirectory()) {
                    dirs.add(fileEntry);
                    //listFilesForFolder(fileEntry,foundFiles);
                } else {
                    if (comparisonList.containsKey(fileEntry)) {
                        continue;
                    }
                    System.out.println(fileEntry.getName());
                    String sha256 = checksumOfFileSHA256(fileEntry.getAbsolutePath());
                    if (foundFiles.containsKey(sha256)) {
                        foundFiles.get(sha256).add(fileEntry);
                    } else {
                        TreeSet<File> files = new TreeSet<>();
                        files.add(fileEntry);
                        foundFiles.put(sha256, files);
                    }
                }
            }
            dirs.remove(0);
            System.out.println("Directories left: " + dirs.size());
        } while (dirs.size() != 0);


        // merge old list and new list


        PrintStream output = null;
        try {
            output = new PrintStream(new File(databaseFilePath + "output_new.txt"));
            //output.println("this is being written in a file");
            //output.append("test");

            for (String sha256 : foundFiles.keySet()) {
                for (File file : foundFiles.get(sha256)) {
                    output.println(sha256 + "=" + file);
                }
            }
            output.close();
            System.out.println("number of files: " + foundFiles.size());

            System.out.println();
            System.out.println("Duplicates:");

            output = new PrintStream(new File(databaseFilePath + "duplicates.txt"));
            int dulic = 0;
            TreeSet<File> duplicates = new TreeSet<>();
            for (TreeSet<File> files : foundFiles.values()) {
                if (files.size() > 1) {
                    for (File file : files) {
                        duplicates.add(file);
                        output.println(file);
                        System.out.println(file);
                        dulic++;
                    }
                }
            }
            System.out.println(dulic + " Duplicates found.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        } finally {
            if (output != null) {
                output.close();
            }
        }
        File newOutput = new File(databaseFilePath + "output_new.txt");
        newOutput.renameTo(new File(databaseFilePath + "output.txt"));


    }


    public static String checksumOfFileSHA1(String datafile) {
        FileInputStream fis = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
            //md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(datafile);

            byte[] dataBytes = new byte[1024];

            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;


            byte[] mdbytes = md.digest();

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //System.out.println("Digest(in hex format):: " + sb.toString());
        } catch (Exception e) {
            e.getLocalizedMessage();
            return "";
        }
        return "";
    }

    public static String checksumOfFileSHA256(String datafile) {
        String code = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(datafile);

            byte[] dataBytes = new byte[1024];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            ;
            byte[] mdbytes = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            code = sb.toString();
            //System.out.println("Hex format : " + code);

//            //convert the byte to hex format method 2
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < mdbytes.length; i++) {
//                hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
//            }
//
//            System.out.println("Hex format : " + hexString.toString());
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        return code;
    }

    public static String checksumOfStringSHA256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            System.out.println("Hex format : " + sb.toString());

//            //convert the byte to hex format method 2
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < byteData.length; i++) {
//                String hex = Integer.toHexString(0xff & byteData[i]);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//            System.out.println("Hex format : " + hexString.toString());
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        return "";
    }


}
