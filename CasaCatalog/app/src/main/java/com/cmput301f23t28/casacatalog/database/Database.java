package com.cmput301f23t28.casacatalog.database;

/**
 * Provides a singleton structure for accessing the TagDatabase within the application.
 * This class should be initialized at the start of the application to set up the database instance.
 */
public final class Database {

    public static TagDatabase tags;
    public static UserDatabase users;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Database(){}

    /**
     * Initializes static instances of different database classes.
     * This method should be called at the beginning of the application lifecycle to ensure the databases are set up.
     */
    public static void initialize(){
        users = new UserDatabase();
        tags = new TagDatabase();
    }
}
