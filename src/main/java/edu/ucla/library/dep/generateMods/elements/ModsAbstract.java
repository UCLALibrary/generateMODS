package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsAbstract {

	public void createAbstract(CsvBean csvbean, Element rootElement) {
		if (null != csvbean.getAbstracts() && !csvbean.getAbstracts().isEmpty()) {

            MultiValuedMap<String, String> abstracts = csvbean.getAbstracts();
            MultiSet<String> columnames = abstracts.keys();
            for (String columnname : columnames) {
                String[] titleCol = columnname.split(Constants.regex);
                if (abstracts.get(columnname).iterator().next().trim().length() > 0) {
                    Element childDescription = new Element("abstract", Constants.namespace);
                    if (titleCol.length > 1) {
                        childDescription.setAttribute("lang", titleCol[1].trim());
                    }
                    childDescription.addContent(abstracts.get(columnname).iterator().next());
                    rootElement.addContent(childDescription);

                }
            }

        }
	}
}
