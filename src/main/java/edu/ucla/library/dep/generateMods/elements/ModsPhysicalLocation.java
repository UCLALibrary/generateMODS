package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.services.CreateBaseElement;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsPhysicalLocation {
	CsvBean csvbean;
	 
	 CreateBaseElement baseElement;
	 
	 public ModsPhysicalLocation(CsvBean csvbean) {
		 
		 this.csvbean = csvbean;
		 baseElement = new CreateBaseElement();
	 }
	 
	
	
	public void createPhysicalLocationElements(Element rootElement) {
		Element childLocation = new Element("location", Constants.namespace); 
		
		if (null != csvbean.getInstitution_repository()) {
            MultiValuedMap<String, String> repos = csvbean.getInstitution_repository();
            MultiSet<String> columnames = repos.keys();
            for (String colName : columnames) {
                baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                        repos.get(colName).iterator().next(), "repository", "Repository", null, null, null, null);

            }

        }
		
		if (null != csvbean.getCollectionName()) {
            MultiValuedMap<String, String> collectionNames = csvbean.getCollectionName();
            MultiSet<String> columnames = collectionNames.keys();
            for (String colName : columnames) {
                baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                        collectionNames.get(colName).iterator().next(), "collection", "Repository Collection", null,
                        null, null, null);
            }

        }

        if (null != csvbean.getCollectionNumber()) {
            MultiValuedMap<String, String> collectionNumbers = csvbean.getCollectionNumber();
            MultiSet<String> columnames = collectionNumbers.keys();
            for (String colName : columnames) {
                baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                        collectionNumbers.get(colName).iterator().next(), "collectionNumber", "Collection Number",
                        null, null, null, null);
            }

        }

        if (null != csvbean.getBox()) {
            MultiValuedMap<String, String> boxes = csvbean.getBox();
            MultiSet<String> columnames = boxes.keys();
            for (String colName : columnames) {
                baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                        boxes.get(colName).iterator().next(), "boxNumber", "Box Number", null, null, null, null);
            }

        }

        if (null != csvbean.getFolder()) {
            MultiValuedMap<String, String> folders = csvbean.getFolder();
            MultiSet<String> columnames = folders.keys();
            for (String colName : columnames) {
                baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                        folders.get(colName).iterator().next(), "folderNumber", "Folder Number", null, null, null,
                        null);
            }

        }
        
        if (null != childLocation.getChildren() && childLocation.getChildren().size() > 0) {
            rootElement.addContent(childLocation);
        }
	}

}
