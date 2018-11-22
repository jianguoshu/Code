package com.douzi.dd.base.titlebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.douzi.dd.R;
import com.douzi.dd.utils.DeviceUtil;
import com.douzi.dd.utils.ViewUtils;

import java.util.Arrays;
import java.util.List;

@SuppressLint("InflateParams")
public class TitleBar<T extends TitleBar> {

    protected final Type mType;
    protected final ViewGroup viewParent;
    protected Context mContext;
    protected View mContentView;

    private ImageView btnBack;
    private ImageView btnClose;
    private TextView title;
    private ImageView btnSearch;
    private ImageView btnRefresh;
    private ImageView btnShare;
    private ImageView btnMenu;
    protected T mSelf;
    private TitleBarMenu menu;
    protected LinearLayout btnContainerLeft;
    protected LinearLayout btnContainerRight;
    private View dividerView;

    /**
     * 用于区分不同的resource样式
     */
    public enum Type {
        NORMAL,
        LBS_HOME,
        NOVEL_HOME,
        APP_HOME,
        PIC_NEWS
    }

    public enum BtnId {
        back, close_left, close_right, refresh_left, refresh_right, search_left, search_right, share_left, share_right
    }

    public enum MenuType {
        me, homepage, refresh, shortcut, share, feedback, bookcloud, scanlocalnovel, history
    }

    public TitleBar(Context context, Type type, ViewGroup parent) {
        mSelf = (T) this;
        mContext = context;
        mType = type;
        viewParent = parent;
        initView();
    }

    public T title(String title) {
        setTitle(title);
        return mSelf;
    }

    public T title(String title, int maxWidth) {
        if (this.title != null) {
            this.title.setMaxWidth(maxWidth);
        }
        setTitle(title);
        return mSelf;
    }

    public void setTitle(String title) {
        if (this.title != null) {
            if (!TextUtils.isEmpty(title)) {
                this.title.setVisibility(View.VISIBLE);
                this.title.setText(title);
            } else {
                this.title.setVisibility(View.GONE);
            }
        }
    }

    public String getTitle() {
        if (this.title != null) {
            return String.valueOf(title.getText());
        }
        return "";
    }

    public T left(View view) {
        return left(view, TitleBarResourceHelper.generateImgBtnParams(mContext));
    }

    public T left(View view, LinearLayout.LayoutParams params) {
        btnContainerLeft.addView(view, params);
        return mSelf;
    }

    public T right(View view) {
        return right(view, TitleBarResourceHelper.generateImgBtnParams(mContext));
    }

    public T right(View view, LinearLayout.LayoutParams params) {
        btnContainerRight.addView(view, params);
        return mSelf;
    }

