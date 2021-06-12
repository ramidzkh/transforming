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

import java.util.Iterator;
import java.util.NavigableSet;

public class TransformingNavigableSet<A, E> extends TransformingSortedSet<A, E> implements NavigableSet<E> {

    private final NavigableSet<A> set;

    public TransformingNavigableSet(NavigableSet<A> set, Mapper<A, E> mapper) {
        super(set, mapper);
        this.set = set;
    }

    @Nullable
    @Override
    public E lower(E e) {
        return mapper.map(set.lower(mapper.unmap(e)));
    }

    @Nullable
    @Override
    public E floor(E e) {
        return mapper.map(set.floor(mapper.unmap(e)));
    }

    @Nullable
    @Override
    public E ceiling(E e) {
        return mapper.map(set.ceiling(mapper.unmap(e)));
    }

    @Nullable
    @Override
    public E higher(E e) {
        return mapper.map(set.higher(mapper.unmap(e)));
    }

    @Nullable
    @Override
    public E pollFirst() {
        return mapper.map(set.pollFirst());
    }

    @Nullable
    @Override
    public E pollLast() {
        return mapper.map(set.pollLast());
    }

    @NotNull
    @Override
    public NavigableSet<E> descendingSet() {
        return new TransformingNavigableSet<>(set.descendingSet(), mapper);
    }

    @NotNull
    @Override
    public Iterator<E> descendingIterator() {
        return new TransformingIterator<>(set.descendingIterator(), mapper);
    }

    @NotNull
    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return new TransformingNavigableSet<>(set.subSet(mapper.unmap(fromElement), fromInclusive, mapper.unmap(toElement), toInclusive), mapper);
    }

    @NotNull
    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return new TransformingNavigableSet<>(set.headSet(mapper.unmap(toElement), inclusive), mapper);
    }

    @NotNull
    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return new TransformingNavigableSet<>(set.tailSet(mapper.unmap(fromElement), inclusive), mapper);
    }
}
