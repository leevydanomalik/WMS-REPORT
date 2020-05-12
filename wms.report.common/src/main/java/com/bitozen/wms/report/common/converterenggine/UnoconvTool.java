package com.bitozen.wms.report.common.converterenggine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Java wrapper for Unoconv tool (which calls LibreOffice) for converting .doc, .docx, .odt, .rtf and .wpd documents into PDF/A.
 * 
 * @author dan179
 */
public class UnoconvTool extends AbstractPdfaConverterTool {

	private List<String> unixCommand = new ArrayList<String>();

	private static final String TOOL_NAME = "UnoconvTool";
	private static final String TOOL_LOG_FILE_NAME = "unoconv-output.txt";
	private static final String UNOCONV_COMMAND = "unoconv";

	private static final Logger logger = LogManager.getLogger();

	public UnoconvTool(String unoconvHome, File outputDir) {
		super(outputDir);
		logger.debug("Entering C-tor for: {}", UnoconvTool.class.getSimpleName());
        
        File unoconvDir = new File(unoconvHome);
        logger.info("Unoconv home: {} -- isDirectory: ", unoconvHome, (unoconvHome == null ? "false" : unoconvDir.isDirectory()) );

		String command = unoconvHome + File.separatorChar + UNOCONV_COMMAND;
		logger.info("Have command: {}", command);
		unixCommand.add(command);
	}

	@Override
	protected String getToolName() {
		return TOOL_NAME;
	}

	/**
	 * @see edu.harvard.hul.ois.drs.pdfaconvert.tools.PdfaConvertable#convert(java.io.File)
	 */
	@Override
	public PdfaConverterOutput convert(File inputFile) {
		return convert(inputFile, false);
	}

	/**
	 * @see edu.harvard.hul.ois.drs.pdfaconvert.tools.PdfaConvertable#convert(java.io.File, boolean)
	 */
	@Override
	public PdfaConverterOutput convert(File inputFile, boolean deleteConvertedFile) {
        logger.debug("{}.convert() starting on file: [{}]", TOOL_NAME, inputFile);
        logger.debug("file exists: {}", inputFile.exists());
        logger.debug("file absolute path: {}", inputFile.getAbsolutePath());

        String generatedPdfFilename = getGeneratedPdfFilename(inputFile);
		List<String> execCommand = new ArrayList<String>();
		execCommand.addAll(unixCommand);
		execCommand.add("-vv"); // for verbosity
		execCommand.add("-f"); // PDF output format
		execCommand.add("pdf");
		execCommand.add("-eSelectPdfVersion=1"); // PDF/A output
		execCommand.add("-o"); // output location - directory or filename
		execCommand.add( getOutputDirectory() + File.separator + generatedPdfFilename);
		execCommand.add(inputFile.getAbsolutePath());

		logger.debug("About to launch {}, command: {}", TOOL_NAME, execCommand);
		ByteArrayOutputStream baos = processCommand(execCommand, null);
		String logFilename = getOutputDirectory() + File.separator + TOOL_LOG_FILE_NAME;
		logApplicationOutput(logFilename, baos);
		
		File pdfaOutputFile = retrieveGeneratedFile(generatedPdfFilename, deleteConvertedFile);
		String toolOutput = getToolLoggingOutput(baos);
		PdfaConverterOutput converterOutput = new PdfaConverterOutput(pdfaOutputFile, toolOutput);		
		logger.debug("Finished running {}", TOOL_NAME);
		return converterOutput;
	}

}
