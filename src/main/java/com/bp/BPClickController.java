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
* @version 创建时间：2017年9月11日 上午10:04:04
* 快速埋点注册控制器  类说明
*/
@Controller
public class BPClickController extends BPBaseController {
	@RequestMapping(value = "/BPClick/Index/{pageIndex}", method = RequestMethod.GET)
	public ModelAndView BPClickIndex(HttpServletRequest request, @PathVariable int pageIndex) {
		ModelAndView mv = new ModelAndView("/BPClick/Index");
		//获取当前用户
		BPSystemUserModel user=GetCurrentUser(request);
		String SpeedTitle = request.getParameter("txtSpeedTitle") != null ? request.getParameter("txtSpeedTitle").trim() : "";
		List<BPSpeedModel> lst = null;
		// 分页查询
		int intThisPage = pageIndex == 0 ? 1 : pageIndex; // 当前页数
		int intPageSize = 15; // 每页显示数量
		int intCount = 0; // 总记录数
		int intPageCount = 0; // 总共页数
		int currentIndex = (intThisPage - 1) * intPageSize;// 当前记录索引
		Integer[] backRowCount = new Integer[1];
		lst=getBPRegist().GetBPSpeedList(SpeedTitle, user.UserId, intThisPage, intPageSize, user.IsAdmin==1, backRowCount);
		intCount = backRowCount[0];
		intPageCount = (int) Math.ceil(intCount * 1.0 / intPageSize);// 计算总页数
		String strUrl = request.getContextPath() + "/BPClick/Index/";
		String pageNav = PagerHelper.strPage(intCount, intPageSize, intPageCount, intThisPage, strUrl);
		mv.addObject("BPSpeedLst", lst);
		mv.addObject("pageNav", pageNav);
		mv.addObject("currentIndex", currentIndex);// 记录当前索引数
//		mv.addObject("ContextPath", request.getContextPath());
		return mv;
	}
	/**
	 * 快速埋点注册
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
		//快速埋点注册
		model=getBPRegist().AddBPSpeedPoints(model);
		//重新刷新页面
		if(model!=null)
		{
			mv=ResultInfoCommon.SuccessJson("快速埋点注册成功！");
			//记录操作日志
			String msg = String.format("用户ID：%s 用户名称：%s,快速埋点注册，埋点标题：%s,埋点参数：%s--%s--%s", user.BPUserId, user.UserName, model.getSpeedTitle(),model.getExtendParams01(),model.getExtendParams02(),model.getExtendParams03());
			WriterLog(msg);
		}
		else
		{
			mv=ResultInfoCommon.FailJson("快速埋点注册失败，请联系管理员");
		}
		return mv;
	}
	/**
	 * 删除快速埋点
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
		//重新刷新页面
		if(isSuccess)
		{
			//更新缓存中快速埋点信息
			List<BPSpeedModel> lst=getBPRegist().GetBPSpeedBySpeedId(speedId);
			if(lst!=null&&lst.size()>0){
				BPSpeedModel m=lst.get(0);
				BPFlg.RemoveBPSpeedFromCache(m.getExtendParams01());
			}
			mv=ResultInfoCommon.SuccessJson("删除快速埋点成功！");
			//记录操作日志
			String msg = String.format("用户ID：%s 用户名称：%s,删除快速埋点，埋点ID：%s,",user.BPUserId,user.UserName,speedId);
			WriterLog(msg);
		}
		else
		{
			mv=ResultInfoCommon.FailJson("删除快速埋点失败，请联系管理员！");
		}
		return mv;
	}
	/**
	 * 核对快速埋点参数是否已注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/BPClick/CheckParms", method = RequestMethod.POST)
	public @ResponseBody int BPClickCheckParms(HttpServletRequest request) {
		String parsType=request.getParameter("parsType");
		String parsValue=request.getParameter("parsValue");
		boolean isSuccess=false;
		//数据校验
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
