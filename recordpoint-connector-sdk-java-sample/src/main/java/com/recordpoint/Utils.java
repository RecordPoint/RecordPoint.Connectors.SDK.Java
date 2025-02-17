package com.recordpoint;

import java.util.function.Consumer;
import java.util.function.Function;

public class Utils {
    public static <T> Function<T, T> passthrough(Consumer<T> consumer) {
        return x -> {
            consumer.accept(x);
            return x;
        };
    }
}
