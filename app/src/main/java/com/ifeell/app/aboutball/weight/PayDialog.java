package com.ifeell.app.aboutball.weight;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.item.weight.ItemView;
import com.example.item.weight.TitleView;
import com.ifeell.app.aboutball.R;
import com.ifeell.app.aboutball.util.UIUtils;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/15 18:18
 * 更新时间: 2019/5/15 18:18
 * 描述:支付dialog
 */
public class PayDialog extends BaseDialog {

    private TextView mTvPay;
    private TextView mTvMoney;
    private TitleView mTitleView;
    public int mSelectorPosition;//0:微信；1：支付宝；2：银联...
    private ItemView mItemWeiChat;
    public static final int WEI_CHET_STATUS = 0;
    public static final int ZHI_FU_BAO_STATUS = 1;

    public void setOnPayDialogClickListener(OnPayDialogClickListener onPayDialogClickListener) {
        this.onPayDialogClickListener = onPayDialogClickListener;
    }

    private OnPayDialogClickListener onPayDialogClickListener;

    public PayDialog(Context context) {
        super(context);
    }

    @Override
    protected void initEvent() {
        mTitleView.setOnBackViewClickListener(view -> dismiss());
    }

    @Override
    protected void initData() {
        mTvMoney.setText(UIUtils.getString(R.string.money_count, "0.0"));
    }

    @Override
    protected void initView(Context context) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.dialog_pay_view, null);
        this.setContentView(inflateView);
        mTitleView = findViewById(R.id.title_view);
        mTvMoney = findViewById(R.id.tv_money);
        mTvPay = findViewById(R.id.tv_pay);
        mItemWeiChat = findViewById(R.id.item_weichat);
        mItemWeiChat.setOnItemClickListener(new ItemView.OnItemClickListener() {
            @Override
            public void onClickItem(View view) {
                mSelectorPosition = PayDialog.WEI_CHET_STATUS;
            }
        });
        mTvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mSelectorPosition) {
                    case PayDialog.WEI_CHET_STATUS:
                        if (onPayDialogClickListener != null) {
                            onPayDialogClickListener.onWeiChat(mTvPay);
                        }
                        break;
                    default:
                        if (onPayDialogClickListener != null) {
                            onPayDialogClickListener.onWeiChat(mTvPay);
                        }
                        break;
                }
                dismiss();

            }
        });
    }

    public PayDialog setMoney(double money) {
        mTvMoney.setText(UIUtils.getString(R.string.money_count, money));
        return this;
    }

    public PayDialog setMoney(String money) {
        mTvMoney.setText(money);

        return this;
    }

    /*  public void setOnClickPayListener(View.OnClickListener clickPayListener) {
          if (clickPayListener != null) {
              mTvPay.setOnClickListener(clickPayListener);
             // dismiss();
          }
      }*/
    public interface OnPayDialogClickListener {
        void onWeiChat(@NonNull View view);
    }
}
