package com.cmput301f23t28.casacatalog.database;

public final class Database {

    public static TagDatabase tags;
    public static UserDatabase users;

    // Utility class
    private Database(){}

    public static void initialize(){
        users = new UserDatabase();
        tags = new TagDatabase();
    }
}
