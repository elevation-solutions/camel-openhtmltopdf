# camel-openhtmltopdf
#### A Camel component to generate nice looking PDF files from HTML, CSS, images, etc.

This project is a Component for the [Apache Camel](https://camel.apache.org) open-source integration
framework. Its PDF generation capabilities are based on the 
[openhtmltopdf](https://github.com/danfickle/openhtmltopdf) project. Future enhancements may include 
image and SVG generation.

## Usage

Maven users will need to add the following dependency to their pom.xml for this component.

    <dependency>
        <groupId>solutions.elevation.camel</groupId>
        <artifactId>camel-openhtmltopdf</artifactId>
        <version>1.0.1</version>
    </dependency>
                    
The latest release uses camel-core version 3.11.0 as dependency.

## URI format

    openhtmltopdf:operation[?options]

Only Producer is suppored.

## Options
                          
| Option | Description | Default |
| ------ | ----------- | ------- |
| defaultPageWidth | Default page width to use if not specified in CSS | 8.5 |
| defaultPageHeight | Default page height to use if not specified in CSS | 11 |
| pageSizeUnits | Default page units to use if not specified in CSS. INCHES, MM | INCHES |
| defaultTextDirection| Default text direction to use if not specified in CSS | LTR |
| testMode | Whether to use test mode and output the PDF uncompressed | false |
| lenientParsing | Use leniant parsing for documents that don't comply with strict XHTML syntax. | true |

## Operations

### pdf

Generate a PDF file from HTML. 

| Parameter | Type | Description |
| --------- | ---- | ----------- |
| Body | String, org.w3c.dom.Document | If a string, must be either a URI or (X)HTML content. For URIs, `file`, `http` and `https` are supported.|
| OpenHtmlToPdfBaseUri | String | Base URI to use for resolving references (images, etc.). If not supplied and a URI was supplied in body, the component will attempt to determine the base URI. |



