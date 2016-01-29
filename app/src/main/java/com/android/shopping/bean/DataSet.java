package com.android.shopping.bean;

import android.content.Context;

import com.android.shopping.R;
import com.android.shopping.utils.DbUtils;
import com.android.shopping.utils.ListUtils;
import com.android.shopping.utils.Logger;
import com.android.shopping.utils.MathUtils;
import com.android.shopping.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
public class DataSet {

    public static String[] GoodsIds = {
            "10000", "10001", "10002", "10003", "10004", "10005", "10006", "10007", "10008", "10009",
            "10010", "10011", "10012", "10013", "10014", "10015", "10016", "10017", "10018", "10019",
            "10020", "10021", "10022", "10023", "10024", "10025", "10026", "10027", "10028", "10029",
            "10030", "10031", "10032", "10033", "10034", "10035", "10036", "10037", "10038", "10039",
    };

    public static int[] GoodsPrices = {
            13800, 19800, 52800, 60800, 48000, 116800, 98000, 32000, 36000, 1480,
            36200, 47000, 16800, 600, 32000, 20480, 2480, 14600, 16800, 4200,
            41600, 19800, 480, 960, 16800, 118000, 9800, 470, 2600, 48000
    };

    public static int[] GoodsCredits = {
            10, 5, 10, 10, 10, 10, 10, 5, 10, 5,
            10, 10, 5, 5, 10, 5, 5, 5, 5, 5,
            10, 5, 5, 5, 5, 10, 5, 5, 5, 10,
    };

    public static String[] LuckyNames = {
            "Sharma Sandeep Kumar", "Sumit Ahuja", "Mohindra Sharma", "Naveen Tiwari", "prasanna mishra",
            "Viney Mehta", "Gautam kumar", "Jitendar Kumar", "Vimal pal", "Siyawar Sharan Singh",
            "Sagar Arora", "Naveen Kumar", "Har Swaroop Tewatia", "Inder Adhikari", "Dinker Nagaraja Rao",
            "Ved Pratap", "Vinod Kumar", "Kamal Kapoor", "Sahoo Dev", "Deepesh Sachdev",
            "Kanika Soni", "Monika Chadha", "Minal Nath", "Alka Singh", "Priyanka Kushwaha",
            "Rupali Kochhar", "Barsha Rawat", "Deepa Joseph", "Richa Sharma", "Kiran Arora",
            "Sarita Bisht", "Archana choubey", "Praerna Mohan", "Aditi Asthana", "Priya Sharma",
            "Neelam Seth", "Sadhana Rani", "Sangeeta Mahendra", "Jyoti Grover", "Neeti Aurora",
            "Jitendra Sharma", "Saneesh Sehgal", "Rachit Kumar", "Anoop Mittal", "Mridul Sharma",
            "Ankur Nayyar", "Vikram Khaitan", "Sonal Vaidya", "Amit Mathur", "Ankush Jain",
            "Timsy Jain", "Jyotsana Wadhwa", "Sadhna Tripathi", "Monica Lyall", "Guneet Malik",
            "Susmita Rautray", "Tannya Rakhra", "Kavita Pathak", "Anandita Tyagi", "Richu Gakhar"
    };

    public static String[] GoodsNames = {
            "Apple four generations TV box", "Apple Ipad mini2", "Apple Ipad Pro 12.9 inches",
            "Apple iphone 6s plus", "Apple iphone 6s", "Apple MacBook 12 inches",
            "Apple MacBook Pro 13.3 inches", "Apple watch", "Asus business notebook B43S",
            "Beats Studio Wireless", "Canon EOS600D", "DJI Phantom3 1.7K",
            "Dostyle SD312 2.1", "ES bluetooth headset", "Four generations of Apple TV",
            "One plus phone 2", "Google sports bracelet", "HTC Desire 826d",
            "Huawei honor U880", "Konka air purifier", "Microsoft Xbox One console",
            "Millet mobile phone 4", "Millet sound", "MOGA bluetooth gamepad",
            "MOTO smart watches", "New apple MAC proimac", "Red rice Note2",
            "Smartisan S-1000", "SONY VR headsets", "The samsung galaxy s6",
    };

