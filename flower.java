package learning.watson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

public class flower {
	public static void main(String[] args) throws FileNotFoundException {
		VisualRecognition service = new VisualRecognition("2018-04-15");
		service.setApiKey("{api key}");
		InputStream imagesStream = new FileInputStream("/Users/monsi/Desktop/170477-004-B774BDDF.jpg");
		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
		  .imagesFile(imagesStream)
		  .imagesFilename("fruitbowl.jpg")
		  .threshold((float) 0.6)
		  .build();
		ClassifiedImages result = service.classify(classifyOptions).execute();
		System.out.println(result);
		System.out.println("NEXT");
		ArrayList<ClassifiedImage> result1 = (ArrayList<ClassifiedImage>) result.getImages();
		ArrayList<ClassifierResult> classifiedResults = (ArrayList<ClassifierResult>) result1.get(0).getClassifiers();
		ArrayList<ClassResult> classResults = (ArrayList<ClassResult>) classifiedResults.get(0).getClasses();
		ArrayList<ClassResult> classResultsFinal = new ArrayList<ClassResult>();
		TreeMap<Float, String> classResultsFinalMap = new TreeMap<Float, String>();
		for(int i = 0; i < classResults.size(); i++) {
			System.out.println(classResults.get(i).getClassName() + " " + classResults.get(i).getScore());
			if (!filterOutColor(classResults.get(i))) {
				if (!classResults.get(i).getClassName().equals("plant")) {
					if (!classResults.get(i).getClassName().equals("flower")) {
						if (!classResults.get(i).getClassName().equals("flowering plant")) {
							classResultsFinal.add(classResults.get(i));
							classResultsFinalMap.put(classResults.get(i).getScore(), classResults.get(i).getClassName());
						}
					}
				}
			}
		}
		System.out.println("NEXT NEXT");
		for (int i = 0; i < classResultsFinal.size(); i++) {
			System.out.println(classResultsFinal.get(i).getClassName() + " " + classResultsFinal.get(i).getScore());
		}
		System.out.println("NEXT NEXT NEXT");
		//Float[] arr = (Float[])classResultsFinalMap.keySet().toArray();
		//System.out.println(classResultsFinalMap.get(arr[arr.length-1]));
		try {
			System.out.println(classResultsFinalMap.lastEntry().getValue());
		} catch(Exception e){
			TreeMap<Float, String> classResultsFinalBadMap = new TreeMap<Float, String>();
			for (int i = 0; i < classResults.size(); i++) {
				classResultsFinalBadMap.put(classResults.get(i).getScore(), classResults.get(i).getClassName());
			}
			System.out.println(classResultsFinalBadMap.lastEntry().getValue());
		}
		
	}	
	
	public static boolean filterOutPlant(ClassResult x) {
		if (x.getClassName().equals("plant")) return true;
		return false;
	}
	
	public static boolean filterOutColor(ClassResult x) {
		if (x.getClassName().contains("color")) return true;
		return false;
	}
}
