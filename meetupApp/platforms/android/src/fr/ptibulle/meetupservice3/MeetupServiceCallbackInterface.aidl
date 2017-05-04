// MeetupServiceCallbackInterface.aidl
package fr.ptibulle.meetupservice3;

// Declare any non-default types here with import statements

interface MeetupServiceCallbackInterface {
    /**
     * notify message
     */
    void notify(String message);
}
