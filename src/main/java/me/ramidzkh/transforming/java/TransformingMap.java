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
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TransformingMap<A, B, K, V> implements Map<K, V> {

    private final Map<A, B> map;
    protected final Mapper<A, K> keyMapper;
    protected final Mapper<B, V> valueMapper;

    public TransformingMap(Map<A, B> map, Mapper<A, K> keyMapper, Mapper<B, V> valueMapper) {
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
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return valueMapper.map(map.get(keyMapper.map((A) key)));
    }

    public V put(K key, V value) {
        return valueMapper.map(map.put(keyMapper.unmap(key), valueMapper.unmap(value)));
    }

    @Override
    public V remove(Object key) {
        return valueMapper.map(map.remove(keyMapper.unmap((K) key)));
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            map.put(keyMapper.unmap(e.getKey()), valueMapper.unmap(e.getValue()));
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

    @NotNull
    @Override
    public Collection<V> values() {
        return new TransformingCollection<>(map.values(), valueMapper);
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new TransformingSet<>(map.entrySet(), new Mapper<Entry<A, B>, Entry<K, V>>() {
            @Override
            public Entry<A, B> unmap(Entry<K, V> kvEntry) {
                return new TransformingEntry<>(kvEntry, keyMapper.swap(), valueMapper.swap());
            }

            @Override
            public Entry<K, V> map(Entry<A, B> abEntry) {
                return new TransformingEntry<>(abEntry, keyMapper, valueMapper);
            }
        });
    }

    @Override
    public int hashCode() {
        return Objects.hash(map, keyMapper, valueMapper);
    }
}
