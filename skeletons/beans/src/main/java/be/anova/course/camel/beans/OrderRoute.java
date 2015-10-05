/*
 * (c) 2010, anova r&d bvba. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.anova.course.camel.beans;

import org.apache.camel.builder.RouteBuilder;

public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //TODO: implement your Camel routes here if you prefer the Java DSL
        /*
        Wire the OrderService into your route
        3 methods
            – enrich() use Exchange as parameter and add timestamp header to the exchange
            – process() uses annotations to get parameter and message body
            – log() method uses @Consume to read from the direct:log endpoint
         */
        from("file:src/main/resources/orders?noop=true")
                .to("file:target/audit")
                .beanRef("orderService", "enrich")
                .beanRef("orderService","process")
                .to("direct:order");
    }

}
