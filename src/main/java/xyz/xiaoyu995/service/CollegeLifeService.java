package xyz.xiaoyu995.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.xiaoyu995.Utils.DateStringFormatUtils;
import xyz.xiaoyu995.Utils.UUIDUtils;
import xyz.xiaoyu995.dao.MemberMapper;
import xyz.xiaoyu995.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollegeLifeService {

    private MemberMapper memberMapper;

    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public List<Member> queryAllMembers() {
        return memberMapper.queryAllMembers();
    }

    public List<Member> queryAllMembersByCid(Integer cid) {
        return memberMapper.queryAllMembersByCid(cid);
    }

    public List<Member> queryAllMembersByCoid(Integer coid) {
        return memberMapper.queryAllMembersByCoid(coid);
    }

    public Member queryMemberByOpenid(String openid) {
        return memberMapper.queryMemberByOpenid(openid);
    }

    public Classes queryClassByCid(Integer cid) {
        return memberMapper.queryClassByCid(cid);
    }

    public String queryClassNameByCid(Integer cid) {
        return memberMapper.queryClassNameByCid(cid);
    }

    public List<Location> queryAllLocations() {
        return memberMapper.queryAllLocations();
    }

    public List<Location> queryAllLocationsByCid(Integer cid) {
        return memberMapper.queryAllLocationsByCid(cid);
    }

    public List<Location> queryAllLocationsByCoid(Integer coid) {
        return memberMapper.queryAllLocationsByCoid(coid);
    }

    public void signIn(Member member) {
        memberMapper.addMember(member);
    }

    //这里是注册的业务
    public void signIn(String openid, String sName, String sNumber, Integer classIndex0, Integer classIndex1) {
        int cid = 0;
        if (classIndex0 == 0) {//这里说明是电子信息工程的
            if (classIndex1 == 0) {
                cid = 1;
            }
        } else if (classIndex0 == 1) {//这里说明是电气的
            switch (classIndex1) {
                case 0:
                    cid = 2;
                    break;
                case 1:
                    cid = 3;
                    break;
                case 2:
                    cid = 4;
                    break;
                case 3:
                    cid = 5;
                    break;
            }
        } else {
            switch (classIndex1) {
                case 0:
                    cid = 6;
                    break;
                case 1:
                    cid = 7;
                    break;
                case 2:
                    cid = 8;
                    break;
                case 3:
                    cid = 9;
                    break;
            }
        }
        Member member = new Member(UUIDUtils.getUUID(), openid, sName, sNumber, cid, 0);
        memberMapper.addMember(member);
    }

    public AllMember queryEntireMemberByOpenid(String openid) {
        return memberMapper.queryEntireMemberByOpenid(openid);
    }

    public Location queryLocationBySid(String sid) {
        return memberMapper.queryLocationBySid(sid);
    }

    public void addLocation(TempLocation tempLocation) {
        memberMapper.addLocation(tempLocation);
    }

    public void alterLocation(TempLocation tempLocation) {
        memberMapper.alterLocation(tempLocation);
    }

    public void addLocation(String sid, String longitude, String latitude) {
        TempLocation tempLocation = new TempLocation(sid, longitude, latitude, DateStringFormatUtils.getDate());
        //首先查数据库中有没有这个人的数据
        Location location = memberMapper.queryLocationBySid(sid);
        System.out.println(location);
        System.out.println(location == null);
        if (location == null) {//是空的，新建数据
            System.out.println("是空的，新建数据");
            memberMapper.addLocation(tempLocation);
        } else {
            System.out.println("不是空的，修改数据");
            memberMapper.alterLocation(tempLocation);
        }
    }

    public void alterMember(String openid, String sName, String sNumber, Integer classIndex0, Integer classIndex1) {
        Member member = memberMapper.queryMemberByOpenid(openid);
        int cid = member.getCid();
        if (classIndex0 == 0) {//这里说明是电子信息工程的
            if (classIndex1 == 0) {
                cid = 1;
            }
        } else if (classIndex0 == 1) {//这里说明是电气的
            switch (classIndex1) {
                case 0:
                    cid = 2;
                    break;
                case 1:
                    cid = 3;
                    break;
                case 2:
                    cid = 4;
                    break;
                case 3:
                    cid = 5;
                    break;
            }
        } else {
            switch (classIndex1) {
                case 0:
                    cid = 6;
                    break;
                case 1:
                    cid = 7;
                    break;
                case 2:
                    cid = 8;
                    break;
                case 3:
                    cid = 9;
                    break;
            }
        }
        member.setCid(cid);
        member.setSName(sName);
        member.setSNumber(sNumber);
        memberMapper.alterMember(member);
    }

    public void alterClassNotice(Map<String, Object> map) {
        memberMapper.alterClassNotice(map);
    }

    public void alterCollegeNotice(Map<String, Object> map) {
        memberMapper.alterCollegeNotice(map);
    }

    //申请修改权限
    public String alterLevel(String sid, String sName, String sNumber, Integer preLevel, Integer newLevel) {
        //先查有没有，没有新增
        AlterLevel alterLevel = memberMapper.queryAlterLevelBySid(sid);
        if (alterLevel == null){
            if (preLevel>=newLevel){//放进列表中
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("newLevel", newLevel);
                map.put("sid", sid);
                memberMapper.alterLevelBySid(map);
                return "更改成功！";
            }else{//直接修改
                memberMapper.addAlterLevel(new AlterLevel(sid,sName,sNumber,preLevel,newLevel));
                return "申请成功！";
            }
        }else{//有，修改
            if (preLevel >= newLevel) {
                //有申请过，但是新申请的等级比现在低，直接修改，然后删除存在的申请
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("newLevel", newLevel);
                map.put("sid", sid);
                memberMapper.alterLevelBySid(map);//直接修改权限
                memberMapper.delAlterLevelBySid(sid);//删除申请列表中的数据
                return "更改成功！";
            }else{
                memberMapper.updateAlterLevel(new AlterLevel(sid,sName,sNumber,preLevel,newLevel));
                return "申请成功！";
            }
        }
    }

    public List<AlterLevel> queryClassApplyByCid(Integer cid){
        return memberMapper.queryClassApplyByCid(cid);
    }
    public List<AlterLevel> queryCollegeApply(){
        return memberMapper.queryCollegeApply();
    }
    public List<AlterLevel> queryAllApply(){
        return memberMapper.queryAllApply();
    }
    
    public void agree(String sid){
        AlterLevel alterLevel = memberMapper.queryAlterLevelBySid(sid);
        Integer newLevel = alterLevel.getNewLevel();
        Member member = memberMapper.queryMemberBySid(sid);
        member.setLevel(newLevel);
        memberMapper.alterMember(member);
        memberMapper.delAlterLevelBySid(sid);
    }

    public void delAlterLevelBySid(String sid){
        memberMapper.delAlterLevelBySid(sid);
    }
}
