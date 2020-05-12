
package com.bitozen.wms.report.svc.controller.convertrtftopdf;

import com.bitozen.wms.report.common.converterenggine.PdfaConvertable;
import com.bitozen.wms.report.common.converterenggine.PdfaConverterOutput;
import com.bitozen.wms.report.common.converterenggine.UnoconvTool;
import com.bitozen.wms.report.common.exception.UnknownFileTypeException;
import java.io.File;  
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PdfConvertService {

    private static final String DOC_TYPE = "doc";
    private static final String DOCM_TYPE = "docm";
    private static final String DOCX_TYPE = "docx"; 
    private static final String ODT_TYPE = "odt"; 
    private static final String RTF_TYPE = "rtf";
    private static final String WP_TYPE = "wp";
    private static final String WPD_TYPE = "wpd"; 

    public PdfaConverterOutput examine(File inputFile,String unoconvHome,String outputDirFile) {
        return examine(inputFile, false,  unoconvHome, outputDirFile);
    }

    public PdfaConverterOutput examine(File inputFile, boolean deleteConvertedFile , String unoconvHome,String outputDirFile) {
        if (inputFile == null) {
            log.error("Invalid null file -- no-op");
            throw new IllegalArgumentException("inputFile parameter is null.");
        }

        String path = inputFile.getPath().toLowerCase();
        String ext = path.substring(path.lastIndexOf(".") + 1);

        PdfaConvertable converter;

        switch (ext) {
            case DOC_TYPE:
            case DOCM_TYPE:
            case DOCX_TYPE:
            case ODT_TYPE:
            case RTF_TYPE:
            case WP_TYPE:
            case WPD_TYPE:
                converter = new UnoconvTool(unoconvHome, new File(outputDirFile));
                break; 
            default:
                throw new UnknownFileTypeException("File type unknown. Cannot process: " + inputFile.getName());
        }
        PdfaConverterOutput output = converter.convert(inputFile, deleteConvertedFile);
        return output;
    }
    
}
