package com.cmput301f23t28.casacatalog.database;

public final class Database {
    public static TagDatabase tags;

    // Utility class
    private Database(){}

    public static void initialize(){
        tags = new TagDatabase();
    }
}
