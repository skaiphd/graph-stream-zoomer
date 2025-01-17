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

import org.apache.flink.table.functions.AggregateFunction;
import org.gradoop.common.model.api.entities.Element;
import org.gradoop.common.model.impl.properties.PropertyValue;
import org.gradoop.flink.model.impl.operators.aggregation.functions.BaseAggregateFunction;
import org.gradoop.flink.model.impl.operators.aggregation.functions.sum.Sum;

import java.util.Objects;
/**
 * Superclass of aggregate functions that sum property values of elements.
 * <p>
 *  This implementation reuses much of the code of Much of Grable.
 *  the code is copied directly or has only small changes.
 *
 *  @link SumProperty
 *  <p>
 *  references to: org.gradoop.flink.model.impl.operators.aggregation.functions.sum;
 */
public class SumProperty extends BaseAggregateFunction implements Sum, CustomizedAggregationFunction {

    /**
     * Property key whose value should be aggregated.
     */
    private final String propertyKey;

    /**
     * Creates a new instance of a SumProperty aggregate function.
     *
     * @param propertyKey property key to aggregate
     */
    public SumProperty(String propertyKey) {
        this(propertyKey, "sum_" + propertyKey);
    }

    /**
     * Creates a new instance of a SumProperty aggregate function.
     *
     * @param propertyKey property key to aggregate
     * @param aggregatePropertyKey aggregate property key
     */
    public SumProperty(String propertyKey, String aggregatePropertyKey) {
        super(aggregatePropertyKey);
        Objects.requireNonNull(propertyKey);
        this.propertyKey = propertyKey;
    }

    @Override
    public PropertyValue getIncrement(Element element) {
        return element.getPropertyValue(propertyKey);
    }

    @Override
    public AggregateFunction<PropertyValue, PropertyValue> getTableAggFunction() {
        return new TableSumProperty();
    }

    @Override
    public String getPropertyKey() {
        return this.propertyKey;
    }
}
