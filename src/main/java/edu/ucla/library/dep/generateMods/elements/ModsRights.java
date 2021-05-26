package edu.ucla.library.dep.generateMods.elements;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.services.CreateBaseElement;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsRights {

	public void createAccessionCondition(CsvBean csvbean, Element rootElement) {
		
		CreateBaseElement baseElement = new CreateBaseElement();
		
		if (null != csvbean.getLicense()) {
            baseElement.createElementFromString(rootElement, "accessCondition", Constants.namespace, csvbean.getLicense(),
                    "use and reproduction", "license", null, null, null, null);
        }
        if (null != csvbean.getLocalRightsStatement()) {
            baseElement.createElementFromString(rootElement, "accessCondition", Constants.namespace, csvbean.getLocalRightsStatement(),
                    "local rights statements", null, null, null, null, null);
        }
		
        if (null != csvbean.getRights() && !csvbean.getRights().isEmpty()) {

            MultiValuedMap<String, String> rights = csvbean.getRights();
            MultiSet<String> columnames = rights.keys();
            Element childAccessCondition = new Element("accessCondition", Constants.namespace);

            Element childCopyright = new Element("copyright", Constants.namespaceCopyrightMD);
            childCopyright.setAttribute("schemaLocation",
                    "http://www.cdlib.org/inside/diglib/copyrightMD http://www.cdlib.org/groups/rmg/docs/copyrightMD.xsd",
                    Constants.namespaceXsi);
            // childCopyright.setAttribute("type","use and reproduction"); it doesnt exist
            // in schema
            for (String columnname : columnames) {
                String[] rightsCol = columnname.split(Constants.regexDot);

                switch (rightsCol[1]) {

                    case "URI": // not allowed in copyright schema
                        // childCopyright.setAttribute("href",
                        // rights.get(columnname).iterator().next(),namespacexlink);
                        Element childAccessConditionRights = new Element("accessCondition", Constants.namespace);
                        childAccessConditionRights.setAttribute("type", "use and reproduction");
                        childAccessConditionRights.setAttribute("displayLabel", "rightsUri");
                        childAccessConditionRights.addContent(rights.get(columnname).iterator().next());
                        rootElement.addContent(childAccessConditionRights);
                        break;

                    case "copyrightStatus":
                        childCopyright.setAttribute("copyright.status", rights.get(columnname).iterator().next());
                        // childCopyright.setAttribute("copyright.status", "unknown");
                        break;
                    case "publicationStatus":
                        childCopyright.setAttribute("publication.status", rights.get(columnname).iterator().next());
                        break;
                    case "servicesContact":
                        Element services = new Element("services", Constants.namespaceCopyrightMD);
                        Element contact = new Element("contact", Constants.namespaceCopyrightMD);
                        contact.addContent(rights.get(columnname).iterator().next());
                        services.addContent(contact);
                        childCopyright.addContent(services);
                        break;
                    case "note":
                        Element childAccessConditionNote = new Element("accessCondition", Constants.namespace);
                        childAccessConditionNote.setAttribute("type", "local rights statements");
                        childAccessConditionNote.addContent(rights.get(columnname).iterator().next());
                        rootElement.addContent(childAccessConditionNote);
                        break;
                    case "license":
                        /*
                         * Element childAccessConditionLicense = new Element("accessCondition",
                         * namespace); childAccessConditionLicense.setAttribute("type",
                         * "use and reproduction");
                         * childAccessConditionLicense.addContent(rights.get(columnname).iterator().next
                         * ()); rootElement.addContent(childAccessConditionLicense);
                         */
                        break;

                    default:
                        break;
                }

            }
            childAccessCondition.addContent(childCopyright);
            rootElement.addContent(childAccessCondition);

        }
	}
}
