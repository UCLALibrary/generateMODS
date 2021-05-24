package edu.ucla.library.dep.generateMods;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;
import org.jdom2.Namespace;

import java.util.Collection;
import java.util.Map;

public class ProcessCsvImpl implements ProcessCSV {
    final CreateBaseElement baseElement;

    public ProcessCsvImpl() {
        baseElement = new CreateBaseElement();
    }

    /**
     * This method adds project as a related item to Mods root.
     *
     * @param rootElement
     * @param namespace
     * @param typeValue
     * @param titleValue
     * @param locationValue
     */
    public void createProjectElement(Element rootElement, Namespace namespace, String typeValue,
                                     String titleValue, String locationValue) {
        if (null != titleValue) {
            Element childRelatedItem = new Element("relatedItem", namespace);
            childRelatedItem.setAttribute(Constants.TYPE, typeValue);
            Element childTitleInfo = new Element("titleInfo", namespace);
            baseElement.createElementFromString(childTitleInfo, "title", namespace, titleValue, null, null, null, null, null, null);
            childRelatedItem.addContent(childTitleInfo);
            Element childLocation = new Element("location", namespace);
            baseElement.createElementFromString(childLocation, "url", namespace, locationValue, null, null, null, null, null, null);
            childRelatedItem.addContent(childLocation);
            rootElement.addContent(childRelatedItem);
        }

    }

    /**
     * This method adds relatedItem to Mods root.
     *
     * @param rootElement
     * @param namespace
     * @param typeValue
     * @param elementValue
     */
    public void createRelatedItemElement(Element rootElement, Namespace namespace, String typeValue,
                                         String elementValue) {

        Element childRelatedItem = new Element("relatedItem", namespace);
        childRelatedItem.setAttribute(Constants.TYPE, typeValue);
        Element childTitleInfo = new Element("titleInfo", namespace);

        if (null != elementValue) {
            baseElement.createElementFromString(childTitleInfo, "title", namespace, elementValue, null, null, null, null, null,
                    null);
        }

        childRelatedItem.addContent(childTitleInfo);
        rootElement.addContent(childRelatedItem);
    }

    /**
     * This method adds language to Mods root.
     * @param rootElement
     * @param namespace
     * @param cvsbean
     */
    public void createLanguage(Element rootElement, Namespace namespace, IdepCsvBean cvsbean) {

        if (null != cvsbean.getLanguages() && !cvsbean.getLanguages().isEmpty()) {
            MultiValuedMap<String, String> languages = cvsbean.getLanguages();
            Collection<Map.Entry<String, String>> languageMap = languages.entries();
            for (Map.Entry<String, String> entry : languageMap) {
                if (null != entry.getValue() && entry.getValue().trim().length() > 5) {
                    String[] langCol = entry.getValue().split(Constants.regex);
                    Element childLanguage = new Element("language", namespace);
                    baseElement.createElementFromString(childLanguage, "languageTerm", namespace, langCol[0].trim(), "text", null,
                            null, null, null, null);
                    baseElement.createElementFromString(childLanguage, "languageTerm", namespace, langCol[1].trim(), "code", null,
                            null, null, "iso639-2b", null);
                    rootElement.addContent(childLanguage);
                }
            }

        }

    }

