/**
 * Copyright (c) 2016-2019 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee.zcl.clusters.general;

import javax.annotation.Generated;

import com.zsmartsystems.zigbee.zcl.ZclCommand;
import com.zsmartsystems.zigbee.zcl.ZclFieldSerializer;
import com.zsmartsystems.zigbee.zcl.ZclFieldDeserializer;
import com.zsmartsystems.zigbee.zcl.protocol.ZclDataType;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;

/**
 * Discover Commands Generated value object class.
 * <p>
 * Cluster: <b>General</b>. Command is sent <b>TO</b> the server.
 * This command is a <b>generic</b> command used across the profile.
 * <p>
 * The Discover Commands Generated command is generated when a remote device wishes to discover the
 * commands that a cluster may generate on the device to which this command is directed.
 * <p>
 * Code is auto-generated. Modifications may be overwritten!
 */
@Generated(value = "com.zsmartsystems.zigbee.autocode.ZclProtocolCodeGenerator", date = "2018-04-26T19:23:24Z")
public class DiscoverCommandsGenerated extends ZclCommand {
    /**
     * Start command identifier command message field.
     */
    private Integer startCommandIdentifier;

    /**
     * Maximum command identifiers command message field.
     */
    private Integer maximumCommandIdentifiers;

    /**
     * Default constructor.
     */
    public DiscoverCommandsGenerated() {
        genericCommand = true;
        commandId = 19;
        commandDirection = ZclCommandDirection.CLIENT_TO_SERVER;
    }

    /**
     * Sets the cluster ID for <i>generic</i> commands. {@link DiscoverCommandsGenerated} is a <i>generic</i> command.
     * <p>
     * For commands that are not <i>generic</i>, this method will do nothing as the cluster ID is fixed.
     * To test if a command is <i>generic</i>, use the {@link #isGenericCommand} method.
     *
     * @param clusterId the cluster ID used for <i>generic</i> commands as an {@link Integer}
     */
    @Override
    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * Gets Start command identifier.
     *
     * @return the Start command identifier
     */
    public Integer getStartCommandIdentifier() {
        return startCommandIdentifier;
    }

    /**
     * Sets Start command identifier.
     *
     * @param startCommandIdentifier the Start command identifier
     */
    public void setStartCommandIdentifier(final Integer startCommandIdentifier) {
        this.startCommandIdentifier = startCommandIdentifier;
    }

    /**
     * Gets Maximum command identifiers.
     *
     * @return the Maximum command identifiers
     */
    public Integer getMaximumCommandIdentifiers() {
        return maximumCommandIdentifiers;
    }

    /**
     * Sets Maximum command identifiers.
     *
     * @param maximumCommandIdentifiers the Maximum command identifiers
     */
    public void setMaximumCommandIdentifiers(final Integer maximumCommandIdentifiers) {
        this.maximumCommandIdentifiers = maximumCommandIdentifiers;
    }

    @Override
    public void serialize(final ZclFieldSerializer serializer) {
        serializer.serialize(startCommandIdentifier, ZclDataType.UNSIGNED_8_BIT_INTEGER);
        serializer.serialize(maximumCommandIdentifiers, ZclDataType.UNSIGNED_8_BIT_INTEGER);
    }

    @Override
    public void deserialize(final ZclFieldDeserializer deserializer) {
        startCommandIdentifier = (Integer) deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
        maximumCommandIdentifiers = (Integer) deserializer.deserialize(ZclDataType.UNSIGNED_8_BIT_INTEGER);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(115);
        builder.append("DiscoverCommandsGenerated [");
        builder.append(super.toString());
        builder.append(", startCommandIdentifier=");
        builder.append(startCommandIdentifier);
        builder.append(", maximumCommandIdentifiers=");
        builder.append(maximumCommandIdentifiers);
        builder.append(']');
        return builder.toString();
    }

}
