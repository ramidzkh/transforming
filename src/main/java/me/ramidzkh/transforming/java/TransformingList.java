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

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class TransformingList<A, E> extends TransformingCollection<A, E> implements List<E> {

    private final List<A> list;

    public TransformingList(List<A> list, Mapper<A, E> mapper) {
        super(list, mapper);
        this.list = list;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> c) {
        return list.addAll(index, new TransformingCollection<>((Collection<E>) c, mapper.swap()));
    }

    @Override
    public E get(int index) {
        return mapper.map(list.get(index));
    }

    @Override
    public E set(int index, E element) {
        return mapper.map(list.set(index, mapper.unmap(element)));
    }

    @Override
    public void add(int index, E element) {
        list.add(index, mapper.unmap(element));
    }

    @Override
    public E remove(int index) {
        return mapper.map(list.remove(index));
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(mapper.unmap((E) o));
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(mapper.unmap((E) o));
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator() {
        return new TransformingListIterator<>(list.listIterator(), mapper);
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return new TransformingListIterator<>(list.listIterator(index), mapper);
    }

    @NotNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new TransformingList<>(list.subList(fromIndex, toIndex), mapper);
    }
}
