package com.example.democrudjwt.util;

import com.example.democrudjwt.exception.IllegalRequestDataException;
import com.example.democrudjwt.model.Users;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(Users entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static <T> T checkNotFoundWithId(T object, long id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, long id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new IllegalRequestDataException("Not found entity with " + msg);
        }
    }
}
