define([
	'lodash',
	'backbone',
	'services/templates',
	'jquery',
	'app',
	'./ServiceItemModel',
	'./ServiceItemView'
], function(_, Backbone, templates, $, app, ServiceItemModel, ServiceItemView) {
	var ServicesView = Backbone.View.extend({
		tagName: 'div',

		id: 'servicesPage',

		template: templates.services,

		events: {
			'click #btnAddServ': 'addService'
		},

		initialize: function(options) {
			this.listenTo(this.model, 'change', this.render);
		},

		render: function(model) {
			this.$el.html(this.template(this.model.toJSON()));
			this.$('.section-container').foundation('section', 'reflow');

			// init services tree
			this.initServiceTree();
		},

		initServiceTree: function() {
			var setting = {
				view: {
					showLine: false
				},
				async: {
					enable: true,
					url: app.ajaxBase + "getServiceNodes",
					autoParam: ["id", "name", "level"],
					contentType: 'application/json',
					type: 'get',
					dataFilter: function(treeId, parentNode, childNodes) {
						if (!childNodes) return null;

						_.each(childNodes, function(node) {
							node.iconSkin = 'lm';
						});

						return childNodes;
					}
				},
				callback: {
					onClick: _.bind(this.editService, this)
				}
			};

			$.fn.zTree.init(this.$("#servTreeCon"), setting);
		},

		editService: function(event, treeId, treeNode) {
			this.addOrEditService(true, treeNode);
		},

		addService: function() {
			this.addOrEditService();
		},

		addOrEditService: function(isEdit, treeNode) {
			if (treeNode && treeNode.isParent) {
				return;
			}

			if (this.serviceItemView) {
				this.serviceItemView.remove();
				this.stopListening(this.serviceItemView);
			}

			var serviceItemModel = this.serviceItemModel = new ServiceItemModel(),
				serviceItemView = this.serviceItemView = new ServiceItemView({
					model: serviceItemModel
				});

			this.$('#servItemCon').append(serviceItemView.el);

			if (isEdit) {
				if (treeNode) {
					this.serviceItemModel.set({
						id: treeNode.id
					}, {
						silent: true
					});
				}

				this.serviceItemModel.fetch();
				this.listenTo(this.serviceItemView, 'service:deleted', this.onServiceDeleted);
			} else {
				this.serviceItemModel.set('isNew', true);
			}

			this.listenTo(this.serviceItemView, 'service:updated', this.onServiceUpdated);
			this.listenTo(this.serviceItemView, 'service:added', this.onServiceAdded);
		},

		onServiceUpdated: function(service) {
			var zTreeObj = $.fn.zTree.getZTreeObj('servTreeCon'),
				node = zTreeObj.getNodesByFilter(function(node) {
					return node.id == service.id;
				}, true);

			if (node) {
				node.name = service.name;

				zTreeObj.updateNode(node);
			}
		},

		onServiceAdded: function(service) {
			var zTreeObj = $.fn.zTree.getZTreeObj('servTreeCon'),
				node = zTreeObj.addNodes(null, {
					id: service.id,
					name: service.name,
					iconSkin: 'lm'
				});


			if (node && node.length > 0) {
				$('#' + node[0].tId + '_a').click();
			}
		},

		onServiceDeleted: function(service) {
			var zTreeObj = $.fn.zTree.getZTreeObj('servTreeCon'),
				node = zTreeObj.getNodesByFilter(function(node) {
					return node.id == service.id;
				}, true);

			if (node) {
				zTreeObj.removeNode(node);
			}
		}
	});

	return ServicesView;
});