define([
	'lodash',
	'backbone',
	'common/utils',
	'app'
], function (_, Backbone, utils, app) {
	return Backbone.Router.extend({
		switchTopView: function (switchToPath, routeName, routeArguments) {
			routeArguments = [].slice.apply(routeArguments || []);

			// free app views
			if (app.topView) {
				app.topView.remove();
			}

			$('#main').removeClass();

			app.topView = null; 

			var router = this;

			// we must use 'switchToPath/main' here for requirejs build optimization
			require([switchToPath + '/main'], function (main) {
				var initData = window.initData;
				window.initData = null;
				main.apply(main, [router].concat(routeName).concat([initData]).concat(routeArguments));
			});
		}
	});
});
