package com.cichang.wordgamesviews.moudle;

import java.util.List;

/**
 * Created by HJ on 2015/2/28.
 */
public class LanguageBooksInfo {
    private String mLangs;  //词书语种
    private boolean mIsExpanded;  //是否已经展开
    private List<BookItemInfo> mBooks;  //词书列表

    public LanguageBooksInfo(String mLangs, boolean mIsExpanded, List<BookItemInfo> mBooks) {
        this.mLangs = mLangs;
        this.mIsExpanded = mIsExpanded;
        this.mBooks = mBooks;
    }

    public String getLangs() {
        return mLangs;
    }

    public void setLangs(String mLangs) {
        this.mLangs = mLangs;
    }

    public boolean isExpanded() {
        return mIsExpanded;
    }

    public void setExpanded(boolean mIsExpanded) {
        this.mIsExpanded = mIsExpanded;
    }

    public List<BookItemInfo> getBooks() {
        return mBooks;
    }

    public void setBooks(List<BookItemInfo> mBooks) {
        this.mBooks = mBooks;
    }
}
