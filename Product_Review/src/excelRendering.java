import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class excelRendering {

    private String inputFile;
   
    
    public void setInputFile(String inputFile) 
    {
        this.inputFile = inputFile;
    }

    public List<String> read(int columnNumber) throws IOException  
    {
        List<String> data = new ArrayList<String>();
        File inputWorkbook = new File(inputFile);
        Workbook w;

        try 
        {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet

            Sheet sheet = w.getSheet(0);
                for (int i = 1; i < sheet.getRows(); i++) 
                {
                    Cell cell = sheet.getCell(columnNumber, i);
                    if (cell.getContents() != null && !cell.getContents().trim().equalsIgnoreCase("")){
                        data.add(cell.getContents());                 
                    }
                }
             w.close();
        } 
        catch (BiffException e) 
        {
            e.printStackTrace();
        }
        
        return data;
    }
}
