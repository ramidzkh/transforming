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

import java.util.NavigableMap;
import java.util.NavigableSet;

public class TransformingNavigableMap<A, B, K, V> extends TransformingSortedMap<A, B, K, V> implements NavigableMap<K, V> {

    private final NavigableMap<A, B> map;

    public TransformingNavigableMap(NavigableMap<A, B> map, Mapper<A, K> keyMapper, Mapper<B, V> valueMapper) {
        super(map, keyMapper, valueMapper);
        this.map = map;
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        return new TransformingEntry<>(map.lowerEntry(keyMapper.unmap(key)), keyMapper, valueMapper);
    }

    @Override
    public K lowerKey(K key) {
        return keyMapper.map(map.lowerKey(keyMapper.unmap(key)));
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return new TransformingEntry<>(map.floorEntry(keyMapper.unmap(key)), keyMapper, valueMapper);
    }

    @Override
    public K floorKey(K key) {
        return keyMapper.map(map.floorKey(keyMapper.unmap(key)));
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return new TransformingEntry<>(map.ceilingEntry(keyMapper.unmap(key)), keyMapper, valueMapper);
    }

    @Override
    public K ceilingKey(K key) {
        return keyMapper.map(map.ceilingKey(keyMapper.unmap(key)));
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        return new TransformingEntry<>(map.higherEntry(keyMapper.unmap(key)), keyMapper, valueMapper);
    }

    @Override
    public K higherKey(K key) {
        return keyMapper.map(map.higherKey(keyMapper.unmap(key)));
    }

    @Override
    public Entry<K, V> firstEntry() {
        return new TransformingEntry<>(map.firstEntry(), keyMapper, valueMapper);
    }

    @Override
    public Entry<K, V> lastEntry() {
        return new TransformingEntry<>(map.lastEntry(), keyMapper, valueMapper);
    }

    @Override
    public Entry<K, V> pollFirstEntry() {
        return new TransformingEntry<>(map.pollFirstEntry(), keyMapper, valueMapper);
    }

    @Override
    public Entry<K, V> pollLastEntry() {
        return new TransformingEntry<>(map.pollLastEntry(), keyMapper, valueMapper);
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return new TransformingNavigableMap<>(map.descendingMap(), keyMapper, valueMapper);
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return new TransformingNavigableSet<>(map.navigableKeySet(), keyMapper);
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return new TransformingNavigableSet<>(map.descendingKeySet(), keyMapper);
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return new TransformingNavigableMap<>(map.subMap(keyMapper.unmap(fromKey), fromInclusive, keyMapper.unmap(toKey), toInclusive), keyMapper, valueMapper);
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return new TransformingNavigableMap<>(map.headMap(keyMapper.unmap(toKey), inclusive), keyMapper, valueMapper);
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return new TransformingNavigableMap<>(map.tailMap(keyMapper.unmap(fromKey), inclusive), keyMapper, valueMapper);
    }
}
