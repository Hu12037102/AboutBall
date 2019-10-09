package com.ifeell.app.aboutball.game.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.game.bean.ResultGameDataBean;
import com.ifeell.app.aboutball.util.UIUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/26 9:51
 * 更新时间: 2019/3/26 9:51
 * 描述:比赛赛况Adapter
 */
public class GameResultAdapter extends BaseRecyclerAdapter<GameResultAdapter.ViewHolder, List<ResultGameDataBean.Bean>> {
    public GameResultAdapter(@NonNull List<ResultGameDataBean.Bean> data) {
        super(data);
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTvName.setText(mData.get(i).nickName);
        UIUtils.setText(viewHolder.mTvName, mData.get(i).nickName /*+ "（" + mData.get(i).anchorName + "）"*/);
        viewHolder.mTvContent.setText(mData.get(i).content);
        viewHolder.mTvName.setCompoundDrawablesWithIntrinsicBounds(i == 0 ? R.drawable.shape_game_result_check_view
                : R.drawable.shape_game_result_default_view, 0, 0, 0);

    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_result_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvContent;
        private View mLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mLine = itemView.findViewById(R.id.line);
            itemView.post(() -> {
                ViewGroup.LayoutParams layoutParams = mLine.getLayoutParams();
                layoutParams.width = ScreenUtils.dp2px(mLine.getContext(), 0.5f);
                layoutParams.height = itemView.getHeight();
                mLine.setLayoutParams(layoutParams);
            });
        }
    }
}
