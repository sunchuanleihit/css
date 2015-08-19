package com.loukou.css.bo;

import java.util.ArrayList;
import java.util.List;

public class PageListEntity<T> {
/**
 * 存储分页信息
 * */
	private int pageCount;//总页数
	private int[] pageCountList;//分页数组
	private long recordCount;//总记录数
	private int curPageIndex;//当前页码
	private List<T> entityList = new ArrayList<T>();//记录
	
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long l) {
		this.recordCount = l;
	}
	public int getCurPageIndex() {
		return curPageIndex;
	}
	public void setCurPageIndex(int curPageIndex) {
		this.curPageIndex = curPageIndex;
	}
	public List<T> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<T> entityList) {
		this.entityList = entityList;
	}
	public int[] getPageCountList() {
		
		if(this.pageCount<=5){
			int[] pageList = new int[pageCount];
			for(int i =1;i<=this.pageCount;i++){
				pageList[i-1] = i;
			}
			return pageList;
		}else{
			int pagefirst = 0;
			int pagelast = 0;
			if(this.curPageIndex<=2){
				pagefirst = 1;
			}else{
				pagefirst = this.curPageIndex-2;
			}
			if(pagefirst+4>this.pageCount){
				pagelast = this.pageCount;
				
			}else{
				pagelast = pagefirst+4;
			}
			if(pagelast-pagefirst<4){
				pagefirst = pagelast-4;
				if(pagefirst<1){pagefirst=1;}
			}
			int[] pageList = new int[pagelast-pagefirst+1];
			int j=0;
			for(int i = pagefirst;i<=pagelast;i++){
				pageList[j] = i;
				j++;
			}
			return pageList;
		}
	}
	public void setPageCountList(int[] pageCountList) {
		this.pageCountList = pageCountList;
	}
	/*public static void main(String[] args){
		PageListEntity<Integer> page = new PageListEntity<Integer>();
		page.curPageIndex = 5;
		page.pageCount = 10;
		int[] i = page.getPageCountList();
		for (int j = 0; j < i.length; j++) {
			System.out.println(i[j]);
		}
	}*/
}
