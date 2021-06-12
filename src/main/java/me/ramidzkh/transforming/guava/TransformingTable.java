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

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import me.ramidzkh.transforming.java.TransformingCollection;
import me.ramidzkh.transforming.java.TransformingMap;
import me.ramidzkh.transforming.java.TransformingSet;
import me.ramidzkh.transforming.map.Mapper;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TransformingTable<A, B, N, R, C, V> implements Table<R, C, V> {

    private final Table<A, B, N> table;
    private final Mapper<A, R> rowMapper;
    private final Mapper<B, C> columnMapper;
    private final Mapper<N, V> valueMapper;

    public TransformingTable(Table<A, B, N> table, Mapper<A, R> rowMapper, Mapper<B, C> columnMapper, Mapper<N, V> valueMapper) {
        this.table = table;
        this.rowMapper = rowMapper;
        this.columnMapper = columnMapper;
        this.valueMapper = valueMapper;
    }

    @Override
    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
        return table.contains(rowMapper.unmap((R) rowKey), columnMapper.unmap((C) columnKey));
    }

    @Override
    public boolean containsRow(@Nullable Object rowKey) {
        return table.containsRow(rowMapper.unmap((R) rowKey));
    }

    @Override
    public boolean containsColumn(@Nullable Object columnKey) {
        return table.containsColumn(columnMapper.unmap((C) columnKey));
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return table.containsValue(valueMapper.unmap((V) value));
    }

    @Override
    public @Nullable V get(@Nullable Object rowKey, @Nullable Object columnKey) {
        return valueMapper.map(table.get(rowMapper.unmap((R) rowKey), columnMapper.unmap((C) columnKey)));
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public void clear() {
        table.clear();
    }

    @Override
    public @Nullable V put(@NotNull R rowKey, @NotNull C columnKey, @NotNull V value) {
        return valueMapper.map(table.put(rowMapper.unmap(rowKey), columnMapper.unmap(columnKey), valueMapper.unmap(value)));
    }

    @Override
    public void putAll(@NotNull Table<? extends R, ? extends C, ? extends V> table) {
        for (Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
            this.table.put(rowMapper.unmap(cell.getRowKey()), columnMapper.unmap(cell.getColumnKey()), valueMapper.unmap(cell.getValue()));
        }
    }

    @Override
    public @Nullable V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
        return valueMapper.map(table.remove(rowMapper.unmap((R) rowKey), columnMapper.unmap((C) columnKey)));
    }

    @Override
    public Map<C, V> row(@NotNull R rowKey) {
        return new TransformingMap<>(table.row(rowMapper.unmap(rowKey)), columnMapper, valueMapper);
    }

    @Override
    public Map<R, V> column(@NotNull C columnKey) {
        return new TransformingMap<>(table.column(columnMapper.unmap(columnKey)), rowMapper, valueMapper);
    }

    @Override
    public Set<Cell<R, C, V>> cellSet() {
        return new TransformingSet<>(table.cellSet(), new Mapper<Cell<A, B, N>, Cell<R, C, V>>() {
            @Override
            public Cell<A, B, N> unmap(Cell<R, C, V> rcvCell) {
                return Tables.immutableCell(rowMapper.unmap(rcvCell.getRowKey()), columnMapper.unmap(rcvCell.getColumnKey()), valueMapper.unmap(rcvCell.getValue()));
            }

            @Override
            public Cell<R, C, V> map(Cell<A, B, N> abnCell) {
                return Tables.immutableCell(rowMapper.map(abnCell.getRowKey()), columnMapper.map(abnCell.getColumnKey()), valueMapper.map(abnCell.getValue()));
            }
        });
    }

    @Override
    public Set<R> rowKeySet() {
        return new TransformingSet<>(table.rowKeySet(), rowMapper);
    }

    @Override
    public Set<C> columnKeySet() {
        return new TransformingSet<>(table.columnKeySet(), columnMapper);
    }

    @Override
    public Collection<V> values() {
        return new TransformingCollection<>(table.values(), valueMapper);
    }

    @Override
    public Map<R, Map<C, V>> rowMap() {
        return new TransformingMap<>(table.rowMap(), rowMapper, new Mapper<Map<B, N>, Map<C, V>>() {
            @Override
            public Map<B, N> unmap(Map<C, V> cvMap) {
                return new TransformingMap<>(cvMap, columnMapper.swap(), valueMapper.swap());
            }

            @Override
            public Map<C, V> map(Map<B, N> bnMap) {
                return new TransformingMap<>(bnMap, columnMapper, valueMapper);
            }
        });
    }

    @Override
    public Map<C, Map<R, V>> columnMap() {
        return new TransformingMap<>(table.columnMap(), columnMapper, new Mapper<Map<A, N>, Map<R, V>>() {
            @Override
            public Map<A, N> unmap(Map<R, V> rvMap) {
                return new TransformingMap<>(rvMap, rowMapper.swap(), valueMapper.swap());
            }

            @Override
            public Map<R, V> map(Map<A, N> anMap) {
                return new TransformingMap<>(anMap, rowMapper, valueMapper);
            }
        });
    }
}
