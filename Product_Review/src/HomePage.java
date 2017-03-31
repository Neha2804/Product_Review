import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import edu.stanford.nlp.io.EncodingPrintWriter.err;


public class HomePage extends JFrame implements ActionListener{
	
    Container c;

    Font font_home = new Font("signboard", Font.BOLD, 30);
    Font font_home1 = new Font("Viner Hand ITC", Font.ITALIC, 20);
    Color right = new Color(0,51,102);
    Color left = new Color(0,51,102);
    JComboBox<String> jcombo_companies = new JComboBox<String>();
    JComboBox<String> jcombo_products = new JComboBox<String>();
	private JLabel lb_txt;

	private JButton compare_product;
    
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

        //jcombo_companies.setBounds(250, 50, 45, 45);
        JPanel jp = new JPanel();
        jp.setBackground(left);
        JPanel jp_btn = new JPanel();
        JPanel jp_cb1 = new JPanel();
        JPanel jp_cb2 = new JPanel();
        jp.setLayout(new FlowLayout());
        jp_btn.setLayout(null);
        
        lb_txt = new JLabel();
        lb_txt.setText("select a company:");

        jp_cb1.add(lb_txt);
        lb_txt.setBounds(250, 150, 100, 30);
        lb_txt.setFont(font_home);
        lb_txt.setForeground(left);
        
        jp_cb1.setBounds(250, 150, 100, 30);
        
        jp_cb1.add(jcombo_companies);
        
        
        //jcombo_products.setBounds(270, 50, 45, 45);
        jp.setBackground(left);
        lb_txt = new JLabel();
        lb_txt.setText("select a product:");

        jp_cb2.add(lb_txt);
        lb_txt.setBounds(250, 150, 100, 30);
        lb_txt.setFont(font_home);
        lb_txt.setForeground(left);
        
        jp_cb2.setBounds(250, 150, 145, 45);
        jp_cb2.add(jcombo_products);
        
//        compare_product = new JButton("Compare Product");
//        compare_product.setFont(font_home1);
//        compare_product.setBounds(400, 400, 200, 50);
//        compare_product.setBackground(Color.white);
//        compare_product.setForeground(right);
//        compare_product.addActionListener(this);
//        jp_btn.add(compare_product);
        
        
        c.add(jp);
       // jp.add(jp_btn);
        jp.add(jp_cb1);
        jp.add(jp_cb2);

    }
    
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
