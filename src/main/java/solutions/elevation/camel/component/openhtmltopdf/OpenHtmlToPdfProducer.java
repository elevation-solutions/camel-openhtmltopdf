package solutions.elevation.camel.component.openhtmltopdf;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenHtmlToPdfProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(OpenHtmlToPdfProducer.class);
    private OpenHtmlToPdfEndpoint endpoint;

    public OpenHtmlToPdfProducer(OpenHtmlToPdfEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());
    }
}
