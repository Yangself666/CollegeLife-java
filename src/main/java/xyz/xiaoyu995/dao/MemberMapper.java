package xyz.xiaoyu995.dao;

import xyz.xiaoyu995.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 通过微信获得的openid进行查询成员，如果数据库中没有相应的openid，就进行创建操作
 * sid是使用UUID作为唯一码，防止重复
 * sName和sNumber分别是姓名和学号，应该从全部学生中进行查询的，但是由于没有全校学生信息，所以这边就随便设置
 * cid班级代码，在注册的时候进行选择，后续可进行更改
 */
public interface MemberMapper {
    /**
     * 查询所有的成员信息(管理成员时可以使用)（ALL）
     *
     * @return
     */
    List<Member> queryAllMembers();

    /**
     * 根据班级ID查询所有的成员信息（管理成员时可以使用）（班级单位）
     *
     * @return
     */
    List<Member> queryAllMembersByCid(Integer cid);

    /**
     * 根据学院ID查询所有的成员信息（管理成员时可以使用）（学院单位）
     *
     * @param coid
     * @return
     */
    List<Member> queryAllMembersByCoid(Integer coid);

    /**
     * 根据用户的openid查找用户
     *
     * @param openid
     * @return
     */
    Member queryMemberByOpenid(String openid);

    Member queryMemberBySid(String sid);

    /**
     * 根据用户的openid查找用户详细信息
     *
     * @param openid
     * @return
     */
    AllMember queryEntireMemberByOpenid(String openid);

    /**
     * 根据班级cid查找出相应的班级信息
     *
     * @param cid
     * @return
     */
    Classes queryClassByCid(Integer cid);

    /**
     * 根据班级cid查询班级的名称
     *
     * @param cid
     * @return
     */
    String queryClassNameByCid(Integer cid);


    //////////////////////////////////////////////////////////////////

    /**
     * 通过sid查询数据库中是否有位置信息
     * @param sid
     * @return
     */
    Location queryLocationBySid(String sid);

    /**
     * 查询所有成员的位置信息
     *
     * @return
     */
    List<Location> queryAllLocations();

    /**
     * 根据班级cid查询成员的位置信息
     *
     * @param cid
     * @return
     */
    List<Location> queryAllLocationsByCid(Integer cid);

    /**
     * 根据学院coid查询成员的位置信息
     *
     * @param coid
     * @return
     */
    List<Location> queryAllLocationsByCoid(Integer coid);

    ////////////////////////////////////////////////////////////
    //接下来是插入数据的操作

    /**
     * 添加一个新成员
     *
     * @param member
     */
    void addMember(Member member);

    /**
     * 增加成员位置信息
     *
     * @param tempLocation
     */
    void addLocation(TempLocation tempLocation);

    /**
     * 修改成员位置信息
     *
     * @param tempLocation
     */
    void alterLocation(TempLocation tempLocation);

    /**
     * 修改成员姓名学号班级信息
     * @param member
     */
    void alterMember(Member member);


    void alterClassNotice(Map<String,Object> map);

    void alterCollegeNotice(Map<String,Object> map);


    //================alterLevel相关操作=================
    AlterLevel queryAlterLevelBySid(String sid);

    void delAlterLevelBySid(String sid);

    void addAlterLevel(AlterLevel alterLevel);

    void updateAlterLevel(AlterLevel alterLevel);

    void alterLevelBySid(Map<String,Object> map);


    List<AlterLevel> queryClassApplyByCid(Integer cid);

    List<AlterLevel> queryCollegeApply();
    List<AlterLevel> queryAllApply();
}
