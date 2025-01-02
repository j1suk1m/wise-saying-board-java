package controller;

public enum Status {
    OLD(0),
    NEW(1);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}