package mod6AI.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
 
/**
 * Created by Student on 25-11-2014.
 */
public class FileMerger {
	
	/**
	 * Merges the text in all .txt files in a given directory 'dir' to a string
	 * @param dir The directory from where all the files are read
	 * @return String with the text from all the files
	 * @throws IOException
	 */
	public String merge(String dir) throws IOException {
		String output = "";
		String[] files = new File(dir).list();
		for(int i = 0; i < files.length; i++) {
			if(files[i].endsWith(".txt")) {
				File file = new File(dir + File.separator + files[i]);
				BufferedReader br = new BufferedReader(new FileReader(file));
				 
				String line = null;
				while ((line = br.readLine()) != null) {
					output += " " + line;
				}
			 
			}
			System.out.println(i);
		}
        return output;
	}
}