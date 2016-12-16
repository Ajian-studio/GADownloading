package com.gastudio.gadownloading.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.gastudio.gadownloading.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: GAStudio
 * qq:1935400187
 * 技术交流qq群:277582728
 */
public class GADownloadingView extends View {

    private static final int ZERO_PROGRESS = 0;
    private static final int FULL_PROGRESS = 100;
    private static final int HALF_PROGRESS = 50;
    private static final int INVALID_PROGRESS = -1;
    private static final float FULL_NORMALIZED_TIME = 1F;

    private static final float FULL_NORMALIZED_PROGRESS = 1F;
    private static final float HALF_NORMALIZED_PROGRESS = 0.5F;
    private static final String FULL_PROGRESS_STR = "100%";
    private static final String FULL_PROGRESS_DONE_STR = "done";
    private static final String FAILED_PROGRESS_STR = "failed";

    private static final int FULL_ALPHA = 255;
    private static final int FULL_ANGLE = 360;
    private static final int HALF_FULL_ANGLE = FULL_ANGLE / 2;

    private static final int DEFAULT_ARROW_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_LOADING_CIRCLE_BG_COLOR = 0xFF491C14;
    private static final int DEFAULT_LOADING_LINE_COLOR = 0xFF491c14;
    private static final int DEFAULT_PROGRESS_LINE_LEFT_COLOR = 0XFFFFFFFF;
    private static final int DEFAULT_PROGRESS_TEXT_COLOR = 0xFF000000;
    private static final int DEFAULT_DONE_PROGRESS_TEXT_COLOR = 0xFF5F9C62;

    private static final int BEFORE_PROGRESS_CIRCLE_SCALE_DURATION = 450;
    private static final int BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DURATION
            = BEFORE_PROGRESS_CIRCLE_SCALE_DURATION / 2;
    private static final int BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DELAY
            = BEFORE_PROGRESS_CIRCLE_SCALE_DURATION - BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DURATION;
    private static final int BEFORE_PROGRESS_CIRCLE_TO_LINE_DURATION = 150;
    private static final int BEFORE_PROGRESS_ARROW_MOVE_AND_LINE_OSCILL = 800;

    private static final float[] CIRCLE_TO_LINE_SEASONS = new float[]{0, 0.4f, 0.8f, 1f};
    private static final float[] CIRCLE_TO_LINE_WIDTH_FACTOR = new float[]{1f, 1.2f, 2.4f, 3.45f};
    private static final float[] CIRCLE_TO_LINE_HEIGHT_FACTOR = new float[]{1f, 0.73f, 0.36f, 0f};
    private static final float[] CIRCLE_TO_LINE_FST_CON_X_FACTOR = new float[]{-0.65f, 0.18f, 0.72f, 1.04f};
    private static final float[] CIRCLE_TO_LINE_FST_CON_Y_FACTOR = new float[]{0f, 0.036f, 0f, 0f};
    private static final float[] CIRCLE_TO_LINE_SEC_CON_X_FACTOR = new float[]{-0.65f, 0.06f, 0.72f, 1.04f};
    private static final float[] CIRCLE_TO_LINE_SEC_CON_Y_FACTOR = new float[]{1f, 0.73f, 0.36f, 0f};
    // value of MAX_LINE_WIDTH_FACTOR is max value in CIRCLE_TO_LINE_WIDTH_FACTOR array
    public static final float MAX_LINE_WIDTH_FACTOR = 3.45f;

    private static final int FULL_PROGRESS_ANIMATION_DURATION = 3000;
    private static final int MIN_PROGRESS_ANIMATION_DURATION = 100;

    private static final int DONE_ANIMATION_DURATION = 500;
    private static final int DONE_LINE_PACK_UP_ANIMATION_DURATION = 500;
    private static final int DONE_LINE_PACK_UP_ARROW_SHAKE_ANIMATION_DURATION = 500;
    private static final int DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION = 1000;
    private static final int DONE_DIALOG_TO_ARROW_DURATION
            = DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION / 4;
    private static final int DONE_DIALOG_UP_DOWN_DURATION
            = DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION - DONE_DIALOG_TO_ARROW_DURATION;


    private static final int DONE_LINE_PACK_UP_ARROW_SHAKE_BASE_POINT_DIAMETER = 2;
    private static final int FAILED_ANIMATION_DURATION = 1000;
    private static final int FAILED_BOMB_ANIMATION_DURATION =
            FAILED_ANIMATION_DURATION / 3;
    private static final int FAILED_ROPE_OSCILLATION_ANIMATION_DURATION = 250;
    private static final int FAILED_ARROW_MOVE_ANIMATION_DURATION = 800;
    private static final int FAILED_ROPE_PACK_UP_ANIMATION_DURATION =
            (FAILED_ARROW_MOVE_ANIMATION_DURATION - FAILED_ROPE_OSCILLATION_ANIMATION_DURATION) / 2;
    private static final int FAILED_CIRCLE_SCALE_ANIMATION_DURATION =
            (FAILED_ARROW_MOVE_ANIMATION_DURATION - FAILED_ROPE_OSCILLATION_ANIMATION_DURATION) / 2;

    // state of arrow's rectangle and triangle
    private static final float DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO = 1F;
    private static final float DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_MIDDLE_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.7F;
    private static final float DEFAULT_END_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO = 1F;
    private static final float DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.5F;
    private static final float DEFAULT_END_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO = 0.34F;
    private static final float DEFAULT_END_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO = 0.17F;
    private static final float DEFAULT_ARROW_TOP_CONNER_RADIUS_TO_CIRCLE_RADIUS_RATIO = 0.05f;

    private static final float DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO = MAX_LINE_WIDTH_FACTOR / 2
            + (DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO
            + DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO) / 4;

    private static final float SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO = 0.75F;
    private static final int SUGGEST_LOADING_VIEW_WIDTH = 300;
    private static final int SUGGEST_LOADING_VIEW_HEIGHT = (int) (SUGGEST_LOADING_VIEW_WIDTH
            / (SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO + MAX_LINE_WIDTH_FACTOR)
            * DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO);

    // progress text size and line stroke width
    private static final int DEFAULT_PROGRESS_TEXT_SIZE = 12;
    private static final int MIN_PROGRESS_TEXT_SIZE = 8;
    private static final float PROGRESS_TEXT_SIZE_TO_VIEW_WIDTH_RATIO
            = (float) DEFAULT_PROGRESS_TEXT_SIZE / SUGGEST_LOADING_VIEW_WIDTH;
    private static final int DEFAULT_LOADING_LINE_STROKE_WIDTH = 3;
    private static final int MIN_LOADING_LINE_STROKE_WIDTH = 1;
    private static final float LOADING_LINE_STROKE_WIDTH_TO_VIEW_WIDTH_RATIO
            = (float) DEFAULT_LOADING_LINE_STROKE_WIDTH / SUGGEST_LOADING_VIEW_WIDTH;

    private static final float CHANGE_ARROW_TO_DIALOG_RECT_CHANGE = 0.563F;
    private static final float CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME = 0.625F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_1 = 0.313F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_2 = 0.626F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_3 = 0.813F;
    private static final float ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_4 = 1F;

    private static final int ROTATE_ARROW_TO_DIALOG_INIT_ANGLE = 0;
    private static final int ROTATE_ARROW_TO_DIALOG_ANGLE_1 = -30;
    private static final int ROTATE_ARROW_TO_DIALOG_ANGLE_2 = 20;
    private static final int ROTATE_ARROW_TO_DIALOG_ANGLE_3 = -10;

    private static final float DEFAULT_PROGRESS_MAX_HEIGHT_TO_BASELINE_RATIO = 0.1F;
    private static final float DEFAULT_LINE_OSCILLATION_MAX_HEIGHT_TO_BASELINE_RATIO = 0.15F;
    private static final float ARROW_BOUNCE_LENGTH_RATIO = 0.2F;
    private static final float ARROW_BOUNCE_LENGTH_RATIO_2 = 0.1F;

    // Animation State
    private static final int STATE_BEFORE_PROGRESS_CIRCLE_SCALE = 1;
    private static final int STATE_BEFORE_PROGRESS_INNER_CIRCLE_SCALE = 2;
    private static final int STATE_BEFORE_PROGRESS_CIRCLE_TO_LINE = 3;
    private static final int STATE_BEFORE_PROGRESS_ARROW_MOVE_LINE_OSCILL = 4;

    private static final int STATE_IN_PROGRESS = 5;
    private static final int STATE_DONE_ROTATE = 6;
    private static final int STATE_DONE_LINE_PACK_UP = 7;
    private static final int STATE_DONE_ARROW_SHAKE = 8;
    private static final int STATE_DONE_REST_TO_CIRCLE = 9;

    private static final int STATE_FAILED_ARROW_SHAKE = 10;
    private static final int STATE_FAILED_LINE_OSCILL = 11;
    private static final int STATE_FAILED_LINE_PACK_UP = 12;
    private static final int STATE_FAILED_CIRCLE_SCALE = 13;

    private static final int DONE_LINE_PACK_UP_ARROW_ANGLE = 10;

    // other
    private Paint mDefaultPaint;
    private Xfermode mXfermode;
    private Camera mCamera;
    private List<Animator> mAnimatorList;

    // loadingView
    private int mLoadingViewCenterX;
    private int mLoadingViewCenterY;
    private int mLoadingViewWidth;
    private int mLoadingViewHeight;

