package edu.ucla.library.dep.generateMods.elements;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections4.MultiValuedMap;
import org.jdom2.Element;

import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

public class ModsNote {

	public void createNotes(CsvBean csvbean, Element rootElement) {
		if (null != csvbean.getNotes() && !csvbean.getNotes().isEmpty()) {

            MultiValuedMap<String, String> notes = csvbean.getNotes();

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

	}
}
