package com.libarab.michaelg.libarab.favorites.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.favorites.adapter.BookInfoAdapter;
import com.libarab.michaelg.libarab.favorites.bean.Book;
import com.libarab.michaelg.libarab.favorites.bean.TagItem;

import java.util.ArrayList;
import java.util.List;

//import org.litepal.crud.DataSupport;


public class BookInfoItemFragment extends Fragment {
    
    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";
    
    private Book book;
    private List<TagItem> data;

    public static BookInfoItemFragment newInstance(int bookId) {
        BookInfoItemFragment fragment = new BookInfoItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookInfoItemFragment newInstance(Book book) {
        BookInfoItemFragment fragment = new BookInfoItemFragment();
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
               // book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView lv = (ListView) inflater.inflate(R.layout.fav_fragment_book_info_item_list, container, false);

        // data
        data = new ArrayList<>();
        data.add(new TagItem("Title", book.getTitle()));
        data.add(new TagItem("Author", book.getAuthor()));


        //data.add(new TagItem("Press", book.getPublisher()));
        //if (!book.getOrigin_title().isEmpty()) data.add(new TagItem("Original name", book.getOrigin_title()));
        //if (!book.getTranslator().isEmpty()) data.add(new TagItem("Translator", book.getTranslator()));
        data.add(new TagItem("Publisher", book.getPublisher()));
        data.add(new TagItem("Creation Date", book.getCreationDate()));
//        data.add(new TagItem("Creation Date", book.getPubdate()));


        //data.add(new TagItem("Pricing", book.getPrice()));
        //if (!book.getBinding().isEmpty()) data.add(new TagItem("Binding", book.getBinding()));
        //data.add(new TagItem("ISBN", book.getIsbn13()));
        ArrayList<Integer> my  = book.getPageList();
        String test="";
        for (Integer object: my) {
           test=test+object+",";
        }
        data.add(new TagItem("Pages", test));
        // List Adapter
        BookInfoAdapter lvBaseAdapter = new BookInfoAdapter(getContext(), data);

        // List
        lv.setAdapter(lvBaseAdapter);

        return lv;
    }
}
