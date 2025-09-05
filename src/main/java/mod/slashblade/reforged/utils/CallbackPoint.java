package mod.slashblade.reforged.utils;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author: til
 * @Description: 简单的回调点
 */
public class CallbackPoint<I> {
    @Nullable
    private List<I> listener;

    public synchronized void register(I i) {
        if (listener == null) {
            listener = new ArrayList<>();
        }
        listener.add(i);
    }

    public synchronized void unregister(I i) {
        if (listener == null) {
            return;
        }
        listener.remove(i);
    }

    public synchronized void call(Consumer<I> consumer) {
        if (listener == null) {
            return;
        }
        for(I i : listener) {
            consumer.accept(i);
        }
    }
}
