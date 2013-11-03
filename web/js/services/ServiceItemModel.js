define(['backbone', 'app'], function(Backbone, app) {
	return Backbone.Model.extend({
		urlRoot: app.ajaxBase + 'service',

		parse: function(resp) {
			if (resp.status) {
				return resp.data;
			} else {
				return resp;
			}
		},

		validate: function(attrs) {
			// backend validate
			if (attrs.status === false) {
				return attrs.msg;
			}

			var errMsgs = [];

			// frontend validate
			if (!attrs.name) {
				errMsgs.push('Name is required.');
			}

			return errMsgs.join('<br>');
		},

		url: function() {
			var base = _.result(this, 'urlRoot') || _.result(this.collection, 'url') || urlError();
			if (this.isNew()) return base;
			return base + '?id=' + encodeURIComponent(this.id);
		}
	});
});