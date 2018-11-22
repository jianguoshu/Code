package com.douzi.dd.base.titlebar;

import com.douzi.dd.utils.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DouZi on 2018/3/19.
 */

public class TitleBarHelper {
    public static ArrayList<TitleBar.BtnId> btns(TitleBar.BtnId... ids) {
        if (ids != null) {
            ArrayList<TitleBar.BtnId> idList = new ArrayList<>();
            for (TitleBar.BtnId id : ids) {
                idList.add(id);
            }
            return idList;
        }
        return null;
    }

    public static ArrayList<ITitleBarMenuBean> menus(TitleBar.MenuType ...menus) {
        return menus != null ? menus(Arrays.asList(menus)) : null;
    }

    public static ArrayList<ITitleBarMenuBean> menus(List<TitleBar.MenuType> menus) {
        if (menus != null) {
            ArrayList<ITitleBarMenuBean> idList = new ArrayList<>();
            for (TitleBar.MenuType menu : menus) {
                ITitleBarMenuBean menuBean = TitleBarResourceHelper.createMenu(menu);
                if (menuBean != null) {
                    idList.add(menuBean);
                }
            }
            return idList;
        }
        return null;
    }

    public static void initByBtnIds(TitleBar titleBar, List<? extends TitleBar.BtnId> idList) {
        if (!ListUtils.isEmpty(idList)) {
            for (TitleBar.BtnId id : idList) {
                switch (id) {
                    case back:
                        titleBar.back();
                        break;
                    case close_left:
                        titleBar.closeLeft();
                        break;
                    case close_right:
                        titleBar.closeRight();
                        break;
                    case search_left:
                        titleBar.searchLeft();
                        break;
                    case search_right:
                        titleBar.searchRight();
                        break;
                    case refresh_left:
                        titleBar.refreshLeft();
                        break;
                    case refresh_right:
                        titleBar.refreshRight();
                        break;
                    case share_left:
                        titleBar.shareLeft();
                        break;
                    case share_right:
                        titleBar.shareRight();
                        break;
                }
            }
        }
    }
}
