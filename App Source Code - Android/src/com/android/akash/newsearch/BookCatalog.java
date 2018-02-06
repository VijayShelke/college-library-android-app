package com.example.lib;

import java.util.ArrayList;

import android.content.Context;

public class BookCatalog {
	
	private static BookCatalog sBookCatalog;
	private Context mAppContext;
	private ArrayList<Book> mBooks;
	
	private BookCatalog(Context appcontext){
		mAppContext = appcontext;
		mBooks  = new ArrayList<Book>();
		
	}
	
	public static BookCatalog get(Context c){
		if(sBookCatalog == null){
			sBookCatalog = new BookCatalog(c.getApplicationContext());
		}
		return sBookCatalog;
	}

	public ArrayList<Book> getBooks() {
		return mBooks;
	}
	
	public void addBook(Book b){
		mBooks.add(b);
	}
	
	public Book getBook(String id){
		for(Book b:mBooks){
			if(b.getId().equals(id)){
				return b;
			}
			
		}
		return null;
	}
	
	public void clearList(){
		mBooks.clear();
	}
	
	
 
}

