package cmpe.boun.NazimVisualize.Model;

public class WorkTenses {
	int workId;
	String name;
	int year;
	String place;
	int genisCnt;
	int simdikiCnt;
	int gelecekCnt;
	int gecmisCnt ;
	
	public WorkTenses(		int workId,
			String name,
			int year,
			String place,
			int genisCnt,
			int simdikiCnt,
			int gelecekCnt,
			int gecmisCnt){
		this.workId =workId;
		this.name =name;
		this.genisCnt =genisCnt;
		this.simdikiCnt =simdikiCnt;
		this.gelecekCnt =gelecekCnt;
		this.gecmisCnt =gecmisCnt;
		this.year = year;
		this.place = place;
	}
	
	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGenisCnt() {
		return genisCnt;
	}
	public void setGenisCnt(int genisCnt) {
		this.genisCnt = genisCnt;
	}
	public int getSimdikiCnt() {
		return simdikiCnt;
	}
	public void setSimdikiCnt(int simdikiCnt) {
		this.simdikiCnt = simdikiCnt;
	}
	public int getGelecekCnt() {
		return gelecekCnt;
	}
	public void setGelecekCnt(int gelecekCnt) {
		this.gelecekCnt = gelecekCnt;
	}
	public int getGecmisCnt() {
		return gecmisCnt;
	}
	public void setGecmisCnt(int gecmisCnt) {
		this.gecmisCnt = gecmisCnt;
	}
	
	
}
