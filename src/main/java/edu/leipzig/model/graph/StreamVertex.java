package edu.leipzig.model.graph;

import org.gradoop.common.model.impl.properties.Properties;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Stream vertex model (vertex_id, vertex_label, vertex_properties)
 */
public class StreamVertex implements Serializable {
    private String vertex_id;
    private String vertex_label;
    private Properties vertex_properties;
    private Timestamp event_time;

    /**
     * Default constructor is necessary to apply to POJO rules.
     */
    public StreamVertex() {
    }

    /**
     * constructor with all fields
     */
    public StreamVertex(String id, String label, Properties properties,Timestamp event_time) {
        this.vertex_id = id;
        this.vertex_label = label;
        this.vertex_properties = properties;
        this.event_time = event_time;
    }

    /**
     * @return current vertex_id
     */
    public String getVertexId() {
        return vertex_id;
    }

    /**
     * @param vertex_id vertex_id to set
     */
    public void setVertexId(String vertex_id) {
        this.vertex_id = vertex_id;
    }

    /**
     * @return current vertex_label
     */
    public String getVertexLabel() {
        return vertex_label;
    }

    /**
     * @param vertex_label vertex_label to set
     */
    public void setVertexLabel(String vertex_label) {
        this.vertex_label = vertex_label;
    }

    /**
     * @return current vertex_properties
     */
    public Properties getVertexProperties() {
        return vertex_properties;
    }

    /**
     * @param vertex_properties vertex_properties to set
     */
    public void setVertexProperties(Properties vertex_properties) {
        this.vertex_properties = vertex_properties;
    }

    /**
     * @return current timestamp
     */
    public Timestamp getEventTime() {
        return event_time;
    }

    /**
     * @param eventTime timestamp to set
     */
    public void setEventTime(Timestamp eventTime) {
        this.event_time = eventTime;
    }

    /**
     * Check equality of the vertex without id comparison.
     *
     * @param other the other vertex to compare
     * @return true, iff the vertex label and properties are equal
     */
    public boolean equalsWithoutId(StreamVertex other) {
        return this.getVertexLabel().equals(other.getVertexLabel())
          && this.getVertexProperties().equals(other.getVertexProperties())
          && this.getEventTime() == other.getEventTime()
         ;
    }

    public String toString() {
        return String.format("(" +
          "t:%s" +
          "%s:%s{%s})",
          this.event_time,
          this.vertex_id, this.vertex_label, this.vertex_properties == null ? "" : this.vertex_properties);
    }
}