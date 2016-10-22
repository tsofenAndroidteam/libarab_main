package com.libarab.michaelg.libarab.favorites.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.favorites.adapter.BookIntroAdapter;
import com.libarab.michaelg.libarab.favorites.bean.Book;
import com.libarab.michaelg.libarab.favorites.bean.TagItem;

import java.util.ArrayList;
import java.util.List;

//import org.litepal.crud.DataSupport;


public class BookIntroFragment extends Fragment {

    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";

    private Book book;
    private List<TagItem> data;

    public static BookIntroFragment newInstance(int bookId) {
        BookIntroFragment fragment = new BookIntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookIntroFragment newInstance(Book book) {
        BookIntroFragment fragment = new BookIntroFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_BOOK_ID)) {
                //book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView lv = (ListView) inflater.inflate(R.layout.fav_fragment_book_intro_list, container, false);

        // data
        data = new ArrayList<>();
        //if (!book.getDescription().isEmpty()) data.add(new TagItem("brief introduction", book.getDescription()));
       // if (!book.getAuthor().isEmpty()) data.add(new TagItem("About the Author", book.getAuthor()));
       // if (!book.getCatalog().isEmpty()) data.add(new TagItem("Book Catalog", ""));

        // List Adapter
        BookIntroAdapter lvBaseAdapter = new BookIntroAdapter(getContext(), data);

        // List
        lv.setAdapter(lvBaseAdapter);

        return lv;
    }
}
