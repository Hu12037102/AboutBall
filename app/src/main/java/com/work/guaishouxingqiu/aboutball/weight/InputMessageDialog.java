package com.work.guaishouxingqiu.aboutball.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.item.util.ScreenUtils;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.other.EmojiManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.LogUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/3/22 11:32
 * 更新时间: 2019/3/22 11:32
 * 描述:
 */
public class InputMessageDialog extends BaseDialog {


    private TextView mTvCommit;
    private AppCompatEditText mAcetMessage;
    private static final int WHAT = 100;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT:
                    UIUtils.editTextFouse(mAcetMessage);
                    InputMethodManager methodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (methodManager != null && !methodManager.isActive()) {
                        methodManager.showSoftInput(mAcetMessage, 0);
                    }
                    mHandler.removeMessages(WHAT);
                    break;
                default:
                    break;
            }
            return true;
        }
    });
    private CheckBox mCbEmoji;
    private ConstraintLayout mClEmojiParent;
    private BaseViewPager mBvpContent;
    private RadioGroup mRgParent;

    public void setOnInputMessageListener(OnInputMessageListener onInputMessageListener) {
        this.onInputMessageListener = onInputMessageListener;
    }

    private OnInputMessageListener onInputMessageListener;

    public InputMessageDialog(Context context) {
        super(context);
    }

    @Override
    protected void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_mesage_view, null);
        setContentView(view);
        mTvCommit = findViewById(R.id.tv_commit);
        mAcetMessage = findViewById(R.id.aet_message);
        mCbEmoji = findViewById(R.id.cb_emoji);
        mClEmojiParent = findViewById(R.id.cl_emoji_parent);
        mBvpContent = findViewById(R.id.bvp_content);
        mRgParent = findViewById(R.id.rg_parent);

    }

    @Override
    protected void initData() {
        List<Integer> emojiData = EmojiManger.get().getAllDrawable();
        EmojiPagerAdapter mPagerAdapter = new EmojiPagerAdapter(getContext(), emojiData);
        mBvpContent.setAdapter(mPagerAdapter);
        ViewGroup.LayoutParams layoutParams =  mBvpContent.getLayoutParams();
        layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewPager.LayoutParams.WRAP_CONTENT;
        mBvpContent.setLayoutParams(layoutParams);

    }

    @Override
    protected void initEvent() {
        mTvCommit.setOnClickListener(v -> {
            if (DataUtils.isEmpty(DataUtils.checkData(mAcetMessage.getText()))) {
                Toasts.with().showToast(R.string.please_input_message);
            } else if (onInputMessageListener != null) {
                onInputMessageListener.onResultMessage(DataUtils.checkData(mAcetMessage.getText()).toString());
                dismiss();
            }
        });
        mAcetMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.editTextFouse(mAcetMessage);
                mClEmojiParent.setVisibility(View.GONE);
                mCbEmoji.setChecked(false);
                mCbEmoji.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_emoji, 0, 0, 0);
            }
        });
        mCbEmoji.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCbEmoji.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_soft, 0, 0, 0);
                    mClEmojiParent.setVisibility(View.VISIBLE);
                } else {
                    mCbEmoji.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_emoji, 0, 0, 0);
                    mClEmojiParent.setVisibility(View.GONE);
                }
                hideSoft(mCbEmoji);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        mHandler.sendEmptyMessageDelayed(100, 100);
    }

    @Override
    public void dismiss() {
        hideSoft(null);
        super.dismiss();
    }

    private void hideSoft(View view) {
        InputMethodManager methodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager != null /*&& !methodManager.isActive()*/) {
            if (view == null) {
                methodManager.showSoftInput(mAcetMessage, 0);
            } else {
                methodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(WHAT, null);
    }

    public void clearEditData() {
        mAcetMessage.setText(null);
    }


    public interface OnInputMessageListener {
        void onResultMessage(@NonNull String text);
    }

    public static class EmojiPagerAdapter extends PagerAdapter {
        private List<Integer> mData;
        private Context mContext;
        private static final int PAGE_MAX = 40;

        public EmojiPagerAdapter(@NonNull Context context, @NonNull List<Integer> data) {
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public int getCount() {
            LogUtils.w("getCount--", mData.size() % PAGE_MAX + "--" + mData.size() / PAGE_MAX + "--" + mData.size());
            return mData == null ? 0 : mData.size() % PAGE_MAX == 0 ? mData.size() / PAGE_MAX : mData.size() / PAGE_MAX + 1;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            RecyclerView recyclerView = new RecyclerView(mContext);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 8));
            container.addView(recyclerView);
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            recyclerView.setLayoutParams(layoutParams);
            List<Integer> rvData = new ArrayList<>(PAGE_MAX);
            rvData.addAll(mData.subList(position * PAGE_MAX, (position + 1) * PAGE_MAX > mData.size() ? mData.size() : (position + 1) * PAGE_MAX ));
            EmojiAdapter adapter = new EmojiAdapter(mContext, rvData);
            recyclerView.setAdapter(adapter);
            return recyclerView;

        }


        public static class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {
            private List<Integer> mData;
            private Context mContext;

            public EmojiAdapter(@NonNull Context context, @NonNull List<Integer> data) {
                this.mContext = context;
                this.mData = data;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_emoji_child_view, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                holder.mIvEmoji.setImageResource(mData.get(position));
            }

            @Override
            public int getItemCount() {
                return mData == null ? 0 : mData.size();
            }

            static class ViewHolder extends RecyclerView.ViewHolder {

                private final ImageView mIvEmoji;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    mIvEmoji = itemView.findViewById(R.id.iv_emoji);
                    ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                    layoutParams.width = ScreenUtils.getScreenWidth(itemView.getContext()) / 8;
                    layoutParams.height = ScreenUtils.getScreenWidth(itemView.getContext()) / 8;
                    itemView.setLayoutParams(layoutParams);
                }
            }
        }
    }


}
