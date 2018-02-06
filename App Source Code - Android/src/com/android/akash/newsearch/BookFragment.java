package com.example.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookFragment extends Fragment {
	
	
	public  static final String EXTRA_BOOK_ID = "com.android.akash.newsearch.BookFragment";
	private Book mBook;
	public static BookFragment newInstance(String book_id){
		
		Bundle args = new Bundle();
		args.putCharSequence(EXTRA_BOOK_ID, book_id);
		BookFragment fragment = new BookFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		String book_id = (String)getArguments().getCharSequence(EXTRA_BOOK_ID);
		mBook = BookCatalog.get(getActivity()).getBook(book_id);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_book, container,false);
		
		TextView mTitle = (TextView)v.findViewById(R.id.title);
		TextView mAuthor = (TextView)v.findViewById(R.id.author);
		TextView mpubl = (TextView)v.findViewById(R.id.publ);
		TextView mavl = (TextView)v.findViewById(R.id.avl);
		
		mTitle.setText(mBook.getTitle());
		mAuthor.setText(mBook.getAuthor());
		mpubl.setText(mBook.getPublication());
		
		if(mBook.getAvl() > 0){
			mavl.setText("Available");
		}
		
		else{
			mavl.setText("Not available");
		}
		
		//return super.onCreateView(inflater, container, savedInstanceState);
		return v;
	}

}
