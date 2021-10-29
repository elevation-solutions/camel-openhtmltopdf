package solutions.elevation.camel.openhtmltopdf;

import java.io.File;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenHtmlToPdfComponentTest extends CamelTestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(OpenHtmlToPdfProducer.class);

    @BeforeEach
    public void setup() {
        File testOutput = new File("test-output");
        if (testOutput.exists()) {
            testOutput.delete();
        }
    }

    @Test
    public void lenientNoTrailingSlash() throws Exception {
        String html = "https://news.ycombinator.com";
        template.sendBody("direct:lenient", html);
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }

    @Test
    public void lenientTrailingSlash() throws Exception {
        String html = "https://news.ycombinator.com/";
        template.sendBody("direct:lenient", html);
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }

    @Test
    public void strict() throws Exception {
        File htmlFile = new File("src/test/resources/testfiles/test.html");
        String fileUri = htmlFile.toURI().toString();
        template.sendBody("direct:strict", fileUri);
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }

    @Test
    public void strictWithBaseUri() throws Exception {
        File htmlFile = new File("src/test/resources/testfiles/test.html");
        String fileUri = htmlFile.toURI().toString();
        File baseDir = new File("src/test/resources/testfiles");
        LOG.warn(baseDir.toURI().toString());
        template.sendBodyAndHeader("direct:strict", fileUri,
                OpenHtmlToPdfProducer.BASE_URI_HEADER, baseDir.toURI().toString());
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }

    @Test
    public void stringInput() {
        template.sendBody("direct:strict", "<html><body>test</body></html>");
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }

    @Test
    public void stringInputlenient() {
        template.sendBodyAndHeader("direct:lenient", "<html><body>test</body></html>",
                OpenHtmlToPdfProducer.BASE_URI_HEADER, "https://news.ycombinator.com");
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }

    @Test
    public void lenientBaseUri() throws Exception {
        String html = "https://news.ycombinator.com";
        template.sendBodyAndHeader("direct:lenient", html,
                OpenHtmlToPdfProducer.BASE_URI_HEADER, "https://news.ycombinator.com");
        File testOutput = new File("test-output/thepdf.pdf");
        assertTrue(testOutput.exists());
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:lenient")
                    .to("openhtmltopdf:pdf")
                    .to("file:test-output?fileName=thepdf.pdf");

                from("direct:strict")
                    .to("openhtmltopdf:pdf?lenientParsing=false")
                    .to("file:test-output?fileName=thepdf.pdf");
            }
        };
    }
}
