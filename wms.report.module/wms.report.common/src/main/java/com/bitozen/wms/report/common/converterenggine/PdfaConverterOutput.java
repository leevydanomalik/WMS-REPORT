package com.bitozen.wms.report.common.converterenggine;

import java.io.File;
import java.io.Serializable;

/**
 * Wrapper class for a file converted to PDF/A format as well as any other relevant data.
 * 
 * @author dan179
 */
public class PdfaConverterOutput implements Serializable {
	private static final long serialVersionUID = 8657370419144639361L;

	private File pdfaConvertedFile;
	private String toolLoggingOutput;

	public PdfaConverterOutput(File pdfaConvertedFile, String toolLoggingOutput) {
		super();
		this.pdfaConvertedFile = pdfaConvertedFile;
		this.toolLoggingOutput = toolLoggingOutput;
	}

	public File getPdfaConvertedFile() {
		return pdfaConvertedFile;
	}
	
	public String getToolLoggingOutput() {
		return toolLoggingOutput;
	}

}
