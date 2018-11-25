package com.bencestumpf.itunessample.helper

import android.view.View
import android.view.ViewGroup

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object ChildMatcher {

    fun hasChildren(numChildrenMatcher: Matcher<Int>): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                return view is ViewGroup && numChildrenMatcher.matches((view as ViewGroup).childCount)
            }

            override fun describeTo(description: Description) {
                description.appendText("a view with # children is ")
                numChildrenMatcher.describeTo(description)
            }
        }
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with $childPosition child view of type parentMatcher")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.getParent() !is ViewGroup) {
                    return parentMatcher.matches(view.getParent())
                }

                val group = view.getParent() as ViewGroup
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition) == view
            }
        }
    }

    fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            internal var currentIndex = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }
}