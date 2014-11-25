package partB;
 
import java.io.*;
import weka.core.*;
import weka.core.converters.TextDirectoryLoader;
import weka.core.tokenizers.WordTokenizer;
 
public class CopyOfGenerate_arff {

	public static void main(String[] args) {
		System.out.println("testen");
	}
	public void TDL() throws IOException {
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