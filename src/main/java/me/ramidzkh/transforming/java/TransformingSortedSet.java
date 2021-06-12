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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.SortedSet;

public class TransformingSortedSet<A, E> extends TransformingSet<A, E> implements SortedSet<E> {

    private final SortedSet<A> set;

    public TransformingSortedSet(SortedSet<A> set, Mapper<A, E> mapper) {
        super(set, mapper);
        this.set = set;
    }

    @Nullable
    @Override
    public Comparator<? super E> comparator() {
        return new TransformingComparator<>(set.comparator(), mapper);
    }

    @NotNull
    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return new TransformingSortedSet<>(set.subSet(mapper.unmap(fromElement), mapper.unmap(toElement)), mapper);
    }

    @NotNull
    @Override
    public SortedSet<E> headSet(E toElement) {
        return new TransformingSortedSet<>(set.headSet(mapper.unmap(toElement)), mapper);
    }

    @NotNull
    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return new TransformingSortedSet<>(set.tailSet(mapper.unmap(fromElement)), mapper);
    }

    @Override
    public E first() {
        return mapper.map(set.first());
    }

    @Override
    public E last() {
        return mapper.map(set.last());
    }
}
