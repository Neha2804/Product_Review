import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class BarChart_AWT extends ApplicationFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
   // private JButton submit;
//    private JTextField emailTextBox;
    private HashMap<String, Double> resultset;
    private String hotel = "";
    
    public BarChart_AWT(String applicationTitle, String chartTitle, final String hotel, Map<String, Double> result, String userType)
    {
        super(applicationTitle);
        this.hotel = hotel;
        System.out.println("counts**********"+result.get("Good review"));
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Hotel",
                "Rating",
                createDataset(hotel, result),
                PlotOrientation.VERTICAL,
                true, true, false);

        Font f = new Font("abc", Font.ITALIC, 30);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        chartPanel.setFont(f);
        
        CategoryPlot cplot = (CategoryPlot)barChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.controlHighlight);//change background color

        BarRenderer r = (BarRenderer)barChart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0,  new Color(37, 139, 37));//dark green
        r.setSeriesPaint(1, new Color(139,5,5));//dark red
        setContentPane(chartPanel);
    }
        
//        JPanel jPanel = new JPanel();
//        emailTextBox = new JTextField(20);
//        emailTextBox.setBounds(200, 220, 150, 30);
        
//        submit = new JButton("Send Email");
//        submit.setBounds(100, 380, 150, 50);
//        submit.setBackground(Color.white);
//          submit.addActionListener(new java.awt.event.ActionListener() {
//        	
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                //actionPerformed(evt);
//                System.out.println("Inside action listener");
//                String email = emailTextBox.getText();
//                if (email != null && !email.isEmpty()){
//                    //sending an email if threshold is less than 50
//                    double threshold = 50;
//                    //hotel = this.hotel;
//                    StringBuilder message = new StringBuilder("Following are the reviews below ").append(threshold).append(" for "+ hotel).append(" : ");
//                    boolean isLessReview = false;
//                    Iterator it = resultset.entrySet().iterator();
//                    while (it.hasNext()) {
//                        Map.Entry pair = (Map.Entry)it.next();
//                        System.out.println(pair.getKey() + " = " + pair.getValue());
//                        if (Double.parseDouble((pair.getValue().toString())) < threshold){
//                            isLessReview = true;
//                            message.append("\n ").append(pair.getKey()).append(" : ").append(pair.getValue());
//                        }
//                    }
//                    
////                    if (isLessReview){
////                        System.out.println("inside send email");
////                        SendEmail.send(email, "Hotel Statistics", message.toString());
////                        JOptionPane.showMessageDialog(getContentPane(), "Email send successfully");
////                    }
//                }
//                System.out.println("END");
//            }
//        });
//
//        jPanel.add(chartPanel);
//        if (("ADMIN").equalsIgnoreCase(userType)){
//            jPanel.add(emailTextBox);
//            jPanel.add(submit);
//        }
//        setContentPane(jPanel);
//        
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e)
//            {
//                System.exit(0);
//            }
//        });
//    }

    public CategoryDataset createDataset(String hotel, Map<String, Double> result)
    {
          
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
        resultset = new HashMap<String, Double>();
        for (Map.Entry<String, Double> entry : result.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            dataset.addValue(entry.getValue() , entry.getKey(),hotel); 
            resultset.put(entry.getKey(), entry.getValue());
        }
               
        return dataset; 
    }
    
}
