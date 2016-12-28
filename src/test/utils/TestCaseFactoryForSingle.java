package test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

/**
 *
 * @author xy-incito-wy
 * @Description 自动生成测试代码的工具类，生成指定模块的用例
 *
 */
public class TestCaseFactoryForSingle {
    public static void main(String[] args) {
        //测试代码包路径
        final String caseFolder = "test/testcases";
        //源文件
        File sourceFile = null;
        //sheet的名字
        String sheetName = null;
        //功能模块名字
        String functionName = null;
        //sheet的号码
        int sheetNum = 0;

        try {
            @SuppressWarnings("resource")
            //从控制台可以输入
                    Scanner s = new Scanner(System.in);
            System.out.println("请输入模块名称（不要按回车键，输入完成之后请再按回车键）：");
            functionName = s.nextLine();// 输入模块名字

            functionName = functionName.replaceFirst(
                    functionName.substring(0, 1),
                    functionName.substring(0, 1).toLowerCase());
            // 如果包名不存在，就新建
            File functionPackage = new File(caseFolder + "/" + functionName);
            if (functionPackage.exists()) {
                System.out.println(functionName + "包已经存在，自动跳过！");
                System.out.println("正在生成用例到" + functionName + "包下，请稍等...");
            } else {
                functionPackage.mkdir();
                System.out.println(functionName + "包已创建！");
                System.out.println("正在生成用例到" + functionName + "包下，请稍等...");
            }

            for (int j = 0; j < getSheetNum(getExcelRelativePath(functionName)); j++) {
                try {
                    sheetName = getSheetName(j, getExcelRelativePath(functionName));

                    sheetNum = getSheetNum(getExcelRelativePath(functionName));
                } catch (BiffException e1) {
                    e1.printStackTrace();
                }
                sourceFile = new File(caseFolder
                        + functionName.toLowerCase()
                        + File.separator
                        + functionName.replaceFirst(functionName.substring(
                        0, 1), functionName.substring(0, 1)
                        .toUpperCase()) + "Page_" + sheetName
                        + "_Test.java");// 创建测试用例源码，指定存放路径
                FileWriter writer = new FileWriter(sourceFile);

                // 生成测试用例代码的头文件
                writer.write("package test.testcases."
                        + functionName
                        + "; \n"
                        + "import org.testng.annotations.Test; \n"
                        + "import com.demo.test.base.BaseParpare; \n "
                        + "import com.demo.test.utils.SuperAction; \n"
                        + "public class "
                        + functionName.replaceFirst(functionName.substring(
                        0, 1), functionName.substring(0, 1)
                        .toUpperCase()) + "Page_" + sheetName
                        + "_Test extends BaseParpare{ \n");

                // @Test的主体部分，也就是测试用例的方法
                String firstLetter = sheetName.substring(
                        sheetName.indexOf("_") + 1).substring(0, 1);
                String others = sheetName.substring(
                        sheetName.indexOf("_") + 1).substring(1);
                String function = firstLetter.toLowerCase() + others;
                writer.write("@Test \n"
                        + " public void"
                        + " "
                        + function
                        + "() { \n"
                        + "SuperAction.parseExcel(\""
                        + functionName.replaceFirst(functionName.substring(
                        0, 1), functionName.substring(0, 1)
                        .toUpperCase()) + "\",\"" + sheetName
                        + "\",seleniumUtil);\n" + " }\n");

                // 代码结尾大括号
                writer.write("}");
                writer.close();
            }
        } catch (IOException e) {
            Assert.fail("IO异常", e);
        }
        System.out.println("模块[" + functionName + "] 的用例已经生成完毕，共计："
                + (sheetNum - 1) + "条，请到" + caseFolder
                + functionName.toLowerCase() + "路径下查阅！");

    }


    /**
     * 获得excel的相对路径
     *
     * @param functionName
     * @return 得到对应index的模块名字
     */
    public static String getExcelRelativePath(String functionName) {
        String dir = "resources/testcase";
        String path = "";
        File file = new File(dir+"/"+functionName+".xls");
        path = file.getPath();
        return path;
    }

    /**
     * 获得当前excel的sheet数量 - 每个模块的用例数
     *
     * @param filePath
     *            文件路径
     * @return 获得excel的sheet数量
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static int getSheetNum(String filePath) throws FileNotFoundException, IOException {
        int casesNum = 0;
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
        casesNum = workbook.getNumberOfSheets();

        return casesNum;
    }

    /**
     *
     * @param sheetIndex
     *            sheet的位置
     * @param filePath
     *            excel文件路径相对的
     * @return 返回sheet的名字
     * @throws BiffException
     * @throws IOException
     */
    public static String getSheetName(int sheetIndex, String filePath)
            throws BiffException, IOException {
        String casesName = "";
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));
        casesName = workbook.getSheetName(sheetIndex);

        return casesName;

    }

}
