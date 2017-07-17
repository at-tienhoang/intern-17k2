package vn.asiantech.internship;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.main.NoteActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DetailNoteTest {
    @Rule
    public ActivityTestRule<NoteActivity> activityTestRule = new ActivityTestRule<NoteActivity>(NoteActivity.class);
    private List<NoteItem> mNotes;

    @Test
    public void testDetailFragment() {
        for (int i = 0; i < mNotes.size(); i++) {
            openNoteActivity();
            Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.click())).check(ViewAssertions.doesNotExist());
            Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_detail)));
            Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
            Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
            Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
            Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(i).getTitle())));
            Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(i).getContent())));
            Espresso.onView(ViewMatchers.withId(R.id.tvNoteTime)).check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(i).getStringTime())));
        }
    }

    @Before
    public void openNoteActivity() {
        Intent intent = new Intent();
        activityTestRule.launchActivity(intent);
        mNotes = activityTestRule.getActivity().getNotes();
        Espresso.onView(ViewMatchers.withId(R.id.llMain)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_note)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
