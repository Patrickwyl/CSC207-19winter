package ATM;

import java.io.*;
import java.util.ArrayList;

/**
 * A class used to read a file or a folder.
 */

class ReadFile {

    /**
     * Return an ArrayList containing all the content in this file.
     */
    static ArrayList<String> readFile(String filename){
        ArrayList<String> data = new ArrayList<>();
        File file = new File("phase1/files/" + filename);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
                data.add(st);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * Return an ArrayList of ArrayList, each contains all the content of each txt file in the folder.
     */
    static ArrayList<ArrayList<String>> readFolder(String folderName) throws Exception {
        ArrayList<ArrayList<String>> summary = new ArrayList<>();
        final File folder = new File("phase1/files/" + folderName);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String filename = file.getName();
                    ArrayList<String> data = readFile(folderName + "/" + filename);
                    summary.add(data);
                }else {
                    throw new Exception("It's not a file.");
                }
            }
        }else {
            throw new Exception("The folder is empty.");
        }
        return summary;

    }

}


