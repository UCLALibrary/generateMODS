package edu.ucla.library.dep.generateMods.services;

import org.jdom2.Element;
import org.jdom2.Namespace;

import edu.ucla.library.dep.generateMods.model.CsvBean;

public interface ProcessCSV {

    public void createSubjectElement(Element rootElement, Namespace namespace, CsvBean cvsbean);
    public void createDateElement(Element childOriginInfo, Namespace namespace, CsvBean cvsbean);
    public void createLanguage(Element rootElement, Namespace namespace, CsvBean cvsbean);
    public void createRelatedItemElement(Element rootElement, Namespace namespace, String typeValue,
                                         String elementValue);
    public void createProjectElement(Element rootElement, Namespace namespace, String typeValue,
                                     String titleValue, String locationValue);


}
