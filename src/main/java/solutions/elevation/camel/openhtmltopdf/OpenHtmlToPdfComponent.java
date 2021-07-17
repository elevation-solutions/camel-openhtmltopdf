package solutions.elevation.camel.openhtmltopdf;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;

@Component("openhtmltopdf")
public class OpenHtmlToPdfComponent extends DefaultComponent {
    
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters)
            throws Exception {
        OpenHtmlToPdfConfiguration configuration = new OpenHtmlToPdfConfiguration();
        configuration.setOperation(remaining);

        Endpoint endpoint = new OpenHtmlToPdfEndpoint(uri, this, configuration);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
