package com.cichang.wordgamesviews.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cichang.wordgamesviews.R;

import java.util.List;

/**
 * Created by HJ on 2015/2/27.
 */
public class SearchResultAdapter extends BaseAdapter {

    private List<String> mResultStringList;
    private String mSearchString;
    private Context mContext;
    private static final String BLUE_START_TAG = "<font color='#0003FF'>";
    private static final String BLUE_END_TAG = "</font>";

    public SearchResultAdapter(List<String> mResultStringList, String mSearchString, Context mContext) {
        this.mResultStringList = mResultStringList;
        this.mSearchString = mSearchString;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mResultStringList.size();
    }

    @Override
    public String getItem(int i) {
        return mResultStringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        View view;
        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.search_item_view, null);
            holder.mResultTextView = (TextView) view.findViewById(R.id.tv_result);
            view.setTag(holder);
        }
        holder.mResultTextView.setText(splitSearchWord(pos));
        return view;
    }

    /**
     * 切割搜索结果 并且将匹配的词语用蓝色显示
     */
    private Spanned splitSearchWord(int pos) {
        String itemString = mResultStringList.get(pos);

        String[] splitResults = itemString.split(mSearchString);
        String result = "";
        for (int i = 0; i < splitResults.length; i++) {
            result += (splitResults[i] + getBlueString());
        }
        if (!itemString.endsWith(mSearchString)) {
            result = result.substring(0,result.lastIndexOf(getBlueString()));
        }
        return Html.fromHtml(result);
    }

    /**
     * 获取蓝色的结果提示
     */
    public String getBlueString() {
        return BLUE_START_TAG + mSearchString + BLUE_END_TAG;
    }

    public class ViewHolder {
        TextView mResultTextView;
    }


    public List<String> getResultStringList() {
        return mResultStringList;
    }

    public void setResultStringList(List<String> mResultStringList) {
        this.mResultStringList = mResultStringList;
    }

    public String getSearchString() {
        return mSearchString;
    }

    public void setSearchString(String mSearchString) {
        this.mSearchString = mSearchString;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
