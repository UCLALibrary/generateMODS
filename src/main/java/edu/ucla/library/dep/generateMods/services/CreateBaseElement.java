package edu.ucla.library.dep.generateMods.services;

import org.jdom2.Element;
import org.jdom2.Namespace;

import edu.ucla.library.dep.generateMods.util.Constants;

public class CreateBaseElement {

    /**
     * This method creates a type of child element from the given element name.
     * @param parentElement
     * @param elementName
     * @param namespace
     * @param elementValue
     * @param typeValue
     * @param displayLabelValue
     * @param langValue
     * @param encodingValue
     * @param authorityValue
     * @param pointValue
     */
        public  void createElementFromString(Element parentElement, String elementName, Namespace namespace,
                                             String elementValue, String typeValue, String displayLabelValue, String langValue, String encodingValue,
                                             String authorityValue, String pointValue) {

            Element childElement = new Element(elementName, namespace);
            if (null != typeValue) {
                childElement.setAttribute(Constants.TYPE, typeValue);
            }
            if (null != displayLabelValue) {
                childElement.setAttribute(Constants.DISPLAY_LABEL, displayLabelValue);
            }
            if (null != langValue) {
                childElement.setAttribute(Constants.LANG, langValue);
            }
            if (null != authorityValue) {
                childElement.setAttribute(Constants.AUTHORITY, authorityValue);
            }
            if (null != encodingValue) {
                childElement.setAttribute(Constants.ENCODING, encodingValue);
            }
            if (null != pointValue) {
                childElement.setAttribute("point", pointValue);
            }
            childElement.addContent(elementValue);
            parentElement.addContent(childElement);
        }
        
        public Element createRootElement() {
        	 Element rootElement = new Element("mods", Constants.namespace);
             rootElement.addNamespaceDeclaration(Constants.namespaceXlink);
             rootElement.addNamespaceDeclaration(Constants.namespaceXsi);
             rootElement.setAttribute("schemaLocation",
                     "http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd", Constants.namespaceXsi);
             return rootElement; 
        }

}
