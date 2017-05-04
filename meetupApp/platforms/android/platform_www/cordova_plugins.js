cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "fr.ptibulle.cordova.meetupservice2.meetupservice2",
        "file": "plugins/fr.ptibulle.cordova.meetupservice2/www/meetupservice2.js",
        "pluginId": "fr.ptibulle.cordova.meetupservice2",
        "clobbers": [
            "meetupservice2"
        ]
    },
    {
        "id": "fr.ptibulle.cordova.meetupservice1.meetupservice1",
        "file": "plugins/fr.ptibulle.cordova.meetupservice1/www/meetupservice1.js",
        "pluginId": "fr.ptibulle.cordova.meetupservice1",
        "clobbers": [
            "meetupservice1"
        ]
    },
    {
        "id": "fr.ptibulle.cordova.meetupservice3.meetupservice3",
        "file": "plugins/fr.ptibulle.cordova.meetupservice3/www/meetupservice3.js",
        "pluginId": "fr.ptibulle.cordova.meetupservice3",
        "clobbers": [
            "meetupservice3"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.2",
    "fr.ptibulle.cordova.meetupservice2": "1.0.0",
    "fr.ptibulle.cordova.meetupservice1": "1.0.0",
    "fr.ptibulle.cordova.meetupservice3": "1.0.0"
};
// BOTTOM OF METADATA
});