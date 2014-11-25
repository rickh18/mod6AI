package partB;

/*
 *    TextDirectoryToArff.java
 *    Copyright (C) 2002 Richard Kirkby
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
 
import java.io.*;

import weka.core.*;
import weka.core.converters.TextDirectoryLoader;
import weka.core.tokenizers.WordTokenizer;
 
/**
 * Builds an arff dataset from the documents in a given directory.
 * Assumes that the file names for the documents end with ".txt".
 *
 * Usage:<p/>
 *
 * TextDirectoryToArff <directory path> <p/>
 *
 * @author Richard Kirkby (rkirkby at cs.waikato.ac.nz)
 * @version 1.0
 */
public class Generate_arff {
 
	public static void main(String[] args) throws IOException {
		//TextDirectoryLoader TDL = new TextDirectoryLoader();
			//TDL.setDirectory(new File("C:/Users/Remco/Documents/UTwente/mod6/blogstrain/M"));
			
			//String s = TDL.getDataSet().toString();
			File file = new File("C:/Users/Remco/Documents/UTwente/mod6/blogstrainM.txt");
	        BufferedWriter output = new BufferedWriter(new FileWriter(file));
	        output.write("test");
	        output.flush();
	        output.close();
	        System.out.println("done");
			//System.out.println(s);

		//WordTokenizer WT = new WordTokenizer();
		//WT.tokenize(s);
		//System.out.println(s);
	}
}