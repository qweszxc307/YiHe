/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.crown.framework.enums.IEnum;

/**
 * <p>
 * 状态枚举
 * </p>
 *
 * @author whZhang
 */
public enum ImagesEnum implements IEnum {

    /**
     * 轮播图
     */
    CAROUSEL_IMAGE(0),
    /**
     * 品牌图片
     */
    BRAND_IMAGE(1),
    /**
     * 产品图片
     */
    PRODUCT_IMAGE(2),
    /**
     * 产品详情图片
     */
    PRODUCT_DETAIL_IMAGE(3) ,
    /**
     * 会员特权图片
     */
    MEMBER_PRIVILEGE_IMAGE(4);



    @EnumValue
    private final int value;

    ImagesEnum(final int value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }
}
