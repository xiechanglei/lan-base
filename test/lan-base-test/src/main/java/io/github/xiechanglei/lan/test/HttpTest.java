package io.github.xiechanglei.lan.test;

import io.github.xiechanglei.lan.http.HttpHelper;
import io.github.xiechanglei.lan.http.HttpResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HttpTest {
    /**
     * ProjectInfoBySection?sid=cc6534a2-9740-4a67-ab82-5e6400f46f87
     * ProjectInfoBySection?sid=a5af783b-6e43-4fc7-98e1-d157715ab02e
     * ProjectInfoBySection?sid=ea6ed0ce-7e97-4ecc-b412-7402082b6b2a
     * ProjectInfoBySection?sid=0be46e6f-bcfe-4cef-add5-c13b454fd147
     * ProjectInfoBySection?sid=9359886c-310d-4244-9b6d-25e7cb13a677
     * ProjectInfoBySection?sid=e60a892f-ab56-4582-9769-63b7cc41dd5a
     * ProjectInfoBySection?sid=583f8e72-274e-409e-bc45-fb6f1ca35040
     * ProjectInfoBySection?sid=9c9cdd1e-5ac8-4b2d-af68-7df3156ebab0
     * ProjectInfoBySection?sid=1058fa4e-84a9-4995-94f3-9f8db9218075
     * ProjectInfoBySection?sid=788db696-c68b-4936-935d-c8a48a5b563c
     * ProjectInfoBySection?sid=7a4b881a-0ce7-49ec-b20c-fd0d3fc120b4
     * ProjectInfoBySection?sid=963331e4-17af-46bc-8206-fef98e34d63d
     * ProjectInfoBySection?sid=ad495000-8ab6-46f8-99d5-ad1b579d51d9
     * ProjectInfoBySection?sid=71db08b2-f471-46a3-acad-d38a8380a3c0
     * ProjectInfoBySection?sid=5e979df7-5338-4f2c-b476-1aa4335c73ab
     * ProjectInfoBySection?sid=f186a24c-b345-40ff-a405-6349c0ef1334
     * ProjectInfoBySection?sid=0c0765e9-fd21-4c1d-9972-f1375880ec05
     * ProjectInfoBySection?sid=15522ef2-f874-4142-8b9b-6a898492fd77
     * ProjectInfoBySection?sid=f97195a9-43f7-40c6-bf03-1d62ba83bcd6
     * ProjectInfoBySection?sid=a0352e38-753d-440a-90fb-b514d8f64931
     * ProjectInfoBySection?sid=c9ee877b-bde1-42ce-b780-19a390d7ca29
     * ProjectInfoBySection?sid=759d5628-1d9c-492a-840f-ba69f008bfb0
     * ProjectInfoBySection?sid=4168bee1-7bd6-48c5-b62d-f97d58dee25f
     * ProjectInfoBySection?sid=eadeeeda-3bc1-4281-a125-3a6ee85e0df5
     * ProjectInfoBySection?sid=2918fd21-6f85-46dd-86f8-4c68e330b3de
     * ProjectInfoBySection?sid=6f5c4741-e88b-4813-a29a-441975a730c0
     * ProjectInfoBySection?sid=77
     * ProjectInfoBySection?sid=468ef732-97ec-4dfc-8398-bcc9ad98837b
     * ProjectInfoBySection?sid=270835c5-3145-4537-a55f-f8d14c6beb1a
     * ProjectInfoBySection?sid=f1da5fbf-146e-45c1-86bd-8282ed84bbe4
     * ProjectInfoBySection?sid=6595ea38-8d42-47ca-81b4-1a6725c83325
     * ProjectInfoBySection?sid=922da5bd-54fe-4e6b-94b3-0423ed3beecb
     * ProjectInfoBySection?sid=dc56f61e-0c0e-419c-b6c9-769f9422bd8e
     * ProjectInfoBySection?sid=17508a68-ea29-40f4-a704-2149db36b561
     * ProjectInfoBySection?sid=2b22b6ef-bbad-4b21-89ce-589a7c5b74df
     * ProjectInfoBySection?sid=2fd51ce2-1a7f-4fbd-b3f4-84d985b2b063
     * ProjectInfoBySection?sid=caa038c8-8603-4e26-bbed-cafbdff8ee51
     * ProjectInfoBySection?sid=fd1dbd25-859f-4cce-98f3-8116a4c5da9d
     * ProjectInfoBySection?sid=9ca1315d-b3da-4f1b-aed8-420c006c3372
     * ProjectInfoBySection?sid=a371e963-47da-468a-a9bc-e10b3b70a323
     * ProjectInfoBySection?sid=404cc908-310c-409c-9f2c-ffe3e9faab9f
     * ProjectInfoBySection?sid=b9baf97a-0187-4799-948f-f871eefce337
     * ProjectInfoBySection?sid=102ee974-d953-40e6-9f63-06e15e9dbf39
     * ProjectInfoBySection?sid=3588f3bb-d0e8-4b69-b15d-b5a07315e586
     * ProjectInfoBySection?sid=4c58560b-3bdf-445b-b488-ec7162451016
     * ProjectInfoBySection?sid=374da16d-141e-45c4-9f65-beefbbf7a077
     * ProjectInfoBySection?sid=43edcce3-3131-457c-99f3-76e348adbf04
     * ProjectInfoBySection?sid=b08a3d34-ec4b-47d3-b1c8-f43154bf2bb3
     * ProjectInfoBySection?sid=ae52180e-7c2e-46d7-8874-a37653d8d774
     * ProjectInfoBySection?sid=f41f8eab-a3ee-47cd-b164-4ecf20cc0394
     * ProjectInfoBySection?sid=50f229d8-898c-42e6-bc09-54f55a8d0be2
     * ProjectInfoBySection?sid=963f1b13-495a-43f0-abc6-1804744b770f
     * ProjectInfoBySection?sid=09aa8051-f5d0-41be-8bae-0fb7ffa9c03b
     * ProjectInfoBySection?sid=292155d1-c881-4396-bfdf-6081374dc7e7
     * ProjectInfoBySection?sid=20
     * ProjectInfoBySection?sid=3bbfad8d-d13c-4506-96bd-1a609d3a21db
     * ProjectInfoBySection?sid=e5f01d3f-8fa0-4eb5-8e31-ccd2b22c25bb
     * ProjectInfoBySection?sid=31edf9aa-d729-447e-9f91-3b4a3968b7a4
     * ProjectInfoBySection?sid=e9a06194-ea87-484c-8bba-69c6bfc2b4c3
     * ProjectInfoBySection?sid=d4d2fd28-7d55-43d1-8af7-84ce80d9ce7f
     * ProjectInfoBySection?sid=8a9a5df1-efea-4f72-8e2b-918f9bb59a6d
     * ProjectInfoBySection?sid=c2c3be6d-6f8d-4056-af7c-ee81e4f5da7a
     * ProjectInfoBySection?sid=2
     * ProjectInfoBySection?sid=55f5ecbf-b57e-458c-82cc-f25aa207294c
     * ProjectInfoBySection?sid=333017d2-6935-46cc-89a4-c45a1c6f23c3
     * ProjectInfoBySection?sid=56cd495b-4f97-4a1e-a3e5-5ba328f663d9
     * ProjectInfoBySection?sid=13
     * ProjectInfoBySection?sid=1be71921-42d6-4f33-ae4d-90af756c4b4f
     * ProjectInfoBySection?sid=cc7d7b64-e47a-4802-95b8-3d97a9a1c991
     * ProjectInfoBySection?sid=e6c5dcb0-bcf8-4bbd-b931-47bd6a6c1b4f
     * ProjectInfoBySection?sid=651b45b2-051c-415a-a8bb-6da5330b6822
     * ProjectInfoBySection?sid=e5622e63-4b9c-43f4-8773-4146ab4f248d
     * ProjectInfoBySection?sid=439b4827-f03d-48bc-827e-8d490f2ac8b2
     * ProjectInfoBySection?sid=db98b728-b083-4ac7-9ef4-335245633cd1
     * ProjectInfoBySection?sid=537560e7-8e29-41cd-8161-9214671bf010
     * ProjectInfoBySection?sid=2be6ef59-6ec6-49d7-bcab-6f2d85b13a0c
     * ProjectInfoBySection?sid=6f8b38c3-bbbd-47d7-ac23-1bc0fe98e0ff
     * ProjectInfoBySection?sid=11734f46-6363-401c-b76c-129ac13dd524
     * ProjectInfoBySection?sid=a514d856-fc9c-4048-9d3b-4287f8221e04
     * ProjectInfoBySection?sid=882c34e5-0fc5-400f-8f46-642ac94fe737
     * ProjectInfoBySection?sid=def0e5e9-c9d1-4b5b-b475-2456c2c602ea
     * ProjectInfoBySection?sid=81
     * ProjectInfoBySection?sid=465b4392-a04b-42ec-9e91-85b77b81a932
     * ProjectInfoBySection?sid=c1c3df61-d043-4f1d-bfed-d8189ba35186
     * ProjectInfoBySection?sid=dbd1230e-6484-4307-89fd-04f8fd49961f
     * ProjectInfoBySection?sid=8a404d16-f70a-40ef-9721-088128763466
     * ProjectInfoBySection?sid=e1b7f852-0361-40f3-8df6-cb61c6a43b4b
     * ProjectInfoBySection?sid=86ec1d45-9f71-4dff-bb1f-dc0abb4525be
     * ProjectInfoBySection?sid=d64c85f9-e6bf-4920-8edf-f106bf856916
     */
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.7:3306/house", "root", "xie123");
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
//        HttpResponse xqListPage = HttpHelper.get("http://www.ahscfdc.com/Project/ShowBuild");
//        xqListPage.select(".table-bordered a").forEach(a->{
//            System.out.println(a.attr("href"));
//        });
//        accept("ProjectInfoBySection?sid=9359886c-310d-4244-9b6d-25e7cb13a677"); //桂语公馆
//        accept("ProjectInfoBySection?sid=6f8b38c3-bbbd-47d7-ac23-1bc0fe98e0ff"); //远大星河印
//        accept("ProjectInfoBySection?sid=4c58560b-3bdf-445b-b488-ec7162451016"); //杭埠上湖龙居
        accept("ProjectInfoBySection?sid=a371e963-47da-468a-a9bc-e10b3b70a323"); //桂语滨湖
    }

    public static Map<String, String> getXqInfo(HttpResponse response) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("小区名称", response.select(".table-bordered tbody tr:eq(0) td:eq(1)").text().trim());
        data.put("小区地址", response.select(".table-bordered tbody tr:eq(1) td:eq(1)").text().trim());
        data.put("建筑设计单位", response.select(".table-bordered tbody tr:eq(4) td:eq(1)").text().trim());
        data.put("物业管理公司", response.select(".table-bordered tbody tr:eq(5) td:eq(1)").text().trim());
        data.put("土地使用年限", response.select(".table-bordered tbody tr:eq(12) td:eq(1)").text().trim());
        data.put("土地规划用途", response.select(".table-bordered tbody tr:eq(13) td:eq(1)").text().trim());
        data.put("容积率", response.select(".table-bordered tbody tr:eq(16) td:eq(1)").text().trim());
        data.put("绿化率", response.select(".table-bordered tbody tr:eq(17) td:eq(1)").text().trim());
        data.put("车位数", response.select(".table-bordered tbody tr:eq(18) td:eq(1)").text().trim());
        return data;
    }

    private static void accept(String url) {
        String link = "http://www.ahscfdc.com/Project/" + url;
        try {
            HttpResponse response = HttpHelper.get(link);
            Map<String, String> xqInfo = getXqInfo(response);
            xqInfo.put("小区编号", link.substring(link.indexOf("sid=") + 4));
            System.out.println(xqInfo);
            // 获取楼栋
            response.select(".news-con table tbody tr").forEach(dong -> {
                try {
                    String 开盘日期 = HttpHelper.get("http://www.ahscfdc.com" + dong.select("td:eq(0) a").attr("href")).select(".table-bordered tbody tr:eq(6) td:eq(1)").text();
                    HttpHelper.get("http://www.ahscfdc.com" + dong.select("td:eq(1) a").attr("href")).select(".house-title a").forEach(house -> {
                        String gid = house.attr("data-guid");
                        try {
                            HttpResponse houseResponse = HttpHelper.get("http://www.ahscfdc.com/Project/HouseInfo/" + gid);
                            Map<String, String> fwxx = new HashMap<>();
                            fwxx.put("编号", gid);
                            fwxx.put("开盘日期", 开盘日期);
                            fwxx.put("楼栋", houseResponse.select(".table-bordered tbody tr:eq(0) td:eq(1)").text().trim());
                            fwxx.put("房号", houseResponse.select(".table-bordered tbody tr:eq(1) td:eq(1)").text().trim());
                            fwxx.put("所在层", houseResponse.select(".table-bordered tbody tr:eq(1) td:eq(3)").text().trim());
                            fwxx.put("层高", houseResponse.select(".table-bordered tbody tr:eq(2) td:eq(1)").text().trim());
                            fwxx.put("户型", houseResponse.select(".table-bordered tbody tr:eq(2) td:eq(3)").text().trim());
                            fwxx.put("建筑结构", houseResponse.select(".table-bordered tbody tr:eq(3) td:eq(1)").text().trim());
                            fwxx.put("设计用途", houseResponse.select(".table-bordered tbody tr:eq(3) td:eq(3)").text().trim());
                            fwxx.put("建筑面积", houseResponse.select(".table-bordered tbody tr:eq(4) td:eq(1)").text().trim());
                            fwxx.put("套内面积", houseResponse.select(".table-bordered tbody tr:eq(4) td:eq(3)").text().trim());
                            fwxx.put("户室状态", houseResponse.select(".table-bordered tbody tr:eq(5) td:eq(1)").text().trim());
                            fwxx.put("是否回迁房", houseResponse.select(".table-bordered tbody tr:eq(5) td:eq(3)").text().trim());
                            fwxx.put("备案价格", houseResponse.select(".table-bordered tbody tr:eq(6) td:eq(0)").text().replace("物价局备案价格：", "").trim());
                            fwxx.put("备案号", houseResponse.select(".table-bordered tbody tr:eq(8) td:eq(1)").text().trim());
                            fwxx.put("签约时间", houseResponse.select(".table-bordered tbody tr:eq(9) td:eq(1)").text().trim());
                            fwxx.put("备案时间", houseResponse.select(".table-bordered tbody tr:eq(9) td:eq(3)").text().trim());
                            save(xqInfo, fwxx);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Map<String, String> xqInfo, Map<String, String> houseInfo) {
        System.out.println(houseInfo);
        String id = houseInfo.get("编号");

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into house(编号) values (?)")) {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            // 已存在
        }

        try {
            for (String s : xqInfo.keySet()) {
                String sql = "update house set " + s + " = ? where 编号 = ? ";
                PreparedStatement p2 = connection.prepareStatement(sql);
                p2.setString(1, xqInfo.get(s));
                p2.setString(2, id);
                p2.executeUpdate();
            }
            for (String s : houseInfo.keySet()) {
                String sql = "update house set " + s + " = ? where 编号 = ? ";
                PreparedStatement p2 = connection.prepareStatement(sql);
                p2.setString(1, houseInfo.get(s));
                p2.setString(2, id);
                p2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
