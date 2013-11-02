define([
	'handlebars',
	'./helpers',
	'common/partials_compiled',
	'lodash'
], function (Handlebars, helpers, partials, _) {
	// register helpers
	_.each(helpers || [], function (helper, name) {
		Handlebars.registerHelper(name, helper);
	});
});