    /**
     * This method adds date to Mods root.
     * @param childOriginInfo
     * @param namespace
     * @param cvsbean
     */
    public void createDateElement(Element childOriginInfo, Namespace namespace, IdepCsvBean cvsbean) {
        // TODO Auto-generated method stub
        if (null != cvsbean.getDates() && !cvsbean.getDates().isEmpty()) {
            MultiValuedMap<String, String> dates = cvsbean.getDates();
            MultiSet<String> columnames = dates.keys();
            for (String columnname : columnames) {
                switch (columnname) {
                    case "Date (human)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateCreated", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, null, null, null);
                        }
                        break;
                    case "Date.created":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            for (String dateValue : dates.get(columnname).iterator().next().split(Constants.regex)) {
                                baseElement.createElementFromString(childOriginInfo, "dateCreated", namespace,
                                        dateValue, null, null, null, null, null, null);
                            }

                        }
                        break;
                    case "Date.created (single)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateCreated", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, "iso8601", null, null);
                        }
                        break;

                    case "Date.created (start)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateCreated", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, "iso8601", null, "start");
                        }

                        break;
                    case "Date.created (end)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateCreated", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, "iso8601", null, "end");
                        }
                        break;
                    case "Date.issued":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateIssued", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, null, null, null);
                        }
                        break;
                    case "Date.issued (single)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateIssued", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, "iso8601", null, null);
                        }
                        break;

                    case "Date.issued (start)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateIssued", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, "iso8601", null, "start");
                        }

                        break;
                    case "Date.issued (end)":
                        if (dates.get(columnname).iterator().next().trim().length() > 0) {
                            baseElement.createElementFromString(childOriginInfo, "dateIssued", namespace,
                                    dates.get(columnname).iterator().next(), null, null, null, "iso8601", null, "end");
                        }
                        break;

                    default:
                        break;
                }
            }

        }

    }

    /**
     * This method adds subjects to Mods root.
     * @param rootElement
     * @param namespace
     * @param cvsbean
     */
    public void createSubjectElement(Element rootElement, Namespace namespace, IdepCsvBean cvsbean) {


        if (null != cvsbean.getSubjects() && !cvsbean.getSubjects().isEmpty()) {
            MultiValuedMap<String, String> subjects = cvsbean.getSubjects();
            MultiSet<String> columnames = subjects.keys();

            Element childSubject;
            for (String columnname : columnames) {
                if (subjects.get(columnname).iterator().next().trim().length() > 0) {
                    switch (columnname) {
                        case "Subject.name":

                            for (String subjectName : subjects.get(columnname).iterator().next().trim().split(Constants.regex)) {
                                childSubject = new Element("subject", namespace);
                                Element childName = new Element("name", namespace);
                                Element childNamePart = new Element("namePart", namespace);
                                childNamePart.addContent(subjectName);
                                childName.addContent(childNamePart);
                                childSubject.addContent(childName);
                                rootElement.addContent(childSubject);
                            }


                            break;
                        case "Subject.topic":

                            childSubject = new Element("subject", namespace);
                            for (String topic : subjects.get(columnname).iterator().next().split(Constants.regex)) {
                                Element childTopic = new Element("topic", namespace);
                                childTopic.addContent(topic);
                                childSubject.addContent(childTopic);
                            }
                            rootElement.addContent(childSubject);

                            break;
                        case "Subject.place":

                            childSubject = new Element("subject", namespace);
                            for (String geography : subjects.get(columnname).iterator().next().split(Constants.regex)) {
                                Element childGeographic = new Element("geographic", namespace);
                                childGeographic.addContent(geography);
                                childSubject.addContent(childGeographic);
                            }
                            rootElement.addContent(childSubject);

                            break;
                        case "Latitude/longitude":
                        case "Subject.coordinates":
                            childSubject = new Element("subject", namespace);
                            Element childCartographics = new Element("cartographics", namespace);
                            Element childCoordinates = new Element("coordinates", namespace);
                            childCoordinates.addContent(subjects.get(columnname).iterator().next());
                            childCartographics.addContent(childCoordinates);
                            childSubject.addContent(childCartographics);
                            rootElement.addContent(childSubject);

                            break;


                        case "Subject.temporal":

                            childSubject = new Element("subject", namespace);
                            for (String temporal : subjects.get(columnname).iterator().next().split(Constants.regex)) {
                                Element childTemporal = new Element("temporal", namespace);
                                childTemporal.addContent(temporal);
                                childSubject.addContent(childTemporal);
                            }

                            rootElement.addContent(childSubject);

                            break;
                        default:
                            break;
                    }
                }


            }

        }


    }
}
