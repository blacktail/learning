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
			this.listenTo(this.model, 'error', function(model, resp) {
				if (resp.status >= 200 && resp.status < 300) {
					this.showTips(false, 'Parse Error');
				} else {
					this.showTips(false, 'Network Error(status: ' + resp.status + '-' + resp.statusText + ')');
				}
			});
			this.listenTo(this.model, 'destroy', this.onServiceDeleted);
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
					that.showTips(true, (that.model.isNew ? 'Add ' : 'Update ') + 'service successfully.');
					that.trigger(that.model.isNew ? 'service:added' : 'service:updated', that.model.toJSON());
				}
			});
		},

		deleteService: function() {
			this.model.destroy({wait: true});
		},

		onServiceDeleted: function(model) {
			this.remove();
			console.log(this.model.toJSON(), model.toJSON());
			this.triggler('service:deleted', model.toJSON());
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
		}
	});

	return ServiceItemView;
});