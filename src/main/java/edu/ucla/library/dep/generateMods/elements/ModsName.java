package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsName {

	public void createNames(CsvBean csvbean, Element rootElement) {
		if (null != csvbean.getCreators() && !csvbean.getCreators().isEmpty()) {
            MultiValuedMap<String, String> creators = csvbean.getCreators();
            MultiSet<String> columnames = creators.keys();
            for (String columnname : columnames) {
                if (creators.get(columnname).iterator().next().trim().length() > 0) {
                    String[] creatorCol = columnname.split(Constants.regex);

                    for (String namePart : creators.get(columnname).iterator().next().split(Constants.regex)) {
                        Element childName = new Element("name", Constants.namespace);

                        Element childNamePart = new Element("namePart", Constants.namespace);
                        childNamePart.addContent(namePart);
                        childName.addContent(childNamePart);

                        Element childNameRole = new Element("role", Constants.namespace);
                        Element childNameRoleTerm = new Element("roleTerm", Constants.namespace);
                        String[] creatorRole = creatorCol[0].split(Constants.regexDot);
                        if (creatorRole.length > 1) {
                            childNameRoleTerm.addContent(creatorRole[1].trim());
                        } else {
                            childNameRoleTerm.addContent("creator");
                        }

                        childNameRole.addContent(childNameRoleTerm);
                        childName.addContent(childNameRole);
                        if (creatorCol.length > 1) {
                            childName.setAttribute("lang", creatorCol[1].trim());
                        }
                        rootElement.addContent(childName);
                    }
                }
            }

        }

        if (null != csvbean.getContributors() && !csvbean.getContributors().isEmpty()) {
            MultiValuedMap<String, String> contributors = csvbean.getContributors();
            MultiSet<String> columnames = contributors.keys();
            for (String columnname : columnames) {
                if (contributors.get(columnname).iterator().next().trim().length() > 0) {
                    for (String contributor : contributors.get(columnname).iterator().next().split(Constants.regex)) {
                        String[] contributorCol = columnname.split(Constants.regex);

                        Element childName = new Element("name", Constants.namespace);
                        // for( String namePart : record.get("columname").split(regex)) {
                        Element childNamePart = new Element("namePart", Constants.namespace);
                        childNamePart.addContent(contributor);
                        childName.addContent(childNamePart);

                        // }
                        Element childNameRole = new Element("role", Constants.namespace);
                        Element childNameRoleTerm = new Element("roleTerm", Constants.namespace);
                        String[] contributorRole = contributorCol[0].split(Constants.regexDot);
                        if (contributorRole.length > 1) {
                            childNameRoleTerm.addContent(contributorRole[1].trim());
                        } else {
                            childNameRoleTerm.addContent("contributor");
                        }

                        childNameRole.addContent(childNameRoleTerm);
                        childName.addContent(childNameRole);
                        if (contributorCol.length > 1) {
                            childName.setAttribute("lang", contributorCol[1].trim());
                        }
                        rootElement.addContent(childName);
                    }

                }
            }

        }
	}
}
