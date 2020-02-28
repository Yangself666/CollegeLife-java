package xyz.xiaoyu995.controller;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xiaoyu995.domain.AllMember;
import xyz.xiaoyu995.domain.AlterLevel;
import xyz.xiaoyu995.domain.Location;
import xyz.xiaoyu995.domain.Member;
import xyz.xiaoyu995.service.CollegeLifeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {
    private static ApplicationContext context;
    private static CollegeLifeService collegeLifeService;
    static {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        collegeLifeService = context.getBean("collegeLifeService", CollegeLifeService.class);
    }

    /**
     * 本方法返回的是openid查询出来的用户数据
     * @param openid
     * @return
     */
    @RequestMapping(value = "/login",produces = "application/json;charset=utf-8")
    public String login(@Param("openid") String openid){
        AllMember member = collegeLifeService.queryEntireMemberByOpenid(openid);
        if(member == null){
            return null;
        }
        return JSON.toJSONString(member);
    }

    /**
     * 根据openid注册用户，需要传入姓名，学号，班级信息
     * @param openid
     * @param sName
     * @param sNumber
     * @param classIndex0
     * @param classIndex1
     * @return
     */
    @RequestMapping(value = "/signin",produces = "application/json;charset=utf-8")
    public String signin(@Param("openid") String openid,@Param("sName") String sName,@Param("sNumber") String sNumber,@Param("classIndex0") Integer classIndex0,@Param("classIndex1") Integer classIndex1){
        Member member = collegeLifeService.queryMemberByOpenid(openid);
        if (member != null){
            return "您已经注册！";
        }
        collegeLifeService.signIn(openid, sName, sNumber, classIndex0, classIndex1);
        return "注册成功！";
    }

    /**
     * 本方法进行位置信息的收集
     * 注意本方法使用的是POST方法
     * @return
     */
    @RequestMapping(value = "/location",produces = "application/json;charset=utf-8")
    public String setLocation(@Param("sid") String sid,@Param("longitude") String longitude,@Param("latitude") String latitude){
        if(longitude == null || latitude == null){
            return "false";
        }
        collegeLifeService.addLocation(sid, longitude, latitude);
        return "success";
    }

    @RequestMapping(value = "/info",produces = "application/json;charset=utf-8")
    public String alterInfo(@Param("openid") String openid,@Param("sName") String sName,@Param("sNumber") String sNumber,@Param("classIndex0") Integer classIndex0,@Param("classIndex1") Integer classIndex1){
        //通过openid修改用户信息
        collegeLifeService.alterMember(openid, sName, sNumber, classIndex0, classIndex1);
        return "success";
    }
    @RequestMapping(value = "/member",produces = "application/json;charset=utf-8")
    public String queryMember(@Param("openid") String openid){
        return JSON.toJSONString(collegeLifeService.queryMemberByOpenid(openid));
    }

    @RequestMapping(value = "/class",produces = "application/json;charset=utf-8")
    public String alterClassNotice(@Param("cid") Integer cid,@Param("cNotice") String cNotice){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("cid", cid);
        map.put("cNotice", cNotice);
        collegeLifeService.alterClassNotice(map);
        return "success";
    }
    @RequestMapping(value = "/college",produces = "application/json;charset=utf-8")
    public String alterCollegeNotice(@Param("cid") Integer coid,@Param("cNotice") String coNotice){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("coid", coid);
        map.put("coNotice", coNotice);
        collegeLifeService.alterCollegeNotice(map);
        return "success";
    }

    @RequestMapping(value = "/allLocation",produces = "application/json;charset=utf-8")
    public String queryAllLocations(){
        List<Location> locations = collegeLifeService.queryAllLocations();
        return JSON.toJSONString(locations);
    }

    @RequestMapping(value = "/classLocation",produces = "application/json;charset=utf-8")
    public String queryAllLocationsByCid(@Param("cid") Integer cid){
        List<Location> locations = collegeLifeService.queryAllLocationsByCid(cid);
        return JSON.toJSONString(locations);
    }

    @RequestMapping(value = "/collegeLocation",produces = "application/json;charset=utf-8")
    public String queryAllLocationsByCoid(@Param("coid") Integer coid){
        List<Location> locations = collegeLifeService.queryAllLocationsByCoid(coid);
        return JSON.toJSONString(locations);
    }
    @RequestMapping(value = "/alterLevel",produces = "application/json;charset=utf-8")
    public String alterLevel(String sid, String sName, String sNumber, Integer preLevel, Integer newLevel){
        return collegeLifeService.alterLevel(sid, sName, sNumber, preLevel, newLevel);
    }

    @RequestMapping(value = "/applyClassNum",produces = "application/json;charset=utf-8")
    public String applyClassNum(Integer cid){
        List<AlterLevel> alterLevels = collegeLifeService.queryClassApplyByCid(cid);
        return JSON.toJSONString(alterLevels);
    }

    @RequestMapping(value = "/applyCollegeNum",produces = "application/json;charset=utf-8")
    public String applyCollegeNum(){
        List<AlterLevel> alterLevels = collegeLifeService.queryCollegeApply();
        return JSON.toJSONString(alterLevels);
    }
    @RequestMapping(value = "/applyAllNum",produces = "application/json;charset=utf-8")
    public String applyAllNum(){
        List<AlterLevel> alterLevels = collegeLifeService.queryAllApply();
        return JSON.toJSONString(alterLevels);
    }

    @RequestMapping(value = "/agree",produces = "application/json;charset=utf-8")
    public String agree(String sid){
        collegeLifeService.agree(sid);
        return null;
    }

    @RequestMapping(value = "/reject",produces = "application/json;charset=utf-8")
    public String reject(String sid){
        collegeLifeService.delAlterLevelBySid(sid);
        return null;
    }


    @RequestMapping(value = "/openid",produces = "application/json;charset=utf-8")
    public String queryAllLocationsByCoid(@Param("js_code") String js_code){
        //DefaulthttpClient已经过期,需要使用HttpClientBuilder来构建.
        //HttpClient httpClient = new DefaultHttpClient();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "https://api.weixin.qq.com/sns/jscode2session?grant_type=client_credential&appid=wxb4af88d61d8255aa&secret=a4c3744b257b5b68403c37ab142cda42&js_code=" +js_code;
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(request);

            InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






}
