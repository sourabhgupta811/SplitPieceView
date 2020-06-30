# SplitPieceView

![Test Image 1](screenshot.gif)

<b>How to use-</b><br>
Add the layout that you want to split in the custom frame.

```
<com.samnetworks.alternateswipe.CustomShatterFrame xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/image_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:scaleType="centerCrop">

    </ImageView>
</com.samnetworks.alternateswipe.CustomShatterFrame>
```
And call CustomShatterFrame.advance() method to start the shatter animation.<br><br>
<b> Transformer used for the sample above:</b>
```
    override fun transformPage(page: View, position: Float) {
        if (position >= 0) {
            page.elevation = 1f - 0.3f * position
            page.translationY = -page.height * position
        } else {
            page.translationY = -page.height * position
            if (page is CustomShatterFrame) {
                page.advance(position)
            }
        }
    }
```