    public T left(final String text) {
        TextView view = TitleBarResourceHelper.createTextBtn(mContext, text, mType);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextBtnClick(text);
            }
        });
        return left(view, TitleBarResourceHelper.generateTextBtnParams(mContext));
    }

    public T right(final String text) {
        TextView view = TitleBarResourceHelper.createTextBtn(mContext, text, mType);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextBtnClick(text);
            }
        });
        return right(view, TitleBarResourceHelper.generateTextBtnParams(mContext));
    }

    public T back() {
        if (btnBack == null) {
            btnBack = TitleBarResourceHelper.createBackImageBtn(mContext, btnContainerLeft, mType);
            btnBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBack();
                }
            });
        }
        setBackAble(true);
        return mSelf;
    }

    public void setBackAble(boolean backAble) {
        if (btnBack != null) {
            btnBack.setVisibility(backAble ? View.VISIBLE : View.GONE);
        }
    }


    public void setCloseable(boolean closeable) {
        if (btnClose != null) {
            btnClose.setVisibility(closeable ? View.VISIBLE : View.GONE);
        }
    }

    public T closeLeft() {
        createBtnClose(btnContainerLeft);
        return mSelf;
    }

    public T closeRight() {
        createBtnClose(btnContainerRight);
        return mSelf;
    }

    private void createBtnClose(ViewGroup parent) {
        if (btnClose == null) {
            btnClose = TitleBarResourceHelper.createCloseImageBtn(mContext, parent, mType);
            btnClose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClose();
                }
            });
        }
        setCloseable(true);
    }

    public T searchLeft() {
        createBtnSearch(btnContainerLeft);
        return mSelf;
    }

    public T searchRight() {
        createBtnSearch(btnContainerRight);
        return mSelf;
    }

    private void createBtnSearch(ViewGroup parent) {
        if (btnSearch == null) {
            btnSearch = TitleBarResourceHelper.createSearchImageBtn(mContext, parent, mType);
            btnSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearch();
                }
            });
        }
        setSearchable(true);
    }

    public void setSearchable(boolean searchAble) {
        if (btnSearch != null) {
            btnSearch.setVisibility(searchAble ? View.VISIBLE : View.GONE);
        }
    }

    public T refreshLeft() {
        createBtnRefresh(btnContainerLeft);
        return mSelf;
    }

    public T refreshRight() {
        createBtnRefresh(btnContainerRight);
        return mSelf;
    }

    private void createBtnRefresh(ViewGroup parent) {
        if (btnRefresh == null) {
            btnRefresh = TitleBarResourceHelper.createRefreshImageBtn(mContext, parent, mType);
            btnRefresh.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }
        setRefreshAble(true);
    }

    public T shareLeft() {
        createBtnShare(btnContainerLeft);
        return mSelf;
    }

    public T shareRight() {
        createBtnShare(btnContainerRight);
        return mSelf;
    }

    private void createBtnShare(ViewGroup parent) {
        if (btnShare == null) {
            btnShare = TitleBarResourceHelper.createShareImageBtn(mContext, parent, mType);
            btnShare.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onShare();
                }
            });
        }
        setRefreshAble(true);
    }

    public void setShareAble(boolean shareAble) {
        if (btnRefresh != null) {
            btnRefresh.setVisibility(shareAble ? View.VISIBLE : View.GONE);
        }
    }

    public T dividerLeft(int width, int weight) {
        createDividerView(width, weight, btnContainerLeft);
        return mSelf;
    }

    public T dividerRight(int width, int weight) {
        createDividerView(width, weight, btnContainerRight);
        return mSelf;
    }

    private void createDividerView(int width, int weight, LinearLayout viewParent) {
        TitleBarResourceHelper.createDividerView(mContext, width, weight, viewParent);
    }

    public void setRefreshAble(boolean searchAble) {
        if (btnRefresh != null) {
            btnRefresh.setVisibility(searchAble ? View.VISIBLE : View.GONE);
        }
    }

    public T menus(TitleBar.MenuType ...menuBeanList) {
        return menus(menuBeanList != null ? Arrays.asList(menuBeanList) : null);
    }
    public T menus(List<MenuType> menuBeanList) {
        if (menuBeanList != null && menuBeanList.size() > 0) {
            createBtnMenu(btnContainerRight);
        } else {
            setMenuAble(false);
        }

        setMenus(menuBeanList);

        return mSelf;
    }

    private void createBtnMenu(ViewGroup parent) {
        if (btnMenu == null) {
            btnMenu = TitleBarResourceHelper.createMenuImageBtn(mContext, parent, mType);
            btnMenu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menu != null) {
                        if (menu.isTimeToShow) {
                            menu.show();
                            onMenu();
                        } else {
                            menu.isTimeToShow = true;
                        }
                    }
                }
            });
            btnMenu.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    if ((x < 0) || (x >= mContentView.getWidth()) || (y < 0) || (y >= mContentView.getHeight())) {
                        menu.isTimeToShow = true;
                    }
                    return false;
                }
            });
        }
        setMenuAble(true);
    }

    public void setMenuAble(boolean searchAble) {
        if (btnMenu != null) {
            btnMenu.setVisibility(searchAble ? View.VISIBLE : View.GONE);
        }
    }

    public void setMenus(TitleBar.MenuType ...menuBeanList) {
        setMenus(menuBeanList != null ? Arrays.asList(menuBeanList) : null);
    }
    public void setMenus(List<MenuType> menuBeanList) {
        if (menuBeanList != null && menuBeanList.size() > 0) {
            if (menu == null) {
                menu = new TitleBarMenu(mContext);
            }
            menu.setData(menuBeanList);
            setMenuAble(true);
        } else {
            setMenuAble(false);
        }
    }

    public View initView() {
//        switch (mType) {
//            case LBS_HOME:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_lbs_home, viewParent);
//                break;
//            case APP_HOME:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_appdownload, viewParent);
//                break;
//            case noveldetail:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_novel_detail, viewParent);
////                gotoNovelReader = (TextView) mContentView.findViewById(R.id.tv_goto_novel_reader);
//                // TODO: 2017/6/15
//                break;
//            case skincenter:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_skincenter, viewParent);
//                break;
//            case cartoon:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_cartoon, viewParent);
//                break;
//            case cartoonhome:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.cartoon_home_titlebar, viewParent);
//                break;
//            case myorder:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_my_order, viewParent);
//                break;
//            case NOVEL_HOME:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_default_new, viewParent);
//                break;
//            default:
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_default_new, viewParent);
//                break;
//        }

        mContentView = LayoutInflater.from(mContext).inflate(R.layout.common_titlebar_default_new, viewParent);

        if (viewParent != null) {
            mContentView = mContentView.findViewById(R.id.title_bar);
        }
        mContentView.setBackgroundResource(TitleBarResourceHelper.getTitleBarBgResource(mType));
        btnContainerLeft = (LinearLayout) mContentView.findViewById(R.id.ll_left);
        title = (TextView) mContentView.findViewById(R.id.tv_title);
        btnContainerRight = (LinearLayout) mContentView.findViewById(R.id.ll_right);
        dividerView = mContentView.findViewById(R.id.title_bar_divider);
        dividerView.setBackgroundResource(TitleBarResourceHelper.getTitleBarDividerBg(mType));

        return mContentView;
    }

    private class TitleBarMenu extends BaseHolder<List<MenuType>, Object> {

        private PopupWindow mPopupWindow;
        private LinearLayout menuContainer;
        private ImageView readPopRaw;
        private View.OnTouchListener mTouchInterceptor;
        private boolean isTimeToShow = true;

        public TitleBarMenu(Context context) {
            super(context);
        }

        @Override
        protected View initView() {
            mContentView = LayoutInflater.from(mContext).inflate(R.layout.popup_titlebar_menu, null);
            menuContainer = (LinearLayout) mContentView.findViewById(R.id.ll_menu_container);
            readPopRaw = (ImageView) mContentView.findViewById(R.id.read_pop_raw);
            initPopup();
            return mContentView;
        }

        @Override
        protected void refreshView() {
            menuContainer.removeAllViews();
            boolean isFirst = true;
            for (final ITitleBarMenuBean menuBean : TitleBarHelper.menus(mData)) {
                if (menuBean != null) {
                    LinearLayout menuItem = (LinearLayout) LayoutInflater.from(mContext).inflate(
                            R.layout.item_titlebar_menu, null);
                    TextView menuText = (TextView) menuItem.findViewById(R.id.tv_menu);
                    menuText.setText(menuBean.getName());
                    ImageView menuImage = menuItem.findViewById(R.id.iv_menu);
                    try {
                        mContext.getResources().getDrawable(menuBean.getImageIdDefault());
//                        ImageLoader.load(menuBean.getImageUrl()).placeholder(menuBean.getImageIdDefault()).into(menuImage);
                    } catch (NotFoundException e) {
                        if (!TextUtils.isEmpty(menuBean.getImageUrl())) {
//                            ImageLoader.load(menuBean.getImageUrl()).into(menuImage);
                        } else {
                            try {
                                mContext.getResources().getDrawable(menuBean.getImageId());
                                menuImage.setImageResource(menuBean.getImageId());
                            } catch (NotFoundException e1) {
                            }
                        }
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                            (int) mContext.getResources().getDimension(R.dimen.titlebar_menu_item_height));
                    menuItem.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onMenuItemClick(menuBean.getType());
                            dismiss();
                        }
                    });
                    if (isFirst) {
                        ViewUtils.bindClickState(menuItem, new View[]{menuItem, readPopRaw});
                        isFirst = false;
                    } else {
                        View divider = new View(mContext);
                        divider.setBackgroundResource(R.drawable.home_menu_divider);
                        LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                                LayoutParams.MATCH_PARENT, DeviceUtil.dip2px(0.6f));
                        divider.setLayoutParams(dividerParams);
                        menuContainer.addView(divider, dividerParams);
                    }
                    menuContainer.addView(menuItem, params);

                }
            }
        }

        public void show() {
            mPopupWindow.showAsDropDown(TitleBar.this.mContentView, TitleBar.this.mContentView.getWidth()
                    - mContentView.getMeasuredWidth(), 0);
        }

        public void dismiss() {
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
            }
        }

        @SuppressWarnings("deprecation")
        private void initPopup() {
            if (mPopupWindow != null) {
                return;
            }
            mPopupWindow = new PopupWindow(mContentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setTouchable(true);
            mPopupWindow.setFocusable(false);
            mPopupWindow.setOutsideTouchable(true);

            mTouchInterceptor = new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int x = (int) event.getX();
                    final int y = (int) event.getY();

                    if ((event.getAction() == MotionEvent.ACTION_DOWN)
                            && ((x < 0) || (x >= mContentView.getWidth()) || (y < 0) || (y >= mContentView.getHeight()))) {
                        dismiss();
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        if (ViewUtils.isViewTouchedIn(TitleBar.this.btnMenu, event, false)) {
                            isTimeToShow = false;
                        }
                        dismiss();
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            mPopupWindow.setTouchInterceptor(mTouchInterceptor);
        }
    }

    public ImageView getBtnBack() {
        return btnBack;
    }

    public ImageView getBtnClose() {
        return btnClose;
    }

    public ImageView getBtnSearch() {
        return btnSearch;
    }

    public ImageView getBtnRefresh() {
        return btnRefresh;
    }

    public ImageView getBtnMenu() {
        return btnMenu;
    }

    public View getTitleBarView() {
        return mContentView;
    }

    public void onBack() {
    }

    public void onClose() {
    }

    public void onSearch() {
    }

    public void onRefresh() {
    }

    public void onShare() {
    }

    public void onMenu() {
    }

    public void onMenuItemClick(TitleBar.MenuType type) {
    }

    public void onTextBtnClick(String text) {

    }
}
