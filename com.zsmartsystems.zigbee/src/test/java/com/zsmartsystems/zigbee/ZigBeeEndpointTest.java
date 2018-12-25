/**
 * Copyright (c) 2016-2019 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.zsmartsystems.zigbee.app.ZigBeeApplication;
import com.zsmartsystems.zigbee.dao.ZigBeeEndpointDao;
import com.zsmartsystems.zigbee.transport.ZigBeeTransportTransmit;
import com.zsmartsystems.zigbee.zcl.ZclCluster;
import com.zsmartsystems.zigbee.zcl.ZclCommand;
import com.zsmartsystems.zigbee.zcl.clusters.ZclAlarmsCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclBasicCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclColorControlCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclDoorLockCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclLevelControlCluster;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReadAttributesResponse;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReportAttributesCommand;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;

/**
 *
 * @author Chris Jackson
 *
 */
public class ZigBeeEndpointTest {

    @Test
    public void testOutputClusterIds() {
        ZigBeeEndpoint device = getEndpoint();

        List<Integer> clusterIdList = new ArrayList<Integer>();
        clusterIdList.add(ZclAlarmsCluster.CLUSTER_ID);
        clusterIdList.add(ZclBasicCluster.CLUSTER_ID);
        clusterIdList.add(ZclColorControlCluster.CLUSTER_ID);
        clusterIdList.add(ZclDoorLockCluster.CLUSTER_ID);
        clusterIdList.add(ZclLevelControlCluster.CLUSTER_ID);
        device.setOutputClusterIds(clusterIdList);

        assertEquals(5, device.getOutputClusterIds().size());

        assertNotNull(device.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID));
        assertTrue(device.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID).isClient());
        assertFalse(device.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID).isServer());

        assertNotNull(device.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID));
        assertTrue(device.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID).isClient());
        assertFalse(device.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID).isServer());

        clusterIdList = new ArrayList<Integer>();
        clusterIdList.add(ZclAlarmsCluster.CLUSTER_ID);
        clusterIdList.add(ZclBasicCluster.CLUSTER_ID);
        assertTrue(device.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID).isClient());
        assertFalse(device.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID).isServer());

        System.out.println(device.toString());
    }

    @Test
    public void testInputClusterIds() {
        ZigBeeEndpoint endpoint = getEndpoint();

        List<Integer> clusterIdList = new ArrayList<Integer>();
        clusterIdList.add(ZclAlarmsCluster.CLUSTER_ID);
        clusterIdList.add(ZclBasicCluster.CLUSTER_ID);
        clusterIdList.add(ZclColorControlCluster.CLUSTER_ID);
        clusterIdList.add(ZclDoorLockCluster.CLUSTER_ID);
        clusterIdList.add(ZclLevelControlCluster.CLUSTER_ID);
        endpoint.setInputClusterIds(clusterIdList);

        assertEquals(5, endpoint.getInputClusterIds().size());

        assertNotNull(endpoint.getInputCluster(ZclAlarmsCluster.CLUSTER_ID));
        assertFalse(endpoint.getInputCluster(ZclAlarmsCluster.CLUSTER_ID).isClient());
        assertTrue(endpoint.getInputCluster(ZclAlarmsCluster.CLUSTER_ID).isServer());

        assertNotNull(endpoint.getInputCluster(ZclLevelControlCluster.CLUSTER_ID));
        assertFalse(endpoint.getInputCluster(ZclLevelControlCluster.CLUSTER_ID).isClient());
        assertTrue(endpoint.getInputCluster(ZclLevelControlCluster.CLUSTER_ID).isServer());
    }

    @Test
    public void testProfileId() {
        ZigBeeEndpoint endpoint = getEndpoint();

        endpoint.setProfileId(0x104);
        assertEquals(0x104, endpoint.getProfileId());
    }

    @Test
    public void testDeviceVersion() {
        ZigBeeEndpoint endpoint = getEndpoint();

        endpoint.setDeviceVersion(123);
        assertEquals(123, endpoint.getDeviceVersion());
    }

    @Test
    public void commandReceived() {
        ZigBeeEndpoint endpoint = getEndpoint();

        ZclCommand command = mockZclCommand(ZclCommand.class);
        endpoint.commandReceived(command);

        List<Integer> clusterIds = new ArrayList<>();
        clusterIds.add(0);
        endpoint.setInputClusterIds(clusterIds);
        endpoint.commandReceived(command);

        command = mockZclCommand(ReportAttributesCommand.class);
        endpoint.commandReceived(command);

        ZigBeeApplication application = Mockito.mock(ZigBeeApplication.class);
        Mockito.when(application.getClusterId()).thenReturn(0);
        endpoint.addApplication(application);
        Mockito.verify(application, Mockito.times(1)).appStartup(ArgumentMatchers.any(ZclCluster.class));

        command = mockZclCommand(ReadAttributesResponse.class);
        endpoint.commandReceived(command);
        Mockito.verify(application, Mockito.times(1)).commandReceived(ArgumentMatchers.any(ZclCommand.class));
    }

    private ZclCommand mockZclCommand(Class<?> clazz) {
        ZclCommand command = (ZclCommand) Mockito.mock(clazz);
        ZigBeeEndpointAddress sourceAddress = new ZigBeeEndpointAddress(1234, 5);
        Mockito.when(command.getSourceAddress()).thenReturn(sourceAddress);
        Mockito.when(command.getClusterId()).thenReturn(0);
        Mockito.when(command.getCommandDirection()).thenReturn(ZclCommandDirection.SERVER_TO_CLIENT);

        return command;
    }

    @Test
    public void testGetDeviceId() {
        ZigBeeEndpoint endpoint = getEndpoint();

        endpoint.setDeviceId(9999);
        assertEquals(9999, endpoint.getDeviceId());
    }

    @Test
    public void setDao() {
        ZigBeeEndpoint endpoint = getEndpoint();

        ZigBeeEndpointDao dao = new ZigBeeEndpointDao();
        endpoint.setDao(dao);
    }

    private ZigBeeEndpoint getEndpoint() {
        ZigBeeTransportTransmit mockedTransport = Mockito.mock(ZigBeeTransportTransmit.class);
        ZigBeeNetworkManager networkManager = new ZigBeeNetworkManager(mockedTransport);
        ZigBeeNode node = new ZigBeeNode(networkManager, new IeeeAddress());
        node.setNetworkAddress(1234);
        return new ZigBeeEndpoint(node, 5);
    }

}
