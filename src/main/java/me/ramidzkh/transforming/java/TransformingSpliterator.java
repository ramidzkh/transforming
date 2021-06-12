/*
 * Copyright 2021 Ramid Khan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ramidzkh.transforming.java;

import me.ramidzkh.transforming.map.Mapper;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TransformingSpliterator<A, T> implements Spliterator<T> {

    private final Spliterator<A> spliterator;
    private final Mapper<A, T> mapper;

    public TransformingSpliterator(Spliterator<A> spliterator, Mapper<A, T> mapper) {
        this.spliterator = spliterator;
        this.mapper = mapper;
    }

    public boolean tryAdvance(Consumer<? super T> action) {
        return spliterator.tryAdvance(a -> {
            action.accept(mapper.map(a));
        });
    }

    @Override
    public Spliterator<T> trySplit() {
        return new TransformingSpliterator<>(spliterator.trySplit(), mapper);
    }

    @Override
    public long estimateSize() {
        return spliterator.estimateSize();
    }

    @Override
    public long getExactSizeIfKnown() {
        return spliterator.getExactSizeIfKnown();
    }

    @Override
    public int characteristics() {
        return spliterator.characteristics();
    }

    @Override
    public Comparator<? super T> getComparator() {
        return new TransformingComparator<>(spliterator.getComparator(), mapper);
    }
}
