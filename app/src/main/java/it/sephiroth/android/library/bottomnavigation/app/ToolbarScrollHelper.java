package it.sephiroth.android.library.bottomnavigation.app;

import android.animation.Animator;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import it.sephiroth.android.library.bottomnavigation.MiscUtils;

/**
 * Created by crugnola on 6/22/16.
 * BottomNavigation
 */

public class ToolbarScrollHelper
    extends RecyclerView.OnScrollListener
    implements View.OnAttachStateChangeListener, View.OnLayoutChangeListener {

    private static final String TAG = ToolbarScrollHelper.class.getSimpleName();
    private static final int ANIMATION_DURATION = 150;
    private final ScrollHelper scrollHelper;
    private int toolbarHeight;
    private Toolbar toolbar;
    private boolean expanding;
    private boolean collapsing;
    private boolean dragging;
    private boolean enabled;

    public ToolbarScrollHelper(@NonNull final Activity activity, @NonNull final Toolbar toolbar) {
        this.enabled = false;
        this.scrollHelper = new ScrollHelper();
        this.toolbar = toolbar;
        this.toolbarHeight = setupToolbar(activity);

        if (this.toolbar != null)
            this.toolbar.addOnLayoutChangeListener(this);

        if (toolbarHeight > 0) {
            scrollHelper.setRange(-toolbarHeight, 0);
            enabled = true;
        }
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    static int count = 0;
    private int setupToolbar(final Activity activity) {
        SystemBarTintManager manager = new SystemBarTintManager(activity);
        final SystemBarTintManager.SystemBarConfig config = manager.getConfig();
        if (config.getPixelInsetTop(false) > 0 && toolbar != null) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();

            params.topMargin = -100;
            params.height = config.getActionBarHeight() + config.getStatusBarHeight() + 50;

            //hamburger menu
            if (toolbar != null)
                toolbar.setLayoutParams(params);

            if (count <= 1) {
                if (toolbar != null)
                    toolbar.setPadding(
                            toolbar.getPaddingLeft(),
                            toolbar.getPaddingTop() + config.getStatusBarHeight(),
                            toolbar.getPaddingRight(),
                            toolbar.getPaddingBottom()
                );
                count++;
            }

            return params.height;
        }
        return config.getActionBarHeight();

    }

    public int getToolbarHeight() {
        return toolbarHeight;
    }

    public void initialize(@NonNull final RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(this);
        recyclerView.addOnAttachStateChangeListener(this);
    }

    @Override
    public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!enabled) {
            return;
        }

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            dragging = false;

            if (scrollHelper.inRange()) {
                expand(true);
            } else {
                if (scrollHelper.getCurrentScroll() > -toolbarHeight / 2) {
                    if (!expanding) {
                        expand(true);
                    }
                } else if (scrollHelper.getCurrentScroll() < -toolbarHeight / 2) {
                    if (!collapsing) {
                        collapse(true);
                    }
                }
            }
        }
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!enabled) {
            return;
        }

        scrollHelper.scroll(-dy);

        if (scrollHelper.inRange() || dragging) {
            // MiscUtils.log(TAG, Log.DEBUG, "inRange: " + toolbar.getTranslationY() + ", " + scrollHelper.getCurrentScroll());
            if (toolbar != null) {
                toolbar.setTranslationY(scrollHelper.clamp(toolbar.getTranslationY() - dy));
                dragging = scrollHelper.valueInRange(toolbar.getTranslationY());
            }else {

            }
        } else {
            if (dy < 0 && scrollHelper.getCurrentScroll() > -toolbarHeight / 2) {
                if (!expanding) {
                    expand(true);
                }
            } else if (dy > 0 && scrollHelper.getCurrentScroll() < -toolbarHeight / 2) {
                if (!collapsing) {
                    collapse(true);
                }
            }
        }
    }

    private void expand(final boolean animate) {
        if (animate) {
            expanding = true;
            if (toolbar != null) {
                toolbar.animate().cancel();
                toolbar
                        .animate()
                        .translationY(0)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(final Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(final Animator animation) {
                                onAnimationCompleted();
                                expanding = false;
                            }

                            @Override
                            public void onAnimationCancel(final Animator animation) {
                                onAnimationCompleted();
                                expanding = false;
                            }

                            @Override
                            public void onAnimationRepeat(final Animator animation) {

                            }
                        })
                        .setDuration(ANIMATION_DURATION)
                        .start();
            }
        } else {
            if (toolbar != null) {
                toolbar.setTranslationY(0);
            }
            onAnimationCompleted();
        }
    }

    private void collapse(final boolean animate) {
        if (animate) {
            collapsing = true;
            if (toolbar != null) {
                toolbar.animate().cancel();
                toolbar
                        .animate()
                        .translationY(-toolbarHeight)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(final Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(final Animator animation) {
                                onAnimationCompleted();
                                collapsing = false;
                            }

                            @Override
                            public void onAnimationCancel(final Animator animation) {
                                onAnimationCompleted();
                                collapsing = false;
                            }

                            @Override
                            public void onAnimationRepeat(final Animator animation) {

                            }
                        })
                        .start();
            }
        } else {
            if (toolbar != null) {
                toolbar.setTranslationY(-toolbarHeight);
            }
            onAnimationCompleted();
        }
    }

    private void onAnimationCompleted() {
        if (toolbar != null) {
            scrollHelper.setCurrent(toolbar.getTranslationY());
        }
    }

    public boolean isCollapsing() {
        return collapsing;
    }

    public boolean isExpanded() {
        if (isAnimating()) {
            return isExpanding();
        } else {
            if (toolbar != null) {
                return toolbar.getTranslationY() == 0;
            }else {
                return false;
            }
        }
    }

    public boolean isAnimating() {
        return expanding || collapsing;
    }

    public boolean isExpanding() {
        return expanding;
    }

    public void setExpanded(boolean expanded, boolean animate) {
        if (!enabled) {
            return;
        }
        if (expanded) {
            expand(animate);
        } else {
            collapse(animate);
        }
    }

    @Override
    public void onViewAttachedToWindow(final View v) { }

    @Override
    public void onViewDetachedFromWindow(final View v) {
        MiscUtils.INSTANCE.log(Log.INFO, "onViewDetachedFromWindow: " + v);

        ((RecyclerView) v).removeOnScrollListener(this);

        if (null != toolbar) {
            this.toolbar.removeOnLayoutChangeListener(this);
        }
        this.toolbar = null;
        this.enabled = false;
    }

    @Override
    public void onLayoutChange(
        final View v, final int left, final int top, final int right, final int bottom, final int oldLeft, final int oldTop,
        final int oldRight, final int oldBottom) {
        final int height = bottom - top;
        if (height > 0 && height != toolbarHeight) {
            MiscUtils.INSTANCE.log(Log.VERBOSE, "height: " + height);
            toolbarHeight = height;
            enabled = true;
            scrollHelper.setRange(-toolbarHeight, 0);
        }
    }

    public static class ScrollHelper {
        float max;
        float min;
        float total;
        float current;

        public void setRange(float min, float max) {
            setMin(min);
            setMax(max);
        }

        public void setMin(final float min) {
            this.min = min;
        }

        public void setMax(final float max) {
            this.max = max;
        }

        public boolean inRange() {
            return total <= max && total >= min;
        }

        public void setCurrent(float current) {
            this.current = clamp(current);
        }

        public float clamp(float value) {
            return Math.max(min, Math.min(max, value));
        }

        public void scroll(float dy) {
            total += dy;
            current = clamp(current + dy);
        }

        public float getCurrentScroll() {
            return current;
        }

        public float getTotalScroll() {
            return total;
        }

        public boolean valueInRange(float value) {
            return value > min || value < max;
        }
    }
}