    public static int[] GoodsIcons = {
            R.mipmap.ic_goods_1, R.mipmap.ic_goods_2, R.mipmap.ic_goods_3, R.mipmap.ic_goods_4,
            R.mipmap.ic_goods_5, R.mipmap.ic_goods_6, R.mipmap.ic_goods_7, R.mipmap.ic_goods_8,
            R.mipmap.ic_goods_9, R.mipmap.ic_goods_10, R.mipmap.ic_goods_11, R.mipmap.ic_goods_12,
            R.mipmap.ic_goods_13, R.mipmap.ic_goods_14, R.mipmap.ic_goods_15, R.mipmap.ic_goods_16,
            R.mipmap.ic_goods_17, R.mipmap.ic_goods_18, R.mipmap.ic_goods_19, R.mipmap.ic_goods_20,
            R.mipmap.ic_goods_21, R.mipmap.ic_goods_22, R.mipmap.ic_goods_23, R.mipmap.ic_goods_24,
            R.mipmap.ic_goods_25, R.mipmap.ic_goods_26, R.mipmap.ic_goods_27, R.mipmap.ic_goods_28,
            R.mipmap.ic_goods_29, R.mipmap.ic_goods_30
    };

    public static int[] Atavars = {
            R.mipmap.ic_winner_1, R.mipmap.ic_winner_2, R.mipmap.ic_winner_3, R.mipmap.ic_winner_4,
            R.mipmap.ic_winner_5, R.mipmap.ic_winner_6, R.mipmap.ic_winner_7, R.mipmap.ic_winner_8,
            R.mipmap.ic_winner_9, R.mipmap.ic_winner_10, R.mipmap.ic_winner_11, R.mipmap.ic_winner_12,
            R.mipmap.ic_winner_13, R.mipmap.ic_winner_14, R.mipmap.ic_winner_15, R.mipmap.ic_winner_16,
            R.mipmap.ic_winner_17, R.mipmap.ic_winner_18, R.mipmap.ic_winner_19, R.mipmap.ic_winner_20,
            R.mipmap.ic_winner_21, R.mipmap.ic_winner_22, R.mipmap.ic_winner_23, R.mipmap.ic_winner_24,
            R.mipmap.ic_winner_25, R.mipmap.ic_winner_26, R.mipmap.ic_winner_27, R.mipmap.ic_winner_28,
            R.mipmap.ic_winner_29, R.mipmap.ic_winner_30, R.mipmap.ic_winner_31, R.mipmap.ic_winner_32,
            R.mipmap.ic_winner_33, R.mipmap.ic_winner_34, R.mipmap.ic_winner_35, R.mipmap.ic_winner_36,
            R.mipmap.ic_winner_37, R.mipmap.ic_winner_38, R.mipmap.ic_winner_39, R.mipmap.ic_winner_40,
            R.mipmap.ic_male_1, R.mipmap.ic_male_1, R.mipmap.ic_male_1, R.mipmap.ic_male_1, R.mipmap.ic_male_1,
            R.mipmap.ic_male_1, R.mipmap.ic_male_1, R.mipmap.ic_male_1, R.mipmap.ic_male_1, R.mipmap.ic_male_1,
            R.mipmap.ic_female_1, R.mipmap.ic_female_1, R.mipmap.ic_female_1, R.mipmap.ic_female_1, R.mipmap.ic_female_1,
            R.mipmap.ic_female_1, R.mipmap.ic_female_1, R.mipmap.ic_female_1, R.mipmap.ic_female_1, R.mipmap.ic_female_1
    };

    public static int[] Flashs = {
            R.mipmap.ic_home_flash_1, R.mipmap.ic_home_flash_2, R.mipmap.ic_home_flash_3, R.mipmap.ic_home_flash_4
    };

