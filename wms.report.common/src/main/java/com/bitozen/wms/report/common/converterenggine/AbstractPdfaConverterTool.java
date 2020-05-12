package com.bitozen.wms.report.common.converterenggine;

import com.bitozen.wms.report.common.exception.ExternalToolException;
import com.bitozen.wms.report.common.exception.GeneratedFileUnavailableException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public abstract class AbstractPdfaConverterTool implements PdfaConvertable {
	
	// the sub-directory within the output directory for storing converted documents
	private File outputDir;

	private static final Logger logger = LogManager.getLogger();

	protected AbstractPdfaConverterTool(File outputDir) {
		super();
		this.outputDir = outputDir;
	}
	
	abstract protected String getToolName();
	

	protected String getOutputDirectory() {
		return outputDir.getAbsolutePath();
	}
	

	protected ByteArrayOutputStream processCommand(List<String> cmd, File directory) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ProcessBuilder builder = new ProcessBuilder(cmd);
			if(directory != null) {
				builder.directory(directory);
			}
			Process proc = builder.start();
	
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(),bos);
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(),bos);
			errorGobbler.start();
			outputGobbler.start();
			proc.waitFor();
		    errorGobbler.join();
		    outputGobbler.join();
		    bos.flush();
		    int exitCode = proc.exitValue();
		    if (exitCode != 0) {
		    	throw new ExternalToolException("Error executing external command line tool: " + getToolName() + " -- with exit code: " + exitCode);
		    }
		}
		catch (IOException | InterruptedException e) {
			throw new ExternalToolException("Error executing external command line tool: " + getToolName(), e);
		}
		finally {
			try {
				bos.close();
			} catch (IOException e) {
				// nothing really to do except log and proceed
				logger.error("Couldn't close ByteArrayOutputStream", e);;
			}
		}
		return bos;
	}

	/*
	 * Log output from application performing the conversion, appending output if file already exists.
	 */
	protected void logApplicationOutput(String outputFilePath, ByteArrayOutputStream baos) {
		try {
			FileOutputStream outFile = new FileOutputStream(outputFilePath, true);
			outFile.write(baos.toByteArray());
			outFile.flush();
			outFile.close();
		} catch(IOException ioe) {
			logger.error("Problem writing to application logging output:", ioe);
		}
	}
	
	/*
	 * Retrieve the tool's output for the currently converted document.
	 */
	protected String getToolLoggingOutput(ByteArrayOutputStream baos) {
		StringWriter sw = new StringWriter();
		sw.append(baos.toString());
		return sw.toString();
	}

	/**
	 * Returns the converted file for the given input.
	 * 
	 * @param outputFilename - The file name to set on the converted file.
	 * @param deleteConvertedFile - Delete the converted file from file system on JVM termination.
	 * @throws GeneratedFileUnavailableException If the file is not available to be returned.
	 */
	protected File retrieveGeneratedFile(String outputFilename, boolean deleteConvertedFile) {
		String filePath = getOutputDirectory() + File.separator + outputFilename;
		File generatedFile = new File(filePath);
		if ( !generatedFile.isFile() || !generatedFile.canRead()) {
			throw new GeneratedFileUnavailableException("The generated file [" + generatedFile + "] is not available to be returned.");
		}
		if (deleteConvertedFile) {
			generatedFile.deleteOnExit();
		}
		return generatedFile;
	}

	protected String getGeneratedPdfFilename(File inputFile) {
		String outputFilenameBase = inputFile.getName().substring(0, inputFile.getName().indexOf('.'));
		String generatedPdfFilename = outputFilenameBase + ".pdf";
        logger.debug("outputFilename: {}", generatedPdfFilename);
		return generatedPdfFilename;
	}
}
