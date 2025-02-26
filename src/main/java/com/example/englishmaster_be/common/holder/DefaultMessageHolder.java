package com.example.englishmaster_be.common.holder;

public class DefaultMessageHolder {

    public static final ThreadLocal<String> messageHolder = ThreadLocal.withInitial(() -> null);

    public static void setMessage(String message) {
        messageHolder.set(message);
    }

    public static String getMessage() {
        return messageHolder.get();
    }

    public static void clear() {
        messageHolder.remove();
    }

}
