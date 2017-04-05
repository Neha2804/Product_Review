import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class ReviewPanel extends JFrame implements ActionListener 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3670162026515955471L;
	Container c;
	Font font_home = new Font("signboard", Font.BOLD, 30);
    Font font_home1 = new Font("Charter BT", Font.ITALIC, 20);
    Color right = new Color(0,51,102);
    Color left = new Color(0,51,102);
	JPanel jp = new JPanel();
	private JButton submit_review;
	String product, company;
	
	//private JPanel jp2;
	private JTextField txt_review;
	
	ReviewPanel(String product, String company)
	{
		this.product = product;
		this.company = company;
		
		c = getContentPane();
        c.setLayout(new BorderLayout());

        JPanel jp = new JPanel();
        jp.setBackground(left);
        JPanel jp_btn = new JPanel();
        JPanel jp_cb1 = new JPanel();
        jp.setLayout(null);
        
        txt_review = new JTextField();

        jp_cb1.add(txt_review);
        txt_review.setBounds(20, 20, 500, 50);
        txt_review.setFont(font_home);
        txt_review.setForeground(left);
        
        jp.setBackground(right);
        
        submit_review = new JButton("Submit Review");
        submit_review.setFont(font_home1);
        submit_review.setBounds(150, 100, 150, 50);
        submit_review.setBackground(Color.white);
        submit_review.setForeground(right);
        submit_review.addActionListener(this);
        jp_btn.add(submit_review);
        jp.add(txt_review);
        jp.add(submit_review);
        c.add(jp);        
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource().equals(submit_review))
		{
			 String filename = "final_"+product+".xls";
			 excelRendering er = new excelRendering();
			 er.setInputFile(filename);
			 int col = 0;
			 
			 if(company.contains("Amazon"))
				 col = 0;
			 if(company.contains("Flipkart"))
				 col = 1;
			 if(company.contains("Ebay"))
				 col = 2;
			 if(company.contains("shopclues"))
				 col = 3;
			 if(company.contains("GadgetBucket"))
				 col = 4;
			 
			 try
			 {
				 File file = new File(filename);
	             //String abPath = file.getAbsolutePath();

	             File f1 = new File("resources/"+filename);
	             System.out.println("going to enter exists");
	             System.out.println("Path: "+ f1.toString());
	             System.out.println(f1.exists());

	             Workbook workbook = Workbook.getWorkbook(f1);
	             Sheet sheet = workbook.getSheet(0);

	             int rows_read = sheet.getRows();
	             int col_read = sheet.getColumns();
	             WritableWorkbook w = Workbook.createWorkbook(f1);
	             WritableSheet wsheet = w.createSheet("First Sheet", 0);
	             
	             String u_r = txt_review.getText();
	             
	             for(int k=0; k<col_read; k++)
	             {
	            	 for (int row = 0; row < rows_read; ++row)
		             {
		                 Cell cell_r = sheet.getCell(k, row);
		                 String review = (cell_r.getContents()).toString();
		                 Label n;
		                 int temp = row-1;		                
		                	 n= new Label(k, row, review);
//		                	 row++;
//		                	 row = temp;
		                 
		                 System.out.println("Row no: " + row);		                 
		                 wsheet.addCell(n);
		             }
	             }

	             int Rows = wsheet.getRows();	             

	             System.out.println("Rows " + Rows);

	             Label label_u = new Label(col, Rows, u_r);
	             wsheet.addCell(label_u);

	             JOptionPane.showMessageDialog(c, "Review added to " + filename);

	             w.write();
	             w.close();
			 }
			 catch(Exception ex)
			 {
				 ex.printStackTrace();
			 }
		}		
		
	}

}
