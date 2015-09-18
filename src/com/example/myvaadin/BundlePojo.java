package com.example.myvaadin;

import java.io.Serializable;

public class BundlePojo implements Serializable{

	private String bname;
	private String bdesc;
	
//	public BundlePojo(String name, String desc){
//		this.bname = name;
//		this.bdesc = bdesc;
//	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBdesc() {
		return bdesc;
	}

	public void setBdesc(String bdesc) {
		this.bdesc = bdesc;
	}
	
	
}
