'use strict';
//TODO: use callbacks

ttmApp.factory('openDataApi', ['$http', function($http){
	//Constantes pour Open Data
	var OD_URL = "http://api.loire-atlantique.fr:80/opendata/1.0";

	return {
		themes: function(callback) {
			$http.get(OD_URL+"/category/p2_100729%2Cp2_100730%2Cp2_100731%2Cp2_100732/children?depth=1").success(callback);
		},
		events: function(category, currentPage, callback) {
			//période de 30 jours, nombre d'items de 15

			//in case of several themes
			// var catIds = "";
			// for(var i=0; i<themeList.length;i++) {
			// 	catIds += themeList[i];
			// 	if(i < themeList.length - 1) {
			// 		catIds += "%2C";
			// 	}
			// }
			$http.get(OD_URL+"/event/summary?catIds="+category+"&periodOfTime=30&itemsPerPage=15&page="+currentPage).success(callback);
		},
		eventDetailed: function(eventId, callback) {
			$http.get(OD_URL+"/event/"+eventId).success(callback);
		}
	}
}]);