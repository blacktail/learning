define(['backbone', 'app'], function(Backbone, app) {
	return Backbone.Model.extend({
		urlRoot: app.ajaxBase + 'service',
		
		url: function () {
			var base = _.result(this, 'urlRoot') || _.result(this.collection, 'url') || urlError();
			if (this.isNew()) return base;
			return base + '?id=' + encodeURIComponent(this.id);
		}
	});
});