    public static String[] Citys = {
            "Sherkot,Uttar Pradesh", "Mathura,Uttar Pradesh", "Sikanderpur,Uttar Pradesh", "Obra,Uttar Pradesh",
            "Malda,West Bengal", "Sonamukhi,West Bengal", "Adra,West Benga", "Suri,West Benga",
            "Farooqnagar,Telangana", "Mahbubnagar,Telangana", "Karimnagar,Telangana", "Palladam,Telangana",
            "Shahpura,Rajasthan", "Losal,Rajasthan", "Lakheri,Rajasthan", "Sumerpur,Rajasthan",
            "Zira,Punjab", "Samana,Punjab", "Dhuri,Punjab", "Tarn Taran,Punjab",
            "Mahe,Puducherry", "Yanam,Puducherry", "Karaikal,Puducherry", "Pondicherry,Puducherry",
            "Byasanagar,Odisha", "Rayagada,Odisha", "Kendujhar,Odisha", "Barbil,Odisha",
            "Wokha,Nagaland", "Kohima,Nagaland", "Saiha,Mizoram", "Lunglei,Mizoram",
            "Adoor,Kerala", "Attingal,Kerala", "Varkala,Kerala", "Cherthala,Kerala",
            "Korba,Chhattisgarh", "Dhamtari,Chhattisgarh", "Chirmiri,Chhattisgarh", "Tilda Newra,Chhattisgarh",
            "Bapatla,Andhra Pradesh", "Jorhat,Assam", "Munger,Bihar", "Bilaspur,Chhattisgarh", "Delhi,Delhi",
            "Vadodara,Gujarat", "Hisar,Haryana", "Solan,Himachal Pradesh", "Ramgarh,Jharkhand", "Kolar,Karnataka",
            "Kollam,Kerala", "Indore,Madhya Pradesh", "Nashik,Maharashtra", "Imphal,Manipur", "Aizawl,Mizoram",
            "Zunheboto,Nagaland", "Cuttack,Odisha", "Hardwar,Uttarakhand", "Asansol,West Bengal", "Tandur,Telangana"
    };

    public static String[] Ips = {
            "218.100.41.***", "210.212.255.***", "203.212.192.***", "203.197.149.***", "202.159.0.***",
            "202.146.32.***", "202.93.16.***", "202.78.164.***", "195.112.167.***", "123.108.224.",
            "121.50.32.***", "116.254.112.***", "61.247.224.***", "60.254.0.***", "58.146.96.***",
            "58.65.240.***", "202.134.130.***", "203.128.64.***", "210.210.0.***", "210.212.216.***",
            "58.65.240.***", "59.144.0.***", "60.243.0.***", "61.11.121.***", "61.17.0.***",
            "116.12.40.***", "117.20.48.***", "117.96.0.***", "121.240.0.***", "123.231.128.***",
            "124.30.0.***", "124.195.0.***", "202.0.81.***", "202.80.208.***", "203.55.173.***",
            "210.7.64.***", "218.100.4.***", "219.90.96.***", "220.157.96.***", "221.134.238.***",
            "14.99.255.***", "27.0.63.***", "36.37.127.***", "39.255.255.***", "42.62.179.***",
            "49.0.3.***", "58.2.255.***", "59.145.255.***", "60.243.255.***", "61.5.80.***",
            "91.242.215.***", "101.0.63.***", "103.1.83.***", "103.7.1.***", "106.79.255.***",
            "110.5.79.***", "111.67.95.***", "111.118.215.***", "114.31.255.***", "117.103.127.***"
    };

    public String[] getLuckyNames() {
        return this.LuckyNames;
    }

    public String[] getGoodsNames() {
        return this.GoodsNames;
    }

    public static List<GoodsBean> getShowGoodsList(Context context) {
        List<GoodsBean> mNoEndGoods = DbUtils.queryDbNoEndGoods(context);
        List<GoodsBean> mGoodsBeans = getGoodsList(mNoEndGoods);
        return mGoodsBeans;
    }


    public static List<GoodsBean> getGoodsList(List<GoodsBean> goodsBeans) {
        List<GoodsBean> mAllGoodsList = getAllGoodsList();
        while (goodsBeans.size() < 10) {
            int i = RandomUtils.random(19);
            GoodsBean goodsBean = mAllGoodsList.get(i);
            if (!goodsBeans.contains(goodsBean)) {
                goodsBeans.add(goodsBean);
            }
            ListUtils.removeRepeat(goodsBeans);
        }
        return goodsBeans;
    }

