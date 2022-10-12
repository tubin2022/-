package com.lianwei;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logistics2 {
    private static Random r = new Random();
    private static List<String> provinces = new ArrayList<>();
    private static List<List<String>> citys = new ArrayList<>();
    private static List<String> company = new ArrayList<>();
    static {
        provinces.add("河北省");
        provinces.add("山西省");
        provinces.add("内蒙古");
        provinces.add("辽宁省");
        provinces.add("吉林省");
        provinces.add("黑龙江省");
        provinces.add("江苏省");
        provinces.add("浙江省");
        provinces.add("安徽省");
        provinces.add("福建省");
        provinces.add("江西省");
        provinces.add("山东省");
        provinces.add("河南省");
        provinces.add("湖北省");
        provinces.add("湖南省");
        provinces.add("广东省");
        provinces.add("广西省");
        provinces.add("四川省");
        provinces.add("贵州省");
        provinces.add("云南省");
        provinces.add("西藏");
        provinces.add("陕西省");
        provinces.add("甘肃省");
        provinces.add("青海省");
        provinces.add("宁夏");
        provinces.add("新疆");
        provinces.add("重庆市");
        provinces.add("北京市");
        provinces.add("上海市");
        provinces.add("天津市");

        String city1 = "石家庄 保定市 秦皇岛 唐山市 邯郸市 邢台市 沧州市 承德市 廊坊市 衡水市 张家口";
        String[] s1 = city1.split(" ");
        List<String> list1 = Arrays.asList(s1);
        citys.add(list1);

        String city2 = "太原市 大同市 阳泉市 长治市 临汾市 晋中市 运城市 晋城市 忻州市 朔州市 吕梁市";
        String[] s2 = city2.split(" ");
        List<String> list2 = Arrays.asList(s2);
        citys.add(list2);

        String city3 = "呼和浩特 呼伦贝尔 包头市 赤峰市 乌海市 通辽市 鄂尔多斯 乌兰察布 巴彦淖尔";
        String[] s3 = city3.split(" ");
        List<String> list3 = Arrays.asList(s3);
        citys.add(list3);

        String city4 = "盘锦市 鞍山市 抚顺市 本溪市 铁岭市 锦州市 丹东市 辽阳市 葫芦岛 阜新市 朝阳市 营口市";
        String[] s4 = city4.split(" ");
        List<String> list4 = Arrays.asList(s4);
        citys.add(list4);

        String city5 = "吉林市 通化市 白城市 四平市 辽源市 松原市 白山市";
        String[] s5 = city5.split(" ");
        List<String> list5 = Arrays.asList(s5);
        citys.add(list5);

        String city6 = "伊春市 牡丹江 大庆市 鸡西市 鹤岗市 绥化市 双鸭山 七台河 佳木斯 黑河市 齐齐哈尔市";
        String[] s6 = city6.split(" ");
        List<String> list6 = Arrays.asList(s6);
        citys.add(list6);

        String city7 = "无锡市 常州市 扬州市 徐州市 苏州市 连云港 盐城市 淮安市 宿迁市 镇江市 南通市 泰州市";
        String[] s7 = city7.split(" ");
        List<String> list7 = Arrays.asList(s7);
        citys.add(list7);

        String city8 = "绍兴市 温州市 湖州市 嘉兴市 台州市 金华市 舟山市 衢州市 丽水市";
        String[] s8 = city8.split(" ");
        List<String> list8 = Arrays.asList(s8);
        citys.add(list8);

        String city9 = "合肥市 芜湖市 亳州市 马鞍山 池州市 淮南市 淮北市 蚌埠市 巢湖市 安庆市 宿州市 宣城市 滁州市 黄山市 六安市 阜阳市 铜陵市";
        String[] s9 = city9.split(" ");
        List<String> list9 = Arrays.asList(s9);
        citys.add(list9);

        String city10 = "福州市 泉州市 漳州市 南平市 三明市 龙岩市 莆田市 宁德市";
        String[] s10 = city10.split(" ");
        List<String> list10 = Arrays.asList(s10);
        citys.add(list10);

        String city11 = "南昌市 赣州市 景德镇 九江市 萍乡市 新余市 抚州市 宜春市 上饶市 鹰潭市 吉安市";
        String[] s11 = city11.split(" ");
        List<String> list11 = Arrays.asList(s11);
        citys.add(list11);

        String city12 = "潍坊市 淄博市 威海市 枣庄市 泰安市 临沂市 东营市 济宁市 烟台市 菏泽市 日照市 德州市 聊城市 滨州市 莱芜市";
        String[] s12 = city12.split(" ");
        List<String> list12 = Arrays.asList(s12);
        citys.add(list12);

        String city13 = "郑州市 洛阳市 焦作市 商丘市 信阳市 新乡市 安阳市 开封市 漯河市 南阳市 鹤壁市 平顶山 濮阳市 许昌市 周口市 三门峡 驻马店";
        String[] s13 = city13.split(" ");
        List<String> list13 = Arrays.asList(s13);
        citys.add(list13);

        String city14 = "荆门市 咸宁市 襄樊市 荆州市 黄石市 宜昌市 随州市 鄂州市 孝感市 黄冈市 十堰市";
        String[] s14 = city14.split(" ");
        List<String> list14 = Arrays.asList(s14);
        citys.add(list14);

        String city15 = "长沙市 郴州市 娄底市 衡阳市 株洲市 湘潭市 岳阳市 常德市 邵阳市 益阳市 永州市 张家界 怀化市";
        String[] s15 = city15.split(" ");
        List<String> list15 = Arrays.asList(s15);
        citys.add(list15);

        String city16 = "江门市 佛山市 汕头市 湛江市 韶关市 中山市 珠海市 茂名市 肇庆市 阳江市 惠州市 潮州市 揭阳市 清远市 河源市 东莞市 汕尾市 云浮市";
        String[] s16 = city16.split(" ");
        List<String> list16 = Arrays.asList(s16);
        citys.add(list16);

        String city17 = "南宁市 贺州市 柳州市 桂林市 梧州市 北海市 玉林市 钦州市 百色市 防城港 贵港市 河池市 崇左市 来宾市";
        String[] s17 = city17.split(" ");
        List<String> list17 = Arrays.asList(s17);
        citys.add(list17);

        String city18 = "乐山市 雅安市 广安市 南充市 自贡市 泸州市 内江市 宜宾市 广元市 达州市 资阳市 绵阳市 眉山市 巴中市 攀枝花 遂宁市 德阳市";
        String[] s18 = city18.split(" ");
        List<String> list18 = Arrays.asList(s18);
        citys.add(list18);

        String city19 = "贵阳市 安顺市 遵义市 六盘水";
        String[] s19 = city19.split(" ");
        List<String> list19 = Arrays.asList(s19);
        citys.add(list19);

        String city20 = "昆明市 玉溪市 大理市 曲靖市 昭通市 保山市 丽江市 临沧市";
        String[] s20 = city20.split(" ");
        List<String> list20 = Arrays.asList(s20);
        citys.add(list20);

        String city21 = "拉萨市 阿里";
        String[] s21 = city21.split(" ");
        List<String> list21 = Arrays.asList(s21);
        citys.add(list21);

        String city22 = "咸阳市 榆林市 宝鸡市 铜川市 渭南市 汉中市 安康市 商洛市 延安市";
        String[] s22 = city22.split(" ");
        List<String> list22 = Arrays.asList(s22);
        citys.add(list22);

        String city23 = "兰州市 白银市 武威市 金昌市 平凉市 张掖市 嘉峪关 酒泉市 庆阳市 定西市 陇南市 天水市";
        String[] s23 = city23.split(" ");
        List<String> list23 = Arrays.asList(s23);
        citys.add(list23);

        String city24 = "西宁市";
        String[] s24 = city24.split(" ");
        List<String> list24 = Arrays.asList(s24);
        citys.add(list24);

        String city25 = "银川市 固原市 青铜峡市 石嘴山市 中卫市";
        String[] s25 = city25.split(" ");
        List<String> list25 = Arrays.asList(s25);
        citys.add(list25);

        String city26 = "乌鲁木齐 克拉玛依市";
        String[] s26 = city26.split(" ");
        List<String> list26 = Arrays.asList(s26);
        citys.add(list26);

        company.add("京东");
        company.add("顺丰");
        company.add("EMS");
        company.add("圆通");
    }

    //随机获取地址
    public static List<String> getAddress(){
        List<String> address = new ArrayList<>();
        int i = r.nextInt(provinces.size());
        String province = provinces.get(i);
        if(province.equals("重庆市") || province.equals("北京市") || province.equals("天津市") || province.equals("上海市")){
            address.add("null");
            address.add(province);
        }else{
            address.add(province);
            List<String> listCity = citys.get(i);
            int i1 = r.nextInt(listCity.size());
            String city = listCity.get(i1);
            address.add(city);
        }
        return address;
    }

    //随机获取时间
    public static String getTime(){
//        int year = r.nextInt(2) == 0?2021:2022;
        int year = 2022;
        int month;
//        if(year == 2022){
            month = r.nextInt(8);
//        }else{
//            month = r.nextInt(12);
//        }
        int day = r.nextInt(getDay(month))+1;
        int hour = r.nextInt(24);
        int min = r.nextInt(60);
        int sec = r.nextInt(60);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONDAY,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,sec);
        Date timeDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(timeDate);
        return time;
    }

    //随机获取物流公司
    public static String getCompany(){
        int i = r.nextInt(company.size());
        String s = company.get(i);
        return s;
    }

    //获取一个时间之后的随机时间
    public static String getNextTime(String nowTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowDate = sdf.parse(nowTime);
        int i = r.nextInt(7200000)+86400000;
        Long nextTime = nowDate.getTime()+i;
        Date netDate = new Date(nextTime);
        String format = sdf.format(netDate);
        return format;
    }

    private static int getDay(int month) {
        if(month == 2){
            return 28;
        }
        if(month ==1 || month ==3 || month ==5 || month == 7 || month == 8 || month == 10 || month == 12){
            return 31;
        }
        return 30;
    }

    public static void write(Logistics logistics) throws IOException {
        File file = new File("C:\\Users\\Tubin\\Desktop\\logistics-data.txt");
        Writer writer = new FileWriter(file,true);
        writer.write(logistics.toString()+"\r\n");
        writer.flush();
    }

    public static void main(String[] args) throws InterruptedException, ParseException, IOException {
        Integer logisticsNum = 30001;
        while (logisticsNum <= 60000){
            //寄件
            Logistics logistics = new Logistics();
            List<String> address = getAddress();
            logistics.setSendProvince(address.get(0));
            logistics.setSendCity(address.get(1));
            List<String> address1 = getAddress();
            logistics.setReceiveProvince(address1.get(0));
            logistics.setReceiveCity(address1.get(1));
            String time = getTime();
            logistics.setSendTime(time);
            logistics.setCompany(getCompany());
//            System.out.println(logistics);
            write(logistics);
            //派件
            int i = r.nextInt(4)+3;//随机派件次数
            for(int a= 0; a<=i;a++){
                logistics.setLogisticsId(logisticsNum);
                List<String> address2 = getAddress();
                logistics.setDispatchedProvince(address2.get(0));
                logistics.setDispatchedCity(address2.get(1));
                if(logistics.getDispatchedTime() == null){
                    //第一次派件
                    logistics.setDispatchedTime(getNextTime(time));
                }else{
                    String dispatchedTime = logistics.getDispatchedTime();
                    logistics.setDispatchedTime(dispatchedTime);
                }
                if(Math.random() < 0.95){
                    logistics.setDispatchedLost(0);
                }else{
                    //物件丢失，结束派件
                    logistics.setDispatchedLost(1);
                    break;
                }
//                System.out.println(logistics);
                write(logistics);
            }
            if(logistics.getDispatchedLost() != 1){
                //没有丢失，派送到终点
                logistics.setDispatchedProvince(address1.get(0));
                logistics.setDispatchedCity(address1.get(1));
//                System.out.println(logistics);
                write(logistics);
            }
            //签收或者退货
            if(Math.random() < 0.85){
                logistics.setSignTime(getNextTime(logistics.getDispatchedTime()));
//                System.out.println(logistics);
                write(logistics);
            }else {
                logistics.setBackTime(getNextTime(logistics.getDispatchedTime()));
//                System.out.println(logistics);
                write(logistics);
            }
            logisticsNum++;
            Thread.sleep(200);
        }
    }

    public static void run() throws IOException, ParseException, InterruptedException {
        Integer logisticsNum = 30001;
        while (logisticsNum <= 60000){
            //寄件
            Logistics logistics = new Logistics();
            List<String> address = getAddress();
            logistics.setSendProvince(address.get(0));
            logistics.setSendCity(address.get(1));
            List<String> address1 = getAddress();
            logistics.setReceiveProvince(address1.get(0));
            logistics.setReceiveCity(address1.get(1));
            String time = getTime();
            logistics.setSendTime(time);
            logistics.setCompany(getCompany());
            logistics.setStatus("1");
//            System.out.println(logistics);
            write(logistics);
            //派件
            int i = r.nextInt(4)+3;//随机派件次数
            for(int a= 0; a<=i;a++){
                logistics.setLogisticsId(logisticsNum);
                if(logistics.getDispatchProvince() == null || logistics.getDispatchCity() == null){
                    //第一次派送，起点派送城市设置为寄件城市
                    logistics.setDispatchProvince(address.get(0));
                    logistics.setDispatchCity(address.get(1));
                }else{
                    logistics.setDispatchProvince(logistics.getDispatchedProvince());
                    logistics.setDispatchCity(logistics.getDispatchedCity());
                }
                List<String> address2 = getAddress();
                logistics.setDispatchedProvince(address2.get(0));
                logistics.setDispatchedCity(address2.get(1));
                if(logistics.getDispatchedTime() == null){
                    //第一次派件
                    logistics.setDispatchedTime(getNextTime(time));
                }else{
                    String dispatchedTime = logistics.getDispatchedTime();
                    logistics.setDispatchedTime(getNextTime(dispatchedTime));
                }
                logistics.setStatus("0");//派送出库
                logistics.setDispatchedLost(0);

                write(logistics);

                String dispatchedTime = logistics.getDispatchedTime();
                logistics.setDispatchedTime(getNextTime(dispatchedTime));
                if(Math.random() < 0.95){
                    logistics.setStatus("1");//派送入库
                    logistics.setDispatchedLost(0);
                    write(logistics);
                }else{
                    //物件丢失，结束派件
                    logistics.setStatus("null");
                    logistics.setDispatchedLost(1);
                    write(logistics);
                    break;
                }
//                System.out.println(logistics);
            }
            if(logistics.getDispatchedLost() != 1){
                //没有丢失，派送到终点
                logistics.setDispatchProvince(logistics.getDispatchedProvince());
                logistics.setDispatchCity(logistics.getDispatchedCity());
                logistics.setDispatchedProvince(address1.get(0));
                logistics.setDispatchedCity(address1.get(1));
                logistics.setStatus("0");//派送最后一战出库
                String dispatchedTime = logistics.getDispatchedTime();
                logistics.setDispatchedTime(getNextTime(dispatchedTime));
                write(logistics);
                logistics.setStatus("1");//派送最后一战入库
                String dispatchedTime2 = logistics.getDispatchedTime();
                logistics.setDispatchedTime(getNextTime(dispatchedTime2));
//                System.out.println(logistics);
                write(logistics);

                //签收或者退货
                if(Math.random() < 0.85){
                    logistics.setStatus("0");
                    logistics.setSignTime(getNextTime(logistics.getDispatchedTime()));
//                System.out.println(logistics);
                    write(logistics);
                }else {
                    logistics.setStatus("0");
                    logistics.setBackTime(getNextTime(logistics.getDispatchedTime()));
//                System.out.println(logistics);
                    write(logistics);
                }
            }
            logisticsNum++;
            Thread.sleep(200);
        }
    }
}
