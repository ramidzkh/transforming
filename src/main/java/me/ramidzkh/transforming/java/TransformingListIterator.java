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

import java.util.ListIterator;

public class TransformingListIterator<A, E> extends TransformingIterator<A, E> implements ListIterator<E> {

    private final ListIterator<A> iterator;

    public TransformingListIterator(ListIterator<A> iterator, Mapper<A, E> mapper) {
        super(iterator, mapper);
        this.iterator = iterator;
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public E previous() {
        return mapper.map(iterator.previous());
    }

    @Override
    public int nextIndex() {
        return iterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iterator.previousIndex();
    }

    @Override
    public void set(E e) {
        iterator.set(mapper.unmap(e));
    }

    @Override
    public void add(E e) {
        iterator.add(mapper.unmap(e));
    }
}
