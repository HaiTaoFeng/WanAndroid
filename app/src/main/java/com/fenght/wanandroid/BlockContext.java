package com.fenght.wanandroid;

import com.github.moduth.blockcanary.BlockCanaryContext;

public class BlockContext extends BlockCanaryContext {

    /**
     * Implement in your project.
     *
     * @return Qualifier which can specify this installation, like version + flavor.
     */
    @Override
    public String provideQualifier() {
        return "unknow";
    }


    /**
     * @return 用户ID
     */
    @Override
    public String provideUid() {
        return "uid";
    }

    /**
     * 网络类型
     * @return {@link String} like 2G, 3G, 4G, wifi, etc.
     */
    public String provideNetworkType() {
        return "unknown";
    }

    /**
     * 日志保存路径
     * @return 日志路径
     */
    @Override
    public String providePath() {
        return "/Block/";
    }
}
