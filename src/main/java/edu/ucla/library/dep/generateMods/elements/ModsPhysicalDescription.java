package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsPhysicalDescription {
	
	public void createPhyicalDescription(CsvBean csvbean, Element rootElement) {
		if (null != csvbean.getPhysicalDescription() && !csvbean.getPhysicalDescription().isEmpty()) {

            MultiValuedMap<String, String> physDescs = csvbean.getPhysicalDescription();
            MultiSet<String> columnames = physDescs.keys();
            Element extent = null;
            for (String columnname : columnames) {
                String[] phyDesCol = columnname.split(Constants.regex);
                if (physDescs.get(columnname).iterator().next().trim().length() > 0) {
                    Element childPhysicalDescription = new Element("physicalDescription", Constants.namespace);

                    if (phyDesCol[0].split(Constants.regexDot).length > 1) {
                        String[] element = phyDesCol[0].split(Constants.regexDot);
                        extent = new Element(element[1], Constants.namespace);
                    } else {
                        extent = new Element(phyDesCol[0], Constants.namespace);
                    }

                    extent.addContent(physDescs.get(columnname).iterator().next());
                    // add type from phyDescCol[1] to displayLabel = "type" //11-05-2020 not
                    // required
                    childPhysicalDescription.addContent(extent);
                    rootElement.addContent(childPhysicalDescription);

                }

            }
        }
	}

}
