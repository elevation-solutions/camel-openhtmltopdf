package solutions.elevation.camel.openhtmltopdf;

import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;
import org.apache.camel.spi.UriPath;

@UriParams
public class OpenHtmlToPdfConfiguration {

    @UriPath(description = "Operation type")
    @Metadata(required = true)
    private OpenHtmlToPdfOperation operation;

    @UriParam(description = "Page size units.", defaultValue = "INCHES")
    private BaseRendererBuilder.PageSizeUnits pageSizeUnits =
            BaseRendererBuilder.PageSizeUnits.INCHES;

    @UriParam(description = "Specifies the default page width to use if none is specified in CSS.",
            defaultValue = "8.5")
    private float defaultPageWidth = 8.5f;

    @UriParam(description = "Specifies the default page width to use if none is specified in CSS.",
            defaultValue = "11")
    private float defaultPageHeight = 11f;

    @UriParam(description = "Default text direction.", defaultValue = "LTR")
    private BaseRendererBuilder.TextDirection defaultTextDirection =
            BaseRendererBuilder.TextDirection.LTR;

    @UriParam(description = "Whether to use test mode and output the PDF uncompressed",
            defaultValue = "false")
    private boolean testMode;

    @UriParam(description = "Use lenient parsing for documents that don't comply with strict syntax.",
            defaultValue = "true")
    private boolean lenientParsing = true;

    public void setOperation(String name) {
        this.operation = OpenHtmlToPdfOperation.valueOf(name);
    }

    public OpenHtmlToPdfOperation getOperation() {
        return operation;
    }

    public float getDefaultPageWidth() {
        return defaultPageWidth;
    }

    public void setDefaultPageWidth(float defaultPageWidth) {
        this.defaultPageWidth = defaultPageWidth;
    }

    public float getDefaultPageHeight() {
        return defaultPageHeight;
    }

    public void setDefaultPageHeight(float defaultPageHeight) {
        this.defaultPageHeight = defaultPageHeight;
    }

    public BaseRendererBuilder.TextDirection getDefaultTextDirection() {
        return defaultTextDirection;
    }

    public void setDefaultTextDirection(BaseRendererBuilder.TextDirection defaultTextDirection) {
        this.defaultTextDirection = defaultTextDirection;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public BaseRendererBuilder.PageSizeUnits getPageSizeUnits() {
        return pageSizeUnits;
    }

    public void setPageSizeUnits(BaseRendererBuilder.PageSizeUnits pageSizeUnits) {
        this.pageSizeUnits = pageSizeUnits;
    }

    public boolean isLenientParsing() {
        return lenientParsing;
    }

    public void setLenientParsing(boolean lenientParsing) {
        this.lenientParsing = lenientParsing;
    }
}