    public static List<GoodsBean> getGoodsList() {
        List<GoodsBean> mAllGoodsList = getAllGoodsList();
        List<GoodsBean> mGoodsBeans = new ArrayList<GoodsBean>();
        while (mGoodsBeans.size() < 10) {
            int i = RandomUtils.random(30);
            GoodsBean goodsBean = mAllGoodsList.get(i);
            if (!mGoodsBeans.contains(goodsBean)) {
                mGoodsBeans.add(goodsBean);
            }
        }
        return mGoodsBeans;
    }

    public static List<GoodsBean> getAllGoodsList() {
        List<GoodsBean> mGoodsList = new ArrayList<GoodsBean>();
        mGoodsList.add(new GoodsBean(GoodsIds[0], GoodsNames[0], GoodsIcons[0], GoodsPrices[0], GoodsCredits[0]));
        mGoodsList.add(new GoodsBean(GoodsIds[1], GoodsNames[1], GoodsIcons[1], GoodsPrices[1], GoodsCredits[1]));
        mGoodsList.add(new GoodsBean(GoodsIds[2], GoodsNames[2], GoodsIcons[2], GoodsPrices[2], GoodsCredits[2]));
        mGoodsList.add(new GoodsBean(GoodsIds[3], GoodsNames[3], GoodsIcons[3], GoodsPrices[3], GoodsCredits[3]));
        mGoodsList.add(new GoodsBean(GoodsIds[4], GoodsNames[4], GoodsIcons[4], GoodsPrices[4], GoodsCredits[4]));
        mGoodsList.add(new GoodsBean(GoodsIds[5], GoodsNames[5], GoodsIcons[5], GoodsPrices[5], GoodsCredits[5]));
        mGoodsList.add(new GoodsBean(GoodsIds[6], GoodsNames[6], GoodsIcons[6], GoodsPrices[6], GoodsCredits[6]));
        mGoodsList.add(new GoodsBean(GoodsIds[7], GoodsNames[7], GoodsIcons[7], GoodsPrices[7], GoodsCredits[7]));
        mGoodsList.add(new GoodsBean(GoodsIds[8], GoodsNames[8], GoodsIcons[8], GoodsPrices[8], GoodsCredits[8]));
        mGoodsList.add(new GoodsBean(GoodsIds[9], GoodsNames[9], GoodsIcons[9], GoodsPrices[9], GoodsCredits[9]));

        mGoodsList.add(new GoodsBean(GoodsIds[10], GoodsNames[10], GoodsIcons[10], GoodsPrices[10], GoodsCredits[10]));
        mGoodsList.add(new GoodsBean(GoodsIds[11], GoodsNames[11], GoodsIcons[11], GoodsPrices[11], GoodsCredits[11]));
        mGoodsList.add(new GoodsBean(GoodsIds[12], GoodsNames[12], GoodsIcons[12], GoodsPrices[12], GoodsCredits[12]));
        mGoodsList.add(new GoodsBean(GoodsIds[13], GoodsNames[13], GoodsIcons[13], GoodsPrices[13], GoodsCredits[13]));
        mGoodsList.add(new GoodsBean(GoodsIds[14], GoodsNames[14], GoodsIcons[14], GoodsPrices[14], GoodsCredits[14]));
        mGoodsList.add(new GoodsBean(GoodsIds[15], GoodsNames[15], GoodsIcons[15], GoodsPrices[15], GoodsCredits[15]));
        mGoodsList.add(new GoodsBean(GoodsIds[16], GoodsNames[16], GoodsIcons[16], GoodsPrices[16], GoodsCredits[16]));
        mGoodsList.add(new GoodsBean(GoodsIds[17], GoodsNames[17], GoodsIcons[17], GoodsPrices[17], GoodsCredits[17]));
        mGoodsList.add(new GoodsBean(GoodsIds[18], GoodsNames[18], GoodsIcons[18], GoodsPrices[18], GoodsCredits[18]));
        mGoodsList.add(new GoodsBean(GoodsIds[19], GoodsNames[19], GoodsIcons[19], GoodsPrices[19], GoodsCredits[19]));

        mGoodsList.add(new GoodsBean(GoodsIds[20], GoodsNames[20], GoodsIcons[20], GoodsPrices[20], GoodsCredits[20]));
        mGoodsList.add(new GoodsBean(GoodsIds[21], GoodsNames[21], GoodsIcons[21], GoodsPrices[21], GoodsCredits[21]));
        mGoodsList.add(new GoodsBean(GoodsIds[22], GoodsNames[22], GoodsIcons[22], GoodsPrices[22], GoodsCredits[22]));
        mGoodsList.add(new GoodsBean(GoodsIds[23], GoodsNames[23], GoodsIcons[23], GoodsPrices[23], GoodsCredits[23]));
        mGoodsList.add(new GoodsBean(GoodsIds[24], GoodsNames[24], GoodsIcons[24], GoodsPrices[24], GoodsCredits[24]));
        mGoodsList.add(new GoodsBean(GoodsIds[25], GoodsNames[25], GoodsIcons[25], GoodsPrices[25], GoodsCredits[25]));
        mGoodsList.add(new GoodsBean(GoodsIds[26], GoodsNames[26], GoodsIcons[26], GoodsPrices[26], GoodsCredits[26]));
        mGoodsList.add(new GoodsBean(GoodsIds[27], GoodsNames[27], GoodsIcons[27], GoodsPrices[27], GoodsCredits[27]));
        mGoodsList.add(new GoodsBean(GoodsIds[28], GoodsNames[28], GoodsIcons[28], GoodsPrices[28], GoodsCredits[28]));
        mGoodsList.add(new GoodsBean(GoodsIds[29], GoodsNames[29], GoodsIcons[29], GoodsPrices[29], GoodsCredits[29]));
        return mGoodsList;
    }

