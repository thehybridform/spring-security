package com.kristinyoung.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Iterators {

    private Iterators() { }

    public static  <T> List<T> safe(final T[] array) {
        if (array == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(array);
    }
}
