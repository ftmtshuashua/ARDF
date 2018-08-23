package com.lfp.ardf.solution.animation;

/**
 * <pre>
 * desc:
 *      简单附着物监听
 *
 * function:
 *      getValue()      :获得计算之后的值
 * Created by LiFuPing on 2018/7/25.
 * </pre>
 */
@Deprecated
public class SimperAttachmentListener implements TimelineAttachment.AttachmentListener {
    float mValue;

    @Override
    public void onAttachmentStart(TimelineAttachment animation) {

    }

    @Override
    public void onAttachmentEnd(TimelineAttachment animation) {

    }

    @Override
    public void onAttachmentRepeat(TimelineAttachment animation) {

    }

    @Override
    public void onElapse(TimelineAttachment animation, float value) {
        mValue = value;
    }

    /**
     * 获得当前时间点对应的进度值
     *
     * @return 进度值
     */
    public float getValue() {
        return mValue;
    }
}
