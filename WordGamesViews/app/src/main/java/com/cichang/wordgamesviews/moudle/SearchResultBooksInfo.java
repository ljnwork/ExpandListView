package com.cichang.wordgamesviews.moudle;

import java.util.List;

/**
 * Created by HJ on 2015/2/27.
 */
public class SearchResultBooksInfo {
    public static final int LAST_SHOW_COUNT = 3;

    private List<LanguageBooksInfo> mLanguageBooksInfos; //一个语种的词书列表信息

    public SearchResultBooksInfo(List<LanguageBooksInfo> mLanguageBooksInfos) {
        this.mLanguageBooksInfos = mLanguageBooksInfos;
    }

    public List<LanguageBooksInfo> getLanguageBooksInfos() {
        return mLanguageBooksInfos;
    }

    public void setLanguageBooksInfos(List<LanguageBooksInfo> mLanguageBooksInfos) {
        this.mLanguageBooksInfos = mLanguageBooksInfos;
    }

    // 获取词书数量
    public int getBooksCount() {
        int count = 0;
        for(LanguageBooksInfo info : mLanguageBooksInfos){
            count += info.getBooks().size();
        }
        return count;
    }

}
