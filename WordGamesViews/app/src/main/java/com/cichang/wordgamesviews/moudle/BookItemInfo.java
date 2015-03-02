package com.cichang.wordgamesviews.moudle;

/**
 * Created by HJ on 2015/2/28.
 */
public class BookItemInfo {
    private String mBookCoverImageUrl;  //词书封面图
    private String mBookName;  //词书名
    private String mWordCount;  //词数和已学词数

    public BookItemInfo(String mBookCoverImageUrl, String mBookName, String mWordCount) {
        this.mBookCoverImageUrl = mBookCoverImageUrl;
        this.mBookName = mBookName;
        this.mWordCount = mWordCount;
    }

    public String getPicID() {
        return mBookCoverImageUrl;
    }

    public void setPicID(String bookCoverImageUrl) {
        this.mBookCoverImageUrl = bookCoverImageUrl;
    }

    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String mBookName) {
        this.mBookName = mBookName;
    }

    public String getWordCount() {
        return mWordCount;
    }

    public void setWordCount(String mWordCount) {
        this.mWordCount = mWordCount;
    }
}
