
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class HW_Rode_Ajinkya_ScatterPlot  {
	
	
	public static int calculate(Double[] attr2,Integer[] class_attr2)
	{
	int a=0;
	int b=0;
	int c=0;
	int d=0;
	double gini1[]=new double[10];
	double gini2[]=new double[10];
	double final_gini[]=new double[10];
	int ptr=0;
	int ptr1=0;
	int ptr3=0; 
	double min=1000;
	int index=0;
	for(int start=1;start<11;start++)
	{
		for(int j=0;j<attr2.length;j++)
		{
		if(attr2[j]<start&&class_attr2[j]==0)
		{
			a++;
		}
		else if(attr2[j]<start&&class_attr2[j]==1)
		{
			b++;
		}
		}
		int total1=a+b;
		//System.out.println(a+":+:"+b);
		double ct=(double)a/(a+b);
		double cr=(double)b/(a+b);
		//System.out.println(ct+"-:+:-"+cr);
		gini1[ptr]=1-(double)Math.pow(ct,2)-(double)Math.pow(cr,2);
		double g1=(double)gini1[ptr];
		ptr++;
		a=0;
		b=0;
		
		
		for(int k=0;k<attr2.length;k++)
		{
		if(attr2[k]>=start&&class_attr2[k]==0)
		{
			c++;
		}
		else if(attr2[k]>=start&&class_attr2[k]==1)
		{
			d++;
		}
		}
		//System.out.println(c+":+:"+d+"here");
		int total2=c+d;
		double ct1=(double)c/(c+d);
		double cr1=(double)d/(c+d);
		//System.out.println(ct1+":+:"+cr1);
		gini2[ptr1]=1-(double)Math.pow(ct1,2)-(double)Math.pow(cr1,2);
		double g2=(double)gini2[ptr1];
		ptr1++;
		
		
		c=0;
		d=0;
	
		
		
		
		final_gini[ptr3]=minimum_gini(g1,total1,g2,total2);
		if(min>final_gini[ptr3])
        {
            min = final_gini[ptr3];
            index = start;
        }
		ptr3++;
		//System.out.println(a+"+"+b+"+"+c+"+"+d);
	
	}
	
	
	//System.out.println(a+"+"+b+"+"+c+"+"+d);
	/*for(int x=0;x<10;x++)
	{
	System.out.println(final_gini[x]+"yo");
	}*/
	

	//System.out.println(min+"+"+index);
	return index;
	}
	
	public static double minimum_gini(double a,int total1,double b,int total2)
	{
		double total=350;
		double a1=(double)((total1/total)*a);
		//System.out.println(a+"a "+total1+"total1 "+a1+"a1");
		
		double a2=(double)((total2/total)*b);
		//System.out.println(b+"b"+total2+"total2 "+a2+"a2");
		if(a1<0.01)
		{
				return a2;
		}
		else if(a2<0.01)
		{
				return a1;
		}
		else if(a1!=0&&a2!=0&&a1<a2)
			return a1;
			else
				return a2;
					
		}
	
	
	public static void main(String args[]) {	
		
		BufferedReader fileReader = null;
		final String DELIMITER = ",";
		boolean firstLine = true;
		String file_name="HW04_Training_Data__v010.csv";
		Double[] attr1=new Double[350];
		Double[] attr2=new Double[350];
		Double[] attr3=new Double[350];
		Integer[] class_attr1=new Integer[350];
		Integer[] class_attr2=new Integer[350];
		Integer[] class_attr3=new Integer[350];
		
		try
		{
			String line = "";
			fileReader = new BufferedReader(new FileReader(file_name));
			int p=0;
			while ((line = fileReader.readLine()) != null)
			{
				
				/*
				 * Remove the first row since it contains labels
				 */
				if (line.contains("Attr1")) {
					if (firstLine) {
						firstLine = false;
						continue;
					} 
				}
				String[] tokens = line.split(DELIMITER);
				attr1[p]=Double.parseDouble(tokens[0]);
				attr2[p]=Double.parseDouble(tokens[1]);
				attr3[p]=Double.parseDouble(tokens[2]);
				class_attr1[p]=Integer.parseInt(tokens[3]);
				class_attr2[p]=Integer.parseInt(tokens[3]);
				class_attr3[p]=Integer.parseInt(tokens[3]);
				p++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/*for(int i=0;i<attr1.length;i++)
		{
		System.out.println(attr1[i]+"->"+attr2[i]+"->"+attr3[i]+"->"+class_attr[i]);
		}*/
		
		
		
		double temp=0;
		int temp1=0;
		for(int i=0;i<attr1.length;i++)
		{
			for(int j=i+1;j<attr1.length;j++)
			{
				if(attr1[i]>attr1[j])
				{
					temp=attr1[j];
					attr1[j]=attr1[i];
					attr1[i]=temp;
					temp1=class_attr1[j];
					class_attr1[j]=class_attr1[i];
					class_attr1[i]=temp1;
					temp=0;
					temp1=0;
				}
					
				if(attr2[i]>attr2[j])
				{
					temp=attr2[j];
					attr2[j]=attr2[i];
					attr2[i]=temp;
					temp1=class_attr2[j];
					class_attr2[j]=class_attr2[i];
					class_attr2[i]=temp1;
					temp=0;
					temp1=0;
				}
				
				if(attr3[i]>attr3[j])
				{
					temp=attr3[j];
					attr3[j]=attr3[i];
					attr3[i]=temp;
					temp1=class_attr3[j];
					class_attr3[j]=class_attr3[i];
					class_attr3[i]=temp1;
					temp=0;
					temp1=0;
				}
				}
			
		}
		
		//System.out.println(attr1[i]+"->"+attr2[i]+"->"+attr3[i]+"->"+class_attr[i]);
		
			
		//System.out.println(attr1[i]+"->"+class_attr1[i]+":"+attr2[i]+"->"+class_attr2[i]+":"+attr3[i]+"->"+class_attr3[i]+"--"+count_0+"--"+count_1+"NEW");
		
		
		
		//Threshold-a
		int threshold_a=0;
		threshold_a=calculate(attr1,class_attr1);
		int threshold_b=0;
		threshold_b=calculate(attr2,class_attr2);
		int threshold_c=0;
		threshold_c=calculate(attr3,class_attr3);
		System.out.println("Threshold for a:"+threshold_a);
		System.out.println("Threshold for b:"+threshold_b);
		System.out.println("Threshold for c:"+threshold_c);
		
/*        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Attribute 1");
        XYSeries series2 = new XYSeries("Attribute 2");
        XYSeries series3 = new XYSeries("Attribute 3");
        for (int i = 0; i < attr1.length; i++) {
            series1.add(attr1[i], attr2[i]);
            series2.add(attr2[i], attr3[i]);
            series3.add(attr1[i], attr3[i]);
        }
        xySeriesCollection.addSeries(series1);
        xySeriesCollection.addSeries(series2);
        xySeriesCollection.addSeries(series3);
        JFreeChart jfreechart = ChartFactory.createScatterPlot("Scatter Plot",
            "X", "Y", xySeriesCollection, PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        XYShapeRenderer renderer = new XYShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));
        xyPlot.setRenderer(renderer);
        try {
			ChartUtilities.saveChartAsJPEG(new File("/Users/ajinkyarode/Desktop/ScatterPlotDecisionTree.jpg"), jfreechart, 1200, 800);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}*/
    }
}
