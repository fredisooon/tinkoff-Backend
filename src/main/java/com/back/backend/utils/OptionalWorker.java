package com.back.backend.utils;

import com.back.backend.exceptions.OptionalNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OptionalWorker {
    public static void checkOptional(Optional optional) throws OptionalNotFoundException {
        if (optional.isEmpty()) {
            throw new OptionalNotFoundException("Объект не найден");
        }
    }
}
