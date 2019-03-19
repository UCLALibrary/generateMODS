package edu.ucla.library.dlcs.app;



import java.io.File;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import org.apache.solr.client.solrj.SolrServer;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;


public class SetThumbnailFlagInSolr {
    private static SolrServer server;

    public SetThumbnailFlagInSolr() throws MalformedURLException {

      
                    
           

           

    	server = new HttpSolrServer( "http://p-w-solr410master01.library.ucla.edu/solr/sheetmusic");
    	System.out.println(server.toString());
            

    }

    public static void main(String[] args) throws MalformedURLException, 
                                                  Exception {
    	SetThumbnailFlagInSolr setThumbnailFlagSolr = new SetThumbnailFlagInSolr();
        setThumbnailFlagSolr.updateSolr();
    }
    
    public void updateSolr() throws Exception {
        String path = "\\\\lit312v\\d$\\OAI\\thumbnails\\umw";
        QueryResponse responseTemp;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int count = 1;
        int total = listOfFiles.length;
        try{
       // for(int i = 0; i < total; i++){
        //    System.out.println("Processing " + count++ + " out of " + total);
        //    if (listOfFiles[i].isDirectory()) {
                File[] listOfFilesthumbs = listOfFiles;//[i].listFiles();
                int countThumb = 1;
                int totalThumb = listOfFilesthumbs.length;
                for(int j = 0; j < totalThumb; j++){
                    System.out.println("Processing " + countThumb++ + " out of " + totalThumb);
                    
                    if (listOfFilesthumbs[j].isFile()) {
                        String solrId = listOfFilesthumbs[j].getName().replaceAll("_5F","_").replaceFirst(".jpg","").trim()+".xml";
                        ModifiableSolrParams params = new ModifiableSolrParams();
                        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
                        params.set("q","id:"+solrId+" and thumbnailExists:false");
                        params.set("defType", "edismax");
                        params.set("start", "0");
                        params.set("rows", "1");
                        responseTemp = server.query(params);
                        docs = new ArrayList<SolrInputDocument>();
                        if(responseTemp != null){
                           
                            if(null != responseTemp.getResults()){
                                long totalrowsTemp =responseTemp.getResults().getNumFound();
                                if(totalrowsTemp > 0){
                                Iterator<SolrDocument> iter = responseTemp.getResults().iterator();
                                while (iter.hasNext()) {
                                    SolrDocument resultDoc = iter.next();
                                    SolrInputDocument inputdoc = new SolrInputDocument();
                                    
                                   inputdoc = ClientUtils.toSolrInputDocument(resultDoc);

                                    inputdoc.setField("thumbnailExists",true);
                                    docs.add(inputdoc);
                                }
                                /*server.add(docs);
                                server.commit();*/
                                    UpdateRequest req = new UpdateRequest();   
                                    req.setAction(AbstractUpdateRequest.ACTION.COMMIT,false,false);                       
                                     req.add( docs );
                                     UpdateResponse rsp = req.process( server );
                                    int status =  rsp.getStatus();
                                    System.out.println(status);
                                }
                            }
                        }
                        
                    }
                }
           // }
        //}
        }catch(Exception ex){
            ex.printStackTrace();
            
        }
       /* int start = 0;
        int rows = 1000;
        long totalrows = 0;
        QueryResponse response;
        ModifiableSolrParams params = new ModifiableSolrParams();
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        String thumbExistsPath = "\\\\lit304v\\d$\\OAI\\thumbnails\\";
        do{
            try {
                
                params.set("q","thumbnailExists:false");
                params.set("defType", "edismax");
                params.set("start", start);
                params.set("rows", rows);
                response = server.query(params);
                docs = new ArrayList<SolrInputDocument>();
                if(response != null){
                   
                    if(null != response.getResults()){
                        totalrows =response.getResults().getNumFound();
                        if(totalrows > 0){
                        Iterator<SolrDocument> iter = response.getResults().iterator();
                        while (iter.hasNext()) {
                            SolrDocument resultDoc = iter.next();
                            SolrInputDocument inputdoc = new SolrInputDocument();
                            Collection names = resultDoc.getFieldNames();
                            if(null != names){
                                Iterator itr = names.iterator();
                               
                                while(itr.hasNext()){
                                    String fieldName = (String)itr.next();
                                    if(!fieldName.equals("thumbnailExists")){
                                        if(fieldName.lastIndexOf("keyword") != -1){
                                            inputdoc.addField(fieldName,resultDoc.getFieldValues(fieldName));
                                        }else{
                                        inputdoc.addField(fieldName,resultDoc.getFieldValue(fieldName));
                                        }
                                    }
                                }
                              
                                
                            }
                            // code to check if thumbnail exists on server
                            String collectionKey =  (String)resultDoc.getFieldValue("collectionKey");
                            String fileName = (String)resultDoc.getFieldValue("fileName");
                             if( new File(thumbExistsPath+collectionKey+ "\\" +fileName.replaceAll("_","_5F").replaceFirst(".xml","").trim()+".jpg").exists()){
                                
                                  inputdoc.addField("thumbnailExists","true"); 
                              }else{
                                  inputdoc.addField("thumbnailExists","false"); 
                              }
                             docs.add(inputdoc);
                        }
                        UpdateRequest req = new UpdateRequest();   
                        req.setAction(AbstractUpdateRequest.ACTION.COMMIT,false,false);                       
                         req.add( docs );
                         UpdateResponse rsp = req.process( server );
                       int status =  rsp.getStatus();
                        }
                    }
                }
                }catch(Exception ex){
                   
                    ex.printStackTrace();
                    
                    
                  
                }
               
        }while(totalrows > 0);*/
    }
}
