package com.cichang.wordgamesviews.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cichang.wordgamesviews.R;
import com.cichang.wordgamesviews.adapter.ResultBooksAdapter;
import com.cichang.wordgamesviews.adapter.SearchAdapter;
import com.cichang.wordgamesviews.moudle.BookItemInfo;
import com.cichang.wordgamesviews.moudle.LanguageBooksInfo;
import com.cichang.wordgamesviews.moudle.SearchResultBooksInfo;
import com.cichang.wordgamesviews.views.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchActivity extends Activity implements View.OnClickListener {
    private Button mSearchButton;
    private EditText mSearchEditText;
    private FlowLayout mHotWordsFlowLayout;
    private ListView mSearchListView;
    private SearchAdapter mSearchAdapter;
    private Button mAddBooksButton;
    private TextView mResultBooksCountTextView;
    private View mSearchView;
    private View mResultView;
    private ListView mResultBooksListView;
    private boolean mIsResultShowing = false;
    private final static String[] HOT_WORDS = {"四级", "托福", "雅思", "剑桥少儿", "新概念英语", "走遍美国", "日语", "泰语", "韩语", "五十音"};

    private List<String> mSearchWordList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        findViews();
        setListener();
        initData();

    }

    private void initData() {
        for (int i = 0; i < HOT_WORDS.length; i++) {
            TextView hotWordTextView = new TextView(this);
            hotWordTextView.setText(HOT_WORDS[i]);
            hotWordTextView.setTextSize(16);
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 14;
            params.topMargin = 8;
            hotWordTextView.setBackgroundResource(R.drawable.search_item_bg);
            hotWordTextView.setLayoutParams(params);
            mHotWordsFlowLayout.addView(hotWordTextView);
        }

        initSearchWordList();

        mSearchAdapter = new SearchAdapter(mSearchWordList, "abc", this);

        mSearchListView.setAdapter(mSearchAdapter);
    }

    private void initSearchWordList() {
        mSearchWordList.add("小学abc英语");
        mSearchWordList.add("人教版小学英语aac一年级");
        mSearchWordList.add("人教版小学英语abc二年级");
        mSearchWordList.add("小学abc配图词汇");
        mSearchWordList.add("牛津小学ab英语");
    }

    private SearchResultBooksInfo getBooksInfo() {
        List<LanguageBooksInfo> booksInfos = new ArrayList<LanguageBooksInfo>();

        List<BookItemInfo> englishBooks = new ArrayList<BookItemInfo>();
        englishBooks.add(new BookItemInfo("", "abc1000词", "125/1000"));
        englishBooks.add(new BookItemInfo("", "abc2000词", "125/1000"));
        englishBooks.add(new BookItemInfo("", "abc3000词", "125/1000"));
        englishBooks.add(new BookItemInfo("", "abc4000词", "125/1000"));
        booksInfos.add(new LanguageBooksInfo("英语", false, englishBooks));

        List<BookItemInfo> jpBooks = new ArrayList<BookItemInfo>();
        jpBooks.add(new BookItemInfo("", "abc1000词", "125/1000"));
        jpBooks.add(new BookItemInfo("", "abc2000词", "125/1000"));
        jpBooks.add(new BookItemInfo("", "abc3000词", "125/1000"));
        jpBooks.add(new BookItemInfo("", "abc4000词", "125/1000"));
        booksInfos.add(new LanguageBooksInfo("日语", false, jpBooks));

        List<BookItemInfo> frBooks = new ArrayList<BookItemInfo>();
        frBooks.add(new BookItemInfo("", "abc1000词", "125/1000"));
        frBooks.add(new BookItemInfo("", "abc2000词", "125/1000"));
        booksInfos.add(new LanguageBooksInfo("法语", true, frBooks));

        return new SearchResultBooksInfo(booksInfos);
    }

    private void setListener() {
        mSearchButton.setOnClickListener(this);
        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        mHotWordsFlowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                String touchedHotWords = ((TextView) view).getText().toString();
                mSearchEditText.setText(touchedHotWords);
                //将editText的光标移到最后端
                Selection.setSelection(mSearchEditText.getText(), mSearchEditText.getText().length());
            }
        });
        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                mSearchEditText.setText(mSearchAdapter.getResultStringList().get(pos));
                mSearchView.setVisibility(View.INVISIBLE);
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int afte) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence)) {
                    mSearchAdapter.setSearchString(mSearchEditText.getText().toString());

                    ArrayList removeList = new ArrayList();
                    for (String searchWord : mSearchWordList) {
                        if (!searchWord.contains(mSearchEditText.getText().toString().trim())) {
                            removeList.add(searchWord);
                        }
                    }
                    mSearchWordList.removeAll(removeList);

                    mSearchAdapter.notifyDataSetChanged();
                    mHotWordsFlowLayout.setVisibility(View.INVISIBLE);
                    mSearchView.setVisibility(View.VISIBLE);
                } else {
                    mSearchView.setVisibility(View.INVISIBLE);
                    if (mResultView.getVisibility() == View.VISIBLE) {
                        mHotWordsFlowLayout.setVisibility(View.INVISIBLE);
                    } else {
                        mHotWordsFlowLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void findViews() {
        mSearchButton = (Button) findViewById(R.id.bt_search);
        mSearchEditText = (EditText) findViewById(R.id.et);
        mHotWordsFlowLayout = (FlowLayout) findViewById(R.id.fl_hot_words);
        mSearchListView = (ListView) findViewById(R.id.lv_search_result_tips);
        mSearchView = findViewById(R.id.ll_search_view);
        mResultView = findViewById(R.id.ll_search_result_view);
        mResultBooksListView = (ListView) findViewById(R.id.lv_search_result_books);
        mAddBooksButton = (Button) findViewById(R.id.bt_add_books);
        mResultBooksCountTextView = (TextView) findViewById(R.id.tv_search_result_count);
    }

    private boolean mHasMeasured = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                mHotWordsFlowLayout.setVisibility(View.INVISIBLE);
                mSearchView.setVisibility(View.INVISIBLE);
                mResultView.setVisibility(View.VISIBLE);
                mIsResultShowing = true;
                boolean flag = new Random().nextBoolean();
                if (true) {
                    mResultBooksListView.setVisibility(View.VISIBLE);
                    mAddBooksButton.setVisibility(View.INVISIBLE);
                    SearchResultBooksInfo booksInfo = getBooksInfo();
                    showResultList(booksInfo);
                } else {
                    mResultBooksListView.setVisibility(View.INVISIBLE);
                    mAddBooksButton.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /**
     * 展示搜索到的词书列表
     */
    private void showResultList(SearchResultBooksInfo booksInfo) {
        mHasMeasured = false;
        ResultBooksAdapter resultBooksAdapter = new ResultBooksAdapter(this, booksInfo);
        if (mResultBooksListView.getFooterViewsCount() == 0) {
            mResultBooksListView.addFooterView(View.inflate(SearchActivity.this, R.layout.footer_search_result, null));
        }
        mResultBooksListView.setAdapter(resultBooksAdapter);
        mResultBooksCountTextView.setText(booksInfo.getBooksCount() + "条搜索结果");
    }


}
