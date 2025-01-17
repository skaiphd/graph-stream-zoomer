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
package edu.dbsleipzig.stream.grouping.model.table;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.gradoop.common.model.impl.properties.Properties;

import java.util.HashMap;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.lit;

/**
 * Basic table set class which is just a wrapper for a map: tableName->{@link Table}
 * <p>
 * The tableName is a name in graph context, like "vertices". It must not be confused with the
 * internal table name in Flink's table environment!
 *
 * <p>
 * This implementation reuses much of the code of Grable.
 *
 * @link GVETableSet
 * <p>
 * references to: org.gradoop.flink.model.impl.layouts.table.gve;
 */

public class TableSet extends HashMap<String, Table> {
    /**
     * Field name of id in vertices table
     */
    public static final String FIELD_VERTEX_ID = "vertex_id";
    /**
     * Field name of label in vertices table
     */
    public static final String FIELD_VERTEX_LABEL = "vertex_label";
    /**
     * Field name of properties in vertices table
     */
    public static final String FIELD_VERTEX_PROPERTIES = "vertex_properties";
    /**
     * Field name of id in edges table
     */
    public static final String FIELD_EDGE_ID = "edge_id";
    /**
     * Field name of source id in edges table
     */
    public static final String FIELD_SOURCE_ID = "source_id";
    /**
     * Field name of head id in edges table
     */
    public static final String FIELD_TARGET_ID = "target_id";
    /**
     * Field name of label in edges table
     */
    public static final String FIELD_EDGE_LABEL = "edge_label";
    /**
     * Field name of properties in edges table
     */
    public static final String FIELD_EDGE_PROPERTIES = "edge_properties";
    /**
     * Field name of source vertex properties in graph table
     */
    public static final String FIELD_VERTEX_SOURCE_PROPERTIES = "source_properties";
    /**
     * Field name of source vertex label in graph table
     */
    public static final String FIELD_VERTEX_SOURCE_LABEL = "source_label";
    /**
     * Field name of target vertex properties in graph table
     */
    public static final String FIELD_VERTEX_TARGET_PROPERTIES = "target_properties";
    /**
     * Field name of target vertex label in graph table
     */
    public static final String FIELD_VERTEX_TARGET_LABEL = "target_label";
    /**
     * Field name of edge timestamp.
     */
    public static final String FIELD_EVENT_TIME = "event_time";
    /**
     * Field name of vertex timestamp.
     */
    public static final String FIELD_VERTEX_EVENT_TIME = "vertex_event_time";
    /**
     * Field name of prepared vertex timestamp
     */
    public static final String PREPARED_VERTICES_EVENT_TIME = "prepared_vertices_event_time";
    /**
     * Field name if not grouping on label
     */
    public static final String FIELD_NO_LABEL = "";
    /**
     * Table key of vertices table
     */
    public static final String TABLE_VERTICES = "vertices";
    /**
     * Table key of edges table
     */
    public static final String TABLE_EDGES = "edges";

    /**
     * Constructor
     */
    public TableSet() {
    }

    /**
     * Return vertices table
     *
     * @return vertices table
     */
    public Table getVertices() {
        return get(TABLE_VERTICES);
    }

    /**
     * Returns edges table
     *
     * @return edges table
     */
    public Table getEdges() {
        return get(TABLE_EDGES);
    }

    /**
     * Get the vertex table schema as {@link Schema} object.
     *
     * @return the vertex table schema
     */
    public static Schema getVertexSchema() {
        return Schema.newBuilder()
          .column(FIELD_VERTEX_ID, DataTypes.STRING())
          .column(FIELD_EVENT_TIME, DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
          .column(FIELD_VERTEX_LABEL, DataTypes.STRING())
          .column(FIELD_VERTEX_PROPERTIES, DataTypes.RAW(Properties.class).bridgedTo(Properties.class))
          .watermark(FIELD_EVENT_TIME, $(FIELD_EVENT_TIME).minus(lit(10).seconds()))
          .build();
    }

    /**
     * Get the edge table schema as {@link Schema} object.
     *
     * @return the edge table schema
     */
    public static Schema getEdgeSchema() {
        return Schema.newBuilder()
          .column(FIELD_EDGE_ID, DataTypes.STRING())
          .column(FIELD_EDGE_LABEL, DataTypes.STRING())
          .column(FIELD_EDGE_PROPERTIES, DataTypes.RAW(Properties.class).bridgedTo(Properties.class))
          .column(FIELD_TARGET_ID, DataTypes.STRING())
          .column(FIELD_SOURCE_ID, DataTypes.STRING())
          .column(FIELD_EVENT_TIME, DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
          .watermark(FIELD_EVENT_TIME, $(FIELD_EVENT_TIME).minus(lit(10).seconds()))
          .build();
    }
}
