package com.mbouchenoire.competitive.programming.hashcode.qualification2015.model;

import java.util.Collections;
import java.util.List;

public class InputValue {

    public final List<Row> rows;
    public final List<Pool> pools;
    public final List<Server> servers;

    public InputValue(List<Row> rows, List<Pool> pools, List<Server> servers) {
        this.rows = Collections.unmodifiableList(rows);
        this.pools = Collections.unmodifiableList(pools);
        this.servers = Collections.unmodifiableList(servers);
    }
}
