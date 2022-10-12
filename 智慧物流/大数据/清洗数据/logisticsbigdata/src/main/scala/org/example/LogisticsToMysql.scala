package org.example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

import java.util.Properties

object LogisticsToMysql {
  case class logistics(id:String,send_pro:String,send_city:String,sign_pro:String,
                       sign_city:String,send_time:String,start_pro:String,
                       start_city:String, delivery_pro:String, delivery_city:String,
                       delivery_time:String,delivery_lost:String,sign_time:String,
                       refund_time:String,company:String,status:String)
  def main(args: Array[String]): Unit = {
    val path = new GetFileName().getName();
    println(path)
    Logger.getLogger("org").setLevel(Level.WARN)
    val conf=new SparkConf().setMaster("local[*]").setAppName("logistics")
    val sc=new SparkContext(conf)
    val spark=SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    val fileRDD=sc.textFile(path)
    val dataRDD=fileRDD.map(line=>{
      val data=line.split("_")
      val s_time=data(5).split(" ")
      val d_time=data(10).split(" ")
      val g_time=data(12).split(" ")
      val r_time=data(13).split(" ")
      logistics(data(0),data(1),data(2),data(3),data(4),s_time(0),
        data(6),data(7),data(8),data(9),d_time(0),data(11),g_time(0),r_time(0),data(14),data(15))
    })
    val df=dataRDD.toDF()
    df.createTempView("logistics")
//    入库件
    val enterWarehouse=spark.sql(
      """
        |select time,pro,sum(num) num from(
        |select time,pro,count(*) num from
        |(select send_time time,(case when send_pro='null' then send_city else send_pro end) pro from logistics
        |where id='null') group by time,pro
        |union all
        |select time,pro,count(*) num from
        |(select delivery_time time,(case when delivery_pro='null' then delivery_city else delivery_pro end) pro from logistics
        |where status='1' and id!='null') group by time,pro)
        |group by time,pro order by time,pro
        |""".stripMargin)
    enterWarehouse.createTempView("enterWarehouse")
//    出库件
    val outWarehouse=spark.sql(
      """
        |select time,pro,count(*) num from
        |(select delivery_time time,(case when start_pro='null' then start_city else start_pro end) pro from logistics
        |where id!='null' and status='0')
        |group by time,pro order by time,pro
        |""".stripMargin)
    outWarehouse.createTempView("outWarehouse")
//    增加件
    val addWarehouse=spark.sql(
      """
        |select time,pro,sum(num) num from(
        |select time,pro,num from enterWarehouse
        |union all
        |select time,pro,-num num from outWarehouse)
        |group by time,pro order by time,pro
        |""".stripMargin)
//    退签件
    val refundNum=spark.sql(
      """
        |select time,pro,count(*) num from
        |(select refund_time time,(case when delivery_pro='null' then delivery_city else delivery_pro end) pro from logistics
        |where refund_time!='null') group by time,pro order by time,pro
        |""".stripMargin)
//    丢失件
    val lostNum=spark.sql(
      """
        |select time,pro,count(*) num from
        |(select delivery_time time,(case when delivery_pro='null' then delivery_city else delivery_pro end) pro from logistics
        |where delivery_lost='1') group by time,pro order by time,pro
        |""".stripMargin)
//    全国物流总量
    val nationalLogistics=spark.sql(
      """
        |select send_time time,count(*) num from logistics
        |where id='null' group by send_time order by time
        |""".stripMargin)
//    各省物流量
    val aroundLogistics=spark.sql(
      """
        |select time,pro,count(*) num from(
        |select send_time time,(case when send_pro='null' then send_city else send_pro end) pro from logistics
        |where id='null') group by time,pro order by time,pro
        |""".stripMargin)
//    全国物流公司寄件数量
    val nationalCompanyNum=spark.sql(
      """
        |select send_time time,company com,count(*) num from logistics
        |where id='null' group by send_time,company order by time,com
        |""".stripMargin)
//    各省物流公司寄件数量
    val aroundCompanyNum=spark.sql(
      """
        |select time,pro,com,count(*) num from(
        |select send_time time,(case when send_pro='null' then send_city else send_pro end) pro,company com from logistics
        |where id='null') group by time,pro,com order by time,pro,com
        |""".stripMargin)
//    各地寄件数量
    val aroundSendNum=spark.sql(
      """
        |select send_time time,send_pro pro,send_city city,count(*) num from logistics
        |where id='null' group by send_time,send_pro,send_city order by time,pro,city
        |""".stripMargin)
//    各地派件数量
    val aroundDeliveryNum=spark.sql(
      """
        |select delivery_time time,start_pro pro,start_city city,count(*) num from logistics
        |where id!='null' and status='0'
        |group by delivery_time,start_pro,start_city order by time,pro,city
        |""".stripMargin)
    val tran_logistics=spark.sql(
      """
        |select time,startPro,endPro,count(*) num from
        |(select delivery_time time,(case when start_pro='null' then start_city else start_pro end) startPro,(case when delivery_pro='null' then delivery_city else delivery_pro end) endPro from logistics
        |where status='0' and delivery_lost='0') group by time,startPro,endPro order by time,startPro,endPro
        |""".stripMargin)
    val props=new Properties()
    props.setProperty("user","root")
    props.setProperty("password","123456")
    val url="jdbc:mysql://localhost:3306/logisticsls?useSSL=false&serverTimezone=UTC"
    enterWarehouse.write.mode(SaveMode.Append).jdbc(url,"enter_house",props)
    outWarehouse.write.mode(SaveMode.Append).jdbc(url,"out_house",props)
    addWarehouse.write.mode(SaveMode.Append).jdbc(url,"in_house",props)
    refundNum.write.mode(SaveMode.Append).jdbc(url,"return_logistics",props)
    lostNum.write.mode(SaveMode.Append).jdbc(url,"lost_logistics",props)
    nationalLogistics.write.mode(SaveMode.Append).jdbc(url,"china_logistics",props)
    aroundLogistics.write.mode(SaveMode.Append).jdbc(url,"province_logistics",props)
    nationalCompanyNum.write.mode(SaveMode.Append).jdbc(url,"china_company",props)
    aroundCompanyNum.write.mode(SaveMode.Append).jdbc(url,"province_company",props)
    aroundSendNum.write.mode(SaveMode.Append).jdbc(url,"send_logistics",props)
    aroundDeliveryNum.write.mode(SaveMode.Append).jdbc(url,"dispatch_logistics",props)
    tran_logistics.write.mode(SaveMode.Append).jdbc(url,"tran_logistics",props)
    sc.stop()
    spark.stop()
  }
}
