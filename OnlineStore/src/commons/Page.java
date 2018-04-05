package commons;

import java.util.List;

public class Page {

	private List records;
	private int totalRecordsNum;

	private int currentPageNum;
	private int prePageNum;
	private int nextPageNum;
	private int totalPageNum;

	private int startIndex;
	private int recordsPerPage;

	private int startPage = 1;
	private int endPage;
	
	private String url;

	public Page(){

	}

	public Page(int currentPageNum,int totalRecordsNum,int recordsPerPage){
		this.totalRecordsNum = totalRecordsNum;
		this.currentPageNum = currentPageNum;
		this.recordsPerPage = recordsPerPage;

		startIndex = (currentPageNum-1)*recordsPerPage;
		totalPageNum = totalRecordsNum%recordsPerPage==0?totalRecordsNum/recordsPerPage:totalRecordsNum/recordsPerPage+1;
		//动态页数，随当前页数改变而改变
		if(totalPageNum<5){
			startPage = 1;
			endPage = totalPageNum;
		}else if(currentPageNum<3){
			startPage = 1;
			endPage = 5;
		}else if(currentPageNum>totalPageNum-2){
			endPage = totalPageNum;
			startPage = endPage-4;
		}else if(totalPageNum>5){
			startPage = currentPageNum-2;
			endPage = currentPageNum+2;
		}
	}

	public List getRecords() {
		return records;
	}
	public void setRecords(List records) {
		this.records = records;
	}
	public int getTotalRecordsNum() {
		return totalRecordsNum;
	}
	public void setTotalRecordsNum(int totalRecordsNum) {
		this.totalRecordsNum = totalRecordsNum;
	}
	public int getCurrentPageNum() {
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	public int getPrePageNum() {
		prePageNum = (currentPageNum-1==0?1:currentPageNum-1);
		return prePageNum;
	}
	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}
	public int getNextPageNum() {
		nextPageNum = (currentPageNum+1>endPage?endPage:currentPageNum+1);
		return nextPageNum;
	}
	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}
	public int getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Page [records=" + records + ", totalRecordsNum="
				+ totalRecordsNum + ", currentPageNum=" + currentPageNum
				+ ", prePageNum=" + prePageNum + ", nextPageNum=" + nextPageNum
				+ ", totalPageNum=" + totalPageNum + ", startIndex="
				+ startIndex + ", recordsPerPage=" + recordsPerPage
				+ ", startPage=" + startPage + ", endPage=" + endPage
				+ ", url=" + url + "]";
	}


}
