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

import java.util.function.Function;

public interface Mapper<A, B> {

    A unmap(B b);

    B map(A a);

    default Mapper<B, A> swap() {
        return new Mapper<B, A>() {
            @Override
            public B unmap(A a) {
                return Mapper.this.map(a);
            }

            @Override
            public A map(B b) {
                return Mapper.this.unmap(b);
            }
        };
    }

    static <A> Mapper<A, A> identity() {
        // noinspection unchecked
        return (Mapper<A, A>) IdentityMapper.INSTANCE;
    }

    static <A, B> Mapper<A, B> compose(Function<A, B> aToB, Function<B, A> bToA) {
        return new Mapper<A, B>() {
            @Override
            public A unmap(B b) {
                return bToA.apply(b);
            }

            @Override
            public B map(A a) {
                return aToB.apply(a);
            }
        };
    }
}
