/**
 * Copyright 2016-2017 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.specification.nukleus.tcp.streams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;
import org.reaktivity.specification.nukleus.NukleusRule;

public class ServerIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("route", "org/reaktivity/specification/nukleus/tcp/control/route")
        .addScriptRoot("streams", "org/reaktivity/specification/nukleus/tcp/streams");

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    private final NukleusRule nukleus = new NukleusRule()
        .directory("target/nukleus-itests")
        .streams("target", "tcp#any")
        .streams("tcp", "target");

    @Rule
    public final TestRule chain = outerRule(nukleus).around(k3po).around(timeout);

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/connection.established/server/nukleus",
        "${streams}/connection.established/server/target"
    })
    public void shouldEstablishConnection() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/server.sent.data/server/nukleus",
        "${streams}/server.sent.data/server/target"
    })
    public void shouldReceiveServerSentData() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/server.sent.data.multiple.frames/server/nukleus",
        "${streams}/server.sent.data.multiple.frames/server/target"
    })
    public void shouldReceiveServerSentDataWithMultipleFrames() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/client.sent.data/server/nukleus",
        "${streams}/client.sent.data/server/target"
    })
    public void shouldReceiveClientSentData() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/client.sent.data.multiple.frames/server/nukleus",
        "${streams}/client.sent.data.multiple.frames/server/target"
    })
    public void shouldReceiveClientSentDataWithMultipleFrames() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/echo.data/server/nukleus",
        "${streams}/echo.data/server/target"
    })
    public void shouldEchoData() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/server.close/server/nukleus",
        "${streams}/server.close/server/target"
    })
    public void shouldInitiateServerClose() throws Exception
    {
        k3po.finish();
    }

    @Test
    @Specification({
        "${route}/input/new/nukleus",
        "${route}/input/new/controller",
        "${streams}/client.close/server/nukleus",
        "${streams}/client.close/server/target"
    })
    public void shouldInitiateClientClose() throws Exception
    {
        k3po.finish();
    }
}
