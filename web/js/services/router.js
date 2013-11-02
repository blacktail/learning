define([
	'app',
	'lodash',
	'common/BaseRouter'
], function (app, _, BaseRouter) {
	return BaseRouter.extend({
		routes: {
			"(services)": "services"
		},

		services: function () {
			this.switchTopView('services', 'services', arguments);
		}
	});
});
