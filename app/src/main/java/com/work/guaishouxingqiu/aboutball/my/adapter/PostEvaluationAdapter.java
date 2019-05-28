package com.work.guaishouxingqiu.aboutball.my.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxiaobai.adapter.BaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.work.guaishouxingqiu.aboutball.Contast;
import com.work.guaishouxingqiu.aboutball.R;
import com.work.guaishouxingqiu.aboutball.my.bean.ResultInputEvaluationBean;
import com.work.guaishouxingqiu.aboutball.other.GlideManger;
import com.work.guaishouxingqiu.aboutball.util.DataUtils;
import com.work.guaishouxingqiu.aboutball.util.UIUtils;
import com.work.guaishouxingqiu.aboutball.weight.ExpandableTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/5/27 13:32
 * 更新时间: 2019/5/27 13:32
 * 描述:用户评价Adapter
 */
public class PostEvaluationAdapter extends BaseRecyclerAdapter<PostEvaluationAdapter.ViewHolder, List<ResultInputEvaluationBean>> {
    private int mFlag;

    public PostEvaluationAdapter(@NonNull List<ResultInputEvaluationBean> data, int flag) {
        super(data);
        this.mFlag = flag;
    }

    @Override
    protected void onBindViewDataHolder(@NonNull ViewHolder viewHolder, int i) {
        ResultInputEvaluationBean bean = mData.get(i);
        switch (mFlag) {
            case Contast.InputEvaluationType.REFEREE:
                GlideManger.get().loadHeadImage(mContext, bean.photoUrl, viewHolder.mCivHead);
                break;
            case Contast.InputEvaluationType.TEAMMATE:
            case Contast.InputEvaluationType.OPPONENT:
                GlideManger.get().loadHeadImage(mContext, bean.headerImg, viewHolder.mCivHead);
                break;
            default:
                break;
        }
        UIUtils.setText(viewHolder.mTvName, bean.nickName);
        UIUtils.setText(viewHolder.mTvTime, bean.commentTime);
        viewHolder.mEtvContent.setText("Nav logo\n" +
                "首页\n" +
                "下载App\n" +
                "搜索\n" +
                "×\n" +
                "广告\n" +
                "华为MateX正式发布，价格感人，网友：看看就好！\n" +
                "96  科技探索新发现丨 \n" +
                " 0.2 2019.02.25 14:27 字数 1432 阅读 612评论 1喜欢 9\n" +
                "转眼间已经到了2月下旬，年初新品手机市场也是逐渐拉开序幕，就拿最近来说，各大厂商新机便是一款接着一款发布，无独有偶，就在刚刚华为也是在MWC大会中发布了自家今年的扛鼎之作华为Mate X，作为华为首款折叠屏手机，华为Mate X无论是外观、性能、功能都让人眼前一亮，不得不说华为技术是真的牛。\n" +
                "\n" +
                "\n" +
                "外观方面华为Mate X可以说相当具有科技感与未来感，其共有三种形态，第一种在完全展开的情况下，华为Mate X屏幕大小为8英寸，其形态更接近于平板，第二种在折叠的情况下，华为Mate X前置面板大小为6.6寸，其形态更类似于大屏手机，第三种在折叠情况下，华为Mate X背部面板大小为6.38英寸，其形态像是一款十分修长的手机。\n" +
                "\n" +
                "\n" +
                "值得一提的是，无论是哪一种形态，华为Mate X屏占比均相当的可观，尤其是完全展开与折叠时前置面板这两种形态，屏占比更是接近于100%，视觉效果相当的惊艳，而在细节方面，华为Mate X折叠方式为向外折叠，折叠部分通过铰链进行连接，值得一提的是，华为Mate X采用的铰链是华为自家的专利，屏幕在折叠时中间没有任何空隙，相当的平坦。\n" +
                "\n" +
                "\n" +
                "作为对比，前不久三星也是发布了自家的首款折叠屏机型，不过从颜值方面来说，华为Mate X应该完胜三星折叠屏，国产手机能够在外观上胜过三星，华为Mate X应该算是一个里程碑了，当然对比iPhoneXS Max，华为Mate X在颜值上更是有明显优势，当苹果、三星高管看到华为Mate X时，应该也会心生敬佩吧。\n" +
                "\n" +
                "\n" +
                "而除了靓丽的外观，华为Mate X在硬件上也是相当奢华，其搭载了麒麟980处理器，配备8G+512G存储组合，性能达到了行业的顶级水平，配备4500毫安电池，支持高达55W的超级快充，续航能力也是极其给力，另外华为Mate X还内置了巴龙5000基带，实现了对于5G网络的支持，按照余承东的话，3秒钟能够下载1G大小的电影，每秒下载速度在300MB左右。 手机已经成为了我们每个人都必不可少的生活用品，时代的快速发展也造就了很多手机品牌的出现。作为一位手机数码控，我一直钟爱iPhone系列，毕竟iOS的流畅度确实是安卓无法代替的！就说说我新入手的iPhoneXS MAX吧：A12仿生芯片真是强大，下载二十来个应用程序后玩王者仍无压力，运行相当流畅！6.5寸超大屏幕看起电影更是超爽！\n" +
                "\n" +
                "\n" +
                "iPhoneX已结是手机中的战斗机了，XS MAX更是比X高出一个层次！好多朋友看到这可能以为我是在炫富。其实不然，小编我也是月光一族。能买得起XS MAX还得多谢雷石自装了，从7P开始，一直从他那入手新款。XS MAX流畅度那是相当神速，Face ID功能秒杀指纹解锁，特别是在这大冷天带着手套，你就会发现Face ID功能简直就是冬天解锁福星！\n" +
                "\n" +
                "\n" +
                "这就是我三千多入手的自装XS MAX 256G的古铜金了。比起7P的土豪金更内敛，但玻璃后盖的淡金又更具手感与档次！手机能比官方优惠一大半，区别还是有的，那就是它不是原封进不了官方售后，当然配件都是原厂全新的，相当于三无机所以价格低，其操作使用都是一样的。至于质量，我从7P开始用到X都没出现什么大问题，所以他们的XS MAX我很放心推荐。以上图片都是来自雷石科技 lsvx2288 的，需要的搜微可以找他们。XS MAX的性能很强大，的确值得入手。虽然三星Note 9在外观方面没有获奖，但在材料上依然扎实，各方面的用户体验和实用性都非常强，国外媒体更注重实用和优化软件，而不是外观的主观偏好，因此三星Note 9仍然成为最普遍、最外国媒体和外国消费者眼中平衡的android机皇帝。你对今年的安卓手机有什么想法？\n" +
                "\n" +
                "\n" +
                "至于大家关心的价格方面，由于各项新技术的采用，华为Mate X价格并不便宜，欧洲售价为2299欧元，约合17000多元人民币，可以说价格相当的感人，不少网友了解完价格后也是表示，这是买不起系列，看看就好，小伙伴们，对于华为Mate X你怎么看？\n" +
                "\n" +
                "来源：众多的黑\u200B\n" +
                "\n" +
                "小礼物走一走，来简书关注我\n" +
                "\n" +
                " 日记本 © 著作权归作者所有 举报文章\n" +
                "96 科技探索新发现丨 \n" +
                "写了 237246 字，被 19 人关注，获得了 55 个喜欢\n" +
                "\n" +
                "探索科技最新资料，发现科技最新资讯。\n" +
                "   更多分享\n" +
                "Web note ad 1\n" +
                "华为Mate X正式发布：最强5G折叠手机，能把三星Fold按在地上摩擦\n" +
                "在MWC2019大会期间，手机厂商可谓是展开了一场“各显神通”的盛宴，韩国三星手机虽然提前几天在美国发布了S10系列旗舰和极具创意的三星Fold折叠屏手机，但熟话说的好，一山更比一山高，三天刚刚过去，中国品牌华为就远赴巴塞罗那正式发布了旗下首款5G折叠手机——HUAWEI ...\n" +
                "\n" +
                " 48  智玩部落\n" +
                " 240\n" +
                "华为mate 20 Pro体验：唯一能和苹果三星分庭抗礼的国产旗舰\n" +
                "Mate系列是华为手机最重要的顶级旗舰系列，曾经凭借Mate 7一举进入高端市场，再经过P系列的迭代，已经在国内高端市场站稳了脚步。说到这就不得不提一下今年三月份发布的华为P20系列，凭借大底优势和无敌的夜景，再发布半余年后，现在依然霸榜DxO排行榜。 现在，华为又带来了全...\n" +
                "\n" +
                " 48  点述\n" +
                " 240\n" +
                "信号差距有多大？华为Mate 20对比iPhone XS Max\n" +
                "2018年即将过去，也许不少人都想更换一台新手机。在此时此刻，最热门的两款手机旗舰，无疑就是苹果iPhone XS Max和华为Mate 20。 iPhone XS Max拥有A12处理器，6.5英寸2688*1242视网膜屏幕，1200万像素双摄，iOS 12系统，甚至还...\n" +
                "\n" +
                " 48  智玩TOP\n" +
                " 240\n" +
                "年度大屏旗舰之争：华为Mate 20 X对比iPhone XS MAX评测\n" +
                "眨眼就到了2018年的年底，很多人都准备换新机了吧，不过新近发布的手机实在太多，低中高档都是如此，可能你会比较纠结，但是如果你想购买高端大屏旗舰机，我想最热门的当属华为Mate 20 X和苹果iPhone XS MAX，那么这两款手机到底该选哪款呢？ 珍珠屏对宽刘海谁更美？...\n" +
                "\n" +
                " 48  叔叔科技\n" +
                " 240\n" +
                "二代莱卡镜头加持，最强商务旗舰来袭——华为Mate 9测评\n" +
                "虽然安卓让国产机摆脱了山寨的名号，但一直都是低价和性价比的代名词，高端机一直被苹果、三星，甚至早年的HTC、摩托罗拉、诺基亚等垄断。作为主打高端商务人士的商务机，也一直没有国产机能够占有一席之地，直到HUAWEI Mate系列的出现，在商务高端机杀出了一片天地，不但国内畅销...\n" +
                "\n" +
                " 48  科技数码说\n" +
                "与猫咪相处需要注意什么？\n" +
                "说到猫咪相信没有小伙伴会感到陌生吧，在生活中也有越来越多的小伙伴开始饲养这些可爱的小动物，那么大家知道在与猫咪相处的时候需要注意什么吗？今天小编给大家带来了相关的知识，有需要的小伙伴一起来参考一下吧。 猫咪在刚进入家门的时候肯定会非常的害怕，就像是我们独自跑到一个陌生的环境...\n" +
                "\n" +
                " 48  傲娇的adc\n" +
                "忙碌\n" +
                "一个人在广州，两年，朋友不多。 有时候后悔来广州，亲人朋友都不在身边，支持自己的只有自己，每每有人问起为何在广州，我只能回答就是缘分。 真的喜欢这座城市吗？大概吧，有美食，有山水，环境好，房价便宜（相对于深圳、上海、北京）应该是属于性价比最高的城市了吧。 慢慢的，我也开始有...\n" +
                "\n" +
                " 48  小流于江海\n" +
                " 240\n" +
                "男人不想让女人知道的秘密？！\n" +
                "2016-8-7晴 有句话叫：人因希望而活。 现在的日记进入故事模式，有读者问我故事的结果，我也不知道是什么结果，这些故事并非完全是我个人创作的，未来会和写作团队一起创作，包括和读者交流的灵感，都会通过某个适合的切入点充实到故事中去。 如果你有故事想分享出来，你可以和我一起...\n" +
                "\n" +
                " 48  实干日志\n" +
                "这些年“离开”的人——外公\n" +
                "外公对于我来说，印象深刻的就是“弟子规”、“百家姓”、气球、钱、白糖……外公和外婆因为工作原因分居四十多年，所以只有春节才是全家团圆的时候。记得我上小学时有年春节两人吵的团圆饭没有吃好，外公外婆在各自房间，偷偷去看外公他在房间偷偷抹眼泪。其实外公是个可敬可悲可爱的人。 可敬...\n" +
                "\n" +
                " 48  仓颉守望麦田\n" +
                " 240\n" +
                "飞飞日复盘Day102 20180419\n" +
                "11问 1.自由职业，上下班时间自由 2.年薪60万 3.两层的独门小院别墅 4.跑车和7人座SUV 5.不看吊牌只买适合的衣服 6.老师 7.传授技能 8.想 9.世界各国 10.有幸福家庭有喜欢做的事情 11.能够成为 二、人生7问 1.不满意，一团糟 3.因为工作加班...\n" +
                "\n" +
                " 48  AI成长家");
        initImageContent(viewHolder, i);


    }

