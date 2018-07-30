package com.lfp.ardf.util;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * <pre>
 * desc:
 *      应用图标上显示小红点
 *      https://github.com/leolin310148/ShortcutBadger
 *
 * function:
 *      setBadge()          :添加角标
 *      removeBadge()       :移除角标
 *
 * Created by LiFuPing on 2018/7/30.
 * </pre>
 */
public class BadgeUtils {
    private BadgeUtils() {
    }

    /**
     * 添加角标
     *
     * @param count 显示数字
     * @return {@code true} 添加角标成功
     */
    public static boolean setBadge(int count) {
        return ShortcutBadger.applyCount(AppUtils.getApp(), count);
    }

    /**
     * 移除角标
     *
     * @return {@code true} 移除角标成功
     */
    public static boolean removeBadge() {
        return ShortcutBadger.removeCount(AppUtils.getApp());
    }
}
