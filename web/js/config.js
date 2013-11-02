require.config({
	baseUrl: 'js',
	packages: ['services',
		'common/templates', 'services/templates'
	],
	paths: {
		'jquery': '../vendor/jquery/jquery',
		'lodash': '../vendor/lodash/dist/lodash',
		'backbone': '../vendor/backbone/backbone',
		'handlebars': '../vendor/handlebars/handlebars',
		'modernizr': '../vendor/modernizr/modernizr',
		'ztree': '../vendor/ztree/js/jquery.ztree.core-3.5',
		'foundation': '../vendor/bower-foundation/js/foundation.min',
		'common/partials_compiled': 'common/templates/.auto_partials',
		'common/templates_compiled': 'common/templates/.auto_templates',
		'services/templates_compiled': 'services/templates/.auto_templates'
	},
	shim: {
		'handlebars': {
			exports: 'Handlebars'
		},
		'backbone': {
			deps: ['lodash', 'jquery'],
			exports: 'Backbone'
		},
		'foundation': {
			deps: ['jquery'],
			exports: 'Foundation'
		},
		'modernizr': {
			exports: 'Modernizr'
		},
		'ztree': {
			deps: ['jquery']
		}
	},

	urlArgs: "bust=" + (new Date()).getTime()
});
