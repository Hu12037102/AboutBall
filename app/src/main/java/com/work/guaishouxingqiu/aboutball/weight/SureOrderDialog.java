package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.OnItemClickListener;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.base.bean.ResultSureOrderDialogBean;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/8/28 10:55
 * 更新时间: 2019/8/28 10:55
 * 描述: 确认订单dialog
 */
public class SureOrderDialog extends BaseDialog {

    private ImageView mIvClose;
    private TextView mTvSures;
    private InputNumberView mInvCount;
    private ResultSureOrderDialogBean mDialogBean;
    private TextView mTvMoney;
    private LinearLayout mLlData;

    private final List<List<ResultSureOrderDialogBean.SpecificationChildBean>> mSpecificationChildData;

    public void setOnSureOrderClickListener(OnSureOrderClickListener onSureOrderClickListener) {
        this.onSureOrderClickListener = onSureOrderClickListener;
    }

    private OnSureOrderClickListener onSureOrderClickListener;


    public SureOrderDialog(Context context, ResultSureOrderDialogBean bean) {
        super(context);
        this.mDialogBean = bean;
        mSpecificationChildData = new ArrayList<>();
        initLoadData();
    }


    private void initLoadData() {
        if (mDialogBean == null) {
            dismiss();
            UIUtils.showToast("没有获取到订单参数，请重试！");
            return;
        }
        UIUtils.setText(mTvMoney, DataUtils.getMoneyFormat(mDialogBean.price));
        mInvCount.setMaxNum(getCheckCount());
        mLlData.removeAllViews();
        mLlData.removeAllViewsInLayout();
        mSpecificationChildData.clear();
        if (mDialogBean.specificationVOList != null && mDialogBean.specificationVOList.size() > 0) {
            for (int i = 0; i < mDialogBean.specificationVOList.size(); i++) {
                ResultSureOrderDialogBean.SpecificationBean bean = mDialogBean.specificationVOList.get(i);
                mSpecificationChildData.add(i, bean.specificationValueVOList);
                View childView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_sure_order_view, mLlData, false);
                mLlData.addView(childView);
                View inflateChildView = mLlData.getChildAt(i);
                TextView tvTitle = inflateChildView.findViewById(R.id.tv_title);
                UIUtils.setText(tvTitle, bean.title);
                RecyclerView rvData = inflateChildView.findViewById(R.id.rv_data);
                rvData.setLayoutManager(new GridLayoutManager(getContext(), 3));
                SureOrderDialog.Adapter adapter = new SureOrderDialog.Adapter(getContext(), mSpecificationChildData.get(i)
                        , bean.isMultipleChoices, i != mDialogBean.specificationVOList.size() - 1);
                rvData.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onClickItem(@NonNull View view, int position) {
                        LogUtils.w("SureOrderDialog--", "我被点击了" + getAllValues());
                        if (onSureOrderClickListener != null) {
                            onSureOrderClickListener.onClickItemNotify(view, position, getAllValues(), mInvCount.getNum());
                        }
                        mInvCount.setInputNum(1);
                        UIUtils.setText(mTvMoney, DataUtils.getMoneyFormat(mInvCount.getNum() * DataUtils.getDoubleFormat(mDialogBean.price)));
                    }
                });
            /*    if (mAdapterList.size() == 0) {
                    SureOrderDialog.Adapter adapter = new SureOrderDialog.Adapter(getContext(), mSpecificationChildData.get(i), bean.isMultipleChoices);
                    rvData.setAdapter(adapter);
                    mAdapterList.add(adapter);
                } else {
                    mAdapterList.get(i).notifyDataSetChanged();
                }*/
            }
        }
    }

    private void notifyData(ResultSureOrderDialogBean bean) {
        this.mDialogBean = bean;
        initLoadData();
    }

    @Override
    protected void initEvent() {
        mIvClose.setOnClickListener(v -> dismiss());
        mTvSures.setOnClickListener(v -> {
            if (onSureOrderClickListener != null) {
                onSureOrderClickListener.onClickSureBuy(v, getAllValues(), mInvCount.getNum());
            }
        });
        mInvCount.setOnClickInputClickListener(new InputNumberView.OnClickInputClickListener() {
            @Override
            public void onClickSubtract(View view, int num) {
                UIUtils.setText(mTvMoney, DataUtils.getMoneyFormat(num * DataUtils.getDoubleFormat(mDialogBean.price)));
            }

            @Override
            public void onClickAdd(View view, int num) {
                UIUtils.setText(mTvMoney, DataUtils.getMoneyFormat(num * DataUtils.getDoubleFormat(mDialogBean.price)));
            }
        });
    }

    private String getAllValues() {
        String values = "";
        if (mDialogBean.specificationVOList != null && mDialogBean.specificationVOList.size() > 0) {
            for (int i = 0; i < mDialogBean.specificationVOList.size(); i++) {
                ResultSureOrderDialogBean.SpecificationBean bean = mDialogBean.specificationVOList.get(i);
                List<ResultSureOrderDialogBean.SpecificationChildBean> childBeanList = bean.specificationValueVOList;
                for (int j = 0; j < childBeanList.size(); j++) {
                    if (childBeanList.get(j).isChecked == 1) {
                        values = values.concat(childBeanList.get(j).value).concat(",");
                    }
                }

            }
        }
        if (!DataUtils.isEmpty(values) && values.endsWith(",")) {
            values = values.substring(0, values.length() - 1);
        }
        return values;
    }

    /**
     * 获取选中最大的票数
     *
     * @return
     */
    private int getCheckCount() {
        int checkMaxCount = 0;
        if (mDialogBean.specificationVOList != null && mDialogBean.specificationVOList.size() > 0) {
            for (int i = 0; i < mDialogBean.specificationVOList.size(); i++) {
                ResultSureOrderDialogBean.SpecificationBean bean = mDialogBean.specificationVOList.get(i);
                List<ResultSureOrderDialogBean.SpecificationChildBean> childBeanList = bean.specificationValueVOList;
                for (int j = 0; j < childBeanList.size(); j++) {
                    if (childBeanList.get(j).isChecked == 1) {
                        checkMaxCount = childBeanList.get(i).stock;
                    }
                }

            }
        }
        return checkMaxCount;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sure_order_view, null);
        setContentView(view);
        mTvSures = findViewById(R.id.tv_sures);
        mIvClose = findViewById(R.id.iv_close);
        mInvCount = findViewById(R.id.inv_count);
        mTvMoney = findViewById(R.id.tv_money_count);
        mLlData = findViewById(R.id.ll_data);

    }

    public static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private Context mContext;
        private List<ResultSureOrderDialogBean.SpecificationChildBean> mData;
        private int mIsMultipleChoices;
        private boolean mIsIndexChang;
        private boolean mIsCanClick;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        private OnItemClickListener onItemClickListener;

        public Adapter(@NonNull Context context, @NonNull List<ResultSureOrderDialogBean.SpecificationChildBean> data, int isMultipleChoices, boolean isIndexChang) {
            this.mContext = context;
            this.mData = data;
            this.mIsMultipleChoices = isMultipleChoices;
            this.mIsIndexChang = isIndexChang;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_sure_order_child_view, parent, false));
        }

        public void checkChang(int position) {
            mIsCanClick = false;
            if (mIsMultipleChoices == 0) {
                if (mData.get(position).isChecked != 1) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (i != position) {
                            mData.get(i).isChecked = 0;
                        } else {
                            mData.get(position).isChecked = 1;
                        }
                    }
                    mIsCanClick = true;
                    notifyDataSetChanged();
                }
            } else {
                if (mData.get(position).isChecked != 1) {
                    mData.get(position).isChecked = 1;
                    mIsCanClick = true;
                    notifyDataSetChanged();
                }
            }

        }

        private String getValuesContent() {
            String values = "";
            for (ResultSureOrderDialogBean.SpecificationChildBean bean : mData) {
                if (bean.isChecked == 1) {
                    values = values.concat(bean.value);
                }
            }
            return values;
        }

        public int getCheckMaxCount() {
            int checkMaxCount = 0;
            for (ResultSureOrderDialogBean.SpecificationChildBean bean : mData) {
                if (bean.isChecked == 1) {
                    checkMaxCount = bean.stock;
                }
            }
            return checkMaxCount;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ResultSureOrderDialogBean.SpecificationChildBean bean = mData.get(position);
            if (bean.isChecked == 1) {
                holder.mTvContent.setBackgroundResource(R.drawable.shape_click_button);
                UIUtils.setTextColor(holder.mTvContent, R.color.colorWhite);
            } else {
                holder.mTvContent.setBackgroundResource(R.drawable.shape_default_fbfafa_button);
                UIUtils.setTextColor(holder.mTvContent, R.color.color_4);
            }
            if (bean.isInvalid == 0) {
                holder.mTvContent.setEnabled(true);
            } else {
                holder.mTvContent.setEnabled(false);
            }
            UIUtils.setText(holder.mTvContent, bean.name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // LogUtils.w("setOnClickListener--", mIsIndexChang + "---" + bean.isChecked);
                    checkChang(position);
                    if (mIsCanClick && onItemClickListener != null) {
                        onItemClickListener.onClickItem(v, position);
                    }

                }

            });
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            layoutParams.topMargin = ScreenUtils.dp2px(mContext, 15);
            if (position % 3 == 0) {
                layoutParams.leftMargin = ScreenUtils.dp2px(mContext, 20);
            } else {
                layoutParams.leftMargin = ScreenUtils.dp2px(mContext, 10);
            }
            holder.itemView.setLayoutParams(layoutParams);
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTvContent;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                initView(itemView);
            }

            private void initView(View itemView) {
                mTvContent = itemView.findViewById(R.id.tv_content);
            }
        }

    }

    public interface OnSureOrderClickListener {
        void onClickSureBuy(View view, String allValues, int num);

        void onClickItemNotify(View view, int position, String values, int num);
    }

}