    // arrow
    private Path mArrowPath;
    private RectF mArrowRectF;
    private int mInitArrowRectWidth;
    private int mInitArrowRectHeight;
    private int mInitArrowTriWidth;
    private int mInitArrowTriHeight;
    private int mInitArrowJointConnerRadius;
    private int mEndArrowRectHeight;
    private int mLastArrowRectWidth;
    private int mLastArrowRectHeight;
    private int mLastArrowTriWidth;
    private int mLastArrowTriHeight;
    private int mLastArrowOffsetX;
    private int mLastArrowOffsetY;
    private float[] mChangeArrowToDialogParamters = new float[5];
    private Matrix mArrowRotateMatrix;

    // arrow move
    private float[] mArrowMovePoint;
    private Path mArrowMovePath;
    private Rect mArrowMovePathRect;
    private PathMeasure mArrowPathMeasure;
    private float mArrowMovePathLength;

    // arrow bounce
    private Path mArrowBouncePath;
    private PathMeasure mArrowBouncePathMeasure;
    private float mArrowBouncePathLength;

    // circle
    private RectF mCircleRectF;
    private int mCircleRadius;
    private int mCircleDiameter;

    // line
    private Path mOscillationLinePath;
    private Paint mBaseLinePaint;
    private Path mBaseLinePath;
    private RectF mBaseLineRectF;
    private int mBaseLineLen;
    private int mHalfBaseLineLen;
    private int mBaseLineStrokeWidth;
    private int mBaseLineX;
    private int mBaseLineY;
    private int mBaseLineCenterX;
    private int mProgressLineMaxHeight;
    private int mLineOscillationMaxHeight;

    // progress text
    private Paint mProgressTextPaint;
    private Rect mProgressTextRect;
    private int mProgressTextSize;

    // before progress
    private AnimatorSet mBefProgressAnimatorSet;
    private ValueAnimator mBefProgCircleScaleAnimator;
    private float mBefProgCircleScalingFactor;
    private ValueAnimator mBefProgInnerCircleScaleAnimator;
    private float mBefProgInnerCircleScalingFactor;
    private ValueAnimator mBefProgCircleToLineAnimator;
    private float mBefProgCircleToLineNormalizedTime;
    private ValueAnimator mBefProgArrowMoveAnimator;
    private float mBefProgArrowMoveNormalizedTime;
    private ValueAnimator mBefProgLineOscillAnimator;
    private float mBefProgLineOscillationFactor;

    // in progress
    private float mProgressValue;
    private int mLastProgress;
    private int mNextProgress;
    private ValueAnimator mProgressAnimator;
    private RectF mProgressLinePathRectF;
    private int mLastValidProgress;
    private String mLastValidProgressTextStr;

    // done
    private AnimatorSet mDoneAnimatorSet;

    private ValueAnimator mDoneRotateAnimator;
    private float mDoneRotateNormalizedTime;

    private ValueAnimator mDoneLinePackUpAnimator;
    private float mDoneLinePackUpNormalizedTime;

    private ValueAnimator mDoneArrowShakeAnimator;
    private float mDoneArrowShakeAngle;

    private ValueAnimator mDoneRestToCircleAnimator;
    private float mDoneRestToCircleScalingFactor;

    private ValueAnimator mDoneDialogToArrowAnimator;
    private float mDoneDialogToArrowNormalizedTime;

    private ValueAnimator mDoneDialogToArrowUpDownAnimator;
    private float mDoneDialogToArrowUpDownFactor;

    //fail
    private boolean mIsFailed;
    private AnimatorSet mFailedAnimatorSet;

    private ValueAnimator mFailedArrowShakeAnimator;
    private float mFailedArrowUpAndDownFactor;
    private ValueAnimator mFailedArrowRotateAnimator;
    private float mFailedArrowRotateAngle;

    private ValueAnimator mFailedLineOscillationAnimator;
    private float mFailedLineOscillationFactor;

    private ValueAnimator mFailedArrowMoveAnimator;
    private float mFailedArrowMoveNormalizeTiem;

    private ValueAnimator mFailedLinePackUpAnimator;
    private float mFailedLinePackUpFactor;

    private ValueAnimator mFailedCircleScaleAnimator;
    private float mFailedCircleScaleFactor;

    private ValueAnimator mFailedBombAnimator;
    private float mFailedBombAnimatorPer;
    private Paint mFailedBombPaint;
    private Paint mFailedBombBellowPaint;
    private Path mFailedBombPath;
    private Path mFailedBombPathBellow;

    private int mCurrentState;
    private int mArrowColor = DEFAULT_ARROW_COLOR;
    private int mLoadingCircleBackColor = DEFAULT_LOADING_CIRCLE_BG_COLOR;
    private int mLoadingLineColor = DEFAULT_LOADING_LINE_COLOR;
    private int mProgressLineColor = DEFAULT_PROGRESS_LINE_LEFT_COLOR;
    private int mProgressTextColor = DEFAULT_PROGRESS_TEXT_COLOR;
    private int mDoneTextColor = DEFAULT_DONE_PROGRESS_TEXT_COLOR;

    public GADownloadingView(Context context) {
        this(context, null);
    }

