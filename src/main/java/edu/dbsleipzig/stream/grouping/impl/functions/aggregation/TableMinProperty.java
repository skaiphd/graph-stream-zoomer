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
package edu.dbsleipzig.stream.grouping.impl.functions.aggregation;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.annotation.InputGroup;
import org.gradoop.common.model.impl.properties.PropertyValue;
import org.gradoop.common.model.impl.properties.PropertyValueUtils;

/**
 * Table Aggregate function to find minimum value of a property
 * <p>
 * This implementation reuses much of the code of Much of Grable.
 * the code is copied directly or has only small changes.
 * the changes are related to using data stream instead of data set in Grable.
 *
 * @link TableMinProperty
 * <p>
 * references to: org.gradoop.flink.model.impl.layouts.table.common.functions.table.aggregate;
 */
@FunctionHint(output = @DataTypeHint(value= "RAW", bridgedTo = PropertyValue.class))
public class TableMinProperty  extends BaseTablePropertyValueAggregateFunction {

    @Override
    public PropertyValue createAccumulator() {
        return PropertyValue.create(Double.MAX_VALUE);
    }

    @Override
    public PropertyValue getValue(PropertyValue propertyValue) {
        if (propertyValue.isDouble() &&
                Double.compare(propertyValue.getDouble(), Double.MAX_VALUE) == 0) {
            return PropertyValue.NULL_VALUE;
        } else {
            return propertyValue;
        }
    }

    @FunctionHint(accumulator = @DataTypeHint(value = "RAW", bridgedTo = PropertyValue.class),
      input = @DataTypeHint(inputGroup = InputGroup.ANY))
    @SuppressWarnings("unused")
    public void accumulate(Object accO, Object valO) {
        PropertyValue acc = (PropertyValue) accO;
        PropertyValue val = (PropertyValue) valO;
        if (null != val) {
            acc.setObject(PropertyValueUtils.Numeric.min(acc, val).getObject());
        }
    }

    @SuppressWarnings("unused")
    public void retract(PropertyValue acc, PropertyValue val) {
    }

    @SuppressWarnings("unused")
    public void merge(PropertyValue acc, Iterable<PropertyValue> it) {
        for (PropertyValue val : it) {
            acc.setObject(PropertyValueUtils.Numeric.min(acc, val).getObject());
        }
    }

    @SuppressWarnings("unused")
    public void resetAccumulator(PropertyValue acc) {
        acc.setDouble(Double.MAX_VALUE);
    }
}
