package edu.ucla.library.dep.generateMods.services;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.opencsv.bean.CsvToBeanBuilder;

import edu.ucla.library.dep.generateMods.elements.ModsAbstract;
import edu.ucla.library.dep.generateMods.elements.ModsIdentifier;
import edu.ucla.library.dep.generateMods.elements.ModsName;
import edu.ucla.library.dep.generateMods.elements.ModsNote;
import edu.ucla.library.dep.generateMods.elements.ModsOrigin;
import edu.ucla.library.dep.generateMods.elements.ModsPart;
import edu.ucla.library.dep.generateMods.elements.ModsPhysicalDescription;
import edu.ucla.library.dep.generateMods.elements.ModsPhysicalLocation;
import edu.ucla.library.dep.generateMods.elements.ModsRelatedItem;
import edu.ucla.library.dep.generateMods.elements.ModsRights;
import edu.ucla.library.dep.generateMods.elements.ModsTitle;
import edu.ucla.library.dep.generateMods.model.CsvBean;
import edu.ucla.library.dep.generateMods.util.Constants;

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
    	
        List<CsvBean> beans = new CsvToBeanBuilder<CsvBean>(new FileReader(inFilePath))
                .withType(CsvBean.class).build().parse();

        for (CsvBean csvbean : beans) {
            System.out.println(csvbean);

            // create root element
            Element rootElement = baseElement.createRootElement();
            
            // Add identifiers
            
            ModsIdentifier identifier = new ModsIdentifier();
            identifier.createIdentifiers(rootElement, csvbean);
            
            ModsRelatedItem items = new ModsRelatedItem();
            items.createRelatedItems(rootElement, projectName, projectURL, program, csvbean);
          

            if (null != csvbean.getTypeOfResource()) {
                baseElement.createElementFromString(rootElement, "typeOfResource", Constants.namespace, csvbean.getTypeOfResource(), null,
                        null, null, null, null, null);
            }

            if (null != csvbean.getGenre()) {
                for (String genre : csvbean.getGenre().split(Constants.regex)) {
                    baseElement.createElementFromString(rootElement, "genre", Constants.namespace, genre, null, null, null, null, null, null);
                }
            }
          

            ModsPhysicalLocation modsPhysicalLocation = new ModsPhysicalLocation(csvbean);
            modsPhysicalLocation.createPhysicalLocationElements(rootElement);
            
            processcsv.createLanguage(rootElement, Constants.namespace, csvbean);
           
            processcsv.createSubjectElement(rootElement, Constants.namespace, csvbean);

            ModsTitle modsTitle = new ModsTitle();
            modsTitle.createModsTitle(csvbean, rootElement);

            ModsName modsName = new ModsName();
            modsName.createNames(csvbean, rootElement);

            ModsOrigin modsOrigin = new ModsOrigin();
            modsOrigin.createOrigin(csvbean, rootElement);

            ModsPhysicalDescription modsPhysicalDesc = new ModsPhysicalDescription();
            modsPhysicalDesc.createPhyicalDescription(csvbean, rootElement);
            
            ModsAbstract modsAbstract = new ModsAbstract();
            modsAbstract.createAbstract(csvbean, rootElement);

            ModsNote notes = new ModsNote();
            notes.createNotes(csvbean, rootElement);
            
            ModsRights rights	= new ModsRights();
            rights.createAccessionCondition(csvbean, rootElement);
            
            ModsPart part = new ModsPart();
            part.createPart(rootElement, csvbean);
            
            // write to xml file
            createFile(rootElement, csvbean);

        }
    }

    public void createFile(Element rootElement, CsvBean cvsbean) throws IOException {
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
