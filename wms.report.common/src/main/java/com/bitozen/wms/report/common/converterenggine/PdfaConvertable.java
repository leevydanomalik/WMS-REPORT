
package com.bitozen.wms.report.common.converterenggine;

import com.bitozen.wms.report.common.exception.UnknownFileTypeException;
import java.io.File;



/**
 * Interface for tools that convert an input file to a PDF/A document.
 * 
 * @author dan179
 */
public interface PdfaConvertable {

	/**
	 * Convert the input file to a PDF/A. Converted file remains in configured output directory.
	 * If method not implemented by class then this default implementation will be used.
	 * 
	 * @param inputFile The file to convert.
	 * @return PdfaConverterOutput - Contains the input converted to PDF/A and other relevant data.
	 * @throws GeneratedFileUnavailableException (RuntimeException) - If the generated file is either unavailable or unreadable.
	 * @throws ExternalToolException - When there is a problem with the external tool being executed.
	 * @throws UnknownFileTypeException - The input file cannot be converted to PDF/A.
	 */
	default PdfaConverterOutput convert(File inputFile) {
		String msg = "Cannot process this file type: " + inputFile.getAbsolutePath();
		throw new UnknownFileTypeException(msg);
	};

	/**
	 * Convert the input file to a PDF/A, deleting converted file if requested.
	 * If method not implemented by class then this default implementation will be used.
	 * 
	 * @param inputFile The file to convert.
	 * @param deleteConvertedFile <code>true</code> delete the converted file once returned; <code>false</code> leave 
	 * 			converted file in configured output directory.
	 * @return PdfaConverterOutput - Contains the input converted to PDF/A and other relevant data.
	 * @throws GeneratedFileUnavailableException (RuntimeException) - If the generated file is either unavailable or unreadable.
	 * @throws ExternalToolException - When there is a problem with the external tool being executed.
	 * @throws UnknownFileTypeException - The input file cannot be converted to PDF/A.
	 */
	default PdfaConverterOutput convert(File inputFile, boolean deleteConvertedFile) {
		String msg = "Cannot process this file type: " + inputFile.getAbsolutePath();
		throw new UnknownFileTypeException(msg);
	};

}