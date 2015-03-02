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
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cichang.wordgamesviews.R;
import com.cichang.wordgamesviews.adapter.SearchResultAdapter;
import com.cichang.wordgamesviews.adapter.SearchResultBooksAdapter;
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
    private ListView mSearchResultListView;
    private SearchResultAdapter mSearchResultAdapter;
    private Button mClearButton;
    private Button mAddBooksButton;
    private TextView mResultBooksCountTextView;
    private View mBottomAddBooksView;
    private View mSearchView;
    private View mSearchResultView;
    private ListView mSearchResultBooksListView;
    private boolean mIsSearchResultShowing = false;
    private final static String[] HOT_WORDS = {"四级", "托福", "雅思", "剑桥少儿", "新概念英语", "走遍美国", "日语", "泰语", "韩语", "五十音"};

    private List<String> mSearchResultList = new ArrayList<String>();

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

        mSearchResultList.add("小学英语");
        mSearchResultList.add("人教版小学英语一年级");
        mSearchResultList.add("人教版小学英语二年级");
        mSearchResultList.add("小学配图词汇");
        mSearchResultList.add("牛津小学英语");

        mSearchResultAdapter = new SearchResultAdapter(mSearchResultList, "小学", this);

        mSearchResultListView.setAdapter(mSearchResultAdapter);
    }

    private SearchResultBooksInfo getBooksInfo() {
        List<LanguageBooksInfo> booksInfos = new ArrayList<LanguageBooksInfo>();

        List<BookItemInfo> englishBooks = new ArrayList<BookItemInfo>();
        englishBooks.add(new BookItemInfo("", "学英语绕不过的1000词", "125/1000"));
        englishBooks.add(new BookItemInfo("", "学英语绕不过的2000词", "125/1000"));
        englishBooks.add(new BookItemInfo("", "学英语绕不过的3000词", "125/1000"));
        englishBooks.add(new BookItemInfo("", "学英语绕不过的4000词", "125/1000"));
        booksInfos.add(new LanguageBooksInfo("英语", false, englishBooks));

//        List<BookItemInfo> jpBooks = new ArrayList<BookItemInfo>();
//        jpBooks.add(new BookItemInfo("", "学日语绕不过的1000词", "125/1000"));
//        jpBooks.add(new BookItemInfo("", "学日语绕不过的2000词", "125/1000"));
//        jpBooks.add(new BookItemInfo("", "学日语绕不过的3000词", "125/1000"));
//        jpBooks.add(new BookItemInfo("", "学日语绕不过的4000词", "125/1000"));
//        booksInfos.add(new LanguageBooksInfo("日语", false, jpBooks));
//
//        List<BookItemInfo> frBooks = new ArrayList<BookItemInfo>();
//        frBooks.add(new BookItemInfo("", "学法语绕不过的1000词", "125/1000"));
//        frBooks.add(new BookItemInfo("", "学法语绕不过的2000词", "125/1000"));
//        booksInfos.add(new LanguageBooksInfo("法语", true, frBooks));

        return new SearchResultBooksInfo(booksInfos);
    }

    private void setListener() {
        mSearchButton.setOnClickListener(this);
        mSearchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        mClearButton.setOnClickListener(this);
        mHotWordsFlowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                String touchedHotWords = ((TextView) view).getText().toString();
                mSearchEditText.setText(touchedHotWords);
                //将editText的光标移到最后端
                Selection.setSelection(mSearchEditText.getText(), mSearchEditText.getText().length());
            }
        });
        mSearchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                mSearchEditText.setText(mSearchResultAdapter.getResultStringList().get(pos));
                mSearchView.setVisibility(View.INVISIBLE);
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int afte) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence) || mIsSearchResultShowing) {
                    mHotWordsFlowLayout.setVisibility(View.INVISIBLE);
                    mSearchView.setVisibility(View.VISIBLE);
                } else {
                    mHotWordsFlowLayout.setVisibility(View.VISIBLE);
                    mSearchView.setVisibility(View.INVISIBLE);
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
        mSearchResultListView = (ListView) findViewById(R.id.lv_search_result_tips);
        mClearButton = (Button) findViewById(R.id.bt_clear_result);
        mSearchView = findViewById(R.id.ll_search_view);
        mSearchResultView = findViewById(R.id.ll_search_result_view);
        mSearchResultBooksListView = (ListView) findViewById(R.id.lv_search_result_books);
        mAddBooksButton = (Button) findViewById(R.id.bt_add_books);
        mResultBooksCountTextView = (TextView) findViewById(R.id.tv_search_result_count);
        mBottomAddBooksView = findViewById(R.id.rl_add_books);
    }

    private boolean mHasMeasured = false;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                mHotWordsFlowLayout.setVisibility(View.INVISIBLE);
                mSearchView.setVisibility(View.INVISIBLE);
                mSearchResultView.setVisibility(View.VISIBLE);
                mIsSearchResultShowing = true;
                boolean flag = new Random().nextBoolean();
                if (true) {
                    mSearchResultBooksListView.setVisibility(View.VISIBLE);
                    mAddBooksButton.setVisibility(View.INVISIBLE);
                    SearchResultBooksInfo booksInfo = getBooksInfo();
                    showResultList(booksInfo);
                } else {
                    mSearchResultBooksListView.setVisibility(View.INVISIBLE);
                    mAddBooksButton.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_clear_result:
                // 清空搜索结果

                break;
        }
    }

    /**
     * 展示搜索到的词书列表
     */
    private void showResultList(SearchResultBooksInfo booksInfo) {
        final SearchResultBooksAdapter searchResultBooksAdapter = new SearchResultBooksAdapter(this, booksInfo);
        searchResultBooksAdapter.setOnNotifyDataSetChangedListener(new SearchResultBooksAdapter.OnNotifyDataSetChangedListener() {
            @Override
            public void notifyFinished() {
                Toast.makeText(SearchActivity.this, "click", Toast.LENGTH_SHORT).show();
                notifyResultListFooterView(searchResultBooksAdapter);
            }
        });
        mSearchResultBooksListView.setAdapter(searchResultBooksAdapter);
        searchResultBooksAdapter.notifyDataSetChanged();

        mResultBooksCountTextView.setText(booksInfo.getBooksCount() + "条搜索结果");
    }

    /**
     * 搜索结果列表的footer的显示更新
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void notifyResultListFooterView(final SearchResultBooksAdapter searchResultBooksAdapter) {
        mSearchResultBooksListView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                if (!mHasMeasured) {
                    if (mSearchResultBooksListView.getLastVisiblePosition() != mSearchResultBooksListView.getCount() - 1) {
                        mSearchResultBooksListView.addFooterView(View.inflate(SearchActivity.this, R.layout.footer_search_result, null));
                        mBottomAddBooksView.setVisibility(View.INVISIBLE);
                    } else {
                        mBottomAddBooksView.setVisibility(View.VISIBLE);
                    }
                    mSearchResultBooksListView.setAdapter(searchResultBooksAdapter);
                    mHasMeasured = true;
                }
            }
        });
    }
}
