package solutions.elevation.camel.component.openhtmltopdf;

import java.util.concurrent.ExecutorService;

import org.apache.camel.Category;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.support.DefaultEndpoint;

/**
 * Create PDF documents from HTML+CSS content
 */
@UriEndpoint(firstVersion = "3.10.0", scheme = "openhtmltopdf", title = "OpenHtmlToPdf",
    syntax="openhtmltopdf:operation", producerOnly = true,
    category = {
        Category.DOCUMENT,
        Category.TRANSFORMATION,
        Category.PRINTING
    })
public class OpenHtmlToPdfEndpoint extends DefaultEndpoint {

    private final OpenHtmlToPdfConfiguration configuration;

    public OpenHtmlToPdfEndpoint(String uri, OpenHtmlToPdfComponent component,
            OpenHtmlToPdfConfiguration configuration) {
        super(uri, component);
        this.configuration = configuration;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new OpenHtmlToPdfProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("This component does not support consuming.");
    }

    public ExecutorService createExecutor() {
        // TODO: Delete me when you implemented your custom component
        return getCamelContext().getExecutorServiceManager().newSingleThreadExecutor(this, "OpenHtmlToPdfConsumer");
    }
}
