package solutions.elevation.camel.component.openhtmltopdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;

@UriParams
public class OpenHtmlToPdfConfiguration {

    @UriParam(defaultValue = "INCHES")
    private BaseRendererBuilder.PageSizeUnits pageSizeUnits =
            BaseRendererBuilder.PageSizeUnits.INCHES;

    @UriParam(description = "Specifies the default page width to use if none is specified in CSS.",
            defaultValue = "8.5")
    private float defaultPageWidth = 8.5f;

    @UriParam(description = "Specifies the default page width to use if none is specified in CSS.",
            defaultValue = "11")
    private float defaultPageHeight = 11f;

    @UriParam(defaultValue = "LTR")
    private BaseRendererBuilder.TextDirection defaultTextDirection =
            BaseRendererBuilder.TextDirection.LTR;

    @UriParam(description = "Whether to use test mode and output the PDF uncompressed",
            defaultValue = "false")
    private boolean testMode;

    @UriPath(description = "Operation type")
    @Metadata(required = true)
    private OpenHtmlToPdfOperation operation;

    public void setOperation(String name) {
        this.operation = OpenHtmlToPdfOperation.valueOf(name);
    }
}
