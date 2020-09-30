package edu.ucla.library.dep.GenerateMods;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvIdep {

	public static void main(String[] args) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		
		List<IdepCsvBean> beans = new CsvToBeanBuilder<IdepCsvBean>(new FileReader("\\\\svm_dlib\\DLIngest\\embroideries\\embroideries-metadata.csv"))
			       .withType(IdepCsvBean.class).build().parse();
		Namespace namespace = Namespace.getNamespace("mods", "http://www.loc.gov/mods/v3");
		Namespace namespacexlink = Namespace.getNamespace("xlink", "http://www.w3.org/1999/xlink");
		Namespace namespacexsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespaceCopyrightMD = Namespace.getNamespace("copyrightMD", "http://www.cdlib.org/inside/diglib/copyrightMD");
		String delim = "|";
		String delimDot = ".";
		String regex = "(?<!\\\\)" + Pattern.quote(delim);
		String regexDot = "(?<!\\\\)" + Pattern.quote(delimDot);
		
		for(IdepCsvBean cvsbean : beans) {
			System.out.println(cvsbean);
			
			Document jdomDoc = new Document();
			// create root element
			Element rootElement = new Element("mods", namespace);
			rootElement.addNamespaceDeclaration(namespacexlink);
			rootElement.addNamespaceDeclaration(namespacexsi);
			rootElement.setAttribute("schemaLocation",
					"http://www.loc.gov/mods/v3 http://www.loc.gov/standards/mods/v3/mods-3-4.xsd", namespacexsi);
			Element childOriginInfo = new Element("originInfo", namespace);
			Element childLocation = new Element("location", namespace);
			if(null != cvsbean.getFileName() ) {
				Element childIdentifier = new Element("identifier", namespace);
				childIdentifier.setAttribute("type", "local");
				childIdentifier.setAttribute("displayLabel", "File name");
				childIdentifier.addContent(cvsbean.getFileName());
				rootElement.addContent(childIdentifier);
			}
			
			if(null != cvsbean.getStreamingURL() ) {
				Element childIdentifier = new Element("identifier", namespace);
				childIdentifier.setAttribute("type", "uri");
				childIdentifier.setAttribute("displayLabel", "Streaming URL");
				childIdentifier.addContent(cvsbean.getStreamingURL());
				rootElement.addContent(childIdentifier);
			}
			
			if(null != cvsbean.getLocalID()) {
				Element childIdentifier = new Element("identifier", namespace);
				childIdentifier.setAttribute("type", "local");
				childIdentifier.addContent(cvsbean.getLocalID());
				rootElement.addContent(childIdentifier);
			}
			
			if(null != cvsbean.getCollection()) {
				for (String collection : cvsbean.getCollection().split(regex)) {
				Element childRelatedItem = new Element("relatedItem", namespace);
				childRelatedItem.setAttribute("type", "host");
				Element childTitleInfo = new Element("titleInfo", namespace);
				Element childTitle = new Element("title", namespace);
				childTitle.addContent(collection);
				childTitleInfo.addContent(childTitle);
				childRelatedItem.addContent(childTitleInfo);
				rootElement.addContent(childRelatedItem);
				}
			}
			
			// add idep porgram for cross collection search
			Element childRelatedItemProgram = new Element("relatedItem", namespace);
			childRelatedItemProgram.setAttribute("type", "program");
			Element childTitleInfoProgram = new Element("titleInfo", namespace);
			Element childTitleProgram = new Element("title", namespace);
			childTitleProgram.addContent("International Digital Ephemera Project");
			childTitleInfoProgram.addContent(childTitleProgram);
			childRelatedItemProgram.addContent(childTitleInfoProgram);
			rootElement.addContent(childRelatedItemProgram);
			
			
			if(null != cvsbean.getSeries()) {
				Element childRelatedItem = new Element("relatedItem", namespace);
				childRelatedItem.setAttribute("type", "series");
				Element childTitleInfo = new Element("titleInfo", namespace);
				Element childTitle = new Element("title", namespace);
				childTitle.addContent(cvsbean.getSeries());
				childTitleInfo.addContent(childTitle);
				childRelatedItem.addContent(childTitleInfo);
				rootElement.addContent(childRelatedItem);
			}
			
			if(null != cvsbean.getTypeOfResource()) {
				Element childTypeOfResource = new Element("typeOfResource", namespace);
				childTypeOfResource.addContent(cvsbean.getTypeOfResource());
				rootElement.addContent(childTypeOfResource);
			}
			
			if(null != cvsbean.getGenre()) {
				for (String genre : cvsbean.getGenre().split(regex)) {
					Element childTypeGenre = new Element("genre", namespace);
					childTypeGenre.addContent(genre);
					rootElement.addContent(childTypeGenre);
				}
			}
			
			if(null != cvsbean.getInstitution_repository()) {
				MultiValuedMap<String, String> repos = cvsbean.getInstitution_repository();
				MultiSet<String> columnames= repos.keys();
				for (String colName : columnames) {
					Element childPhysicalLocation = new Element("physicalLocation", namespace);
					childPhysicalLocation.addContent(repos.get(colName).iterator().next());
					childPhysicalLocation.setAttribute("type", "repository");
					childPhysicalLocation.setAttribute("displayLabel", "Repository Collection");
					childLocation.addContent(childPhysicalLocation);
				}
				
			}
			
			if(null != cvsbean.getCollectionName()) {
				Element childPhysicalLocation = new Element("physicalLocation", namespace);
				childPhysicalLocation.addContent(cvsbean.getCollectionName());
				childPhysicalLocation.setAttribute("type", "collection");
				childPhysicalLocation.setAttribute("displayLabel", "Repository Collection");
				childLocation.addContent(childPhysicalLocation);
			}
			
			if(null != cvsbean.getCollectionNumber()) {
				Element childPhysicalLocation = new Element("physicalLocation", namespace);
				childPhysicalLocation.addContent(cvsbean.getCollectionNumber());
				childPhysicalLocation.setAttribute("type", "collectionNumber");
				childPhysicalLocation.setAttribute("displayLabel", "Collection Number");
				childLocation.addContent(childPhysicalLocation);
			}
			
			if(null != cvsbean.getBox()) {
				Element childPhysicalLocation = new Element("physicalLocation", namespace);
				childPhysicalLocation.addContent(cvsbean.getBox());
				childPhysicalLocation.setAttribute("type", "boxNumber");
				childPhysicalLocation.setAttribute("displayLabel", "Box Number");
				childLocation.addContent(childPhysicalLocation);
			}
			
			if(null != cvsbean.getFolder()) {
				Element childPhysicalLocation = new Element("physicalLocation", namespace);
				childPhysicalLocation.addContent(cvsbean.getFolder());
				childPhysicalLocation.setAttribute("type", "folderNumber");
				childPhysicalLocation.setAttribute("displayLabel", "Folder Number");
				childLocation.addContent(childPhysicalLocation);
			}
			
			if(null != cvsbean.getLanguages() && !cvsbean.getLanguages().isEmpty()) {
				MultiValuedMap<String, String> languages = cvsbean.getLanguages();
				Collection<Entry<String, String>> languageMap = languages.entries();
				for (Entry<String, String> entry : languageMap) {
					if(null != entry.getValue() && entry.getValue().trim().length() > 5) {
						String[] langCol = entry.getValue().split(regex);
						Element childLanguage = new Element("language", namespace);
						Element childLanguageTermText = new Element("languageTerm", namespace);
						childLanguageTermText.addContent(langCol[0].trim());
						childLanguageTermText.setAttribute("type", "text");
						childLanguage.addContent(childLanguageTermText);
						Element childLanguageTermCode = new Element("languageTerm", namespace);
						childLanguageTermCode.addContent(langCol[1].trim());
						childLanguageTermCode.setAttribute("type", "code");
						childLanguageTermCode.setAttribute("authority", "iso639-2b");
						childLanguage.addContent(childLanguageTermCode);
						rootElement.addContent(childLanguage);
					}
				}
								

				
			}
			
			if(null != cvsbean.getDates() && !cvsbean.getDates().isEmpty()) {
				MultiValuedMap<String, String> dates = cvsbean.getDates();
				MultiSet<String> columnames= dates.keys();
				Element childDate = null;
				for(String columnname : columnames) {
					switch (columnname) {
					case "Date.created":
				   		if(dates.get(columnname).iterator().next().trim().length() > 0) {
				   			childDate = new Element("dateCreated", namespace);
							childDate.addContent(dates.get(columnname).iterator().next());							
							childOriginInfo.addContent(childDate);
				   		}
				   		break;
					case "Date.created (single)":
						   		if(dates.get(columnname).iterator().next().trim().length() > 0) {
						   			childDate = new Element("dateCreated", namespace);
									childDate.addContent(dates.get(columnname).iterator().next());
									childDate.setAttribute("encoding", "iso8601");
									childOriginInfo.addContent(childDate);
						   		}
						break;

					case "Date.created (start)":
						if(dates.get(columnname).iterator().next().trim().length() > 0) {
							 childDate = new Element("dateCreated", namespace);
							childDate.addContent(dates.get(columnname).iterator().next());
							childDate.setAttribute("encoding", "iso8601");
							childDate.setAttribute("point", "start");
							childOriginInfo.addContent(childDate);
						}	
						
						break;
					case "Date.created (end)":
						if(dates.get(columnname).iterator().next().trim().length() > 0) {
							 childDate = new Element("dateCreated", namespace);
							childDate.addContent(dates.get(columnname).iterator().next());
							childDate.setAttribute("encoding", "iso8601");
							childDate.setAttribute("point", "end");
							childOriginInfo.addContent(childDate);
						}
						break;					
					case "Date.issued":
				   		if(dates.get(columnname).iterator().next().trim().length() > 0) {
				   			childDate = new Element("dateIssued", namespace);
							childDate.addContent(dates.get(columnname).iterator().next());							
							childOriginInfo.addContent(childDate);
				   		}
				   		break;
					case "Date.issued (single)":
						   		if(dates.get(columnname).iterator().next().trim().length() > 0) {
						   			childDate = new Element("dateIssued", namespace);
									childDate.addContent(dates.get(columnname).iterator().next());
									childDate.setAttribute("encoding", "iso8601");
									childOriginInfo.addContent(childDate);
						   		}
						break;

					case "Date.issued (start)":
						if(dates.get(columnname).iterator().next().trim().length() > 0) {
							 childDate = new Element("dateIssued", namespace);
							childDate.addContent(dates.get(columnname).iterator().next());
							childDate.setAttribute("encoding", "iso8601");
							childDate.setAttribute("point", "start");
							childOriginInfo.addContent(childDate);
						}	
						
						break;
					case "Date.issued (end)":
						if(dates.get(columnname).iterator().next().trim().length() > 0) {
							 childDate = new Element("dateIssued", namespace);
							childDate.addContent(dates.get(columnname).iterator().next());
							childDate.setAttribute("encoding", "iso8601");
							childDate.setAttribute("point", "end");
							childOriginInfo.addContent(childDate);
						}
						break;


					default:
						break;
					}
				}
				
			}
			
			
			if(null != cvsbean.getSubjects() && !cvsbean.getSubjects().isEmpty()) {
				MultiValuedMap<String, String> subjects = cvsbean.getSubjects();
				MultiSet<String> columnames= subjects.keys();
				Element childSubject = null;
				for(String columnname : columnames) {
					switch (columnname) {
					case "Subject.name":
						if(subjects.get(columnname).iterator().next().trim().length() > 0) {
							for (String subjectName : subjects.get(columnname).iterator().next().trim().split(regex)) {
								 childSubject = new Element("subject", namespace);
									Element childName = new Element("name", namespace);
									Element childNamePart = new Element("namePart", namespace);
									childNamePart.addContent(subjectName);
									childName.addContent(childNamePart);
									childSubject.addContent(childName);
									rootElement.addContent(childSubject);
							}
							
						}
						break;
					case "Subject.topic":
						if(subjects.get(columnname).iterator().next().trim().length() > 0) {
							childSubject = new Element("subject", namespace);
							for (String topic : subjects.get(columnname).iterator().next().split(regex)) {
								Element childTopic = new Element("topic", namespace);
								childTopic.addContent(topic);
								childSubject.addContent(childTopic);
							}
							rootElement.addContent(childSubject);
						}	
						break;
					case "Subject.place":
						if(subjects.get(columnname).iterator().next().trim().length() > 0) {
							 childSubject = new Element("subject", namespace);
							Element childGeographic = new Element("geographic", namespace);
							childGeographic.addContent(subjects.get(columnname).iterator().next());
							childSubject.addContent(childGeographic);
							rootElement.addContent(childSubject);
						}	
						break;
					case "Subject.coordinates":
						if(subjects.get(columnname).iterator().next().trim().length() > 0) {
							 childSubject = new Element("subject", namespace);
							Element childCartographics = new Element("cartographics", namespace);
							Element childCoordinates = new Element("coordinates", namespace);
							childCoordinates.addContent(subjects.get(columnname).iterator().next());
							childCartographics.addContent(childCoordinates);
							childSubject.addContent(childCartographics);
							rootElement.addContent(childSubject);
						}	
						break;
					case "Subject.temporal":
						if(subjects.get(columnname).iterator().next().trim().length() > 0) {
							 childSubject = new Element("subject", namespace);
							 for (String temporal : subjects.get(columnname).iterator().next().split(regex)) {
								 Element childTemporal = new Element("temporal", namespace);
									childTemporal.addContent(temporal);
									childSubject.addContent(childTemporal);
							 }
							
							rootElement.addContent(childSubject);
						}	
						break;	
					default:
						break;
					}
				}
				
			}
			
			
			if (null != cvsbean.getTitles() && !cvsbean.getTitles().isEmpty()) {
				
				MultiValuedMap<String, String> titles = cvsbean.getTitles();
				MultiSet<String> columnames= titles.keys();
				
				for(String columnname : columnames) {
					if(titles.get(columnname).iterator().next().trim().length() > 0) {
						String[] titleCol = columnname.split(regex);
						Element childTitleInfo = new Element("titleInfo", namespace);
						Element childTitle = new Element("title", namespace);
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
				MultiSet<String> columnames= altTitles.keys();
				
				for(String columnname : columnames) {
					if(altTitles.get(columnname).iterator().next().trim().length() > 0) {
						String[] titleCol = columnname.split(regex);
						Element childTitleInfo = new Element("titleInfo", namespace);
						Element childTitle = new Element("title", namespace);
						childTitle.addContent(altTitles.get(columnname).iterator().next());
						if (titleCol.length > 1) {
							childTitle.setAttribute("lang", titleCol[1].trim());
						}
						childTitle.setAttribute("type","alternative");
						childTitleInfo.addContent(childTitle);
						rootElement.addContent(childTitleInfo);
					}
					
				}
				
				

			}
			
			if (null != cvsbean.getTranslated_title() && !cvsbean.getTranslated_title().isEmpty()) {
				
				MultiValuedMap<String, String> titles = cvsbean.getTranslated_title();
				MultiSet<String> columnames= titles.keys();
				
				for(String columnname : columnames) {
					if(titles.get(columnname).iterator().next().trim().length() > 0) {
						String[] titleCol = columnname.split(regex);
						Element childTitleInfo = new Element("titleInfo", namespace);
						Element childTitle = new Element("title", namespace);
						childTitle.addContent(titles.get(columnname).iterator().next());
						childTitle.setAttribute("type","translated");
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
				MultiSet<String> columnames= creators.keys();
				for(String columnname : columnames) {
					if(creators.get(columnname).iterator().next().trim().length() > 0) {
						String[] creatorCol = columnname.split(regex);
					
						Element childName = new Element("name", namespace);
						// for( String namePart : record.get("columname").split(regex)) {
						Element childNamePart = new Element("namePart", namespace);
						childNamePart.addContent(creators.get(columnname).iterator().next());
						childName.addContent(childNamePart);

						// }
						Element childNameRole = new Element("role", namespace);
						Element childNameRoleTerm = new Element("roleTerm", namespace);
						String[] creatorRole = creatorCol[0].split(regexDot);
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
			
			if (null != cvsbean.getContributors() && !cvsbean.getContributors().isEmpty()) {
				MultiValuedMap<String, String> contributors = cvsbean.getContributors();
				MultiSet<String> columnames= contributors.keys();
				for(String columnname : columnames) {
					if(contributors.get(columnname).iterator().next().trim().length() > 0) {
						for(String contributor : contributors.get(columnname).iterator().next().split(regex)) {
							String[] contributorCol = columnname.split(regex);
							
							Element childName = new Element("name", namespace);
							// for( String namePart : record.get("columname").split(regex)) {
							Element childNamePart = new Element("namePart", namespace);
							childNamePart.addContent(contributor);
							childName.addContent(childNamePart);

							// }
							Element childNameRole = new Element("role", namespace);
							Element childNameRoleTerm = new Element("roleTerm", namespace);
							String[] contributorRole = contributorCol[0].split(regexDot);
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
				MultiSet<String> columnames= publishers.keys();
				for(String columnname : columnames) {
					String[] pubCol = columnname.split(regexDot);
					if (publishers.get(columnname).iterator().next().trim().length() > 0) {
						if (pubCol.length == 1) {
							Element childPublisher = new Element("publisher", namespace);
							childPublisher.addContent(publishers.get(columnname).iterator().next());
							childOriginInfo.addContent(childPublisher);
							
						}

						if (pubCol.length > 1) {
							if (publishers.get(columnname).iterator().next().trim().length() > 0) {
								Element childPlace = new Element(pubCol[1], namespace);
								Element childPlaceTerm = new Element("placeTerm", namespace);
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
				MultiSet<String> columnames= physDescs.keys();
				for(String columnname : columnames) {
					String[] phyDesCol = columnname.split(regex);
					if (physDescs.get(columnname).iterator().next().trim().length() > 0) {
						String[] element = phyDesCol[0].split(regexDot);
						Element childPhysicalDescription = new Element("physicalDescription", namespace);
						Element extent = new Element(element[1], namespace);
						extent.addContent(physDescs.get(columnname).iterator().next());
						// add type from phyDescCol[1] to displayLabel = "type"
						childPhysicalDescription.addContent(extent);
						rootElement.addContent(childPhysicalDescription);

					}

				}
			}

			if (null != cvsbean.getAbstracts() && !cvsbean.getAbstracts().isEmpty()) {
				
				MultiValuedMap<String, String> abstracts = cvsbean.getAbstracts();
				MultiSet<String> columnames= abstracts.keys();
				for(String columnname : columnames) {
					String[] titleCol = columnname.split(regex);
					if (abstracts.get(columnname).iterator().next().trim().length() > 0) {
						Element childDescription = new Element("abstract", namespace);
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
				
				Set<String> columnames= notes.keySet();
				for(String columnname : columnames) {
					String[] noteCol = columnname.split(regex);
					Iterator<String> notesValues = notes.get(columnname).iterator();
					while(notesValues.hasNext()) {
						String notevalue = notesValues.next();
						if(notevalue.trim().length() > 0) {
							Element childNote = new Element("note", namespace);
							if (noteCol.length > 1) {

								childNote.setAttribute("lang", noteCol[1].trim());

							}
							String[] noteType = noteCol[0].split(regexDot);
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

			if (null != cvsbean.getRights() && !cvsbean.getRights().isEmpty()) {
				
				MultiValuedMap<String, String> rights = cvsbean.getRights();
				MultiSet<String> columnames= rights.keys();
				Element childAccessCondition = new Element("accessCondition", namespace);
				
				Element childCopyright = new Element("copyright", namespaceCopyrightMD);
				childCopyright.setAttribute("schemaLocation",
						"http://www.cdlib.org/inside/diglib/copyrightMD http://www.cdlib.org/groups/rmg/docs/copyrightMD.xsd",
						namespacexsi);
				//childCopyright.setAttribute("type","use and reproduction"); it doesnt exist in schema
				for(String columnname : columnames) {
					String[] rightsCol = columnname.split(regexDot);
					
					switch (rightsCol[1]) {
					/*case "URI":
						//not allowed in copyright schema childCopyright.setAttribute("href", rights.get(columnname).iterator().next(),namespacexlink);
						
						break;*/
					case "copyrightStatus":
						childCopyright.setAttribute("copyright.status", rights.get(columnname).iterator().next());
						//childCopyright.setAttribute("copyright.status", "unknown");
						break;
					case "publicationStatus":
						childCopyright.setAttribute("publication.status", rights.get(columnname).iterator().next());
						break;
					case "servicesContact":
						Element services = new Element("services", namespaceCopyrightMD);
						Element contact = new Element("contact", namespaceCopyrightMD);
						contact.addContent(rights.get(columnname).iterator().next());
						services.addContent(contact);
						childCopyright.addContent(services);
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
					String[] volCol = columnname.split(regex);
					if (volumes.get(columnname).iterator().next().trim().length() > 0) {
						Element childPart = new Element("part", namespace);
						String[] values = volumes.get(columnname).iterator().next().split(regex);
						
						for (int i = 0; i < values.length; i++) {
							if("date".equals(volCol[i].trim())) {
								Element childPartDate = new Element("Date", namespace);	
								childPartDate.addContent(values[i].trim());
								childPart.addContent(childPartDate);
								continue;
							}
							
								Element childPartDetail = new Element("detail", namespace);							
								childPartDetail.setAttribute("type", volCol[i].trim());
								Element childNumber = new Element("number",namespace);
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
					new FileWriter("\\\\svm_dlib\\DLIngest\\embroideries\\mods\\"
							+ cvsbean.getFileName().replaceFirst("pdf", "xml").replaceFirst("tif","xml").replaceFirst("mp4","xml").replaceFirst("mp3","xml")));

		}

	}

}
