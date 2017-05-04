cordova.define("fr.ptibulle.cordova.meetupservice3.meetupservice3", function(require, exports, module) {
/**
 * cordova is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) Matt Kane 2010
 * Copyright (c) 2011, IBM Corporation
 */

var exec = require('cordova/exec');

module.exports = {
	bindService : function (successCallback, errorCallback, parametres) {
            if (errorCallback == null) {
                errorCallback = function () {};
            }

            if (typeof errorCallback != "function") {
                console.log("meetupservice3.bindService failure: error callback parameter must be a function");
                return;
            }

            if (typeof successCallback != "function") {
                console.log("meetupservice3.bindService failure: success callback parameter must be a function");
                return;
            }

            exec(successCallback, errorCallback, 'MeetupService3', 'bindService', [parametres]);
    },
    unbindService : function (successCallback, errorCallback, parametres) {
            if (errorCallback == null) {
                errorCallback = function () {};
            }

            if (typeof errorCallback != "function") {
                console.log("meetupservice3.unbindService failure: error callback parameter must be a function");
                return;
            }

            if (typeof successCallback != "function") {
                console.log("meetupservice3.unbindService failure: success callback parameter must be a function");
                return;
            }

            exec(successCallback, errorCallback, 'MeetupService3', 'unbindService', [parametres]);
    },
    startTimer : function (successCallback, errorCallback, parametres) {
            if (errorCallback == null) {
                errorCallback = function () {};
            }

            if (typeof errorCallback != "function") {
                console.log("meetupservice3.startTimer failure: error callback parameter must be a function");
                return;
            }

            if (typeof successCallback != "function") {
                console.log("meetupservice3.startTimer failure: success callback parameter must be a function");
                return;
            }

            exec(successCallback, errorCallback, 'MeetupService3', 'startTimer', [parametres]);
    },
    stopTimer : function (successCallback, errorCallback) {
            if (errorCallback == null) {
                errorCallback = function () {};
            }

            if (typeof errorCallback != "function") {
                console.log("meetupservice3.stopTimer failure: error callback parameter must be a function");
                return;
            }

            if (typeof successCallback != "function") {
                console.log("meetupservice3.stopTimer failure: success callback parameter must be a function");
                return;
            }

            exec(successCallback, errorCallback, 'MeetupService3', 'stopTimer', []);
    },
    register : function (successCallback, errorCallback, parametres) {
            if (errorCallback == null) {
                errorCallback = function () {};
            }

            if (typeof errorCallback != "function") {
                console.log("meetupservice3.register failure: error callback parameter must be a function");
                return;
            }

            if (typeof successCallback != "function") {
                console.log("meetupservice3.register failure: success callback parameter must be a function");
                return;
            }

            exec(successCallback, errorCallback, 'MeetupService3', 'register', [parametres]);
    },
    unregister : function (successCallback, errorCallback, parametres) {
            if (errorCallback == null) {
                errorCallback = function () {};
            }

            if (typeof errorCallback != "function") {
                console.log("meetupservice3.unregister failure: error callback parameter must be a function");
                return;
            }

            if (typeof successCallback != "function") {
                console.log("meetupservice3.unregister failure: success callback parameter must be a function");
                return;
            }

            exec(successCallback, errorCallback, 'MeetupService3', 'unregister', [parametres]);
    }
};

});
