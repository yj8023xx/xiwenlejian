package com.smallc.xiwenlejian.book.constant;

import com.smallc.xiwenlejian.common.recommender.constant.RecModelType;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/13
 * @since com.smallc.xiwenlejian.book.constant
 */
public class Constant {

    /**
     * 一次推荐的数量
     */
    public static Integer REC_SIZE = 12;

    /**
     * 推荐模型
     */
    public static RecModelType REC_MODEL = RecModelType.NCF;

    /**
     * 相似物品的数量
     */
    public static Integer SIMILAR_SIZE = 12;

    /**
     * 相似度计算模型
     */
    public static RecModelType SIMILAR_MODEL = RecModelType.Embedding;

}
