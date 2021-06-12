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

package me.ramidzkh.transforming.map;

import me.ramidzkh.transforming.java.TransformingEntry;

import java.util.Map;

public class EntryMapper<A, B, K, V> implements Mapper<Map.Entry<A, B>, Map.Entry<K, V>> {

    protected final Mapper<A, K> keyMapper;
    protected final Mapper<B, V> valueMapper;

    public EntryMapper(Mapper<A, K> keyMapper, Mapper<B, V> valueMapper) {
        this.keyMapper = keyMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public Map.Entry<A, B> unmap(Map.Entry<K, V> kvEntry) {
        return new TransformingEntry<>(kvEntry, keyMapper.swap(), valueMapper.swap());
    }

    @Override
    public Map.Entry<K, V> map(Map.Entry<A, B> abEntry) {
        return new TransformingEntry<>(abEntry, keyMapper, valueMapper);
    }
}
