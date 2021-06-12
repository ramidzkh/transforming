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

import java.util.Map;

public class TransformingEntry<A, B, K, V> implements Map.Entry<K, V> {

    private final Map.Entry<A, B> entry;
    private final Mapper<A, K> keyMapper;
    private final Mapper<B, V> valueMapper;

    public TransformingEntry(Map.Entry<A, B> entry, Mapper<A, K> keyMapper, Mapper<B, V> valueMapper) {
        this.entry = entry;
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public K getKey() {
        return keyMapper.map(entry.getKey());
    }

    @Override
    public V getValue() {
        return valueMapper.map(entry.getValue());
    }

    @Override
    public V setValue(V value) {
        return valueMapper.map(entry.setValue(valueMapper.unmap(value)));
    }
}
