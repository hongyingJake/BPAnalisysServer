package com.bp.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.bp.common.LogEnum;
import com.bp.common.LogWritter;
import com.bp.models.*;

/**
 * @author zyk
 * @version ����ʱ�䣺2017��8��8�� ����4:24:51 ���ע�����Ϣ���ݿ������ ��˵��
 */
public class BPRegistDAL {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * ע���û���Ϣ
	 * 
	 * @param model
	 * @return
	 */
	public boolean AddSystemUser(BPSystemUserModel model) {
		String sql = " insert into BPSystemUser (UserId,UserLoginName,UserLoginPwd,UserName,UserCompanyName,UserPhone,Demo,RegisterDate,IsValid,IsAdmin ) "
				+ " values(?,?,?,?,?,?,?,?,?,?) ";
		try {
			jdbcTemplate
					.update(sql,
							new Object[] { model.UserId, model.UserLoginName, model.UserLoginPwd, model.UserName,
									model.UserCompanyName, model.UserPhone, model.Demo, model.RegisterDate,
									model.IsValid ,model.IsAdmin});
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ע���û���Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	/**
	 * ��¼
	 * @param LoginName
	 * @param LoginPwd
	 * @return
	 */
	public BPSystemUserModel Login(String LoginName,String LoginPwd)
	{
		String sql=" select BPUserId,UserId,UserLoginName ,UserLoginPwd,UserName,UserCompanyName,UserPhone,Demo,RegisterDate,IsValid,IsAdmin from BPSystemUser "
				+ " where IsValid=1 and UserLoginName=? and UserLoginPwd=? ";
		try
		{
			List<BPSystemUserModel> models= jdbcTemplate.query(sql, new Object[]{LoginName,LoginPwd},new BPSystemUserMapper());
			if(models!=null&&!models.isEmpty())
			{
				return models.get(0);
			}
			else
			{
				return null;
			}
			//return jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(BPSystemUserModel.class),LoginName,LoginPwd);
		}
		catch(EmptyResultDataAccessException e)
		{
			//δ��ѯ��
			return null;
		}
		catch(IncorrectResultSizeDataAccessException e)
		{
			//��ѯ������¼
			LogWritter.LogWritterInfo(BPSystemUserModel.class, LogEnum.Error, "�û���¼-��¼��Ϣ��ѯ������δ��������¼��"+e.toString());
			return null;
		}
		catch(RecoverableDataAccessException e){
			//������ݿ�����ӳ��Ѿ��������ٴ�����
			List<BPSystemUserModel> models= jdbcTemplate.query(sql, new Object[]{LoginName,LoginPwd},new BPSystemUserMapper());
			if(models!=null&&!models.isEmpty())
			{
				return models.get(0);
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(BPSystemUserModel.class, LogEnum.Error, "�û���¼�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ͨ���û�ID��ѯ�û�
	 * @param UserId
	 * @return
	 */
	public BPSystemUserModel GetUserByUserId(String UserId)
	{
		String sql=" select BPUserId,UserId,UserLoginName ,UserLoginPwd,UserName,UserCompanyName,UserPhone,Demo,RegisterDate,IsValid,IsAdmin from BPSystemUser "
				+ " where IsValid=1 and UserId=? ";
		try
		{
			List<BPSystemUserModel> models= jdbcTemplate.query(sql, new Object[]{UserId},new BPSystemUserMapper());
			if(models!=null&&!models.isEmpty())
			{
				return models.get(0);
			}
			else
			{
				return null;
			}
			//return jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(BPSystemUserModel.class),UserId);
		}
		catch(EmptyResultDataAccessException e)
		{
			//δ��ѯ��
			return null;
		}
		catch(IncorrectResultSizeDataAccessException e)
		{
			//��ѯ������¼
			return null;
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(BPSystemUserModel.class, LogEnum.Error, "��ѯ�û���Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	private static final class BPSystemUserMapper implements RowMapper {
		public BPSystemUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			BPSystemUserModel model = new BPSystemUserModel();
			model.BPUserId=rs.getInt("BPUserId");
			model.UserId=rs.getString("UserId");
			model.UserLoginName=rs.getString("UserLoginName");
			model.UserLoginPwd=rs.getString("UserLoginPwd");
			model.UserName=rs.getString("UserName");
			model.UserCompanyName=rs.getString("UserCompanyName");
			model.UserPhone=rs.getString("UserPhone");
			model.Demo=rs.getString("Demo");
			model.RegisterDate=rs.getDate("RegisterDate");
			model.IsValid=rs.getInt("IsValid");
			model.IsAdmin=rs.getInt("IsAdmin");
			return model;
		}
	}
	
	/**
	 * ע�����������Ϣ
	 * @param model ��������BPIdֵ
	 * @return
	 */
	public BPPointsModel AddBPPoints(final BPPointsModel model)
	{
		final String sql = " insert into BPPoints (BPFlg,BPName,BPDemo,RegUserId,LastUpUserId,RegisterDate,LastUpDate,IsValid) "
				+ " values(?,?,?,?,?,?,?,?) ";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			//��������ID
			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection con)
	                    throws SQLException {
	                PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	                int z = 0;
	                ps.setString(++z, model.getBPFlg());
	                ps.setString(++z,model.getBPName());
	                ps.setString(++z, model.getBPDemo());
	                ps.setString(++z, model.getRegUserId());
	                ps.setString(++z, model.getLastUpUserId());
	                ps.setDate(++z, new java.sql.Date(model.getRegisterDate().getTime()));
	                ps.setDate(++z, new java.sql.Date(model.getLastUpDate().getTime()));
	                ps.setInt(++z, model.getIsValid());
	                return ps;
	            }
	        }, keyHolder);
			//��������������ID
			model.setBPId(keyHolder.getKey().intValue());
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ע�����������Ϣ�쳣��"+e.toString());
			return null;
		}
		return model;
	}
	
	/**
	 * �������IDɾ����㣨δ�õ���������ɾ����㣩
	 * @param BPId
	 * @return
	 */
	public boolean  DeleteBPPointsById(int BPId){
		String sql = " update BPPoints set IsValid=0  where BPId=? ";
		try {
			jdbcTemplate.update(sql,new Object[] {BPId});
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ɾ�������Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * �������������ֻ����BPName,BPDemo,LastUpDate,LastUpUserId��
	 * @param model
	 * @return
	 */
	public boolean UpdateBPPoints(BPPointsModel model)
	{
		String sql = " update BPPoints set BPName=?,BPDemo=?,LastUpDate=?,LastUpUserId=? "
				+ " where BPId=? ";
		try {
			jdbcTemplate
					.update(sql,
							new Object[] {model.getBPName(),model.getBPDemo(),model.getLastUpDate(),
									model.getLastUpUserId(),model.getBPId()}
					);
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "�������������Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	/**
	 * ͨ������ʶ���������Ϣ
	 * @param BPFlg
	 * @return
	 */
	public BPPointsModel GetBPPointsByBPFlg(String BPFlg)
	{
		String sql="  select BPId,BPFlg,BPName,BPDemo,RegUserId,LastUpUserId,RegisterDate,LastUpDate,IsValid from BPPoints "
				+ " where BPFlg=? ";
		try
		{
			List<BPPointsModel> models= jdbcTemplate.query(sql, new Object[]{BPFlg},new BPPointsMapper());
			if(models!=null&&!models.isEmpty())
			{
				return models.get(0);
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(BPSystemUserModel.class, LogEnum.Error, "ͨ������ʶ��ѯ���������Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ͨ���������ID��ȡ���������Ϣ
	 * @param BPId
	 * @return
	 */
	public BPPointsModel GetBPPointsByBPId(String BPId)
	{
		String sql="  select BPId,BPFlg,BPName,BPDemo,RegUserId,LastUpUserId,RegisterDate,LastUpDate,IsValid from BPPoints "
				+ " where BPId=? ";
		try
		{
			List<BPPointsModel> models= jdbcTemplate.query(sql, new Object[]{BPId},new BPPointsMapper());
			if(models!=null&&!models.isEmpty())
			{
				return models.get(0);
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(BPSystemUserModel.class, LogEnum.Error, "��ѯ���������Ϣ�쳣��"+e.toString());
			return null;
		}
	}

	/**
	 * ��ѯ���������Ϣ
	 * @return
	 */
	public List<BPPointsModel> GetBPPointsList(String BPName,int pageIndx,int pageSize,final Integer[] rowCount)
	{
		String sql = " select BPId,BPFlg,BPName,BPDemo,RegUserId,LastUpUserId,RegisterDate,LastUpDate,IsValid from BPPoints "
				+ " where IsValid=1 ";
		String sqlCount= "select count(*) from BPPoints where IsValid=1 ";
		int rowIndx=(pageIndx-1)*pageSize;//��ѯ��ʼ��¼����
		if(BPName!=null&&BPName.trim().length()>0)
		{
			sql+=" and BPName like '%"+BPName+"%'";
			sqlCount+=" and BPName like '%"+BPName+"%'";
		}
		sql+=" order by LastUpDate desc limit ?,? ";
		try{
			rowCount[0]=jdbcTemplate.queryForObject(sqlCount, new Object[]{}, Integer.class);
			return jdbcTemplate.query(sql,new Object[]{rowIndx,pageSize},new BPPointsMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ���������Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ģ����ѯ���������Ϣ
	 * @param BPName ���ģ����ѯ����
	 * @return
	 */
	public List<BPPointsModel> GetBPPointsListLike(String RegUserId,String BPName,int pageIndx,int pageSize,final Integer[] rowCount)
	{
		String sql = " select BPId,BPFlg,BPName,BPDemo,RegUserId,LastUpUserId,RegisterDate,LastUpDate,IsValid from BPPoints "
				+ " where IsValid=1 and RegUserId=? and BPName like '%"+BPName+"%' order by LastUpDate desc limit ?,?  ";
		int rowIndx=(pageIndx-1)*pageSize;//��ѯ��ʼ��¼����
		String sqlCount= "select count(*) from BPPoints "
				+ " where IsValid=1 and RegUserId=? and BPName like '%"+BPName+"%' ";
		try{
			rowCount[0]=jdbcTemplate.queryForObject(sqlCount, new Object[]{RegUserId}, Integer.class);
			return jdbcTemplate.query(sql,new Object[]{	RegUserId,rowIndx,pageSize},new BPPointsMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ�������ģ����ѯ��Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ��ѯ���������Ϣ
	 * @param RegUserId ע���û�ID
	 * @return
	 */
	public List<BPPointsModel> GetBPPointsListByRegUId(String RegUserId,int pageIndx,int pageSize,final Integer[] rowCount)
	{
		String sql = " select BPId,BPFlg,BPName,BPDemo,RegUserId,LastUpUserId,RegisterDate,LastUpDate,IsValid from BPPoints "
				+ "where IsValid=1 and RegUserId=? order by LastUpDate desc limit ?,? ";
		int rowIndx=(pageIndx-1)*pageSize;//��ѯ��ʼ��¼����
		String sqlCount= "select count(*) from BPPoints where IsValid=1 and RegUserId=? ";
		try{
			rowCount[0]=jdbcTemplate.queryForObject(sqlCount, new Object[]{RegUserId}, Integer.class);
			return jdbcTemplate.query(sql,new Object[] { RegUserId,rowIndx,pageSize},  new BPPointsMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ���������Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	
	/**
	 * ע������ӱ���Ϣ
	 * @param model
	 * @return
	 */
	public BPSubPointsModel AddBPSubPoints(final BPSubPointsModel model)
	{
		final String sql = " insert into BPSubPoints (BPId,BPKName,BPKValue) "
				+ " values(?,?,?) ";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
//			jdbcTemplate
//					.update(sql,
//							new Object[] {model.BPId,model.BPKName,model.BPKValue});
			//��������������ID
			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection con)
	                    throws SQLException {
	                PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	                int z = 0;
	                ps.setInt(++z, model.BPId);
	                ps.setString(++z,model.BPKName);
	                ps.setString(++z, model.BPKValue);
	                return ps;
	            }
	        }, keyHolder);
			model.BPSubPointId=keyHolder.getKey().intValue();
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ע������ӱ���Ϣ�쳣��"+e.toString());
			return null;
		}
		return model;
	}
	/**
	 * ͨ�������������ƻ�ȡ����Ϣ
	 * @param KeyName
	 * @return
	 */
	public BPSubPointsModel GetBPSubPointsByKeyName(int BPId,String KeyName)
	{
		String sql=" select BPSubPointId,BPId,BPKName,BPKValue from BPSubPoints where BPId=? and BPKName=? ";
		try
		{
			List<BPSubPointsModel> models= jdbcTemplate.query(sql, new Object[]{BPId,KeyName},new BPSubPointsMapper());
			if(models!=null&&!models.isEmpty())
			{
				return models.get(0);
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(BPSystemUserModel.class, LogEnum.Error, "ͨ�������������ƻ�ȡ����Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ��ȡ����ӱ�����Ϣ�б�
	 * @param BPId
	 * @return
	 */
	public List<BPSubPointsModel> GetBPSubPointsList(int BPId)
	{
		String sql = " select BPSubPointId,BPId,BPKName,BPKValue from BPSubPoints where BPId=? ";
		try{
		return jdbcTemplate.query(sql,new Object[] { BPId}, new BPSubPointsMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ����ӱ���Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ͨ������ʶ����ȡ����ӱ���ֵ��Ϣ
	 * @param BPFlg
	 * @return
	 */
	public List<BPSubPointsModel> GetBPSubPointsLstByBPFlg(String BPFlg)
	{
		String sql = " select BPSubPointId,s.BPId,BPKName,BPKValue from BPSubPoints s"
					+" left join BPPoints b on s.BPId=b.BPId"
					+" where b.BPFlg=? ";
		try{
		return jdbcTemplate.query(sql,new Object[] { BPFlg}, new BPSubPointsMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ����ӱ���Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	
	
	/**
	 * ɾ������ӱ���Ϣ
	 * @param BPSubPointId
	 * @return
	 */
	public  boolean DeleteBPSubPoints(int BPSubPointId)
	{
		String sql = " delete from BPSubPoints where BPSubPointId=? ";
		try{
			jdbcTemplate.update(sql,BPSubPointId);
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ɾ������ӱ���Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	/**
	 * ͨ��BPId�ͼ�����ɾ������Ϣ
	 * @param BPId
	 * @param BPKName
	 * @return
	 */
	public  boolean DeleteBPSubPoints(int BPId,String BPKName)
	{
		String sql = " delete from BPSubPoints where BPId=? and BPKName=? ";
		try{
			jdbcTemplate.update(sql,BPId,BPKName);
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ɾ������ӱ���Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	
	
	/*
	 * ��Ϊquery��������ֱ�ӷŻ�һ�����飬��������ֻ��ͨ��rowMapper��ֵ��BPPointsModel;
	 * RowMapper���Խ������е�ÿһ�з�װ���û�������࣬�����ݿ��ѯ�У�������ص��������û��Զ������������Ҫ��װ
	 */
	private static final class BPPointsMapper implements RowMapper {
		public BPPointsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			BPPointsModel model = new BPPointsModel();
			model.setBPId(rs.getInt("BPId"));
			model.setBPFlg(rs.getString("BPFlg"));
			model.setBPName(rs.getString("BPName"));
			model.setBPDemo(rs.getString("BPDemo"));
			model.setRegUserId(rs.getString("RegUserId"));
			model.setLastUpUserId(rs.getString("LastUpUserId"));
			model.setRegisterDate(rs.getDate("RegisterDate"));
			model.setLastUpDate(rs.getDate("LastUpDate"));
			model.setIsValid(rs.getInt("IsValid"));
			return model;
		}
	}
	private static final class BPSubPointsMapper implements RowMapper {
		public BPSubPointsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			BPSubPointsModel model = new BPSubPointsModel();
			model.BPId=rs.getInt("BPId");
			model.BPSubPointId=rs.getInt("BPSubPointId");
			model.BPKName=rs.getString("BPKName");
			model.BPKValue=rs.getString("BPKValue");
			return model;
		}
	}
	
	/**
	 * �������ע��
	 * @param model
	 * @return
	 */
	public BPSpeedModel AddBPSpeedPoints(final BPSpeedModel model)
	{
		final String sql = " insert into BPSpeedPoints(SpeedTitle ,extendParams01 ,extendParams01Title,extendParams02,extendParams02Title,extendParams03,extendParams03Title,RegUserId ,RegisterDate,isDelete) "
				+ " values(?,?,?,?,?,?,?,?,?,?) ";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			//��������������ID
			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection con)
	                    throws SQLException {
	                PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	                int z = 0;
	                ps.setString(++z, model.getSpeedTitle());
	                ps.setString(++z,model.getExtendParams01());
	                ps.setString(++z, model.getExtendParams01Title());
	                ps.setString(++z, model.getExtendParams02());
	                ps.setString(++z, model.getExtendParams02Title());
	                ps.setString(++z, model.getExtendParams03());
	                ps.setString(++z, model.getExtendParams03Title());
	                ps.setString(++z, model.getRegUserId());
	                ps.setDate(++z, new java.sql.Date(model.getRegisterDate().getTime()));
	                ps.setBoolean(++z, model.isDelete());
	                return ps;
	            }
	        }, keyHolder);
			model.setSpeedId(keyHolder.getKey().intValue());
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ע��������ע�����Ϣ�쳣��"+e.toString());
			return null;
		}
		return model;
	}
	/**
	 * ��ȡ����ע�������Ϣ
	 * @param bpTitle ������
	 * @param registUserId ע���û�ID
	 * @param pageIndx
	 * @param pageSize
	 * @param isAdmin �Ƿ����Ա
	 * @param rowCount
	 * @return
	 */
	public List<BPSpeedModel> GetBPSpeedList(String bpTitle,String registUserId,int pageIndx,int pageSize,boolean isAdmin,final Integer[] rowCount)
	{
		String sql = " select a.SpeedId,a.SpeedTitle ,extendParams01 ,extendParams01Title,extendParams02,extendParams02Title,extendParams03,extendParams03Title,a.RegUserId ,a.RegisterDate,a.isDelete,u.UserName from BPSpeedPoints a "
				+ " left join BPSystemUser u on u.UserId=a.RegUserId "
				+ " where isDelete=0 ";
		String sqlCount= "select count(*) from BPSpeedPoints where isDelete=0 ";
		int rowIndx=(pageIndx-1)*pageSize;//��ѯ��ʼ��¼����
		if(bpTitle!=null&&bpTitle.trim().length()>0)
		{
			sql+=" and a.SpeedTitle like '%"+bpTitle+"%'";
			sqlCount+=" and SpeedTitle like '%"+bpTitle+"%'";
		}
		if(!isAdmin){
			sql+=" and a.RegUserId ='"+registUserId+"' ";
			sqlCount+=" and RegUserId ='"+registUserId+"' ";
		}
		sql+=" order by a.RegisterDate desc limit ?,? ";
		try{
			rowCount[0]=jdbcTemplate.queryForObject(sqlCount, new Object[]{}, Integer.class);
			return jdbcTemplate.query(sql,new Object[]{rowIndx,pageSize},new BPSpeedMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ�������ע����Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ͨ���������ID��ȡ�����������
	 * @param SpeedId �������ID
	 * @return
	 */
	public List<BPSpeedModel> GetBPSpeedBySpeedId(int SpeedId)
	{
		String sql = " select a.SpeedId,a.SpeedTitle ,extendParams01 ,extendParams01Title,extendParams02,extendParams02Title,extendParams03,extendParams03Title,a.RegUserId ,a.RegisterDate,a.isDelete,u.UserName from BPSpeedPoints a "
				+ " left join BPSystemUser u on u.UserId=a.RegUserId "
				+ " where a.SpeedId=? ";
		try{
			return jdbcTemplate.query(sql,new Object[]{SpeedId},new BPSpeedMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ͨ���������ID��ȡ���������Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * �жϿ����������Ƿ��Ѿ�ע��
	 * @param parsType �������� 1 2 3�ֱ������һ�������ڶ�������
	 * @param parsValue ������ʶ��
	 * @return ����true��ʶ�Ѿ�ע��
	 */
	public  boolean BPSpeedParsCheck(String parsType,String parsValue){
		String sqlCount= "select count(*) from BPSpeedPoints where isDelete=0 ";
		if(parsType.equals("1")){
			sqlCount+=" and extendParams01=? ";
		}
		else if(parsType.equals("2")){
			sqlCount+=" and extendParams02=? ";
		}
		else if(parsType.equals("3")){
			sqlCount+=" and extendParams03=? ";
		}
		int count=jdbcTemplate.queryForObject(sqlCount, new Object[]{parsValue}, Integer.class);
		if(count>0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * ��������ѯ
	 * @author aaa
	 *
	 */
	private static final class BPSpeedMapper implements RowMapper {
		public BPSpeedModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			BPSpeedModel model = new BPSpeedModel();
			model.setSpeedId(rs.getInt("SpeedId"));
			model.setSpeedTitle(rs.getString("SpeedTitle"));
			model.setExtendParams01(rs.getString("extendParams01"));
			model.setExtendParams02(rs.getString("extendParams02"));
			model.setExtendParams03(rs.getString("extendParams03"));
			model.setExtendParams01Title(rs.getString("extendParams01Title"));
			model.setExtendParams02Title(rs.getString("extendParams02Title"));
			model.setExtendParams03Title(rs.getString("extendParams03Title"));
			model.setRegUserId(rs.getString("RegUserId"));
			model.setRegisterDate(rs.getDate("RegisterDate"));
			model.setDelete(rs.getBoolean("isDelete"));
			model.setRegUserName(rs.getString("UserName"));
			return model;
		}
	}
	
	/**
	 * ɾ������ע�����(��ɾ��)
	 * @param SpeedId ����ע�����ID
	 * @return
	 */
	public boolean DeleteBPSpeed(int SpeedId)
	{
		String sql = " update BPSpeedPoints set isDelete=1 "
				+ " where SpeedId=? ";
		try {
			jdbcTemplate
					.update(sql,
							new Object[] {SpeedId}
					);
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ɾ������ע�������Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	/**
	 * �鿴������Ƿ�ע��
	 * @param NoBuryKey ������ʶ��
	 * @return
	 */
	public  boolean BPNoBuryCheck(String NoBuryKey){
		String sqlCount= "select count(*) from NoBuryPoints where isDelete=0 and NoBuryKey=? ";
		int count=jdbcTemplate.queryForObject(sqlCount, new Object[]{NoBuryKey}, Integer.class);
		if(count>0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * ɾ�������
	 * @param NoBuryId
	 * @return
	 */
	public boolean DeleteBPNoBury(int NoBuryId)
	{
		String sql = " update NoBuryPoints set isDelete=1 "
				+ " where NoBuryId=? ";
		try {
			jdbcTemplate
					.update(sql,
							new Object[] {NoBuryId}
					);
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ɾ���������Ϣ�쳣��"+e.toString());
			return false;
		}
		return true;
	}
	/**
	 * ע�������
	 * @param model
	 * @return
	 */
	public BPNoBuryModel AddBPNoBuryPoints(final BPNoBuryModel model)
	{
		final String sql = " insert into NoBuryPoints(NoBuryKey,NoBuryInfo,NoBuryExtend,RegUserId,RegisterDate,isDelete) "
				+ " values(?,?,?,?,?,?) ";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			//��������������ID
			jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection con)
	                    throws SQLException {
	                PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
	                int z = 0;
	                ps.setString(++z, model.getNoBuryKey());
	                ps.setString(++z,model.getNoBuryInfo());
	                ps.setString(++z, model.getNoBuryExtend());
	                ps.setString(++z, model.getRegUserId());
	                ps.setDate(++z, new java.sql.Date(model.getRegisterDate().getTime()));
	                ps.setBoolean(++z, model.isDelete());
	                return ps;
	            }
	        }, keyHolder);
			model.setNoBuryId(keyHolder.getKey().intValue());
		} catch (Exception e) {
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ע���������Ϣ�쳣��"+e.toString());
			return null;
		}
		return model;
	}
	/**
	 * ������б���ѯ
	 * @param NoBuryKey ������ʶ��
	 * @param registUserId ע���û�ID
	 * @param pageIndx
	 * @param pageSize
	 * @param isAdmin �Ƿ����Ա
	 * @param rowCount ��ѯ������
	 * @return
	 */
	public List<BPNoBuryModel> GetBPNoBuryList(String NoBuryKey,String registUserId,int pageIndx,int pageSize,boolean isAdmin,final Integer[] rowCount)
	{
		String sql = " select NoBuryId,NoBuryKey,NoBuryInfo,NoBuryExtend,a.RegUserId,a.RegisterDate,a.isDelete,u.UserName  from NoBuryPoints a "
				+ " left join BPSystemUser u on u.UserId=a.RegUserId "
				+ " where a.isDelete=0 ";
		String sqlCount= "select count(*) from NoBuryPoints where isDelete=0 ";
		int rowIndx=(pageIndx-1)*pageSize;//��ѯ��ʼ��¼����
		if(NoBuryKey!=null&&NoBuryKey.trim().length()>0)
		{
			sql+=" and a.NoBuryKey like '%"+NoBuryKey+"%'";
			sqlCount+=" and NoBuryKey like '%"+NoBuryKey+"%'";
		}
		if(!isAdmin){
			sql+=" and a.RegUserId ='"+registUserId+"' ";
			sqlCount+=" and RegUserId ='"+registUserId+"' ";
		}
		sql+=" order by a.RegisterDate desc limit ?,? ";
		try{
			rowCount[0]=jdbcTemplate.queryForObject(sqlCount, new Object[]{}, Integer.class);
			return jdbcTemplate.query(sql,new Object[]{rowIndx,pageSize},new BPNoBuryMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "��ȡ�����ע����Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ͨ�������ID��ȡ�������Ϣ
	 * @param NoBuryId
	 * @return
	 */
	public List<BPNoBuryModel> GetBPNoBuryById(int NoBuryId)
	{
		String sql = " select NoBuryId,NoBuryKey,NoBuryInfo,NoBuryExtend,a.RegUserId,a.RegisterDate,a.isDelete,u.UserName  from NoBuryPoints a "
				+ " left join BPSystemUser u on u.UserId=a.RegUserId "
				+ " where a.NoBuryId=? ";
		try{
			return jdbcTemplate.query(sql,new Object[]{NoBuryId},new BPNoBuryMapper());
		}
		catch(Exception e)
		{
			LogWritter.LogWritterInfo(this.getClass(), LogEnum.Error, "ͨ��NoBuryId��ȡ�����ע����Ϣ�쳣��"+e.toString());
			return null;
		}
	}
	/**
	 * ������б���ѯ
	 * @author aaa
	 *
	 */
	private static final class BPNoBuryMapper implements RowMapper {
		public BPNoBuryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			BPNoBuryModel model = new BPNoBuryModel();
			model.setNoBuryId(rs.getInt("NoBuryId"));
			model.setNoBuryKey(rs.getString("NoBuryKey"));
			model.setNoBuryInfo(rs.getString("NoBuryInfo"));
			model.setNoBuryExtend(rs.getString("NoBuryExtend"));
			model.setRegUserId(rs.getString("RegUserId"));
			model.setRegisterDate(rs.getDate("RegisterDate"));
			model.setDelete(rs.getBoolean("isDelete"));
			model.setRegUserName(rs.getString("UserName"));
			return model;
		}
	}
	
	
}