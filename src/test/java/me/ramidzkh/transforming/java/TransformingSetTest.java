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

import me.ramidzkh.transforming.map.IntegerStringMapper;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransformingSetTest {

    @Test
    public void addOriginal() {
        Set<Integer> set = new HashSet<>();
        Set<String> strings = new TransformingSet<>(set, IntegerStringMapper.INSTANCE);
        set.add(14);
        assertEquals(strings.iterator().next(), "14");
    }

    @Test
    public void addTransformed() {
        Set<Integer> set = new HashSet<>();
        Set<String> strings = new TransformingSet<>(set, IntegerStringMapper.INSTANCE);
        strings.add("14");
        assertEquals(set.iterator().next(), 14);
    }

    @Test
    public void error() {
        assertThrows(NumberFormatException.class, () -> {
            Set<Integer> set = new HashSet<>();
            Set<String> strings = new TransformingSet<>(set, IntegerStringMapper.INSTANCE);
            strings.add(" ");
        });
    }
}
