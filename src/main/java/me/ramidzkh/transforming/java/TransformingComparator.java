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

public class TransformingComparator<A, T> implements Comparator<T> {

    private final Comparator<? super A> comparator;
    private final Mapper<A, T> mapper;

    public TransformingComparator(Comparator<? super A> comparator, Mapper<A, T> mapper) {
        this.comparator = comparator;
        this.mapper = mapper;
    }

    @Override
    public int compare(T o1, T o2) {
        return comparator.compare(mapper.unmap(o1), mapper.unmap(o2));
    }
}
