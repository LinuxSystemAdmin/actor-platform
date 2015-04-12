package im.actor.messenger.app.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import im.actor.messenger.R;
import im.actor.model.entity.Avatar;
import im.actor.model.files.FileSystemReference;
import im.actor.model.viewmodel.FileVM;
import im.actor.model.viewmodel.FileVMCallback;

import static im.actor.messenger.app.Core.messenger;

/**
 * Created by ex3ndr on 26.12.14.
 */
public class CoverAvatarView extends SimpleDraweeView {

    private FileVM fileVM;
    private FileVM fullFileVM;
    private boolean isLoaded;

    public CoverAvatarView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public CoverAvatarView(Context context) {
        super(context);
        init();
    }

    public CoverAvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverAvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        if (isInEditMode()) {
            return;
        }

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());

        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(160)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setBackground(getResources().getDrawable(R.drawable.img_profile_avatar_default))
                .setOverlay(new CoverOverlayDrawable(getContext()))
                .build();
        setHierarchy(hierarchy);
    }

    public void bind(Avatar avatar) {
        if (fileVM != null) {
            fileVM.detach();
            fileVM = null;
        }
        if (fullFileVM != null) {
            fullFileVM.detach();
            fullFileVM = null;
        }
        isLoaded = false;

        if (avatar != null && avatar.getSmallImage() != null) {
            fileVM = messenger().bindFile(avatar.getSmallImage().getFileReference(), true, new FileVMCallback() {
                @Override
                public void onNotDownloaded() {

                }

                @Override
                public void onDownloading(float progress) {

                }

                @Override
                public void onDownloaded(FileSystemReference reference) {
                    if (!isLoaded) {
                        setImageURI(Uri.fromFile(new File(reference.getDescriptor())));
                    }
                }
            });
            if (avatar.getFullImage() != null) {
                fullFileVM = messenger().bindFile(avatar.getFullImage().getFileReference(), true, new FileVMCallback() {
                    @Override
                    public void onNotDownloaded() {

                    }

                    @Override
                    public void onDownloading(float progress) {

                    }

                    @Override
                    public void onDownloaded(FileSystemReference reference) {
                        isLoaded = true;
                        setImageURI(Uri.fromFile(new File(reference.getDescriptor())));
                    }
                });
            }
        }
    }

    public void setOffset(int offset) {
        setScrollY(-offset / 2);
    }

    public void unbind() {
        if (fileVM != null) {
            fileVM.detach();
            fileVM = null;
        }
        if (fullFileVM != null) {
            fullFileVM.detach();
            fullFileVM = null;
        }
    }
}
