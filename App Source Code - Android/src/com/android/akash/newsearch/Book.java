package com.example.lib;

public class Book {
	
	private String title;
	private String author;
	private String publication;
	private String[] categories;
	private String id;
	private String return_date;
	private Boolean islate;
	private int avl ; 
	
	public Book(String title,String author,String publication,String id){
		this.title = title;
		this.author = author;
		this.publication=publication;
		//this.categories=categories;
		this.id=id;
		
	}
	

	public Book(String title,String author,String publication,String id,int avl){
		this.title = title;
		this.author = author;
		this.publication=publication;
		//this.categories=categories;
		this.id=id;
		this.avl = avl;
		
	}
	
	public Book(String title,String author,String publication){
		this.title = title;
		this.author = author;
		this.publication=publication;
		//this.categories=categories;
	
	}
	
	public Book(String title,String author,String publication,String id ,String ret){
		this.title = title;
		this.author = author;
		this.publication=publication;
		//this.categories=categories;
		this.id=id;
		this.return_date = ret ; 
	}
	
	public Book(String title,String author,String publication,String id ,String ret,Boolean late){
		this.title = title;
		this.author = author;
		this.publication=publication;
		//this.categories=categories;
		this.id=id;
		this.return_date = ret ; 
		this.islate = late;
	}
	
	public Book(){
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

	public Boolean Islate() {
		return islate;
	}

	public void setIslate(Boolean islate) {
		this.islate = islate;
	}

	public int getAvl() {
		return avl;
	}

	public void setAvl(int avl) {
		this.avl = avl;
	}
	
	
	

}
