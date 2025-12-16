package main;

import utils.FileManager;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Object o = new FileManager();
        Class<?> cls = o.getClass();
        System.out.println(Arrays.asList(cls.getAnnotations()));
    }
}
