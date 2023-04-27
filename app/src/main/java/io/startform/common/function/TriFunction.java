package io.startform.common.function;

import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<Z, A, B, C> {
    Z apply(A a, B b, C c);

    default <X> TriFunction<X, A, B, C> after(Function<? super Z, ? extends X> after) {
        return (a, b, c) -> {
            return after.apply(this.apply(a, b, c));
        };
    }
}
