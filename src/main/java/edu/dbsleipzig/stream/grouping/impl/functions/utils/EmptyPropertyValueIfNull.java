/*
 * Copyright © 2021 - 2024 Leipzig University (Database Research Group)
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
package edu.dbsleipzig.stream.grouping.impl.functions.utils;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.annotation.InputGroup;
import org.apache.flink.table.functions.ScalarFunction;
import org.gradoop.common.model.impl.properties.PropertyValue;

/**
 * Returns {@link PropertyValue#NULL_VALUE} if passed object is null
 * <p>
 * This implementation reuses much of the code of Much of Grable.
 * the code is copied directly or has only small changes.
 *
 * @link EmptyPropertyValueIfNull
 * <p>
 * references to: org.gradoop.flink.model.impl.layouts.table.common.functions.table.scalar;
 */
@FunctionHint(output = @DataTypeHint(value= "RAW", bridgedTo = PropertyValue.class))
public class EmptyPropertyValueIfNull extends ScalarFunction {

    /**
     * Returns
     * - {@link PropertyValue#NULL_VALUE}, if passed object is null
     * - passed property value, otherwise
     *
     * @param propertyValue property value or null
     * @return property value
     */
    @FunctionHint(input = @DataTypeHint(value= "RAW", bridgedTo = PropertyValue.class))
    @SuppressWarnings("unused")
    public PropertyValue eval(PropertyValue propertyValue) {
        if (null == propertyValue) {
            return PropertyValue.NULL_VALUE;
        }
        return propertyValue;
    }

    /**
     * If this scalar function is used w/o an argument, {@link PropertyValue#NULL_VALUE} is returned.
     *
     * @return {@link PropertyValue#NULL_VALUE}
     */
    @SuppressWarnings("unused")
    public PropertyValue eval() {
        return PropertyValue.NULL_VALUE;
    }
}
