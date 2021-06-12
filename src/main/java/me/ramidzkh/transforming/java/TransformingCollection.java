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

import java.util.*;

public class TransformingCollection<A, E> implements Collection<E> {

    private final Collection<A> collection;
    protected final Mapper<A, E> mapper;

    public TransformingCollection(Collection<A> collection, Mapper<A, E> mapper) {
        this.collection = collection;
        this.mapper = mapper;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new TransformingIterator<>(collection.iterator(), mapper);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return new ArrayList<>(this).toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return new ArrayList<>(this).toArray(a);
    }

    public boolean add(E a) {
        return collection.add(mapper.unmap(a));
    }

    @Override
    public boolean remove(Object o) {
        return collection.remove(mapper.unmap((E) o));
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }

        return true;
    }

    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean modified = false;

        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        Objects.requireNonNull(c);

        boolean modified = false;
        Iterator<E> it = iterator();

        while (it.hasNext()) {
            if (c.contains(mapper.unmap(it.next()))) {
                it.remove();
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();

        while (it.hasNext()) {
            if (!c.contains(mapper.unmap(it.next()))) {
                it.remove();
                modified = true;
            }
        }

        return modified;
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection, mapper);
    }

    @Override
    public Spliterator<E> spliterator() {
        return new TransformingSpliterator<>(collection.spliterator(), mapper);
    }
}
