package edu.leipzig.model.graph;

import org.gradoop.common.model.impl.properties.Properties;

import java.io.Serializable;

/**
 * Stream edge model (edge_id, edge_label, edge_properties, source_id, target_id, timestamp)
 */

public class StreamEdge implements Serializable {
    private String edge_id;
    private String edge_label;
    private Properties edge_properties;
    private String source_id;
    private String target_id;
    private long event_time;

    /**
     * Default constructor is necessary to apply to POJO rules.
     */
    public StreamEdge() {
    }

    /**
     * constructor with all fields
     */
    public StreamEdge(String id, Long event_time, String label, Properties properties, String sourceId, String targetId) {
        this.edge_id = id;
        this.event_time = event_time;
        this.edge_label = label;
        this.edge_properties = properties;
        this.target_id = sourceId;
        this.source_id = targetId;
    }

    /**
     * @return current edge_id
     */
    public String getEdgeId() {
        return edge_id;
    }

    /**
     * @param edge_id edge_id to set
     */
    public void setEdgeId(String edge_id) {
        this.edge_id = edge_id;
    }

    /**
     * @return current edge_label
     */
    public String getEdgeLabel() {
        return edge_label;
    }

    /**
     * @param edge_label edge_label to set
     */
    public void setEdgeLabel(String edge_label) {
        this.edge_label = edge_label;
    }

    /**
     * @return current edge_properties
     */
    public Properties getEdgeProperties() {
        return edge_properties;
    }

    /**
     * @param edge_properties edge_properties to set
     */
    public void setEdgeProperties(Properties edge_properties) {
        this.edge_properties = edge_properties;
    }

    /**
     * @return current source_id
     */
    public String getSourceId() {
        return source_id;
    }

    /**
     * @param source_id source_id to set
     */
    public void setSourceId(String source_id) {
        this.source_id = source_id;
    }

    /**
     * @return current head_id
     */
    public String getTargetId() {
        return target_id;
    }

    /**
     * @param target_id head_id to set
     */
    public void setTargetId(String target_id) {
        this.target_id = target_id;
    }

    /**
     * @return current timestamp
     */
    public long getEventTime() {
        return event_time;
    }

    /**
     * @param eventTime timestamp to set
     */
    public void setEventTime(long eventTime) {
        this.event_time = eventTime;
    }

    /**
     * Check equality of the edge without id comparison.
     *
     * @param other the other edge to compare
     * @return true, iff the edge label, properties and timestamp are equal
     */
    public boolean equalsWithoutId(StreamEdge other) {
        return this.getEdgeLabel().equals(other.getEdgeLabel())
          && this.getEdgeProperties().equals(other.getEdgeProperties())
          && this.getEventTime() == other.getEventTime();
    }

    @Override
    public String toString() {
        return String.format("(%s)-[t:%d %s%s%s{%s}]->(%s)",
                this.target_id, this.event_time, this.edge_id,
                this.edge_label != null && !this.edge_label.equals("") ? ":" : "",
                this.edge_label, this.edge_properties == null ? "" : this.edge_properties, this.source_id);
    }
}
