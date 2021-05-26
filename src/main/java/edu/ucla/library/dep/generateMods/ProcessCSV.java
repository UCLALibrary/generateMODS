package edu.ucla.library.dep.generateMods;

import org.jdom2.Element;
import org.jdom2.Namespace;

public interface ProcessCSV {

    public void createSubjectElement(Element rootElement, Namespace namespace, IdepCsvBean cvsbean);
    public void createDateElement(Element childOriginInfo, Namespace namespace, IdepCsvBean cvsbean);
    public void createLanguage(Element rootElement, Namespace namespace, IdepCsvBean cvsbean);
    public void createRelatedItemElement(Element rootElement, Namespace namespace, String typeValue,
                                         String elementValue);
    public void createProjectElement(Element rootElement, Namespace namespace, String typeValue,
                                     String titleValue, String locationValue);


}
