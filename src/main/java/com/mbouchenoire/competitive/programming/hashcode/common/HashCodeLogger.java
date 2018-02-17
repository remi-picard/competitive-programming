package com.mbouchenoire.competitive.programming.hashcode.common;

import org.apache.commons.lang3.time.StopWatch;

public class HashCodeLogger {

    private final HashCodeInput input;
    private final StopWatch stopWatch;

    public HashCodeLogger(HashCodeInput input) {
        this.input = input;
        this.stopWatch = new StopWatch();
        this.stopWatch.start();
    }

    public void info(Object o) {
        info(o.toString(), "");
    }

    public void info(CharSequence cs, Object... values) {
        final String prefix = input.toString() + " - " + ((double)stopWatch.getTime() / (double)1000) + "s: ";
        System.out.printf(prefix + cs, values);
        System.out.println();
    }
}
