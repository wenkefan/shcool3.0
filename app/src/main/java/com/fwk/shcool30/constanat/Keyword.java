package com.fwk.shcool30.constanat;

/**
 * Created by fanwenke on 16/8/16.
 */
public class Keyword {
    //SP的key
    public static final String APPCONTEXT = "APPCONTEXT";
    public static final String APPORIGINALDATA = "APPORIGINALDATA";
    public static final String SPLOGIN = "SPLOGIN";

    /**-----------------------Login-----------------------**/
    //username
    public static final String LOGIN_USERNAME = "LOGIN_USERNAME";
    //password
    public static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";
    //kgid
    public static final String LOGIN_KGID = "LOGIN_KGID";
    //kgname
    public static final String LOGIN_KGNAME = "LOGIN_KGNAME";
    //name
    public static final String LOGIN_NAME ="LOGIN_NAME";
    //userid
    public static final String LOGIN_USERID = "LOGIN_USERID";
    //WorkerExtensionId
    public static final String LOGIN_WORKEREXTENSIONID = "LOGIN_WORKEREXTENSIONID";
    //设置是否登录
    public static final String LOGIN_ALREADYLOGIN = "LOGIN_ALREADYLOGIN";

    /**-----------------------SharedPreferences-----------------------**/


    //班次ID
    public static final String SP_BANCI_ID = "SP_BANCI_ID";
    //方向
    public static final String SP_ATTENDANCEDIRECTIONS = "SP_ATTENDANCEDIRECTIONS";
    //站点
    public static final String SP_STATION = "SP_STATION";
    //显示站点列表
    public static final String SP_STATION_MAP = "SP_STATION_MAP";
    //幼儿
    public static final String SP_CHILD = "SP_CHILD";
    //线路编号
    public static final String SP_XIANLU_ID = "SP_XIANLU_ID";


    //选择的状态
    public static final String SP_SELECT_ID = "SP_SELECT_ID";

    public static final String SP_ISSENDCAR = "SP_ISSENDCAR";

    public static final String BEGIN = "BEGIN";


    public static final String STATIONIDLIST = "STATIONIDLIST";//站点ID list
    public static final String STATIONMODE = "STATIONMODE";

    public static final String POTIONIT = "POTIONIT";

    public static final String CHILDGROUP = "CHILDGROUP";//分组之后的幼儿list
    public static final String HEADERLOCATION = "HEADERLOCATION";//头位置
    public static final String STATIONNAEM = "STATIONNAEM";//站点名称
    public static final String CHILDCOUNT = "CHILDCOUNT";//每个站点需要操作的人数
    public static final String STATIONPOSITION = "STATIONPOSITION";//当前站点

    public static final String THISSATION = "THISSATION";//第几站
    public static final String JUMPPOSITION = "JUMPPOSITION";//跳转到指定位置

    public static final String ISDAOZHAN = "ISDAOZHAN";//判断是否到站

    public static final String SELECTBANCI = "SELECTBANCI";//选择的班次
    public static final String SELECTCHILD = "SELECTCHILD";//选择中的幼儿


    public static final String SELECTZUOFEI = "SELECTZUOFEI";//引导作废

    public static final String GETSJTIME = "GETSJTIME";//实际到站时间
    /**
     * 保存运行状态
     */
    public static final String NEXTSTANAME = "NEXTSTANAME";//保存下一站名称
    public static final String NEXTTIME = "NEXTTIME";//保存预计到站时间

    /**
     * 没站的上车人数、下车人数和车上还剩余多少人
     */
    public static final String SHANGCHENUMBER = "SHANGCHENUMBER";//上车人数
    public static final String XIACHENUMBER = "XIACHENUMBER";//下车人数
    public static final String CARNUMBER = "CARNUMBER";//车上还剩多少人





    public static final String SELECTSTATIONID = "SELECTSTATIONID";//点击到站时选择的站点ID

    public static final String STATESTATIONBEAN = "STATESTATIONBEAN";


    /***************************************请求网络关键字**********************************************************/
    public static final int FLAGBANCI = 100;//班次
    public static final int FLAGSTATION = 200;//线路
    public static final int FLAGCHILD = 300;//幼儿
    public static final int FLAGFIRSTFACHE = 600;//第一次发车
    public static final int FLAGFIRSTFACHEError = 6001;//第一次发车Error
    public static final int FLAGENDDAOZHAN = 602;//结束

    public static final int FLAGFACHE = 701;//发车
    public static final int FLAGFACHEERROR = 70101;//发车error
    public static final int FLAGFACHEERROR1 = 70102;//发车error
    public static final int FLAGFACHE1 = 7010;//发车
    public static final int FLAGFACHE1ERROR = 701001;//发车1error
    public static final int FLAGDAOZHAN = 702;//到站
    public static final int FLAGDAOZHANERROR = 70201;//到站error


    public static final int FLAGDOWNCAR = 1101;//上车
    public static final int FLAGUPCAR = 1102;//下车


    /**************************************基础数据关键字**********************************************************/
    //班次List
    public static final String SP_BANCI_LIST = "SP_BANCI_LIST";
    //幼儿List
    public static final String SP_CHILD_LIST = "SP_CHILD_LIST";
    //站点List
    public static final String SP_STATION_LIST = "SP_STATION_LIST";
    //把每个站点分为了上车和下车俩个站点
    public static final String STAIDLIST = "STAIDLIST";
    //分组之后的map
    public static final String MAPLIST = "MAPLIST";
    //有幼儿上下车的站点
    public static final String SELECTSTA = "SELECTSTA";
    /**************************************业务逻辑关键字***********************************************************/
    //派车单号
    public static final String SP_PAICHEDANHAO = "SP_PAICHEDANHAO";
    //幼儿列表定位
    public static final String DINGWEI = "DINGWEI";

    public static final String SELECTITME = "SELECTITME";

    /**************************************业务逻辑关键字***********************************************************/

    public static final String LIXIANFASONGCARURL = "LIXIANFASONGCARURL";
    public static final int XiaURL = 100000;

    public static final String LIXIANSHANGXIAURL = "LIXIANSHANGXIAURL";
    public static final int ShangURL = 20000;


    public static final int ZUOFEI = 801;//作废



    public static final String TEACHERZT = "TEACHERZT";


    /**************************************MainActivity***********************************************************/


    public static final String BusOrderId = "BusOrderId";
    public static final String IntentBanCi0 = "IntentBanCi0";
    public static final String IntentBanCi1 = "IntentBanCi1";


    public static final int TEACHERZTFLAG = 1200;//用户类型
    public static final int TEACHERZTSUEE = 1201;
    public static final int TEACHERZTNULL = 1202;

    public static final int ATTENDANCEUSERFLAG = 1300;//用户表

    /**************************************StationActivity***********************************************************/
    public static final int STATIONJILU = 1300;

}
