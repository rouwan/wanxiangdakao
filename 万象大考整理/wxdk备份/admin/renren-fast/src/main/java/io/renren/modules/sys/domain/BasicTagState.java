package io.renren.modules.sys.domain;

public class BasicTagState {
    /// <summary>
    /// 成功状态
    /// </summary>
    public static int TagSuccess = 1;

    /// <summary>
    /// 重复状态
    /// </summary>
    public static int TagRepeat = 4;

    /// <summary>
    /// 没有用户信息 状态
    /// </summary>
    public static int TagSignOut = -8;

    /// <summary>
    /// 失败状态
    /// </summary>
    public static int TagFailure = -1;

    /// <summary>
    /// 异常状态
    /// </summary>
    public static int TagAbnormal = -2;

    /// <summary>
    /// 缓存过期
    /// </summary>
    public static int TagCacheExpired = -3;

    /// <summary>
    /// 活动专用--失败
    /// </summary>
    public static int ActivityFail = 20;
}
