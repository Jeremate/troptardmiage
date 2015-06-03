'use strict';

ttmApp.factory('openDataApi', ['$http', function($http){
	//Constantes pour Open Data
	var OD_URL = "http://api.loire-atlantique.fr:80/opendata/1.0";
	
	return {
		categories: function() {
			return $http.get(OD_URL+"/category/p2_100729%2Cp2_100730%2Cp2_100731%2Cp2_100732/children?depth=1");
		},
		events: function(category, currentPage) {
			//période de 30 jours, nombre d'items de 15
			
			//in case of several categories
			// var catIds = "";
			// for(var i=0; i<categoryList.length;i++) {
			// 	catIds += categoryList[i];
			// 	if(i < categoryList.length - 1) {
			// 		catIds += "%2C";
			// 	}
			// }
			return $http.get(OD_URL+"/event/summary?catIds="+category+"&periodOfTime=30&itemsPerPage=15&page="+currentPage);
		},
		eventDetailed: function(eventId) {
			return $http.get(OD_URL+"/event/"+eventId);	
		}
	}	
}]);