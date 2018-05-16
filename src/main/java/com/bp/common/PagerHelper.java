package com.bp.common;
/**
* @author zyk
* @version ����ʱ�䣺2017��8��17�� ����6:49:53
* �б���������   ��˵��
*/
public class PagerHelper {
	/**
	 * ת����������
	 * @param intCounts �ܼ�¼��
	 * @param intPageSizes ÿҳ��ʾ����
	 * @param intPageCounts �ܹ�ҳ��
	 * @param intThisPages ��ǰҳ�����
	 * @param strUrl ����URL��ַ
	 * @return
	 */
	public static String strPage(int intCounts, int intPageSizes, int intPageCounts, int intThisPages, String strUrl)
    {
        int intCount = intCounts; //�ܼ�¼��
        int intPageCount = intPageCounts; //�ܹ�ҳ��
        int intPageSize = intPageSizes; //ÿҳ��ʾ����
        int intPage = 7;  //������ʾ
        int intThisPage =intThisPages; //��ǰҳ��
        int intBeginPage = 0; //��ʼҳ��
        int intCrossPage = 0; //�任ҳ��
        int intEndPage = 0; //����ҳ��
        String strPage = null; //����ֵ

        intCrossPage = intPage / 2;
            strPage = "�� <font color=\"#FF0000\">" + Integer.toString(intCount) + "</font> ����¼ �� <font color=\"#FF0000\">" + Integer.toString(intThisPage) + "/" + Integer.toString(intPageCount) + "</font> ҳ ÿҳ <font color=\"#FF0000\">" + Integer.toString(intPageSize) + "</font> �� &nbsp;&nbsp;&nbsp;&nbsp;";
            if (intThisPage > 1)
            {
                strPage = strPage + "<a href=\"" + strUrl + "1\">��ҳ</a> ";
                strPage = strPage + "<a href=\"" + strUrl + Integer.toString(intThisPage - 1) + "\">��һҳ</a> ";
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
                    strPage = strPage + " <a href=\"" + strUrl + Integer.toString(i) + "\" title=\"��" + Integer.toString(i) + "ҳ\">" + Integer.toString(i) + "</a> ";
                }
            }
        }
        if (intThisPage < intPageCount)
        {
            strPage = strPage + "<a href=\"" + strUrl + Integer.toString(intThisPage + 1) + "\">��һҳ</a> ";
            strPage = strPage + "<a href=\"" + strUrl + Integer.toString(intPageCount) + "\">βҳ</a> ";
        }
        return strPage;
    }
}