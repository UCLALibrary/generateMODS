package edu.ucla.library.dep.generateMods;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Document;
import org.jdom2.Element;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AssembleMods {
    CreateBaseElement baseElement;
    ProcessCSV processcsv;
    String projectName;
    String projectURL;
    String program;
    String inFilePath;
    String outfilePath;





    public AssembleMods(String projectName, String projectURL, String program, String infilePath, String outfilePath) {
        processcsv = new ProcessCsvImpl();
        baseElement = new CreateBaseElement();
        this.program = program;
        this.projectName = projectName;
        this.projectURL = projectURL;
        this.inFilePath = infilePath;
        this.outfilePath = outfilePath;
    }

    /**
     * This method transforms CSV to MODS
     */
    public void generateMods() throws IOException {
        List<IdepCsvBean> beans = new CsvToBeanBuilder<IdepCsvBean>(new FileReader(inFilePath))
                .withType(IdepCsvBean.class).build().parse();

        for (IdepCsvBean cvsbean : beans) {
            System.out.println(cvsbean);


            // create root element
            Element rootElement = new Element("mods", Constants.namespace);
            rootElement.addNamespaceDeclaration(Constants.namespaceXlink);
            rootElement.addNamespaceDeclaration(Constants.namespaceXsi);
            rootElement.setAttribute("schemaLocation",
                    "http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd", Constants.namespaceXsi);

            Element childOriginInfo = new Element("originInfo", Constants.namespace);
            Element childLocation = new Element("location", Constants.namespace);

            // Add Project values
            processcsv.createProjectElement(rootElement, Constants.namespace, "host", projectName, projectURL);

            // Add File Name
            if (null != cvsbean.getFileName()) {
                baseElement.createElementFromString(rootElement, "identifier", Constants.namespace, cvsbean.getFileName(), "local",
                        "File name", null, null, null, null);
            }

            // Add Streaming URL
            if (null != cvsbean.getStreamingURL()) {
                baseElement.createElementFromString(rootElement, "identifier", Constants.namespace, cvsbean.getStreamingURL(), "uri",
                        "Streaming URL", null, null, null, null);
            }

            // Add Local ID
            if (null != cvsbean.getLocalID()) {
                baseElement.createElementFromString(rootElement, "identifier", Constants.namespace, cvsbean.getLocalID(), "local", null, null,
                        null, null, null);
            }

            // Add collection column
            if (null != cvsbean.getCollection()) {
                MultiValuedMap<String, String> collections = cvsbean.getCollection();
                MultiSet<String> columnames = collections.keys();
                for (String colName : columnames) {
                    if (collections.get(colName).iterator().next().trim().length() > 0) {
                        for (String collection : collections.get(colName).iterator().next().split(Constants.regex)) {
                            processcsv.createRelatedItemElement(rootElement, Constants.namespace, "host", collection);

                        }
                    }
                }
            }
            if (null != cvsbean.getRelated_resource()) {
                processcsv.createRelatedItemElement(rootElement, Constants.namespace, null, cvsbean.getRelated_resource());
            }

            // add program for cross collection search

            if ("IDEP".equals(program)) {
                processcsv.createRelatedItemElement(rootElement, Constants.namespace, "program", "International Digital Ephemera Project");
            } else {
                processcsv.createRelatedItemElement(rootElement, Constants.namespace, "program", "Modern Endangered Archives Program");
            }

            if (null != cvsbean.getSeries() && !cvsbean.getSeries().isEmpty()) {
                processcsv.createRelatedItemElement(rootElement, Constants.namespace, "series", cvsbean.getSeries());
            }

            if (null != cvsbean.getSubSeries() && !cvsbean.getSubSeries().isEmpty()) {
                processcsv.createRelatedItemElement(rootElement, Constants.namespace, "subseries", cvsbean.getSubSeries());
            }
            if (null != cvsbean.getLicense()) {
                baseElement.createElementFromString(rootElement, "accessCondition", Constants.namespace, cvsbean.getLicense(),
                        "use and reproduction", "license", null, null, null, null);
            }
            if (null != cvsbean.getLocalRightsStatement()) {
                baseElement.createElementFromString(rootElement, "accessCondition", Constants.namespace, cvsbean.getLocalRightsStatement(),
                        "local rights statements", null, null, null, null, null);
            }

            if (null != cvsbean.getTypeOfResource()) {
                baseElement.createElementFromString(rootElement, "typeOfResource", Constants.namespace, cvsbean.getTypeOfResource(), null,
                        null, null, null, null, null);
            }

            if (null != cvsbean.getGenre()) {
                for (String genre : cvsbean.getGenre().split(Constants.regex)) {
                    baseElement.createElementFromString(rootElement, "genre", Constants.namespace, genre, null, null, null, null, null, null);
                }
            }

            if (null != cvsbean.getInstitution_repository()) {
                MultiValuedMap<String, String> repos = cvsbean.getInstitution_repository();
                MultiSet<String> columnames = repos.keys();
                for (String colName : columnames) {
                    baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                            repos.get(colName).iterator().next(), "repository", "Repository", null, null, null, null);

                }

            }

            if (null != cvsbean.getCollectionName()) {
                MultiValuedMap<String, String> collectionNames = cvsbean.getCollectionName();
                MultiSet<String> columnames = collectionNames.keys();
                for (String colName : columnames) {
                    baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                            collectionNames.get(colName).iterator().next(), "collection", "Repository Collection", null,
                            null, null, null);
                }

            }

            if (null != cvsbean.getCollectionNumber()) {
                MultiValuedMap<String, String> collectionNumbers = cvsbean.getCollectionNumber();
                MultiSet<String> columnames = collectionNumbers.keys();
                for (String colName : columnames) {
                    baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                            collectionNumbers.get(colName).iterator().next(), "collectionNumber", "Collection Number",
                            null, null, null, null);
                }

            }

            if (null != cvsbean.getBox()) {
                MultiValuedMap<String, String> boxes = cvsbean.getBox();
                MultiSet<String> columnames = boxes.keys();
                for (String colName : columnames) {
                    baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                            boxes.get(colName).iterator().next(), "boxNumber", "Box Number", null, null, null, null);
                }

            }

            if (null != cvsbean.getFolder()) {
                MultiValuedMap<String, String> folders = cvsbean.getFolder();
                MultiSet<String> columnames = folders.keys();
                for (String colName : columnames) {
                    baseElement.createElementFromString(childLocation, "physicalLocation", Constants.namespace,
                            folders.get(colName).iterator().next(), "folderNumber", "Folder Number", null, null, null,
                            null);
                }

            }

            processcsv.createLanguage(rootElement, Constants.namespace, cvsbean);
            processcsv.createDateElement(childOriginInfo, Constants.namespace, cvsbean);
            processcsv.createSubjectElement(rootElement, Constants.namespace, cvsbean);

            if (null != cvsbean.getTitles() && !cvsbean.getTitles().isEmpty()) {

                MultiValuedMap<String, String> titles = cvsbean.getTitles();
                MultiSet<String> columnames = titles.keys();

                for (String columnname : columnames) {
                    if (titles.get(columnname).iterator().next().trim().length() > 0) {
                        String[] titleCol = columnname.split(Constants.regex);
                        Element childTitleInfo = new Element("titleInfo", Constants.namespace);
                        Element childTitle = new Element("title", Constants.namespace);
                        childTitle.addContent(titles.get(columnname).iterator().next());
                        if (titleCol.length > 1) {
                            childTitle.setAttribute("lang", titleCol[1].trim());
                        }
                        childTitleInfo.addContent(childTitle);
                        rootElement.addContent(childTitleInfo);
                    }

                }

            }

            if (null != cvsbean.getAlt_titles() && !cvsbean.getAlt_titles().isEmpty()) {

                MultiValuedMap<String, String> altTitles = cvsbean.getAlt_titles();
                MultiSet<String> columnames = altTitles.keys();

                for (String columnname : columnames) {
                    if (altTitles.get(columnname).iterator().next().trim().length() > 0) {
                        String[] titleCol = columnname.split(Constants.regex);
                        Element childTitleInfo = new Element("titleInfo", Constants.namespace);
                        Element childTitle = new Element("title", Constants.namespace);
                        childTitle.addContent(altTitles.get(columnname).iterator().next());
                        if (titleCol.length > 1) {
                            childTitle.setAttribute("lang", titleCol[1].trim());
                        }
                        childTitle.setAttribute("type", "alternative");
                        childTitleInfo.addContent(childTitle);
                        rootElement.addContent(childTitleInfo);
                    }

                }

            }

            if (null != cvsbean.getTranslated_title() && !cvsbean.getTranslated_title().isEmpty()) {

                MultiValuedMap<String, String> titles = cvsbean.getTranslated_title();
                MultiSet<String> columnames = titles.keys();

                for (String columnname : columnames) {
                    if (titles.get(columnname).iterator().next().trim().length() > 0) {
                        String[] titleCol = columnname.split(Constants.regex);
                        Element childTitleInfo = new Element("titleInfo", Constants.namespace);
                        Element childTitle = new Element("title", Constants.namespace);
                        childTitle.addContent(titles.get(columnname).iterator().next());
                        childTitle.setAttribute("type", "translated");
                        if (titleCol.length > 1) {
                            childTitle.setAttribute("lang", titleCol[1].trim());
                        }
                        childTitleInfo.addContent(childTitle);
                        rootElement.addContent(childTitleInfo);
                    }

                }

            }

            if (null != cvsbean.getCreators() && !cvsbean.getCreators().isEmpty()) {
                MultiValuedMap<String, String> creators = cvsbean.getCreators();
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

            if (null != cvsbean.getContributors() && !cvsbean.getContributors().isEmpty()) {
                MultiValuedMap<String, String> contributors = cvsbean.getContributors();
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

            if (null != cvsbean.getPublishers() && !cvsbean.getPublishers().isEmpty()) {

                MultiValuedMap<String, String> publishers = cvsbean.getPublishers();
                MultiSet<String> columnames = publishers.keys();
                for (String columnname : columnames) {
                    String[] pubCol = columnname.split(Constants.regexDot);
                    if (publishers.get(columnname).iterator().next().trim().length() > 0) {
                        if (pubCol.length == 1) {
                            Element childPublisher = new Element("publisher", Constants.namespace);
                            childPublisher.addContent(publishers.get(columnname).iterator().next());
                            childOriginInfo.addContent(childPublisher);

                        }

                        if (pubCol.length > 1) {
                            if (publishers.get(columnname).iterator().next().trim().length() > 0) {
                                Element childPlace = new Element(pubCol[1], Constants.namespace);
                                Element childPlaceTerm = new Element("placeTerm", Constants.namespace);
                                childPlaceTerm.addContent(publishers.get(columnname).iterator().next());
                                childPlace.addContent(childPlaceTerm);
                                childOriginInfo.addContent(childPlace);
                            }

                        }
                    }
                }

            }

            if (null != cvsbean.getPhysicalDescription() && !cvsbean.getPhysicalDescription().isEmpty()) {

                MultiValuedMap<String, String> physDescs = cvsbean.getPhysicalDescription();
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

            if (null != cvsbean.getAbstracts() && !cvsbean.getAbstracts().isEmpty()) {

                MultiValuedMap<String, String> abstracts = cvsbean.getAbstracts();
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

            if (null != cvsbean.getNotes() && !cvsbean.getNotes().isEmpty()) {

                MultiValuedMap<String, String> notes = cvsbean.getNotes();

                Set<String> columnames = notes.keySet();
                for (String columnname : columnames) {
                    String[] noteCol = columnname.split(Constants.regex);
                    Iterator<String> notesValues = notes.get(columnname).iterator();
                    while (notesValues.hasNext()) {
                        String notevalue = notesValues.next();
                        if (notevalue.trim().length() > 0) {
                            String[] noteType = noteCol[0].split(Constants.regexDot);
                            if (noteType.length > 1 && "license".equalsIgnoreCase(noteType[1].trim())) {
                                Element childAccessConditionLicense = new Element("accessCondition", Constants.namespace);
                                childAccessConditionLicense.setAttribute("type", "use and reproduction");
                                childAccessConditionLicense.addContent(notevalue.trim());
                                rootElement.addContent(childAccessConditionLicense);
                            } else {
                                Element childNote = new Element("note", Constants.namespace);
                                if (noteCol.length > 1) {

                                    childNote.setAttribute("lang", noteCol[1].trim());

                                }

                                if (noteType.length > 1) {
                                    childNote.setAttribute("type", noteType[1].trim());
                                    childNote.setAttribute("displayLabel", noteType[1].trim());
                                }

                                childNote.addContent(notevalue.trim());

                                rootElement.addContent(childNote);
                            }

                        }
                    }

                }

            }

            if (null != cvsbean.getRights() && !cvsbean.getRights().isEmpty()) {

                MultiValuedMap<String, String> rights = cvsbean.getRights();
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

            if (null != cvsbean.getVolumes() && !cvsbean.getVolumes().isEmpty()) {

                MultiValuedMap<String, String> volumes = cvsbean.getVolumes();
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

            if (null != childOriginInfo.getChildren() && childOriginInfo.getChildren().size() > 0) {
                rootElement.addContent(childOriginInfo);
            }

            if (null != childLocation.getChildren() && childLocation.getChildren().size() > 0) {
                rootElement.addContent(childLocation);
            }

            // write to xml file
            createFile(rootElement, cvsbean);



        }
    }

    public void createFile(Element rootElement, IdepCsvBean cvsbean) throws IOException {
        Document jdomDoc = new Document();
        // System.out.println(record.get("File name"));
        // System.out.println(record.get("local ID"));
        jdomDoc.setRootElement(rootElement);
        // create XMLOutputter
        // XMLOutputter xml = new XMLOutputter();
        // we want to format the xml. This is used only for demonstration. pretty
        // formatting adds extra spaces and is generally not required.
        /*
         * xml.setFormat(Format.getPrettyFormat());
         * System.out.println(xml.outputString(jdomDoc));
         */

        XMLOutputter xmlOutput = new XMLOutputter();

        // passsed System.out to see document content on console
        // xmlOutput.output(jdomDoc, System.out);

        // passed fileWriter to write content in specified file

        xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
        xmlOutput.output(jdomDoc, new OutputStreamWriter(
                new FileOutputStream(
                        outfilePath + cvsbean.getFileName().replaceFirst("pdf", "xml").replaceFirst("tif", "xml")
                                .replaceFirst("mp4", "xml").replaceFirst("mp3", "xml").replaceFirst("jpg", "xml")),
                StandardCharsets.UTF_8));
    }
}