    private void initImageContent(ViewHolder viewHolder, int i) {
        ResultInputEvaluationBean bean = mData.get(i);
        int imageCount = DataUtils.splitImagePathCount(bean.commentPic);
        if (imageCount > 0) {
            viewHolder.mLlImages.setVisibility(View.VISIBLE);
            String[] imagePathArray = bean.commentPic.split(",");
            View inflateView = null;
            switch (imagePathArray.length) {
                case 1:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_1, viewHolder.mLlImages, false);
                    RoundedImageView riv1_1 = inflateView.findViewById(R.id.riv_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv1_1);
                    break;
                case 2:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_2, viewHolder.mLlImages, false);
                    RoundedImageView riv2_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv2_2 = inflateView.findViewById(R.id.riv_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv2_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv2_2);
                    break;
                case 3:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_3, viewHolder.mLlImages, false);
                    RoundedImageView riv3_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv3_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv3_3 = inflateView.findViewById(R.id.riv_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv3_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv3_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv3_3);
                    break;
                case 4:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_4, viewHolder.mLlImages, false);
                    RoundedImageView riv4_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv4_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv4_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv4_4 = inflateView.findViewById(R.id.riv_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv4_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv4_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv4_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv4_4);
                    break;
                case 5:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_5, viewHolder.mLlImages, false);
                    RoundedImageView riv5_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv5_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv5_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv5_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv5_5 = inflateView.findViewById(R.id.riv_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv5_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv5_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv5_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv5_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv5_5);
                    break;
                case 6:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_6, viewHolder.mLlImages, false);
                    RoundedImageView riv6_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv6_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv6_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv6_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv6_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv6_6 = inflateView.findViewById(R.id.riv_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv6_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv6_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv6_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv6_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv6_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv6_6);
                    break;
                case 7:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_7, viewHolder.mLlImages, false);
                    RoundedImageView riv7_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv7_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv7_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv7_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv7_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv7_6 = inflateView.findViewById(R.id.riv_6);
                    RoundedImageView riv7_7 = inflateView.findViewById(R.id.riv_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv7_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv7_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv7_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv7_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv7_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv7_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], riv7_7);
                    break;
                case 8:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_8, viewHolder.mLlImages, false);
                    RoundedImageView riv8_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv8_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv8_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv8_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv8_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv8_6 = inflateView.findViewById(R.id.riv_6);
                    RoundedImageView riv8_7 = inflateView.findViewById(R.id.riv_7);
                    RoundedImageView riv8_8 = inflateView.findViewById(R.id.riv_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv8_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv8_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv8_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv8_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv8_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv8_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], riv8_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[7], riv8_8);
                    break;
                case 9:
                    inflateView = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_image_9, viewHolder.mLlImages, false);
                    RoundedImageView riv9_1 = inflateView.findViewById(R.id.riv_1);
                    RoundedImageView riv9_2 = inflateView.findViewById(R.id.riv_2);
                    RoundedImageView riv9_3 = inflateView.findViewById(R.id.riv_3);
                    RoundedImageView riv9_4 = inflateView.findViewById(R.id.riv_4);
                    RoundedImageView riv9_5 = inflateView.findViewById(R.id.riv_5);
                    RoundedImageView riv9_6 = inflateView.findViewById(R.id.riv_6);
                    RoundedImageView riv9_7 = inflateView.findViewById(R.id.riv_7);
                    RoundedImageView riv9_8 = inflateView.findViewById(R.id.riv_8);
                    RoundedImageView riv9_9 = inflateView.findViewById(R.id.riv_9);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[0], riv9_1);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[1], riv9_2);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[2], riv9_3);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[3], riv9_4);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[4], riv9_5);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[5], riv9_6);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[6], riv9_7);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[7], riv9_8);
                    GlideManger.get().loadBannerImage(mContext, imagePathArray[8], riv9_9);
                    break;
                default:
                    break;

            }
            viewHolder.mLlImages.addView(inflateView);
        }else {
            viewHolder.mLlImages.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder onCreateDataViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_input_evaluation_view, viewGroup, false));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mCivHead;
        private TextView mTvName;
        private TextView mTvTime;
        private ExpandableTextView mEtvContent;
        private LinearLayout mLlImages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mCivHead = itemView.findViewById(R.id.civ_head);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvTime = itemView.findViewById(R.id.tv_time);
            mEtvContent = itemView.findViewById(R.id.rtv_content);
            mLlImages = itemView.findViewById(R.id.ll_images);
        }
    }
}
