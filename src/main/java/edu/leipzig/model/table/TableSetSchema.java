package edu.leipzig.model.table;

import edu.leipzig.impl.functions.utils.PlannerExpressionSeqBuilder;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.planner.expressions.PlannerExpression;
import scala.collection.Seq;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for a table based graph schema,
 * which is basically a map: tableName->{@link TableSchema}
 * <p>
 * This implementation reuses much of the code of Much of Grable.
 * the code is copied directly or has only small changes.
 *
 * @link TableSetSchema
 * <p>
 * references to: org.gradoop.flink.model.impl.layouts.table;
 */
class TableSetSchema {
    /**
     * schema map
     */
    private Map<String, TableSchema> schema;

    /**
     * Constructor
     *
     * @param schema immutable schema map
     */
    TableSetSchema(Map<String, TableSchema> schema) {
        this.schema = new HashMap<>();
        this.schema.putAll(schema);
    }

    /**
     * Returns true, iff the schema contains a table with given table name
     *
     * @param tableName table name to check
     * @return true, iff the schema contains a table with given table name
     */
    private boolean containsTable(String tableName) {
        return schema.containsKey(tableName);
    }

    /**
     * Returns the {@link TableSchema} for table with given table name
     *
     * @param tableName name of table to get schema for
     * @return table schema for table with given table name
     */
    private TableSchema getTable(String tableName) {
        if (!containsTable(tableName)) {
            throw new RuntimeException("Invalid tableName " + tableName);
        }
        return schema.get(tableName);
    }

    /**
     * Returns a string array of field names of table with given table name
     *
     * @param tableName name of table to get field names for
     * @return string array of field names
     */
    private String[] getFieldNamesForTable(String tableName) {
        return getTable(tableName).getFieldNames();
    }


    /**
     * Builds a scala sequence of expressions which can be used to project a table with a super set
     * of the fields (of the table for the given table name) to those fields only
     *
     * @param tableName name of table to get project expressions for
     * @return scala sequence of expressions
     */
    Seq<PlannerExpression> buildProjectExpressions(String tableName) {
        PlannerExpressionSeqBuilder builder = new PlannerExpressionSeqBuilder();
        for (String fieldName : getFieldNamesForTable(tableName)) {
            builder.field(fieldName);
        }
        return builder.buildSeq();
    }
}
