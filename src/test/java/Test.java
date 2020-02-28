import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.xiaoyu995.Utils.DateStringFormatUtils;
import xyz.xiaoyu995.Utils.UUIDUtils;
import xyz.xiaoyu995.domain.*;
import xyz.xiaoyu995.service.CollegeLifeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Test {
    private ApplicationContext context;
    private CollegeLifeService collegeLifeService;
    @Before
    public void getContext(){
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        collegeLifeService = context.getBean("collegeLifeService", CollegeLifeService.class);
    }
    @org.junit.Test
    public void testUUIDUtils(){
        System.out.println(UUIDUtils.getUUID());
    }
    @org.junit.Test
    public void testDateStringFormatUtils(){
        String[] s = DateStringFormatUtils.dateFormat("2020-02-02 13:46:37.0");
        for (String s1 : s) {
            System.out.println(s1);
        }
    }
    @org.junit.Test
    public void testQueryAll(){
        List<Member> members = collegeLifeService.queryAllMembers();
        for (Member member : members) {
            System.out.println(member);
        }
    }
    @org.junit.Test
    public void TestQueryAllMembersByCid(){
        List<Member> members = collegeLifeService.queryAllMembersByCid(1);
        for (Member member : members) {
            System.out.println(member);
        }
    }
    @org.junit.Test
    public void TestQueryAllMembersByCoid(){
        List<Member> members = collegeLifeService.queryAllMembersByCoid(3);
        for (Member member : members) {
            System.out.println(member);
        }
    }

    @org.junit.Test
    public void testQueryClassByCid(){
        Classes classes = collegeLifeService.queryClassByCid(1);
        System.out.println(classes);
    }
    @org.junit.Test
    public void testQueryAllLocations(){
        List<Location> locations = collegeLifeService.queryAllLocations();
        for (Location location : locations) {
            System.out.println(location);
        }
    }
    @org.junit.Test
    public void testQueryAllLocationsByCid(){
        List<Location> locations = collegeLifeService.queryAllLocationsByCid(2);
        for (Location location : locations) {
            System.out.println(location);
        }
    }
    @org.junit.Test
    public void testQueryAllLocationsByCoid(){
        List<Location> locations = collegeLifeService.queryAllLocationsByCoid(1);
        for (Location location : locations) {
            System.out.println(location);
        }
    }
    @org.junit.Test
    public void testQueryMemberByOpenid(){
        Member member = collegeLifeService.queryMemberByOpenid("oHtPU5Igq299glgTp6leJYNeFmYf");
        System.out.println(member);
    }

    @org.junit.Test
    public void testSignIn() {
        Member member = new Member(UUIDUtils.getUUID(),"123214124124","卢本伟","12312312",1,0);
        collegeLifeService.signIn(member);
    }

    @org.junit.Test
    public void testQueryEntireMemberByOpenid() {
        AllMember member = collegeLifeService.queryEntireMemberByOpenid("oHtPU5Igq299glgTp6leJYNeFmYc");
        System.out.println(member);
    }

    @org.junit.Test
    public void testAddLocation() {
        collegeLifeService.addLocation(new TempLocation("02eb964c33cd4587b4e9697531a6e6c5","123.231","123123.123","2019-02-02 19:12:12"));
    }

    @org.junit.Test
    public void testQueryLocationBySid() {
        Location location = collegeLifeService.queryLocationBySid("02eb964c33cd4587b4e9697531a6e6c5");
        System.out.println(location);
    }

    @org.junit.Test
    public void testAlterLocation() {
        TempLocation a = new TempLocation("02eb964c33cd4587b4e9697531a6e6c5", "12334", "12123", "2019-02-02 19:12:12");
        collegeLifeService.alterLocation(a);
    }

    @org.junit.Test
    public void textHttpClient() {
        //DefaulthttpClient已经过期,需要使用HttpClientBuilder来构建.
        //HttpClient httpClient = new DefaultHttpClient();
        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "https://api.weixin.qq.com/sns/jscode2session?grant_type=client_credential&appid=wxb4af88d61d8255aa&secret=a4c3744b257b5b68403c37ab142cda42&js_code=021Ql7LB0NMlik2oJyNB0Ri6LB0Ql7L1";
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
            System.out.println(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testQueryClassApplyByCid() {
        List<AlterLevel> alterLevels = collegeLifeService.queryClassApplyByCid(1);
        System.out.println(alterLevels);
    }
}
