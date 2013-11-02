define([
	'modernizr',
	'lodash',
	'jquery',
	'foundation',
	'backbone',
	'handlebars',
	'app',
	'routers',
	'common/templates',
	'ztree'
], function (Modernizr, _, $, Foundation, Backbone, Handlebars, app, routers) {
	app.topView = null;

	_.each(routers, function (Router) {
		if (Router && _.isFunction (Router)) {
			new Router();
		}
	});

	Backbone.history.start({
		pushState: false,
		hashChange: true
	});

	$(document).foundation();
});
