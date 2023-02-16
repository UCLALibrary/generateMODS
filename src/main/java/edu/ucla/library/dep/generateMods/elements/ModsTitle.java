package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsTitle {
	
	public void createModsTitle(CsvBean csvbean, Element rootElement) {
		
		if (null != csvbean.getTitles() && !csvbean.getTitles().isEmpty()) {

            MultiValuedMap<String, String> titles = csvbean.getTitles();
            MultiSet<String> columnames = titles.keys();

            for (String columnname : columnames) {
                if (titles.get(columnname).iterator().next().trim().length() > 0) {
                    String[] titleCol = columnname.split(Constants.regex);
                    Element childTitleInfo = new Element("titleInfo", Constants.namespace);
                    Element childTitle = new Element("title", Constants.namespace);
                    childTitle.addContent(titles.get(columnname).iterator().next());
                    if (titleCol.length > 1) {
                        childTitle.setAttribute("lang", titleCol[1].trim());
                    }
                    childTitleInfo.addContent(childTitle);
                    rootElement.addContent(childTitleInfo);
                }

            }

        }

        if (null != csvbean.getAlt_titles() && !csvbean.getAlt_titles().isEmpty()) {

            MultiValuedMap<String, String> altTitles = csvbean.getAlt_titles();
            MultiSet<String> columnames = altTitles.keys();

            for (String columnname : columnames) {
                if (altTitles.get(columnname).iterator().next().trim().length() > 0) {
                    String[] titleCol = columnname.split(Constants.regex);
                    
                    for (String altTitle : altTitles.get(columnname).iterator().next().split(Constants.regex)) {
                        Element childTitleInfo = new Element("titleInfo", Constants.namespace);
                    	Element childTitle = new Element("title", Constants.namespace);
                    	childTitle.addContent(altTitle);
                    	if (titleCol.length > 1) {
                    		childTitle.setAttribute("lang", titleCol[1].trim());
                    	}
                    	childTitle.setAttribute("type", "alternative");
                    	childTitleInfo.addContent(childTitle);
                    	rootElement.addContent(childTitleInfo);
                    	
                    }
                    
                }

            }

        }

        if (null != csvbean.getTranslated_title() && !csvbean.getTranslated_title().isEmpty()) {

            MultiValuedMap<String, String> titles = csvbean.getTranslated_title();
            MultiSet<String> columnames = titles.keys();

            for (String columnname : columnames) {
                if (titles.get(columnname).iterator().next().trim().length() > 0) {
                    String[] titleCol = columnname.split(Constants.regex);
                    Element childTitleInfo = new Element("titleInfo", Constants.namespace);
                    Element childTitle = new Element("title", Constants.namespace);
                    childTitle.addContent(titles.get(columnname).iterator().next());
                    childTitle.setAttribute("type", "translated");
                    if (titleCol.length > 1) {
                        childTitle.setAttribute("lang", titleCol[1].trim());
                    }
                    childTitleInfo.addContent(childTitle);
                    rootElement.addContent(childTitleInfo);
                }

            }

        }
	}

}
