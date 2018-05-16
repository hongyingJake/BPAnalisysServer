package com.bp;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bp.bll.BPFlg;
import com.bp.bll.BPRegistDAL;
import com.bp.common.LogEnum;
import com.bp.common.LogWritter;
import com.bp.common.PagerHelper;
import com.bp.common.ResultInfoCommon;
import com.bp.models.BPSpeedModel;
import com.bp.models.BPSystemUserModel;

/**
* @author zyk
* @version ����ʱ�䣺2017��9��11�� ����10:04:04
* �������ע�������  ��˵��
*/
@Controller
public class BPClickController extends BPBaseController {
	@RequestMapping(value = "/BPClick/Index/{pageIndex}", method = RequestMethod.GET)
	public ModelAndView BPClickIndex(HttpServletRequest request, @PathVariable int pageIndex) {
		ModelAndView mv = new ModelAndView("/BPClick/Index");
		//��ȡ��ǰ�û�
		BPSystemUserModel user=GetCurrentUser(request);
		String SpeedTitle = request.getParameter("txtSpeedTitle") != null ? request.getParameter("txtSpeedTitle").trim() : "";
		List<BPSpeedModel> lst = null;
		// ��ҳ��ѯ
		int intThisPage = pageIndex == 0 ? 1 : pageIndex; // ��ǰҳ��
		int intPageSize = 15; // ÿҳ��ʾ����
		int intCount = 0; // �ܼ�¼��
		int intPageCount = 0; // �ܹ�ҳ��
		int currentIndex = (intThisPage - 1) * intPageSize;// ��ǰ��¼����
		Integer[] backRowCount = new Integer[1];
		lst=getBPRegist().GetBPSpeedList(SpeedTitle, user.UserId, intThisPage, intPageSize, user.IsAdmin==1, backRowCount);
		intCount = backRowCount[0];
		intPageCount = (int) Math.ceil(intCount * 1.0 / intPageSize);// ������ҳ��
		String strUrl = request.getContextPath() + "/BPClick/Index/";
		String pageNav = PagerHelper.strPage(intCount, intPageSize, intPageCount, intThisPage, strUrl);
		mv.addObject("BPSpeedLst", lst);
		mv.addObject("pageNav", pageNav);
		mv.addObject("currentIndex", currentIndex);// ��¼��ǰ������
//		mv.addObject("ContextPath", request.getContextPath());
		return mv;
	}
	/**
	 * �������ע��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/BPClick/Regist", method = RequestMethod.POST)
	public @ResponseBody ModelAndView BPClickRegist(HttpServletRequest request) {
		ModelAndView mv = null;
		BPSystemUserModel user=GetCurrentUser(request);
		BPSpeedModel model=new BPSpeedModel();
		model.setSpeedTitle(request.getParameter("txtSaveTitle"));
		model.setExtendParams01(request.getParameter("params1"));
		model.setExtendParams01Title(request.getParameter("title1"));
		model.setExtendParams02(request.getParameter("params2"));
		model.setExtendParams02Title(request.getParameter("title2"));
//		model.setExtendParams03(request.getParameter("params3"));
//		model.setExtendParams03Title(request.getParameter("title3"));
		model.setRegUserId(user.UserId);
		model.setRegisterDate(new Date());
		model.setDelete(false);
		//�������ע��
		model=getBPRegist().AddBPSpeedPoints(model);
		//����ˢ��ҳ��
		if(model!=null)
		{
			mv=ResultInfoCommon.SuccessJson("�������ע��ɹ���");
			//��¼������־
			String msg = String.format("�û�ID��%s �û����ƣ�%s,�������ע�ᣬ�����⣺%s,��������%s--%s--%s", user.BPUserId, user.UserName, model.getSpeedTitle(),model.getExtendParams01(),model.getExtendParams02(),model.getExtendParams03());
			WriterLog(msg);
		}
		else
		{
			mv=ResultInfoCommon.FailJson("�������ע��ʧ�ܣ�����ϵ����Ա");
		}
		return mv;
	}
	/**
	 * ɾ���������
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/BPClick/DeleteSpeed", method = RequestMethod.POST)
	public @ResponseBody ModelAndView BPClickDeleteSpeed(HttpServletRequest request) {
		ModelAndView mv = null;
		BPSystemUserModel user=GetCurrentUser(request);
		String id=request.getParameter("SpeedId").toString();
		int speedId=Integer.parseInt(id);
		boolean isSuccess=getBPRegist().DeleteBPSpeed(speedId);
		//����ˢ��ҳ��
		if(isSuccess)
		{
			//���»����п��������Ϣ
			List<BPSpeedModel> lst=getBPRegist().GetBPSpeedBySpeedId(speedId);
			if(lst!=null&&lst.size()>0){
				BPSpeedModel m=lst.get(0);
				BPFlg.RemoveBPSpeedFromCache(m.getExtendParams01());
			}
			mv=ResultInfoCommon.SuccessJson("ɾ���������ɹ���");
			//��¼������־
			String msg = String.format("�û�ID��%s �û����ƣ�%s,ɾ��������㣬���ID��%s,",user.BPUserId,user.UserName,speedId);
			WriterLog(msg);
		}
		else
		{
			mv=ResultInfoCommon.FailJson("ɾ���������ʧ�ܣ�����ϵ����Ա��");
		}
		return mv;
	}
	/**
	 * �˶Կ����������Ƿ���ע��
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/BPClick/CheckParms", method = RequestMethod.POST)
	public @ResponseBody int BPClickCheckParms(HttpServletRequest request) {
		String parsType=request.getParameter("parsType");
		String parsValue=request.getParameter("parsValue");
		boolean isSuccess=false;
		//����У��
		isSuccess=getBPRegist().BPSpeedParsCheck(parsType,parsValue);
		if(isSuccess)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}