package com.wnw.lovebaby.config;

/**
 * Created by wnw on 2017/4/9.
 */

public class NetConfig {
    public final static String SERVICE = "http://119.29.182.235:8080/babyTest/";

    //Order
    public final static String INSERT_ORDER = "insertOrder?";
    public final static String UPDATE_ORDER = "updateOrder?";
    public final static String FIND_ORDER_BY_ID = "findOrderById?";
    public final static String FIND_ORDER_BY_USER_ID = "findOrderByUserId?";
    public final static String FIND_ORDER_BY_SHOP_ID = "findOrderByShopId?";

    //shop
    public final static String INSERT_SHOP = "insertShop?";
    public final static String UPDATE_SHOP = "updateShop?";
    public final static String FIND_SHOP_BY_ID = "findShopById?";
    public final static String FIND_SHOP_BY_USER_ID = "findShopByUserId?";
    public final static String FIND_SHOP_BY_INVITEE = "findShopByInvitee?";

    //deal
    public final static String INSERT_DEAL = "insertDeal?";
    public final static String UPDATE_DEAL = "updateDeal?";
    public final static String FIND_DEAL_BY_ID = "findDealById?";
    public final static String FIND_DEAL_BY_ORDER_ID = "findDealByOrderId?";

    //user wallet
    public final static String UPDATE_WALLET_PASSWORD = "updateWalletPassword?";
    public final static String SUB_WALLET_MONEY = "subWalletMoney?";
    public final static String VALITE_WALLET = "valiteWallet?";
    public final static String FIND_WALLET_MONEY_BY_USERID = "findWalletMoneyByUserId?";

    //address
    public final static String FIND_RECE_ADDRESS_BY_ID = "findReceAddress?";

    //product
    public final static String FIND_PRODUCT_BY_ID = "findProductById?";
    public final static String FIND_PRODUCT_BY_SC_ID = "findProductByScId?";

    //pr
    public final static String FIND_PR_BY_DEAL_ID = "findPrsByDealId?";
}
