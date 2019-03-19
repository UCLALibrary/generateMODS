package edu.ucla.library.dlcs.app;



import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class ThumbnailGrabber {
    private static final String THUMBS_DIR = "C:\\Temp\\nails\\";

    public ThumbnailGrabber() {
    }

    public static void main(String[] args) {

        /* String testfile = "http://library.duke.edu/digitalcollections/hasm_n0095/";
     String temp =    testfile.substring(47,testfile.lastIndexOf("/"));
        System.out.println("http://library.duke.edu/digitalcollections/hasm_"+temp+"_"+temp+"-1/");
     */
        // TODO Specify the path to the harvested sheet music collection
        String path = "\\\\lit347v\\d$\\OAI\\dds\\harvested_records\\mods\\umw";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        try {
            createThumbs(listOfFiles, path);
            // Used to rename file from Washington Univesity in St. Louis.
            // renameThumbs(listOfFiles,path);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Done");
    }

    private static void createThumbs(File[] listOfFiles, 
                                     String path) throws FileNotFoundException, 
                                                         IOException {
        // York University Libraries, Sheet Music Collections: "http://hdl.handle.net";
        // National Library of Australia: "http://nla.gov.au/"
        // University of Tennessee: "http://diglib.lib.utk.edu"
        // Mississippi State University, Charles Templeton Sheet Music Collection: "http://cdm16631.contentdm.oclc.org/cdm/ref/collection/SheetMusic/id/"
        // University of Oregon: "http://oregondigital.org/u?/sheetmusic,"
        // University of Washington Pacific Northwest Sheet Music Collection: "http://content.lib.washington.edu/u?/sm,"
        // Baylor: "http://contentdm.baylor.edu/u?/01amp,"
        //Baylor New:"http://digitalcollections.baylor.edu/cdm/ref/collection/fa-spnc/id/"
        // Temple University: "http://digital.library.temple.edu/u?/p15037coll1,"
        // University of North Carolina at Chapel Hill, 19th-Century American Sheet Music: "http://dc.lib.unc.edu/u?/sheetmusic,"
        // The University of Iowa Libraries, Historic Sheet Music Collection: "http://digital.lib.uiowa.edu/u?/sheetmusic,"
        // Ball State University, Sheet Music from the Hague Collection: "http://libx.bsu.edu/u?/ShtMus,"
        // Southern Illinois University Edwardsville, KMOX Popular Sheet Music: "http://collections.carli.illinois.edu/u?/sie_kmox,"
        // University of Illinois at Chicago, Sheet Music Collection: "http://collections.carli.illinois.edu/u?/uic_smusic,"
        // University of St. Francis, Barbara A. Cooke Sheet Music Collection: "http://collections.carli.illinois.edu/u?/usf_cooke,"
        // University of South Carolina, Hemrick Nathan Salley Family Sheet Music: "http://digital.tcl.sc.edu/u?/salleysheet,"
        // Auburn http://content.lib.auburn.edu:81/u?/pianobench,
		/// Tulane http://cdm16313.contentdm.oclc.org/cdm/ref/collection/p16313coll30/id/
        // American University http://ausheetmusic.wrlc.org/u?/sheetmusic,
    	// University Of  Winsconsin-Milwaukee  UWM Sheet Music Collection http://cdm17272.contentdm.oclc.org/cdm/ref/collection/sheetmusic/id/
        // TODO Update the logic to assemble the thumbnail URL for the collection being harvest.
        String test ="http://cdm17272.contentdm.oclc.org/cdm/ref/collection/sheetmusic/id/"; 
        //psg: added this for re-harvest
        String thumbExistsPath ="C:\\Temp\\nails\\";
        int total = listOfFiles.length;
        int count = 1;

        // create a log file
        FileOutputStream fos = new FileOutputStream(new File("C:\\Temp\\thumbnail01222019.log"));
        OutputStreamWriter osw = new OutputStreamWriter(fos);

        for (int i = 0; i < total; i++) {
            System.out.println("Processing " + count++ + " out of " + total);
            if (listOfFiles[i].isFile()) {

                try {
                //  PSG added this for re-harvest.
                
               
                    if( new File(thumbExistsPath+listOfFiles[i].getName().replaceAll("_","_5F").replaceFirst(".xml","").trim()+".jpg").exists()){
                         osw.write(listOfFiles[i].getName().replaceAll("_","_5F").replaceFirst(".xml","").trim()+".jpg eXIST \n");
                        continue;
                     }
                    File file = new File(path + "\\" + listOfFiles[i].getName());
                    SAXBuilder saxBuilder = new SAXBuilder();

                    Document jDocXml = saxBuilder.build(file);
                    Element recordsElement = jDocXml.getRootElement();
                    Element recordElement;
                    Element recordElementURL;
                    List child = recordsElement.getChildren();
                    boolean found = false;

                    if (child != null) {
                        Iterator iterate = child.iterator();
                        while (iterate.hasNext()) {

                            recordElement = (Element)iterate.next();
                            if ("location".equals(recordElement.getName())) {
                                List children = recordElement.getChildren();
                                if (null != children) {
                                    Iterator iterateLocation = children.iterator();
                                    while (iterateLocation.hasNext()) {
                                        recordElementURL = (Element)iterateLocation.next();
                                        if ("url".equals(recordElementURL.getName()) && recordElementURL.getTextTrim().indexOf(test) != -1) {
                                            found = true;
                                            BufferedImage image = null;
                                            String thumbnailurl = null;
                                            
                                            try {

                                                //if(recordElementURL.getTextTrim().indexOf("starr")!= -1){
                                                //    thumbnailurl = recordElementURL.getTextTrim().replaceFirst("http://purl.dlib.indiana.edu/iudl/lilly/starr/","http://purl.dlib.indiana.edu/iudl/lilly/starr/thumbnail/");
                                                //}else if(recordElementURL.getTextTrim().indexOf("devincent")!= -1){
                                                thumbnailurl = recordElementURL.getTextTrim();
                                                String temp = thumbnailurl.substring(test.length());
                                                // TODO Update the logic to pull the thumbnail for the collection in question.
                                                // Tulane University, Guiseppe Ferrata Score Collection
                                                //thumbnailurl = "http://cdm16313.contentdm.oclc.org/utils/getthumbnail/collection/p16313coll30/id/"+temp;
                                                //American University
                                                //thumbnailurl = " http://ausheetmusic.wrlc.org/utils/getthumbnail/collection/sheetmusic/id/"+temp;
                                                // York University Libraries, Sheet Music Collections
                                               //if (temp.endsWith("8006") || temp.endsWith("8007") || temp.endsWith("8014") || temp.endsWith("8015")) {
                                               //     thumbnailurl = "http://yorkspace.library.yorku.ca/dspace/bitstream/handle" + temp + "/0001.jpg?sequence=3";
                                                //} else {
                                                 //   thumbnailurl = "http://yorkspace.library.yorku.ca/dspace/bitstream/handle" + temp + "/0001.jpg";
                                                //}
                                                //Auburn Pianobench
                                                 /*thumbnailurl = "http://content.lib.auburn.edu:81/cgi-bin/thumbnail.exe?CISOROOT=/pianobench&CISOPTR=" + temp;*/
                                                // Oregon -- ContentDM
                                                //thumbnailurl = "http://oregondigital.org/cgi-bin/thumbnail.exe?CISOROOT=/sheetmusic&CISOPTR=" + temp;
                                                // University of Tennessee
                                                //String tempName = listOfFiles[i].getName().replaceFirst(".xml","");
                                                 //thumbnailurl = test + "/utsmc/data/" + tempName + "/" + tempName + "_0001.jpg";
                                                // National Library of Australia
                                                 //thumbnailurl += "-s1-t.jpg";
                                                // University of Washington Pacific Northwest Sheet Music Collection
                                                //thumbnailurl = "http://content.lib.washington.edu/cgi-bin/thumbnail.exe?CISOROOT=/sm&CISOPTR=" + temp;
                                                // Baylor
                                                //thumbnailurl = "http://digitalcollections.baylor.edu/utils/getthumbnail/collection/fa-spnc/id/"+temp;
                                                //thumbnailurl = "http://contentdm.baylor.edu/cgi-bin/thumbnail.exe?CISOROOT=/01amp&CISOPTR=" + temp;
                                                // Mississippi State University
                                                //thumbnailurl = "https://cdm16631.contentdm.oclc.org/digital/api/singleitem/collection/SheetMusic/id/" + temp + "/thumbnail";
                                                // UWM sheet music collection
                                                thumbnailurl = "https://cdm17272.contentdm.oclc.org/digital/api/singleitem/collection/sheetmusic/id/" + temp + "/thumbnail";
                                                
                                                // Temple University
                                                //thumbnailurl = "http://digital.library.temple.edu/cgi-bin/thumbnail.exe?CISOROOT=/p15037coll1&CISOPTR=" + temp;
                                                // University of North Carolina at Chapel Hill, 19th-Century American Sheet Music:
                                                //thumbnailurl = "http://dc.lib.unc.edu/cgi-bin/thumbnail.exe?CISOROOT=/sheetmusic&CISOPTR=" + temp;
                                                // The University of Iowa Libraries, Historic Sheet Music Collection
                                                //thumbnailurl = "http://digital.lib.uiowa.edu/cgi-bin/thumbnail.exe?CISOROOT=/sheetmusic&CISOPTR=" + temp;
                                                // Ball State University, Sheet Music from the Hague Collection
                                                //thumbnailurl = "http://libx.bsu.edu/cgi-bin/thumbnail.exe?CISOROOT=/ShtMus&CISOPTR=" + temp;
                                                // Southern Illinois University Edwardsville, KMOX Popular Sheet Music
                                                //thumbnailurl = "http://collections.carli.illinois.edu/cgi-bin/thumbnail.exe?CISOROOT=/sie_kmox&CISOPTR=" + temp;
                                                // University of Illinois at Chicago, Sheet Music Collection
                                                //thumbnailurl = "http://collections.carli.illinois.edu/cgi-bin/thumbnail.exe?CISOROOT=/uic_smusic&CISOPTR=" + temp;
                                                // University of St. Francis, Barbara A. Cooke Sheet Music Collection:
                                                //thumbnailurl = "http://collections.carli.illinois.edu/cgi-bin/thumbnail.exe?CISOROOT=/usf_cooke&CISOPTR=" + temp;
                                                // University of South Carolina, Hemrick Nathan Salley Family Sheet Music
                                                //thumbnailurl = "http://digital.tcl.sc.edu/cgi-bin/thumbnail.exe?CISOROOT=/salleysheet&CISOPTR=" + temp;
                                                
                                                //System.out.println(thumbnailurl);
                                                //}

                                                if (thumbnailurl != null) {
                                                    URL url = new URL(thumbnailurl);
                                                    HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
                                                    urlConn.setRequestProperty("User-Agent","OAIHarvester/2.0");
                                                    urlConn.setRequestProperty("Accept-Encoding","gzip,deflate");

                                                    //System.out.println("HTTP STATUS CODE: " + urlConn.getResponseCode());

                                                    if (urlConn.getResponseCode() == 404) {
                                                        osw.write(thumbnailurl + " not found.\n");
                                                    }

                                                    InputStream input = urlConn.getInputStream();
                                                    image = ImageIO.read(input);

                                                    //ImageIO.write(image, "jpeg",new File("\\\\lit304v\\d$\\OAI\\thumbnails\\yorku\\"+listOfFiles[i].getName().replaceAll("_","_5F").replaceFirst(".xml","").trim()+".jpg"));
                                                    if(null != image){
                                                        ImageIO.write(image,"jpeg", new File("\\\\lit347v\\d$\\OAI\\thumbnails\\umw\\" + listOfFiles[i].getName().replaceAll("_","_5F").replaceFirst(".xml", "").trim() + ".jpg"));
                                                    }
                                                }


                                            } catch (IOException ioe) {
                                                osw.write(thumbnailurl + " " + ioe.getMessage() + " \n");
                                                ioe.printStackTrace();
                                            } catch (Exception ex){
                                               ex.printStackTrace();
                                                
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("XML File => " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        osw.flush();
        osw.close();
    }
    
    private static void renameThumbs(File[] listOfFiles, 
                                     String path) throws FileNotFoundException, 
                                                         IOException {
        
        // TODO Update the logic to assemble the thumbnail URL for the collection in question.
        String test1 = "http://library.wustl.edu/units/music/supplcat/";        
        String test2 = "http://www.library.wustl.edu/units/music/supplcat/";
        int total = listOfFiles.length;
        int count = 1;

        // create a log file
        FileOutputStream fos = new FileOutputStream(new File("C:\\thumbnail.log"));
        OutputStreamWriter osw = new OutputStreamWriter(fos);

        for (int i = 0; i < total; i++) {
            System.out.println("Processing " + count++ + " out of " + total);
            if (listOfFiles[i].isFile()) {

                try {
                    File file = new File(path + "\\" + listOfFiles[i].getName());
                    SAXBuilder saxBuilder = new SAXBuilder();
                    
                    Document jDocXml = saxBuilder.build(file);
                    Element recordsElement = jDocXml.getRootElement();
                    Element recordElement;
                    Element recordElementURL;
                    List child = recordsElement.getChildren();
                    boolean found = false;

                    if (child != null) {
                        Iterator iterate = child.iterator();
                        while (iterate.hasNext()) {

                            recordElement = (Element)iterate.next();
                            
                            if ("location".equals(recordElement.getName())) {
                                List children = recordElement.getChildren();
                                if (null != children) {
                                    Iterator iterateLocation = children.iterator();
                                    while (iterateLocation.hasNext()) {
                                        recordElementURL = (Element)iterateLocation.next();

                                        if ("url".equals(recordElementURL.getName())) { 
                                            String thumbnail = recordElementURL.getTextTrim();
                                            String temp = "";
                                            
                                            if (thumbnail.startsWith(test1)) {
                                                temp = thumbnail.substring(test1.length());
                                            } else {
                                                temp = thumbnail.substring(test2.length());
                                            }

                                            try {
                                                thumbnail = THUMBS_DIR + temp.replaceAll(".pdf",".jpg");
                                                File thumbFile = new File(thumbnail);
                                                if (thumbnail != null && thumbFile.exists()) {
                                                    File changeFilename = new File(THUMBS_DIR + listOfFiles[i].getName().replaceAll(".xml", ".jpg"));
                                                    thumbFile.renameTo(changeFilename);
                                                    found = true;
                                                } else {
                                                    osw.write(listOfFiles[i].getName() + " " + thumbnail + " not found.\n");
                                                    found = false;
                                                }
                                            } catch (Exception ioe) {
                                                ioe.printStackTrace();
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("XML File => " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        osw.flush();
        osw.close();
    }
}