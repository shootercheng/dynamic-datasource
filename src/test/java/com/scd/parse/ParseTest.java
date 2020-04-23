package com.scd.parse;

import com.scd.model.vo.Label;
import org.osource.scd.constant.ParseType;
import org.osource.scd.param.ParseParam;
import org.osource.scd.parse.FileParse;
import org.osource.scd.parse.FileParseCreateor;
import org.osource.scd.utils.AnnotationUtil;
import org.osource.scd.utils.ExcelUtil;
import org.osource.scd.utils.FileParseCommonUtil;

import java.util.List;

/**
 * @author James
 */
public class ParseTest {

    public static void main(String[] args) {
        String excelPath = "file/excel/test.xlsx";
        ParseParam parseParam = new ParseParam()
                .setStartLine(1)
                .setFieldSetterMap(AnnotationUtil.findOneSheetSetter(Label.class))
                .setParseType(ParseType.EASYEXCEL);
        FileParse fileParse = FileParseCreateor.createFileParse(
                FileParseCommonUtil.findParserType(excelPath, parseParam));
        List<Label> labelList = fileParse.parseFile(excelPath, Label.class, parseParam);
        System.out.println(labelList);
    }
}
