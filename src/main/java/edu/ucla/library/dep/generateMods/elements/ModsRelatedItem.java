package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.services.ProcessCSV;
import edu.ucla.library.dep.generateMods.services.ProcessCsvImpl;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsRelatedItem {
	
	public void createRelatedItems(Element rootElement, String projectName, String projectURL, String program, CsvBean csvbean) {
		ProcessCSV processcsv = new ProcessCsvImpl();
		
		 // Add Project values
        processcsv.createProjectElement(rootElement, Constants.namespace, "host", projectName, projectURL);

        
        // Add collection column
        if (null != csvbean.getCollection()) {
            MultiValuedMap<String, String> collections = csvbean.getCollection();
            MultiSet<String> columnames = collections.keys();
            for (String colName : columnames) {
                if (collections.get(colName).iterator().next().trim().length() > 0) {
                    for (String collection : collections.get(colName).iterator().next().split(Constants.regex)) {
                        processcsv.createRelatedItemElement(rootElement, Constants.namespace, "host", collection);

                    }
                }
            }
        }
        if (null != csvbean.getRelated_resource()) {
            processcsv.createRelatedItemElement(rootElement, Constants.namespace, null, csvbean.getRelated_resource());
        }

        // add program for cross collection search

        if ("IDEP".equals(program)) {
            processcsv.createRelatedItemElement(rootElement, Constants.namespace, "program", "International Digital Ephemera Project");
        } else {
            processcsv.createRelatedItemElement(rootElement, Constants.namespace, "program", "Modern Endangered Archives Program");
        }

        if (null != csvbean.getSeries() && !csvbean.getSeries().isEmpty()) {
        	for (String series : csvbean.getSeries().split(Constants.regex)) {
        		processcsv.createRelatedItemElement(rootElement, Constants.namespace, "series", series);
        	}
        }

        if (null != csvbean.getSubSeries() && !csvbean.getSubSeries().isEmpty()) {
            processcsv.createRelatedItemElement(rootElement, Constants.namespace, "subseries", csvbean.getSubSeries());
        }
	}

}
