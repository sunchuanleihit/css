package com.loukou.css.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComplaintRespListDto implements Serializable{
	private static final long serialVersionUID = -1787393288847592607L;
	
	private int count = 0;
	private List<ComplaintRespDto> complaintList = new ArrayList<ComplaintRespDto>();
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ComplaintRespDto> getComplaintList() {
		return complaintList;
	}
	public void setComplaintList(List<ComplaintRespDto> complaintList) {
		this.complaintList = complaintList;
	}
	
}
