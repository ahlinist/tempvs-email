package club.tempvs.email.util;

public interface ObjectFactory {
    <T> T getInstance(Class<T> clazz, Object... args);
}
