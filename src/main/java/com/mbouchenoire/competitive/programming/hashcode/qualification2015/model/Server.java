package com.mbouchenoire.competitive.programming.hashcode.qualification2015.model;

import java.util.Objects;

public class Server {

    private final int id;
    private final int size;
    private final int capacity;

    public Server(int id, int size, int capacity) {
        this.id = id;
        this.size = size;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getValue() {
        return (double)getCapacity() / (double)getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return id == server.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", size=" + size +
                ", capacity=" + capacity +
                ", value=" + getValue() +
                '}';
    }
}
