import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * - A class that generates a decision tree based on the 
 *   different thresholds and gini created by program 
 *   'HW04_Rode_Ajinkya_Trainer'. 
 * - It classifies the test data based on the decision model 
 *   created by that program and final writes the class 
 *   attributes based on this decision tree in a '.txt' file.
 * 
 * @author ajinkyarode
 *
 */
public class HW04_Rode_Ajinkya_Classifier {

	/**
	 * Default method
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		
		/*
		 * Read the test data file from the user. 
		 */
		BufferedReader fileReader = null;
		final String DELIMITER = ",";
		boolean firstLine = true;
		String file_name="HW04_Testing__Data__FOR_RELEASE__v010.csv";
		Double[] attr1=new Double[49];
		Double[] attr2=new Double[49];
		Double[] attr3=new Double[49];
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
		 * Run the classifier and get the three thresholds
		 */
		HW04_Rode_Ajinkya_Trainer train=new HW04_Rode_Ajinkya_Trainer();
		train.main(args);
		double a[] =train.getThreshold_a();
		double b[] =train.getThreshold_b();
		double c[] =train.getThreshold_c();
		double threshold_a=0;
		double threshold_b=0;
		double threshold_c=0;
		
		/*
		 * Sort the threshold in ascending order to get the most
		 * important attribute as threshold_a and so on
		 */
		if( a[1] > b[1] ){
		 if( a[1] > c[1] ){
			 threshold_c = a[0];
		  if( b[1] > c[1] ){
			  threshold_b = b[0];
			  threshold_a = c[0];
		  }else{
			  threshold_b = c[0];
			  threshold_a = b[0];
		  }
		 }else{
			 threshold_b = a[0];
		  if( b[1] > c[1] ){
			  threshold_c = b[0];
		   threshold_a = c[0];
		  }else{
			  threshold_c = c[0];
		   threshold_a = b[0];
		  }
		 }
		}else{
		 if( b[1] > c[1] ){
			 threshold_c = b[0];
		  if( a[1] > c[1] ){
			  threshold_b = a[0];
		   threshold_a = c[0];
		  }else{
			  threshold_b = c[0];
		   threshold_a = a[0];
		  }
		 }else{
			 threshold_b = b[0];
		  threshold_c = c[0];
		  threshold_a = a[0];
		 }
		}
		
		/*
		 * Classification of the attributes 
		 * for the test data
		 */
		int class_attr[]=new int[attr1.length];
		for(int i=0;i<attr1.length;i++)
		{
		if(attr1[i]>= threshold_a)
		{
			if(attr2[i]>=threshold_b)
				class_attr[i]=1;
			else
				class_attr[i]=0;
		}
		else
		{
			if(attr3[i]>=threshold_c)
				class_attr[i]=1;
			else
				class_attr[i]=0;
		}
		}
		
		/*
		 * Write the classified attributes into a new '.txt' file
		 */
		try
		{
		    PrintWriter pr = new PrintWriter("HW04_Rode_Ajinkya_MyClassifications.txt");    

		    for (int i=0; i<attr1.length ; i++)
		    {
		        pr.println(attr1[i]+","+attr2[i]+","+attr3[i]+"->"+class_attr[i]);
		    }
		    pr.close();
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		    System.out.println("No such file exists.");
		}	
	}
}