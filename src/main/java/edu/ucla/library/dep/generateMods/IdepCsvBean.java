package edu.ucla.library.dep.generateMods;

import org.apache.commons.collections4.MultiValuedMap;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;

public class IdepCsvBean {
	
	 @CsvBindByName(column = "File name")
     private String fileName;
	 
	 @CsvBindByName(column = "Streaming URL")
     private String streamingURL;
	 
	 @CsvBindByName(column = "local ID")
     private String localID;
	 
	 @CsvBindByName(column = "License")
     private String license;
	 
	 public String getLocalRightsStatement() {
		return localRightsStatement;
	}


	public void setLocalRightsStatement(String localRightsStatement) {
		this.localRightsStatement = localRightsStatement;
	}


	public String getRelated_resource() {
		return related_resource;
	}


	public void setRelated_resource(String related_resource) {
		this.related_resource = related_resource;
	}

	@CsvBindByName(column = "Local rights statement")
	 private String localRightsStatement;
	 
	 public String getLicense() {
		return license;
	}


	public void setLicense(String license) {
		this.license = license;
	}
	
	@CsvBindByName(column = "Related resource")
    private String related_resource;

	@CsvBindAndJoinByName(column = "(Collection|Digital Collection Title)", elementType = String.class)
     private MultiValuedMap<String, String> collection;
	 
	 @CsvBindByName(column = "Series")
     private String series;
	 
	 @CsvBindByName(column = "Subseries")
     private String subSeries;
	 
	 
	 public String getSubSeries() {
		return subSeries;
	}


	public void setSubSeries(String subSeries) {
		this.subSeries = subSeries;
	}
	 
	 @CsvBindByName(column = "TypeOfResource")
     private String typeOfResource;
	 
	 @CsvBindByName(column = "Genre")
     private String genre;
	 
	 @CsvBindAndJoinByName(column = "(Institution|Insitution)/Repository", elementType = String.class)
     private MultiValuedMap<String, String> institution_repository;
	 
	 @CsvBindAndJoinByName(column = "(collection name|Archival collection name )", elementType = String.class)
     private MultiValuedMap<String, String> collectionName;
	 
	 @CsvBindAndJoinByName(column = "(collection number|Archival collection number or identifier)", elementType = String.class)
     private MultiValuedMap<String, String> collectionNumber;
	 
	 @CsvBindAndJoinByName(column = "(box|box number)", elementType = String.class)
     private MultiValuedMap<String, String> box;
	 
	 @CsvBindAndJoinByName(column = "(folder|folder number)", elementType = String.class)
     private MultiValuedMap<String, String> folder;
	 
	 @CsvBindAndJoinByName(column = "(TRANSLATED TITLE|Alternative title).*", elementType = String.class)
     private MultiValuedMap<String, String> translated_title;
	 
	 @CsvBindAndJoinByName(column = "(ALT TITLE|English translation of title).*", elementType = String.class)
     private MultiValuedMap<String, String> alt_titles;
	
	 
	 public String getFileName() {
		return fileName;
	}


	
	/*public MultiValuedMap<String, String> getTracks() {
		return tracks;
	}


	public void setTracks(MultiValuedMap<String, String> tracks) {
		this.tracks = tracks;
	}*/


	@CsvBindAndJoinByName(column = "(TITLE|Original language title).*", elementType = String.class)
     private MultiValuedMap<String, String> titles;
	 

     @CsvBindAndJoinByName(column = "CREATOR.*", elementType = String.class)
     private MultiValuedMap<String, String> creators;
     
     @CsvBindAndJoinByName(column = "PUBLISHER.*", elementType = String.class)
     private MultiValuedMap<String, String> publishers;
     
     @CsvBindAndJoinByName(column = "(DATE|Date (human)).*", elementType = String.class)
     private MultiValuedMap<String, String> dates;
     
     @CsvBindAndJoinByName(column = "PHYSICALDESCRIPTION.*|Extent|Dimensions|Medium", elementType = String.class)
     private MultiValuedMap<String, String> physicalDescription;  
     
	@CsvBindAndJoinByName(column = "LANGUAGE.*", elementType = String.class)
     private MultiValuedMap<String, String> languages;
     
     @CsvBindAndJoinByName(column = "ABSTRACT.*", elementType = String.class)
     private MultiValuedMap<String, String> abstracts;
     
     @CsvBindAndJoinByName(column = "NOTE.*", elementType = String.class)
     private MultiValuedMap<String, String> notes;
     
     @CsvBindAndJoinByName(column = "SUBJECT.*|Latitude/longitude", elementType = String.class)
     private MultiValuedMap<String, String> subjects;
     
     @CsvBindAndJoinByName(column = "RIGHTS.*", elementType = String.class)
     private MultiValuedMap<String, String> rights;
     
     @CsvBindAndJoinByName(column = "VOLUME.*", elementType = String.class)
     private MultiValuedMap<String, String> volumes;
     
     @CsvBindAndJoinByName(column = "CONTRIBUTOR.*", elementType = String.class)
     private MultiValuedMap<String, String> contributors;
     

   //  @CsvBindAndJoinByName(column = "Track[0-9]+", elementType = String.class, mapType = HashSetValuedHashMap.class, required = true)
  //   private MultiValuedMap<String, String> tracks;
     
     
     public void setFileName(String fileName) {
 		this.fileName = fileName;
 	}


 	public String getLocalID() {
 		return localID;
 	}


 	public void setLocalID(String localID) {
 		this.localID = localID;
 	}


 	public MultiValuedMap<String, String> getCollection() {
 		return collection;
 	}


