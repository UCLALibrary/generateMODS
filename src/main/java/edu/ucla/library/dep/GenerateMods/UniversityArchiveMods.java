/**
 * 
 */
package edu.ucla.library.dep.GenerateMods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author parinita ghorpade
 *
 */
public class UniversityArchiveMods {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
    	Reader in = new FileReader("\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\ua_batch_2\\ua_batch2.csv");
    	
    	Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
    	Namespace namespace = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
    	Namespace namespacexlink = Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink");
    	Namespace namespacexsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    	String delim = "|";
    	String regex = "(?<!\\\\)" + Pattern.quote(delim);
    	Set<String> fileID = new HashSet<>();
    	try (
          		
          		BufferedWriter writer = Files.newBufferedWriter(Paths.get("duplucateFileNames.txt"));
          		) 
          {
    		
    		
	    	for (CSVRecord record : records) {
    		
    		
//	    		 if(fileID.contains(record.get("file name"))) {
//	    			 
//	    			 System.out.println("Duplicate Found : "+record.get("file name"));
//					 writer.append(record.get("file name"), 0 ,record.get("file name").length());
//					 writer.newLine();
//					   
//				  }else {
//					  
//					   
//					 fileID.add(record.get("file name"));
//				   }
		    		Document jdomDoc = new Document();
		    		// create root element
		    		Element rootElement = new Element("mods",namespace);
		    		rootElement.addNamespaceDeclaration(namespacexlink);
		    		rootElement.addNamespaceDeclaration(namespacexsi);
		    		rootElement.setAttribute("schemaLocation", "http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd", namespacexsi);
		    		
		    		
		        	
		        	
		    		
		    		// Create English, Armenian and Russian titles
		    		if(record.isSet("Title (English)")){
		    			if(!record.get("Title (English)").isEmpty()) {
		    				Element childTitleInfo = new Element("titleInfo", namespace);
		            		Element childTitle = new Element("title", namespace);
		            		childTitle.addContent(record.get("Title (English)"));
		            		//childTitle.setAttribute("lang","eng");
		            		
		            		childTitleInfo.addContent(childTitle);
		            		rootElement.addContent(childTitleInfo);
		    			}
		    			
		        		
		    		}
		    		
		    		
		    		// Type.typeOfResource	<mods:typeOfResource>
		    		if(record.isSet("type.typeOfResource")){
		    			if(!record.get("type.typeOfResource").isEmpty()) {
		    				Element childTypeOfResource = new Element("typeOfResource", namespace);
		        			childTypeOfResource.addContent(record.get("type.typeOfResource"));
		            		rootElement.addContent(childTypeOfResource);
		    			}
		    			
		        		
		    		}
		    		
		    		//Type.genre	<mods:genre>
		    		
		    		if(record.isSet("Type.genre")){
		    			if(!record.get("Type.genre").isEmpty()) {
		    				for(String genre : record.get("Type.genre").split(regex))
		        			{
		        				Element childTypeGenre = new Element("genre", namespace);
		            			childTypeGenre.addContent(genre);
		                		rootElement.addContent(childTypeGenre);
		        			}
		    			}
		    			
		        		
		    		}
		    		
		    		//PhysicalDescription	<mods:physicalDescription>
		    		if(record.isSet("physicalDescription.extent")){
		    			if(!record.get("physicalDescription.extent").isEmpty()) {
		    				Element childPhysicalDescription = new Element("physicalDescription", namespace);
		        			Element extent = new Element("extent",namespace);
		        			extent.addContent(record.get("physicalDescription.extent"));
		        			childPhysicalDescription.addContent(extent);
		            		rootElement.addContent(childPhysicalDescription);
		    			}
		    			
		        		
		    		}
		    		
		    		//Description.note	<mods:note lang="...">
		    		if(record.isSet("Description.note")){
		    			if(record.get("Description.note").length() > 0) {
		    				Element childDescriptionEnglish = new Element("note", namespace);
		        			//childDescriptionEnglish.setAttribute("lang", "eng");
		        			childDescriptionEnglish.addContent(record.get("Description.note"));
		            		rootElement.addContent(childDescriptionEnglish);
		    			}
		    			
		        		
		    		}
		    		
		    		
		    		
		    		//Description.inscription
		    		if(record.isSet("Description.inscription")){
		    			if(!record.get("Description.inscription").isEmpty()) {
		    				Element childDescriptionEnglish = new Element("note", namespace);    			
		        			childDescriptionEnglish.setAttribute("type", "inscription");
		        			childDescriptionEnglish.addContent(record.get("Description.inscription"));
		            		rootElement.addContent(childDescriptionEnglish);
		    			}
		    			
		        		
		    		}
		    		
		    		
		    		
		    		
		    		
		    		
		    		//Subject.topic 	<mods:subject lang="..."><mods:topic>
		    		if(record.isSet("Subject.topic (English)")){
		    			if(!record.get("Subject.topic (English)").isEmpty()) {
		    				Element childSubject = new Element("subject", namespace);
		        			//childSubject.setAttribute("lang", "eng");
		        			for( String topic : record.get("Subject.topic (English)").split(regex)) {
		        				Element childTopic = new Element("topic", namespace);
		                		childTopic.addContent(topic);
		                		childSubject.addContent(childTopic);
		        			}
		            		
		            		rootElement.addContent(childSubject);
		    			}
		    			
		        		
		    		}
		    		
		    		
		    		
		    		
		    		
		    		
		    		
		    		//Collection	<mods:relatedItem type="series" displayLabel="collection"><titleInfo>
		    		if(record.isSet("Collection")){
		    			if(!record.get("Collection").isEmpty()) {
		    				Element childRelatedItem = new Element("relatedItem", namespace);
		        			childRelatedItem.setAttribute("type", "series");
		        			childRelatedItem.setAttribute("displayLabel", "collection");
		            		Element childTitleInfo = new Element("titleInfo", namespace);
		            		Element childTitle = new Element("title", namespace);
		            		childTitle.addContent(record.get("Collection"));    		
		            		childTitleInfo.addContent(childTitle);
		            		childRelatedItem.addContent(childTitleInfo);
		            		rootElement.addContent(childRelatedItem);
		    			}
		    			
		    		}
		    		
		    		
		    		//Folder Name	<mods:relatedItem type="series" displayLabel="series"><titleInfo>
		    		if(record.isSet("relation.isPartOf") && !record.isSet("Folder Name")){
		    			if(!record.get("relation.isPartOf").isEmpty()) {
		    				Element childRelatedItem = new Element("relatedItem", namespace);
		        			childRelatedItem.setAttribute("type", "host");
		        			
		            		Element childTitleInfo = new Element("titleInfo", namespace);
		            		Element childTitle = new Element("title", namespace);
		            		childTitle.addContent(record.get("relation.isPartOf"));    		
		            		childTitleInfo.addContent(childTitle);
		            		childRelatedItem.addContent(childTitleInfo);
		            		rootElement.addContent(childRelatedItem);
		    			}
		    			
		    		}
		    		
		    		
		    		if(record.isSet("relation.isPartOf") && record.isSet("Folder Name")){
		    			if(!record.get("relation.isPartOf").isEmpty()) {
		    				Element childRelatedItem = new Element("relatedItem", namespace);
		        			childRelatedItem.setAttribute("type", "host");
		        			
		            		Element childTitleInfo = new Element("titleInfo", namespace);
		            		Element childTitle = new Element("title", namespace);
		            		childTitle.addContent(record.get("relation.isPartOf")+": "+record.get("Folder Name"));    		
		            		childTitleInfo.addContent(childTitle);
		            		childRelatedItem.addContent(childTitleInfo);
		            		rootElement.addContent(childRelatedItem);
		    			}
		    			
		    		}
		    		
		    		//Insitution/Repository	"<location><physicalLocation>"
		    		if(record.isSet("Institution/Reposity")){
		    			if(!record.get("Institution/Reposity").isEmpty()) {
		    				Element childLocation = new Element("location", namespace);
		        			Element childPhysicalLocation = new Element("physicalLocation", namespace);
		        			childPhysicalLocation.addContent(record.get("Institution/Reposity"));
		            		childLocation.addContent(childPhysicalLocation);
		            		rootElement.addContent(childLocation);
		    			}
		    			
		        		
		    		}
		    		
		    		
		    		//File name	<mods:identifier type="local">
		    		if(record.isSet("file name")){
		    			Element childIdentifier = new Element("identifier", namespace);
		    			childIdentifier.setAttribute("type", "local");
		    			childIdentifier.addContent(record.get("file name"));
		        		rootElement.addContent(childIdentifier);
		        		
		    		}
		    		
		    		
		    		
		    		/*
		    		 * "<accessCondition>
		   <copyright copyright.status=""copyrighted"" publication.status=""published"">
		      <services>
		         <contact>"
		
		
		    		 */
		    		
		    		if(record.isSet("Rights.copyrightStatus")){
		    			Element childAccessCondition = new Element("accessCondition", namespace);
		    			Element childCopyright = new Element("copyright", namespace);
		    			childCopyright.setAttribute("copyright.status",record.get("Rights.copyrightStatus"));
		    			if(record.isSet("Rights.publicationStatus")) {
		    				childCopyright.setAttribute("publication.status", record.get("Rights.publicationStatus"));
		    			}
		    			childCopyright.setAttribute("schemaLocation","http://www.cdlib.org/inside/diglib/copyrightMD http://www.cdlib.org/groups/rmg/docs/copyrightMD.xsd", namespacexsi);
		    			//childIdentifier.setAttribute("type", "local");
		    			if(record.isSet("Rights.servicesContact")) {
		    				Element services = new Element("services",namespace);
		    				Element contact = new Element("contact",namespace);
		    				contact.addContent(record.get("Rights.servicesContact"));
		    				services.addContent(contact);
		    				childCopyright.addContent(services);
		    			}
		    			childAccessCondition.addContent(childCopyright);
		        		rootElement.addContent(childAccessCondition);
		        		
		    		}
		    		Element childOriginInfo = new Element("originInfo", namespace);
		    		
		    		if(record.isSet("Date (single)")){
		    			if(!record.get("Date (single)").isEmpty()) {
							Element childDate= new Element("dateCreated", namespace);
							childDate.addContent(record.get("Date (single)"));
							childDate.setAttribute("encoding","iso8601");
							childOriginInfo.addContent(childDate);
		    			}
						
						
					}
		    		
		    		
					if(record.isSet("Date (start)")){
					    if(!record.get("Date (start)").isEmpty()) {
					    	Element childDate= new Element("dateCreated", namespace);
			        		childDate.addContent(record.get("Date (start)"));
			        		childDate.setAttribute("encoding","iso8601");
			        		childDate.setAttribute("point","start");
			        		childOriginInfo.addContent(childDate);
					    }
		        		
		        		
		        		
		    		}
		    		
		    		
					if(null != childOriginInfo.getChildren() && childOriginInfo.getChildren().size() > 0) {
		    			rootElement.addContent(childOriginInfo);
		    		}
		    		
		    		
		    		
		    		//System.out.println(record.get("File name"));
		    		//System.out.println(record.get("local ID"));
		    		jdomDoc.setRootElement(rootElement);
		    		// create XMLOutputter
					//XMLOutputter xml = new XMLOutputter();
					// we want to format the xml. This is used only for demonstration. pretty formatting adds extra spaces and is generally not required.
					/*xml.setFormat(Format.getPrettyFormat());
					System.out.println(xml.outputString(jdomDoc));*/
					
					
					   XMLOutputter xmlOutput = new XMLOutputter();  
					  
					   // passsed System.out to see document content on console  
					  // xmlOutput.output(jdomDoc, System.out);  
					  
					   // passed fileWriter to write content in specified file  
					   xmlOutput.setFormat(Format.getPrettyFormat());  
					   Path source = Paths.get("\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\ua_batch_2\\images\\"+record.get("file name")+".tif");
		        		Path sourcea = Paths.get("\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\ua_batch_2\\images\\"+record.get("file name")+"a.tif");
		        		if(Files.exists(source)) {
		        			xmlOutput.output(jdomDoc, new FileWriter(  
		   					     "\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\ua_batch_2\\mods\\"+record.get("file name").replaceFirst(".tif", "")+".xml"));
		        		}else if(Files.exists(sourcea)){
		        			xmlOutput.output(jdomDoc, new FileWriter(  
		   					     "\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\ua_batch_2\\mods\\"+record.get("file name").replaceFirst(".tif", "")+"a.xml"));
		        		}
					   
					  
				   
	    		
	    	}
          }catch (Exception e) {
			// TODO: handle exception
        	  System.out.println(e.getMessage());
  			  e.printStackTrace();
		}
    	
       // System.out.println( "Hello World!" );		

	}

}
