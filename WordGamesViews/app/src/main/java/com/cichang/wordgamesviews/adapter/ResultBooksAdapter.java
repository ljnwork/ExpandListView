package com.cichang.wordgamesviews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cichang.wordgamesviews.R;
import com.cichang.wordgamesviews.moudle.BookItemInfo;
import com.cichang.wordgamesviews.moudle.LanguageBooksInfo;
import com.cichang.wordgamesviews.moudle.SearchResultBooksInfo;

import java.util.ArrayList;

/**
 * Created by HJ on 2015/2/27.
 */
public class ResultBooksAdapter extends BaseAdapter {

    private Context mContext;
    private SearchResultBooksInfo mBooksInfo;
    private ArrayList mItemList;
    private static final int TYPE_LANGS = 0;  //条目为语种
    private static final int TYPE_BOOK = 1;  //条目为词书
    private static final int TYPE_EXPAND = 2;  //条目为加载更多
    private static final int[] SEARCH_RESULT_ITEM_TYPES = {TYPE_LANGS, TYPE_BOOK, TYPE_EXPAND};

    public ResultBooksAdapter(Context mContext, SearchResultBooksInfo mBooksInfo) {
        this.mContext = mContext;
        this.mBooksInfo = mBooksInfo;
    }

    /**
     * 生成用来展示的列表条目
     *
     * @param mBooksInfo
     */
    private ArrayList generateItemList(SearchResultBooksInfo mBooksInfo) {
        mItemList = new ArrayList();
        for (LanguageBooksInfo booksInfo : mBooksInfo.getLanguageBooksInfos()) {
            mItemList.add(booksInfo.getLangs());
            if (!booksInfo.isExpanded()) {
                for (int i = 0; i < 3; i++) {
                    mItemList.add(booksInfo.getBooks().get(i));
                }
                mItemList.add(booksInfo);
            } else {
                mItemList.addAll(booksInfo.getBooks());
            }
        }
        return mItemList;
    }

    @Override
    public int getCount() {
        return generateItemList(mBooksInfo).size();
    }

    @Override
    public Object getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View view;
        LangsViewHolder langsHolder = null;
        BookViewHolder bookHolder = null;
        ExpandViewHolder expandHolder = null;
        int type = getItemViewType(i);
        if (convertView != null) {
            view = convertView;
            switch (type) {
                case TYPE_LANGS:
                    langsHolder = (LangsViewHolder) view.getTag();
                    break;
                case TYPE_BOOK:
                    bookHolder = (BookViewHolder) view.getTag();
                    break;
                case TYPE_EXPAND:
                    expandHolder = (ExpandViewHolder) view.getTag();
                    break;
            }
        } else {
            switch (type) {
                case TYPE_LANGS:
                    convertView = View.inflate(mContext, R.layout.item_langs, null);
                    langsHolder = new LangsViewHolder();
                    langsHolder.mLangsNameTextView = (TextView) convertView.findViewById(R.id.tv_langsname);
                    convertView.setTag(langsHolder);
                    break;
                case TYPE_BOOK:
                    convertView = View.inflate(mContext, R.layout.item_book_item, null);
                    bookHolder = new BookViewHolder();
                    bookHolder.mBookPicImageView = (ImageView) convertView.findViewById(R.id.book_layout_bg);
                    bookHolder.mBookNameTextView = (TextView) convertView.findViewById(R.id.book_name);
                    bookHolder.mBookWordCountTextView = (TextView) convertView.findViewById(R.id.tv_best_book_word_count);
                    convertView.setTag(bookHolder);
                    break;
                case TYPE_EXPAND:
                    convertView = View.inflate(mContext, R.layout.item_expand, null);
                    expandHolder = new ExpandViewHolder();
                    expandHolder.mExpandTextView = (TextView) convertView.findViewById(R.id.tv_expand);
                    expandHolder.mRootExpandView = convertView.findViewById(R.id.rl_expand_root);
                    convertView.setTag(expandHolder);
                    break;
            }
        }

        switch (type) {
            case TYPE_LANGS:
                langsHolder.mLangsNameTextView.setText(mItemList.get(i).toString());
                break;
            case TYPE_BOOK:
                bookHolder.mBookWordCountTextView.setText(((BookItemInfo) mItemList.get(i)).getWordCount());
                bookHolder.mBookNameTextView.setText(((BookItemInfo) mItemList.get(i)).getBookName());
                break;
            case TYPE_EXPAND:
                expandHolder.mExpandTextView.setText("V 展开更多");
                expandHolder.mRootExpandView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ((LanguageBooksInfo) mItemList.get(i)).setExpanded(true);
                        notifyDataSetChanged();
                    }
                });
                break;
        }

        return convertView;
    }

    class LangsViewHolder {
        private TextView mLangsNameTextView;
    }

    class BookViewHolder {
        private ImageView mBookPicImageView;
        private TextView mBookNameTextView;
        private TextView mBookWordCountTextView;
    }

    class ExpandViewHolder {
        private TextView mExpandTextView;
        private View mRootExpandView;
    }


    @Override
    public int getItemViewType(int position) {
        if (mItemList.get(position) instanceof BookItemInfo) {
            return TYPE_BOOK;
        } else if (mItemList.get(position) instanceof LanguageBooksInfo) {
            return TYPE_EXPAND;
        } else {
            return TYPE_LANGS;
        }
    }

    @Override
    public int getViewTypeCount() {
        return SEARCH_RESULT_ITEM_TYPES.length;
    }

}
