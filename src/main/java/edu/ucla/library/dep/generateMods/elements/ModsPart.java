package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsPart {

	public void createPart(Element rootElement, CsvBean csvbean) {
		if (null != csvbean.getVolumesOrIssues() && !csvbean.getVolumesOrIssues().isEmpty()) {

            MultiValuedMap<String, String> volumes = csvbean.getVolumesOrIssues();
            MultiSet<String> columnames = volumes.keys();
            for (String columnname : columnames) {
                String[] volCol = columnname.split(Constants.regex);
                if (volumes.get(columnname).iterator().next().trim().length() > 0) {
                    Element childPart = new Element("part", Constants.namespace);
                    String[] values = volumes.get(columnname).iterator().next().split(Constants.regex);

                    for (int i = 0; i < values.length; i++) {
                        if ("date".equals(volCol[i].trim())) {
                            Element childPartDate = new Element("Date", Constants.namespace);
                            childPartDate.addContent(values[i].trim());
                            childPart.addContent(childPartDate);
                            continue;
                        }

                        Element childPartDetail = new Element("detail", Constants.namespace);
                        childPartDetail.setAttribute("type", volCol[i].trim());
                        Element childNumber = new Element("number", Constants.namespace);
                        childNumber.addContent(values[i].trim());
                        childPartDetail.addContent(childNumber);
                        childPart.addContent(childPartDetail);

                    }

                    rootElement.addContent(childPart);
                }
            }

        }
	}
}
