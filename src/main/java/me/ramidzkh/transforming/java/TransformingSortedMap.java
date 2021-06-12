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

import java.util.Comparator;
import java.util.SortedMap;

public class TransformingSortedMap<A, B, K, V> extends TransformingMap<A, B, K, V> implements SortedMap<K, V> {

    private final SortedMap<A, B> map;

    public TransformingSortedMap(SortedMap<A, B> map, Mapper<A, K> keyMapper, Mapper<B, V> valueMapper) {
        super(map, keyMapper, valueMapper);
        this.map = map;
    }

    @Override
    public Comparator<? super K> comparator() {
        return new TransformingComparator<>(map.comparator(), keyMapper);
    }

    @NotNull
    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return new TransformingSortedMap<>(map.subMap(keyMapper.unmap(fromKey), keyMapper.unmap(toKey)), keyMapper, valueMapper);
    }

    @NotNull
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return new TransformingSortedMap<>(map.headMap(keyMapper.unmap(toKey)), keyMapper, valueMapper);
    }

    @NotNull
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return new TransformingSortedMap<>(map.tailMap(keyMapper.unmap(fromKey)), keyMapper, valueMapper);
    }

    @Override
    public K firstKey() {
        return keyMapper.map(map.firstKey());
    }

    @Override
    public K lastKey() {
        return keyMapper.map(map.lastKey());
    }
}
