package com.back.backend.utils;

import com.back.backend.exceptions.OptionalNotFoundException;

import java.util.Optional;
public class OptionalWorker {
    public static void checkOptional(Optional optional) throws OptionalNotFoundException {
        if (optional.isEmpty()) {
            throw new OptionalNotFoundException("Объект не найден");
        }
    }
}
