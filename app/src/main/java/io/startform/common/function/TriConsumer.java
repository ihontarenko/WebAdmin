package io.startform.common.function;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);

    default TriConsumer<A, B, C> before(TriConsumer<? super A, ? super B, ? super C> consumer) {
        return (a, b, c) -> {
            consumer.accept(a, b, c);
            this.accept(a, b, c);
        };
    }

    default TriConsumer<A, B, C> after(TriConsumer<? super A, ? super B, ? super C> consumer) {
        return (a, b, c) -> {
            this.accept(a, b, c);
            consumer.accept(a, b, c);
        };
    }
}