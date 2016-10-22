package com.libarab.michaelg.libarab.favorites.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.libarab.michaelg.libarab.R;
import com.libarab.michaelg.libarab.favorites.bean.Book;

// TODO: 03/10/2016  import me.icxd.bookshelve.activity.BookNoteEditActivity;


public class BookNoteFragment extends Fragment {

    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";
    private int booId = 1;
    private Book book;

    private TextView tvContent;
    private TextView tvDate;
    private ImageView ivIconDate;

    public static BookNoteFragment newInstance(int itemId) {
        BookNoteFragment fragment = new BookNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }
    public static BookNoteFragment newInstance(Book book) {
        BookNoteFragment fragment = new BookNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        book = (Book) getArguments().getSerializable(ARG_BOOK);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentPanel = inflater.inflate(R.layout.fav_fragment_book_note, container, false);


        tvContent = (TextView) contentPanel.findViewById(R.id.tv_content);
        tvDate = (TextView) contentPanel.findViewById(R.id.tv_date);
        ivIconDate = (ImageView) contentPanel.findViewById(R.id.iv_icon_date);

        // Edit Button
        ImageView ivEdit = (ImageView) contentPanel.findViewById(R.id.iv_edit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 03/10/2016   Intent intent = new Intent(getContext(), BookNoteEditActivity.class);
                // TODO: 02/10/2016  change bookid to book
                // TODO: 03/10/2016   intent.putExtra("id", booId);
                // TODO: 03/10/2016   startActivity(intent);
            }
        });

        return contentPanel;
    }

    public void loadData() {
        // Books data
        //Book book = DataSupport.find(Book.class, booId);

        String note = book.getDescription();
        String note_date = "Today";

        if (note.isEmpty()) {
            note = "\nNo notes, top right, click the button to start to write notes it! \n";
        }
        tvContent.setText(note);

        if (note_date.isEmpty()) {
            ivIconDate.setAlpha(0f);
            tvContent.setGravity(Gravity.CENTER);
        } else {
            ivIconDate.setAlpha(1f);
            tvContent.setGravity(Gravity.NO_GRAVITY);
        }
        tvDate.setText(note_date);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
