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

package me.ramidzkh.transforming.guava;

import com.google.common.collect.BiMap;
import me.ramidzkh.transforming.java.TransformingSet;
import me.ramidzkh.transforming.map.EntryMapper;
import me.ramidzkh.transforming.map.Mapper;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class TransformingBiMap<A, B, K, V> implements BiMap<K, V> {

    private final BiMap<A, B> map;
    protected final Mapper<A, K> keyMapper;
    protected final Mapper<B, V> valueMapper;

    public TransformingBiMap(BiMap<A, B> map, Mapper<A, K> keyMapper, Mapper<B, V> valueMapper) {
        this.map = map;
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(keyMapper.unmap((K) key));
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(valueMapper.unmap((V) value));
    }

    @Override
    public V get(Object key) {
        return valueMapper.map(map.get(keyMapper.unmap((K) key)));
    }

    @Override
    public @Nullable V put(@Nullable K key, @Nullable V value) {
        return valueMapper.map(map.put(keyMapper.unmap(key), valueMapper.unmap(value)));
    }

    @Override
    public V remove(Object key) {
        return valueMapper.map(map.remove(keyMapper.unmap((K) key)));
    }

    @Override
    public @Nullable V forcePut(@Nullable K key, @Nullable V value) {
        return valueMapper.map(map.forcePut(keyMapper.unmap(key), valueMapper.unmap(value)));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.map.put(keyMapper.unmap(entry.getKey()), valueMapper.unmap(entry.getValue()));
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return new TransformingSet<>(map.keySet(), keyMapper);
    }

    @Override
    public Set<V> values() {
        return new TransformingSet<>(map.values(), valueMapper);
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new TransformingSet<>(map.entrySet(), new EntryMapper<>(keyMapper, valueMapper));
    }

    @Override
    public BiMap<V, K> inverse() {
        return new TransformingBiMap<>(map.inverse(), valueMapper, keyMapper);
    }
}
