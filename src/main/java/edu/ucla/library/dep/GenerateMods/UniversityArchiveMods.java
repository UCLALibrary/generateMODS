/**
 * 
 */
package edu.ucla.library.dep.GenerateMods;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
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
		
    	Reader in = new FileReader("C:\\Users\\parinita ghorpade\\Downloads\\UniversityArchivesPart2.csv");
    	//Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("File name","local ID ","Collection","Series","Title (English)","Title (Armenian)","Title (Russian)","Creator (English)","Creator (Armenian)","Creator (Russian)","Contributor.publisher","Publisher.placeOfOrigin","date.normalized","Language","Type.typeOfResource","Type.genre","PhysicalDescription","Description.note (English)","Description.note (Armenian)","\"Description.inscription \"\"translated\"\"\"","Description.inscription","Subject.name (English)","Subject.name (Armenian)","Subject.topic (English)","Subject.topic (Armenian)","Subject.geographic (English)","Subject.geographic (Armenian)","Rights.copyrightStatus","Rights.publicationStatus","Rights.servicesContact","Insitution/Repository").parse(in);
    	Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
    	Namespace namespace = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
    	Namespace namespacexlink = Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink");
    	Namespace namespacexsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    	String delim = "|";
    	String regex = "(?<!\\\\)" + Pattern.quote(delim);
    	for (CSVRecord record : records) {
    		Document jdomDoc = new Document();
    		// create root element
    		Element rootElement = new Element("mods",namespace);
    		rootElement.addNamespaceDeclaration(namespacexlink);
    		rootElement.addNamespaceDeclaration(namespacexsi);
    		rootElement.setAttribute("schemaLocation", "http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd", namespacexsi);
    		
        	
        	
    		
    		// Create English, Armenian and Russian titles
    		if(record.isSet("Title (English)")){
    			Element childTitleInfo = new Element("titleInfo", namespace);
        		Element childTitle = new Element("title", namespace);
        		childTitle.addContent(record.get("Title (English)"));
        		//childTitle.setAttribute("lang","eng");
        		
        		childTitleInfo.addContent(childTitle);
        		rootElement.addContent(childTitleInfo);
        		
    		}
    		
    		
    		// Type.typeOfResource	<mods:typeOfResource>
    		if(record.isSet("type.typeOfResource")){
    			Element childTypeOfResource = new Element("typeOfResource", namespace);
    			childTypeOfResource.addContent(record.get("type.typeOfResource"));
        		rootElement.addContent(childTypeOfResource);
        		
    		}
    		
    		//Type.genre	<mods:genre>
    		
    		if(record.isSet("Type.genre")){
    			for(String genre : record.get("Type.genre").split(regex))
    			{
    				Element childTypeGenre = new Element("genre", namespace);
        			childTypeGenre.addContent(genre);
            		rootElement.addContent(childTypeGenre);
    			}
        		
    		}
    		
    		//PhysicalDescription	<mods:physicalDescription>
    		if(record.isSet("physicalDescription.extent")){
    			Element childPhysicalDescription = new Element("physicalDescription", namespace);
    			Element extent = new Element("extent",namespace);
    			extent.addContent(record.get("physicalDescription.extent"));
    			childPhysicalDescription.addContent(extent);
        		rootElement.addContent(childPhysicalDescription);
        		
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
    			Element childDescriptionEnglish = new Element("note", namespace);    			
    			childDescriptionEnglish.setAttribute("type", "inscription");
    			childDescriptionEnglish.addContent(record.get("Description.inscription"));
        		rootElement.addContent(childDescriptionEnglish);
        		
    		}
    		
    		
    		
    		
    		
    		
    		//Subject.topic 	<mods:subject lang="..."><mods:topic>
    		if(record.isSet("Subject.topic (English)")){
    			Element childSubject = new Element("subject", namespace);
    			//childSubject.setAttribute("lang", "eng");
    			for( String topic : record.get("Subject.topic (English)").split(regex)) {
    				Element childTopic = new Element("topic", namespace);
            		childTopic.addContent(topic);
            		childSubject.addContent(childTopic);
    			}
        		
        		rootElement.addContent(childSubject);
        		
    		}
    		
    		
    		
    		
    		
    		
    		
    		//Collection	<mods:relatedItem type="series" displayLabel="collection"><titleInfo>
    		if(record.isSet("Collection")){
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
    		
    		
    		//Folder Name	<mods:relatedItem type="series" displayLabel="series"><titleInfo>
    		if(record.isSet("relation.isPartOf")){
    			Element childRelatedItem = new Element("relatedItem", namespace);
    			childRelatedItem.setAttribute("type", "host");
    			
        		Element childTitleInfo = new Element("titleInfo", namespace);
        		Element childTitle = new Element("title", namespace);
        		childTitle.addContent(record.get("relation.isPartOf"));    		
        		childTitleInfo.addContent(childTitle);
        		childRelatedItem.addContent(childTitleInfo);
        		rootElement.addContent(childRelatedItem);
    		}
    		
    		//Insitution/Repository	"<location><physicalLocation>"
    		if(record.isSet("Institution/Reposity")){
    			Element childLocation = new Element("location", namespace);
    			Element childPhysicalLocation = new Element("physicalLocation", namespace);
    			childPhysicalLocation.addContent(record.get("Institution/Reposity"));
        		childLocation.addContent(childPhysicalLocation);
        		rootElement.addContent(childLocation);
        		
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
			   xmlOutput.output(jdomDoc, new FileWriter(  
			     "\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\universityarchives\\mods\\"+record.get("file name").replaceFirst("tif", "xml")));  
    		
    	}
    	
       // System.out.println( "Hello World!" );		

	}

}
