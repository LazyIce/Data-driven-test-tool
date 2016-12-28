package test.testcases.home;

import java.util.Map;
import org.testng.annotations.Test;
import test.base.BaseParpare;
import test.pageshelper.HomePageHelper;
/**
 * @description 登录之后验证用户名是不是正确的
 * */

public class HomePage_001_SearchSuccessFunction_Test extends BaseParpare{
    @Test
    public void loginSuccessFunction() {
        //等待首页元素显示出来
        HomePageHelper.waitHomePageLoad(seleniumUtil, timeOut);
        HomePageHelper.typeSearchInfo(seleniumUtil, "selenium");
    }
}
