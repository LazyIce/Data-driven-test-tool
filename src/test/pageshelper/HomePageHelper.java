package test.pageshelper;

import org.apache.log4j.Logger;

import test.pages.HomePage;
import test.utils.SeleniumUtil;

/**
 * @desciption 首页帮助类：专门提供在这个页面进行操作的方法封装
 */
public class HomePageHelper {
    // 提供本类中日志输出对象
    public static Logger logger = Logger.getLogger(HomePageHelper.class);

    /**
     * @description 等待首页元素加载
     * @param seleniumUtil
     *            selenium api封装引用对象
     * @param timeOut
     *            等待元素超时的时间
     * */
    public static void waitHomePageLoad(SeleniumUtil seleniumUtil, int timeOut) {

        logger.info("开始等待首页元素加载");
        seleniumUtil.waitForElementToLoad(timeOut, HomePage.HP_BUTTON_SEARCH);
        seleniumUtil.waitForElementToLoad(timeOut, HomePage.HP_INPUT_SEARCH);
        logger.info("首页元素加载完毕");
    }

    public static void typeSearchInfo(SeleniumUtil seleniumUtil, String keyword) {

        logger.info("开始输入登录信息");
        // 清空用户名输入框
        seleniumUtil.clear(HomePage.HP_INPUT_SEARCH);
        // 输入用户名到用户名输入框
        seleniumUtil.type(HomePage.HP_INPUT_SEARCH, keyword);
        logger.info("输入登录信息完毕");
        // 点击登录按钮
        seleniumUtil.click(HomePage.HP_BUTTON_SEARCH);

    }


}
