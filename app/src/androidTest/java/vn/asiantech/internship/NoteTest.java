package vn.asiantech.internship;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import vn.asiantech.internship.ui.note.activity.NoteActivity;

/**
 * Test UI for NoteActivity
 * Created by quanghai on 13/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class NoteTest {
    @Rule
    public ActivityTestRule<NoteActivity> mRule = new ActivityTestRule<>(NoteActivity.class);

    @Test
    public void checkNewNote() {
        onView(withId(R.id.imgNewNote)).perform(click()).check(doesNotExist());
        onView(withId(R.id.edtNoteTitle)).perform(typeText("New note"), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.edtInputContent)).perform(typeText("Content"), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.imgSaveNote)).perform(click()).check(doesNotExist());
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.edtEditNoteTitle)).check(matches(withText("New note")));
        onView(withId(R.id.edtEditInputContent)).check(matches(withText("Content")));
    }

    @Test
    public void checkNewNoteFail() {
        onView(withId(R.id.imgNewNote)).perform(click()).check(doesNotExist());
        onView(withId(R.id.edtInputContent)).perform(typeText("Content"), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.imgSaveNote)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void checkSelectNote() {
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
    }

    @Test
    public void checkEditNoteCompleted() {
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.edtEditNoteTitle)).perform(clearText(), typeText("Edit title"), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.imgEditNote)).perform(click()).check(doesNotExist());
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.edtEditNoteTitle)).check(matches(withText("Edit title")));
    }

    @Test
    public void checkEditNoteFail() {
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.edtEditNoteTitle)).perform(clearText(), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.imgEditNote)).perform(click()).check(matches(isDisplayed()));
    }

    @Test
    public void checkDeleteNote() {
        onView(withId(R.id.imgNewNote)).perform(click()).check(doesNotExist());
        onView(withId(R.id.edtNoteTitle)).perform(typeText("New note"), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.edtInputContent)).perform(typeText("Content"), closeSoftKeyboard()).check(matches(isDisplayed()));
        onView(withId(R.id.imgSaveNote)).perform(click()).check(doesNotExist());
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.imgDeleteNote)).perform(click()).check(doesNotExist());
    }
}
