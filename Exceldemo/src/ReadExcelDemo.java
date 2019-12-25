import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class ReadExcelDemo 
{
	static String[] p;
	
	public ReadExcelDemo() {
		
	}
	
	public void displayFromExcel(String xlsxPath) {
		
		InputStream  inputStream = null;
		
		try {
			
			inputStream = new FileInputStream(xlsxPath);
			if(!inputStream.markSupported()) {
				inputStream = new PushbackInputStream(inputStream, 8);
				
				if (POIFSFileSystem.hasPOIFSHeader(inputStream)) {
		            POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		            
		        }
			}
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		//POIFSFileSystem fs = null;
		
		
		try
		{
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document document = builder.newDocument();
	        Element rootElement = document.createElement("manifest");
	        
	        
	        rootElement.setAttribute("identifier", "MANIFEST_IDENTIFIER");
	        rootElement.setAttribute("version", "1.0");
	        rootElement.setAttribute("xmlns", "http://www.imsglobal.org/xsd/imscp_v1p1");
	        rootElement.setAttribute("xmlns:adlcp", "http://www.adlnet.org/xsd/adlcp_v1p3");
	        rootElement.setAttribute("xmlns:adlnav", "http://www.adlnet.org/xsd/adlnav_v1p3");
	        rootElement.setAttribute("xmlns:adlseq", "http://www.adlnet.org/xsd/adlseq_v1p3");
	        rootElement.setAttribute("xmlns:imsss", "http://www.imsglobal.org/xsd/imsss");
	        rootElement.setAttribute("xmlns:lom", "http://ltsc.ieee.org/xsd/LOM");
	        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
	        rootElement.setAttribute("xsi:schemaLocation", "http://www.imsglobal.org/xsd/imscp_v1p1 imscp_v1p1.xsd" +
"                        http://www.adlnet.org/xsd/adlcp_v1p3 adlcp_v1p3.xsd" +
"                        http://www.adlnet.org/xsd/adlnav_v1p3 adlnav_v1p3.xsd" +
"                        http://www.adlnet.org/xsd/adlseq_v1p3 adlseq_v1p3.xsd" +
"                        http://www.imsglobal.org/xsd/imsss imsss_v1p0.xsd" +
"                        http://ltsc.ieee.org/xsd/LOM lom.xsd");
	        
	        
	        
	        Element p1=document.createElement("schema");
	           Element p2=document.createElement("schemaversion");
	           
	           Text a1 = document.createTextNode("ADL SCORM");
	           Text a2 = document.createTextNode("2004 3rd Edition");
	           
	           
	           p1.appendChild(a1);
	           p2.appendChild(a2);
	           
	           Node j1 = document.createElement("metadata");
	           j1.appendChild(p1);
	           j1.appendChild(p2);
	           rootElement.appendChild(j1);
	        
	        
	        Element organizations=document.createElement("organizations");
            organizations.setAttribute("default", "ORG-210529");
            rootElement.appendChild(organizations);
             
           Element organization=document.createElement("organization");
           organization.setAttribute("identifier", "ORG-210529");
           organization.setAttribute("structure", "hierarchical");
           organizations.appendChild(organization);
	        
           Element title1 = document.createElement("title");
           title1.appendChild(document.createTextNode("index"));
           organization.appendChild(title1);
           
           
           
           /*Element r1 = document.getDocumentElement();
           r1.insertBefore(j1,r1.getFirstChild());
           r1.normalize();*/
	        
	        document.appendChild(rootElement);
	        
	       // fs = new POIFSFileSystem(inputStream);
	        
		//	FileInputStream file = new FileInputStream(new File("readexcel.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<?> rowIterator = sheet.rowIterator();
			
			ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			
			while (rowIterator.hasNext()) 
			{
				XSSFRow row = (XSSFRow) rowIterator.next();
				int rowNumber = row.getRowNum ();
				
				 System.out.println ("Row No.: " + rowNumber);
				 
				//For each row, iterate through all the columns
				 
				Iterator<?> cells = row.cellIterator();
				
				ArrayList<String> rowData = new ArrayList<String>();
				
				while (cells.hasNext()) 
				{
					XSSFCell cell = (XSSFCell) cells.next();
					//Check the cell type and format accordingly
					switch (cell.getCellType()) 
					{
						case Cell.CELL_TYPE_NUMERIC:
						{
							System.out.println ("Numeric: " + cell.getNumericCellValue ());
		                    rowData.add(cell.getNumericCellValue () + "");
		                    break;
						}
						case Cell.CELL_TYPE_STRING:
							
						{
		                    // STRING CELL TYPE
		                    XSSFRichTextString richTextString = cell.getRichStringCellValue ();

		                    System.out.println ("String: " + richTextString.getString ());
		                    rowData.add(richTextString.getString ());
		                    break;
		                }default:
						{
		                    // types other than String and Numeric.
		                    System.out.println ("Type not supported.");
		                    break;
		                }
					}
					
				}
				data.add(rowData);
				
			}
			
		
		 int numOfProduct = data.size();
		 
		 System.out.println("Hello :"+numOfProduct);

	       /* for (int i = 1; i < numOfProduct; i++){
	            Element productElement = document.createElement("data");
	            
	            rootElement.appendChild(productElement);

	            int index = 0;
	            for(String s: data.get(i)) {
	                String headerString = data.get(0).get(index);
	                if( data.get(0).get(index).equals("item") ){
	                    headerString = "item";
	                }

	                if( data.get(0).get(index).equals("title") ){
	                    headerString = "title";
	                }

	                Element headerElement = document.createElement(headerString);
	                productElement.appendChild(headerElement);
	                headerElement.appendChild(document.createTextNode(s));
	                index++;
	            }
	        }*/
		 
		 
		 
		 String str="<!DOCTYPE html>\r\n" + 
		 		"<html lang=\"en\">\r\n" + 
		 		"	<head>\r\n" + 
		 		"		<meta charset=\"UTF-8\">\r\n" + 
		 		"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
		 		"		<script src=\"../js/jquery.js\"></script>\r\n" + 
		 		"		<!--script src=\"../libs/js/jquery.js\"></script><script type=\"text/javascript\" src=\"dis.js\"></script-->\r\n" + 
		 		"		       <script type=\"text/javascript\" src=\"../js/validation.js\"></script>\r\n" + 
		 		"				\r\n" + 
		 		"                \r\n" + 
		 		"\r\n" + 
		 		"\r\n" + 
		 		"		\r\n" + 
		 		"		\r\n" + 
		 		"		<title>MathSight Book 3</title>\r\n" + 
		 		"		<style>\r\n" + 
		 		"			#myVideo{\r\n" + 
		 		"			opacity:1;\r\n" + 
		 		"			height: 99vh !important;\r\n" + 
		 		"			}\r\n" + 
		 		"		</style>\r\n" + 
		 		"		<script>\r\n" + 
		 		"  window.oncontextmenu = function() {\r\n" + 
		 		"    return false;\r\n" + 
		 		"  } </script>\r\n" + 
		 		"	</head>\r\n" + 
		 		"\r\n" + 
		 		"	<body  style=\"padding:0;margin:0;background-color:#99CCFF;\"> \r\n" + 
		 		"		<div align=\"center\">\r\n" + 
		 		"         <video id=\"myVideo\" controls>\r\n" + 
		 		"		  	 <source src=\"class.webm\" type=\"video/webm\">\r\n" + 
		 		"		Your browser does not support the video tag.\r\n" + 
		 		"		</video> \r\n" + 
		 		"		</div>\r\n" + 
		 		"		<script>\r\n" + 
		 		"		$(document).ready(function(){\r\n" + 
		 		"		var vid = document.getElementById(\"myVideo\");\r\n" + 
		 		"               vid.onloadeddata = function() {\r\n" + 
		 		"               	$(\"#myVideo\").animate({'opacity':'1'},500,function(){});\r\n" + 
		 		"                 };\r\n" + 
		 		"		\r\n" + 
		 		"			$(window).resize(function(){\r\n" + 
		 		"				//$(\"#myVideo\").css('max-height',($(window).height() - 30)+'px');\r\n" + 
		 		"				//$(\"#myVideo\").height(($(window).height() - 5));\r\n" + 
		 		"				$(\"#myVideo\").width(($(window).width()));\r\n" + 
		 		"				});\r\n" + 
		 		"			\r\n" + 
		 		"			$(window).load(function(){				\r\n" + 
		 		"				\r\n" + 
		 		"				$(\"#myVideo\").css('max-height','801px');\r\n" + 
		 		"								$(\"#myVideo\").height(($(window).height() - 5));\r\n" + 
		 		"								$(\"#myVideo\").width(($(window).width()));\r\n" + 
		 		"				});\r\n" + 
		 		"				\r\n" + 
		 		"				\r\n" + 
		 		"			});\r\n" + 
		 		"			\r\n" + 
		 		"		</script>\r\n" + 
		 		"			<script src=\"newjavascript.js\"></script>\r\n" + 
		 		"	</body>\r\n" + 
		 		"</html>\r\n" + 
		 		"";
			
			
			String str1="(function () {\r\n" + 
					"    var str=\"data:video/webm;\";\r\n" + 
					"    var bytes = new Uint8Array(str.length);\r\n" + 
					"for (var i=0; i<str.length; i++)\r\n" + 
					"{\r\n" + 
					"    bytes[i] = str.charCodeAt(i);\r\n" + 
					"    }\r\n" + 
					"    \r\n" + 
					"console.log(bytes);\r\n" + 
					"\r\n" + 
					"  localStorage.removeItem(\"myVideo\");  \r\n" + 
					"    // localStorage with image\r\n" + 
					"    \r\n" + 
					"    /*\r\n" + 
					"    var storageFiles = JSON.parse(localStorage.getItem(\"storageFiles\")) || {},\r\n" + 
					"        elephant = document.getElementById(\"elephant\"),\r\n" + 
					"        storageFilesDate = storageFiles.date,\r\n" + 
					"        date = new Date(),\r\n" + 
					"        todaysDate = (date.getMonth() + 1).toString() + date.getDate().toString();\r\n" + 
					"\r\n" + 
					"    // Compare date and create localStorage if it's not existing/too old   \r\n" + 
					"    if (typeof storageFilesDate === \"undefined\" || storageFilesDate < todaysDate) {\r\n" + 
					"        // Take action when the image has loaded\r\n" + 
					"        elephant.addEventListener(\"load\", function () {\r\n" + 
					"            var imgCanvas = document.createElement(\"canvas\"),\r\n" + 
					"                imgContext = imgCanvas.getContext(\"2d\");\r\n" + 
					"\r\n" + 
					"            // Make sure canvas is as big as the picture\r\n" + 
					"            imgCanvas.width = elephant.width;\r\n" + 
					"            imgCanvas.height = elephant.height;\r\n" + 
					"\r\n" + 
					"            // Draw image into canvas element\r\n" + 
					"            imgContext.drawImage(elephant, 0, 0, elephant.width, elephant.height);\r\n" + 
					"\r\n" + 
					"            // Save image as a data URL\r\n" + 
					"            storageFiles.elephant = imgCanvas.toDataURL(\"video/webm\");\r\n" + 
					"\r\n" + 
					"            // Set date for localStorage\r\n" + 
					"            storageFiles.date = todaysDate;\r\n" + 
					"\r\n" + 
					"            // Save as JSON in localStorage\r\n" + 
					"            try {\r\n" + 
					"                localStorage.setItem(\"storageFiles\", JSON.stringify(storageFiles));\r\n" + 
					"            }\r\n" + 
					"            catch (e) {\r\n" + 
					"                    console.log(\"Storage failed: \" + e);                \r\n" + 
					"            }\r\n" + 
					"        }, false);\r\n" + 
					"\r\n" + 
					"        // Set initial image src    \r\n" + 
					"        elephant.setAttribute(\"src\", \"large.webm\");\r\n" + 
					"    }\r\n" + 
					"    else {\r\n" + 
					"        // Use image from localStorage\r\n" + 
					"        elephant.setAttribute(\"src\", storageFiles.elephant);\r\n" + 
					"    }  */\r\n" + 
					"\r\n" + 
					"    // Getting a file through XMLHttpRequest as an arraybuffer and creating a Blob\r\n" + 
					"    var rhinoStorage = localStorage.getItem(\"myVideo\"),\r\n" + 
					"        rhino = document.getElementById(\"myVideo\");\r\n" + 
					"    if (rhinoStorage) {\r\n" + 
					"        // Reuse existing Data URL from localStorage\r\n" + 
					"        rhino.setAttribute(\"src\", rhinoStorage);\r\n" + 
					"    }\r\n" + 
					"    else {\r\n" + 
					"        // Create XHR, BlobBuilder and FileReader objects\r\n" + 
					"        var xhr = new XMLHttpRequest(),\r\n" + 
					"            blob,\r\n" + 
					"            fileReader = new FileReader();\r\n" + 
					"\r\n" + 
					"        xhr.open(\"GET\", \"class.webm\", true);\r\n" + 
					"        // Set the responseType to arraybuffer. \"blob\" is an option too, rendering BlobBuilder unnecessary, but the support for \"blob\" is not widespread enough yet\r\n" + 
					"        xhr.responseType = \"arraybuffer\";\r\n" + 
					"\r\n" + 
					"        xhr.addEventListener(\"load\", function () {\r\n" + 
					"            if (xhr.status === 200) {\r\n" + 
					"                // Create a blob from the response\r\n" + 
					"                blob = new Blob([xhr.response], {type: \"video/webm\"});\r\n" + 
					"\r\n" + 
					"                // onload needed since Google Chrome doesn't support addEventListener for FileReader\r\n" + 
					"                fileReader.onload = function (evt) {\r\n" + 
					"                    // Read out file contents as a Data URL\r\n" + 
					"                    var result = evt.target.result;\r\n" + 
					"                   //////\r\n" + 
					"                   // var res = str.concat(result);\r\n" + 
					"                     //console.log(res);  \r\n" + 
					"                     console.log(result);   \r\n" + 
					"                   // var n = res.length;\r\n" + 
					"//result = result.split(\"mayank\").pop();\r\n" + 
					"result = result.split(\"RFYwODEwSktP\").pop();\r\n" + 
					"//var res=result;\r\n" + 
					"           // console.log(result);\r\n" + 
					"var res = str.concat(result);\r\n" + 
					"                    // console.log(res); \r\n" + 
					"            //console.log(res);        \r\n" + 
					"                    // Set image src to Data URL\r\n" + 
					"                    //console.log(result);\r\n" + 
					"                    rhino.setAttribute(\"src\", res);\r\n" + 
					"                    // Store Data URL in localStorage\r\n" + 
					"                    try {\r\n" + 
					"                        localStorage.setItem(\"myVideo\", res);\r\n" + 
					"                    }\r\n" + 
					"                    catch (e) {\r\n" + 
					"                        console.log(\"Storage failed: \" + e);\r\n" + 
					"                    }\r\n" + 
					"                };\r\n" + 
					"                // Load blob as Data URL\r\n" + 
					"                fileReader.readAsDataURL(blob);\r\n" + 
					"            }\r\n" + 
					"        }, false);\r\n" + 
					"        // Send XHR\r\n" + 
					"        xhr.send();\r\n" + 
					"    }\r\n" + 
					"\r\n" + 
					"})();\r\n" + 
					"";
		 
		 
		 
		 Element j= document.createElement("britannicaresources");
		 for(int i=1; i<numOfProduct; i++) {
			 
			 try {
					//Files.createDirectories(Paths.get("C:/new/" + folderName));
				 
					Files.createDirectories(Paths.get("C://Module//" + i));
					
					String path="C://Module//" +i +"//index.html";
					String path1="C://Module//" +i +"//newjavascript.js";
					
					Files.write( Paths.get(path), str.getBytes(), StandardOpenOption.CREATE);
					Files.write( Paths.get(path1), str1.getBytes(), StandardOpenOption.CREATE);
				
					
				}catch(FileAlreadyExistsException e){
		            System.err.println("Folder already exists");
		        }
			 
			 
			 
			 
			 Element item=document.createElement("item");
			 
			 item.setAttribute("identifier", "Ack-"+i);
             item.setAttribute("identifierref", "RES-"+i);
             
            // Element titlew = document.createElement("title");
             int index = 0;
	            for(String s: data.get(i)) {
	                String headerString = data.get(0).get(index);
	                if( data.get(0).get(index).equals("item") ){
	                    headerString = "item";
	                }

	                if( data.get(0).get(index).equals("title") ){
	                    headerString = "title";
	                }

	                Element headerElement = document.createElement(headerString);
	                item.appendChild(headerElement);
	                headerElement.appendChild(document.createTextNode(s));
	                index++;
	            }
             
             organization.appendChild(item);
             
            // Element j= document.createElement("britannicaresources");
             
             Element res=document.createElement("britannicaresource");
             res.setAttribute("identifier", "RES-"+i);
             res.setAttribute("type", "webcontent");
             res.setAttribute("adlcp:scorm", "sco");
             
             //creating Interactive Exercises SCORM
             
             res.setAttribute("subject_status", "0");
             res.setAttribute("chpt", "0");
             res.setAttribute("subject_subtopic", "1");
             res.setAttribute("subject", "Chapter");
             
             res.setAttribute("href", "Grade7/Module/"+i +"/index.html");
             
             
             Element file=document.createElement("file");
             file.setAttribute("href", "Grade7/Module/"+i +"/index.html");
             res.appendChild(file);
             j.appendChild(res);
            // rootElement.appendChild(j);
             Element r = document.getDocumentElement();
             r.insertBefore(j, r.getNextSibling());
             r.normalize();
		 }
		 
		 
		TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer = tFactory.newTransformer();
        //Add indentation to output
       
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("F:\\imsmanifest.xml"));
        //StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
        System.out.println("Done");
    }
    catch(IOException e)
    {
        System.out.println("IOException " + e.getMessage());
    } catch (ParserConfigurationException e) {
        System.out.println("ParserConfigurationException " + e.getMessage());
    } catch (TransformerConfigurationException e) {
        System.out.println("TransformerConfigurationException "+ e.getMessage());
    } catch (TransformerException e) {
        System.out.println("TransformerException " + e.getMessage());
    }}
	
public static void main(String[] args) 
{
	ReadExcelDemo poi = new ReadExcelDemo();
	String xlsxPath = "Book1.xlsx";
	poi.displayFromExcel(xlsxPath);
}
}