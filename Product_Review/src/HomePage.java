import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import org.jfree.ui.RefineryUtilities;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import edu.stanford.nlp.io.EncodingPrintWriter.err;


public class HomePage extends JFrame implements ActionListener{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Container c;

    Font font_home = new Font("signboard", Font.BOLD, 30);
    Font font_home1 = new Font("Charter BT", Font.ITALIC, 20);
    Color right = new Color(0,51,102);
    Color left = new Color(0,51,102);
    JComboBox<String> jcombo_companies = new JComboBox<String>();
    JComboBox<String> jcombo_products = new JComboBox<String>();
	private JLabel lb_txt;

	private JButton compare_product;

	private JButton review_product;
    
    public HomePage() throws IOException
    {
	    try
	    {
	    	excelRendering er = new excelRendering();
	    	er.setInputFile("resources/companylist.xls");
	    	List<String> companylist = er.read(0);
	    	for(int i=0; i<companylist.size(); i++)
	    	{
	    		jcombo_companies.addItem(companylist.get(i));
	    	}
	    	
	    	er.setInputFile("resources/productlist.xls");
	    	List<String> productlist = er.read(0);
	    	for(int i=0; i<productlist.size(); i++)
	    	{
	    		jcombo_products.addItem(productlist.get(i));
	    	}
	    	
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    
	    c = getContentPane();
        c.setLayout(new GridLayout());

        JPanel jp = new JPanel();
        jp.setBackground(left);
        JPanel jp_btn = new JPanel();
        JPanel jp_cb1 = new JPanel();
        JPanel jp_cb2 = new JPanel();
        jp.setLayout(new FlowLayout());
        
        lb_txt = new JLabel();
        lb_txt.setText("select a company:");

        jp_cb1.add(lb_txt);
        lb_txt.setBounds(250, 150, 100, 30);
        lb_txt.setFont(font_home);
        lb_txt.setForeground(left);
        
        jp_cb1.setBounds(250, 150, 100, 30);
        jp_cb1.add(jcombo_companies);
        
        jp.setBackground(left);
        lb_txt = new JLabel();
        lb_txt.setText("select a product:");

        jp_cb2.add(lb_txt);
        lb_txt.setBounds(250, 150, 100, 30);
        lb_txt.setFont(font_home);
        lb_txt.setForeground(left);
        
        jp_cb2.setBounds(250, 150, 145, 45);
        jp_cb2.add(jcombo_products);
        
        compare_product = new JButton("Compare Product");
        compare_product.setFont(font_home1);
        compare_product.setBounds(250, 150, 145, 45);
        compare_product.setBackground(Color.white);
        compare_product.setForeground(right);
        compare_product.addActionListener(this);
        jp_btn.add(compare_product);
        
        JPanel jp_btn1 = new JPanel();

        review_product = new JButton("Review Product");
        review_product.setFont(font_home1);
        review_product.setBounds(250, 150, 145, 45);
        review_product.setBackground(Color.white);
        review_product.setForeground(right);
        review_product.addActionListener(this);
        jp_btn1.add(review_product);
        
        jp.add(jp_cb1);
        jp.add(jp_cb2);
        //jp.add(jp_btn);
        jp.add(jp_btn1);
        jp.add(jp_btn);
        c.add(jp);
    }
    
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		
		if(ae.getSource().equals(review_product))
		{
			String productname = jcombo_products.getSelectedItem().toString();
			String companyname = jcombo_companies.getSelectedItem().toString();
			ReviewPanel rp = new ReviewPanel(productname, companyname);
			rp.setVisible(true);
			rp.setSize(600,400);            
		}
		if(ae.getSource().equals(compare_product))
		{
			String productname = jcombo_products.getSelectedItem().toString();
			try {
				Data_Preprocessed dp = new Data_Preprocessed(productname);
				HashMap<String, Double> resultset = new HashMap<String, Double>();
				resultset.put("Amazon", 10d);
				resultset.put("Flipkart", 12d);
				resultset.put("Ebay", 12d);
				resultset.put("shopclues", 15d);
				resultset.put("GadgetBucket", 22d);
			BarChart_AWT bchart = new BarChart_AWT("Product Compare Chart", "Comparing "+productname, productname, resultset, "user");
			bchart.pack( );        
            RefineryUtilities.centerFrameOnScreen( bchart );        
            bchart.setVisible( true );
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            
		}
	}

}
