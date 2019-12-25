import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Directory {

	public static void main(String[] args) throws IOException {
		BufferedWriter output=null;
		  File root1 = new File("G:\\TRK4.0\\Front_Lists_TRK4.0\\GeoTrek\\GeoTrek_Class7\\GeoTrek_Class_7\\DataFiles\\Data\\MCB\\Grade7\\Module");
	        String fileName = "index.html";
	        
	        File file = null;
	       String s=""; 
	       List<String> zoom = new ArrayList<>();
	        try {
	            boolean recursive = true;

	            Collection files = FileUtils.listFiles(root1, null, recursive);

	            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
	                 file = (File) iterator.next();
	                if (file.getName().equals(fileName))
	                	
	               
	               s += file.getAbsolutePath().replace("G:\\TRK4.0\\Front_Lists_TRK4.0\\GeoTrek\\GeoTrek_Class7\\GeoTrek_Class_7\\DataFiles\\Data\\MCB\\", "").replace("\\", "/") + "\n ";
	                  //System.out.println(file.getAbsolutePath().replace("G:\\TRK4.0\\Front_Lists_TRK4.0\\Weavers\\Weavers_Class2\\Weavers_Class2\\DataFiles\\Data\\MCB\\", "").replace("\\", "/"));
	               	                 
	              //String s1="/n"+s;
	              
	              
	            }
	           // File file1 = new File("F:\\example.txt");
	           // output = new BufferedWriter(new FileWriter(file1));
	          //  output.write(s);
	          //  
	            System.out.println(s);
	            //s=s.concat(file.getAbsolutePath().replace("G:\\TRK4.0\\Front_Lists_TRK4.0\\Weavers\\Weavers_Class2\\Weavers_Class2\\DataFiles\\Data\\MCB\\", "").replace("\\", "/").toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }/*finally {
	            if ( output != null ) {
	                output.close();
	              }
	            }*/
	      
	   String arr[]= {"s"};
	        try {
		         DocumentBuilderFactory dbFactory =  DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.newDocument();
		         
		         Element rootElement = doc.createElement("britannicaresources");
		         doc.appendChild(rootElement);
		         
		         //System.out.println("\n"+s);
		         for(int j=1; j<=arr.length; j++) {
		        	 
		              	 System.out.println(j);
		        	 
		        	 Element root = doc.createElement("britannicaresource");
		 
		        	 root.setAttribute("identifier", "RES-"+j);
		        	 root.setAttribute("type", "webcontent");
		        	// root.setAttribute("adlcp:scorm", "sco");
		        	 root.setAttribute("href", ""+j);
		        	 
		        	 Element files=doc.createElement("file");
	                 files.setAttribute("href", ""+j);
	                 root.appendChild(files);
	                 
	                 rootElement.appendChild(root);
	                 
	                 Element rr = doc.getDocumentElement();
	                 rr.insertBefore(rootElement, rr.getNextSibling());
	                 rr.normalize();
		         }
		        
		         
		         TransformerFactory transformerFactory = TransformerFactory.newInstance();
		         Transformer transformer = transformerFactory.newTransformer();
		         DOMSource source = new DOMSource(doc);
		         StreamResult result = new StreamResult(new File("F:\\Eclipse\\cars.xml"));
		         transformer.transform(source, result);
		         
		         // Output to console for testing
		         StreamResult consoleResult = new StreamResult(System.out);
		         transformer.transform(source, consoleResult);
		      } catch (Exception e) {
		         e.printStackTrace();
		      }    
	        

	}

}
