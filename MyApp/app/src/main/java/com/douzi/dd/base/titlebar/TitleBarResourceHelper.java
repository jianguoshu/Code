package com.douzi.dd.base.titlebar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.douzi.dd.R;
import com.douzi.dd.utils.DeviceUtil;

/**
 * Created by douzi on 2017/6/15.
 */

public class TitleBarResourceHelper {

    public static final boolean SHOW_IMG_SRC_SHADOW = false;
    // TODO: 2017/6/16   这个值改为true在夜间模式下会有问题，后续解决
    public static final boolean SHOW_BG_SHADOW = false;

    static ImageView createBackImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
            case NOVEL_HOME:
                drawable = R.drawable.selector_title_bar_btn_back;
                break;
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                drawable = R.drawable.selector_title_bar_btn_back_lbs_home;
                break;
            default:
                drawable = R.drawable.selector_title_bar_btn_back;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static ImageView createCloseImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
            case NOVEL_HOME:
                drawable = R.drawable.selector_title_bar_btn_close;
                break;
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                drawable = R.drawable.selector_title_bar_btn_close_lbs_home;
                break;
            default:
                drawable = R.drawable.selector_title_bar_btn_close;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static ImageView createSearchImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
                drawable = R.drawable.selector_title_bar_btn_search;
                break;
            case LBS_HOME:
            case NOVEL_HOME:
            case APP_HOME:
            case PIC_NEWS:
                drawable = R.drawable.selector_title_bar_btn_search_lbs_home;
                break;
            default:
                drawable = R.drawable.selector_title_bar_btn_search;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static ImageView createRefreshImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
            case NOVEL_HOME:
                drawable = R.drawable.selector_title_bar_btn_refresh;
                break;
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                drawable = R.drawable.selector_title_bar_btn_refresh_lbs_home;
                break;
            default:
                drawable = R.drawable.selector_title_bar_btn_refresh;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static ImageView createShareImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
                drawable = R.drawable.selector_title_bar_btn_share;
                break;
            case LBS_HOME:
            case NOVEL_HOME:
            case APP_HOME:
            case PIC_NEWS:
                drawable = R.drawable.selector_title_bar_btn_share;
                break;
            default:
                drawable = R.drawable.selector_title_bar_btn_share;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    public static ImageView createFocusAddImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
            case LBS_HOME:
            case NOVEL_HOME:
            case APP_HOME:
            case PIC_NEWS:
            default:
                drawable = R.drawable.selector_title_bar_btn_focus_add;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static ImageView createFilterImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
            case LBS_HOME:
            case NOVEL_HOME:
            case APP_HOME:
            case PIC_NEWS:
            default:
                drawable = R.drawable.selector_title_bar_btn_focus_filter;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static ImageView createMenuImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        int drawable;
        switch (titleBarType) {
            case NORMAL:
            case NOVEL_HOME:
                drawable = R.drawable.selector_title_bar_btn_menu;
                break;
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                drawable = R.drawable.selector_title_bar_btn_menu_lbs_home;
                break;
            default:
                drawable = R.drawable.selector_title_bar_btn_menu;
        }
        return createImageBtn(context, drawable, parent, titleBarType);
    }

    static View createDividerView(Context context, int width, int weight, LinearLayout parent) {
        View view = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        if (weight > 0) {
            params.weight = weight;
        }
        if (parent != null) {
            parent.addView(view, params);
        }
        return view;
    }

    public static ImageView createNovelAccountImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        return createImageBtn(context, R.drawable.selector_title_bar_btn_novel_acount, parent, titleBarType);
    }

    public static ImageView createSetWallImageBtn(Context context, ViewGroup parent, TitleBar.Type titleBarType) {
        return createImageBtn(context, R.drawable.selector_title_bar_btn_wallpager, parent, titleBarType);
    }

    public static TextView createTextBtn(Context context, String text, TitleBar.Type titleBarType) {
        TextView btn = new TextView(context);
        btn.setPadding(DeviceUtil.dip2px(10), 0, DeviceUtil.dip2px(10), 0);
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.titlebar_btn_textsize));
        btn.setTextColor(context.getResources().getColorStateList(getBtnTextColorResource(titleBarType)));
        btn.setBackgroundResource(getImageBtnBgResourceByType(titleBarType));
        btn.setText(text);
        return btn;
    }

    public static int getTitleBarBgResource(TitleBar.Type titleBarType) {
        switch (titleBarType) {
            case NORMAL:
            case NOVEL_HOME:
                return R.color.title_bar_bg;
            case LBS_HOME:
                return R.color.title_bar_bg_lbs_home;
            case APP_HOME:
                return R.color.title_bar_bg_app;
            case PIC_NEWS:
                return R.color.title_bar_bg_pic_news;
            default:
                return R.color.title_bar_bg;
        }
    }

    public static int getTitleTextColorResource(TitleBar.Type titleBarType) {
        switch (titleBarType) {
            case NORMAL:
            case NOVEL_HOME:
                return R.color.title_bar_text_color;
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                return R.color.title_bar_title_text_color_lbs_home;
            default:
                return R.color.title_bar_text_color;
        }
    }

    public static int getBtnTextColorResource(TitleBar.Type titleBarType) {
        switch (titleBarType) {
            case NORMAL:
                return R.color.selector_title_bar_text_btn_color;
            case NOVEL_HOME:
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                return R.color.selector_title_bar_text_btn_color_lbs_home;
            default:
                return R.color.selector_title_bar_text_btn_color;
        }
    }

    private static ImageView createImageBtn(Context context, int imageResource, ViewGroup parent, TitleBar.Type titleBarType) {
        ImageView btn = (ImageView) LayoutInflater.from(context).inflate(R.layout.common_titlebar_btn, parent, false);
        if (parent != null) {
            parent.addView(btn);
        }
        btn.setBackgroundResource(getImageBtnBgResourceByType(titleBarType));
        btn.setImageResource(imageResource);
        return btn;
    }

    static int getImageBtnBgResourceByType(TitleBar.Type titleBarType) {
        switch (titleBarType) {
            case NORMAL:
                return R.drawable.selector_title_bar_bg;
            case NOVEL_HOME:
                return R.drawable.selector_title_bar_bg;
            case LBS_HOME:
                return R.drawable.selector_title_bar_bg_lbs_home;
            case APP_HOME:
                return R.drawable.selecter_title_bar_bg_app;
            case PIC_NEWS:
                return R.drawable.selector_title_bar_bg_pic_news;
            default:
                return R.drawable.selector_title_bar_bg;
        }
    }

    public static ITitleBarMenuBean createMenuBeanRefresh() {
        return new TitleBarMenuBean("刷新", R.drawable.icon_titlebar_menu_refresh, TitleBar.MenuType.refresh);
    }

    public static ITitleBarMenuBean createMenuBeanBookRackCloud() {
        return new TitleBarMenuBean("云书架", R.drawable.bookshelf_yun_home_ic, TitleBar.MenuType.bookcloud);
    }

    public static ITitleBarMenuBean createMenuBeanAddToDesk() {
        return new TitleBarMenuBean("添加至桌面", R.drawable.novel_shortcut_ic, TitleBar.MenuType.shortcut);
    }

    public static ITitleBarMenuBean createMenuBeanFeedBack() {
        return new TitleBarMenuBean("意见反馈", R.drawable.pop_mail_ic, TitleBar.MenuType.feedback);

    }

    public static ITitleBarMenuBean createMenuBeanScanLocalNovel() {
        return new TitleBarMenuBean("本机导入", R.drawable.novel_bookshelf_more_into_ic, TitleBar.MenuType.scanlocalnovel);

    }

    public static ITitleBarMenuBean createMenuBeanMyLBS() {
        return new TitleBarMenuBean("我的本地", R.drawable.icon_titlebar_menu_me, TitleBar.MenuType.me);
    }

    public static ITitleBarMenuBean createMenuBeanShare() {
        return new TitleBarMenuBean("分享", R.drawable.icon_titlebar_menu_share, TitleBar.MenuType.share);
    }

    public static ITitleBarMenuBean createMenuBeanReadHistory() {
        return new TitleBarMenuBean("阅读历史", R.drawable.novel_bookshelf_pop_history_ic, TitleBar.MenuType.history);
    }

    public static ITitleBarMenuBean createMenu(TitleBar.MenuType type) {
        switch (type) {
            case history: return createMenuBeanReadHistory();
            case share: return createMenuBeanShare();
            case me: return createMenuBeanMyLBS();
            case scanlocalnovel: return createMenuBeanScanLocalNovel();
            case feedback: return createMenuBeanFeedBack();
            case shortcut: return createMenuBeanAddToDesk();
            case bookcloud: return createMenuBeanBookRackCloud();
            case refresh: return createMenuBeanRefresh();
        }
        return null;
    }


    public static int getTitleBarDividerBg(TitleBar.Type type) {
        switch (type) {
            case NORMAL:
                return R.color.title_bar_divider;
            case NOVEL_HOME:
            case LBS_HOME:
            case APP_HOME:
            case PIC_NEWS:
                return R.color.title_bar_divider_lbs_home;
            default:
                return R.color.title_bar_divider;
        }
    }

    public static LinearLayout.LayoutParams generateImgBtnParams(Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.titlebar_item_width), LinearLayout.LayoutParams.MATCH_PARENT);
        return params;
    }

    public static LinearLayout.LayoutParams generateTextBtnParams(Context context) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return params;
    }

    private static class TitleBarMenuBean implements ITitleBarMenuBean {
        private String name;
        private int imageId;
        private int imageIdDefault;
        private String imageUrl;
        private TitleBar.MenuType type;

        /**
         * 默认的ImageView形式--在TitleBar中认为menubean中imageIdDefault imageUrl 全都无效才认为是此种方式
         *
         * @param name
         * @param imageId
         * @param type
         */
        public TitleBarMenuBean(String name, int imageId, TitleBar.MenuType type) {
            this(name, imageId, 0, "", type);
        }

        /**
         * 使用RemoteImageView时使用这个--在TitleBar中认为menubean中imageIdDefault imageUrl 任何一个有效都认为是此种方式
         *
         * @param name
         * @param imageIdDefault
         * @param imageUrl
         * @param type
         */
        public TitleBarMenuBean(String name, int imageIdDefault, String imageUrl, TitleBar.MenuType type) {
            this(name, 0, imageIdDefault, "", type);
        }

        private TitleBarMenuBean(String name, int imageId, int imageIdDefault, String imageUrl, TitleBar.MenuType type) {
            super();
            this.name = name;
            this.imageId = imageId;
            this.imageIdDefault = imageIdDefault;
            this.imageUrl = imageUrl;
            this.type = type;
        }

        public TitleBarMenuBean(Parcel source) {
            this.name = source.readString();
            this.imageId = source.readInt();
            this.imageIdDefault = source.readInt();
            this.imageUrl = source.readString();
            this.type = TitleBar.MenuType.valueOf(source.readString());
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        @Override
        public int getImageIdDefault() {
            return imageIdDefault;
        }

        public void setImageIdDefault(int imageIdDefault) {
            this.imageIdDefault = imageIdDefault;
        }

        @Override
        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public TitleBar.MenuType getType() {
            return type;
        }

        public void setType(TitleBar.MenuType type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(imageId);
            dest.writeInt(imageIdDefault);
            dest.writeString(imageUrl);
            dest.writeString(type.name());
        }

        public static final Parcelable.Creator<TitleBarMenuBean> CREATOR = new Parcelable.Creator<TitleBarMenuBean>() {

            @Override
            public TitleBarMenuBean createFromParcel(Parcel source) {
                return new TitleBarMenuBean(source);
            }

            @Override
            public TitleBarMenuBean[] newArray(int size) {
                return null;
            }
        };
    }

}
