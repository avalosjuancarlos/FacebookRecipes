package edu.galileo.android.facebookrecipes.libs.base;

/**
 * Created by avalo.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
