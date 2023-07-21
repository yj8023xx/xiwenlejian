package com.smallc.xiwenlejian.recommender.online.contsant;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/11
 * @since com.smallc.xiwenlejian.recommender.online.contsant
 */
public class Constant {

    /**
     * 候选集的数量
     */
    public static Integer CANDIDATE_SIZE = 1000;

    /**
     * 多路召回候选集的数量
     */
    public static Integer MULTIPLE_CANDIDATE_SIZE = 500;

    /**
     * 是否使用用户Embedding
     */
    public static Boolean USE_USER_EMBEDDING = false;

    /**
     * 是否使用用户特征
     */
    public static Boolean USE_USER_FEATURES = false;

    /**
     * 是否使用物品Embedding
     */
    public static Boolean USE_ITEM_EMBEDDING = false;

    /**
     * 是否使用物品特征
     */
    public static Boolean USE_ITEM_FEATURES = false;

    /**
     * NCF模型地址
     */
    public static String NCF_MODEL_URL = "http://localhost:3000/predictions/ncf";

    public static String XDEEPFM_MODEL_URL = "http://localhost:3000/predictions/xdeepfm";

    public static String REDIS_USER_EMB_NAME = "user_embedding";

    /**
     * 用户特征数据库名
     */
    public static String REDIS_USER_FEATURES_NAME = "user_features";

    public static String REDIS_ITEM_EMB_NAME = "item_embedding";

    /**
     * 物品特征数据库名
     */
    public static String REDIS_ITEM_FEATURES_NAME = "item_features";

}
