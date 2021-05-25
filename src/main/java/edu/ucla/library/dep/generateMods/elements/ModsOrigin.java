package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.services.CreateBaseElement;
import edu.ucla.library.dep.generateMods.services.ProcessCSV;
import edu.ucla.library.dep.generateMods.services.ProcessCsvImpl;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsOrigin {
	 ProcessCSV processcsv;
	public ModsOrigin() {
		 
		
		processcsv = new ProcessCsvImpl();
	 }
	
	public void createOrigin(CsvBean csvbean, Element rootElement) {
		
		Element childOriginInfo = new Element("originInfo", Constants.namespace);
		
		processcsv.createDateElement(childOriginInfo, Constants.namespace, csvbean);
		
		if (null != csvbean.getPublishers() && !csvbean.getPublishers().isEmpty()) {
			 
            MultiValuedMap<String, String> publishers = csvbean.getPublishers();
            MultiSet<String> columnames = publishers.keys();
            for (String columnname : columnames) {
                String[] pubCol = columnname.split(Constants.regexDot);
                if (publishers.get(columnname).iterator().next().trim().length() > 0) {
                    if (pubCol.length == 1) {
                        Element childPublisher = new Element("publisher", Constants.namespace);
                        childPublisher.addContent(publishers.get(columnname).iterator().next());
                        childOriginInfo.addContent(childPublisher);

                    }

                    if (pubCol.length > 1) {
                        if (publishers.get(columnname).iterator().next().trim().length() > 0) {
                            Element childPlace = new Element(pubCol[1], Constants.namespace);
                            Element childPlaceTerm = new Element("placeTerm", Constants.namespace);
                            childPlaceTerm.addContent(publishers.get(columnname).iterator().next());
                            childPlace.addContent(childPlaceTerm);
                            childOriginInfo.addContent(childPlace);
                        }

                    }
                }
            }

        }
		
		 if (null != childOriginInfo.getChildren() && childOriginInfo.getChildren().size() > 0) {
             rootElement.addContent(childOriginInfo);
         }
	}

}
