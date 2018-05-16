package com.bp.common;
/**
* @author zyk
* @version 创建时间：2017年8月17日 下午6:49:53
* 列表导航帮助   类说明
*/
public class PagerHelper {
	/**
	 * 转化导航链接
	 * @param intCounts 总记录数
	 * @param intPageSizes 每页显示数量
	 * @param intPageCounts 总共页数
	 * @param intThisPages 当前页数编号
	 * @param strUrl 导航URL地址
	 * @return
	 */
	public static String strPage(int intCounts, int intPageSizes, int intPageCounts, int intThisPages, String strUrl)
    {
        int intCount = intCounts; //总记录数
        int intPageCount = intPageCounts; //总共页数
        int intPageSize = intPageSizes; //每页显示数量
        int intPage = 7;  //数字显示
        int intThisPage =intThisPages; //当前页数
        int intBeginPage = 0; //开始页数
        int intCrossPage = 0; //变换页数
        int intEndPage = 0; //结束页数
        String strPage = null; //返回值

        intCrossPage = intPage / 2;
            strPage = "共 <font color=\"#FF0000\">" + Integer.toString(intCount) + "</font> 条记录 第 <font color=\"#FF0000\">" + Integer.toString(intThisPage) + "/" + Integer.toString(intPageCount) + "</font> 页 每页 <font color=\"#FF0000\">" + Integer.toString(intPageSize) + "</font> 条 &nbsp;&nbsp;&nbsp;&nbsp;";
            if (intThisPage > 1)
            {
                strPage = strPage + "<a href=\"" + strUrl + "1\">首页</a> ";
                strPage = strPage + "<a href=\"" + strUrl + Integer.toString(intThisPage - 1) + "\">上一页</a> ";
            }
            if (intPageCount > intPage)
            {
                if (intThisPage > intPageCount - intCrossPage)
                {
                    intBeginPage = intPageCount - intPage + 1;
                    intEndPage = intPageCount;
                }
            else
            {
                if (intThisPage <= intPage - intCrossPage)
                {
                    intBeginPage = 1;
                    intEndPage = intPage;
                }
                else
                {
                    intBeginPage = intThisPage - intCrossPage;
                    intEndPage = intThisPage + intCrossPage;
                }
            }
        }
        else
        {
            intBeginPage = 1;
            intEndPage = intPageCount;
        }
        if (intCount > 0)
        {

            for (int i = intBeginPage; i <= intEndPage; i++)
            {
                if (i == intThisPage)
                {
                    strPage = strPage + " <font color=\"#FF0000\">" + Integer.toString(i) + "</font> ";
                }
                else
                {
                    strPage = strPage + " <a href=\"" + strUrl + Integer.toString(i) + "\" title=\"第" + Integer.toString(i) + "页\">" + Integer.toString(i) + "</a> ";
                }
            }
        }
        if (intThisPage < intPageCount)
        {
            strPage = strPage + "<a href=\"" + strUrl + Integer.toString(intThisPage + 1) + "\">下一页</a> ";
            strPage = strPage + "<a href=\"" + strUrl + Integer.toString(intPageCount) + "\">尾页</a> ";
        }
        return strPage;
    }
}
