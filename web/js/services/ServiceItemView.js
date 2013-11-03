define([
	'lodash',
	'backbone',
	'services/templates',
	'jquery',
	'app'
], function(_, Backbone, templates, $, app) {
	var ServiceItemView = Backbone.View.extend({
		tagName: 'div',

		template: templates['services/ServiceItem'],

		events: {
			"click #reset": "resetServiceForm",
			"click #submit": 'addOrUpdateService',
			"click #delete": "deleteService"
		},

		initialize: function(options) {
			this.listenTo(this.model, 'change', this.render);
			this.listenTo(this.model, 'error', this.errorHandler);
			this.listenTo(this.model, 'destroy', this.onServiceDeleted);
			this.listenTo(this.model, 'invalid', this.onValidateFailed);
		},

		render: function(model) {
			this.$el.hide();
			this.$el.html(this.template(this.model.toJSON()));

			this.$el.show();

			this.$('.section-container').foundation('section', 'reflow');
		},

		resetServiceForm: function() {
			this.$('form')[0].reset();
		},

		addOrUpdateService: function() {
			var data = this.$('form').serializeArray(),
				obj = {};

			_.each(data, function(param) {
				obj[param.name] = param.value;
			});

			var that = this;

			this.model.save(obj, {
				success: function() {
					var isNew = that.model.get('isNew');

					that.showTips(true, (isNew ? 'Add ' : 'Update ') + 'service successfully.');
					that.trigger(isNew ? 'service:added' : 'service:updated', that.model.toJSON());
				},
				wait: true
			});
		},

		deleteService: function() {
			this.model.destroy({wait: true});
		},

		onServiceDeleted: function(model) {
			this.remove();
			this.trigger('service:deleted', model.toJSON());
		},

		onValidateFailed: function(model, error) {
			this.showTips(false, error);
		},

		showTips: function(success, msg) {
			this.$('#alertMsg').html(msg || 'Add or update service is failed!');

			var removeCls = 'alert',
				addCls = 'success';

			if (!success) {
				removeCls = 'success';
				addCls = 'alert';
			}

			this.$('.alert-box').removeClass(removeCls);
			this.$('.alert-box').addClass(addCls);

			this.$('.alert-box').show();

			setTimeout(function() {
				this.$('.alert-box').fadeOut();
			}, 3000);
		},

		errorHandler: function(model, resp) {
			if (resp.status >= 200 && resp.status < 300) {
				this.showTips(false, 'Parse Error');
			} else {
				var errorObj = {};
				try {
					errorObj = JSON.parse(resp.responseText);
				} catch (e) {

				}

				this.showTips(false, 'Network Error(status: ' + resp.status + '-' + resp.statusText + '): ' + (errorObj.msg || ''));
			}
		}
	});

	return ServiceItemView;
});