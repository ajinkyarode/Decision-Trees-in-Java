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

/**
 * - A class that sets the threshold for each of the attributes
 *   based on the gini index of class atribute.
 * - In this program I have considered the thresholds ranging from 
 *   1-10 with a difference of 1 for the convenience.
 * - Method 'HW04_Rode_Ajinkya_GenerateScatterPlots' generates a
 *   scatterplot graph for the combination of three attribues.
 *  
 * @author ajinkyarode
 *
 */
public class HW04_Rode_Ajinkya_Trainer  {
	
	private static double[] threshold_a=new double[2];
	private static double[] threshold_b=new double[2];
	private static double[] threshold_c=new double[2];
	
	/**
	 * Method to generate the scatter plots for three attributes
	 * 
	 * @param attr1
	 * 		Attribute 1 array
	 * @param attr2
	 * 		Attribute 2 array
	 * @param attr3
	 * 		Attribute 3 array
	 */
	private static void HW04_Rode_Ajinkya_GenerateScatterPlots(Double[] attr1,Double[] attr2,Double[] attr3)
	{
	XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
    XYSeries series1 = new XYSeries("Attribute 1v2");
    XYSeries series2 = new XYSeries("Attribute 2v3");
    XYSeries series3 = new XYSeries("Attribute 1v3");
    /*
     * Generate three series for attributes
     * 1 vs 2, 2 vs 3 and 1 vs 3
     */
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
	}
}
	
	/**
	 * Method to calculate the gini index for a 
	 * particular attribute for upper and lower limit
	 * 
	 * @param attr2
	 * 		Attribute array
	 * @param class_attr2
	 * 		Class variable
	 * @return
	 * 		Returns threshold along with minimum gini
	 */
	public static double[] calculate(Double[] attr2,Integer[] class_attr2)
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
		double ct=(double)a/(a+b);
		double cr=(double)b/(a+b);
		
		/*
		 * Gini for class 0 values of the left hand side
		 */
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
		int total2=c+d;
		double ct1=(double)c/(c+d);
		double cr1=(double)d/(c+d);
		
		/*
		 * Gini for class 1 values or the right hand side
		 */
		gini2[ptr1]=1-(double)Math.pow(ct1,2)-(double)Math.pow(cr1,2);
		double g2=(double)gini2[ptr1];
		ptr1++;
		c=0;
		d=0;
		
		/*
		 * Calculate the weighted gini index
		 */
		final_gini[ptr3]=minimum_gini(g1,total1,g2,total2);
		
		/*
		 * Find the minimum gini index and respective 
		 * threshold for the same value
		 */
		if(min>final_gini[ptr3])
        {
            min = final_gini[ptr3];
            index = start;
        }
		ptr3++;
	}

	/*
	 * Store the threshold and its 
	 * respective gini in an array
	 */
	double values[]=new double[2];
	values[0]=index;
	values[1]=min;
	return values;
	}
	
	
	/**
	 * Method to calculate the weighted gini index and 
	 * the minimum of them for the two classes
	 * 
	 * @param a
	 * 		minimum gini for class 0
	 * @param total1
	 * 		total number of attributes for class 0
	 * @param b
	 * 		minimum gini for class 1
	 * @param total2
	 * 		total number of attributes for class 1
	 * @return
	 * 		minimum gini value
	 */
	public static double minimum_gini(double a,int total1,double b,int total2)
	{
		double total=350;
		double a1=(double)((total1/total)*a);
		double a2=(double)((total2/total)*b);
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
	
	
	/**
	 * Default method
	 *  
	 * @param args
	 */
	public static void main(String args[]) {	
		
		/*
		 * Read the training data file from the user. 
		 */
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
		
		/*
		 * Sort the attributes along with the class variable per attribute
		 */
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
			HW04_Rode_Ajinkya_GenerateScatterPlots(attr1,attr2,attr3);
		}
		
		/*
		 * Calculate the threshold for individual 
		 * attribute based on the Gini-index
		 */
		setThreshold_a(calculate(attr1,class_attr1));
		setThreshold_b(calculate(attr2,class_attr2));
		setThreshold_c(calculate(attr3,class_attr3));
    }
	
	/*
	 * Getters and setters for the three thresholds
	 */
	public static double[] getThreshold_a() {
		return threshold_a;
	}

	public static void setThreshold_a(double[] threshold_a) {
		HW04_Rode_Ajinkya_Trainer.threshold_a = threshold_a;
	}

	public static double[] getThreshold_b() {
		return threshold_b;
	}

	public static void setThreshold_b(double[] threshold_b) {
		HW04_Rode_Ajinkya_Trainer.threshold_b = threshold_b;
	}

	public static double[] getThreshold_c() {
		return threshold_c;
	}

	public static void setThreshold_c(double[] threshold_c) {
		HW04_Rode_Ajinkya_Trainer.threshold_c = threshold_c;
	}
}
