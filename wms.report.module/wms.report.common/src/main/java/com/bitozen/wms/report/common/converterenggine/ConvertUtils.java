package com.bitozen.wms.report.common.converterenggine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rey
 */
@Slf4j
public class ConvertUtils {

    @Autowired
    public static ObjectMapper objectMapper;

    public static String getConvertIntegerToWord(int number) {
        String[] words = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh",
            "Sebelas"};
        String result = "";
        if (number < 12) {
            result = result + words[number];
        } else if (number < 20) {
            result = result + getConvertIntegerToWord(number - 10) + " Belas";
        } else if (number < 100) {
            result = result + getConvertIntegerToWord(number / 10) + " Puluh " + getConvertIntegerToWord(number % 10);
        } else if (number < 200) {
            result = result + "Seratus " + getConvertIntegerToWord(number - 100);
        } else if (number < 1000) {
            result = result + getConvertIntegerToWord(number / 100) + " Ratus " + getConvertIntegerToWord(number % 100);
        } else if (number < 2000) {
            result = result + "Seribu " + getConvertIntegerToWord(number - 1000);
        } else if (number < 1000000) {
            result = result + getConvertIntegerToWord(number / 1000) + " Ribu " + getConvertIntegerToWord(number % 1000);
        } else if (number < 1000000000) {
            result = result + getConvertIntegerToWord(number / 1000000) + " Juta " + getConvertIntegerToWord(number % 1000000);
        }
        return result;
    }

    public static String convertDateToWord(Date date) {
        String result = "";
        Locale id = new Locale("in", "ID");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MMMM", id);
        SimpleDateFormat formatYears = new SimpleDateFormat("YYYY");
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", id);
        simpleDateformat.format(date);
        result = simpleDateformat.format(date);
        result = result + " Tanggal " + getConvertIntegerToWord(Integer.parseInt(formatDay.format(date)));
        result = result + " Bulan " + formatMonth.format(date);
        result = result + " Tahun " + getConvertIntegerToWord(Integer.parseInt(formatYears.format(date)));
        return result;
    }

    public static String convertDayToWord(Date date) {
        Locale id = new Locale("in", "ID");
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", id);
        return simpleDateformat.format(date);
    }

    public static String getPostitionESByOuid(String obj, String ouid) {
        String path = "$..[?(@.ouid == " + "'" + ouid + "'" + ")].ouposition.bizparValue";
        if (obj != null && !obj.isEmpty()) {
            try {
                List<String> tempt = JsonPath.read(obj, path);
                return (String) tempt.get(0);
            } catch (Exception ex) {
                log.info(ex.getMessage());
                return null;
            }
        }
        return null;
    }

    public static String getLevelESByOuid(String obj, String ouid) {
        String path = "$..[?(@.ouid == " + "'" + ouid + "'" + ")].oulevel.bizparValue";
        if (obj != null && !obj.isEmpty()) {
            try {
                List<String> tempt = JsonPath.read(obj, path);
                return (String) tempt.get(0);
            } catch (Exception ex) {
                log.info(ex.getMessage());
                return null;
            }
        }
        return null;
    }

    public static String getJobDescESByOuid(String obj, String ouid) {
        String path = "$..[?(@.ouid == " + "'" + ouid + "'" + ")].oujobDescription";
        if (obj != null && !obj.isEmpty()) {
            try {
                List<String> tempt = JsonPath.read(obj, path);
                return (String) tempt.get(0);
            } catch (Exception ex) {
                log.info(ex.getMessage());
                return null;
            }
        }
        return null;
    }

    public static String getMovementType(String obj, String movementid) {
        String type = obj.substring(obj.indexOf("KE") + 3);
        return type;
    }

    public static String convertDataLocal(Date date) {
        Locale id = new Locale("in", "ID");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd MMMM YYYY", id);
        return formatDay.format(date);
    }

    public static String getListPostitionESByOuid(String obj, String ouid) {
        String path = "$..[*]";
        List<String> tempt = JsonPath.read(ouid, path);
        List<String> asd = new ArrayList();
        for (int i = 0; tempt.size() > i; i++) {
            asd.add(getPostitionESByOuid(obj, tempt.get(i)));
        }
        return String.join(", ", asd);

    }
    
}
