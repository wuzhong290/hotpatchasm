package com.hotpatch.asm.util.matcher;

import com.hotpatch.asm.util.GaCheckUtils;

/**
 * 相等比对
 * Created by oldmanpushcart@gmail.com on 15/12/12.
 */
public class EqualsMatcher<T> implements Matcher<T> {

    private final T source;

    public EqualsMatcher(T source) {
        this.source = source;
    }

    @Override
    public boolean matching(T target) {
        return GaCheckUtils.isEquals(source, target);
    }

}
