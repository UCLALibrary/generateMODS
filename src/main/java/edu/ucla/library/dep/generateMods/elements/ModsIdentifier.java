package edu.ucla.library.dep.generateMods.elements;

import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.services.CreateBaseElement;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsIdentifier {
	
	public void createIdentifiers(Element rootElement, CsvBean csvbean) {
		CreateBaseElement baseElement = new CreateBaseElement();
		// Add File Name
        if (null != csvbean.getFileName()) {
            baseElement.createElementFromString(rootElement, "identifier", Constants.namespace, csvbean.getFileName(), "local",
                    "File name", null, null, null, null);
        }

        // Add Streaming URL
        if (null != csvbean.getStreamingURL()) {
            baseElement.createElementFromString(rootElement, "identifier", Constants.namespace, csvbean.getStreamingURL(), "uri",
                    "Streaming URL", null, null, null, null);
        }

        // Add Local ID
        if (null != csvbean.getLocalID()) {
            baseElement.createElementFromString(rootElement, "identifier", Constants.namespace, csvbean.getLocalID(), "local", null, null,
                    null, null, null);
        }

	}
}
