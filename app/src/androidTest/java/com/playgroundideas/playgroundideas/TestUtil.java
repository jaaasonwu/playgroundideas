package com.playgroundideas.playgroundideas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.Toolbar;

import com.playgroundideas.playgroundideas.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.hamcrest.core.Is.is;

/**
 * Created by Ferdinand on 14/09/2017.
 */

public class TestUtil {

    public static InputStream createImage() {
        Context context = InstrumentationRegistry.getTargetContext();
        Drawable drawable = context.getResources().getDrawable(R.drawable.sample1);
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return new ByteArrayInputStream(stream.toByteArray());
    }

    public static InputStream createText() {
        String text = "This is a text file for testing purposes only.";
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }

    public static User createUser(long id) {
        User user = new User(0, id, "Hans", "Smith", "hans.smith@gmail.com", "0416777888");
        return user;
    }

    public static ViewInteraction matchToolbarTitle(CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
