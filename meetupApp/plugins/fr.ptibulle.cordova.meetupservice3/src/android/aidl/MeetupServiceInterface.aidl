// MeetupServiceInterface.aidl
package fr.ptibulle.meetupservice3;

import fr.ptibulle.meetupservice3.MeetupServiceCallbackInterface;

// Declare any non-default types here with import statements

interface MeetupServiceInterface {
    /**
     * Demarrage du timer
     */
    void startTimer();

    /**
     * Arret du timer
     */
	void stopTimer();

    void register(MeetupServiceCallbackInterface callback);

    void unregister(MeetupServiceCallbackInterface callback);

}
