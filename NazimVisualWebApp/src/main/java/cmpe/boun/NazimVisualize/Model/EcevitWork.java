package cmpe.boun.NazimVisualize.Model;

public class EcevitWork {
	private Integer workID;
	private String header;
	private String source;
	private String date;
	private String location;
	private String fullText;
	private String files;
	private String collection;
	private String tags;
	private Integer pageId;
	private String citation;


	public String getCitation() {
		return citation;
	}


	public void setCitation(String citation) {
		this.citation = citation;
	}

	public Integer getWorkID() {
		return workID;
	}


	public void setWorkID(Integer workID) {
		this.workID = workID;
	}


	public Integer getPageId() {
		return pageId;
	}


	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}


	public String getHeader() {
		return header;
	}


	public void setHeader(String header) {
		this.header = header;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getFullText() {
		return fullText;
	}


	public void setFullText(String fullText) {
		this.fullText = fullText;
	}


	public String getFiles() {
		return files;
	}


	public void setFiles(String files) {
		this.files = files;
	}


	public String getCollection() {
		return collection;
	}


	public void setCollection(String collection) {
		this.collection = collection;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}
}
