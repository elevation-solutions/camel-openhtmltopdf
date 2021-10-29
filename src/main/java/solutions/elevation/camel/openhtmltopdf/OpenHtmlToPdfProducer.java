package solutions.elevation.camel.openhtmltopdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class OpenHtmlToPdfProducer extends DefaultProducer {
    public static String BASE_URI_HEADER = "OpenHtmlToPdfBaseUri";

    private static final Logger LOG = LoggerFactory.getLogger(OpenHtmlToPdfProducer.class);
    private final OpenHtmlToPdfConfiguration configuration;

    public OpenHtmlToPdfProducer(OpenHtmlToPdfEndpoint endpoint) {
        super(endpoint);
        this.configuration = endpoint.getConfiguration();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Object result = null;
        switch (configuration.getOperation()) {
            case pdf:
                result = doPdf(exchange);
        }
        exchange.getMessage().setBody(result);
    }

    private OutputStream doPdf(Exchange exchange) throws IOException {
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useHttpStreamImplementation(new OkHttpStreamFactory());
        Object body = exchange.getMessage().getBody();
        String baseUri = slashifyBaseUri(exchange.getIn().getHeader(BASE_URI_HEADER, String.class));
        if (body instanceof String) {
            String stringBody = (String) body;
            URL url = isUrl(stringBody);
            if (url != null) {
                if (baseUri == null) {
                    baseUri = determineBaseUri(stringBody);
                }
                if (configuration.isLenientParsing()) {
                    if (url.getProtocol().equalsIgnoreCase("file")) {
                        org.jsoup.nodes.Document document = Jsoup.parse(new File(url.getPath()),
                                "UTF-8", baseUri);
                        builder.withW3cDocument(W3CDom.convert(document), baseUri);
                    }
                    else {
                        final org.jsoup.nodes.Document document = Jsoup.connect(url.toString()).get();
                        builder.withW3cDocument(W3CDom.convert(document), baseUri);
                    }
                }
                else {
                    builder.withUri(stringBody);
                }
            }
            else { // assume body is HTML content
                if (configuration.isLenientParsing()) {
                    org.jsoup.nodes.Document document = Jsoup.parse(stringBody, baseUri);
                    builder.withW3cDocument(W3CDom.convert(document), baseUri);
                }
                else {
                    builder.withHtmlContent(stringBody, baseUri);
                }
            }
        } else if (body instanceof Document) {
            builder.withW3cDocument((Document) body, baseUri);
        } else {
            throw new IllegalArgumentException(
                    String.format("Body type not supported: %s.", body.getClass().getName()));
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        builder.toStream(os);
        builder.run();
        return os;
    }

    private URL isUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private String slashifyBaseUri(String original) throws MalformedURLException {
        if (original == null) {
            return null;
        }
        if (original.endsWith("/")) {
            return original;
        }
        return original + "/";
    }

    private String determineBaseUri(String original) throws MalformedURLException {
        if (original == null) {
            return null;
        }
        URL url = new URL(original);
        if (original.endsWith("/")) {
            return original;
        }
        String path = url.getPath();
        if (path.equals("")) {
            return original + "/";
        }
        return original.replaceAll("/[^/]+$", "") + "/";
    }
}
