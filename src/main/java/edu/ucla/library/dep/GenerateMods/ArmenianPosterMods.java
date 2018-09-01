package edu.ucla.library.dep.GenerateMods;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Hello world!
 *
 */
public class ArmenianPosterMods {
	public static void main(String[] args) throws IOException {
		Reader in = new FileReader(
				"\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\armenia_testbatch1\\test batch 1 (002-050) - test batch 1.csv");
		// Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader("File name","local
		// ID ","Collection","Series","Title (English)","Title (Armenian)","Title
		// (Russian)","Creator (English)","Creator (Armenian)","Creator
		// (Russian)","Contributor.publisher","Publisher.placeOfOrigin","date.normalized","Language","Type.typeOfResource","Type.genre","PhysicalDescription","Description.note
		// (English)","Description.note (Armenian)","\"Description.inscription
		// \"\"translated\"\"\"","Description.inscription","Subject.name
		// (English)","Subject.name (Armenian)","Subject.topic (English)","Subject.topic
		// (Armenian)","Subject.geographic (English)","Subject.geographic
		// (Armenian)","Rights.copyrightStatus","Rights.publicationStatus","Rights.servicesContact","Insitution/Repository").parse(in);

		Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		String[] headers = CSVFormat.EXCEL.getHeader();
		Namespace namespace = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
		Namespace namespacexlink = Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink");
		Namespace namespacexsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		String delim = "|";
		String regex = "(?<!\\\\)" + Pattern.quote(delim);
		for (CSVRecord record : records) {
			Document jdomDoc = new Document();
			// create root element
			Element rootElement = new Element("mods", namespace);
			rootElement.addNamespaceDeclaration(namespacexlink);
			rootElement.addNamespaceDeclaration(namespacexsi);
			rootElement.setAttribute("schemaLocation",
					"http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd", namespacexsi);

			Element childOriginInfo = new Element("originInfo", namespace);
			Element childLocation = new Element("location", namespace);
			boolean publisherName = false;
			for (String columname : headers) {

				if (columname.startsWith("Title")) {
					String[] titleCol = columname.split("|");
					if (record.isSet(columname)) {
						Element childTitleInfo = new Element("titleInfo", namespace);
						Element childTitle = new Element("title", namespace);
						childTitle.addContent(record.get(columname));
						if (titleCol.length > 1) {
							childTitle.setAttribute("lang", titleCol[1]);
						}
						childTitleInfo.addContent(childTitle);
						rootElement.addContent(childTitleInfo);
					}

				}

				if (columname.startsWith("Creator")) {
					String[] creatorCol = columname.split("|");
					if (record.isSet(columname)) {
						Element childName = new Element("name", namespace);
						// for( String namePart : record.get("columname").split(regex)) {
						Element childNamePart = new Element("namePart", namespace);
						childNamePart.addContent(record.get(columname));
						childName.addContent(childNamePart);

						// }
						Element childNameRole = new Element("role", namespace);
						Element childNameRoleTerm = new Element("roleTerm", namespace);
						String[] creatorRole = creatorCol[0].split(".");
						if (creatorRole.length > 1) {
							childNameRoleTerm.addContent(creatorRole[1]);
						} else {
							childNameRoleTerm.addContent("creator");
						}

						childNameRole.addContent(childNameRoleTerm);
						childName.addContent(childNameRole);
						if (creatorCol.length > 1) {
							childName.setAttribute("lang", creatorCol[1]);
						}
						rootElement.addContent(childName);
					}

				}

				if (columname.startsWith("Publisher")) {
					String[] pubCol = columname.split(".");
					if (record.isSet(pubCol[0])) {
						if (!publisherName) {
							Element childPublisher = new Element("publisher", namespace);
							childPublisher.addContent(record.get(pubCol[0]));
							childOriginInfo.addContent(childPublisher);
						}

						if (pubCol.length > 1) {
							if (record.isSet(pubCol[1])) {
								Element childPlace = new Element("place", namespace);
								childPlace.addContent(record.get(pubCol[1]));
								childOriginInfo.addContent(childPlace);
							}

						}
					}
				}

				if (columname.startsWith("PhysicalDescription")) {
					String[] phyDesCol = columname.split(regex);
					if (record.isSet(columname)) {
						String[] element = phyDesCol[0].split(".");
						Element childPhysicalDescription = new Element("physicalDescription", namespace);
						Element extent = new Element(element[1], namespace);
						extent.addContent(record.get("PhysicalDescription"));
						// add type from phyDescCol[1] to displayLabel = "type"
						childPhysicalDescription.addContent(extent);
						rootElement.addContent(childPhysicalDescription);

					}
				}

				if (columname.startsWith("Abstract")) {
					String[] titleCol = columname.split(regex);
					if (record.isSet(columname)) {
						Element childDescription = new Element("abstract", namespace);
						if (titleCol.length > 1) {
							childDescription.setAttribute("lang", titleCol[1]);
						}
						childDescription.addContent(record.get(columname));
						rootElement.addContent(childDescription);

					}
				}

				if (columname.startsWith("Note")) {
					String[] noteCol = columname.split(regex);
					if (record.isSet(columname)) {
						Element childNote = new Element("note", namespace);
						if (noteCol.length > 1) {

							childNote.setAttribute("lang", noteCol[1]);

						}
						String[] noteType = noteCol[0].split(".");
						if (noteType.length > 1) {
							childNote.setAttribute("type", noteType[1]);
							childNote.setAttribute("displayLabel", noteType[1]);
						}

						childNote.addContent(record.get(columname));

						rootElement.addContent(childNote);
					}

				}

				if (columname.startsWith("Rights")) {
					String[] rightsCol = columname.split(".");
					Element childAccessCondition = new Element("accessCondition", namespace);
					Element childCopyright = new Element("copyright", namespace);
					childCopyright.setAttribute("schemaLocation",
							"http://www.cdlib.org/inside/diglib/copyrightMD http://www.cdlib.org/groups/rmg/docs/copyrightMD.xsd",
							namespacexsi);
					switch (rightsCol[1]) {
					case "copyrightStatus":
						childCopyright.setAttribute("copyright.status", record.get(columname));
						break;
					case "publicationStatus":
						childCopyright.setAttribute("publication.status", record.get(columname));
						break;
					case "servicesContact":
						Element services = new Element("services", namespace);
						Element contact = new Element("contact", namespace);
						contact.addContent(record.get(columname));
						services.addContent(contact);
						childCopyright.addContent(services);
						break;

					default:
						break;
					}

				}

				switch (columname) {

				case "File name":
					if (record.isSet(columname)) {
						Element childIdentifier = new Element("identifier", namespace);
						childIdentifier.setAttribute("type", "local");
						childIdentifier.setAttribute("displayLabel", "Filename");
						childIdentifier.addContent(record.get(columname));
						rootElement.addContent(childIdentifier);

					}
					break;
				case "local ID":
					if (record.isSet(columname)) {
						Element childIdentifier = new Element("identifier", namespace);
						childIdentifier.setAttribute("type", "local");
						childIdentifier.addContent(record.get(columname));
						rootElement.addContent(childIdentifier);
					}
					break;

				case "Collection":
					if (record.isSet(columname)) {
						Element childRelatedItem = new Element("relatedItem", namespace);
						childRelatedItem.setAttribute("type", "host");
						Element childTitleInfo = new Element("titleInfo", namespace);
						Element childTitle = new Element("title", namespace);
						childTitle.addContent(record.get(columname));
						childTitleInfo.addContent(childTitle);
						childRelatedItem.addContent(childTitleInfo);
						rootElement.addContent(childRelatedItem);

					}
					break;
				case "Series":
					if (record.isSet(columname)) {
						Element childRelatedItem = new Element("relatedItem", namespace);
						childRelatedItem.setAttribute("type", "series");
						Element childTitleInfo = new Element("titleInfo", namespace);
						Element childTitle = new Element("title", namespace);
						childTitle.addContent(record.get(columname));
						childTitleInfo.addContent(childTitle);
						childRelatedItem.addContent(childTitleInfo);
						rootElement.addContent(childRelatedItem);
					}
					break;

				case "Date.issued(single)":
					if (record.isSet(columname)) {
						Element childDate = new Element("dateIssued", namespace);
						childDate.addContent(record.get(columname));
						childDate.setAttribute("encoding", "iso8601");
						childOriginInfo.addContent(childDate);
					}
					break;

				case "Date.issued(start)":
					if (record.isSet(columname)) {
						Element childDate = new Element("dateIssued", namespace);
						childDate.addContent(record.get("date (start)"));
						childDate.setAttribute("encoding", "iso8601");
						childDate.setAttribute("point", "start");
						childOriginInfo.addContent(childDate);
					}
					break;
				case "Date.issued(end)":
					if (record.isSet(columname)) {
						Element childDate = new Element("dateIssued", namespace);
						childDate.addContent(record.get(columname));
						childDate.setAttribute("encoding", "iso8601");
						childDate.setAttribute("point", "end");
						childOriginInfo.addContent(childDate);
					}
					break;
				case "Language | code":
					if (record.isSet(columname)) {
						String[] langCol = columname.split(regex);

						Element childLanguage = new Element("language", namespace);
						Element childLanguageTermText = new Element("languageTerm", namespace);
						childLanguageTermText.addContent(langCol[0]);
						childLanguageTermText.setAttribute("type", "text");
						childLanguage.addContent(childLanguageTermText);
						Element childLanguageTermCode = new Element("languageTerm", namespace);
						childLanguageTermCode.addContent(langCol[1]);
						childLanguageTermCode.setAttribute("type", "code");
						childLanguageTermCode.setAttribute("encoding", "iso639-2b");
						childLanguage.addContent(childLanguageTermCode);
						rootElement.addContent(childLanguage);

					}
					break;

				case "TypeOfResource":
					if (record.isSet(columname)) {
						Element childTypeOfResource = new Element("typeOfResource", namespace);
						childTypeOfResource.addContent(record.get(columname));
						rootElement.addContent(childTypeOfResource);
					}
					break;

				case "Genre":
					if (record.isSet(columname)) {
						for (String genre : record.get(columname).split(regex)) {
							Element childTypeGenre = new Element("genre", namespace);
							childTypeGenre.addContent(genre);
							rootElement.addContent(childTypeGenre);
						}
					}
					break;
				case "Subject.name":
					if (record.isSet(columname)) {
						Element childSubject = new Element("subject", namespace);
						Element childName = new Element("name", namespace);
						Element childNamePart = new Element("namePart", namespace);
						childNamePart.addContent(record.get(columname));
						childName.addContent(childNamePart);
						childSubject.addContent(childName);
						rootElement.addContent(childSubject);
					}
					break;
				case "Subject.topic":
					if (record.isSet(columname)) {
						Element childSubject = new Element("subject", namespace);
						for (String topic : record.get(columname).split(regex)) {
							Element childTopic = new Element("topic", namespace);
							childTopic.addContent(topic);
							childSubject.addContent(childTopic);
						}
					}
					break;
				case "Subject.place":
					if (record.isSet(columname)) {
						Element childSubject = new Element("subject", namespace);
						Element childGeographic = new Element("geographic", namespace);
						childGeographic.addContent(record.get(columname));
						childSubject.addContent(childGeographic);
						rootElement.addContent(childSubject);
					}

				case "Insitution/Repository":
					if (record.isSet(columname)) {

						Element childPhysicalLocation = new Element("physicalLocation", namespace);
						childPhysicalLocation.addContent(record.get(columname));
						childLocation.addContent(childPhysicalLocation);

					}
					break;

				default:
					break;
				}

				// File name <mods:identifier type="local">
				/*
				 * if(record.isSet("File name")){ if(null != record.get("File name") &&
				 * record.get("File name").length() > 0){ Element childIdentifier = new
				 * Element("identifier", namespace); childIdentifier.setAttribute("type",
				 * "local"); childIdentifier.setAttribute("displayLabel", "filename");
				 * childIdentifier.addContent(record.get("File name"));
				 * rootElement.addContent(childIdentifier);
				 * 
				 * } }
				 */

				// File name <mods:identifier type="local">
				/*
				 * if(record.isSet("local ID ")){ if(null != record.get("local ID ") &&
				 * record.get("local ID ").length() > 0){ Element childIdentifier = new
				 * Element("identifier", namespace); childIdentifier.setAttribute("type",
				 * "local"); childIdentifier.addContent(record.get("local ID "));
				 * rootElement.addContent(childIdentifier);
				 * 
				 * } }
				 */

				// Collection <mods:relatedItem type="series"
				// displayLabel="collection"><titleInfo>
				/*
				 * if(record.isSet("Collection")){ if(null != record.get("Collection") &&
				 * record.get("Collection").length() > 0){ Element childRelatedItem = new
				 * Element("relatedItem", namespace); childRelatedItem.setAttribute("type",
				 * "host"); //childRelatedItem.setAttribute("displayLabel", "collection");
				 * Element childTitleInfo = new Element("titleInfo", namespace); Element
				 * childTitle = new Element("title", namespace);
				 * childTitle.addContent(record.get("Collection"));
				 * childTitleInfo.addContent(childTitle);
				 * childRelatedItem.addContent(childTitleInfo);
				 * rootElement.addContent(childRelatedItem); } }
				 */

				// Series <mods:relatedItem type="series" displayLabel="series"><titleInfo>
				/*
				 * if(record.isSet("Series")){ if(null != record.get("Series") &&
				 * record.get("Series").length() > 0){ Element childRelatedItem = new
				 * Element("relatedItem", namespace); childRelatedItem.setAttribute("type",
				 * "series"); //childRelatedItem.setAttribute("displayLabel", "series"); Element
				 * childTitleInfo = new Element("titleInfo", namespace); Element childTitle =
				 * new Element("title", namespace); childTitle.addContent(record.get("Series"));
				 * childTitleInfo.addContent(childTitle);
				 * childRelatedItem.addContent(childTitleInfo);
				 * rootElement.addContent(childRelatedItem); } }
				 */

				// Create English, Armenian and Russian titles
				/*
				 * if(record.isSet("Title")){ if(null != record.get("Title") &&
				 * record.get("Title").length() > 0){ Element childTitleInfo = new
				 * Element("titleInfo", namespace); Element childTitle = new Element("title",
				 * namespace); childTitle.addContent(record.get("Title"));
				 * //childTitle.setAttribute("lang","eng");
				 * childTitleInfo.addContent(childTitle);
				 * rootElement.addContent(childTitleInfo);
				 * 
				 * } }
				 * 
				 * 
				 * if(record.isSet("Title | arm")){ if(null != record.get("Title | arm") &&
				 * record.get("Title | arm)").length() > 0){ Element childTitleInfo = new
				 * Element("titleInfo", namespace); Element childTitle = new Element("title",
				 * namespace); childTitle.addContent(record.get("Title | arm"));
				 * childTitle.setAttribute("lang","arm");
				 * //childTitle.setAttribute("type","alternative");
				 * childTitleInfo.addContent(childTitle);
				 * rootElement.addContent(childTitleInfo);
				 * 
				 * } }
				 * 
				 * 
				 * if(record.isSet("Title | rus")){ if(null != record.get("Title | rus") &&
				 * record.get("Title | rus").length() > 0){ Element childTitleInfo = new
				 * Element("titleInfo", namespace); Element childTitle = new Element("title",
				 * namespace); childTitle.addContent(record.get("Title | rus"));
				 * childTitle.setAttribute("lang","rus"); childTitleInfo.addContent(childTitle);
				 * rootElement.addContent(childTitleInfo);
				 * 
				 * } }
				 */

				// create name element for eng, rus and arm
				/*
				 * if(record.isSet("Creator (English)")){ if(null !=
				 * record.get("Creator (English)") && record.get("Creator (English)").length() >
				 * 0){ Element childName = new Element("name", namespace); for( String namePart
				 * : record.get("Creator (English)").split(regex)) { Element childNamePart = new
				 * Element("namePart", namespace); childNamePart.addContent(namePart);
				 * childName.addContent(childNamePart);
				 * 
				 * } Element childNameRole = new Element("role",namespace); Element
				 * childNameRoleTerm = new Element("roleTerm",namespace);
				 * childNameRoleTerm.addContent("creator");
				 * childNameRole.addContent(childNameRoleTerm);
				 * childName.addContent(childNameRole); //childName.setAttribute("lang","eng");
				 * 
				 * rootElement.addContent(childName);
				 * 
				 * } }
				 * 
				 * if(record.isSet("Creator (Armenian)")){ if(null !=
				 * record.get("Creator (Armenian)") && record.get("Creator (Armenian)").length()
				 * > 0){ Element childName = new Element("name", namespace); for( String
				 * namePart : record.get("Creator (Armenian)").split(regex)) { Element
				 * childNamePart = new Element("namePart", namespace);
				 * childNamePart.addContent(namePart); childName.addContent(childNamePart); }
				 * 
				 * childName.setAttribute("lang","arm"); Element childNameRole = new
				 * Element("role",namespace); Element childNameRoleTerm = new
				 * Element("roleTerm",namespace); childNameRoleTerm.addContent("creator");
				 * childNameRole.addContent(childNameRoleTerm);
				 * childName.addContent(childNameRole); rootElement.addContent(childName);
				 * 
				 * } }
				 * 
				 * if(record.isSet("Creator (Russian)")){ if(null !=
				 * record.get("Creator (Russian)") && record.get("Creator (Russian)").length() >
				 * 0){ Element childName = new Element("name", namespace); for( String namePart
				 * : record.get("Creator (Russian)").split(regex)) { Element childNamePart = new
				 * Element("namePart", namespace); childNamePart.addContent(namePart);
				 * childName.addContent(childNamePart); } childName.setAttribute("lang", "rus");
				 * Element childNameRole = new Element("role",namespace); Element
				 * childNameRoleTerm = new Element("roleTerm",namespace);
				 * childNameRoleTerm.addContent("creator");
				 * childNameRole.addContent(childNameRoleTerm);
				 * childName.addContent(childNameRole); rootElement.addContent(childName);
				 * 
				 * } }
				 */

				// Element childOriginInfo = new Element("originInfo", namespace);
				// Contributor.publisher <mods:originInfo><mods:publisher>
				/*
				 * if(record.isSet("Contributor.publisher")){ if(null !=
				 * record.get("Contributor.publisher") &&
				 * record.get("Contributor.publisher").length() > 0){
				 * 
				 * Element childPublisher = new Element("publisher", namespace);
				 * childPublisher.addContent(record.get("Contributor.publisher"));
				 * childOriginInfo.addContent(childPublisher);
				 * 
				 * 
				 * } }
				 * 
				 * 
				 * //Publisher.placeOfOrigin <mods:originInfo><mods:place>
				 * if(record.isSet("Publisher.placeOfOrigin")){ if(null !=
				 * record.get("Publisher.placeOfOrigin") &&
				 * record.get("Publisher.placeOfOrigin").length() > 0){
				 * 
				 * Element childPlace = new Element("place", namespace);
				 * childPlace.addContent(record.get("Publisher.placeOfOrigin"));
				 * childOriginInfo.addContent(childPlace);
				 * 
				 * 
				 * } }
				 */

				// Date.normalized <mods:originInfo><mods:dateCreated encoding="iso8601">

				/*
				 * if(null != record.get("date.normalized") &&
				 * record.get("date.normalized").length() > 0){
				 * 
				 * Element childDate= new Element("dateCreated", namespace);
				 * childDate.addContent(record.get("date.normalized"));
				 * childDate.setAttribute("encoding","iso8601");
				 * childOriginInfo.addContent(childDate);
				 * 
				 * 
				 * }
				 */

				/*
				 * if(record.isSet("date (single)")){ if(null != record.get("date (single)") &&
				 * record.get("date (single)").length() > 0){
				 * 
				 * Element childDate= new Element("dateCreated", namespace);
				 * childDate.addContent(record.get("date (single)"));
				 * childDate.setAttribute("encoding","iso8601");
				 * childOriginInfo.addContent(childDate);
				 * 
				 * 
				 * } }
				 * 
				 * 
				 * if(record.isSet("date (start)")){ if(null != record.get("date (start)") &&
				 * record.get("date (start)").length() > 0){
				 * 
				 * Element childDate= new Element("dateCreated", namespace);
				 * childDate.addContent(record.get("date (start)"));
				 * childDate.setAttribute("encoding","iso8601");
				 * childDate.setAttribute("point","start");
				 * childOriginInfo.addContent(childDate);
				 * 
				 * 
				 * } }
				 * 
				 * 
				 * if(record.isSet("date (end)")){ if(null != record.get("date (end)") &&
				 * record.get("date (end)").length() > 0){
				 * 
				 * Element childDate= new Element("dateCreated", namespace);
				 * childDate.addContent(record.get("date (end)"));
				 * childDate.setAttribute("encoding","iso8601");
				 * childDate.setAttribute("point","end"); childOriginInfo.addContent(childDate);
				 * 
				 * 
				 * } }
				 */

				if (null != childOriginInfo.getChildren() && childOriginInfo.getChildren().size() > 0) {
					rootElement.addContent(childOriginInfo);
				}

				if (null != childLocation.getChildren() && childLocation.getChildren().size() > 0) {
					rootElement.addContent(childLocation);
				}

				/*
				 * Language "<language> <languageTerm type=""text""> <languageTerm type=""code""
				 * authority=""iso639-2b"">"
				 */
				/*
				 * if(record.isSet("Language (code)")){ if(null != record.get("Language (code)")
				 * && record.get("Language (code)").length() > 0){
				 * 
				 * for( String languagecode : record.get("Language (code)").split(regex)) {
				 * Element childLanguage = new Element("language", namespace); Element
				 * childLanguageTermText= new Element("languageTerm", namespace);
				 * switch(languagecode) { case
				 * "eng":childLanguageTermText.addContent("English"); break; case
				 * "arm":childLanguageTermText.addContent("Armenian"); break; case
				 * "rus":childLanguageTermText.addContent("Russian"); break; }
				 * 
				 * childLanguageTermText.setAttribute("type", "text");
				 * childLanguage.addContent(childLanguageTermText); Element
				 * childLanguageTermCode= new Element("languageTerm", namespace);
				 * childLanguageTermCode.addContent(languagecode);
				 * childLanguageTermCode.setAttribute("type", "code");
				 * childLanguageTermCode.setAttribute("encoding", "iso639-2b");
				 * childLanguage.addContent(childLanguageTermCode);
				 * rootElement.addContent(childLanguage); }
				 * 
				 * } }
				 */

				// Type.typeOfResource <mods:typeOfResource>
				/*
				 * if(record.isSet("Type.typeOfResource")){ if(null !=
				 * record.get("Type.typeOfResource") &&
				 * record.get("Type.typeOfResource").length() > 0){ Element childTypeOfResource
				 * = new Element("typeOfResource", namespace);
				 * childTypeOfResource.addContent(record.get("Type.typeOfResource"));
				 * rootElement.addContent(childTypeOfResource);
				 * 
				 * } }
				 */

				// Type.genre <mods:genre>
				/*
				 * if(record.isSet("Type.genre")){ if(null != record.get("Type.genre") &&
				 * record.get("Type.genre").length() > 0){ for(String genre :
				 * record.get("Type.genre").split(regex)) { Element childTypeGenre = new
				 * Element("genre", namespace); childTypeGenre.addContent(genre);
				 * rootElement.addContent(childTypeGenre); }
				 * 
				 * } }
				 */

				// PhysicalDescription <mods:physicalDescription>
				/*
				 * if(record.isSet("PhysicalDescription")){ if(null !=
				 * record.get("PhysicalDescription") &&
				 * record.get("PhysicalDescription").length() > 0){ Element
				 * childPhysicalDescription = new Element("physicalDescription", namespace);
				 * Element extent = new Element("extent",namespace);
				 * extent.addContent(record.get("PhysicalDescription"));
				 * childPhysicalDescription.addContent(extent);
				 * rootElement.addContent(childPhysicalDescription);
				 * 
				 * } }
				 * 
				 * 
				 * //Description.note <mods:note lang="...">
				 * if(record.isSet("Description.note (English)")){ if(null !=
				 * record.get("Description.note (English)") &&
				 * record.get("Description.note (English)").length() > 0){ Element
				 * childDescriptionEnglish = new Element("abstract", namespace);
				 * //childDescriptionEnglish.setAttribute("lang", "eng");
				 * childDescriptionEnglish.addContent(record.get("Description.note (English)"));
				 * rootElement.addContent(childDescriptionEnglish);
				 * 
				 * } }
				 * 
				 * if(record.isSet("Description.note (Armenian)")){ if(null !=
				 * record.get("Description.note (Armenian)") &&
				 * record.get("Description.note (Armenian)").length() > 0){ Element
				 * childDescriptionArmenian = new Element("abstract", namespace);
				 * childDescriptionArmenian.setAttribute("lang", "arm");
				 * childDescriptionArmenian.addContent(record.get("Description.note (Armenian)")
				 * ); rootElement.addContent(childDescriptionArmenian);
				 * 
				 * } }
				 */

				/*
				 * if(null != record.get("Description.inscription \"translated\"") &&
				 * record.get("Description.inscription \"translated\"").length() > 0){ Element
				 * childDescriptionEnglish = new Element("note", namespace);
				 * childDescriptionEnglish.setAttribute("lang", "eng");
				 * childDescriptionEnglish.setAttribute("type", "inscription");
				 * childDescriptionEnglish.addContent(record.
				 * get("Description.inscription \"translated\""));
				 * rootElement.addContent(childDescriptionEnglish);
				 * 
				 * }
				 */

				// Description.inscription
				/*
				 * if(record.isSet("Description.inscription")){ if(null !=
				 * record.get("Description.inscription") &&
				 * record.get("Description.inscription").length() > 0){ Element
				 * childDescriptionEnglish = new Element("note", namespace);
				 * childDescriptionEnglish.setAttribute("type", "inscription");
				 * childDescriptionEnglish.addContent(record.get("Description.inscription"));
				 * rootElement.addContent(childDescriptionEnglish);
				 * 
				 * } }
				 */

				// Subject.name <mods:subject lang="..."><mods:name>
				// Subject.name (English)
				/*
				 * if(record.isSet("Subject.name (English)")){ if(null !=
				 * record.get("Subject.name (English)") &&
				 * record.get("Subject.name (English)").length() > 0){ Element childSubject =
				 * new Element("subject", namespace); //childSubject.setAttribute("lang",
				 * "eng"); Element childName = new Element("name", namespace); Element
				 * childNamePart = new Element("namePart", namespace);
				 * childNamePart.addContent(record.get("Subject.name (English)"));
				 * childName.addContent(childNamePart); childSubject.addContent(childName);
				 * rootElement.addContent(childSubject);
				 * 
				 * } }
				 * 
				 * 
				 * 
				 * 
				 * 
				 * //Subject.topic <mods:subject lang="..."><mods:topic>
				 * if(record.isSet("Subject.topic (English)")){ if(null !=
				 * record.get("Subject.topic (English)") &&
				 * record.get("Subject.topic (English)").length() > 0){ Element childSubject =
				 * new Element("subject", namespace); //childSubject.setAttribute("lang",
				 * "eng"); for( String topic :
				 * record.get("Subject.topic (English)").split(regex)) { Element childTopic =
				 * new Element("topic", namespace); childTopic.addContent(topic);
				 * childSubject.addContent(childTopic); }
				 * 
				 * rootElement.addContent(childSubject);
				 * 
				 * } }
				 * 
				 * 
				 * if(record.isSet("Subject.topic (Armenian)")){ if(null !=
				 * record.get("Subject.topic (Armenian)") &&
				 * record.get("Subject.topic (Armenian)").length() > 0){ Element childSubject =
				 * new Element("subject", namespace); childSubject.setAttribute("lang", "arm");
				 * for( String topic : record.get("Subject.topic (Armenian)").split(regex)) {
				 * Element childTopic = new Element("topic", namespace);
				 * childTopic.addContent(topic); childSubject.addContent(childTopic); }
				 * rootElement.addContent(childSubject);
				 * 
				 * }
				 * 
				 * }
				 */

				// Subject.geographic <mods:subject lang="..."><mods:geographic>
				/*
				 * if(record.isSet("Subject.geographic (English)")){ if(null !=
				 * record.get("Subject.geographic (English)") &&
				 * record.get("Subject.geographic (English)").length() > 0){ Element
				 * childSubject = new Element("subject", namespace);
				 * //childSubject.setAttribute("lang", "eng"); Element childGeographic = new
				 * Element("geographic", namespace);
				 * childGeographic.addContent(record.get("Subject.geographic (English)"));
				 * childSubject.addContent(childGeographic);
				 * rootElement.addContent(childSubject);
				 * 
				 * } }
				 * 
				 * if(record.isSet("Subject.geographic (Armenian)")){ if(null !=
				 * record.get("Subject.geographic (Armenian)") &&
				 * record.get("Subject.geographic (Armenian)").length() > 0){ Element
				 * childSubject = new Element("subject", namespace);
				 * childSubject.setAttribute("lang", "arm"); Element childGeographic = new
				 * Element("geographic", namespace);
				 * childGeographic.addContent(record.get("Subject.geographic (Armenian)"));
				 * childSubject.addContent(childGeographic);
				 * rootElement.addContent(childSubject);
				 * 
				 * } }
				 */

				// Insitution/Repository "<location><physicalLocation>"
				/*
				 * if(record.isSet("Insitution/Repository")){ if(null !=
				 * record.get("Insitution/Repository") &&
				 * record.get("Insitution/Repository").length() > 0){ Element childLocation =
				 * new Element("location", namespace); Element childPhysicalLocation = new
				 * Element("physicalLocation", namespace);
				 * childPhysicalLocation.addContent(record.get("Insitution/Repository"));
				 * childLocation.addContent(childPhysicalLocation);
				 * rootElement.addContent(childLocation);
				 * 
				 * } }
				 */

				/*
				 * "<accessCondition> <copyright copyright.status=""copyrighted""
				 * publication.status=""published""> <services> <contact>"
				 * 
				 * 
				 */
				/*if (record.isSet("Rights.copyrightStatus")) {
					if (null != record.get("Rights.copyrightStatus")
							&& record.get("Rights.copyrightStatus").trim().length() > 0) {
						Element childAccessCondition = new Element("accessCondition", namespace);
						Element childCopyright = new Element("copyright", namespace);
						childCopyright.setAttribute("copyright.status", record.get("Rights.copyrightStatus"));
						if (null != record.get("Rights.publicationStatus")
								&& record.get("Rights.publicationStatus").trim().length() > 0) {
							childCopyright.setAttribute("publication.status", record.get("Rights.publicationStatus"));
						}
						childCopyright.setAttribute("schemaLocation",
								"http://www.cdlib.org/inside/diglib/copyrightMD http://www.cdlib.org/groups/rmg/docs/copyrightMD.xsd",
								namespacexsi);

						if (null != record.get("Rights.servicesContact")
								&& record.get("Rights.servicesContact").trim().length() > 0) {
							Element services = new Element("services", namespace);
							Element contact = new Element("contact", namespace);
							contact.addContent(record.get("Rights.servicesContact"));
							services.addContent(contact);
							childCopyright.addContent(services);
						}
						childAccessCondition.addContent(childCopyright);
						rootElement.addContent(childAccessCondition);

					}
				}*/

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
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(jdomDoc,
						new FileWriter("\\\\svm-netapp-dlib.in.library.ucla.edu\\DLIngest\\armenia_testbatch1\\test_mods\\"
								+ record.get("File name").replaceFirst("tif", "xml")));

			}

			// System.out.println( "Hello World!" );
		}
	}
}
