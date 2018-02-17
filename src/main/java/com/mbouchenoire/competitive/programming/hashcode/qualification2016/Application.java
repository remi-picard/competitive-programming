package com.mbouchenoire.competitive.programming.hashcode.qualification2016;

import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeApplication;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodePhase;
import com.mbouchenoire.competitive.programming.hashcode.common.HashCodeSession;
import com.mbouchenoire.competitive.programming.hashcode.qualification2016.impl.Algorithm;
import com.mbouchenoire.competitive.programming.hashcode.qualification2016.impl.InputValueParser;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        final HashCodeSession session = new HashCodeSession(2016, HashCodePhase.QUALIFICATION);
        final HashCodeApplication hashCodeApplication = new HashCodeApplication(session);
        hashCodeApplication.run(new Algorithm(), new InputValueParser());
    }
}
