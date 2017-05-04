// MeetupServiceInterface.aidl
package fr.ptibulle.meetupservice2;

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

}
