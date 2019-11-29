package minesweeper;

public class StorageSingleton {
    private static StorageSingleton singleton = new StorageSingleton();

    public double animationSpeed = 1050.0;

    private StorageSingleton() {

    }

    public static StorageSingleton getInstance() {
        return singleton;
    }
}
