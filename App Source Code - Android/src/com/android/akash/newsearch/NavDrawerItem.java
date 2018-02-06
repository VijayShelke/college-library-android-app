package com.example.lib;

public class NavDrawerItem {

	private String mTitle ;
	private int icon;
	
	public NavDrawerItem(String title, int icon){
		this.mTitle  = title;
		this.icon = icon;
	}

	public String getTitle() {
		return this.mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	public void setIcon(int icon){
		this.icon = icon;
		
	}
}
