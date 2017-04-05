import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

//import edu.stanford.nlp.ie.machinereading.*;


public class Data_Preprocessed {

	private String product;
	List<String> stopwords = new ArrayList<String>();
	private Component c;
	

	public Data_Preprocessed(String productname) throws IOException, BiffException, RowsExceededException, WriteException {

		excelRendering ne = new excelRendering();
        ne.setInputFile("resources/stopwords.xls");
        try 
        {
			stopwords = ne.read(1);
		} 
        catch (IOException e) 
		{
			e.printStackTrace();
		}
        
        String path = new String("resources/final_");
        String sw = new String("resources/final_Stopwords_");
        String lem = new String("resources/final_Lemmatized_");
        if (productname.contains("Mobile"))
        {
            product = "Mobile";
            path = path + "Mobile.xls";
            sw = sw + "Mobile.xls";
            lem = lem + "Mobile.xls";
        }
        else if (productname.contains("LCD TV"))
        {
            product = "LCD TV";
            path = path + "LCD.xls";
            sw = sw + "LCD.xls";
            lem = lem + "LCD.xls";
        }
        else if (productname.contains("I-Pad"))
        {
            product = "I-Pad";
            path = path + "I-Pad.xls";
            sw = sw + "I-Pad.xls";
            lem = lem + "I-Pad.xls";
        }
        else if (productname.contains("Headphone"))
        {
            product = "Headphone";
            path = path + "Headphone.xls";
            sw = sw + "Headphone.xls";
            lem = lem + "Headphone.xls";
        }
        else if(productname.contains("Laptop"))
        {
            product = "Laptop";
            path = path + "Laptop.xls";
            sw = sw + "Laptop.xls";
            lem = lem + "Laptop.xls";
        }
        else
        {
        	JOptionPane.showMessageDialog(c, "Something went wrong, please check for the review excel in resources.");
        }

        Workbook workbook = Workbook.getWorkbook(new File(path));
        Sheet sheet = workbook.getSheet(0); //original review sheet (read only)

        WritableWorkbook w = Workbook.createWorkbook(new File(sw));
        WritableSheet wsheet = w.createSheet("First Sheet", 0); //read-write version

        WritableWorkbook w1 = Workbook.createWorkbook(new File(lem));
        WritableSheet wsheet1 = w1.createSheet("First Sheet", 0);

        StanfordLemmatizer slem = new StanfordLemmatizer();
        List<String> ls = new LinkedList<String>();

        int i = 1;

        for (i = 1; i <= 100; i++)
        {
            Cell cell1;

            cell1 = sheet.getCell(1, i);

            String s = (cell1.getContents()).toString();

            //stop words filtering

            String s1 = new String();
            String s2 = new String();

            String array[] = new String[s.length()];
            String d = " ";

            array = s.split(d);

            int j = 0;
            int k = 0;

            /*  for(j=0;j<array.length;j++)
              {
                  System.out.println(array[j]);
              }*/

            for (j = 0; j < array.length; j++)
            {
                for (k = 0; k < stopwords.size(); k++)
                {
                    if (array[j].equalsIgnoreCase(stopwords.get(k)))
                        array[j] = " ";

                }
            }

            s = " ";

            for (j = 0; j < array.length; j++)
            {
                s = s + " " + array[j];
            }

            //lemmatization

            ls = slem.lemmatize(s);

            String temp = new String();

            String a[] = new String[ls.size()];
            int t = 0;
            for (Object value : ls) {
                a[t] = (String) value;
                t++;
            }

            for (int p = 0; p < a.length; p++)
            {
                temp = temp + a[p] + " ";
            }

            for(int cc = 0; cc <5; cc++)
            {
            	Label label = new Label(cc, i, s);
                wsheet.addCell(label); //copying stop words filtered

                Label label1 = new Label(cc, i, temp);
                wsheet1.addCell(label1); //copying lemmatized 

                Cell cell2 = wsheet.getCell(cc, i);
                // System.out.println(cell2.getContents());

                Cell cell3 = wsheet1.getCell(cc, i);
                // System.out.println(cell3.getContents());
            }

        }

        w.write();
        w1.write();

        workbook.close();
        w.close();
        w1.close();

        JOptionPane.showMessageDialog(c, "Pre-Processing Done.");
//        Hypernyms h = new Hypernyms();
//        
//        h.setup(hotel, lem, user);

	}

}

class StanfordLemmatizer
{
    protected StanfordCoreNLP pipeline;

    public StanfordLemmatizer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit,pos,lemma");

        /*
         * This is a pipeline that takes in a string and returns various analyzed linguistic forms. 
         * The String is tokenized via a tokenizer (such as PTBTokenizerAnnotator), 
         * and then other sequence model style annotation can be used to add things like lemmas, 
         * POS tags, and named entities. These are returned as a list of CoreLabels. 
         * Other analysis components build and store parse trees, dependency graphs, etc. 
         * 
         * This class is designed to apply multiple Annotators to an Annotation. 
         * The idea is that you first build up the pipeline by adding Annotators, 
         * and then you take the objects you wish to annotate and pass them in and 
         * get in return a fully annotated object.
         * 
         *  StanfordCoreNLP loads a lot of models, so you probably
         *  only want to do this once per execution
         */
        this.pipeline = new StanfordCoreNLP(props);
    }

    List<String> lemmatize(String documentText)
    {
        List<String> lemmas = new LinkedList<String>();
        // Create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);
        // run all Annotators on this text
        this.pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the
                // list of lemmas
                lemmas.add(token.get(LemmaAnnotation.class));
            }
        }
        return lemmas;

    }
}
