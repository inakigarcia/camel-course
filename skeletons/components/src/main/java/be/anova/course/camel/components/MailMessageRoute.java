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

package be.anova.course.camel.components;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MailMessageRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
        //TODO: implement your Camel routes here if you prefer the Java DSL
		/*
		Build a set of camel routes to
		Generate a message every 30 seconds
		Write the message to a file
		Read the file and it to the JMS queue
		Read the message from the JMS queue and convert it to a nice HTML mail
		*/

		from("timer:per-second?period=30000").process(new HtmlTextGeneratorProcessor())
				.to("file:target/data/mail-out");

		from("file:target/data/mail-out")
				.to("jms:queue:msgsout");

		from("jms:queue:msgsout")
				.setHeader("timestamp").simple("${date:now:yyyyMMdd.hhss}").convertBodyTo(String.class)
				.to("xslt:transform.xsl").process(new HtmlEmailGeneratorProcessor())
				.to("log:test")
				.to("smtps:smtp.gmail.com?password=test1975&username=test.anova.rd@gmail.com");
	}

}
