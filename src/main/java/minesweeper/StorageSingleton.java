package minesweeper;

/**
 * Singleton class for miscellaneous storage
 * Used for transferring state between UI scenes without variable passing
 */
public class StorageSingleton {
    // The instance is stored in a static, private variable
    // This allows us to instantiate the singleton with getInstance()
    private static StorageSingleton singleton = new StorageSingleton();

    // Animation speed
    public double animationSpeed = 1050.0;

    /**
     * Private constructor for creating one instance of the singleton
     */
    private StorageSingleton() {

    }

    /**
     * Method for getting access to the singleton instance
     *
     * @return Singleton instance
     */
    public static StorageSingleton getInstance() {
        return singleton;
    }
}
