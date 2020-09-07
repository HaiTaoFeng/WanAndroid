package com.fenght.wanandroid.constants;

public class API {

    public final static String BASE_URL = "https://www.wanandroid.com/";

    //首页文章列表
    public final static String HOME_ARTICLE = BASE_URL + "article/list/";
    //首页Binner
    public final static String HOME_BINNER = BASE_URL + "banner/json";
    //体系模块的标签
    public final static String SYSTEM_LABEL = BASE_URL + "tree/json";
    //体系模块下按标签分类的文章
    public final static String SYSTEM_LABEL_ARTICLE = BASE_URL + "article/list/";
    //体系模块下按作者搜索的文章
    public final static String SYSTEM_SEARCH_ARTICLE = BASE_URL + "article/list/";
    //导航模块下标签分类
    public final static String NAVIGATION_DATA = BASE_URL + "navi/json";
    //项目模块下的类别
    public final static String PROJECT_SORT = BASE_URL + "project/tree/json";
    //项目模块下的分类项目(https://www.wanandroid.com/project/list/1/json?cid=294)
    public final static String PROJECT_LIST = BASE_URL + "project/list/";

}
