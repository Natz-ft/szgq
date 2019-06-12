package com.icfcc.credit.platform.util.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginationBean {
	private static Logger log = LoggerFactory.getLogger(PaginationBean.class);
	public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 15;

    private int rowCount; //记录数
    private int pageNo;  //当前页
    private int pageSize;  //每页记录数
    private int firstPageNo;
    private int lastPageNo;
    
    public PaginationBean(){
    	this.pageNo = DEFAULT_PAGE_NO;
    	this.pageSize = DEFAULT_PAGE_SIZE;
    }
    public void counting(long rowCountl) {
    	int rowCount = Integer.parseInt(rowCountl+"");
    	if (rowCount < 0) {
            this.rowCount = 0;
        } else {
            this.rowCount = rowCount;
        }

        if (pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            //this.pageSize = pageSize;
        }
        
        firstPageNo = 1;
        lastPageNo = (rowCount - 1) / pageSize + 1;

        if (pageNo < firstPageNo) {
            this.pageNo = firstPageNo;
        } else if (pageNo > lastPageNo) {
            this.pageNo = lastPageNo;
        } else {
            this.pageNo = pageNo;
        }
    }


    public int getRowCount() {
        return rowCount;
    }

    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(String pageSize) {
    	if (pageSize==null || pageSize.length()<1) {
    		this.pageSize = DEFAULT_PAGE_SIZE;
    		return;
    	}
    	try {
    		this.pageSize = Integer.parseInt(pageSize);
    		if (this.pageSize < 1) {
    			this.pageSize = DEFAULT_PAGE_SIZE;
    		}
    	} catch (Exception e) {
    		this.pageSize = DEFAULT_PAGE_SIZE;
    		log.error(e.getMessage(),e);
    		e.printStackTrace();
    	}
    }

    public int getPageNo() {
        return pageNo;
    }
    
    public void setPageNo(String pageNo) {
    	if (pageNo==null || pageNo.length()<1) {
    		this.pageNo = DEFAULT_PAGE_NO;
    		return;
    	}
    	try {
    		this.pageNo = Integer.parseInt(pageNo);
    		if (this.pageNo < 1) {
    			this.pageNo = DEFAULT_PAGE_NO;
    		} else if (this.pageNo > this.lastPageNo){
    			this.pageNo = this.lastPageNo;
    		}
    	} catch (Exception e) {
    		this.pageNo = DEFAULT_PAGE_NO;
    		log.error(e.getMessage(),e);
    		e.printStackTrace();
    	}
    }

    public int getLastPageNo() {
        return lastPageNo;
    }

    public int getPreviousPageNo() {
        if (!isHasPreviousPage()) {
            return firstPageNo;
        }
        return pageNo - 1;
    }

    public int getNextPageNo() {
        if (!isHasNextPage()) {
            return lastPageNo;
        }
        return pageNo + 1;
    }

    private boolean isHasNextPage() {
        return pageNo < lastPageNo;
    }

    private boolean isHasPreviousPage() {
        return pageNo > firstPageNo;
    }
	
}
