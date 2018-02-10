package com.mbouchenoire.competitive.programming.common.model.math;

import java.util.Collection;
import java.util.function.ToLongFunction;

public class Math2 {

    private static <T> long median(Collection<T> values, ToLongFunction<T> toLongFunction) {
        final long[] intValues = values.stream().mapToLong(toLongFunction).toArray();
        return median(intValues);
    }

    private static long median(long[] values) {
        final long halfValue = values[values.length / 2];

        if (values.length % 2 == 0) {
            return (halfValue + values[(values.length / 2) - 1]) / 2;
        } else {
            return halfValue;
        }
    }
}