    public GADownloadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GADownloadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.GADownloadingView, defStyleAttr, 0);
        mArrowColor = typedArray.getColor(R.styleable.GADownloadingView_arrow_color, DEFAULT_ARROW_COLOR);
        mLoadingCircleBackColor = typedArray.getColor(R.styleable.GADownloadingView_loading_circle_back_color, DEFAULT_LOADING_CIRCLE_BG_COLOR);
        mLoadingLineColor = typedArray.getColor(R.styleable.GADownloadingView_loading_line_color, DEFAULT_LOADING_LINE_COLOR);
        mProgressLineColor = typedArray.getColor(R.styleable.GADownloadingView_progress_line_color, DEFAULT_PROGRESS_LINE_LEFT_COLOR);
        mProgressTextColor = typedArray.getColor(R.styleable.GADownloadingView_progress_text_color, DEFAULT_PROGRESS_TEXT_COLOR);
        mDoneTextColor = typedArray.getColor(R.styleable.GADownloadingView_done_text_color, DEFAULT_DONE_PROGRESS_TEXT_COLOR);

        typedArray.recycle();

        init();
    }

    private void init() {
        mDefaultPaint = new Paint();
        mDefaultPaint.setAntiAlias(true);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        mBaseLinePaint = new Paint();
        mBaseLinePaint.setAntiAlias(true);
        mBaseLinePaint.setStyle(Paint.Style.STROKE);
        mBaseLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mBaseLinePaint.setPathEffect(new CornerPathEffect(10f));
        mBaseLinePath = new Path();
        mBaseLineRectF = new RectF();

        mArrowPath = new Path();
        mCircleRectF = new RectF();
        mArrowRectF = new RectF();

        mIsFailed = false;
        mNextProgress = ZERO_PROGRESS;
        mLastValidProgressTextStr = "";
        mLastValidProgress = INVALID_PROGRESS;
        mProgressTextPaint = new Paint();
        mProgressTextPaint.setColor(mProgressTextColor);
        mProgressTextPaint.setAntiAlias(true);
        mProgressTextRect = new Rect();

        mLoadingViewWidth = dipToPx(getContext(), SUGGEST_LOADING_VIEW_WIDTH);
        mLoadingViewHeight = dipToPx(getContext(), SUGGEST_LOADING_VIEW_HEIGHT);
        mAnimatorList = new ArrayList<Animator>();
    }

    private void updateArrowPath(int rectWith, int rectHeight, int triWidth, int triHeight) {
        if (mArrowPath == null) {
            mArrowPath = new Path();
        } else if (mLastArrowRectWidth == rectWith && mLastArrowRectHeight == rectHeight
                && mLastArrowTriWidth == triWidth && mLastArrowTriHeight == triHeight) {
            return;
        } else {
            mArrowPath.reset();
        }

        mLastArrowRectWidth = rectWith;
        mLastArrowRectHeight = rectHeight;
        mLastArrowTriWidth = triWidth;
        mLastArrowTriHeight = triHeight;

        int arrowWidth = Math.max(rectWith, triWidth);
        int halfArrowWidth = arrowWidth / 2;
        int arrowHeight = rectHeight + triHeight;
        int rectPaddingLeft = (arrowWidth - rectWith) / 2;
        int triPaddingLeft = (arrowWidth - triWidth) / 2;

        // move to bottom center
        mArrowPath.moveTo(halfArrowWidth, 0);
        // rect bottom left edge
        mArrowPath.lineTo(rectPaddingLeft, 0);
        // rect left edge
        mArrowPath.lineTo(rectPaddingLeft, rectHeight);
        // tri bottom left edge
        mArrowPath.lineTo(triPaddingLeft, rectHeight);
        // tri left edge
        mArrowPath.lineTo(halfArrowWidth, arrowHeight);
        // tri right edge
        mArrowPath.lineTo(arrowWidth - triPaddingLeft, rectHeight);
        // tri bottom right edge
        mArrowPath.lineTo(arrowWidth - rectPaddingLeft, rectHeight);
        // rect right edge
        mArrowPath.lineTo(arrowWidth - rectPaddingLeft, 0);
        // rect right bottom edge
        mArrowPath.lineTo(halfArrowWidth, 0);

        // update path RectF
        if (mArrowRectF == null) {
            mArrowRectF = new RectF();
        }
        mArrowPath.computeBounds(mArrowRectF, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode != MeasureSpec.EXACTLY) {
            widthMeasureSpec =
                    MeasureSpec.makeMeasureSpec(mLoadingViewWidth, MeasureSpec.EXACTLY);
        }
        if (heightSpecMode != MeasureSpec.EXACTLY) {
            heightMeasureSpec =
                    MeasureSpec.makeMeasureSpec(mLoadingViewHeight, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLoadingViewCenterX = w / 2;
        mLoadingViewCenterY = h / 2;
        mCircleDiameter = (int) Math.min(w / (MAX_LINE_WIDTH_FACTOR
                        + SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO),
                h / (DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO));
        int actualViewWidth = (int) (mCircleDiameter * MAX_LINE_WIDTH_FACTOR
                + SUGGEST_X_AXIS_PADDING_TO_CIRCLE_DIAMETER_RATIO);
        mCircleRadius = mCircleDiameter / 2;

        mCircleRectF.set(0, 0, mCircleDiameter, mCircleDiameter);
        mCircleRectF.offsetTo((w - mCircleRectF.width()) / 2,
                (h - mCircleRectF.height()) / 2);
        mInitArrowRectWidth = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowRectHeight = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowTriWidth = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowTriHeight = (int) (mCircleRadius
                * DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO);
        mInitArrowJointConnerRadius = (int) (mCircleRadius
                * DEFAULT_ARROW_TOP_CONNER_RADIUS_TO_CIRCLE_RADIUS_RATIO);
        mEndArrowRectHeight = (int) (mCircleRadius
                * DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO);

        mArrowRectF.set(mCircleRectF);
        mArrowRectF.inset(
                (mCircleRectF.width() - Math.max(mInitArrowRectWidth, mInitArrowTriWidth)) / 2,
                (mCircleRectF.height() - mInitArrowRectHeight - mInitArrowTriHeight) / 2);

        mBaseLineStrokeWidth = dipToPx(getContext(), (int) Math.max(MIN_LOADING_LINE_STROKE_WIDTH,
                LOADING_LINE_STROKE_WIDTH_TO_VIEW_WIDTH_RATIO * actualViewWidth / getScreenDensity(getContext())));
        mBaseLinePaint.setStrokeWidth(mBaseLineStrokeWidth);
        mBaseLineLen = (int) (mCircleDiameter * MAX_LINE_WIDTH_FACTOR);
        mHalfBaseLineLen = mBaseLineLen / 2;
        mBaseLineX = mLoadingViewCenterX - mHalfBaseLineLen;
        mBaseLineY = mLoadingViewCenterY - mBaseLineStrokeWidth / 2;
        mBaseLineCenterX = mLoadingViewCenterX;
        mProgressLineMaxHeight =
                (int) (mBaseLineLen * DEFAULT_PROGRESS_MAX_HEIGHT_TO_BASELINE_RATIO);
        mLineOscillationMaxHeight =
                (int) (mBaseLineLen * DEFAULT_LINE_OSCILLATION_MAX_HEIGHT_TO_BASELINE_RATIO);

        mProgressTextSize = dipToPx(getContext(), (int) Math.max(MIN_PROGRESS_TEXT_SIZE,
                PROGRESS_TEXT_SIZE_TO_VIEW_WIDTH_RATIO * actualViewWidth / getScreenDensity(getContext())));
        mProgressTextPaint.setTextSize(mProgressTextSize);

        mDefaultPaint.setPathEffect(new CornerPathEffect(mInitArrowJointConnerRadius));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentState) {
            // in this state, scale circle and arrow
            case STATE_BEFORE_PROGRESS_CIRCLE_SCALE:
                drawCircleAndArrowScale(canvas, mBefProgCircleScalingFactor);
                break;
            // in this state, scale circle, arrow and inner circle
            case STATE_BEFORE_PROGRESS_INNER_CIRCLE_SCALE:
                drawCircleAndArrowScaleAndInnerScale(canvas, mBefProgCircleScalingFactor,
                        mBefProgInnerCircleScalingFactor);
                break;
            // in this state, circle change to a line, while the arrow moves up along the y-axis
            case STATE_BEFORE_PROGRESS_CIRCLE_TO_LINE:
                drawBeforeProgressCircleToLine(canvas, mBefProgCircleToLineNormalizedTime);
                break;
            // in this state, arrow move to left, while line oscillate
            case STATE_BEFORE_PROGRESS_ARROW_MOVE_LINE_OSCILL:
                drawBeforeProgressArrowMoveAndLineOscill(canvas,
                        mBefProgArrowMoveNormalizedTime, mBefProgLineOscillationFactor);
                break;
            // in this state, draw arrow move by progress
            case STATE_IN_PROGRESS:
                drawProgress(canvas, mProgressValue);
                break;
            // in this state, arrow is rotate by y
            case STATE_DONE_ROTATE:
                drawArrowRotate(canvas, mDoneRotateNormalizedTime);
                break;

            // in this state, arrow move to center, line pack up
            case STATE_DONE_LINE_PACK_UP:
                drawDoneLinePackUp(canvas, mDoneLinePackUpNormalizedTime);
                break;
            // in this state, arrow shake
            case STATE_DONE_ARROW_SHAKE:
                drawDoneLinePackUpArrowShake(canvas, mDoneArrowShakeAngle);
                break;
            case STATE_DONE_REST_TO_CIRCLE:
                drawDoneRestToCircle(canvas, mDoneRestToCircleScalingFactor,
                        mDoneDialogToArrowNormalizedTime, mDoneDialogToArrowUpDownFactor);
                break;
            case STATE_FAILED_ARROW_SHAKE:
                drawFailedArrowShake(canvas, mFailedArrowUpAndDownFactor, mFailedArrowRotateAngle, 15f);
                break;
            case STATE_FAILED_LINE_OSCILL:
                drawFailedRopeOscillation(canvas, mFailedLineOscillationFactor);
                drawFailedArrowMove(canvas, mFailedArrowMoveNormalizeTiem);
                break;
            case STATE_FAILED_LINE_PACK_UP:
                drawFailedLinePackUp(canvas, mFailedLinePackUpFactor);
                drawFailedArrowMove(canvas, mFailedArrowMoveNormalizeTiem);
                break;
            case STATE_FAILED_CIRCLE_SCALE:
                drawFailedCircleScale(canvas, mFailedCircleScaleFactor);
                drawFailedArrowMove(canvas, mFailedArrowMoveNormalizeTiem);
                break;
            default:
                break;
        }
    }

    private void drawBombPoint(Canvas canvas, float normalizedTime, int lastProgress, int highestPointHeight) {
        if (mFailedBombPaint == null || mFailedBombBellowPaint == null) {
            Path circle = new Path();
            // generate bomb point shape
            circle.addCircle(0, 0, mBaseLineStrokeWidth / 2, Path.Direction.CCW);
            circle.addCircle(mBaseLineStrokeWidth, 0, mBaseLineStrokeWidth / 3, Path.Direction.CCW);
            circle.addCircle(mBaseLineStrokeWidth * 2, 0, mBaseLineStrokeWidth / 4, Path.Direction.CCW);
            circle.addCircle(mBaseLineStrokeWidth * 3, 0, mBaseLineStrokeWidth / 5, Path.Direction.CCW);
            mFailedBombPaint = new Paint();
            mFailedBombPaint.setStrokeWidth(mBaseLineStrokeWidth);
            mFailedBombPaint.setAntiAlias(true);
            mFailedBombPaint.setColor(mProgressLineColor);
            mFailedBombPaint.setStyle(Paint.Style.STROKE);

            mFailedBombPaint.setPathEffect(new PathDashPathEffect(circle,
                    mBaseLineStrokeWidth * 3, 0, PathDashPathEffect.Style.TRANSLATE));
            mFailedBombBellowPaint = new Paint(mFailedBombPaint);
            mFailedBombBellowPaint.setPathEffect(new PathDashPathEffect(circle,
                    mBaseLineStrokeWidth * 3, HALF_FULL_ANGLE, PathDashPathEffect.Style.TRANSLATE));
        }

        if (mFailedBombPath == null || mFailedBombPathBellow == null) {
            mFailedBombPath = new Path();
            float normalizeProgress = (float) lastProgress / FULL_PROGRESS;
            int baseLineEndX = (int) (mBaseLineX + normalizeProgress * mBaseLineLen);
            int baseLineEndY;
            float k = (float) highestPointHeight / mHalfBaseLineLen;
            if (normalizeProgress < HALF_NORMALIZED_PROGRESS) {
                baseLineEndY = (int) (mHalfBaseLineLen * k * normalizeProgress / HALF_NORMALIZED_PROGRESS) + mBaseLineY;
            } else {
                baseLineEndY = (int) (mHalfBaseLineLen * k * (1 - normalizeProgress) / HALF_NORMALIZED_PROGRESS) + mBaseLineY;
            }
            mFailedBombPath.moveTo(mBaseLineX, mBaseLineY - mBaseLineStrokeWidth / 2);
            mFailedBombPath.lineTo(baseLineEndX, baseLineEndY);
            mFailedBombPathBellow = new Path(mFailedBombPath);
            mFailedBombPathBellow.offset(0, mBaseLineStrokeWidth / 2);
        }
        mFailedBombPaint.setAlpha((int) (FULL_ALPHA * (1 - normalizedTime)));
        mFailedBombBellowPaint.setAlpha((int) (FULL_ALPHA * (1 - normalizedTime)));
        canvas.save();
        canvas.translate(0, -mBaseLineStrokeWidth * normalizedTime);
        canvas.drawPath(mFailedBombPath, mFailedBombPaint);
        canvas.translate(0, mBaseLineStrokeWidth * 2 * normalizedTime);
        canvas.drawPath(mFailedBombPathBellow, mFailedBombBellowPaint);
        canvas.restore();
    }

    private void drawBeforeProgressArrowMoveAndLineOscill(Canvas canvas, float normalizeTime, float lineOsscilFactor) {
        // update arrow paramters
        updateArrowToDialogParamters(mChangeArrowToDialogParamters, normalizeTime);
        // height point of path move
        int maxMovePathHeight = (int) (mCircleDiameter * DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO);
        updateArrowMovePoint(normalizeTime,
                CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME, mHalfBaseLineLen,
                maxMovePathHeight, mLoadingViewCenterX - mHalfBaseLineLen,
                mLoadingViewCenterY - maxMovePathHeight, false);

        // move the center of arrow by path
        mLastArrowOffsetX = (int) (mArrowMovePoint[0] - mArrowRectF.width() / 2);
        mLastArrowOffsetY = (int) (mArrowMovePoint[1] - mArrowRectF.height());
        drawArrowTrans(canvas, mLastArrowOffsetX, mLastArrowOffsetY, mChangeArrowToDialogParamters);
        int maxHeightPointOfLineOscill = (int) (mBaseLineLen * DEFAULT_LINE_OSCILLATION_MAX_HEIGHT_TO_BASELINE_RATIO);
        updateLineOscillationPath(lineOsscilFactor, mBaseLineLen,
                mBaseLineX, mBaseLineY, maxHeightPointOfLineOscill, mHalfBaseLineLen);
        canvas.drawPath(mOscillationLinePath, mBaseLinePaint);
    }

    private void drawBeforeProgressCircleToLine(Canvas canvas, float normalizeTime) {
        mBaseLinePaint.setColor(mLoadingLineColor);
        // update path in center of bounds[0, 0, mCircleDiameter, mCircleDiameter]
        updateCircleToLinePath(mBaseLinePath, mCircleDiameter, normalizeTime);
        // offset the path to the place of circle
        mBaseLinePath.offset(mCircleRectF.left, mCircleRectF.top);
        canvas.drawPath(mBaseLinePath, mBaseLinePaint);
        // compute bounds to get the lowest (center) point of the path
        mBaseLinePath.computeBounds(mBaseLineRectF, false);

        // because the bounds of arrow is always [0, 0, width, height]
        mLastArrowOffsetX = (int) (mCircleRectF.centerX() - mArrowRectF.centerX());
        mLastArrowOffsetY = (int) (mCircleRectF.centerY() - mArrowRectF.centerY());
        // calculate the bottom of arrow
        int arrowBottom = (int) (mLastArrowOffsetY + mArrowRectF.height());
        // determine if the bottom of the rope is below the bottom of the arrow
        if (mBaseLineRectF.bottom <= arrowBottom) {
            // move arrow up
            mLastArrowOffsetY += (int) (mBaseLineRectF.bottom - arrowBottom);
        }
        drawArrowTrans(canvas, mLastArrowOffsetX, mLastArrowOffsetY, 0);
    }

    private void drawProgress(Canvas canvas, float progress) {
        float normalizedProgress = progress / FULL_PROGRESS;
        drawProgressLinePath(canvas, normalizedProgress, mBaseLineLen,
                mBaseLineX, mBaseLineY, mProgressLineMaxHeight, mProgressLineColor);
        mLastArrowOffsetX = (int) (mProgressLinePathRectF.left - mArrowRectF.width() / 2
                + mBaseLineLen * normalizedProgress);
        mLastArrowOffsetY = (int) (mProgressLinePathRectF.bottom - mArrowRectF.height());
        drawArrowTrans(canvas, mLastArrowOffsetX, mLastArrowOffsetY, 0);

        // draw progress text
        if (mLastValidProgress != (int) progress) {
            mLastValidProgressTextStr = String.valueOf((int) progress) + "%";
            mProgressTextPaint.getTextBounds(mLastValidProgressTextStr, 0,
                    mLastValidProgressTextStr.length(), mProgressTextRect);
        }
        int offsetHeight = (mEndArrowRectHeight - mProgressTextRect.height()) / 2;
        int textBaseLineX = (int) (mLastArrowOffsetX
                + (mArrowRectF.width() - mProgressTextRect.width()) / 2);
        int textBastLineY = mLastArrowOffsetY + offsetHeight - mProgressTextRect.top;
        canvas.drawText(mLastValidProgressTextStr, textBaseLineX, textBastLineY
                , mProgressTextPaint);
    }

    private void drawArrowRotate(Canvas canvas, float normalizedTime) {
        if (mArrowRotateMatrix == null) {
            mArrowRotateMatrix = new Matrix();
        } else {
            mArrowRotateMatrix.reset();
        }
        mArrowRotateMatrix.reset();

        float angle;
        String tmpStr = mLastValidProgressTextStr;
        if (normalizedTime <= HALF_NORMALIZED_PROGRESS) {
            // first text is 100%, rotate angle is  0 to 90
            mLastValidProgressTextStr = FULL_PROGRESS_STR;
            angle = HALF_FULL_ANGLE * normalizedTime;
            mProgressTextPaint.setColor(mProgressTextColor);
        } else {
            // second text is done, rotate angle is  270 to 360
            mLastValidProgressTextStr = FULL_PROGRESS_DONE_STR;
            angle = HALF_FULL_ANGLE * normalizedTime + HALF_FULL_ANGLE;
            mProgressTextPaint.setColor(mDoneTextColor);
        }
        if (mCamera == null) {
            mCamera = new Camera();
        }
        mCamera.save();
        mCamera.rotateY(angle);
        mCamera.getMatrix(mArrowRotateMatrix);
        mCamera.restore();
        mArrowRotateMatrix.preTranslate(-mArrowRectF.centerX(), -mArrowRectF.centerY());
        mArrowRotateMatrix.postTranslate(mArrowRectF.centerX(), mArrowRectF.centerY());

        mLastArrowOffsetX = (int) (mBaseLineX + mBaseLineLen - mArrowRectF.width() / 2);
        mLastArrowOffsetY = (int) (mBaseLineY - mArrowRectF.height());

        canvas.save();
        canvas.translate(mLastArrowOffsetX, mLastArrowOffsetY);
        canvas.concat(mArrowRotateMatrix);
        mDefaultPaint.setColor(mArrowColor);
        canvas.drawPath(mArrowPath, mDefaultPaint);

        // str is changed, need re-calculate bounds
        if (!tmpStr.equals(mLastValidProgressTextStr)) {
            mProgressTextPaint.getTextBounds(mLastValidProgressTextStr,
                    0, mLastValidProgressTextStr.length(), mProgressTextRect);
        }

        int textBaseLineX = (int) (mArrowRectF.left + (mArrowRectF.width() - mProgressTextRect.width()) / 2);
        int textBaseLineY = (int) (mArrowRectF.bottom - mArrowRectF.height() / 2);
        canvas.drawText(mLastValidProgressTextStr, textBaseLineX, textBaseLineY, mProgressTextPaint);
        canvas.restore();

        // the FULL_NORMALIZED_TIME is means, no need bounce
        drawProgressLinePath(canvas, FULL_NORMALIZED_TIME, mBaseLineLen,
                mBaseLineX, mBaseLineY, mProgressLineMaxHeight, mProgressLineColor);
    }


    private void drawDoneLinePackUp(Canvas canvas, float normalizedTime) {
        int adjustLen = (int) (mBaseLineLen * (FULL_NORMALIZED_TIME - normalizedTime));
        // keep a min len
        if (adjustLen < DONE_LINE_PACK_UP_ARROW_SHAKE_BASE_POINT_DIAMETER) {
            adjustLen = DONE_LINE_PACK_UP_ARROW_SHAKE_BASE_POINT_DIAMETER;
        }
        int adjustBaseLineX = mBaseLineCenterX - adjustLen / 2;
        mLastArrowOffsetX = (int) (adjustBaseLineX + adjustLen - mArrowRectF.width() / 2);
        mLastArrowOffsetY = (int) (mBaseLineY - mArrowRectF.height());

        mBaseLinePaint.setColor(mProgressLineColor);
        canvas.drawLine(adjustBaseLineX, mBaseLineY, adjustBaseLineX + adjustLen, mBaseLineY, mBaseLinePaint);
        drawArrowTrans(canvas, mLastArrowOffsetX, mLastArrowOffsetY, DONE_LINE_PACK_UP_ARROW_ANGLE);
    }

    private void drawDoneLinePackUpArrowShake(Canvas canvas, float angle) {
        int adjustLen = DONE_LINE_PACK_UP_ARROW_SHAKE_BASE_POINT_DIAMETER;
        mBaseLinePaint.setColor(mProgressLineColor);
        canvas.drawLine(mBaseLineCenterX, mBaseLineY, mBaseLineCenterX + adjustLen, mBaseLineY, mBaseLinePaint);
        drawArrowTrans(canvas,
                (int) (mBaseLineCenterX - mArrowRectF.width() / 2),
                (int) (mBaseLineY - mArrowRectF.height()), angle);
    }

    private void drawDoneRestToCircle(Canvas canvas, float circleScaleFactor,
                                      float dialogToArrowNormalizeTime, float arrowUpDownFactor) {
        // draw bg circle
        float circleRadius = mCircleRadius * circleScaleFactor;
        mDefaultPaint.setColor(mLoadingCircleBackColor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), circleRadius, mDefaultPaint);

        // draw arrow
        // generate dialog to arrow
        updateArrowToDialogParamters(mChangeArrowToDialogParamters, 1 - dialogToArrowNormalizeTime);
        updateArrowPath((int) (mCircleRadius * mChangeArrowToDialogParamters[0]),
                (int) (mCircleRadius * mChangeArrowToDialogParamters[1]),
                (int) (mCircleRadius * mChangeArrowToDialogParamters[2]),
                (int) (mCircleRadius * mChangeArrowToDialogParamters[3]));
        int offsetArrowX = (int) (mCircleRectF.centerX() - mArrowRectF.width() / 2);
        // dialogToArrowNormalizeTime * 0.5 can ensure the arrow move end point smoothly
        int offsetArrowY = (int) (mCircleRectF.centerY() - mArrowRectF.height()
                * (1 - dialogToArrowNormalizeTime * 0.5 + arrowUpDownFactor));
        canvas.save();
        canvas.translate(offsetArrowX, offsetArrowY);
        mDefaultPaint.setColor(mArrowColor);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
    }

    private void drawFailedArrowShake(Canvas canvas, float upAndDownFactor, float rotateFactor, float maxRotateAngle) {
        float normalizedProgress = mProgressValue / FULL_PROGRESS;
        int offsetLine = (int) (mProgressLineMaxHeight * upAndDownFactor);
        int offsetArrow;
        if (normalizedProgress < HALF_NORMALIZED_PROGRESS) {
            offsetArrow = (int) (offsetLine * normalizedProgress / HALF_NORMALIZED_PROGRESS);
        } else {
            offsetArrow = (int) (offsetLine * (FULL_NORMALIZED_PROGRESS - normalizedProgress) / HALF_NORMALIZED_PROGRESS);
        }
        drawProgressLinePath(canvas, normalizedProgress, mBaseLineLen,
                mBaseLineX, mBaseLineY, mProgressLineMaxHeight + offsetLine, mLoadingLineColor);
        drawBombPoint(canvas, mFailedBombAnimatorPer, (int) mProgressValue, mProgressLineMaxHeight);

        if (!mLastValidProgressTextStr.equals(FAILED_PROGRESS_STR)) {
            mLastValidProgressTextStr = FAILED_PROGRESS_STR;
            mProgressTextPaint.getTextBounds(FAILED_PROGRESS_STR,
                    0, FAILED_PROGRESS_STR.length(), mProgressTextRect);
        }

        canvas.save();
        canvas.translate(mLastArrowOffsetX, mLastArrowOffsetY + offsetArrow);
        canvas.rotate(maxRotateAngle * rotateFactor, mArrowRectF.centerX(), mArrowRectF.bottom);
        canvas.save();
        canvas.rotate(HALF_FULL_ANGLE, mArrowRectF.centerX(), mArrowRectF.bottom);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
        int textBaseLineX = (int) (mArrowRectF.left + (mArrowRectF.width() - mProgressTextRect.width()) / 2);
        int textBaseLineY = (int) (mArrowRectF.bottom + mArrowRectF.height() +
                -(mEndArrowRectHeight - mProgressTextRect.height()) / 2
                - mProgressTextRect.bottom);
        canvas.drawText(FAILED_PROGRESS_STR, textBaseLineX, textBaseLineY, mProgressTextPaint);
        canvas.restore();
    }

    private void drawFailedRopeOscillation(Canvas canvas, float ropeOsillFactor) {
        updateLineOscillationPath(ropeOsillFactor, mBaseLineLen,
                mBaseLineX, mBaseLineY, mLineOscillationMaxHeight, mHalfBaseLineLen);
        mBaseLinePaint.setColor(mLoadingLineColor);
        canvas.drawPath(mOscillationLinePath, mBaseLinePaint);
    }


    private void drawFailedArrowMove(Canvas canvas, float normalizedTime) {
        int circleRadius = (int) (mCircleRectF.width() / 2);
        int halfLineLen = (int) (circleRadius * MAX_LINE_WIDTH_FACTOR);
        int startX = (int) (mLoadingViewCenterX
                + (mProgressValue - HALF_PROGRESS) / HALF_PROGRESS * halfLineLen);
        int width = Math.abs(startX - mLoadingViewCenterX);
        int maxMovePathHeight = (int) (circleRadius
                * DEFAULT_ARROW_MOVE_MAX_HEIGHT_TO_CIRCLE_DIAMETER_RATIO);

        updateArrowMovePoint(normalizedTime, FULL_NORMALIZED_TIME,
                width, maxMovePathHeight, Math.min(startX, mLoadingViewCenterX),
                mLoadingViewCenterY - maxMovePathHeight, mProgressValue < HALF_PROGRESS);
        int offsetArrowX = (int) (mArrowMovePoint[0] - mArrowRectF.width() / 2);
        int offsetArrowY = (int) (mArrowMovePoint[1] - mArrowRectF.height() / 2);

        updateFailedDialogToArrowParamters(mChangeArrowToDialogParamters, normalizedTime);
        updateArrowPath((int) (mCircleRadius * mChangeArrowToDialogParamters[0]),
                (int) (mCircleRadius * mChangeArrowToDialogParamters[1]),
                (int) (mCircleRadius * mChangeArrowToDialogParamters[2]),
                (int) (mCircleRadius * mChangeArrowToDialogParamters[3]));
        mDefaultPaint.setColor(mArrowColor);
        canvas.save();
        canvas.translate(offsetArrowX, offsetArrowY);
        canvas.concat(mArrowRotateMatrix);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
    }

    private void drawFailedLinePackUp(Canvas canvas, float packUpFactor) {
        int halfLineLen = (int) (mHalfBaseLineLen * packUpFactor);
        mBaseLinePaint.setColor(mLoadingLineColor);
        canvas.drawLine(mLoadingViewCenterX - halfLineLen, mLoadingViewCenterY,
                mLoadingViewCenterX + halfLineLen, mLoadingViewCenterY, mBaseLinePaint);
    }

    private void drawFailedCircleScale(Canvas canvas, float scaleFactor) {
        int circleRadius = (int) (mCircleRadius * scaleFactor);
        mDefaultPaint.setColor(mLoadingCircleBackColor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), circleRadius, mDefaultPaint);
    }

    private void updateArrowMovePoint(float normalizedTime, float startBounceTime,
                                      int width, int height, int left, int top, boolean isLTR) {

        if (mArrowMovePath == null) {
            mArrowMovePath = new Path();
        }

        if (mArrowPathMeasure == null) {
            mArrowPathMeasure = new PathMeasure();
        }

        if (mArrowBouncePath == null) {
            mArrowBouncePath = new Path();
        }

        if (mArrowBouncePathMeasure == null) {
            mArrowBouncePathMeasure = new PathMeasure();
        }

        if (mArrowMovePathRect == null) {
            mArrowMovePathRect = new Rect();
        }

        if (mArrowMovePathRect.width() != width || mArrowMovePathRect.height() != height
                || mArrowMovePathRect.left != left || mArrowMovePathRect.top != top) {
            mArrowMovePathRect.set(left, top, left + width, top + height);
            mArrowMovePath.reset();
            mArrowBouncePath.reset();

        }

        // move path
        if (mArrowMovePath.isEmpty()) {
            mArrowMovePath.moveTo(mArrowMovePathRect.left, mArrowMovePathRect.bottom);
            mArrowMovePath.cubicTo(mArrowMovePathRect.left + mArrowMovePathRect.width() / 4,
                    mArrowMovePathRect.top,
                    mArrowMovePathRect.right,
                    mArrowMovePathRect.top,
                    mArrowMovePathRect.right, mArrowMovePathRect.bottom);
            mArrowPathMeasure.setPath(mArrowMovePath, false);
            mArrowMovePathLength = mArrowPathMeasure.getLength();
        }

        // bounce path
        if (mArrowBouncePath.isEmpty()) {
            mArrowBouncePath.moveTo(mArrowMovePathRect.right, mArrowMovePathRect.bottom);
            mArrowBouncePath.lineTo(mArrowMovePathRect.right, mArrowMovePathRect.bottom
                    - mArrowRectF.height() * ARROW_BOUNCE_LENGTH_RATIO);
            mArrowBouncePath.lineTo(mArrowMovePathRect.right, mArrowMovePathRect.bottom);
            mArrowBouncePath.lineTo(mArrowMovePathRect.right, mArrowMovePathRect.bottom
                    - mArrowRectF.height() * ARROW_BOUNCE_LENGTH_RATIO_2);
            mArrowBouncePath.lineTo(mArrowMovePathRect.right, mArrowMovePathRect.bottom);
            mArrowBouncePathMeasure.setPath(mArrowBouncePath, false);
            mArrowBouncePathLength = mArrowBouncePathMeasure.getLength();
        }

        if (mArrowMovePoint == null) {
            mArrowMovePoint = new float[2];
        }

        // move
        if (normalizedTime <= startBounceTime) {
            mArrowPathMeasure.getPosTan(mArrowMovePathLength
                    * normalizedTime / startBounceTime, mArrowMovePoint, null);
        } else {
            // bounce
            mArrowBouncePathMeasure.getPosTan(mArrowBouncePathLength
                    * (normalizedTime - startBounceTime)
                    / (FULL_NORMALIZED_TIME - startBounceTime), mArrowMovePoint, null);
        }

        if (!isLTR) {
            mArrowMovePoint[0] = mArrowMovePathRect.centerX()
                    - (mArrowMovePoint[0] - mArrowMovePathRect.centerX());
        }
    }

    /**
     * generate arrow change to dialog paramters
     *
     * @param paramters must be length = 5
     *                  paramteArr[0] and paramteArr[1] indicate arrow rectangle's width and height,respectively.
     *                  paramteArr[2] and paramteArr[3] indicate arrow triangle's width and height,respectively.
     *                  paramteArr[4] indicates arrow rotate angle.
     */
    private void updateArrowToDialogParamters(float[] paramters, float normalizeTime) {

        if (paramters == null || paramters.length != 5) {
            return;
        }

        // rect width
        if (normalizeTime <= CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME) {
            paramters[0] = (DEFAULT_END_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO
                    - DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO)
                    * normalizeTime / CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME
                    + DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO;
        } else {
            paramters[0] = DEFAULT_END_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO;
        }

        // rect height
        if (normalizeTime <= CHANGE_ARROW_TO_DIALOG_RECT_CHANGE) {
            paramters[1] = (DEFAULT_MIDDLE_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO
                    - DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO)
                    * normalizeTime / CHANGE_ARROW_TO_DIALOG_RECT_CHANGE
                    + DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO;
        } else if (normalizeTime <= CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME) {
            paramters[1] = (DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO
                    - DEFAULT_MIDDLE_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO)
                    * (normalizeTime - CHANGE_ARROW_TO_DIALOG_RECT_CHANGE)
                    / (CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME - CHANGE_ARROW_TO_DIALOG_RECT_CHANGE)
                    + DEFAULT_MIDDLE_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO;
        } else {
            paramters[1] = DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO;
        }

        // tri width and height
        if (normalizeTime < CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME) {
            // tri width
            paramters[2] = (DEFAULT_END_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO
                    - DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO)
                    * normalizeTime / CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME
                    + DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO;
            // tri height
            paramters[3] = (DEFAULT_END_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO
                    - DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO)
                    * normalizeTime / CHANGE_ARROW_TO_DIALOG_BOUNCE_TIME
                    + DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO;
        } else {
            // tri width
            paramters[2] = DEFAULT_END_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO;
            // tri height
            paramters[3] = DEFAULT_END_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO;
        }

        // angle
        if (normalizeTime <= ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_1) {
            paramters[4] = (ROTATE_ARROW_TO_DIALOG_ANGLE_1 - ROTATE_ARROW_TO_DIALOG_INIT_ANGLE)
                    * normalizeTime / ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_1
                    + ROTATE_ARROW_TO_DIALOG_INIT_ANGLE;
        } else if (normalizeTime <= ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_2) {
            paramters[4] = (ROTATE_ARROW_TO_DIALOG_ANGLE_2 - ROTATE_ARROW_TO_DIALOG_ANGLE_1)
                    * (normalizeTime - ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_1)
                    / (ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_2 - ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_1)
                    + ROTATE_ARROW_TO_DIALOG_ANGLE_1;
        } else if (normalizeTime <= ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_3) {
            paramters[4] = (ROTATE_ARROW_TO_DIALOG_ANGLE_3 - ROTATE_ARROW_TO_DIALOG_ANGLE_2)
                    * (normalizeTime - ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_2)
                    / (ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_3 - ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_2)
                    + ROTATE_ARROW_TO_DIALOG_ANGLE_2;
        } else {
            paramters[4] = (ROTATE_ARROW_TO_DIALOG_INIT_ANGLE - ROTATE_ARROW_TO_DIALOG_ANGLE_3)
                    * (normalizeTime - ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_3)
                    / (ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_4 - ROTATE_ARROW_TO_DIALOG_ANGLE_SEASON_3)
                    + ROTATE_ARROW_TO_DIALOG_ANGLE_3;
        }

    }


    private void updateFailedDialogToArrowParamters(float[] paramters, float normalizeTime) {

        if (paramters == null || paramters.length != 5) {
            return;
        }

        // rect width
        paramters[0] = (DEFAULT_INIT_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO
                - DEFAULT_END_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO)
                * normalizeTime + DEFAULT_END_ARROW_RECT_WIDTH_TO_CIRCLE_RADIUS_RATIO;

        // rect height
        paramters[1] = (DEFAULT_INIT_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO
                - DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO)
                * normalizeTime + DEFAULT_END_ARROW_RECT_HEIGHT_TO_CIRCLE_RADIUS_RATIO;

        // tri width and height
        // tri width
        paramters[2] = (DEFAULT_INIT_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO
                - DEFAULT_END_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO)
                * normalizeTime + DEFAULT_END_ARROW_TRI_WIDTH_TO_CIRCLE_RADIUS_RATIO;
        // tri height
        paramters[3] = (DEFAULT_INIT_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO
                - DEFAULT_END_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO)
                * normalizeTime + DEFAULT_END_ARROW_TRI_HEIGHT_TO_CIRCLE_RADIUS_RATIO;

    }

    /**
     * Assumed the circle height is h, width is w, we can obtain the following content:
     * this animation have four State:
     *
     * State 1: Circle to special line 1
     * width: w -> 1.2w
     * height: w -> 0.73w
     * firstConXFactor: -0.65w to 0.18w
     * firstConYFactor: 0 to 0.036w
     * SecondConXFactor: -0.65w to 0.06w
     * SecondConYFactor: 1w to 0.73w
     *
     * State 2: line 1 to line 2
     * width: 1.2w -> 2.4w
     * height: w -> 0.36w
     * firstConXFactor: 0.18w to 0.72w
     * firstConYFactor: 0.036w to 0
     * SecondConXFactor: 0.06w to 0.72w
     * SecondConYFactor: 0.73w to 0.36w
     *
     * State 3: line 2 to line 3
     * width: 2.4w -> 2.4w
     * height: 0.36w -> 0
     * firstConXFactor: 0.72w to 1.04w
     * firstConYFactor: 0 to  0
     * SecondConXFactor: 0.72w to 1.04w
     * SecondConYFactor: 0.36w to 0
     */

    private void updateCircleToLinePath(Path linePath, int circleDiameter, float normalizedTime) {
        if (linePath == null) {
            return;
        }
        int index = 0;
        float adjustNormalizedTime = 0;
        if (normalizedTime <= CIRCLE_TO_LINE_SEASONS[1]) {
            adjustNormalizedTime = normalizedTime / CIRCLE_TO_LINE_SEASONS[1];
        } else if (normalizedTime < CIRCLE_TO_LINE_SEASONS[2]) {
            index = 1;
            adjustNormalizedTime = (normalizedTime - CIRCLE_TO_LINE_SEASONS[1])
                    / (CIRCLE_TO_LINE_SEASONS[2] - CIRCLE_TO_LINE_SEASONS[1]);
        } else {
            index = 2;
            adjustNormalizedTime = (normalizedTime - CIRCLE_TO_LINE_SEASONS[2])
                    / (CIRCLE_TO_LINE_SEASONS[3] - CIRCLE_TO_LINE_SEASONS[2]);
        }

        // the path bounds width
        int boundWidth = (int) (((CIRCLE_TO_LINE_WIDTH_FACTOR[index + 1]
                - CIRCLE_TO_LINE_WIDTH_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_WIDTH_FACTOR[index]) * circleDiameter);

        // the distance of cubic line1' x1 to cubic line2's x2
        int adjustBoundWidth = boundWidth;
        if (normalizedTime <= CIRCLE_TO_LINE_SEASONS[1]) {
            adjustBoundWidth = (int) (boundWidth * adjustNormalizedTime);
        }

        // the path bounds height
        int boundHeight = (int) (((CIRCLE_TO_LINE_HEIGHT_FACTOR[index + 1]
                - CIRCLE_TO_LINE_HEIGHT_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_HEIGHT_FACTOR[index]) * circleDiameter);

        // calculate the four points
        float firstControlXFactor = (CIRCLE_TO_LINE_FST_CON_X_FACTOR[index + 1]
                - CIRCLE_TO_LINE_FST_CON_X_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_FST_CON_X_FACTOR[index];
        float firstControlYFactor = (CIRCLE_TO_LINE_FST_CON_Y_FACTOR[index + 1]
                - CIRCLE_TO_LINE_FST_CON_Y_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_FST_CON_Y_FACTOR[index];
        float secondControlXFactor = (CIRCLE_TO_LINE_SEC_CON_X_FACTOR[index + 1]
                - CIRCLE_TO_LINE_SEC_CON_X_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_SEC_CON_X_FACTOR[index];
        float secondControlYFactor = (CIRCLE_TO_LINE_SEC_CON_Y_FACTOR[index + 1]
                - CIRCLE_TO_LINE_SEC_CON_Y_FACTOR[index])
                * adjustNormalizedTime + CIRCLE_TO_LINE_SEC_CON_Y_FACTOR[index];
        int firstControlX = (int) (circleDiameter * firstControlXFactor);
        int firstControlY = (int) (circleDiameter * firstControlYFactor);
        int secondControlX = (int) (circleDiameter * secondControlXFactor);
        int secondControlY = (int) (circleDiameter * secondControlYFactor);

        linePath.reset();
        // left line
        linePath.cubicTo(firstControlX, firstControlY,
                secondControlX, secondControlY, adjustBoundWidth / 2, boundHeight);
        // left right line
        linePath.cubicTo(adjustBoundWidth - secondControlX,
                secondControlY, adjustBoundWidth - firstControlX, firstControlY, adjustBoundWidth, 0);

        // translate path to move the origin to the center
        int offsetX = (circleDiameter - adjustBoundWidth) / 2;
        int offsetY = (circleDiameter - boundHeight) / 2;
        linePath.offset(offsetX, offsetY);
    }

    private void drawCircleAndArrowScale(Canvas canvas, float scaleFactor) {
        // draw bg circle
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor, mCircleRectF.centerX(), mCircleRectF.centerY());
        mDefaultPaint.setColor(mLoadingCircleBackColor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), mCircleRadius, mDefaultPaint);

        // draw arrow
        updateArrowPath(mInitArrowRectWidth,
                mInitArrowRectHeight, mInitArrowTriWidth, mInitArrowTriHeight);
        // translate the canvas  causes the center point of the arrow to coincide with the center point of the circle
        canvas.translate(mCircleRectF.centerX() - mArrowRectF.width() / 2 * scaleFactor,
                mCircleRectF.centerY() - mArrowRectF.height() / 2 * scaleFactor);
        mDefaultPaint.setColor(mArrowColor);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
    }

    private void drawCircleAndArrowScaleAndInnerScale(Canvas canvas, float scalingFactor, float innerCircleScalingFactor) {
        // draw bg circle
        canvas.save();
        canvas.scale(scalingFactor, scalingFactor, mCircleRectF.centerX(), mCircleRectF.centerY());
        int layoutCont = canvas.saveLayer(mCircleRectF, mDefaultPaint, Canvas.ALL_SAVE_FLAG);
        mDefaultPaint.setColor(mLoadingCircleBackColor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), mCircleRadius, mDefaultPaint);

        mDefaultPaint.setXfermode(mXfermode);
        // draw bg circle 2
        int innerCircleRadius = (int) (mCircleRadius * innerCircleScalingFactor);
        canvas.drawCircle(mCircleRectF.centerX(), mCircleRectF.centerY(), innerCircleRadius, mDefaultPaint);
        mDefaultPaint.setXfermode(null);
        canvas.restoreToCount(layoutCont);

        // draw arrow
        updateArrowPath(mInitArrowRectWidth,
                mInitArrowRectHeight, mInitArrowTriWidth, mInitArrowTriHeight);
        // translate the canvas  causes the center point of the arrow to coincide with the center point of the circle
        canvas.translate(mCircleRectF.centerX() - mArrowRectF.width() / 2 * scalingFactor,
                mCircleRectF.centerY() - mArrowRectF.height() / 2 * scalingFactor);
        mDefaultPaint.setColor(mArrowColor);
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
    }

    private void drawArrowTrans(Canvas canvas, int offsetX, int offsetY, float rotateAngle) {
        if (mArrowPath == null) {
            mArrowPath = new Path();
            updateArrowPath(mInitArrowRectWidth, mInitArrowRectHeight,
                    mInitArrowTriWidth, mLastArrowTriWidth);
        }

        if (mArrowRectF == null) {
            mArrowRectF = new RectF();
            mArrowPath.computeBounds(mArrowRectF, true);
        }
        if (mArrowRotateMatrix == null) {
            mArrowRotateMatrix = new Matrix();
        } else {
            mArrowRotateMatrix.reset();
        }

        mDefaultPaint.setColor(mArrowColor);
        canvas.save();
        canvas.translate(offsetX, offsetY);
        // arrow shake
        if (rotateAngle != 0) {
            mArrowRotateMatrix.postRotate(rotateAngle,
                    mArrowRectF.centerX(), mArrowRectF.bottom);
            canvas.concat(mArrowRotateMatrix);
        }
        canvas.drawPath(mArrowPath, mDefaultPaint);
        canvas.restore();
    }

    private void drawArrowTrans(Canvas canvas, int offsetX, int offsetY, float[] paramters) {
        updateArrowPath((int) (mCircleRadius * paramters[0]),
                (int) (mCircleRadius * paramters[1]),
                (int) (mCircleRadius * paramters[2]),
                (int) (mCircleRadius * paramters[3]));
        drawArrowTrans(canvas, offsetX, offsetY, paramters[4]);
    }

    /**
     * @param oscillFactor            line oscillation factor
     * @param baseLineX               The the original X coordinate of starting point of the base straight line
     * @param baseLineY               The the original Y coordinate of starting point of the base straight line
     * @param highestPointHeight      The height highest point
     * @param highestPointPaddingLeft Indicates the distance from the top of the rope to the left of the rope
     */
    private void updateLineOscillationPath(
            float oscillFactor, int baselineLen,
            int baseLineX, int baseLineY, int highestPointHeight, int highestPointPaddingLeft) {
        if (mOscillationLinePath == null) {
            mOscillationLinePath = new Path();
        } else {
            mOscillationLinePath.reset();
        }
        // calculate the y of the control point
        highestPointHeight *= oscillFactor;
        int controlPointY = (int) (highestPointHeight * baselineLen
                * baselineLen / (2f * highestPointPaddingLeft * (baselineLen - highestPointPaddingLeft)));

        // move to start point
        mOscillationLinePath.moveTo(baseLineX, baseLineY);
        mOscillationLinePath.quadTo(baseLineX + highestPointPaddingLeft,
                baseLineY - controlPointY,
                baseLineX + baselineLen, baseLineY);
    }

    private void drawProgressLinePath(
            Canvas canvas, float normalizeProgress, int baselineLen,
            int baseLineX, int baseLineY, int highestPointHeight, int leftLineColor) {
        int halfLen = baselineLen / 2;
        int middlePointX = (int) (baseLineX + baselineLen * normalizeProgress);
        int middlePointY;

        float k = (float) highestPointHeight / halfLen;
        if (normalizeProgress < HALF_NORMALIZED_PROGRESS) {
            middlePointY = (int) (halfLen * k
                    * normalizeProgress / HALF_NORMALIZED_PROGRESS) + baseLineY;
        } else {
            middlePointY = (int) (halfLen * k
                    * (1 - normalizeProgress) / HALF_NORMALIZED_PROGRESS) + baseLineY;
        }
        // draw right part first
        mBaseLinePaint.setColor(mLoadingLineColor);
        canvas.drawLine(middlePointX, middlePointY, baseLineX + baselineLen,
                baseLineY, mBaseLinePaint);

        // draw left part
        mBaseLinePaint.setColor(leftLineColor);
        canvas.drawLine(baseLineX, baseLineY, middlePointX, middlePointY, mBaseLinePaint);
        if (mProgressLinePathRectF == null) {
            mProgressLinePathRectF = new RectF();
        }
        mProgressLinePathRectF.set(baseLineX, baseLineY, baseLineX + baselineLen, middlePointY);
    }

    public void performAnimation() {
        mIsFailed = false;
        mLastProgress = ZERO_PROGRESS;
        mNextProgress = ZERO_PROGRESS;
        releaseAnimation();

        // circle and arrow scale
        mBefProgCircleScaleAnimator = ValueAnimator.ofFloat(1f, 0.91f, 1f);
        mBefProgCircleScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBefProgCircleScalingFactor = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mBefProgCircleScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_BEFORE_PROGRESS_CIRCLE_SCALE;
            }
        });
        mBefProgCircleScaleAnimator.setDuration(BEFORE_PROGRESS_CIRCLE_SCALE_DURATION);
        mAnimatorList.add(mBefProgCircleScaleAnimator);

        // inner circle arrow scale
        mBefProgInnerCircleScaleAnimator = ValueAnimator.ofFloat(0, 0.9f);
        mBefProgInnerCircleScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBefProgInnerCircleScalingFactor = (float) animation.getAnimatedValue();
            }
        });
        mBefProgInnerCircleScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_BEFORE_PROGRESS_INNER_CIRCLE_SCALE;
            }
        });
        mBefProgInnerCircleScaleAnimator.setDuration(BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DURATION);
        mBefProgInnerCircleScaleAnimator.setStartDelay(BEFORE_PROGRESS_INNER_CIRCLE_SCALE_DELAY);
        mAnimatorList.add(mBefProgInnerCircleScaleAnimator);

        // circle to line animation
        mBefProgCircleToLineAnimator = ValueAnimator.ofFloat(0, 1f);
        mBefProgCircleToLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBefProgCircleToLineNormalizedTime = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mBefProgCircleToLineAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_BEFORE_PROGRESS_CIRCLE_TO_LINE;
            }
        });
        mBefProgCircleToLineAnimator.setDuration(BEFORE_PROGRESS_CIRCLE_TO_LINE_DURATION);
        mAnimatorList.add(mBefProgCircleToLineAnimator);

        // arrow move animation
        mBefProgArrowMoveAnimator = ValueAnimator.ofFloat(0, 1f);
        mBefProgArrowMoveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBefProgArrowMoveNormalizedTime = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mBefProgArrowMoveAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_BEFORE_PROGRESS_ARROW_MOVE_LINE_OSCILL;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                performProgressAnimation();
            }
        });
        mBefProgArrowMoveAnimator.setDuration(BEFORE_PROGRESS_ARROW_MOVE_AND_LINE_OSCILL);
        mAnimatorList.add(mBefProgArrowMoveAnimator);

        // line oscill animation
        mBefProgLineOscillAnimator = ValueAnimator.ofFloat(0, 1, -0.5f, 0.25f, -0.125f, 0);
        mBefProgLineOscillAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBefProgLineOscillationFactor = (float) animation.getAnimatedValue();
            }
        });
        mBefProgLineOscillAnimator.setDuration(BEFORE_PROGRESS_ARROW_MOVE_AND_LINE_OSCILL);
        AnimatorSet arrowMoveAndLineOscillSet = new AnimatorSet();
        arrowMoveAndLineOscillSet.playTogether(mBefProgArrowMoveAnimator, mBefProgLineOscillAnimator);
        mAnimatorList.add(arrowMoveAndLineOscillSet);

        mBefProgressAnimatorSet = new AnimatorSet();
        mBefProgressAnimatorSet.playSequentially(mBefProgCircleScaleAnimator, mBefProgCircleToLineAnimator,
                arrowMoveAndLineOscillSet);
        mBefProgressAnimatorSet.playTogether(mBefProgInnerCircleScaleAnimator);
        mBefProgressAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorList.add(mBefProgressAnimatorSet);
        mBefProgressAnimatorSet.start();
    }

    private void performProgressAnimation() {
        int startProgress = mLastProgress;
        int endProgress = mNextProgress;
        mLastProgress = mNextProgress;

        if (mIsFailed) {
            performFailedAnimation();
            return;
        }

        mProgressAnimator = ValueAnimator.ofFloat(startProgress, endProgress);
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mProgressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_IN_PROGRESS;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mIsFailed) {
                    performFailedAnimation();
                } else if (mLastProgress != FULL_PROGRESS) {
                    performProgressAnimation();
                } else {
                    performDoneAnimation();
                }
            }
        });
        mProgressAnimator.setInterpolator(new LinearInterpolator());
        mProgressAnimator.setDuration(Math.max(
                FULL_PROGRESS_ANIMATION_DURATION
                        * (endProgress - startProgress) / FULL_PROGRESS,
                MIN_PROGRESS_ANIMATION_DURATION));
        mAnimatorList.add(mProgressAnimator);
        mProgressAnimator.start();
    }

    private void performDoneAnimation() {
        // done rotate animation
        mDoneRotateAnimator = ValueAnimator.ofFloat(0, 1);
        mDoneRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDoneRotateNormalizedTime = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mDoneRotateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_DONE_ROTATE;
            }
        });
        mDoneRotateAnimator.setDuration(DONE_ANIMATION_DURATION);
        mAnimatorList.add(mDoneRotateAnimator);

        // line pack up animation
        mDoneLinePackUpAnimator = ValueAnimator.ofFloat(0, 1);
        mDoneLinePackUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mDoneLinePackUpNormalizedTime = value;
                invalidate();
            }
        });
        mDoneLinePackUpAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_DONE_LINE_PACK_UP;
            }
        });
        mDoneLinePackUpAnimator.setDuration(DONE_LINE_PACK_UP_ANIMATION_DURATION);
        mAnimatorList.add(mDoneLinePackUpAnimator);

        // arrow shake animator and line change to a point
        mDoneArrowShakeAnimator = ValueAnimator.ofFloat(0, -20, 20, -10, 10);
        mDoneArrowShakeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDoneArrowShakeAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mDoneArrowShakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_DONE_ARROW_SHAKE;
            }
        });
        mDoneArrowShakeAnimator.setDuration(DONE_LINE_PACK_UP_ARROW_SHAKE_ANIMATION_DURATION);
        mAnimatorList.add(mDoneArrowShakeAnimator);

        // circle scale animation
        mDoneRestToCircleAnimator = ValueAnimator.ofFloat(0f, 1.1f, .9f, 1f);
        mDoneRestToCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDoneRestToCircleScalingFactor = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mDoneRestToCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_DONE_REST_TO_CIRCLE;
            }
        });
        mDoneRestToCircleAnimator.setDuration(DONE_REST_TO_CIRCLE_DOWN_ANIMATION_DURATION);
        mAnimatorList.add(mDoneRestToCircleAnimator);

        // dialog change to arrow
        mDoneDialogToArrowAnimator = ValueAnimator.ofFloat(0, 1f);
        mDoneDialogToArrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDoneDialogToArrowNormalizedTime = (float) animation.getAnimatedValue();
            }
        });
        mDoneDialogToArrowAnimator.setDuration(DONE_DIALOG_TO_ARROW_DURATION);
        mAnimatorList.add(mDoneDialogToArrowAnimator);

        // arrow up and down animation
        mDoneDialogToArrowUpDownAnimator = ValueAnimator.ofFloat(0f, -0.2f, 0.1f, -0.05f, 0f);
        mDoneDialogToArrowUpDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDoneDialogToArrowUpDownFactor = (float) animation.getAnimatedValue();
            }
        });
        mDoneDialogToArrowUpDownAnimator.setDuration(DONE_DIALOG_UP_DOWN_DURATION);
        mAnimatorList.add(mDoneDialogToArrowUpDownAnimator);

        AnimatorSet dialogToArrowAnimatorSet = new AnimatorSet();
        dialogToArrowAnimatorSet.playSequentially(mDoneDialogToArrowAnimator, mDoneDialogToArrowUpDownAnimator);
        mAnimatorList.add(dialogToArrowAnimatorSet);

        AnimatorSet restToCircleAnimatorSet = new AnimatorSet();
        restToCircleAnimatorSet.playTogether(mDoneRestToCircleAnimator, dialogToArrowAnimatorSet);
        mAnimatorList.add(restToCircleAnimatorSet);

        // done animator set
        mDoneAnimatorSet = new AnimatorSet();
        mDoneAnimatorSet.setInterpolator(new LinearInterpolator());
        mDoneAnimatorSet.playSequentially(mDoneRotateAnimator,
                mDoneLinePackUpAnimator, mDoneArrowShakeAnimator, restToCircleAnimatorSet);
        mAnimatorList.add(mDoneAnimatorSet);
        mDoneAnimatorSet.start();
    }

    private void performFailedAnimation() {
        // failed arrow shake animation
        mFailedArrowShakeAnimator = ValueAnimator.ofFloat(0, 0.05f, -0.05f, 0.1f, -0.15f, 0.25f, -1f);
        mFailedArrowShakeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedArrowUpAndDownFactor = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mFailedArrowShakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_FAILED_ARROW_SHAKE;
            }
        });
        mFailedArrowShakeAnimator.setDuration(FAILED_ANIMATION_DURATION);
        mAnimatorList.add(mFailedArrowShakeAnimator);

        // failed arrow rotate animation
        mFailedArrowRotateAnimator = ValueAnimator.ofFloat(0, 0.5f, -0.5f, 0.25f, 0.25f, 0f);
        mFailedArrowRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedArrowRotateAngle = (float) animation.getAnimatedValue();
            }
        });
        mFailedArrowRotateAnimator.setDuration(FAILED_ANIMATION_DURATION);
        mAnimatorList.add(mFailedArrowRotateAnimator);

        // failed bomb animator
        mFailedBombAnimator = ValueAnimator.ofFloat(0, 1f);
        mFailedBombAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedBombAnimatorPer = (float) animation.getAnimatedValue();
            }
        });
        mFailedBombAnimator.setDuration(FAILED_BOMB_ANIMATION_DURATION);
        mAnimatorList.add(mFailedBombAnimator);

        // arrow shake rotate and line bomb animatorSet
        AnimatorSet arrowShakeRotateAndLineBomb = new AnimatorSet();
        arrowShakeRotateAndLineBomb.playTogether(
                mFailedArrowShakeAnimator, mFailedArrowRotateAnimator, mFailedBombAnimator);
        mAnimatorList.add(arrowShakeRotateAndLineBomb);

        // failed line oscillation animation
        mFailedLineOscillationAnimator = ValueAnimator.ofFloat(0, 0.2F, -0.2F, 0.1F, -0.1F, 0F);
        mFailedLineOscillationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedLineOscillationFactor = (float) animation.getAnimatedValue();
            }
        });
        mFailedLineOscillationAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_FAILED_LINE_OSCILL;
            }
        });
        mFailedLineOscillationAnimator.setDuration(FAILED_ROPE_OSCILLATION_ANIMATION_DURATION);
        mAnimatorList.add(mFailedLineOscillationAnimator);

        // failed line pack up Animator
        mFailedLinePackUpAnimator = ValueAnimator.ofFloat(1, 0f);
        mFailedLinePackUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedLinePackUpFactor = (float) animation.getAnimatedValue();
            }
        });
        mFailedLinePackUpAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_FAILED_LINE_PACK_UP;
            }
        });
        mFailedLinePackUpAnimator.setDuration(FAILED_ROPE_PACK_UP_ANIMATION_DURATION);
        mAnimatorList.add(mFailedLinePackUpAnimator);

        // failed circle scale animation
        mFailedCircleScaleAnimator = ValueAnimator.ofFloat(0, 1.1f, 0.9f, 1.0f);
        mFailedCircleScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedCircleScaleFactor = (float) animation.getAnimatedValue();
            }
        });
        mFailedCircleScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mCurrentState = STATE_FAILED_CIRCLE_SCALE;
            }
        });
        mFailedCircleScaleAnimator.setInterpolator(new LinearInterpolator());
        mFailedCircleScaleAnimator.setDuration(FAILED_CIRCLE_SCALE_ANIMATION_DURATION);
        mAnimatorList.add(mFailedCircleScaleAnimator);

        AnimatorSet lineOscillPackAndCircleScaleSet = new AnimatorSet();
        lineOscillPackAndCircleScaleSet.playSequentially(
                mFailedLineOscillationAnimator, mFailedLinePackUpAnimator, mFailedCircleScaleAnimator);
        mAnimatorList.add(lineOscillPackAndCircleScaleSet);

        // failed arrow move animation
        mFailedArrowMoveAnimator = ValueAnimator.ofFloat(0, .2f, .4f, .6f, .8f, 1f, .95f, 1f);
        mFailedArrowMoveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFailedArrowMoveNormalizeTiem = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mFailedArrowMoveAnimator.setDuration(FAILED_ARROW_MOVE_ANIMATION_DURATION);
        mAnimatorList.add(mFailedArrowMoveAnimator);

        AnimatorSet lineOscillAndArrowMoveSet = new AnimatorSet();
        lineOscillAndArrowMoveSet.playTogether(lineOscillPackAndCircleScaleSet, mFailedArrowMoveAnimator);
        mAnimatorList.add(lineOscillAndArrowMoveSet);

        mFailedAnimatorSet = new AnimatorSet();
        mFailedAnimatorSet.setInterpolator(new LinearInterpolator());
        mFailedAnimatorSet.playSequentially(arrowShakeRotateAndLineBomb, lineOscillAndArrowMoveSet);
        mAnimatorList.add(mFailedAnimatorSet);
        mFailedAnimatorSet.start();
    }

    public void releaseAnimation() {
        if (mAnimatorList == null || mAnimatorList.size() == 0) {
            return;
        }

        for (Animator animator : mAnimatorList) {
            if (animator == null) {
                continue;
            }

            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).removeAllUpdateListeners();
            }
            animator.removeAllListeners();
            animator.cancel();
            animator = null;
        }
        mAnimatorList.clear();
    }

    public void updateProgress(int progress) {
        // adjust progress in area 0 to 100
        if (progress < ZERO_PROGRESS) {
            progress = ZERO_PROGRESS;
        } else if (progress > FULL_PROGRESS) {
            progress = FULL_PROGRESS;
        }
        mNextProgress = Math.max(progress, mNextProgress);
    }

    public void onFail() {
        mIsFailed = true;
    }

    private int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    private float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

}
