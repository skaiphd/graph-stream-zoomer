package edu.leipzig.impl.functions.utils;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.annotation.InputGroup;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.types.Row;
import org.gradoop.common.model.impl.properties.Properties;
import org.gradoop.common.model.impl.properties.PropertyValue;

/**
 * Takes a row in special format and returns a properties instance
 * <p>
 * This implementation reuses much of the code of Much of Grable.
 * the code is copied directly or has only small changes.
 *
 * @link ToProperties
 * <p>
 * references to: org.gradoop.flink.model.impl.layouts.table.common.functions.table.scalar;
 */
@FunctionHint(
        output = @DataTypeHint(value= "RAW", bridgedTo = Properties.class))
public class ToProperties extends ScalarFunction {

    /**
     * Receives a 2n-ary Row
     * | key_1:String | value_1:PropertyValue | ... | key_n:String | value_n:PropertyValue|
     * and adds each key-value pair to given Properties instance
     *
     * @param properties Properties instance to add new key-value pairs to
     * @param o          2n-ary Row containing keys and values
     * @return properties instance with newly added entries
     */
    public static Properties processPropertiesRow(Properties properties, Object o) {
        if (!(o instanceof Row)) {
            throw new RuntimeException("Passing a " + o.getClass().getName() + " is not allowed here");
        }

        Row row = (Row) o;

        if (row.getArity() % 2 != 0) {
            throw new RuntimeException("Arity of given row is odd and therefore can't get processed");
        }

        for (int i = 0; i < row.getArity(); i = i + 2) {
            if (null == row.getField(i + 1)) {
                continue;
            }
            Object f0 = row.getField(i);
            if (!(f0 instanceof String)) {
                throw new RuntimeException("Odd expression of property row must be a property key string");
            }
            Object f1 = row.getField(i + 1);
            if (f1 instanceof Long) {
                f1 = PropertyValue.create(f1);
            } else if (!(f1 instanceof PropertyValue)) {
                throw new RuntimeException("Even expression of property row must be of type " +
                  "[Long, PropertyValue].");
            }

            String key = (String) f0;
            PropertyValue value = (PropertyValue) f1;

            properties.set(key, value);
        }
        return properties;
    }

    /**
     * Requires a 2n-ary Row
     * | key_1:String | value_1:PropertyValue | ... | key_n:String | value_n:PropertyValue|
     *
     * @param row row containing property key-value pairs
     * @return properties instance
     */
    @FunctionHint(
            input = @DataTypeHint(inputGroup = InputGroup.ANY))
    public Properties eval(Object rowO) {
        Row row = (Row) rowO;
        Properties properties = Properties.create();
        properties = processPropertiesRow(properties, row);
        return properties;
    }
}