    public static List<Integer> getHomeFlashsRes() {
        List<Integer> mAllHomeFlash = getAllHomeFlashsRes();
        List<Integer> mHomeFlash = new ArrayList<Integer>();
        while (mHomeFlash.size() < 4) {
            int i = RandomUtils.random(4);
            int resid = mAllHomeFlash.get(i);
            if (!mHomeFlash.contains(resid)) {
                mHomeFlash.add(resid);
            }
        }
        return mHomeFlash;
    }

    public static List<Integer> getAllHomeFlashsRes() {
        List<Integer> mAllHomeFlash = new ArrayList<Integer>();
        mAllHomeFlash.add(Flashs[0]);
        mAllHomeFlash.add(Flashs[1]);
        mAllHomeFlash.add(Flashs[2]);
        mAllHomeFlash.add(Flashs[3]);
        return mAllHomeFlash;
    }

    public static List<WinnerBean> getAllWinnerList() {
        List<WinnerBean> winnerBeans = new ArrayList<WinnerBean>();
        winnerBeans.add(new WinnerBean(Atavars[0], Citys[0], LuckyNames[0], Ips[0], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[1], Citys[1], LuckyNames[1], Ips[1], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[2], Citys[2], LuckyNames[2], Ips[2], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[3], Citys[3], LuckyNames[3], Ips[3], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[4], Citys[4], LuckyNames[4], Ips[4], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[5], Citys[5], LuckyNames[5], Ips[5], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[6], Citys[6], LuckyNames[6], Ips[6], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[7], Citys[7], LuckyNames[7], Ips[7], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[8], Citys[8], LuckyNames[8], Ips[8], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[9], Citys[9], LuckyNames[9], Ips[9], RandomUtils.getCommentTime()));

        winnerBeans.add(new WinnerBean(Atavars[10], Citys[10], LuckyNames[10], Ips[10], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[11], Citys[11], LuckyNames[11], Ips[11], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[12], Citys[12], LuckyNames[12], Ips[12], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[13], Citys[13], LuckyNames[13], Ips[13], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[14], Citys[14], LuckyNames[14], Ips[14], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[15], Citys[15], LuckyNames[15], Ips[15], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[16], Citys[16], LuckyNames[16], Ips[16], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[17], Citys[17], LuckyNames[17], Ips[17], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[18], Citys[18], LuckyNames[18], Ips[18], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[19], Citys[19], LuckyNames[19], Ips[19], RandomUtils.getCommentTime()));

        winnerBeans.add(new WinnerBean(Atavars[20], Citys[20], LuckyNames[20], Ips[20], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[21], Citys[21], LuckyNames[21], Ips[21], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[22], Citys[22], LuckyNames[22], Ips[22], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[23], Citys[23], LuckyNames[23], Ips[23], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[24], Citys[24], LuckyNames[24], Ips[24], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[25], Citys[25], LuckyNames[25], Ips[25], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[26], Citys[26], LuckyNames[26], Ips[26], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[27], Citys[27], LuckyNames[27], Ips[27], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[28], Citys[28], LuckyNames[28], Ips[28], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[29], Citys[29], LuckyNames[29], Ips[29], RandomUtils.getCommentTime()));

        winnerBeans.add(new WinnerBean(Atavars[30], Citys[30], LuckyNames[30], Ips[30], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[31], Citys[31], LuckyNames[31], Ips[31], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[32], Citys[32], LuckyNames[32], Ips[32], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[33], Citys[33], LuckyNames[33], Ips[33], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[34], Citys[34], LuckyNames[34], Ips[34], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[35], Citys[35], LuckyNames[35], Ips[35], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[36], Citys[36], LuckyNames[36], Ips[36], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[37], Citys[37], LuckyNames[37], Ips[37], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[38], Citys[38], LuckyNames[38], Ips[38], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[39], Citys[39], LuckyNames[39], Ips[39], RandomUtils.getCommentTime()));

        winnerBeans.add(new WinnerBean(Atavars[40], Citys[40], LuckyNames[40], Ips[40], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[41], Citys[41], LuckyNames[41], Ips[41], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[42], Citys[42], LuckyNames[42], Ips[42], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[43], Citys[43], LuckyNames[43], Ips[43], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[44], Citys[44], LuckyNames[44], Ips[44], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[45], Citys[45], LuckyNames[45], Ips[45], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[46], Citys[46], LuckyNames[46], Ips[46], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[47], Citys[47], LuckyNames[47], Ips[47], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[48], Citys[48], LuckyNames[48], Ips[48], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[49], Citys[49], LuckyNames[49], Ips[49], RandomUtils.getCommentTime()));

        winnerBeans.add(new WinnerBean(Atavars[50], Citys[50], LuckyNames[50], Ips[50], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[51], Citys[51], LuckyNames[51], Ips[51], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[52], Citys[52], LuckyNames[52], Ips[52], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[53], Citys[53], LuckyNames[53], Ips[53], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[54], Citys[54], LuckyNames[54], Ips[54], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[55], Citys[55], LuckyNames[55], Ips[55], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[56], Citys[56], LuckyNames[56], Ips[56], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[57], Citys[57], LuckyNames[57], Ips[57], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[58], Citys[58], LuckyNames[58], Ips[58], RandomUtils.getCommentTime()));
        winnerBeans.add(new WinnerBean(Atavars[59], Citys[59], LuckyNames[59], Ips[59], RandomUtils.getCommentTime()));
        return winnerBeans;
    }

    public static List<WinnerBean> getWinnerList() {
        List<WinnerBean> winnerBeans = new ArrayList<WinnerBean>();
        List<WinnerBean> mAllWinnerList = getAllWinnerList();
        while (winnerBeans.size() < 5) {
            int i = RandomUtils.random(60);
            WinnerBean winnerBean = mAllWinnerList.get(i);
            if (!winnerBeans.contains(winnerBean)) {
                winnerBeans.add(winnerBean);
            }
        }
        ComparatorWinner comparator = new ComparatorWinner();
        Collections.sort(winnerBeans, comparator);
        return winnerBeans;
    }

}
