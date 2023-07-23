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
    public static Integer CANDIDATE_SIZE = 3000;

    /**
     * NCF模型地址
     */
    public static String NCF_MODEL_URL = "http://localhost:3000/predictions/ncf";

    public static String XDEEPFM_MODEL_URL = "http://localhost:3000/predictions/xdeepfm";

}
