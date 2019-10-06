package ATM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class used to write a file and clear a file.
 */

class WriteFile {

    /**
     * Write a new line to an existing file.
     */
    static void write(String content, String filename){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter("phase1/files/" + filename, true);
            bw = new BufferedWriter(fw);
            bw.append(content);
            bw.newLine();
        }catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }

    /**
     * Delete all the content of the file.
     */
    static void clearInfo(String fileName){
        File file = new File("phase1/files/" + fileName);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}