package com.douzi.dd.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.douzi.dd.R;

public class CollapsibleFlowLayout extends FlowLayout implements Collapsible {

    public static final int COLLAPSE_LINE_VALID = -1;
    public static final boolean COLLAPSED_DEF = true;
    private int collapsedLine = COLLAPSE_LINE_VALID;
    private boolean isCollapsed = COLLAPSED_DEF;
    private boolean isCollapsible = false;
    private int measureHeight;
    private int collapsedHeight;
    private int childEndPosition;

    public CollapsibleFlowLayout(Context context) {
        this(context, null);
    }

    public CollapsibleFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollapsibleFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleFlowLayout, R.attr.CollapsibleFlowLayoutStyle,
                R.style.CollapsibleFlowLayout);
        isCollapsed = typedArray.getBoolean(R.styleable.CollapsibleFlowLayout_collapsedLine, COLLAPSED_DEF);
        setCollapsedLine(typedArray.getInt(R.styleable.CollapsibleFlowLayout_collapsedLine, COLLAPSE_LINE_VALID));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updateCollapsibleState();
        childEndPosition = -1;
        collapsedHeight = 0;
        if (isCollapsible()) {
            for (int i = 1; i <= collapsedLine; i++) {
                FlowLayoutHelper.Line line = measureResult.lines.get(i);
                collapsedHeight += line.height;
                childEndPosition += line.childNum;
            }
        }
        measureHeight = measureResult.measureHeight + getPaddingTop() + getPaddingBottom();

        if (isCollapsed()) {
            collapse(false);
        } else {
            expand(false);
        }
    }

    public void setCollapsedLine(int collapsedLine) {
        if (collapsedLine < 0) {
            collapsedLine = COLLAPSE_LINE_VALID;
        }
        this.collapsedLine = collapsedLine;
        updateCollapsibleState();
    }

    private void updateCollapsibleState() {
        isCollapsible = collapsedLine < maxLine && measureResult.lineNum > collapsedLine;
    }

    @Override
    public void setMaxLine(int maxLine) {
        super.setMaxLine(maxLine);
        updateCollapsibleState();
    }

    @Override
    public void expand(boolean anim) {
        if (!isCollapsible()) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = measureHeight;
        setLayoutParams(layoutParams);
        isCollapsed = false;
        if (stateListener != null) {
            stateListener.onExpend();
        }
    }

    @Override
    public void collapse(boolean anim) {
        if (!isCollapsible()) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = collapsedHeight;
        setLayoutParams(layoutParams);
        isCollapsed = true;
        if (stateListener != null &&  (childEndPosition < 0 || childEndPosition >= getChildCount())) {
            stateListener.onCollapse(childEndPosition);
        }
    }

    public boolean isCollapsible() {
        return isCollapsible;
    }

    @Override
    public boolean isCollapsed() {
        return isCollapsed;
    }

    CollapseStateListener stateListener;

    public void setStateListener(CollapseStateListener stateListener) {
        this.stateListener = stateListener;
    }

    interface CollapseStateListener {
        void onExpend();

        void onCollapse(int childVisualCount);
    }
}
