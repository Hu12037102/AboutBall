package com.work.guaishouxingqiu.aboutball.login.adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.login.bean.RegisterInputEditBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;

import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/11 13:21
 * 更新时间: 2019/3/11 13:21
 * 描述: 注册输入验证码Adapter
 */
public class RegisterInputAdapter extends RecyclerView.Adapter<RegisterInputAdapter.ViewHolder> {

    private List<RegisterInputEditBean> mData;
    private StringBuilder mMessageCode = new StringBuilder();

    public void setmOnInputCompleteResult(OnInputCompleteResult mOnInputCompleteResult) {
        this.mOnInputCompleteResult = mOnInputCompleteResult;
    }

    private OnInputCompleteResult mOnInputCompleteResult;


    public RegisterInputAdapter(@NonNull List<RegisterInputEditBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_register_input_edit, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTietText.setText(mData.get(i).text);
        viewHolder.mTietText.setBackgroundResource(mData.get(i).isCheck ?
                R.drawable.shape_register_input_check_edit : R.drawable.shape_register_input_default_edit);
        viewHolder.mTietText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mData.get(i).text = DataUtils.checkData(viewHolder.mTietText.getText()).toString();
                int flag = 0;

                for (int j = 0; j < mData.size(); j++) {
                    if (!DataUtils.isEmpty(mData.get(j).text)) {
                        flag++;
                        mMessageCode.append(mData.get(j).text);
                    }
                }
                if (flag == mData.size() - 1 && mOnInputCompleteResult != null) {
                    mOnInputCompleteResult.onInputComplete(mMessageCode.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextInputEditText mTietText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTietText = itemView.findViewById(R.id.tiet_text);

        }
    }

    public interface OnInputCompleteResult {
        void onInputComplete(@NonNull String messageCode);
    }
}