 	public void setCollection(MultiValuedMap<String, String> collection) {
 		this.collection = collection;
 	}


 	public String getSeries() {
 		return series;
 	}


 	public void setSeries(String series) {
 		this.series = series;
 	}


 	public String getTypeOfResource() {
 		return typeOfResource;
 	}


 	public void setTypeOfResource(String typeOfResource) {
 		this.typeOfResource = typeOfResource;
 	}


 	public String getGenre() {
 		return genre;
 	}


 	public void setGenre(String genre) {
 		this.genre = genre;
 	}


 	public MultiValuedMap<String, String> getInstitution_repository() {
 		return institution_repository;
 	}


 	public void setInstitution_repository(MultiValuedMap<String, String> institution_repository) {
 		this.institution_repository = institution_repository;
 	}


 	public MultiValuedMap<String, String> getTitles() {
 		return titles;
 	}


 	public void setTitles(MultiValuedMap<String, String> titles) {
 		this.titles = titles;
 	}


 	public MultiValuedMap<String, String> getCreators() {
 		return creators;
 	}


 	public void setCreators(MultiValuedMap<String, String> creators) {
 		this.creators = creators;
 	}


 	public MultiValuedMap<String, String> getPublishers() {
 		return publishers;
 	}


 	public void setPublishers(MultiValuedMap<String, String> publishers) {
 		this.publishers = publishers;
 	}


 	public MultiValuedMap<String, String> getDates() {
 		return dates;
 	}


 	public void setDates(MultiValuedMap<String, String> dates) {
 		this.dates = dates;
 	}


 	


 	public MultiValuedMap<String, String> getLanguages() {
 		return languages;
 	}


 	public void setLanguages(MultiValuedMap<String, String> languages) {
 		this.languages = languages;
 	}


 	public MultiValuedMap<String, String> getAbstracts() {
 		return abstracts;
 	}


 	public void setAbstracts(MultiValuedMap<String, String> abstracts) {
 		this.abstracts = abstracts;
 	}


 	public MultiValuedMap<String, String> getNotes() {
 		return notes;
 	}


 	public void setNotes(MultiValuedMap<String, String> notes) {
 		this.notes = notes;
 	}


 	public MultiValuedMap<String, String> getSubjects() {
 		return subjects;
 	}


 	public void setSubjects(MultiValuedMap<String, String> subjects) {
 		this.subjects = subjects;
 	}


 	public MultiValuedMap<String, String> getRights() {
 		return rights;
 	}


 	public void setRights(MultiValuedMap<String, String> rights) {
 		this.rights = rights;
 	}

 	 public MultiValuedMap<String, String> getPhysicalDescription() {
 		return physicalDescription;
 	}


 	public void setPhysicalDescription(MultiValuedMap<String, String> physicalDescription) {
 		this.physicalDescription = physicalDescription;
 	}

	public MultiValuedMap<String, String> getVolumes() {
		return volumes;
	}


	public void setVolumes(MultiValuedMap<String, String> volumes) {
		this.volumes = volumes;
	}


	public MultiValuedMap<String, String> getContributors() {
		return contributors;
	}


	public void setContributors(MultiValuedMap<String, String> contributors) {
		this.contributors = contributors;
	}


	public MultiValuedMap<String, String> getCollectionName() {
		return collectionName;
	}


	public void setCollectionName(MultiValuedMap<String, String> collectionName) {
		this.collectionName = collectionName;
	}


	public MultiValuedMap<String, String> getCollectionNumber() {
		return collectionNumber;
	}


	public void setCollectionNumber(MultiValuedMap<String, String> collectionNumber) {
		this.collectionNumber = collectionNumber;
	}


	public MultiValuedMap<String, String> getBox() {
		return box;
	}


	public void setBox(MultiValuedMap<String, String> box) {
		this.box = box;
	}


	public MultiValuedMap<String, String> getFolder() {
		return folder;
	}


	public void setFolder(MultiValuedMap<String, String> folder) {
		this.folder = folder;
	}

	
	

	public MultiValuedMap<String, String> getTranslated_title() {
		return translated_title;
	}


	public void setTranslated_title(MultiValuedMap<String, String> translated_title) {
		this.translated_title = translated_title;
	}


	public String getStreamingURL() {
		return streamingURL;
	}


	public void setStreamingURL(String streamingURL) {
		this.streamingURL = streamingURL;
	}


	public MultiValuedMap<String, String> getAlt_titles() {
		return alt_titles;
	}


	public void setAlt_titles(MultiValuedMap<String, String> alt_titles) {
		this.alt_titles = alt_titles;
	}


	@Override
	public String toString() {
		return "IdepCsvBean [fileName=" + fileName + ", streamingURL=" + streamingURL + ", localID=" + localID
				+ ", license=" + license + ", localRightsStatement=" + localRightsStatement + ", related_resource="
				+ related_resource + ", collection=" + collection + ", series=" + series + ", subSeries=" + subSeries
				+ ", typeOfResource=" + typeOfResource + ", genre=" + genre + ", institution_repository="
				+ institution_repository + ", collectionName=" + collectionName + ", collectionNumber="
				+ collectionNumber + ", box=" + box + ", folder=" + folder + ", translated_title=" + translated_title
				+ ", alt_titles=" + alt_titles + ", titles=" + titles + ", creators=" + creators + ", publishers="
				+ publishers + ", dates=" + dates + ", physicalDescription=" + physicalDescription + ", languages="
				+ languages + ", abstracts=" + abstracts + ", notes=" + notes + ", subjects=" + subjects + ", rights="
				+ rights + ", volumes=" + volumes + ", contributors=" + contributors + "]";
	}


	


	

}
